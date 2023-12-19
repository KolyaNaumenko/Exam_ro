import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class ServerSocketTask2 {
    private static List<Event> events = new ArrayList<>();

    public static void main(String[] args) {
        int port = 12345;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected");

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ClientHandler implements Runnable {
        private Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try (
                    ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                    ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())
            ) {
                while (true) {
                    String operation = (String) in.readObject();

                    if (operation.equals("EXIT")) {
                        break;
                    }

                    switch (operation) {
                        case "CREATE":
                            Event event = (Event) in.readObject();
                            createEvent(event);
                            break;
                        case "READ":
                            out.writeObject(events);
                            break;
                        case "UPDATE":
                            Event updatedEvent = (Event) in.readObject();
                            updateEvent(updatedEvent);
                            break;
                        case "DELETE":
                            int eventIdToDelete = (int) in.readObject();
                            deleteEvent(eventIdToDelete);
                            break;
                        default:
                            break;
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        private synchronized void createEvent(Event event) {
            events.add(event);
        }

        private synchronized void updateEvent(Event updatedEvent) {
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

        private synchronized void deleteEvent(int eventIdToDelete) {
            events.removeIf(e -> e.getId() == eventIdToDelete);
        }
    }


    private void deleteEvent(int eventIdToDelete) {
            events.removeIf(e -> e.getId() == eventIdToDelete);
        }
    }
}