package pl.wat.michal.capala.praca_inz.backend.services;

import pl.wat.michal.capala.praca_inz.backend.dtos.user.UserData;

public interface UserService  {
    UserData getUser(String login);
    void registerUser(UserData userData);
}
