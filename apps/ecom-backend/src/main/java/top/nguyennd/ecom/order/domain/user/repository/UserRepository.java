package top.nguyennd.ecom.order.domain.user.repository;

import top.nguyennd.ecom.order.domain.user.aggregate.User;
import top.nguyennd.ecom.order.domain.user.vo.UserAddressToUpdate;
import top.nguyennd.ecom.order.domain.user.vo.UserEmail;
import top.nguyennd.ecom.order.domain.user.vo.UserPublicId;

import java.util.Optional;

public interface UserRepository {
  void save(User user);

  Optional<User> get(UserPublicId userPublicId);

  Optional<User> getOneByEmail(UserEmail email);

  void updateAddress(UserPublicId userPublicId, UserAddressToUpdate address);
}
