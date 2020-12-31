package pl.wat.michal.capala.praca_inz.backend.repositories;

import org.hyperledger.fabric.gateway.Gateway;

public interface HyperledgerRepository {


    void enrollAdmin();
    Gateway connect();
    String evaluateChaincodeTransaction(String channel_name, String contract_name, String method, String ... args);
    String submitChaincodeTransaction(String channel_name, String contract_name, String method, String ... args);

}
