package client;

import io.grpc.stub.StreamObserver;
import ru.otus.protobuf.generated.ServerMessage;

public class ClientObserver  implements StreamObserver<ServerMessage> {

    private long last = 0;

    @Override
    public void onNext(ServerMessage message) {
        long value = message.getNumber();
        System.out.println("new value: " + value);
        setLast(value);
    }

    @Override
    public void onError(Throwable t) {
        System.out.println("Error");
        t.printStackTrace();
    }

    @Override
    public void onCompleted() {
        System.out.println("Finished");
    }

    public void setLast(long value) {
        this.last = value;
    }

    public long getLast() {
        long value = this.last;
        this.last = 0;
        return value;
    }
}
