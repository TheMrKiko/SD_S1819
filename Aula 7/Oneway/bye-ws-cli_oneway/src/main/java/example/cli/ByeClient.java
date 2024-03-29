package example.cli;

import java.util.Map;
import javax.xml.ws.*;
import static javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY;

import example.ws.*; // classes generated by wsimport from WSDL


public class ByeClient {

    public static void main(String[] args) {
        // Check arguments
        if (args.length < 1) {
            System.err.println("Argument(s) missing!");
            System.err.printf("Usage: java %s url%n", ByeClient.class.getName());
            return;
        }

        ByeImplService service = new ByeImplService();
        Bye port = service.getByeImplPort();

        BindingProvider bindingProvider = (BindingProvider) port;
        Map<String, Object> requestContext = bindingProvider.getRequestContext();

        // set endpoint address
        String url = args[0];
        requestContext.put(ENDPOINT_ADDRESS_PROPERTY, url);
        System.out.printf("Remote calls to %s ...%n", url);

        System.out.println();
        System.out.println("sayGoodbye() will be called 3 times");
        System.out.println("with changeFarewell() after the 1st call");
        System.out.println();

        // call say hello
        System.out.println("1st sayGoodbye()");
        String result = port.sayGoodbye("friend");
        System.out.println(result);
        // should wait for response and receive default greeting

        // set greeting (one-way operation)
        System.out.println("changeFarewell()");
        port.changeFarewell("So long");
        // should not wait for response

        // call say hello again
        System.out.println("2nd sayGoodbye()");
        String result2 = port.sayGoodbye("friend");
        System.out.println(result2);
        // should wait for response and still receive default greeting
        // because changeGreeting did not have time to complete

        // call say hello yet again
        System.out.println("3rd sayGoodbye()");
        String result3 = port.sayGoodbye("friend");
        System.out.println(result3);
        // should wait for response and receive new greeting
    }

}
