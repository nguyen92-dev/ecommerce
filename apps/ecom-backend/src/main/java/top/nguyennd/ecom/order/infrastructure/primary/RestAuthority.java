package top.nguyennd.ecom.order.infrastructure.primary;

import lombok.Builder;
import top.nguyennd.ecom.order.domain.user.aggregate.Authority;

import java.util.Set;
import java.util.stream.Collectors;

@Builder
public record RestAuthority(String name) {

  public static Set<String> fromSet(Set<Authority> authorities) {
    return authorities.stream().map(authority -> authority.getName().name())
      .collect(Collectors.toSet());
  }
}
