package top.nguyennd.ecom.order.infrastructure.primary;

import lombok.Builder;
import top.nguyennd.ecom.order.domain.user.aggregate.User;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Builder
public record RestUser(UUID publicId,
                       String firstName,
                       String lastName,
                       String email,
                       String imageUrl,
                       Set<String> authorities) {

  public static RestUser from(User user) {
    var restUserBuilder = RestUser.builder();
    if (nonNull(user.getImageUrl())) {
      restUserBuilder.imageUrl(user.getImageUrl().value());
    }
    return restUserBuilder
      .publicId(user.getUserPublicId().value())
      .firstName(user.getFirstName().firstName())
      .lastName(user.getLastName().value())
      .email(user.getEmail().email())
      .authorities(RestAuthority.fromSet(user.getAuthorities()))
      .build();
  }
}
