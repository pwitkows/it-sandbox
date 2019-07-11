package sandbox.samples.hexagonal.core.user.registration.infrastructure.entrypoint;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sandbox.samples.hexagonal.core.user.registration.domain.UserData;
import sandbox.samples.hexagonal.core.user.registration.domain.UserRegistrationFacade;
import sandbox.samples.hexagonal.core.user.registration.domain.UserRegistrationInput;
import sandbox.samples.hexagonal.core.user.registration.infrastructure.repository.UserRegistrationDataProviderAdapter;

@RestController
@RequestMapping("/users/")
@AllArgsConstructor
public class UserRegistrationController {

  private UserRegistrationFacade userRegistrationFacade;
  private UserRegistrationDataProviderAdapter userRegistrationDataProviderAdapter;

  @RequestMapping(path = "register", method = POST)
  public ResponseEntity<UserRegistrationResponseDto> register(
      @RequestBody UserRegistrationRequestDto userRegistrationDto) {
    UserRegistrationInput input = toUserRegistrationInput(userRegistrationDto);
    Long userId = userRegistrationFacade.createUserAccount(input);
    return new ResponseEntity<>(new UserRegistrationResponseDto(userId), HttpStatus.CREATED);
  }

  @RequestMapping(path = "activate/{activationCode}", method = PUT)
  public ResponseEntity<Void> activateAccount(
      @PathVariable("activationCode") String activationCode) {
    userRegistrationFacade.activateUserAccount(activationCode);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @RequestMapping(path = "list", method = GET)
  public ResponseEntity<List<UserData>> listUsers() {
    return new ResponseEntity<>(userRegistrationDataProviderAdapter.findAll(), HttpStatus.CREATED);
  }

  private UserRegistrationInput toUserRegistrationInput(
      UserRegistrationRequestDto userRegistrationDto) {
    return UserRegistrationInput.builder()
        .email(userRegistrationDto.getEmail())
        .login(userRegistrationDto.getLogin())
        .password(userRegistrationDto.getPassword())
        .build();
  }
}
