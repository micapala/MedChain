package pl.wat.michal.capala.praca_inz.backend.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wat.michal.capala.praca_inz.backend.dtos.user.UserData;
import pl.wat.michal.capala.praca_inz.backend.repositories.HyperledgerRepository;

@Service
public class UserServiceImpl implements UserService {

    private final HyperledgerRepository hyperledgerRepository;
    private final String usersContractName = "Users";

    @Autowired
    public UserServiceImpl(HyperledgerRepository hyperledgerRepository) {
        this.hyperledgerRepository = hyperledgerRepository;
    }


    public UserData getUser(String login) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            UserData userData = objectMapper.readValue(hyperledgerRepository.evaluateChaincodeTransaction("mychannel",
                    usersContractName,
                    "ReadUser",
                    login),UserData.class);
            return userData;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }


    public void registerUser(UserData userData) {
        this.hyperledgerRepository.submitChaincodeTransaction("mychannel",usersContractName,"CreateUser",
                userData.getLogin(),userData.getPassword(),userData.getRole(),userData.getName(),userData.getSurname(),userData.getGender(),"[]");

    }


}
