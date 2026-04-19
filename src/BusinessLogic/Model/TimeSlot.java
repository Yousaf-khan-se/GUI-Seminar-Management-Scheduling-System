package BusinessLogic.Model;

import java.util.Objects;

public class TimeSlot {
    private static int counter = 1;
    public int value;
    private int id;

    public TimeSlot(int value)
    {
        this.value = value;
        this.id = counter++;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
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
        if (!(o instanceof TimeSlot timeSlot)) return false;
        return (value == timeSlot.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return "TimeSlot{" +
                "value=" + value +
                ", id=" + id +
                '}';
    }
}
