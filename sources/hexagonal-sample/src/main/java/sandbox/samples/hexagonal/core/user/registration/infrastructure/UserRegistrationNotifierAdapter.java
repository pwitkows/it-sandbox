package sandbox.samples.hexagonal.core.user.registration.infrastructure;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import sandbox.samples.hexagonal.core.user.registration.domain.UserData;
import sandbox.samples.hexagonal.core.user.registration.domain.UserRegistrationNotifier;
import sandbox.samples.hexagonal.external.KafkaMessageSender;

@Component
@AllArgsConstructor
class UserRegistrationNotifierAdapter implements UserRegistrationNotifier {

  private KafkaMessageSender messageSender;

  @Override
  public void sendMessage(UserData userData) {
    messageSender.send("REGISTERED_USERS", userData.toString());
  }
}
