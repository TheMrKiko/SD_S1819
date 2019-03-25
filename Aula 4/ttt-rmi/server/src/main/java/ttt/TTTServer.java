package ttt;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class TTTServer {

	public static void main(String args[]) {
		int registryPort = 1099;
		System.out.println("Main OK");
		try {
			TTTService atttservice = new TTT();
			System.out.println("After create");

			Registry rmiregistry = LocateRegistry.createRegistry(registryPort);
			rmiregistry.rebind("TTTService", atttservice);

			System.out.println("TTTService server ready");

			System.out.println("Awaiting connections");
			System.out.println("Press enter to shutdown");
			System.in.read();
			System.exit(0);

		} catch (Exception e) {
			System.out.println("ShapeList server main " + e.getMessage());
		}
	}
}