package server;

import io.grpc.stub.StreamObserver;
import ru.otus.protobuf.generated.ClientMessage;
import ru.otus.protobuf.generated.ServerMessage;
import ru.otus.protobuf.generated.ServiceGrpc;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class ServiceImpl extends ServiceGrpc.ServiceImplBase {

    @Override
    public void generateNumber(ClientMessage request, StreamObserver<ServerMessage> responseObserver) {
        var currentValue = new AtomicLong(request.getFirstNumber());

        var executor = Executors.newScheduledThreadPool(1);
        Runnable task = () -> {
            var value = currentValue.incrementAndGet();
            var response = ServerMessage
                    .newBuilder()
                    .setNumber(value)
                    .build();
            responseObserver.onNext(response);
            if (value == request.getLastNumber()) {
                executor.shutdown();
                responseObserver.onCompleted();
            }
        };

        executor.scheduleAtFixedRate(task, 0, 2, TimeUnit.SECONDS);
    }
}
