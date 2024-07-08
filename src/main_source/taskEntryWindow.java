//Author: suayptalha
//Date: 08.07.2024

package main_source;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class taskEntryWindow extends JFrame implements ActionListener {
    JTextArea taskArea;
    JButton saveButton;
    JPanel panel;
    mainFrame parentFrame;

    public taskEntryWindow(mainFrame parentFrame) {
    	this.parentFrame = parentFrame;
        setTitle("Add new task");
        setSize(300, 150);
        this.setMinimumSize(new Dimension(400,200));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        taskArea = new JTextArea(5, 20);
        taskArea.setLineWrap(true);
        taskArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(taskArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        saveButton = new JButton("Save");
        saveButton.setBackground(Color.black);
        saveButton.setForeground(Color.white);
        saveButton.addActionListener(this);
        saveButton.setFocusable(false);
        panel.add(saveButton, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            String task = taskArea.getText();
            System.out.println(task);
            parentFrame.addTask(task, 1);
            dispose();
        }
    }
}
