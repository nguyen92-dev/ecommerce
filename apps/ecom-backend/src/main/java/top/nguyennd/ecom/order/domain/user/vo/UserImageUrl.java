package top.nguyennd.ecom.order.domain.user.vo;

import top.nguyennd.ecom.shared.error.domain.Assert;

public record UserImageUrl(String value) {

  public UserImageUrl {
    Assert.field("value", value).maxLength(1000);
  }
}
