package top.nguyennd.ecom.order.infrastructure.secondary.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.nguyennd.ecom.order.domain.user.aggregate.User;
import top.nguyennd.ecom.order.domain.user.vo.UserAddress;
import top.nguyennd.ecom.order.domain.user.vo.UserEmail;
import top.nguyennd.ecom.order.domain.user.vo.UserFirstName;
import top.nguyennd.ecom.order.domain.user.vo.UserImageUrl;
import top.nguyennd.ecom.order.domain.user.vo.UserLastName;
import top.nguyennd.ecom.order.domain.user.vo.UserPublicId;
import top.nguyennd.ecom.shared.jpa.AbstractAuditingEntity;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "ecommerce_user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserEntity extends AbstractAuditingEntity<Long> {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSequenceGenerator")
  @SequenceGenerator(name = "userSequenceGenerator", sequenceName = "user_sequence", allocationSize = 1)
  @Column(name = "id")
  private Long id;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "email")
  private String email;

  @Column(name = "image_url")
  private String imageUrl;

  @Column(name = "public_id")
  @EqualsAndHashCode.Include
  private UUID publicId;

  @Column(name = "address_street")
  private String addressStreet;

  @Column(name = "address_city")
  private String addressCity;

  @Column(name = "address_zip_code")
  private String addressZipCode;

  @Column(name = "address_country")
  private String addressCountry;

  @Column(name = "last_seen")
  private Instant lastSeen;

  @ManyToMany(cascade = CascadeType.REMOVE)
  @JoinTable(
    name = "user_authority",
    joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
    inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "name")}
  )
  private Set<AuthorityEntity> authorities = new HashSet<>();

  public void updateFromUser(User user) {
    this.email = user.getEmail().email();
    this.lastName = user.getLastName().value();
    this.firstName = user.getFirstName().firstName();
    this.imageUrl = user.getImageUrl().value();
    this.lastSeen = user.getLastSeen();
  }

  public static UserEntity fromUser(User user) {
    UserEntityBuilder userEntityBuilder = UserEntity.builder();

    if (user.getImageUrl() != null) {
      userEntityBuilder.imageUrl(user.getImageUrl().value());
    }
    if (user.getUserPublicId() != null) {
      userEntityBuilder.publicId(user.getUserPublicId().value());
    }

    if (user.getUserAddress() != null) {
      userEntityBuilder.addressCity(user.getUserAddress().city());
      userEntityBuilder.addressStreet(user.getUserAddress().street());
      userEntityBuilder.addressZipCode(user.getUserAddress().zipCode());
      userEntityBuilder.addressCountry(user.getUserAddress().country());
    }

    return userEntityBuilder
      .authorities(AuthorityEntity.from(user.getAuthorities()))
      .email(user.getEmail().email())
      .lastName(user.getLastName().value())
      .firstName(user.getFirstName().firstName())
      .lastSeen(user.getLastSeen())
      .id(user.getDbId())
      .build();
  }

  public static User toDomain(UserEntity userEntity) {
    var userBuilder = User.builder();

    if (userEntity.getImageUrl() != null) {
      userBuilder.imageUrl(new UserImageUrl(userEntity.getImageUrl()));
    }

    if (userEntity.getAddressStreet() != null) {
      userBuilder.userAddress(UserAddress.builder()
        .city(userEntity.getAddressStreet())
        .street(userEntity.getAddressStreet())
        .zipCode(userEntity.getAddressZipCode())
        .country(userEntity.getAddressCountry())
        .build());
    }

    return userBuilder
      .email(new UserEmail(userEntity.getEmail()))
      .lastName(new UserLastName(userEntity.getLastName()))
      .firstName(new UserFirstName(userEntity.getFirstName()))
      .authorities(AuthorityEntity.toDomain(userEntity.getAuthorities()))
      .userPublicId(new UserPublicId(userEntity.getPublicId()))
      .lastModifiedDate(userEntity.getLastModifiedDate())
      .createdDate(userEntity.getCreatedDate())
      .lastSeen(userEntity.getLastSeen())
      .dbId(userEntity.getId())
      .build();
  }

  public static Set<UserEntity> from(List<User> users) {
    return users.stream().map(UserEntity::fromUser).collect(Collectors.toSet());
  }

  public static Set<User> toDomain(List<UserEntity> users) {
    return users.stream().map(UserEntity::toDomain).collect(Collectors.toSet());
  }
}
