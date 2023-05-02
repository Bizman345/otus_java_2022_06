package server;

import io.grpc.ServerBuilder;

import java.io.IOException;

public class GrpcServer {
    private static final int SERVER_PORT = 8190;

    public static void main(String[] args) throws IOException, InterruptedException {
        var server = ServerBuilder
                .forPort(SERVER_PORT)
                .addService(new ServiceImpl())
                .build();

        server.start();
        System.out.println("Server started");
        server.awaitTermination();
    }
}
