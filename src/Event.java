import java.io.Serializable;

public class Event implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String title;
    private String date;
    private String category;

    public Event(int id, String title, String date, String category) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.category = category;
    }

    // Геттери та сеттери

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}