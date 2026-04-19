package BusinessLogic.Model;

import java.util.Objects;

public class Speaker {
    private static int counter = 1;
    private String name;
    private int id;

    public Speaker(String name)
    {
        this.name = name;
        this.id = counter++;
    }

    private int getCounter() {
        return counter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        if (!(o instanceof Speaker speaker)) return false;
        return Objects.equals(name, speaker.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        return "Speaker{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
