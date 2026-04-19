package BusinessLogic.Model;

import java.util.ArrayList;
import java.util.Objects;

public class Seminar {
    private static int counter = 1;
    private String name;
    private int id;
    private Speaker speaker;
    private ArrayList<Attendee> attendees;

    public Seminar(String name, Speaker speaker, ArrayList<Attendee> attendees)
    {
        this.name = name;
        this.id = counter++;
        this.speaker = speaker;
        this.attendees = attendees;
    }

    public Seminar(String name, Speaker speaker)
    {
        this.name = name;
        this.speaker = speaker;
        this.id = counter++;
        this.attendees = new ArrayList<>();
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

    public Speaker getSpeaker() {
        return speaker;
    }

    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
    }

    public ArrayList<Attendee> getAttendees() {
        return attendees;
    }

    public ArrayList<Integer> getAttendeesIDs() {
        ArrayList<Integer> attendeesIDS = new ArrayList<Integer>();

        for(Attendee attendee : attendees)
        {
            attendeesIDS.add(attendee.getId());
        }

        return attendeesIDS;
    }

    public void setAttendees(ArrayList<Attendee> attendees) {
        this.attendees = attendees;
    }

    public void addAttendee(Attendee attendee) throws RuntimeException{
        if(attendee != null)
        {
            if(attendees.contains(attendee))
            {
                throw new RuntimeException("Attendee with id: "+attendee.getId()+" already exists or you entered it twice!");
            }
            attendees.add(attendee);
        }
    }

    public void forceAddAttendee(Attendee attendee) throws RuntimeException{
        if(attendee != null)
        {
            if(!attendees.contains(attendee))
            {
                attendees.add(attendee);
            }
        }
    }

    public void removeAttendee(Attendee attendee) {
        attendees.remove(attendee);
    }

    public void removeAttendee(Integer attendeeID) {
        attendees.removeIf(n -> n.getId() == attendeeID);
    }

    public boolean isAttendeeExist(Attendee attendee)
    {
        return attendees.contains(attendee);
    }

    public Attendee getAttendeeByID(int attendeeID)
    {
        for(Attendee attendee: attendees)
        {
            if(attendee.getId() == attendeeID)
                return attendee;
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Seminar seminar)) return false;
        return Objects.equals(name, seminar.name) && Objects.equals(speaker, seminar.speaker) && Objects.equals(attendees, seminar.attendees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, speaker, attendees);
    }

    @Override
    public String toString() {
        return "Seminar{" +
                "speaker=" + speaker +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
