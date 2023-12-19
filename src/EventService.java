import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface EventService extends Remote {
    void createEvent(Event event) throws RemoteException;
    List<Event> getEvents() throws RemoteException;
    void updateEvent(Event event) throws RemoteException;
    void deleteEvent(int eventId) throws RemoteException;
}