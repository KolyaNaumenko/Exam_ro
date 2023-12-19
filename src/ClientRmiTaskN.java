import java.rmi.Naming;
import java.util.List;
import java.util.Scanner;

public class ClientRmiTaskN {
    public static void main(String[] args) {
        try {
            String url = "//localhost/EventService";
            EventService eventService = (EventService) Naming.lookup(url);

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Choose operation (CREATE, READ, UPDATE, DELETE, EXIT):");
                String operation = scanner.nextLine();

                if (operation.equals("EXIT")) {
                    break;
                }

                switch (operation) {
                    case "CREATE":
                        Event newEvent = readEventFromUserInput();
                        eventService.createEvent(newEvent);
                        break;
                    case "READ":
                        List<Event> events = eventService.getEvents();
                        displayEvents(events);
                        break;
                    case "UPDATE":
                        Event updatedEvent = readEventFromUserInput();
                        eventService.updateEvent(updatedEvent);
                        break;
                    case "DELETE":
                        System.out.println("Enter Event ID to delete:");
                        int eventIdToDelete = scanner.nextInt();
                        eventService.deleteEvent(eventIdToDelete);
                        break;
                    default:
                        System.out.println("Invalid operation. Try again.");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Event readEventFromUserInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Event ID:");
        int id = scanner.nextInt();
        System.out.println("Enter Event Title:");
        String title = scanner.next();
        System.out.println("Enter Event Date:");
        String date = scanner.next();
        System.out.println("Enter Event Category:");
        String category = scanner.next();

        return new Event(id, title, date, category);
    }

    private static void displayEvents(List<Event> events) {
        System.out.println("Events:");
        for (Event event : events) {
            System.out.println(event);
        }
    }
}