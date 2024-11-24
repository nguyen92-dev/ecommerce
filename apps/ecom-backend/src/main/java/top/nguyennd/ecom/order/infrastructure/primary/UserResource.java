package top.nguyennd.ecom.order.infrastructure.primary;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.nguyennd.ecom.order.application.UserApplicationService;
import top.nguyennd.ecom.order.domain.user.aggregate.User;

@RestController
@RequestMapping("/api/users")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserResource {

  UserApplicationService userApplicationService;

  public UserResource(UserApplicationService userApplicationService) {
    this.userApplicationService = userApplicationService;
  }

  @GetMapping("/authenticated")
  public ResponseEntity<RestUser> getAuthenticatedUser(@AuthenticationPrincipal Jwt jwtToken,
                                                       @RequestParam boolean forceReSync) {
    User user = userApplicationService.getAuthenticatedUserWithSync(jwtToken, forceReSync);
    return ResponseEntity.ok(RestUser.from(user));
  }
}
