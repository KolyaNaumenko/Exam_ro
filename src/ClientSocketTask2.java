import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Scanner;

public class ClientSocketTask2 {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 12345;

        try (
                Socket socket = new Socket(host, port);
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                Scanner scanner = new Scanner(System.in)
        ) {
            while (true) {
                System.out.println("Choose operation (CREATE, READ, UPDATE, DELETE, EXIT):");
                String operation = scanner.nextLine();

                if (operation.equals("EXIT")) {
                    out.writeObject("EXIT");
                    break;
                }

                switch (operation) {
                    case "CREATE":
                        Event newEvent = readEventFromUserInput();
                        out.writeObject(operation);
                        out.writeObject(newEvent);
                        break;
                    case "READ":
                        out.writeObject(operation);
                        List<Event> events = (List<Event>) in.readObject();
                        displayEvents(events);
                        break;
                    case "UPDATE":
                        Event updatedEvent = readEventFromUserInput();
                        out.writeObject(operation);
                        out.writeObject(updatedEvent);
                        break;
                    case "DELETE":
                        System.out.println("Enter Event ID to delete:");
                        int eventIdToDelete = scanner.nextInt();
                        out.writeObject(operation);
                        out.writeObject(eventIdToDelete);
                        break;
                    default:
                        System.out.println("Invalid operation. Try again.");
                        break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
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