package BusinessLogic.Model;

import java.util.Objects;

public class Attendee {
    private int id;

    public Attendee(int id)
    {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Attendee attendee)) return false;
        return (this.id == attendee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Attendee{" +
                "id=" + id +
                '}';
    }
}
