package panels;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Maingraphic{

	//the main JFrame
	JFrame mainf = new JFrame("ICS IdealLand V1.0");

	//the panel supporting the Mainframe
	JPanel mainp = new JPanel();

	//creating the cardlayout and the object
	CardLayout c1 = new CardLayout();
	User u;

	//creating all of the subpanels
	Kinematicfree p2 = new Kinematicfree(u,mainp, c1);
	Projectialfree p1 = new Projectialfree(u,mainp, c1);
	Kinematics kin = new Kinematics();
	Projectial proj = new Projectial(); 
	LoginPanels log = new LoginPanels(mainp,c1);
	Achievement ach = new Achievement();
	JBackgroundPanel MenuPanel = new JBackgroundPanel();
	JBackgroundPanel levelsuppan = new JBackgroundPanel();
	JPanel Kinlev = new JPanel();
	JPanel Prolev = new JPanel();

	//creating the menu bar for the program
	JMenuBar meunBar= new JMenuBar();
	JMenu options = new JMenu("Options");
	JMenuItem Logout = new JMenuItem("Logout");
	JMenuItem Return = new JMenuItem("Return to main menu");

	//creating all the component for the main menu
	JButton me_Play = new JButton("Play");
	JButton me_Ach = new JButton("Achievements");
	JButton me_Logout = new JButton("Logout");

	//the array for the list of levels
	String kinlevels[] = {"1","2","3","4","5","6","7","8","9","10"};
	String prolevels[] = {"1","2","3","4","5","6","7","8","9","10"};

	//the constant that controls the level variables 
	final int Kinlevelsnum = 5;
	final int Prolevelsnum = 3;

	public Maingraphic() {

		//housekeeping
		mainf.setVisible(true);
		mainf.setResizable(false);
		mainf.setSize(1050,450);
		mainf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//creating the menubar
		options.add(Logout);
		meunBar.add(options);

		//adding actions to the ind logout menu items
		Logout.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {

				//display the mainmenu
				c1.show(mainp, "Login");
			}
		});

		//setting the cardlayout
		mainp.setLayout(c1);

		//adding all the items to the main panel
		mainp.add("Kinefree",p2);
		mainp.add("Profree",p1);
		mainp.add("Login", log);
		mainp.add("MainMenu",MenuPanel);
		mainp.add("Ach", ach);
		mainp.add("Levelsel",levelsuppan);
		mainp.add("Kinesel",Kinlev);
		mainp.add("Prosel",Prolev);
		mainp.add("Kin",kin);
		mainp.add("Pro",proj);

		//formating the menu program
		JPanel MenuCenPan = new JPanel(); 
		MenuPanel.setLayout(new BorderLayout());
		MenuPanel.add(MenuCenPan,BorderLayout.CENTER);
		MenuCenPan.add(me_Play);
		MenuCenPan.add(me_Ach);
		MenuCenPan.add(me_Logout);
		MenuCenPan.setOpaque(false);
		Image img1 = null;

		try {
			//read all of the image from the file
			File im  = new File("src/humho.png");
			img1 = ImageIO.read(im);
			img1 = img1.getScaledInstance(500, 115,  java.awt.Image.SCALE_SMOOTH); 

		} catch (IOException e) {
			//eclipse alto generated
			e.printStackTrace();
		}


		//add the top Banners
		JLabel Logo = new JLabel( new ImageIcon(img1));
		MenuPanel.add(Logo, BorderLayout.NORTH);

		mainf.setJMenuBar(meunBar);
		mainf.add(mainp);

		//default to the login panel
		c1.show(mainp,"Login");

		//set the Panel for the general sub levels

		//reading the image from text
		Image img2 = null;
		Image img3 = null;

		try {
			//read all of the image from the file
			File im  = new File("src/KinLogo.jpg");
			img2 = ImageIO.read(im);
			img2 = img2.getScaledInstance(100, 133,  java.awt.Image.SCALE_SMOOTH); 

			//read all of the image from the file
			im  = new File("src/ProLogo.jpg");
			img3 = ImageIO.read(im);
			img3 = img3.getScaledInstance(100, 133,  java.awt.Image.SCALE_SMOOTH); 

		} catch (IOException e) {
			//eclipse alto generated
			e.printStackTrace();
		}

		JPanel levelsupanCen = new JPanel();
		levelsupanCen.setOpaque(false);
		levelsuppan.setLayout(new BorderLayout());
		levelsuppan.add(levelsupanCen, BorderLayout.SOUTH);

		JButton levelsekin = new JButton("Kinematics",new ImageIcon(img2) );
		JButton levelsepro = new JButton("Projectiles",new ImageIcon(img3));

		//Set the position of the text, relative to the icon:
		levelsekin.setVerticalTextPosition(JLabel.BOTTOM);
		levelsekin.setHorizontalTextPosition(JLabel.CENTER);
		levelsepro.setVerticalTextPosition(JLabel.BOTTOM);
		levelsepro.setHorizontalTextPosition(JLabel.CENTER);

		levelsupanCen.add(levelsekin);
		levelsupanCen.add(levelsepro);

		levelsuppan.add(new JLabel(new ImageIcon(img1)), BorderLayout.NORTH);

		//adding actions to the panels
		me_Ach.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {

				//Refresh the ach panel
				int i = u.levelpassed;
				ach.refresh(u,c1,mainp);
				ach.revalidate();
				ach.repaint();

				//display the mainmenu
				c1.show(mainp, "Ach");
			}
		});

		me_Logout.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				//display the LoginPanel
				c1.show(mainp, "Login");
			}
		});

		me_Play.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				levelsuppan.revalidate();

				//display the mainmenu
				c1.show(mainp, "Levelsel");
			}
		});

		levelsekin.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				levelsuppan.revalidate();

				//switch in to the kinlev
				checkkinpanel(u);

				//display the mainmenu
				c1.show(mainp, "Kinesel");
			}
		});

		levelsepro.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				levelsuppan.revalidate();

				//switch in to the kinlev
				checkpropanel(u);

				//display the mainmenu
				c1.show(mainp, "Prosel");
			}
		});
	}

	public void checkkinpanel(User u){
		//initial panel
		Kinlev.removeAll();

		Kinlev.setLayout(new BoxLayout(Kinlev,BoxLayout.Y_AXIS));

		Kinlev.add(new JLabel("Welcome to the world of kinematics car simlulator"));

		//adding the individual levels
		for(int i = 0; i< u.levelpassed&&i<Kinlevelsnum;i++){

			//stores the value i
			final int num = i;

			//creating a new JButton
			JButton btn = new JButton(kinlevels[i]);

			//Adding action to the panel
			btn.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {

					//update and show the kinematics panel
					upkinlev(num);
					kin.revalidate();
					c1.show(mainp, "Kin");
				}
			});

			Kinlev.add(btn);
		}

		//add the free levels
		if(u.levelpassed>Kinlevelsnum){
			//creating a new JButton
			JButton btn = new JButton("Free to play level");

			//Adding action to the panel
			btn.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					upkinfree();
					c1.show(mainp,"Kinefree");
				}
			});

			Kinlev.add(btn);
		}


		//tutorial
		JButton tutbtn = new JButton("Lessons");
		tutbtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//reading the text from the file, then display it through a screen
				try {
					BufferedReader reader = new BufferedReader(new FileReader("src/Levels/Kinless.txt" ));

					JOptionPane.showMessageDialog(null, reader.readLine(),"Lesson" , JOptionPane.INFORMATION_MESSAGE);

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (HeadlessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		Kinlev.add(tutbtn);

		JButton returnm = new JButton("Return to main menu");
		returnm.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//shows mainpanel
				c1.show(mainp, "MainMenu");
			}
		});

		Kinlev.add(returnm);

		Kinlev.revalidate();
	}

	//just updates the free panel
	void upkinfree(){
		p2.update(u);
	}

	//this updates the level
	void upkinlev(int num){

		//Varible for the levels
		double[] givens = new double[5];
		String Tut = "";
		boolean istut = true;
		int unknown = -1;
		int notg = -1;


		//read from file
		try {
			BufferedReader reader = new BufferedReader(new FileReader("src/Levels/Kin-" + kinlevels[num] +".txt" ));

			unknown = Integer.parseInt(reader.readLine());
			notg = Integer.parseInt(reader.readLine());
			//System.out.println(notg);

			//read the array from the list
			for(int i = 0;i<5;i++){

				String output = reader.readLine();
				//System.out.println(output);

				//if it's unknown
				if(output.equals("u")){
					givens[i] = Double.MAX_VALUE;
				}

				else if(output.equals("z")){
					givens[i] = 0;
				}

				else{
					givens[i] = Double.parseDouble(output);
				}
			}


			//load the remaining variable 
			istut = Boolean.parseBoolean(reader.readLine());

			if(istut){
				Tut = reader.readLine();
			}

			kin.update(Kinlevelsnum,num,givens, unknown, notg, istut, Tut, u, mainp, c1);


		} catch ( NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void checkpropanel(User u){
		//initial panel
		Prolev.removeAll();

		Prolev.setLayout(new BoxLayout(Prolev,BoxLayout.Y_AXIS));

		Prolev.add(new JLabel("Welcome to the world of projectile apply throw"));

		//adding the individual levels
		for(int i = 0; i< u.levelpassed-Kinlevelsnum&&i<Prolevelsnum;i++){

			//stores the value i
			final int num = i;

			//creating a new JButton
			JButton btn = new JButton(prolevels[i]);

			//Adding action to the panel
			btn.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {

					//update and show the kinematics panel
					upprolev(num);
					proj.revalidate();
					c1.show(mainp, "Pro");
				}
			});

			Prolev.add(btn);
		}

		//add the free levels
		if(u.levelpassed>Kinlevelsnum+Prolevelsnum){
			//creating a new JButton
			JButton btn = new JButton("Free to play level");

			//Adding action to the panel
			btn.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					upprofree();
					c1.show(mainp,"Profree");
				}
			});

			Prolev.add(btn);
		}


		//tutorial
		JButton tutbtn = new JButton("Lessons");
		tutbtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//reading the text from the file, then display it through a screen
				try {
					BufferedReader reader = new BufferedReader(new FileReader("src/Levels/Proless.txt" ));

					JOptionPane.showMessageDialog(null, reader.readLine(),"Lesson" , JOptionPane.INFORMATION_MESSAGE);

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (HeadlessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		Prolev.add(tutbtn);

		JButton returnm = new JButton("Return to main menu");
		returnm.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//shows mainpanel
				c1.show(mainp, "MainMenu");
			}
		});

		Prolev.add(returnm);

		Prolev.revalidate();
	}

	void upprofree(){
		p1.update(u);
	};

	//this updates the level
	void upprolev(int num){

		//Varible for the levels
		double[] givens = new double[5];
		String Tut = "";
		boolean istut = true;
		int unknown = -1;


		//read from file
		try {
			BufferedReader reader = new BufferedReader(new FileReader("src/Levels/Pro-" + kinlevels[num] +".txt" ));

			unknown = Integer.parseInt(reader.readLine());
			//System.out.println(notg);

			//read the array from the list
			for(int i = 0;i<5;i++){

				String output = reader.readLine();
				//System.out.println(output);

				//if it's unknown
				//System.out.println(output);
				if(output.equals("u")){
					givens[i] = Double.MAX_VALUE;
				}

				else if(output.equals("z")){
					givens[i] = 0;
				}

				else{
					givens[i] = Double.parseDouble(output);
				}
			}


			//load the remaining variable 
			istut = Boolean.parseBoolean(reader.readLine());

			if(istut){
				Tut = reader.readLine();
			}

			proj.update(num+Kinlevelsnum,Kinlevelsnum+Prolevelsnum,givens, unknown, istut, Tut, u, mainp, c1);


		} catch ( NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//creating the new class for backgrounds, I put it here to make the rest earlier
	public class JBackgroundPanel extends JPanel {
		private BufferedImage img;

		public JBackgroundPanel() {
			// load the background image
			try {
				img = ImageIO.read(new File("src/Earth.jpg"));
			} catch(IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			// paint the background image and scale it to fill the entire space
			g.drawImage(img, 0, 0, 1050,425, this);
		}
	}



	class LoginPanels extends JPanel{


		//Creating all the component 
		private JPanel SM1 = new JPanel();
		private JComboBox User;
		private JLabel L1;
		private JButton Start1 = new JButton("Login");
		private JButton Start2 = new JButton("Or new user: Create a new Account here");
		private JButton Start3 = new JButton("Delete currently selected User");

		//the varible that can be link outside
		String username;

		//img for back
		private BufferedImage img;

		LoginPanels(final JPanel main,final CardLayout c){

			//Declaring a new ComboBox which contains all of the component
			try {
				User = new LoginCombo();
			} 

			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//init the label
			//check if the ach have been obtain
			Image img1 = null;

			try {
				//read all of the image from the file
				File im  = new File("src/humho.png");
				img1 = ImageIO.read(im);
				img1 = img1.getScaledInstance(500, 115,  java.awt.Image.SCALE_SMOOTH); 

			} catch (IOException e) {
				//eclipse alto generated
				e.printStackTrace();
			}

			L1 = new JLabel("Please select a user to continue", new ImageIcon(img1), JLabel.CENTER);
			//Set the position of the text, relative to the icon:
			L1.setVerticalTextPosition(JLabel.BOTTOM);
			L1.setHorizontalTextPosition(JLabel.CENTER);

			//adding component into the panel
			setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

			//format the top panel
			JPanel top = new JPanel();
			top.setLayout(new FlowLayout());
			top.add(Box.createVerticalStrut(200));
			top.add(L1);
			top.setOpaque(false);

			//set the login bar
			SM1.setLayout(new BoxLayout(SM1,BoxLayout.X_AXIS));
			SM1.add(User);
			SM1.add(Start1);
			SM1.setOpaque(false);
			add(top,BorderLayout.NORTH);
			add(Box.createHorizontalStrut(100));
			add(SM1,BorderLayout.CENTER);

			//format the bottom panel
			JPanel bottom = new JPanel();
			bottom.setLayout(new FlowLayout());
			bottom.add(Start2);
			bottom.add(Start3);
			bottom.setOpaque(false);
			add(bottom,BorderLayout.SOUTH);	

			//adding action to buttons
			Start1.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					try{
						//start a new user
						username = (String) User.getSelectedItem();
						u = new User(username);
					}
					catch(IOException e){
						e.printStackTrace();
					}
					c.show(main, "MainMenu");
				}
			});

			Start2.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {

					try{
						BufferedWriter FWriter = new BufferedWriter (new FileWriter ("src/User/LoginN.txt",true));

						String username = JOptionPane.showInputDialog(null, "Please enter your username: ", "Username", 1);

						//making sure the user didn't open an empty string
						if(username!=null){

							//if the user is not found in the file
							if(!checkuser(username)){

								//updating the filelist
								FWriter.newLine();
								FWriter.write(username);
								FWriter.close();


								//Adding the new account name
								u = new User(new File(("src/User/user_" + username + ".txt")), username);

								//re-make the ComboBox and re-set the user list
								SM1.removeAll();

								User = new LoginCombo();

								SM1.setLayout(new BoxLayout(SM1,BoxLayout.X_AXIS));
								SM1.add(User);
								SM1.add(Start1);

								//refresh screen
								SM1.revalidate();

								c.show(main, "MainMenu");
							}

							//if there are duplicated user name
							else{
								JOptionPane.showMessageDialog(null, "You already have a account name " + username);
							}
						}

						//close the file writer
						FWriter.close();
					}

					//complete the try-catch
					catch(IOException e){
						e.printStackTrace();
					}
				}
			});

			Start3.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {

					String deletename = (String) User.getSelectedItem();
					int n = JOptionPane.showConfirmDialog(null,"Are you sure you want to delete " + deletename, null,JOptionPane.YES_NO_OPTION);

					//if the user choice yes
					if (n == 0){
						try {

							//delete the element from the Vector, Then re-write it back to file
							BufferedReader LoginRead = new BufferedReader(new FileReader("src/User/LoginN.txt"));
							Vector<String> Logins = new Vector<String>();

							//This writes it in to a temporary Vertor Login
							String input;

							while((input = LoginRead.readLine()) != null){
								Logins.add(input);
							}

							LoginRead.close();

							//Check if it have been remove
							boolean isremove= Logins.remove(deletename);

							if(isremove){

								//writes back to the file 
								BufferedWriter FWriter = new BufferedWriter (new FileWriter ("src/User/LoginN.txt"));
								for(int i =0; i < Logins.size()-1;i++){
									FWriter.write((String) Logins.get(i));
									FWriter.newLine();
								}

								if(!(Logins.size()==0)){
									FWriter.write((String) Logins.get((Logins.size()-1)));
								}

								FWriter.close();

								//Physically delete the file
								File f = new File(deletename +".txt");
								f.delete();

								//re-make the ComboBox and re-set the user list
								SM1.removeAll();

								User = new LoginCombo();

								SM1.setLayout(new BoxLayout(SM1,BoxLayout.X_AXIS));		 
								SM1.add(User);
								SM1.add(Start1);

								//refresh screen
								SM1.revalidate();
							}

							//in case of an error
							else{
								JOptionPane.showMessageDialog(null, "An error have occur, please re-selected the name");
							}
						}

						//Complete the try - catch
						catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			});

			// load the background image
			try {
				img = ImageIO.read(new File("src/Earth.jpg"));
			} catch(IOException e) {
				e.printStackTrace();
			}
		}

		//this checks if the name is in the file (Since it have been used twice)
		private boolean checkuser(String name){
			try {
				//set up the variable 
				BufferedReader LoginRead = new BufferedReader(new FileReader("src/User/LoginN.txt"));
				String input;

				//search if there any user
				while((input = LoginRead.readLine()) != null){

					//if a user is found
					if(input.equals(name)){
						return true;
					}
				}
			} 

			//Complete the try-choice
			catch (IOException e) {
				e.printStackTrace();
			}

			return false;
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			// paint the background image and scale it to fill the entire space
			g.drawImage(img, 0, 0, 1050,425, this);
		}



	}
	//This generate a new ComboList for the start menu (Since it have been used twice)
	private static class LoginCombo extends JComboBox{

		//the method return a Vector that stores 
		static Vector<String> getitem() throws IOException{
			BufferedReader LoginRead = new BufferedReader(new FileReader("src/User/LoginN.txt"));


			//Using a Vector to stores all of the user names
			Vector<String> Logins = new Vector<String>();

			//reading 
			String inputuser;

			while((inputuser = LoginRead.readLine()) != null){
				Logins.add(inputuser);
			}

			//sort the username
			Collections.sort(Logins);

			//closing the file reader
			LoginRead.close();

			return Logins;
		}

		//creating the object
		LoginCombo() throws IOException{
			super(getitem());
			setSize(20,100);
		}
	}
}

