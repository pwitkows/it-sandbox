package sandbox.samples.hexagonal.core.user.registration.domain;

import java.util.Optional;

public interface UserRegistrationDataProvider {

  Optional<UserData> findUserByActivationCode(String confirmationCode);

  Long createUser(UserRegistrationInput registrationInput, String confirmationCode);

  void activateAccount(String login);

}
