package pl.wat.michal.capala.praca_inz.backend.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.wat.michal.capala.praca_inz.backend.dtos.user.UserData;
import pl.wat.michal.capala.praca_inz.backend.repositories.HyperledgerRepository;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final HyperledgerRepository hyperledgerRepository;
    private final String usersContractName = "Users";

    @Autowired
    public UserDetailsServiceImpl(HyperledgerRepository hyperledgerRepository) {
        this.hyperledgerRepository = hyperledgerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            UserData userData = objectMapper.readValue(hyperledgerRepository.evaluateChaincodeTransaction("mychannel",
                    usersContractName,
                    "ReadUser",
                    login),UserData.class);
            Set<GrantedAuthority> grantedAuthorites = new HashSet<>();
            grantedAuthorites.add(new SimpleGrantedAuthority(userData.getRole()));
            return new User(userData.getLogin(), userData.getPassword(), grantedAuthorites);
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return null;
    }
}
