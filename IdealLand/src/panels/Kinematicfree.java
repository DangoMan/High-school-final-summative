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
import javax.swing.SpinnerNumberModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.Timer;

public class Kinematicfree extends JPanel {

	private JPanel FinVeloPanel;

	//creating all the external variables 

	//all of the input spinners 
	private JSpinner InitVeloIn;
	private JSpinner FinVeloIn;
	private JSpinner AccIn;
	private JSpinner Timein;
	private JSpinner DisIn;


	//creating all the combobox
	JComboBox Dirin;
	JComboBox Unuse1in ;

	//the current mode that the simulation is on
	int mode = 1;


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
	double velocity = 10.0;
	double acceleration = 0;
	double time = 5.00;
	double ti = 0;
	double initv;

	//the displacement of the object
	double displacement = 0.00;

	//timer to control the object
	Timer t;
	
	User u;

	//the decimal formatter
	DecimalFormat df = new DecimalFormat ("#.##");
	
	//updates the u
	public void update(User u){
		this.u = u;
	}
	
	public Kinematicfree(User u,final JPanel p, final CardLayout c) {
		
		this.u = u;
		//System.out.println(u.achievement[3]);
		
		//setting the layout for the program
		setLayout(new BorderLayout(0, 0));

		//here is the layout for the different panels
		//note that this is all alto generated so the commenting will be limited
		//the basic structures for 
		JPanel ButtonPanel = new JPanel();
		add(ButtonPanel, BorderLayout.WEST);
		ButtonPanel.setLayout(new BoxLayout(ButtonPanel, BoxLayout.Y_AXIS));

		//label for variables
		JLabel lblVariables = new JLabel("Variables");
		lblVariables.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblVariables.setFont(new Font("Tahoma", Font.PLAIN, 16));
		ButtonPanel.add(lblVariables);

		//initial velocity
		JPanel InitVeloPanel = new JPanel();
		ButtonPanel.add(InitVeloPanel);

		JLabel InitLabInitVelocity = new JLabel("Initial Velocity");
		InitVeloPanel.add(InitLabInitVelocity);

		InitVeloIn = new JSpinner();
		InitVeloIn.setModel(m1);

		//format the spinner
		InitVeloIn = format(InitVeloIn);
		InitVeloPanel.add(InitVeloIn);

		JLabel lblMs = new JLabel("m/s");
		InitVeloPanel.add(lblMs);

		FinVeloPanel = new JPanel();
		ButtonPanel.add(FinVeloPanel);

		JLabel lblFinalVelocity = new JLabel("Final Velocity");
		FinVeloPanel.add(lblFinalVelocity);

		FinVeloIn = new JSpinner();
		FinVeloIn.setModel(m2);


		//format the spinner
		FinVeloIn = format(FinVeloIn);
		FinVeloIn.setEnabled(false);
		FinVeloPanel.add(FinVeloIn);

		JLabel lblMs_1 = new JLabel("m/s");
		FinVeloPanel.add(lblMs_1);

		JPanel Acceleration = new JPanel();
		ButtonPanel.add(Acceleration);

		JLabel lblAcceleration = new JLabel("Acceleration");
		Acceleration.add(lblAcceleration);

		AccIn = new JSpinner();
		AccIn.setModel(m3);

		//format the spinner
		AccIn = format(AccIn);
		Acceleration.add(AccIn);

		JLabel lblMs_2 = new JLabel("m/s^2");
		Acceleration.add(lblMs_2);

		JPanel Time = new JPanel();
		ButtonPanel.add(Time);

		JLabel lblTime = new JLabel("Time");
		Time.add(lblTime);

		Timein = new JSpinner();
		Timein.setModel(m4);

		JComponent field = ((JSpinner.DefaultEditor) Timein.getEditor());
		Dimension prefSize = field.getPreferredSize();
		prefSize = new Dimension(40, prefSize.height);
		field.setPreferredSize(prefSize);

		Time.add(Timein);

		JLabel lblS = new JLabel("s");
		Time.add(lblS);

		JLabel lblBackground = new JLabel("Background");
		lblBackground.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblBackground.setAlignmentX(Component.CENTER_ALIGNMENT);
		ButtonPanel.add(lblBackground);

		JPanel DisplacementPanel = new JPanel();
		ButtonPanel.add(DisplacementPanel);

		JLabel lblDisplacement = new JLabel("Displacement");
		DisplacementPanel.add(lblDisplacement);

		DisIn = new JSpinner();
		DisIn.setModel(m5);

		//format the spinner
		DisIn = format(DisIn);
		DisplacementPanel.add(DisIn);

		JLabel lblM = new JLabel("m");
		DisplacementPanel.add(lblM);

		JPanel panel_1 = new JPanel();
		ButtonPanel.add(panel_1);


		JLabel lblUnusedVariables = new JLabel("Unused Variables ");
		panel_1.add(lblUnusedVariables);

		Unuse1in = new JComboBox();
		Unuse1in.setModel(new DefaultComboBoxModel(KinVar.values()));
		Unuse1in.setSelectedIndex(1);
		panel_1.add(Unuse1in);

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

		btnreturn = new JButton("Return to main");
		Buttonpanel.add(btnreturn);

		ButtonPanel.add(Buttonpanel);

		DrawPanel GraPanel = new DrawPanel();
		add(GraPanel, BorderLayout.CENTER);
		GraPanel.setLayout(new CardLayout(0, 0));

		//here is where all the functions of the buttons are set 
		//the function for the reset button (Resets background)
		btnReset.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {

				//this resets the backgrounds
				reset();
			}
		});

		Unuse1in.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				int selected = Unuse1in.getSelectedIndex();
				InitVeloIn.setEnabled(true);
				FinVeloIn.setEnabled(true);
				AccIn.setEnabled(true);
				Timein.setEnabled(true);

				if(selected == 0){
					InitVeloIn.setEnabled(false);
					mode = 0;
				}

				else if(selected == 1){
					FinVeloIn.setEnabled(false);
					mode = 1;
				}

				else if(selected == 2){
					AccIn.setEnabled(false);
					mode = 2;
				}

				else if(selected == 3){
					Timein.setEnabled(false);
					mode = 3;
				}
			}
		});



		//Launch button
		btnLaunch.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {

				//this resets the backgrounds
				reset();

				double vf=0;

				//initialize everything base on model
				if(mode == 0){

					//input the variable
					vf = m2.getNumber().doubleValue();
					acceleration =  m3.getNumber().doubleValue();
					time = m4.getNumber().doubleValue();

					//update the values 
					velocity = vf - acceleration*time;
					m1.setValue(velocity);

					//Debugging
					////System.out.println("Hello");
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
				
				ti = time;
				
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
					JOptionPane.showMessageDialog(null, "You stop at the stop sign\nPlease reset for another Demo","Launch Result" , JOptionPane.INFORMATION_MESSAGE);
				}

				else{
					double daway = Math.abs(displacement - actdis);
					JOptionPane.showMessageDialog(null, "You are " + df.format(daway)+" meters away from the stop sign\nPlease reset for another Demo","Launch Result" , JOptionPane.INFORMATION_MESSAGE);
				}
				
				//ach
				if(u.achievement[3] == false && displacement >=1000){
					u.achievement[3] = true;
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

	};

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
			g.drawImage(Background, 0, 0,700,400, this);

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
