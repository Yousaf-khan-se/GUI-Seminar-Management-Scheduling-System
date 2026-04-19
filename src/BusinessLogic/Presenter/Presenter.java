package BusinessLogic.Presenter;

import BusinessLogic.Model.*;
import UI.View.IView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Presenter {

    IModel model;
    IView view;
    public Presenter(IModel model, IView view)
    {
        this.model = model;
        this.view = view;
    }

    public void getAndloadFileDatatoSeminarTable(File seminarFile){
        boolean forceRead = false;
        try {
            model.readSeminarList(seminarFile);
        } catch (Exception e) {
            if(e.getMessage().contains("trying to overwrite"))
            {
                forceRead = view.showConfirmationMessage(e.getMessage());
            }
            else
            {
                view.showErrorMessage("Seminar Data not updated 2!\n" + e.getMessage());
                return;
            }
        }

        if(forceRead)
        {
            try {
                model.forceReadSeminarList(seminarFile);
            } catch (Exception ex) {
                view.showErrorMessage("Seminar Data not updated 1!\n" + ex.getMessage());
                return;
            }
        }

        view.loadSeminarFormTable(model.getSeminarList());
    }

    public void getAndloadFileDatatoScheduleTable(File scheduleFile){
        try {
            model.readScheduleList(scheduleFile);
        } catch (Exception e) {
            if(e instanceof RuntimeException && e.getMessage().contains("trying to overwrite"))
            {
                if(view.showConfirmationMessage(e.getMessage())) {
                    try {
                        model.forceReadScheduleList(scheduleFile);
                    } catch (IOException ex) {
                        view.showErrorMessage("Schedule Data not updated!\n" + e.getMessage());
                        return;
                    }
                }
            }
            else
            {
                view.showErrorMessage("Schedule Data not updated!\n" + e.getMessage());
                return;
            }
        }
        view.loadSchedulingFormTable(model.getSchedules());
    }

    public ArrayList<ConflictingSeminars> getConflictingSeminarNames()
    {
        return model.getConflictingSeminarNames();
    }

    public ArrayList<ConflictingAttendees> getConflictingAttendeesIDs()
    {
        return model.getConflictingAttendeesID();
    }

    public void addNewSeminar(Seminar seminar)
    {
        try {
            model.addSeminar(seminar);
        } catch (Exception e) {
            if(view.showConfirmationMessage(e.getMessage()))
                model.forceAddSeminar(seminar);
        }
    }

    public void addNewSchedule(Schedule schedule)
    {
        try
        {
            System.out.println("Adding Schedule("+schedule+")...");
            model.addSchedule(schedule);
        } catch (RuntimeException e) {
//            throw new RuntimeException(e);
            if(view.showConfirmationMessage(e.getMessage())) {
                System.out.println("Updating Schedule("+schedule+")...");
                model.forceAddSchdeule(schedule);
            }
        }
    }

    public void removeSeminar(String seminarName)
    {
        if(!model.deleteSeminar(seminarName))
        {
            view.showErrorMessage("Unable to delete seminar "+ seminarName + " from the seminar table");
        }
    }

    public void removeSchedule(String seminarName)
    {
        if(!model.deleteSchedule(seminarName))
        {
            view.showErrorMessage("Unable to delete schedule of "+ seminarName + " from the schedule table");
        }
    }

    public ArrayList<Seminar> getSeminars()
    {
        return model.getSeminarList();
    }

    public ArrayList<Schedule> getScheduleList()
    {
        return model.getSchedules();
    }
}
