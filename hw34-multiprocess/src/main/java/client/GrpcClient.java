package client;

import io.grpc.ManagedChannelBuilder;
import ru.otus.protobuf.generated.ClientMessage;
import ru.otus.protobuf.generated.ServiceGrpc;

public class GrpcClient {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8190;
    private static final int LIMIT = 50;
    private static long value = 0;

    public static void main(String[] args) {
        var channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();
        var client = ServiceGrpc.newStub(channel);

        doInteraction(client);

        channel.shutdown();
    }

    private static void doInteraction(ServiceGrpc.ServiceStub client) {
        ClientObserver clientObserver = new ClientObserver();
        client.generateNumber(buildClientMessage(), clientObserver);

        for (int i = 0; i < LIMIT; i++) {
            getAndBuildValue(clientObserver);
            System.out.println("currentValue: " + value);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private static void getAndBuildValue(ClientObserver clientObserver) {
        value = value + clientObserver.getLast() + 1;
    }

    private static ClientMessage buildClientMessage() {
        return ClientMessage.newBuilder().setFirstNumber(1).setLastNumber(30).build();
    }
}
