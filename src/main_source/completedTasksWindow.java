//Author: suayptalha
//Date: 08.07.2024

package main_source;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class completedTasksWindow extends JFrame implements ActionListener {
    JPanel panel;
    JPanel tasks;
    JButton clear_button;
    
    public completedTasksWindow() {
        
        setTitle("Completed Tasks");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        this.setResizable(true);
        this.setSize(800,600);
        this.setMinimumSize(new Dimension(700, 600));

        this.setTitle("Completed Tasks");
        ImageIcon icon = new ImageIcon("todo-icon.png");
        this.setIconImage(icon.getImage());
        
        this.getContentPane().setBackground(new Color(0,0,0));

        this.setLayout(new BorderLayout());

        //Panels
        JPanel up = new JPanel();
        this.add(up,BorderLayout.NORTH);
        up.setLayout(new BorderLayout());
        up.setBorder(new EmptyBorder(20, 20, 10, 20));
        up.setOpaque(false);

        JPanel mid = new JPanel();
        mid.setOpaque(false);
        mid.setLayout(new BorderLayout());
        this.add(mid,BorderLayout.CENTER);
        
        tasks = new JPanel();
        tasks.setOpaque(false);
        tasks.setLayout(new BoxLayout(tasks, BoxLayout.Y_AXIS));
        tasks.setBorder(new EmptyBorder(20, 20, 10, 20));
        tasks.add(Box.createVerticalStrut(10));
        mid.add(tasks, BorderLayout.CENTER);

        //Labels
        JLabel title_l = new JLabel("Completed Tasks");
        title_l.setForeground(new Color(255,255,255));
        title_l.setFont(new Font("Roboto", Font.PLAIN, 20));
        title_l.setHorizontalTextPosition(JLabel.LEFT);
        title_l.setVerticalTextPosition(JLabel.TOP);
        title_l.setHorizontalAlignment(JLabel.LEFT);
        title_l.setVerticalAlignment(JLabel.TOP);
        title_l.setBounds(10,10,100,100);
        up.add(title_l, BorderLayout.WEST);

        //Buttons
        clear_button = new JButton("Clear");
        clear_button.addActionListener(this);
        clear_button.setPreferredSize(new Dimension(100, 20));
        clear_button.setBackground(new Color(3, 248, 252));
        clear_button.setBorderPainted(false);
        clear_button.setFocusable(false);
        up.add(clear_button,BorderLayout.EAST);
        
        //Array lists
        mainFrame.compTaskList =  readCompData();
        if (mainFrame.compTaskList != null) {
            for (String task : mainFrame.compTaskList) {
                addCompTask(task);
            }
        }
        
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == clear_button) {
        	mainFrame.compTaskList.clear();
        	saveCompData();
            dispose();
            completedTasksWindow comp_win = new completedTasksWindow();
        }
    }
    
    public ArrayList<String> readCompData() {
    	ArrayList<String> list = new ArrayList<>();
        try {
            FileInputStream fileIn = new FileInputStream("completed_tasks.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            list = (ArrayList<String>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            System.out.println("file couldn't found");
            list = new ArrayList<>();
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
        }
        return list;
    }
    
    public void saveCompData() {
    	try {
            FileOutputStream fileOut = new FileOutputStream("completed_tasks.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(mainFrame.compTaskList);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
    
    public void addCompTask(String task) {
        JPanel taskPanel = new JPanel(new BorderLayout());
        taskPanel.setOpaque(false);
        taskPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
        
        JLabel taskLabel = new JLabel(task);
        taskLabel.setForeground(Color.white);
        taskLabel.setFont(new Font("Roboto", Font.PLAIN, 16));
        taskPanel.add(taskLabel, BorderLayout.CENTER);
        
        tasks.add(taskPanel);
        tasks.add(Box.createVerticalStrut(10));
        tasks.revalidate();
        tasks.repaint();
    }
}
