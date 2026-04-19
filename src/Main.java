import BusinessLogic.Model.*;
import BusinessLogic.Presenter.Presenter;
import UI.View.IView;
import UI.View.View;
import UI.View.ViewController;

import javax.swing.*;

public class Main {
    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            View viewFrontEnd = new View();
            IView view = new ViewController(viewFrontEnd);
            IModel model = new ScheduleList();
            Presenter presenter = new Presenter(model, view);
            ViewController controllSet = (ViewController) view;
            controllSet.setPresenter(presenter);
        });


//
//        ScheduleList sl = new ScheduleList();
//        File file = new File("C:\\Users\\Yousaf Khan\\Desktop\\JAVA Projects\\SCD_As_2\\src\\DataAccessLayer.DataFiles\\seminarList.txt");
//        File file2 = new File("C:\\Users\\Yousaf Khan\\Desktop\\JAVA Projects\\SCD_As_2\\src\\DataAccessLayer.DataFiles\\schedule.txt");
//
//        try {
//            sl.readSeminarList(file);
//            sl.readScheduleList(file2);
//
////            System.out.println(sl.getSeminarList());
////            sl.getSchedules().get(0).setRoom(new Room("R204"));
////            System.out.println(sl.getSchedules());
//            System.out.println(sl.getConflictingSeminarNames());
//            System.out.println(sl.getConflictingAttendeesID());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }


//        ArrayList<Attendee> a1 = new ArrayList<Attendee>();
//        a1.add(new Attendee("1"));
//        a1.add(new Attendee("2"));
//
//        ArrayList<Attendee> a2 = new ArrayList<Attendee>();
//        a2.add(new Attendee("1"));
//        a2.add(new Attendee("2"));
//
//        Schedule s1 = new Schedule(new Seminar("sem1", new Speaker("sp1"),a1), new Room("R1"), new TimeSlot(1));
//        Schedule s2 = new Schedule(new Seminar("sem1", new Speaker("sp1"),a2), new Room("R1"), new TimeSlot(1));
////        Seminar s1 = new Seminar("sem1", new Speaker("sp1"));
////        Seminar s2 = new Seminar("sem1", new Speaker("sp1"));
//
//        if(s1.equals(s2))
//        {
//            System.out.println("True");
//        }
//        else
//        {
//            System.out.println("false");
//        }


    }
}
