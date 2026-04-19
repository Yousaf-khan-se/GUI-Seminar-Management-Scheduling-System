package BusinessLogic.Model;

import java.io.*;
import java.util.ArrayList;

public interface IModel {
    public void readSeminarList(File file) throws FileNotFoundException, IOException;
    public void forceReadSeminarList(File file) throws FileNotFoundException, IOException;

    void readScheduleList(File scheduleFile) throws FileNotFoundException, IOException;
    public void forceReadScheduleList(File file) throws FileNotFoundException, IOException;

    public ArrayList<ConflictingSeminars> getConflictingSeminarNames();

    public ArrayList<ConflictingAttendees> getConflictingAttendeesID();


    public ArrayList<Schedule> getSchedules();

    public void setSchedules(ArrayList<Schedule> schedules);

    public ArrayList<Seminar> getSeminarList();

    public void addSeminar(Seminar seminar);
    public void forceAddSeminar(Seminar seminar);

    public void addSchedule(Schedule schedule);
    public void forceAddSchdeule(Schedule schedule);

    public boolean deleteSeminar(String seminarName);
    public boolean deleteSchedule(String seminarName);
}
