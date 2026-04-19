package UI.View.Forms;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class Form extends JPanel {
    public JTable table;
    public String formHeader;

    public String field1Header;
    public String field2Header;
    public String field3Header;

    public DefaultTableModel tableModel;

    public JPanel headerPanel;
    public JPanel editsPanel;
    public JPanel infoPanel;

    Form(String formHeader, String field1Header, String field2Header, String field3Header)
    {
        this.formHeader = formHeader;
        this.field1Header = field1Header;
        this.field2Header = field2Header;
        this.field3Header = field3Header;

        this.setLayout(new BorderLayout(10,10));
        this.setSize(900,650);
        this.setBackground(Color.darkGray);

        headerPanel = createHeaderPanel();
        // first createeditsForm() to initialize editsForm
        infoPanel = createInfoPanel();

        this.add(headerPanel, BorderLayout.NORTH);
        //first initialize edit form to add it
        this.add(infoPanel, BorderLayout.SOUTH);
    }

    private JPanel createHeaderPanel()
    {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        this.setSize(860, 50);
        panel.setBackground(Color.black);
        JLabel header = createFormLabel(formHeader);
        header.setFont(new Font("TimesRoman", Font.BOLD, 14));
        panel.add(header);

        return panel;
    }

    protected abstract JPanel createEditsPanel();

    private JPanel createInfoPanel()
    {
        JPanel panel = new JPanel(new BorderLayout(20,10));
        panel.setPreferredSize(new Dimension(860, 300));
        panel.setBackground(Color.BLACK);

        String colNames[] = {field1Header, field2Header, field3Header};
        tableModel = new MyTableMode(colNames,0);

        table = new JTable(tableModel);
        table.setForeground(Color.cyan);
        table.setBackground(Color.black);
        table.setFillsViewportHeight(true);
//        table.setEnabled(false);


        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    protected JButton createFormButton(String text)
    {
        JButton btn = new JButton(text);
        btn.setForeground(Color.cyan);
        btn.setBackground(Color.black);
        btn.setSize(20,20);
        btn.setPreferredSize(new Dimension(text.length() * 10, 30));
        btn.setFont(new Font("TimesRoman", Font.BOLD,14));
        btn.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.cyan, Color.gray));
        btn.setContentAreaFilled(false);

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                btn.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.cyan, Color.gray));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                try {
                    Thread.sleep(250);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                btn.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.cyan, Color.gray));
            }
        });

        return btn;
    }

    protected JLabel createFormLabel(String text)
    {
        JLabel lbl = new JLabel(text);
        lbl.setForeground(Color.cyan);
        lbl.setBackground(Color.black);
        lbl.setSize(10,15);
        lbl.setFont(new Font("TimesRoman", Font.PLAIN, 14));
        return lbl;
    }

    protected JTextField createFormTextField()
    {
        JTextField textField = new JTextField();
        textField.setBackground(Color.black);
        textField.setForeground(Color.cyan);
        textField.setPreferredSize(new Dimension(150, 25));
        textField.setFont(new Font("TimesRoman", Font.PLAIN, 14));

        return textField;
    }

    protected <T> JComboBox<T> createComboBox(DefaultComboBoxModel<T> model)
    {
        JComboBox<T> comboBox = new JComboBox(model);
        comboBox.setBackground(Color.black);
        comboBox.setForeground(Color.cyan);
        comboBox.setSize(100,10);
        comboBox.setFont(new Font("TimesRoman", Font.PLAIN, 14));
        comboBox.setEditable(false);

        return comboBox;
    }

    protected JPanel getComponentsRow(JComponent components[])
    {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER,5,3));
        row.setBackground(Color.black);

        for(JComponent comp : components)
        {
            row.add(comp);
        }

        return row;
    }
}

class MyTableMode extends DefaultTableModel
{
    public MyTableMode(Object[] columnNames, Integer noOfRows) {
        super(columnNames, noOfRows);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}