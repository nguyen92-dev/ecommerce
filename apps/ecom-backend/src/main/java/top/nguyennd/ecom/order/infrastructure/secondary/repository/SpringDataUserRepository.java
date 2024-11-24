package top.nguyennd.ecom.order.infrastructure.secondary.repository;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;
import top.nguyennd.ecom.order.domain.user.aggregate.User;
import top.nguyennd.ecom.order.domain.user.repository.UserRepository;
import top.nguyennd.ecom.order.domain.user.vo.UserAddressToUpdate;
import top.nguyennd.ecom.order.domain.user.vo.UserEmail;
import top.nguyennd.ecom.order.domain.user.vo.UserPublicId;
import top.nguyennd.ecom.order.infrastructure.secondary.entity.UserEntity;

import java.util.Optional;

import static java.util.Objects.nonNull;

@Repository
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpringDataUserRepository implements UserRepository {

  JpaUserRepository jpaUserRepository;

  public SpringDataUserRepository(JpaUserRepository jpaUserRepository) {
    this.jpaUserRepository = jpaUserRepository;
  }

  @Override
  public void save(User user) {
    if (nonNull(user.getDbId())) {
      var userToUpdateOpt = jpaUserRepository.findById(user.getDbId());
      if (userToUpdateOpt.isPresent()) {
        var userToUpdate = userToUpdateOpt.get();
        userToUpdate.updateFromUser(user);
        jpaUserRepository.save(userToUpdate);
      }
    } else {
      jpaUserRepository.save(UserEntity.fromUser(user));
    }
  }

  @Override
  public Optional<User> get(UserPublicId userPublicId) {
    return jpaUserRepository.findOneByPublicId(userPublicId.value()).map(UserEntity::toDomain);
  }

  @Override
  public Optional<User> getOneByEmail(UserEmail email) {
    return jpaUserRepository.findByEmail(email.email()).map(UserEntity::toDomain);
  }

  @Override
  public void updateAddress(UserPublicId userPublicId, UserAddressToUpdate address) {
    jpaUserRepository.updateAddress(userPublicId.value(), address.userAddress().street(),
      address.userAddress().city(), address.userAddress().country(), address.userAddress().zipCode());
  }
}
