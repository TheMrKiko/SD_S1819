package example.grpc.server;

/* helper to print binary in hexadecimal */
import static javax.xml.bind.DatatypeConverter.printHexBinary;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import com.google.protobuf.ByteString;
/* predefined types */
import com.google.type.Money;

/* these classes are generated from protobuf definitions */
import example.grpc.Product;
import example.grpc.ProductsRequest;
import example.grpc.ProductsResponse;
import example.grpc.SignedResponse;
import example.grpc.Signature;
import example.grpc.SupplierGrpc;

/* grpc library */
import io.grpc.stub.StreamObserver;

import java.security.InvalidKeyException;
import java.security.Key;

import java.io.InputStream;

public class SupplierServiceImpl extends SupplierGrpc.SupplierImplBase {

	public static Key readKey(String resourcePath) throws Exception {
		System.out.println("Reading key from resource " + resourcePath + " ...");

		InputStream fis = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath);
		byte[] encoded = new byte[fis.available()];
		fis.read(encoded);
		fis.close();

		System.out.println("Key:");
		System.out.println(printHexBinary(encoded));
		SecretKeySpec keySpec = new SecretKeySpec(encoded, "AES");

		return keySpec;
	}

	@Override
	public void listProducts(ProductsRequest request, StreamObserver<SignedResponse> responseObserver) {

		System.out.println("listProducts called");
		System.out.println("Received request:");
		System.out.println("in binary hexadecimals:");
		byte[] requestBinary = request.toByteArray();
		System.out.println(printHexBinary(requestBinary));
		System.out.printf("%d bytes%n", requestBinary.length);

		// build response
		ProductsResponse.Builder responseBuilder = ProductsResponse.newBuilder();
		SignedResponse.Builder signedResponseBuilder = SignedResponse.newBuilder();
		responseBuilder.setSupplierIdentifier("Tagus Sports Store");
		{
			Product.Builder productBuilder = Product.newBuilder();
			productBuilder.setIdentifier("A1");
			productBuilder.setDescription("Soccer ball");
			productBuilder.setQuantity(22);
			Money.Builder moneyBuilder = Money.newBuilder();
			moneyBuilder.setCurrencyCode("EUR").setUnits(10);
			productBuilder.setPrice(moneyBuilder.build());
			responseBuilder.addProduct(productBuilder.build());
		}
		{
			Product.Builder productBuilder = Product.newBuilder();
			productBuilder.setIdentifier("B2");
			productBuilder.setDescription("Basketball");
			productBuilder.setQuantity(100);
			Money.Builder moneyBuilder = Money.newBuilder();
			moneyBuilder.setCurrencyCode("EUR").setUnits(12);
			productBuilder.setPrice(moneyBuilder.build());
			responseBuilder.addProduct(productBuilder.build());
		}
		{
			Product.Builder productBuilder = Product.newBuilder();
			productBuilder.setIdentifier("C3");
			productBuilder.setDescription("Volley ball");
			productBuilder.setQuantity(7);
			Money.Builder moneyBuilder = Money.newBuilder();
			moneyBuilder.setCurrencyCode("EUR").setUnits(8);
			productBuilder.setPrice(moneyBuilder.build());
			responseBuilder.addProduct(productBuilder.build());
		}

		ProductsResponse response = responseBuilder.build();
		signedResponseBuilder.setResponse(response);

		Signature.Builder signatureBuilder = Signature.newBuilder();
		signatureBuilder.setSignerId("1");

		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			byte[] plainBytes = response.toByteArray();
			messageDigest.update(plainBytes);
			byte[] digest = messageDigest.digest();

			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, readKey("secret.key"));
			byte[] cipherBytes;
			cipherBytes = cipher.doFinal(digest);

			signatureBuilder.setValue(ByteString.copyFrom(cipherBytes));

			signedResponseBuilder.setSignature(signatureBuilder.build());

			SignedResponse signedResponse = signedResponseBuilder.build();

			System.out.println("Response to send:");
			System.out.println(response);
			System.out.println("in binary hexadecimals:");
			byte[] responseBinary = response.toByteArray();
			System.out.println(printHexBinary(responseBinary));
			System.out.printf("%d bytes%n", responseBinary.length);

			// send single response back
			responseObserver.onNext(signedResponse);
			// complete call
			responseObserver.onCompleted();

		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
