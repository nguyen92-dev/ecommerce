package top.nguyennd.ecom.order.infrastructure.secondary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import top.nguyennd.ecom.order.infrastructure.secondary.entity.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {

  Optional<UserEntity> findByEmail(String email);

  List<UserEntity> findByPublicIdIn(List<UUID> publicIds);

  Optional<UserEntity> findOneByPublicId(UUID publicId);

  @Modifying
  @Query("""
    UPDATE UserEntity user
    SET user.addressStreet = :street, user.addressCity = :city,
        user.addressZipCode = :zipCode, user.addressCountry = :country
    WHERE user.publicId = :publicId
    """)
  void updateAddress(UUID publicId, String street, String city, String country, String zipCode);
}
