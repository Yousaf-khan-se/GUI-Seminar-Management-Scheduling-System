package UI.View;

import UI.View.Forms.ConflictInfoForm;
import UI.View.Forms.SchedulingForm;
import UI.View.Forms.SeminarForm;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame implements FormSwitcher{

    public NavBar navBar;

    public SeminarForm seminarForm;
    public SchedulingForm schedulingForm;
    public ConflictInfoForm conflictsForm;

    private JPanel formCardPanel;

    public View()
    {
        this.setTitle("Event Scheduling System");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout(5,5));
        this.setSize(800, 730);
        this.setMinimumSize(new Dimension(730, 730));
        this.getContentPane().setBackground(Color.darkGray);

        seminarForm = new SeminarForm("Seminar Information:", "Seminars", "Speakers", "Attendies");
        schedulingForm = new SchedulingForm("Seminar Information:", "Seminars","Rooms", "Time Slots");
        conflictsForm = new ConflictInfoForm("Seminar Information:", "Seminars","Speakers", "Attendies");

        formCardPanel = new JPanel(new CardLayout(5,15));
        formCardPanel.setBackground(Color.darkGray);
        formCardPanel.add(seminarForm, "seminarForm");
        formCardPanel.add(schedulingForm, "schedulingForm");
        formCardPanel.add(conflictsForm, "conflictsForm");

        navBar = new NavBar(this);

        this.add(navBar,BorderLayout.NORTH);
        this.add(formCardPanel);

        this.setVisible(true);
    }

    @Override
    public void switchForm(String cardName) {
        ((CardLayout)formCardPanel.getLayout()).show(formCardPanel,cardName);
    }
}
