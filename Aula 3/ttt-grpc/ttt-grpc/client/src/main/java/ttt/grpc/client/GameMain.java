package ttt.grpc.client;

import ttt.grpc.TTTServiceGrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GameMain {

	public static void main(String[] args) {

		TTTServiceGrpc.TTTServiceBlockingStub stub;

		System.out.println(Game.class.getSimpleName());

		// receive and print arguments
		System.out.printf("Received %d arguments%n", args.length);
		for (int i = 0; i < args.length; i++) {
			System.out.printf("arg[%d] = %s%n", i, args[i]);
		}

		// check arguments
		if (args.length < 2) {
			System.err.println("Argument(s) missing!");
			System.err.printf("Usage: java %s host port%n", Game.class.getName());
			return;
		}

		final String host = args[0];
		final int port = Integer.parseInt(args[1]);
		final String target = host + ":" + port;

		// Channel is the abstraction to connect to a service endpoint
		// Let us use plaintext communication because we do not have certificates
		final ManagedChannel channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();

		// It is up to the client to determine whether to block the call
		// Here we create a blocking stub, but an async stub,
		// or an async stub with Future are always possible.
		stub = TTTServiceGrpc.newBlockingStub(channel);

		Game g = new Game(stub);
		g.playGame();
		g.congratulate();

		// A Channel should be shutdown before stopping the process.
		channel.shutdownNow();
	}

}
