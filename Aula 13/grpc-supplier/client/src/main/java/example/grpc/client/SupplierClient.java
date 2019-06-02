package example.grpc.client;

/* helper to print binary in hexadecimal */
import static javax.xml.bind.DatatypeConverter.printHexBinary;

/* classes are generated from protobuf definitions */
import example.grpc.ProductsRequest;
import example.grpc.ProductsResponse;
import example.grpc.SupplierGrpc;

/* grpc library */
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;


public class SupplierClient {

	public static void main(String[] args) throws Exception {
		System.out.println(SupplierClient.class.getSimpleName() + " starting ...");

		// receive and print arguments
		System.out.printf("Received %d arguments%n", args.length);
		for (int i = 0; i < args.length; i++) {
			System.out.printf("arg[%d] = %s%n", i, args[i]);
		}

		// check arguments
		if (args.length < 2) {
			System.err.println("Argument(s) missing!");
			System.err.printf("Usage: java %s host port%n", SupplierClient.class.getName());
			return;
		}

		final String host = args[0];
		final int port = Integer.parseInt(args[1]);
		final String target = host + ":" + port;

		// Channel is the abstraction to connect to a service endpoint
		final ManagedChannel channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();

		// create a blocking stub for making synchronous remote calls
		SupplierGrpc.SupplierBlockingStub stub = SupplierGrpc.newBlockingStub(channel);

		// prepare request
		ProductsRequest request = ProductsRequest.newBuilder().build();
		System.out.println("Request to send:");
		System.out.println(request);
		System.out.println("in binary hexadecimals:");
		byte[] requestBinary = request.toByteArray();
		System.out.println(printHexBinary(requestBinary));
		System.out.printf("%d bytes%n", requestBinary.length);

		// make the call using the stub
		System.out.printf("%nRemote call...%n%n");
		ProductsResponse response = stub.listProducts(request);

		// print response
		System.out.println("Received response:");
		System.out.println(response);
		System.out.println("in binary hexadecimals:");
		byte[] responseBinary = response.toByteArray();
		System.out.println(printHexBinary(responseBinary));
		System.out.printf("%d bytes%n", responseBinary.length);

		// A Channel should be shutdown before stopping the process.
		channel.shutdownNow();
	}

}
