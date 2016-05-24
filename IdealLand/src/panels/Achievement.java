package panels;
/**
 * This Panel generates all the achievements for the user by a given input
 * 
 * by Randy Lin
 * 
 * Ideal land
 */
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.BoxLayout;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;

import java.awt.Font;
import java.io.*;

import javax.swing.*;

public class Achievement extends JPanel {

	//the image that loads into the icon
	private Image img1 = null;
	private Image img2 = null;
	private Image img3 = null;
	private Image img4 = null;
	private Image img5 = null;

	//This just stores the user 
	User u;

	public void refresh(User u,final CardLayout c,final JPanel par) {
		removeAll();
		this.u = u;
		//set the user to the  
		setLayout(new BorderLayout(0, 0));

		JPanel panel_4 = new JPanel();
		add(panel_4, BorderLayout.NORTH);

		JLabel lblAchievements = new JLabel("Achievements");
		lblAchievements.setFont(new Font("STSong", Font.PLAIN, 18));
		panel_4.add(lblAchievements);

		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
		//the label to return to main menu
		JButton Return_To_Main = new JButton("Return to Main Menu");
		Return_To_Main.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}
		});

		//adding the Button
		panel.add(Return_To_Main);

		//creating the Main panel
		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));

		//creating all the labels for the image
		JLabel one = Labelgen(0,"src/Ach1.png","<html><b>Beat all the levels within the Kinematics world</b><br><i>You are a model driver</i></html>");
		panel_1.add(one);

		JLabel two = Labelgen(1,"src/Ach2.jpg","<html><b>Beat all the levels within the Projectile world</b><br><i>By this rate, Newton will have <br>the Universe figure it out already</i></html>");
		panel_1.add(two);

		JLabel three = Labelgen(2,"src/Ach3.jpg","<html><b>Off by the target within a meter</b><br><i>So close, yet so far</i></html>");
		panel_1.add(three);

		JLabel four = Labelgen(3,"src/Ach4.jpg","<html><b>Drive the car 1 km away</b><br><i>Forget about physic, I am going on a road trip</i></html>");
		panel_1.add(four);

		JLabel five = Labelgen(4,"src/Ach5.jpg","<html><b>Launch the apple 1 km away</b><br><i>Nice aim, but only in ideal land</i></html>");
		panel_1.add(five);

		//adding action to the Button
		Return_To_Main.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
				//display the main menu
				c.show(par, "MainMenu");
			}
		});
	}

	//default constructor
	public Achievement() {
		// TODO Auto-generated constructor stub
	}

	//this method is use to generate the different image labels
	public JLabel Labelgen(int i, String path, String description){

		//check if the ach have been obtain
		try {
			
			//read all of the image from the file
			File im;
			if(u.achievement[i] == true){
				im = new File(path);
			}
			else{
				im = new File("src/Ach6.jpg");
			}
			
			img1 = ImageIO.read(im);

			img1 = img1.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH);  
			
			//System.out.println("Hello");

		} catch (IOException e) {
			//eclipse alto generated
			e.printStackTrace();
		}

		//creating the JLabel
		String des;
		if(u.achievement[i] == true){
			des = description;
		}
		
		else{
			des = description.split("<br>")[0];
		}
		
		JLabel Lab = new JLabel(des, new ImageIcon(img1), JLabel.CENTER);

		//Set the position of the text, relative to the icon:
		Lab.setVerticalTextPosition(JLabel.BOTTOM);
		Lab.setHorizontalTextPosition(JLabel.CENTER);

		return Lab;

	}
}
