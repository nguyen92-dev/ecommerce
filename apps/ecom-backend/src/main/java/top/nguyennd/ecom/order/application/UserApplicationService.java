package top.nguyennd.ecom.order.application;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.nguyennd.ecom.order.domain.user.aggregate.User;
import top.nguyennd.ecom.order.domain.user.repository.UserRepository;
import top.nguyennd.ecom.order.domain.user.service.UserReader;
import top.nguyennd.ecom.order.domain.user.service.UserSynchronizer;
import top.nguyennd.ecom.order.domain.user.vo.UserAddressToUpdate;
import top.nguyennd.ecom.order.domain.user.vo.UserEmail;
import top.nguyennd.ecom.order.infrastructure.secondary.service.kinde.KindeService;
import top.nguyennd.ecom.shared.authentication.application.AuthenticatedUser;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserApplicationService {

  UserSynchronizer userSynchronizer;
  UserReader userReader;

  public UserApplicationService(UserRepository userRepository, KindeService kindeService) {
    this.userSynchronizer = new UserSynchronizer(userRepository, kindeService);
    this.userReader = new UserReader(userRepository);
  }

  @Transactional
  public User getAuthenticatedUserWithSync(Jwt jwtToken, boolean forceReSync) {
    userSynchronizer.syncWithIdp(jwtToken, forceReSync);
    return userReader.getByEmail(new UserEmail(AuthenticatedUser.username().get()))
      .orElseThrow();
  }

  @Transactional(readOnly = true)
  public User getAuthenticatedUser() {
    return userReader.getByEmail(new UserEmail(AuthenticatedUser.username().get()))
      .orElseThrow();
  }

  @Transactional(readOnly = true)
  public void updateAddress(UserAddressToUpdate userAddressToUpdate) {
    userSynchronizer.updateAddress(userAddressToUpdate);
  }
}
