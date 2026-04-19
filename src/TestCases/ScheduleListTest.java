package TestCases;

import BusinessLogic.Model.*;
import DataAccessLayer.DAOClasses.DBDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class ScheduleListTest {
    private ScheduleList scheduleList;

    @BeforeEach
    void setUp() {
        DBDAO.clearDataBase();

        scheduleList = new ScheduleList();

        // Create mock seminars, rooms, and time slots for testing
        Seminar seminar1 = new Seminar("Seminar A", new Speaker("Dr. Smith"));
        seminar1.addAttendee(new Attendee(101));
        seminar1.addAttendee(new Attendee(102));

        Seminar seminar2 = new Seminar("Seminar B", new Speaker("Dr. Jane"));
        seminar2.addAttendee(new Attendee(102)); // Conflicting attendee with Seminar A
        seminar2.addAttendee(new Attendee(103));

        Seminar seminar3 = new Seminar("Seminar C", new Speaker("Dr. Alan"));
        seminar3.addAttendee(new Attendee(104));

        // Setup schedules with conflicting and non-conflicting time slots and rooms
        Schedule schedule1 = new Schedule(seminar1, new Room("Room 1"), new TimeSlot(10));
        Schedule schedule2 = new Schedule(seminar2, new Room("Room 1"), new TimeSlot(10)); // Conflicts with schedule1
        Schedule schedule3 = new Schedule(seminar3, new Room("Room 2"), new TimeSlot(11)); // No conflict

        ArrayList<Schedule> schedules = new ArrayList<>();
        schedules.add(schedule1);
        schedules.add(schedule2);
        schedules.add(schedule3);

        scheduleList.setSchedules(schedules);
    }

    @Test
    void testGetConflictingSeminarNames() {
        ArrayList<ConflictingSeminars> conflicts = scheduleList.getConflictingSeminarNames();

        // Check if the correct conflicting seminars are identified
        assertEquals(1, conflicts.size(), "Expected 1 conflict due to time and room overlap");
        ConflictingSeminars conflict = conflicts.get(0);

        assertTrue((conflict.seminarName1.equals("Seminar A") && conflict.seminarName2.equals("Seminar B")) ||
                        (conflict.seminarName1.equals("Seminar B") && conflict.seminarName2.equals("Seminar A")),
                "Expected conflict between Seminar A and Seminar B");
    }

    @Test
    void testGetConflictingAttendeesID() {
        ArrayList<ConflictingAttendees> conflicts = scheduleList.getConflictingAttendeesID();

        // Check if the conflicting attendees are identified for overlapping time slots
        assertEquals(1, conflicts.size(), "Expected 1 conflict due to overlapping attendee");
        ConflictingAttendees conflict = conflicts.get(0);

        assertEquals("Seminar A", conflict.seminarName1);
        assertEquals("Seminar B", conflict.seminarName2);
        assertTrue(conflict.conflictingAttendeesIDs.contains(102), "Expected attendee ID 102 to be in the conflict list");
    }
}

