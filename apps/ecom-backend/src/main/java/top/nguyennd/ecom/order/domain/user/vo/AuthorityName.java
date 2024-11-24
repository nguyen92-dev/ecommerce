package top.nguyennd.ecom.order.domain.user.vo;

import top.nguyennd.ecom.shared.error.domain.Assert;

public record AuthorityName(String name) {

  public AuthorityName {
    Assert.field("name", name).notNull();

  }
}
