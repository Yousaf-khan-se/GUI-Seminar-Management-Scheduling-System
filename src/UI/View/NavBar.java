package UI.View;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;

public class NavBar extends JPanel {
    public JButton seminarLstBtn = createNavBtn("Show Seminar List");
    public JButton schedulingInfoBtn = createNavBtn("Show Scheduling Information");
    public JButton conflictsInfoBtn = createNavBtn("Show Conflicts");

    private FormSwitcher formSwitcher;

    public NavBar(FormSwitcher formSwitcher)
    {
        this.formSwitcher = formSwitcher;
        this.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
        this.setBackground(Color.black);
        this.setSize(800,70);

        NavButtonSynchronization(seminarLstBtn.getText());

        this.add(seminarLstBtn);
        this.add(schedulingInfoBtn);
        this.add(conflictsInfoBtn);
    }

    private JButton createNavBtn(String name)
    {
        JButton btn = new JButton(name);
        btn.setSize(50,20);
        btn.setBackground(Color.BLACK);
        btn.setForeground(Color.white);
        btn.setFont(new Font("TimesRoman", Font.BOLD,18));
        btn.setContentAreaFilled(false);

        btn.setBorder(RaisedBorder());
        btn.putClientProperty("isRaised", true);

        btn.addActionListener(e -> {
            NavButtonSynchronization(((JButton) e.getSource()).getText());
        });
        return btn;
    }

    private static Border RaisedBorder()
    {
        return BorderFactory.createSoftBevelBorder(BevelBorder.RAISED,Color.cyan,Color.black);
    }

    private static Border LoweredBorder()
    {
        return BorderFactory.createSoftBevelBorder(BevelBorder.LOWERED,Color.cyan,Color.black);
    }

    private void RaiseButton(JButton btn)
    {
        boolean isRaised = (boolean)btn.getClientProperty("isRaised");
        if(!isRaised)
        {
            btn.setBorder(RaisedBorder());
            btn.setForeground(Color.white);
            btn.putClientProperty("isRaised", !isRaised);
        }
    }

    private void LowerButton(JButton btn)
    {
        boolean isRaised = (boolean)btn.getClientProperty("isRaised");
        if(isRaised)
        {
            btn.setBorder(LoweredBorder());
            btn.setForeground(Color.cyan);
            btn.putClientProperty("isRaised", !isRaised);
        }
    }


    private void NavButtonSynchronization(String btnName)
    {
        if(btnName.equals("Show Seminar List"))
        {
            LowerButton(seminarLstBtn);
            RaiseButton(schedulingInfoBtn);
            RaiseButton(conflictsInfoBtn);

            this.formSwitcher.switchForm("seminarForm");
        }
        else if(btnName.equals("Show Scheduling Information"))
        {
            RaiseButton(seminarLstBtn);
            LowerButton(schedulingInfoBtn);
            RaiseButton(conflictsInfoBtn);

            this.formSwitcher.switchForm("schedulingForm");
        }
        else if(btnName.equals("Show Conflicts"))
        {
            RaiseButton(seminarLstBtn);
            RaiseButton(schedulingInfoBtn);
            LowerButton(conflictsInfoBtn);

            this.formSwitcher.switchForm("conflictsForm");

        }
    }
}
