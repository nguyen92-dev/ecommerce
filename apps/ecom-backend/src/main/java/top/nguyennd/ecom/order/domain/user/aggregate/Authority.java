package top.nguyennd.ecom.order.domain.user.aggregate;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import top.nguyennd.ecom.order.domain.user.vo.AuthorityName;
import top.nguyennd.ecom.shared.error.domain.Assert;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class Authority {

  AuthorityName name;

  public Authority(AuthorityName authorityName) {
    Assert.notNull("authority", authorityName);
    this.name = authorityName;
  }
}
