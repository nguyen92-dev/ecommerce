package top.nguyennd.ecom.order.infrastructure.secondary.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import top.nguyennd.ecom.order.domain.user.aggregate.Authority;
import top.nguyennd.ecom.order.domain.user.vo.AuthorityName;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "authority")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthorityEntity implements Serializable {
  @NotNull
  @Size(max = 50)
  @Id
  @Column(length = 50)
  private String name;

  public static Set<AuthorityEntity> from(Set<Authority> authorities) {
    return authorities.stream()
      .map(authority -> AuthorityEntity.builder().name(authority.getName().name()).build())
      .collect(Collectors.toSet());
  }

  public static Set<Authority> toDomain(Set<AuthorityEntity> authorityEntities) {
    return authorityEntities.stream().map(
        authorityEntity -> Authority.builder().name(new AuthorityName(authorityEntity.getName())).build())
      .collect(Collectors.toSet());
  }
}
