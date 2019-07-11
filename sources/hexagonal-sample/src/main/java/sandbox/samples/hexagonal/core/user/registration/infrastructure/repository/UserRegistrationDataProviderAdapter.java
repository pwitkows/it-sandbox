package sandbox.samples.hexagonal.core.user.registration.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sandbox.samples.hexagonal.core.user.registration.domain.UserData;
import sandbox.samples.hexagonal.core.user.registration.domain.UserRegistrationDataProvider;
import sandbox.samples.hexagonal.core.user.registration.domain.UserRegistrationInput;

@Service
@AllArgsConstructor
public class UserRegistrationDataProviderAdapter implements UserRegistrationDataProvider {

  private UserRegistrationRepository userRegistrationRepository;

  @Override
  public Optional<UserData> findUserByActivationCode(String activationCode) {
    Optional<UserModel> userModel = userRegistrationRepository.findByActivationCode(activationCode);
    return userModel.map(this::toUserData);
  }

  @Override
  public Long createUser(UserRegistrationInput registrationInput, String confirmationCode) {
    UserModel userModel = new UserModel();
    userModel.setEmail(registrationInput.getEmail());
    userModel.setLogin(registrationInput.getLogin());
    userModel.setActivationCode(confirmationCode);
    userModel.setPassword(registrationInput.getPassword());
    userRegistrationRepository.save(userModel);
    return userModel.getId();
  }

  @Override
  public void activateAccount(String login) {}

  public List<UserData> findAll(){
    Iterable<UserModel> users = userRegistrationRepository.findAll();
    return StreamSupport
        .stream(users.spliterator(), false)
        .map(this::toUserData)
        .collect(Collectors.toList());
  }

  private UserData toUserData(UserModel userModel) {
    return UserData.builder()
        .id(userModel.getId())
        .activationCode(userModel.getActivationCode())
        .login(userModel.getLogin())
        .active(userModel.isActive()).build();
  }
}
