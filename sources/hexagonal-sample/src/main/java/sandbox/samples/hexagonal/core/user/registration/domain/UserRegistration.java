package sandbox.samples.hexagonal.core.user.registration.domain;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

@AllArgsConstructor
class UserRegistration {

  private UserRegistrationDataProvider serviceProvider;
  private ConfirmationMailSender confirmationMailSender;
  private UserRegistrationNotifier userRegistrationNotifier;

  Long createUserAccount(UserRegistrationInput registrationInput) {
    validate(registrationInput);
    String confirmationCode = RandomStringUtils.randomAlphanumeric(20);
    Long userId = serviceProvider.createUser(registrationInput, confirmationCode);

    ConfirmationMailData confirmationMailData =  ConfirmationMailData.builder()
        .confirmationCode(confirmationCode)
        .mailTo(registrationInput.getEmail())
        .login(registrationInput.getLogin()).build();
    confirmationMailSender.send(confirmationMailData);
    return userId;
  }

  void activateUserAccount(String activationCode) {
    UserData userData = serviceProvider.findUserByActivationCode(activationCode)
        .orElseThrow(() ->  new RuntimeException("Couldn't confirm your account."));
    serviceProvider.activateAccount(userData.getLogin());
    userRegistrationNotifier.sendMessage(userData);
  }

  private void validate(UserRegistrationInput registrationInput) {
    if(StringUtils.isBlank(registrationInput.getEmail())) {
      throw new IllegalArgumentException("Email is required.");
    }
  }

}
