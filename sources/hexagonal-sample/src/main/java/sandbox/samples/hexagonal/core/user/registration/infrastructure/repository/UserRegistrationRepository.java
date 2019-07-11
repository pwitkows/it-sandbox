package sandbox.samples.hexagonal.core.user.registration.infrastructure.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface UserRegistrationRepository extends CrudRepository<UserModel, Long> {

  Optional<UserModel> findByActivationCode(String activationCode);
}
