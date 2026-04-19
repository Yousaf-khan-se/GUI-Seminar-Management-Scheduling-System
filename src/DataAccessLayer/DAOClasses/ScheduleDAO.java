package DataAccessLayer.DAOClasses;

import BusinessLogic.Model.*;

import java.util.ArrayList;

public class ScheduleDAO extends DBDAO {

    public ScheduleDAO() {
        super("Schedule", 3, new String[]{"SeminarName", "rooms", "timeSlot"});
    }

    public boolean addSchedule(Schedule schedule) {
        ArrayList<Object> data = new ArrayList<>();
        data.add(schedule.getSeminar().getName());
        data.add(schedule.getRoom().getName());
        data.add(schedule.getTimeSlot().getValue());
        return insert(data);
    }

    public boolean updateRoomOrTimeSlot(Schedule schedule) {
        ArrayList<Object> data = new ArrayList<>();
        data.add(schedule.getSeminar().getName());
        data.add(schedule.getRoom().getName());
        data.add(schedule.getTimeSlot().getValue());
        return update(data, schedule.getSeminar().getName(), "seminarName");
    }

    public boolean deleteSchedule(String seminarName){
        return delete(seminarName, "seminarName");
    }

    public Schedule getSchedule(String seminarName)
    {
        SeminarsDAO semDAO = new SeminarsDAO();
        Seminar sem = semDAO.getSeminar(seminarName);
        if(sem == null)
            return null;

        ArrayList<Object> data = load(seminarName, "seminarName");

        Schedule schedule = new Schedule(sem, new Room((String)data.get(1)), new TimeSlot((Integer) data.get(2)));

        return schedule;
    }

    public ArrayList<Schedule> getScheduleList()
    {
        ArrayList<Schedule> Schedule = new ArrayList<>();
        ArrayList<ArrayList<Object>> data = load();
        for(ArrayList<Object> d : data)
        {
            Schedule.add(getSchedule((String) d.get(0)));
        }
        return Schedule;
    }
}
