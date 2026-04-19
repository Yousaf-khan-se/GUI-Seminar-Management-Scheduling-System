package BusinessLogic.Model;

import java.util.Objects;

public class Schedule {
    private Seminar seminar;
    private  Room room;
    private  TimeSlot timeSlot;

    public Schedule(Seminar seminar, Room room, TimeSlot timeSlot) {
        this.seminar = seminar;
        this.room = room;
        this.timeSlot = timeSlot;
    }

    public Schedule(Seminar seminar)
    {
        this.seminar = seminar;
        this.room = new Room("Not Set!");
        this.timeSlot = new TimeSlot(-1);
    }

    public Seminar getSeminar() {
        return seminar;
    }

    public void setSeminar(Seminar seminar) {
        this.seminar = seminar;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Schedule schedule)) return false;
        return Objects.equals(seminar, schedule.seminar) && Objects.equals(room, schedule.room) && Objects.equals(timeSlot, schedule.timeSlot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seminar, room, timeSlot);
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "seminar=" + seminar +
                ", room=" + room +
                ", timeSlot=" + timeSlot +
                '}';
    }
}
