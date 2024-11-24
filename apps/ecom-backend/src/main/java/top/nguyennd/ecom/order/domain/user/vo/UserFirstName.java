package top.nguyennd.ecom.order.domain.user.vo;

import top.nguyennd.ecom.shared.error.domain.Assert;

public record UserFirstName(String firstName) {

  public UserFirstName {
    Assert.field("firstName",firstName).maxLength(255);
  }
}
