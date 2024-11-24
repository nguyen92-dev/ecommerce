package top.nguyennd.ecom.order.domain.user.vo;

import lombok.Builder;
import top.nguyennd.ecom.shared.error.domain.Assert;

@Builder
public record UserAddressToUpdate(UserPublicId userPublicId, UserAddress userAddress) {

  public UserAddressToUpdate {
    Assert.notNull("userPublicId", userPublicId);
    Assert.notNull("userAddress", userAddress);
  }
}
