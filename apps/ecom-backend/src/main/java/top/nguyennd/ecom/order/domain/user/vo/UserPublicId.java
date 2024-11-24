package top.nguyennd.ecom.order.domain.user.vo;

import top.nguyennd.ecom.shared.error.domain.Assert;

import java.util.UUID;

public record UserPublicId(UUID value) {

  public UserPublicId {
    Assert.notNull("value", value);
  }
}
