package panels;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JLabel;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.JSpinner;

import java.awt.Font;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.Timer;

public class Kinematics  extends JPanel{


	//creating all the external variables 

	//all of the input spinners 
	private JSpinner InitVeloIn;
	private JSpinner FinVeloIn;
	private JSpinner AccIn;
	private JSpinner Timein;
	private JSpinner DisIn;

	//all of the spinner models 
	//m1 = init velocity, m2 = final velocity, m3 = acceleration, m4 = time, m5 = displacement
	private SpinnerNumberModel m1 = new SpinnerNumberModel(15.0, 0.0, 40.0, 5.0);
	private SpinnerNumberModel m2 = new SpinnerNumberModel(30.0, 0.0, 40.0, 5.0);
	private SpinnerNumberModel m3 = new SpinnerNumberModel(0.0, -10.0, 30.0, 1.0);
	private SpinnerNumberModel m4 = new SpinnerNumberModel(5.0, 0.0, 50, 1.0);
	private SpinnerNumberModel m5 = new SpinnerNumberModel(30.0, -30.0, 250.0, 5.0);

	//a button that is connected to outside
	public JButton btnreturn;

	//the dimensions for the simulations
	//note, 1 pixel represent 2 meter
	//the value controlling the apple
	int x = 60;
	int y = 300;

	//the actual demonstion for the demo
	double actdis = 30.0;

	//storing all the variable of the object
	double velocity;
	double acceleration;
	double time;
	double ti;
	double initv;

	//the displacement of the object
	double displacement = 0.00;

	//timer to control the object
	Timer t;

	//controls the variable input for the level
	double given[];
	int mode;
	int unknown;
	int num;
	int numlev;
	int lev;
	User u;
	CardLayout c;
	JPanel p;


	//the decimal formatter
	DecimalFormat df = new DecimalFormat ("#.#");
	
	//default constructors
	public Kinematics() {
		// TODO Auto-generated constructor stub
	}

