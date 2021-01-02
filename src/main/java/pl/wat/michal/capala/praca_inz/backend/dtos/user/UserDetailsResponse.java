package pl.wat.michal.capala.praca_inz.backend.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDetailsResponse {
    String login;
    String name;
    String surname;
    String gender;
    String[] attrs;
}
