import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class ServerRmiTaskN {
    public static void main(String[] args) {
        try {
            int port = 1099;
            LocateRegistry.createRegistry(port);

            EventService eventService = new EventServiceImpl();
            Naming.rebind("//localhost/EventService", eventService);

            System.out.println("RMI Server is running.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}