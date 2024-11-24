package top.nguyennd.ecom.order.domain.user.aggregate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import top.nguyennd.ecom.order.domain.user.vo.*;
import top.nguyennd.ecom.shared.error.domain.Assert;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class User {
  UserLastName lastName;
  UserFirstName firstName;
  UserEmail email;
  UserPublicId userPublicId;
  UserImageUrl imageUrl;
  Instant createdDate;
  Instant lastModifiedDate;
  Set<Authority> authorities;
  Long dbId;
  UserAddress userAddress;
  Instant lastSeen;

  private void assertMandatoryFields() {
    Assert.notNull("lastName", lastName);
    Assert.notNull("firstName", firstName);
    Assert.notNull("email", email);
    Assert.notNull("authorities", authorities);
  }

  public void updateFromUser(User user) {
    this.email = user.email;
    this.imageUrl = user.imageUrl;
    this.firstName = user.firstName;
    this.lastName = user.lastName;
  }

  public void initFieldForSignup() {
    this.userPublicId = new UserPublicId(UUID.randomUUID());
  }

  public static User fromTokenAttributes(Map<String, Object> attributes, List<String> rolesFromAccessToken) {
    UserBuilder userBuilder = User.builder();

    if (attributes.containsKey("preferred_email")) {
      userBuilder.email(new UserEmail(attributes.get("preferred_email").toString()));
    }

    if (attributes.containsKey("last_name")) {
      userBuilder.lastName(new UserLastName(attributes.get("last_name").toString()));
    }

    if (attributes.containsKey("first_name")) {
      userBuilder.firstName(new UserFirstName(attributes.get("first_name").toString()));
    }

    if (attributes.containsKey("picture")) {
      userBuilder.imageUrl(new UserImageUrl(attributes.get("picture").toString()));
    }

    if (attributes.containsKey("last_signed_in")) {
      userBuilder.lastSeen(Instant.parse(attributes.get("last_signed_in").toString()));
    }

    Set<Authority> authorities = rolesFromAccessToken.stream()
      .map(authority -> Authority.builder().name(new AuthorityName(authority)).build())
      .collect(Collectors.toSet());

    userBuilder.authorities(authorities);
    return userBuilder.build();
  }
}
