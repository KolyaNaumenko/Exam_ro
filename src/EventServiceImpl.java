import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class EventServiceImpl extends UnicastRemoteObject implements EventService {
    private List<Event> events = new ArrayList<>();

    public EventServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public synchronized void createEvent(Event event) throws RemoteException {
        events.add(event);
    }

    @Override
    public synchronized List<Event> getEvents() throws RemoteException {
        return new ArrayList<>(events);
    }

    @Override
    public synchronized void updateEvent(Event updatedEvent) throws RemoteException {
        int eventId = updatedEvent.getId();
        for (Event existingEvent : events) {
            if (existingEvent.getId() == eventId) {
                existingEvent.setTitle(updatedEvent.getTitle());
                existingEvent.setDate(updatedEvent.getDate());
                existingEvent.setCategory(updatedEvent.getCategory());
                break;
            }
        }
    }

    @Override
    public synchronized void deleteEvent(int eventIdToDelete) throws RemoteException {
        events.removeIf(e -> e.getId() == eventIdToDelete);
    }
}