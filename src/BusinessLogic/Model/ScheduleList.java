package BusinessLogic.Model;

import DataAccessLayer.DAOClasses.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ScheduleList implements IModel {
    private ArrayList<Schedule> schedules;

    private ScheduleDAO scheduleDAO;
    private SeminarsDAO seminarsDAO;

    public ScheduleList() {
        this.scheduleDAO = new ScheduleDAO();
        this.seminarsDAO = new SeminarsDAO();
        this.schedules = scheduleDAO.getScheduleList();
    }

    @Override
    public void readSeminarList(File file) throws FileNotFoundException, IOException {
        int initialSize = this.schedules.size();
        FileReader reader = new FileReader(file);
        try (BufferedReader br = new BufferedReader(reader)) {
            String line;
            while ((line = br.readLine()) != null) {
                if(line.trim().isEmpty())
                {
                    continue;
                }
                String []columns = line.split(",");
                if(columns.length < 1)
                {
                    throw  new IOException("Incomplete Data! Speaker missing for the Seminar : " + columns[0] + " " +
                            "Inside the file: " + file.getAbsolutePath());
                }
                Seminar temp = new Seminar(columns[0], new Speaker(columns[1]));
                for(int i = 2; i < columns.length; i++)
                {
                    int x = -1;
                    try {
                        if(!columns[i].trim().isEmpty())
                            x = Integer.parseInt(columns[i].trim());
                    } catch (NumberFormatException e) {
                        reader.close();
                        br.close();
                        throw new NumberFormatException("Unable to parse "+ columns[i] + " " +
                                "as integer Attendee ID of the seminar " + temp.getName() + " " +
                                "in file: " + file.getAbsolutePath());
                    }

                    try {
                        if(x != -1)
                            temp.addAttendee(new Attendee(x));
                    } catch (RuntimeException e) {
                        reader.close();
                        br.close();
                        throw new RuntimeException("Inside file: "+file.getAbsolutePath()+" " +
                                "For Seminar: "+temp.getName()+" "+e.getMessage());
                    }
                }

                try{
                    addSeminar(temp);
                } catch (Exception e){
                    reader.close();
                    br.close();
                    throw new RuntimeException("Inside file: "+file.getAbsolutePath()+" " +
                            "For Seminar: "+temp.getName()+" "+e.getMessage());
                }
            }
            reader.close();
            br.close();
            if(this.schedules.size() == initialSize)
            {
                throw new IOException("Empty file: " + file.getAbsolutePath());
            }
        }catch (FileNotFoundException e) {
            throw new FileNotFoundException("File not found: " + file.getAbsolutePath());
        } catch (IOException e) {
            throw new IOException("Error reading file: " + file.getAbsolutePath(), e);
        }
    }

    @Override
    public void forceReadSeminarList(File file) throws FileNotFoundException, IOException {
        int initialSize = schedules.size();
        FileReader reader = new FileReader(file);
        try (BufferedReader br = new BufferedReader(reader)) {
            String line;
            while ((line = br.readLine()) != null) {
                if(line.trim().isEmpty())
                {
                    continue;
                }
                String []columns = line.split(",");
                if(columns.length < 1)
                {
                    reader.close();
                    br.close();
                    throw  new IOException("Incomplete Data! Speaker missing for the Seminar : " + columns[0] + " " +
                            "Inside the file: " + file.getAbsolutePath());
                }
                Seminar temp = new Seminar(columns[0], new Speaker(columns[1]));
                for(int i = 2; i < columns.length; i++)
                {
                    int x = -1;
                    try {
                        if(!columns[i].trim().isEmpty())
                            x = Integer.parseInt(columns[i].trim());
                    } catch (NumberFormatException e) {
                        reader.close();
                        br.close();
                        throw new NumberFormatException("Unable to parse "+ columns[i] + " " +
                                "as integer Attendee ID of the seminar " + temp.getName() + " " +
                                "in file: " + file.getAbsolutePath());
                    }
                    if(x != -1)
                        temp.forceAddAttendee(new Attendee(x));

                }

                if(!getSeminarList().contains(temp))
                {
                    forceAddSeminar(temp);
                }
            }
            reader.close();
            br.close();
            if(schedules.size() == initialSize)
            {
                reader.close();
                br.close();
                throw new IOException("Empty file: " + file.getAbsolutePath());
            }
        }catch (FileNotFoundException e) {
            throw new FileNotFoundException("File not found: " + file.getAbsolutePath());
        } catch (IOException e) {
            throw new IOException("Error while reading the file: " + file.getAbsolutePath(), e);
        }

    }

    @Override
    public void forceReadScheduleList(File file) throws FileNotFoundException, IOException {
        boolean isFileEmpty = true;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if(line.trim().isEmpty())
                {
                    continue;
                }
                String []columns = line.split(",");
                if(columns.length < 1)
                {
                    br.close();
                    throw  new IOException("Incomplete Data! Room and Time Slot for the Schedule of the Seminar : " + columns[0] + " " +
                            "Inside the file: " + file.getAbsolutePath());
                }
                else if(columns.length < 2)
                {
                    br.close();
                    throw  new IOException("Incomplete Data! Time Slot for the Schedule of the Seminar : " + columns[0] + " " +
                            "Inside the file: " + file.getAbsolutePath());
                }

                for(Schedule sc : schedules)
                {
                    if(sc.getSeminar().getName().equals(columns[0]))
                    {
                        sc.setRoom(new Room(columns[1]));
                        isFileEmpty = false;
                        int x;
                        try {
                            x = Integer.parseInt(columns[2].trim());
                        } catch (NumberFormatException e) {
                            br.close();
                            throw new NumberFormatException("Unable to parse " + columns[2] + " as integer Time Slot from the Schedule list of the seminar " + columns[0] + " in file: " + file.getAbsolutePath());
                        }
                        sc.setTimeSlot(new TimeSlot(x));
                        this.scheduleDAO.updateRoomOrTimeSlot(sc);
                    }
                }
            }
            br.close();
            if(isFileEmpty)
            {
                throw new IOException("Empty file: " + file.getAbsolutePath());
            }
        }catch (FileNotFoundException e) {
            throw new FileNotFoundException("Custom message: File not found: " + file.getAbsolutePath());
        } catch (IOException e) {
            throw new IOException("Custom message: Error reading file: " + file.getAbsolutePath(), e);
        }
    }

    @Override
    public void readScheduleList(File file) throws IOException {
        boolean isFileEmpty = true;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if(line.trim().isEmpty())
                {
                    continue;
                }
                String []columns = line.split(",");
                if(columns.length < 1)
                {
                    br.close();
                    throw  new IOException("Incomplete Data! Room and Time Slot for the Schedule of the Seminar : " + columns[0] + " " +
                            "Inside the file: " + file.getAbsolutePath());
                }
                else if(columns.length < 2)
                {
                    br.close();
                    throw  new IOException("Incomplete Data! Time Slot for the Schedule of the Seminar : " + columns[0] + " " +
                            "Inside the file: " + file.getAbsolutePath());
                }

                for(Schedule sc : schedules)
                {
                    if(sc.getSeminar().getName().equals(columns[0]))
                    {
                        if(!sc.getRoom().getName().equals("Not Set!"))
                        {
                            br.close();
                            throw new RuntimeException("File : " + file.getAbsolutePath() + " " +
                                    "is trying to overwrite the scheduled room of the seminar: " + columns[0] + " " +
                                    "which is "+sc.getRoom().getName()+"! \n" +
                                    "Either the the room is already been set or the room is set twice in the file.");
                        }
                        if(sc.getTimeSlot().getValue() != -1)
                        {
                            br.close();
                            throw new RuntimeException("File : " + file.getAbsolutePath() + " is trying to overwrite the scheduled time slot of the seminar: " + columns[0] + " which is " + sc.getTimeSlot().getValue() + "! \nEither the the room is already been set or the room is set twice in the file.");
                        }
                        sc.setRoom(new Room(columns[1]));
                        isFileEmpty = false;
                        int x;
                        try {
                            x = Integer.parseInt(columns[2].trim());
                        } catch (NumberFormatException e) {
                            br.close();
                            throw new NumberFormatException("Unable to parse " + columns[2] + " as integer Time Slot from the Schedule list of the seminar " + columns[0] + " in file: " + file.getAbsolutePath());
                        }
                        sc.setTimeSlot(new TimeSlot(x));
                        this.scheduleDAO.addSchedule(sc);
                    }
                }
            }
            br.close();
            if(isFileEmpty)
            {
                throw new IOException("Empty file: " + file.getAbsolutePath());
            }
        }catch (FileNotFoundException e) {
            throw new FileNotFoundException("Custom message: File not found: " + file.getAbsolutePath());
        } catch (IOException e) {
            throw new IOException("Custom message: Error reading file: " + file.getAbsolutePath(), e);
        }
    }

    @Override
    public ArrayList<ConflictingSeminars> getConflictingSeminarNames() {
        ArrayList<ConflictingSeminars> names = new ArrayList<>();

        for (int i = 0; i < schedules.size() - 1; i++) {
            Schedule sc1 = schedules.get(i);

            for (int j = i + 1; j < schedules.size(); j++) {
                Schedule sc2 = schedules.get(j);

                // Check if there's a conflict between sc1 and sc2
                if (sc1.getTimeSlot().equals(sc2.getTimeSlot()) && sc1.getRoom().equals(sc2.getRoom())) {
                    ConflictingSeminars newConflict = new ConflictingSeminars(sc1.getSeminar().getName(), sc2.getSeminar().getName());

                    // Set the ID based on existing conflicts
                    for (ConflictingSeminars existingConflict : names) {
                        int existingId = existingConflict.checkIfAlreadyConflicted(sc1.getSeminar().getName());
                        if (existingId != -1) {
                            newConflict.id = Math.min(newConflict.id, existingId);
                        }
                        existingId = existingConflict.checkIfAlreadyConflicted(sc2.getSeminar().getName());
                        if (existingId != -1) {
                            newConflict.id = Math.min(newConflict.id, existingId);
                        }
                    }

                    // Add the conflict only if it doesn't already exist
                    if (!names.contains(newConflict)) {
                        names.add(newConflict);
                    }
                }
            }
        }

        return names;
    }



    @Override
    public ArrayList<ConflictingAttendees> getConflictingAttendeesID() {
        ArrayList<ConflictingAttendees> conflicts = new ArrayList<>();

        for (int i = 0; i < schedules.size() - 1; i++) {
            Set<Integer> ids = new HashSet<>();
            Schedule sc1 = schedules.get(i);
            for (int j = i + 1; j < schedules.size(); j++) {
                Schedule sc2 = schedules.get(j);

                if (sc1.getTimeSlot().equals(sc2.getTimeSlot())) {
                    // Use a HashSet for faster intersection
                    Set<Integer> attendees1 = new HashSet<>(sc1.getSeminar().getAttendeesIDs());
                    Set<Integer> attendees2 = new HashSet<>(sc2.getSeminar().getAttendeesIDs());

                    attendees1.retainAll(attendees2); // Retain only common IDs
                    ids.addAll(attendees1); // Add common IDs to the result set

                    conflicts.add(new ConflictingAttendees(sc1.getSeminar().getName(), sc2.getSeminar().getName(), new ArrayList<>(ids)));
                }
            }
        }

        return conflicts;
    }

    @Override
    public ArrayList<Schedule> getSchedules() {
        return this.scheduleDAO.getScheduleList();
    }

    @Override
    public void setSchedules(ArrayList<Schedule> schedules) {
        this.schedules = schedules;
        for(Schedule sc : schedules)
        {
            this.seminarsDAO.addSeminar(sc.getSeminar());
        }
        for(Schedule sc : schedules)
        {
            this.scheduleDAO.addSchedule(sc);
        }
    }

    @Override
    public ArrayList<Seminar> getSeminarList() {

        return this.seminarsDAO.getSeminarList();
    }

    @Override
    public void addSeminar(Seminar seminar) throws RuntimeException{
        for(Seminar sm : getSeminarList())
        {
            if(sm.getName().equals(seminar.getName()))
            {
                if(sm.getSpeaker().getName().equals(seminar.getSpeaker().getName()) && sm.getAttendees().equals(seminar.getAttendees()))
                {
                    throw new RuntimeException("Seminar Already Exists! ");
                }
                else
                {
                    if(!sm.getSpeaker().getName().equals(seminar.getSpeaker().getName()) && !seminar.getSpeaker().getName().isEmpty())
                    {
                        throw new RuntimeException("trying to overwrite the Speaker: "+sm.getSpeaker().getName()+" of the Seminar: "+sm.getName()+" with speaker: "+seminar.getSpeaker().getName());
                    }
                    if(!sm.getAttendees().equals(seminar.getAttendees()) && (seminar.getAttendees().size() > 0) )
                    {
                        throw new RuntimeException("trying to overwrite the attendess: "+sm.getAttendees()+" of the Seminar: "+sm.getName()+" with attendees: "+seminar.getAttendees());
                    }
                }
            }
        }

        if(!seminar.getSpeaker().getName().trim().isEmpty()) {
            schedules.add(new Schedule(seminar));
            if(!seminarsDAO.addSeminar(seminar))
            {
                System.out.println("Seminar not added to the database!");
            }
        }
    }

    @Override
    public void forceAddSeminar(Seminar seminar) {
        boolean isEdited = false;
        for(Seminar sm : getSeminarList())
        {
            if(sm.getName().equals(seminar.getName()))
            {
                if(!sm.getSpeaker().getName().equals(seminar.getSpeaker().getName()) && !seminar.getSpeaker().getName().isEmpty())
                {
                    sm.getSpeaker().setName(seminar.getSpeaker().getName());
                    isEdited = true;
                }
                if(!sm.getAttendees().equals(seminar.getAttendees()) && (seminar.getAttendees().size() > 0) )
                {
                    sm.getAttendees().clear();
                    sm.getAttendees().addAll(seminar.getAttendees());
                    isEdited = true;
                }

                if(isEdited) {
                    if(!seminarsDAO.updateSeminar(sm))
                    {
                        System.out.println("Seminar not updated in the database!");
                    }
                    else
                        System.out.println("Seminar updated successfully");
                    break;
                }
            }
        }

        if(!isEdited && !getSeminarList().contains(seminar)){
            schedules.add(new Schedule(seminar));
            if(!seminarsDAO.addSeminar(seminar))
            {
                System.out.println("Seminar not updated but added in the database!");
            }
            else
                System.out.println("Seminar nor updated nor added successfully");
        }
    }

    @Override
    public void addSchedule(Schedule schedule) throws RuntimeException {
        boolean isExisted = false;

        // Check if schedule already exists in the list
        for (Schedule sc : this.schedules) {
            System.out.println("Comparing " + schedule.getSeminar().getName() + " to " + sc.getSeminar().getName());

            if (sc.getSeminar().getName().trim().equals(schedule.getSeminar().getName().trim())) {
                // Check if room or time slot is already set
                if (!sc.getRoom().getName().equals("Not Set!") && !sc.getRoom().getName().isEmpty()) {
                    throw new RuntimeException("You are trying to overwrite the scheduled room of the seminar: " +
                            sc.getSeminar().getName() + ", which is " + sc.getRoom().getName() + "! \n" +
                            "Either the room has already been set or the room is set twice in the file.");
                }
                if (sc.getTimeSlot().getValue() != -1 && sc.getTimeSlot().getValue() != 0) {
                    throw new RuntimeException("You are trying to overwrite the scheduled time slot of the seminar: " +
                            sc.getSeminar().getName() + ", which is " + sc.getTimeSlot().getValue() + "! \n" +
                            "Either the time slot has already been set or it is set twice in the file.");
                }
                // Update room and time slot if it’s valid to set them
                sc.getRoom().setName(schedule.getRoom().getName());
                sc.getTimeSlot().setValue(schedule.getTimeSlot().getValue());
                if(!this.scheduleDAO.updateRoomOrTimeSlot(sc))
                    System.out.println("Schedule not updated in the database!");
                else
                    System.out.println("Schedule updated to the database");

                System.out.println(sc + " already exists in the list and has been updated.");
                isExisted = true;
                break;
            }
        }

        if (!isExisted) {
            if (!this.scheduleDAO.addSchedule(schedule)) {
                System.out.println("Schedule not added to the database!");
            } else {
                System.out.println("Schedule added to the database.");
                this.schedules = this.scheduleDAO.getScheduleList();
            }
        }
    }


    @Override
    public void forceAddSchdeule(Schedule schedule) {

        for(Schedule sc : this.schedules)
        {
            if(sc.getSeminar().getName().equals(schedule.getSeminar().getName()))
            {
                sc.getRoom().setName(schedule.getRoom().getName());
                sc.getTimeSlot().setValue(schedule.getTimeSlot().getValue());
                break;
            }
        }

        if(!this.scheduleDAO.updateRoomOrTimeSlot(schedule))
            System.out.println("Schedule not updated in the database!");
        else
            System.out.println("Schedule updated to the database");
    }

    @Override
    public boolean deleteSeminar(String seminarName) {
        if(this.seminarsDAO.deleteSeminar(seminarName))
        {
            this.schedules = scheduleDAO.getScheduleList();
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteSchedule(String seminarName) {
        if(this.scheduleDAO.deleteSchedule(seminarName))
        {
            this.schedules = scheduleDAO.getScheduleList();
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScheduleList that)) return false;
        return Objects.equals(schedules, that.schedules);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(schedules);
    }
}

