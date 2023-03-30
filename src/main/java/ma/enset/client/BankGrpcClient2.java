package ma.enset.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import ma.enset.stubs.Bank;
import ma.enset.stubs.BankServiceGrpc;

import java.io.IOException;
//Server Stream Client
public class BankGrpcClient2 {
    public static void main(String[] args) {
        ManagedChannel managedChannel= ManagedChannelBuilder.forAddress("localhost",5555)
                .usePlaintext()
                .build();

        BankServiceGrpc.BankServiceStub serviceStub= BankServiceGrpc.newStub(managedChannel);
        Bank.ConvertCurrencyRequest request= Bank.ConvertCurrencyRequest.newBuilder()
                .setCurrencyFrom("MAD")
                .setAmount(8695.652173913043)
                .setCurrencyTo("DOLLAR")
                .build();
        serviceStub.convert(request, new StreamObserver<Bank.ConvertCurrencyResponse>() {
            //Getting Response
            @Override
            public void onNext(Bank.ConvertCurrencyResponse convertCurrencyResponse) {
                System.out.println("**********************");
                System.out.println(convertCurrencyResponse);
                System.out.println("**********************");
            }
            //in case of an error
            @Override
            public void onError(Throwable throwable) {
                System.out.println(throwable.getMessage());
            }
            //The end
            @Override
            public void onCompleted() {
                System.out.println("Finiiiiiiish.............");
            }
        });
        System.out.println("0........0........0........0........0.......0");

        try {
            System.in.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
