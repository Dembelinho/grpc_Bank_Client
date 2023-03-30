package ma.enset.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import ma.enset.stubs.Bank;
import ma.enset.stubs.BankServiceGrpc;
//unary client
public class BankGrpcClient1 {
    public static void main(String[] args) {
        //Establish connection with the server
        ManagedChannel managedChannel= ManagedChannelBuilder.forAddress("localhost",5555)
                .usePlaintext()
                .build();
        //Create stub between client and server
        BankServiceGrpc.BankServiceBlockingStub blockingStub=
                BankServiceGrpc.newBlockingStub(managedChannel);
        // Prepare/Build a request Msg
        Bank.ConvertCurrencyRequest request= Bank.ConvertCurrencyRequest.newBuilder()
                .setCurrencyFrom("MAD")
                .setAmount(896325)
                .setCurrencyTo("DOLLAR")
                .build();
        //Get & Print response
        Bank.ConvertCurrencyResponse currencyResponse = blockingStub.convert(request);
        System.out.println(currencyResponse);
    }
}
