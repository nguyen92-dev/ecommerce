package top.nguyennd.ecom.order.domain.user.vo;

import lombok.Builder;
import top.nguyennd.ecom.shared.error.domain.Assert;

@Builder
public record UserAddress(String street, String city, String zipCode, String country) {

  public UserAddress {
    Assert.field("street", street).notNull();
    Assert.field("city", city).notNull();
    Assert.field("zipCode", zipCode).notNull();
    Assert.field("country", country).notNull();
  }

}
