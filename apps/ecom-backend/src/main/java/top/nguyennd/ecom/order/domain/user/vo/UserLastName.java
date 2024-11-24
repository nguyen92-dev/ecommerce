package top.nguyennd.ecom.order.domain.user.vo;

import top.nguyennd.ecom.shared.error.domain.Assert;

public record UserLastName(String value) {

  public UserLastName {
    Assert.field("value", value).maxLength(255);
  }
}
