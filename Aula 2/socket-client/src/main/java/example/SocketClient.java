package example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Date;


public class SocketClient {

    public static void main( String[] args ) throws IOException {
        // Check arguments
        if (args.length < 3) {
            System.err.println("Argument(s) missing!");
            System.err.printf("Usage: java %s host port file%n", SocketClient.class.getName());
            return;
        }

        String host = args[0];
        // Convert port from String to int
        int port = Integer.parseInt(args[1]);
        // Concatenate arguments using a string builder
        StringBuilder sb = new StringBuilder();

        String sd = "SD2019\n";
        sb.append(sd);
        sb.append(args[2]+"\n");

        Date a = new Date();
        sb.append(a.toString());
        sb.append("\n");
        String text = sb.toString();

        // Create client socket
        Socket socket = new Socket(host, port);
        System.out.printf("Connected to server %s on port %d %n", host, port);

        // Create stream to send data to server
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        // Send text to server as bytes
        out.writeBytes(text);
        out.writeBytes("\n");
        System.out.println("Sent text: " + text);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String response2;
        while (true) {
            //Reads a line of text.
            //A line ends with a line feed ('\n').
            response2 = in.readLine();
            if(response2.isEmpty()) {
                break;
            }
            System.out.printf("Received message with content: '%s'%n", response2);
        }


        // Close client socket
        socket.close();
        System.out.println("Connection closed");
    }
}
