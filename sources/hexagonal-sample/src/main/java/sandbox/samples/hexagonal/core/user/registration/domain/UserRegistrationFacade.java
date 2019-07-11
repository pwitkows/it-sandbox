package sandbox.samples.hexagonal.core.user.registration.domain;

public class UserRegistrationFacade {

  private UserRegistration userRegistration;

  public UserRegistrationFacade(
      UserRegistrationDataProvider serviceProvider,
      ConfirmationMailSender confirmationMailSender,
      UserRegistrationNotifier userRegistrationNotifier) {
    this.userRegistration =
        new UserRegistration(serviceProvider, confirmationMailSender, userRegistrationNotifier);
  }

  public Long createUserAccount(UserRegistrationInput registrationInput) {
    return this.userRegistration.createUserAccount(registrationInput);
  }

  public void activateUserAccount(String activationCode) {
    this.userRegistration.activateUserAccount(activationCode);
  }
}
