package pl.wat.michal.capala.praca_inz.backend.repositories;

import org.hyperledger.fabric.gateway.*;
import org.hyperledger.fabric.gateway.impl.TimePeriod;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric.sdk.security.CryptoSuiteFactory;
import org.hyperledger.fabric_ca.sdk.EnrollmentRequest;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.springframework.stereotype.Repository;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Repository
public class HyperledgerRepositoryImpl implements HyperledgerRepository {


    private final TimePeriod timeout = new TimePeriod(7, TimeUnit.DAYS);

    private static final String pemFile = "ca.org1.example.com-cert.pem";
    private static final Path networkConfigFile = Paths.get("connection.json");
    private static final String walletName = "wallet";
    private static final String caUrl = "https://localhost:7054";

    private static final String userName = "Org1 Admin";
    private static final String secret = "adminpw";

    private static Gateway gateway;

    public HyperledgerRepositoryImpl(){
        //enrollAdmin();

        gateway = connect();
    }

    @Override
    public void enrollAdmin() {

        Properties props = new Properties();

        props.put("pemFile",pemFile);
        props.put("allowAllHostNames", "true");

        try{
            HFCAClient caClient = HFCAClient.createNewInstance(caUrl, props);

            CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();

            caClient.setCryptoSuite(cryptoSuite);

            Wallet wallet = Wallets.newFileSystemWallet(Paths.get(walletName));

            if (wallet.get("Org1 Admin") != null){
                System.out.println("Tożsamość administratora 'Admin' już istnieje w portfelu");
                return;
            }

            final EnrollmentRequest enrollmentRequest = new EnrollmentRequest();

            enrollmentRequest.addHost("localhost");
            enrollmentRequest.setProfile("tls");

            Enrollment enrollment = caClient.enroll(userName,secret,enrollmentRequest);

            Identity user = Identities.newX509Identity("Org1MSP", enrollment);

            wallet.put("admin", user);

            System.out.println("Pomyślnie dodano tożsamość 'admin' do portfela");

        }
        catch (Exception e){
            System.err.println(e);
        }


    }

    @Override
    public Gateway connect() {
        Path walletPath = Paths.get(walletName);
        Gateway.Builder builder = Gateway.createBuilder().commitTimeout(timeout.getTime(), timeout.getTimeUnit());
        try {
            Wallet wallet = Wallets. newFileSystemWallet(walletPath);
            builder.identity(wallet, userName).networkConfig(networkConfigFile).discovery(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.connect();
    }

    public String evaluateChaincodeTransaction(String channel_name, String contract_name, String method, String ... args){
        byte[] result;

        try{
            Network network = gateway.getNetwork(channel_name);

            Contract contract = network.getContract(contract_name);

            result = contract.evaluateTransaction(method, args);
            return new String(result);
        } catch (ContractException e) {
            e.printStackTrace();
            return e.getLocalizedMessage();
        }
    }
    public String submitChaincodeTransaction(String channel_name, String contract_name, String method, String ... args){
        byte[] result;
        try{
            Network network = gateway.getNetwork(channel_name);

            Contract contract = network.getContract(contract_name);

            contract.submitTransaction(method,args);
            return new String("1");
        } catch (ContractException e){
            System.out.println(Arrays.toString(new Collection[]{e.getProposalResponses()}));
            System.out.println("toString(): " + e.toString());
            System.out.println("getMessage(): " + e.getMessage());
            return e.getLocalizedMessage();
        }
        catch (Exception e) {
            System.out.println("toString(): " + e.toString());
            System.out.println("getMessage(): " + e.getMessage());
            System.out.println("StackTrace: ");
            e.printStackTrace();
            return e.getLocalizedMessage();
        }
    }
}
