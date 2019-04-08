package example.ws;

import javax.jws.*;

@WebService
public interface Bye {

    @WebMethod String sayGoodbye(String name);

    @Oneway public void changeFarewell(String farewell);

}
