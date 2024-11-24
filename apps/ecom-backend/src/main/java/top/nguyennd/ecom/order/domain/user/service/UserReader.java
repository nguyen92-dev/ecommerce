package top.nguyennd.ecom.order.domain.user.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import top.nguyennd.ecom.order.domain.user.aggregate.User;
import top.nguyennd.ecom.order.domain.user.repository.UserRepository;
import top.nguyennd.ecom.order.domain.user.vo.UserEmail;
import top.nguyennd.ecom.order.domain.user.vo.UserPublicId;

import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserReader {

  private final UserRepository userRepository;

  public UserReader(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public Optional<User> getByEmail(UserEmail userEmail) {
    return userRepository.getOneByEmail(userEmail);
  }

  public Optional<User> getByPublicId(UserPublicId userPublicId) {
    return userRepository.get(userPublicId);
  }
}
