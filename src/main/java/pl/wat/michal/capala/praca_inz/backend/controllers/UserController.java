package pl.wat.michal.capala.praca_inz.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.wat.michal.capala.praca_inz.backend.dtos.authentication.AuthenticationRequest;
import pl.wat.michal.capala.praca_inz.backend.dtos.authentication.AuthenticationResponse;
import pl.wat.michal.capala.praca_inz.backend.dtos.user.UserData;
import pl.wat.michal.capala.praca_inz.backend.dtos.user.UserDetailsResponse;
import pl.wat.michal.capala.praca_inz.backend.services.UserDetailsServiceImpl;
import pl.wat.michal.capala.praca_inz.backend.services.UserService;
import pl.wat.michal.capala.praca_inz.backend.util.JwtUtil;

@RestController
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }
    @PostMapping("/api/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
        );
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @GetMapping("/api/user/userData/{login}")
    public ResponseEntity<UserDetailsResponse> getUserDAta(@PathVariable String login) {
        final UserData userDetails = userService.getUser(login);
        UserDetailsResponse userDetailsResponse = new UserDetailsResponse(userDetails.getLogin(),userDetails.getName(),userDetails.getSurname(),userDetails.getGender(), userDetails.getAttrs());

        return ResponseEntity.ok(userDetailsResponse);

    }

    @PostMapping("/api/user/registerUser")
    public ResponseEntity registerUser(@RequestBody UserData userData){
        this.userService.registerUser(userData);

        return new ResponseEntity(HttpStatus.OK);
    }


}
