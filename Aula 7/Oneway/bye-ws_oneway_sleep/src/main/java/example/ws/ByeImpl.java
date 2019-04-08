package example.ws;

import javax.jws.*;

@WebService(endpointInterface="example.ws.Bye")
public class ByeImpl implements Bye {

    public String farewell = "Bye";

    public String sayGoodbye(String name) {
        // print current thread and object instance executing the operation
        System.out.printf("%s %s>%n    ", Thread.currentThread(), this);
        System.out.printf("sayGoodbye(%s)%n", name);

        // sleep
        nap(5);
        // execute operation
        String result = farewell + " " + name + "!";

        System.out.printf("%s %s>%n    ", Thread.currentThread(), this);
        System.out.printf("result=%s%n", result);
        return result;
    }

    public void changeFarewell(String newFarewell) {
        System.out.printf("%s %s>%n    ", Thread.currentThread(), this);
        System.out.printf("changeFarewell(%s)%n", newFarewell);

        // sleep
        nap(6);
        // execute
        this.farewell = newFarewell;
    }

    private void nap(int seconds) {
        try {
            System.out.printf("%s %s>%n    ", Thread.currentThread(), this);
            System.out.printf("Sleeping for %d seconds...%n", seconds);

            Thread.sleep(seconds*1000);

            System.out.printf("%s %s>%n    ", Thread.currentThread(), this);
            System.out.printf("Woke up!%n");

        } catch(InterruptedException e) {
            System.out.printf("%s %s>%n    ", Thread.currentThread(), this);
            System.out.printf("Caught exception: %s%n", e);
        }
    }

}
