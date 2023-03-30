package ma.enset.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import ma.enset.stubs.Bank;
import ma.enset.stubs.BankServiceGrpc;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
//Bi-Dirictional Stream
public class BankGrpcClient5 {
    public static void main(String[] args) throws IOException {
        ManagedChannel managedChannel= ManagedChannelBuilder.forAddress("localhost",5555)
                .usePlaintext()
                .build();
        BankServiceGrpc.BankServiceStub asyncStub=
                BankServiceGrpc.newStub(managedChannel);
        Bank.ConvertCurrencyRequest request= Bank.ConvertCurrencyRequest.newBuilder()
                .setCurrencyFrom("MAD")
                .setAmount(8963.05)
                .setCurrencyTo("DOLLAR")
                .build();

        StreamObserver<Bank.ConvertCurrencyRequest> performStream =
                asyncStub.fullCurrencyStream(new StreamObserver<Bank.ConvertCurrencyResponse>() {
            @Override
            public void onNext(Bank.ConvertCurrencyResponse convertCurrencyResponse) {
                System.out.println("*-/--/-*-*--**/*/*-**-");
                System.out.println(convertCurrencyResponse);
                System.out.println("*-/--/-*-*--**/*/*-**-");
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println(throwable.toString());
            }

            @Override
            public void onCompleted() {
                System.out.println(" Finiiiiiiiish....");
            }
        });
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            int cpt;
            @Override
            public void run() {
                Bank.ConvertCurrencyRequest currencyRequest= Bank.ConvertCurrencyRequest.newBuilder()
                        .setAmount(Math.random()*5000)
                        .build();
                performStream.onNext(currencyRequest);
                System.out.println("==============> counter = "+ cpt);
                ++cpt;
                if (cpt==20){
                    performStream.onCompleted();
                    timer.cancel();
                }
            }
        }, 1000, 1000);

        System.out.println("0........0........0........0........0.......0");
        System.in.read();
    }
}
