package top.nguyennd.ecom.order.domain.user.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.security.oauth2.jwt.Jwt;
import top.nguyennd.ecom.order.domain.user.aggregate.User;
import top.nguyennd.ecom.order.domain.user.repository.UserRepository;
import top.nguyennd.ecom.order.domain.user.vo.UserAddressToUpdate;
import top.nguyennd.ecom.order.infrastructure.secondary.service.kinde.KindeService;
import top.nguyennd.ecom.shared.authentication.application.AuthenticatedUser;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserSynchronizer {
  UserRepository userRepository;
  KindeService kindeService;
  static String UPDATE_AT_KEY = "last_signed_in";

  public UserSynchronizer(UserRepository userRepository, KindeService kindeService) {
    this.userRepository = userRepository;
    this.kindeService = kindeService;
  }

  public void syncWithIdp(Jwt jwtToken, boolean forceReSync) {
    Map<String, Object> claims = jwtToken.getClaims();
    List<String> rolesFromToken = AuthenticatedUser.extractRolesFromToken(jwtToken);
    Map<String, Object> userInfo = kindeService.getUserInfo(claims.get("sub").toString());
    User user = User.fromTokenAttributes(userInfo, rolesFromToken);
    Optional<User> existingUser = userRepository.getOneByEmail(user.getEmail());
    if (existingUser.isPresent()) {
      if (claims.get(UPDATE_AT_KEY) != null) {
        Instant lastModifiedDate = existingUser.get().getLastModifiedDate();
        Instant idpModifiedDate = Instant.ofEpochSecond((Integer) claims.get(UPDATE_AT_KEY));

        if (idpModifiedDate.isAfter(lastModifiedDate) || forceReSync) {
          updateUser(user, existingUser.get());
        }
      }
    } else {
      user.initFieldForSignup();
      userRepository.save(user);
    }

  }

  private void updateUser(User user, User existingUser) {
    existingUser.updateFromUser(user);
    userRepository.save(existingUser);
  }

  public void updateAddress(UserAddressToUpdate userAddressToUpdate) {
    userRepository.updateAddress(userAddressToUpdate.userPublicId(), userAddressToUpdate);
  }
}
