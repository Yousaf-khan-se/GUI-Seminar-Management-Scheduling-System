package DataAccessLayer.DAOClasses;

import BusinessLogic.Model.Attendee;
import BusinessLogic.Model.Seminar;
import BusinessLogic.Model.Speaker;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class SeminarsDAO extends DBDAO {

    public SeminarsDAO() {
        super("Seminars", 3, new String[]{"seminarName", "speakerName", "attendees"});
    }

    private String serializeAttendees(ArrayList<Integer> numbers)
    {
        String result = numbers.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
        result = " " + result + " ";


        return result;
    }

    public boolean addSeminar(Seminar seminar) {
        ArrayList<Object> data = new ArrayList<>();
        data.add(seminar.getName());
        data.add(seminar.getSpeaker().getName());
        data.add(serializeAttendees(seminar.getAttendeesIDs()));

        return insert(data);
    }

    public boolean updateSeminar(Seminar seminar) {
        ArrayList<Object> data = new ArrayList<>();
        data.add(seminar.getName());
        data.add(seminar.getSpeaker().getName());
        data.add(serializeAttendees(seminar.getAttendeesIDs()));
        return update(data, seminar.getName(), "seminarName");
    }

    public boolean deleteSeminar(String seminarName){
        return delete(seminarName, "seminarName");
    }

    public Seminar getSeminar(String seminarName)
    {
        ArrayList<Object> data = load(seminarName, "seminarName");

        if(data.isEmpty())
            return null;

        Seminar sem = new Seminar((String) data.get(0), new Speaker((String)data.get(1)));
        String ids[] = ((String)data.get(2)).split(",");
        for(String id : ids)
        {
            sem.addAttendee(new Attendee(Integer.parseInt(id.trim())));
        }

        return sem;
    }

    public ArrayList<Seminar> getSeminarList()
    {
        ArrayList<Seminar> seminars = new ArrayList<>();
        ArrayList<ArrayList<Object>> data = load();
        for(ArrayList<Object> d : data)
        {
            seminars.add(getSeminar((String) d.get(0)));
        }

        return seminars;
    }


}
