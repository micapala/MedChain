package pl.wat.michal.capala.praca_inz.backend.dtos.user;

import lombok.Data;

@Data
public class UserData {
    String login;
    String password;
    String role;
    String name;
    String surname;
    String gender;
    String[] attrs;
}
