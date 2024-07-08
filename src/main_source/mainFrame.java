//Author: suayptalha
//Date: 08.07.2024

package main_source;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class mainFrame extends JFrame implements ActionListener{
    JButton add_button;
    JButton curr_button;
    JButton comp_button;
    JPanel tasks;
    ArrayList<String> taskList;
    static ArrayList<String> compTaskList;
    completedTasksWindow compTasksWindow;

    public mainFrame(){ 
        this.setResizable(true);
        this.setSize(800,600);
        this.setMinimumSize(new Dimension(700, 600));

        this.setTitle("To-Do App");
        ImageIcon icon = new ImageIcon("todo-icon.png");
        this.setIconImage(icon.getImage());

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

        JPanel mid_up = new JPanel();
        mid_up.setOpaque(false);
        mid_up.setLayout(new FlowLayout());
        mid_up.setBorder(new EmptyBorder(20, 20, 10, 20));
        mid.add(mid_up,BorderLayout.NORTH);
        
        tasks = new JPanel();
        tasks.setOpaque(false);
        tasks.setLayout(new BoxLayout(tasks, BoxLayout.Y_AXIS));
        tasks.setBorder(new EmptyBorder(20, 20, 10, 20));
        tasks.add(Box.createVerticalStrut(10));
        mid.add(tasks, BorderLayout.CENTER);

        //Labels
        JLabel title_l = new JLabel("To-Do App");
        title_l.setForeground(new Color(255,255,255));
        title_l.setFont(new Font("Roboto", Font.PLAIN, 20));
        title_l.setHorizontalTextPosition(JLabel.LEFT);
        title_l.setVerticalTextPosition(JLabel.TOP);
        title_l.setHorizontalAlignment(JLabel.LEFT);
        title_l.setVerticalAlignment(JLabel.TOP);
        title_l.setBounds(10,10,100,100);
        up.add(title_l, BorderLayout.WEST);

        //Buttons
        add_button = new JButton("New Task");
        add_button.addActionListener(this);
        add_button.setPreferredSize(new Dimension(100, 20));
        add_button.setBackground(new Color(3, 248, 252));
        add_button.setBorderPainted(false);
        add_button.setFocusable(false);
        up.add(add_button,BorderLayout.EAST);
        
        /*
        curr_button = new JButton("Current Tasks");
        curr_button.addActionListener(this);
        curr_button.setPreferredSize(new Dimension(150, 25));
        curr_button.setBackground(new Color(0,0,0));
        curr_button.setForeground(Color.white);
        curr_button.setFocusable(false);
        curr_button.setBorder(new LineBorder(Color.white, 2));
        mid_up.add(curr_button);
        */

        comp_button = new JButton("Completed Tasks");
        comp_button.addActionListener(this);
        comp_button.setPreferredSize(new Dimension(150, 25));
        comp_button.setBackground(new Color(0,0,0));
        comp_button.setForeground(Color.white);
        comp_button.setFocusable(false);
        comp_button.setBorder(new LineBorder(Color.white, 2));
        mid_up.add(comp_button);

        this.setVisible(true);
        
        //Array lists
        taskList = new ArrayList<>();
        taskList = readData();
        if (taskList != null) {
            for (String task : taskList) {
                addTask(task, 0);
            }
        }
        
        compTaskList = new ArrayList<>();
        compTaskList = readCompData();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == add_button) {
            System.out.println("clicked");
            taskEntryWindow win = new taskEntryWindow(this);
        }else if(e.getSource() == comp_button) {
        	completedTasksWindow comp_win = new completedTasksWindow();
        }
    }

    public void addTask(String task, int num) {
    	
    	if(num == 1) {
	    	taskList.add(task);
	        saveData();
    	}
    	
        JPanel taskPanel = new JPanel(new BorderLayout());
        taskPanel.setOpaque(false);
        taskPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
        
        ImageIcon tick = new ImageIcon("tick image.jpg");
        Image tickImage = tick.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon scaledTickIcon = new ImageIcon(tickImage);
        
        JLabel taskLabel = new JLabel(task);
        taskLabel.setForeground(Color.white);
        taskLabel.setFont(new Font("Roboto", Font.PLAIN, 16));
        taskPanel.add(taskLabel, BorderLayout.CENTER);
        
        JButton completeButton = new JButton("Completed");
        //completeButton.setMaximumSize(new Dimension(50,50));
        completeButton.setPreferredSize(new Dimension(125,0));
        
        completeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tasks.remove(taskPanel);
                tasks.revalidate();
                tasks.repaint();
                compTaskList.add(taskLabel.getText());
                saveCompData();
                taskList.remove(taskLabel.getText());
                saveData();
                
                int temp = 0;
                
                for (Window window : Window.getWindows()) {
                    if (window instanceof completedTasksWindow) {
                        window.dispose();
                        temp++;
                    }
                }
                
                if(temp > 0) {
                	completedTasksWindow comp_win = new completedTasksWindow();
                }
            }
        });
        
        completeButton.setForeground(Color.white);
        completeButton.setIcon(scaledTickIcon);
        completeButton.setBackground(Color.black);
        completeButton.setFocusable(false);
        completeButton.setBorderPainted(false);   
        taskPanel.add(completeButton, BorderLayout.EAST);
        
        tasks.add(taskPanel);
        tasks.add(Box.createVerticalStrut(10));
        tasks.revalidate();
        tasks.repaint();
    }
    
    public ArrayList<String> readData() {
    	ArrayList<String> list = new ArrayList<>();
        try {
            FileInputStream fileIn = new FileInputStream("tasks.ser");
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
    
    public void saveData() {
    	try {
            FileOutputStream fileOut = new FileOutputStream("tasks.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(taskList);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
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
            out.writeObject(compTaskList);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}
