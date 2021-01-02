package pl.wat.michal.capala.praca_inz.backend.dtos.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor

public class AuthenticationResponse {

    private final String jwt;
}
