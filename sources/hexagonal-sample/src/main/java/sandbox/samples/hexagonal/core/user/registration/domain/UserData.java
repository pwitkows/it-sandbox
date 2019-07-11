package sandbox.samples.hexagonal.core.user.registration.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserData {

  private Long id;
  private String login;
  private String activationCode;
  private boolean active;

}
