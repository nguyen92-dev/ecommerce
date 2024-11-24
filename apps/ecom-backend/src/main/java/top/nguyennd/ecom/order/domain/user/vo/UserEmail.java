package top.nguyennd.ecom.order.domain.user.vo;

import lombok.Builder;
import top.nguyennd.ecom.shared.error.domain.Assert;

@Builder
public record UserEmail(String email) {

  public UserEmail {
    Assert.field("email", email()).maxLength(255);
  }
}