	//for the unknown and notg (not given), 0 = Vi, 1 = Vf, 2
	public void update(int numlev, int num, double given[], int unknown, int notg,boolean istut,final String tut,User u,final JPanel p, final CardLayout c) {
		removeAll();

		//init all the variables
		this.given = given;
		this.mode = notg;
		this.unknown = unknown;
		this.u =u ;
		this.c = c;
		this.p = p;
		this.numlev = numlev;
		this.num = num;

		//setting the layout for the program
		setLayout(new BorderLayout(0, 0));

		//here is the layout for the different panels
		//note that this is all alto generated so the commenting will be limited
		//the basic structures for 
		JPanel ButtonPanel = new JPanel();
		add(ButtonPanel, BorderLayout.WEST);
		ButtonPanel.setLayout(new BoxLayout(ButtonPanel, BoxLayout.Y_AXIS));

		JLabel lblVariables = new JLabel("Variables");
		lblVariables.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblVariables.setFont(new Font("Tahoma", Font.PLAIN, 16));
		ButtonPanel.add(lblVariables);

		//init velocity 
		JPanel InitVeloPanel = new JPanel();

		//add it if it's a given
		if(notg != 0){
			ButtonPanel.add(InitVeloPanel);
		}


		JLabel InitLabInitVelocity = new JLabel("Initial Velocity");
		InitVeloPanel.add(InitLabInitVelocity);

		InitVeloIn = new JSpinner();
		InitVeloIn.setModel(m1);

		//format the spinner
		InitVeloIn = format(InitVeloIn);

		//check if it's the known variable
		if(unknown != 0){
			InitVeloIn.setEnabled(false);
			m1.setValue(given[0]);
		}

		InitVeloPanel.add(InitVeloIn);

		JLabel lblMs = new JLabel("m/s");

		InitVeloPanel.add(lblMs);

		//final velocity 
		JPanel FinVeloPanel = new JPanel();
		//add it if it's a given
		if(notg != 1){
			ButtonPanel.add(FinVeloPanel);
		}

		JLabel lblFinalVelocity = new JLabel("Final Velocity");
		FinVeloPanel.add(lblFinalVelocity);

		FinVeloIn = new JSpinner();
		FinVeloIn.setModel(m2);

		//format the spinner
		FinVeloIn = format(FinVeloIn);

		//check if it's the known variable
		if(unknown != 1){
			FinVeloIn.setEnabled(false);
			m2.setValue(given[1]);
		}

		FinVeloPanel.add(FinVeloIn);

		JLabel lblMs_1 = new JLabel("m/s");
		FinVeloPanel.add(lblMs_1);

		//acceleration
		JPanel Acceleration = new JPanel();

		//add it if it's a given
		if(notg != 2){
			ButtonPanel.add(Acceleration);
		}

		JLabel lblAcceleration = new JLabel("Acceleration");
		Acceleration.add(lblAcceleration);

		AccIn = new JSpinner();
		AccIn.setModel(m3);

		//format the spinner
		AccIn = format(AccIn);

		//check if it's the known variable
		if(unknown != 2){
			AccIn.setEnabled(false);
			m3.setValue(given[2]);
		}

		Acceleration.add(AccIn);

		JLabel lblMs_2 = new JLabel("m/s^2");
		Acceleration.add(lblMs_2);

		//Time
		JPanel Time = new JPanel();

		//add it if it's a given
		if(notg != 3){
			ButtonPanel.add(Time);
		}


		JLabel lblTime = new JLabel("Time");
		Time.add(lblTime);

		Timein = new JSpinner();
		Timein.setModel(m4);

		JComponent field = ((JSpinner.DefaultEditor) Timein.getEditor());
		Dimension prefSize = field.getPreferredSize();
		prefSize = new Dimension(40, prefSize.height);
		field.setPreferredSize(prefSize);

		//check if it's the known variable
		if(unknown != 3){
			Timein.setEnabled(false);
			m4.setValue(given[3]);
		}

		Time.add(Timein);

		JLabel lblS = new JLabel("s");
		Time.add(lblS);

		JLabel lblBackground = new JLabel("Background");
		lblBackground.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblBackground.setAlignmentX(Component.CENTER_ALIGNMENT);
		ButtonPanel.add(lblBackground);

		//the location of the stop sign
		JPanel DisplacementPanel = new JPanel();
		ButtonPanel.add(DisplacementPanel);

		JLabel lblDisplacement = new JLabel("Displacement");
		DisplacementPanel.add(lblDisplacement);

		DisIn = new JSpinner();
		DisIn.setModel(m5);

		//format the spinner
		DisIn = format(DisIn);
		DisplacementPanel.add(DisIn);

		//check if it's the known variable
		if(unknown != 4){
			DisIn.setEnabled(false);
			m5.setValue(given[4]);
		}

		JLabel lblM = new JLabel("m");
		DisplacementPanel.add(lblM);

		JPanel panel_3 = new JPanel();
		ButtonPanel.add(panel_3);

		JPanel Buttonpanel = new JPanel();
		ButtonPanel.add(Buttonpanel);

		Buttonpanel= new JPanel();
		//Buttonpanel.setLayout(new BoxLayout(Buttonpanel, BoxLayout.X_AXIS));

		JButton btnReset = new JButton("Resets Background");
		Buttonpanel.add(btnReset);

		JButton btnLaunch = new JButton("Launch");
		Buttonpanel.add(btnLaunch);

		ButtonPanel.add(Buttonpanel);

		JPanel Buttonpanel2 = new JPanel();
		btnreturn = new JButton("Return to main");
		Buttonpanel2.add(btnreturn);

		JButton btntut = new JButton("Tutorials");
		if(istut = true){
			Buttonpanel2.add(btntut);
		}

		ButtonPanel.add(Buttonpanel2);

		//reset the background
		repaint();


		DrawPanel GraPanel = new DrawPanel();
		add(GraPanel, BorderLayout.CENTER);

		//reset the Panel before put it in action
		reset();

		//here is where all the functions of the buttons are set 
		//the function for the reset button (Resets background)
		btntut.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				//this resets the backgrounds
				JOptionPane.showMessageDialog(null, tut,"hints" , JOptionPane.INFORMATION_MESSAGE);
			}
		});

		btnReset.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {

				//this resets the backgrounds
				reset();
			}
		});


		//Launch button
		btnLaunch.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {

				//this resets the backgrounds
				reset();

				double vf=0;

				//initialize everything base on model
				//if initial velocity is not given
				if(mode == 0){

					//input the variable
					vf = m2.getNumber().doubleValue();
					acceleration =  m3.getNumber().doubleValue();
					time = m4.getNumber().doubleValue();

					//update the values 
					velocity = vf - acceleration*time;

					//Debugging
					//System.out.println(time);
					//InitVeloIn.setValue("11");
				}

				//initialize everything base on model
				else if(mode == 1){
					//input the variable
					velocity = m1.getNumber().doubleValue();
					acceleration =  m3.getNumber().doubleValue();
					time = m4.getNumber().doubleValue();

					//update the values 
					vf = velocity + time*acceleration;
					m2.setValue(vf);
				}

				else if(mode == 2){

					//input the variable
					velocity = m1.getNumber().doubleValue();
					vf = m2.getNumber().doubleValue();
					time = m4.getNumber().doubleValue();

					//update the values 
					acceleration = (vf - velocity)/time;
					m3.setValue(acceleration);
				}

				else if(mode == 3){

					//input the variable
					velocity = m1.getNumber().doubleValue();
					vf = m2.getNumber().doubleValue();
					acceleration = m3.getNumber().doubleValue();
					//update the values 
					time = (vf - velocity)/acceleration;
					m4.setValue(time);
				}
				ti = time;
				initv = velocity;


				//update the current panel
				repaint();

				t = new Timer (1,Launch);
				t.start();
			}
		});

		//the Button to return to main
		btnreturn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {

				//show the confirm message
				int exit = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?","",JOptionPane.YES_NO_OPTION);

				if(exit == 0){
					c.show(p, "MainMenu");
				}
			}
		});
	}

	//method to reset the Background
	public void reset(){
		//stop the timer
		if(t != null){
			if(t.isRunning()){
				t.stop();
			}
		}


		//reset the variable to the one that user enter
		actdis = m5.getNumber().doubleValue();

		//resets the x and the y variables 
		x = 60;
		y = 300;

		//reset displacement
		displacement = 0;
		time = 0;


		//repaints the background
		repaint();

	}

	//the action for animate
	final ActionListener Launch = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {

			//calculating the rate of the motion
			int rate = 10;

			time = time - 0.1;
			//System.out.println(time);

			if(!(time<0)){

				//repaint the panel
				repaint();

				//recalculate the dimensions 
				displacement += velocity/rate;

				x = (int) Math.round(displacement*2)+60;

				velocity += acceleration/rate;
				//System.out.println(time);
			}


			else{
				t.stop();

				//recalculate base on givens
				velocity = initv + acceleration*ti;
				displacement = velocity * ti - acceleration * ti*ti*0.5;
				//System.out.println(velocity);
				int temp = (int) Math.round(displacement*100);
				displacement = (double) temp/100;

				x = (int) Math.round(displacement*2)+60;

				//update the panel
				repaint();

				////System.out.println("Hello");

				//display messages for the user
				if(df.format(displacement).equalsIgnoreCase(df.format(actdis))){
					JOptionPane.showMessageDialog(null, "You stop at the stop sign","Launch Result" , JOptionPane.INFORMATION_MESSAGE);
					//System.out.println(num);
					//updates the users account
					if(u.levelpassed  == num+1){
						u.levelpassed++;
						try {
							u.update();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					//ach
					if(u.achievement[0]  == false && u.levelpassed == numlev+1){
						u.achievement[0] = true;
						JOptionPane.showMessageDialog(null, "You got an achievement, go check it out.","Launch Result" , JOptionPane.INFORMATION_MESSAGE);
						
						try {
							u.update();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					
					//show the confirm message to return to main
					int exit = JOptionPane.showConfirmDialog(null, "Do you want to return to main menu?","",JOptionPane.YES_NO_OPTION);

					if(exit == 0){
						c.show(p, "MainMenu");
					}
					
					
				}
				
				
				//if fails
				else{
					JOptionPane.showMessageDialog(null, "You missed your apple","Launch Result" , JOptionPane.INFORMATION_MESSAGE);
					
					//ach
					if(u.achievement[2]  == false && Math.abs(displacement- actdis)<0.1){
						u.achievement[2] = true;
						JOptionPane.showMessageDialog(null, "You got an achievement, go check it out.","Launch Result" , JOptionPane.INFORMATION_MESSAGE);
						
						try {
							u.update();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}

	};


	class DrawPanel extends JPanel{

		//creating the initial varible
		private Image Car = null;
		private Image Background = null;
		private Image StopSign = null;


		public DrawPanel(){
			try {
				//read all of the image from the file
				File img = new File("src/Car.png");
				Car = ImageIO.read(img);

				img = new File("src/Background.jpg");
				Background = ImageIO.read(img);

				img = new File("src/stop_sign.png");
				StopSign = ImageIO.read(img);

			} catch (IOException e) {
				//eclipse alto generated
				e.printStackTrace();
			}
		}


		public void paint(Graphics g){

			//drawing the background up on the board
			g.drawImage(Background, 0, 0,1000,400, this);

			//drawing the dimensions up on the board
			g.drawImage(Car, x, y,75,24, this);
			g.drawImage(StopSign, (int) (actdis*2) + 100, 225,75,75, this);

			//drawing the dimensions up on the board
			if(x <585){
				g.setColor(Color.white);
				g.fillRect(x+25,0,50, 20);

				g.setColor(Color.BLACK);
				g.drawString(df.format(displacement) + " M", x+25, 15);
			}
			else{
				g.setColor(Color.white);
				g.fillRect(585+25,0,50, 20);

				g.setColor(Color.BLACK);
				g.drawString(df.format(displacement) + " M", 585+25, 15);
			}

		}
	}


	//format the JSpinner
	public static JSpinner format(JSpinner sp){

		//edit the decimals
		JSpinner.NumberEditor editor = (JSpinner.NumberEditor)sp.getEditor(); 
		editor.getFormat().setMinimumFractionDigits(2);


		JComponent field = ((JSpinner.DefaultEditor) sp.getEditor());
		Dimension prefSize = field.getPreferredSize();
		prefSize = new Dimension(40, prefSize.height);
		field.setPreferredSize(prefSize);

		return sp;
	}
}
