package panels;

import javax.imageio.*;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.DecimalFormat;


public class Projectial extends JPanel {

	//Creating all the global component needed for the project
	private JSpinner  VIin;
	private JSpinner  Anglein;
	private JSpinner  InitHighIn;
	private JSpinner  textField;

	//all the spinner models
	SpinnerNumberModel m1 = new SpinnerNumberModel(20,0,100,5);
	SpinnerNumberModel m2 = new SpinnerNumberModel(45,0,90,1); 
	SpinnerNumberModel m3 = new SpinnerNumberModel(25.00,0,35,5); 
	SpinnerNumberModel m4 = new SpinnerNumberModel(30.00,0,50,5); 
	private JPanel panel;

	JComboBox Planet = new JComboBox();

	//the graphic panel
	final DrawPanel DPanel = new DrawPanel();

	//note, 1 pixel represent a meter
	//the value controlling the apple
	int x = 25;
	int y = 87;

	//this value just record the exact location of the apple
	double actx = 0;
	double acty = 25;

	//the value controlling the target
	int deltax = 325;
	double actdeltax = 30;

	//the value controlling the initial height
	int inity = 120;

	//the movement of the ball
	double vx = 0;
	double vy = 0;
	double g = 9.81;

	//the background 0 = earth, 1= moon, 2 = mars
	int back = 0;

	//timer for animation
	Timer t;

	//this stores the time 
	double ti = 0;

	//this is used to store the temp varibles
	User u;
	JPanel p;
	CardLayout c;
	int lev;
	int levsel;

	//the decimal formatter
	DecimalFormat df = new DecimalFormat ("#.#");

	//parameter
	public void update(final int lev,final int levsel,double given[], int unknown,boolean istut,String tut,final User u,final JPanel p, final CardLayout c) {

		//init everything
		this.u = u;
		this.p = p;
		this.c = c;
		this.lev = lev;
		this.levsel = levsel;

		//sets the initial layout of the project
		setLayout(new BorderLayout());

		//here is the layout for the different panels
		//note that this is all alto generated so the commenting will be limited
		//the basic structures for 
		JPanel panel = new JPanel();
		add(panel, BorderLayout.WEST);
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

		JLabel Descriptions1 = new JLabel("Object");
		Descriptions1.setAlignmentX(Component.CENTER_ALIGNMENT);
		Descriptions1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		panel.add(Descriptions1);

		JPanel VI = new JPanel();
		panel.add(VI);
		VI.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel VILable = new JLabel("Initial Velocity (m/s)");
		VI.add(VILable);

		VIin = new JSpinner ();
		VI.add(VIin);
		VIin.setModel(m1);

		//if it's not a given, set the variable 
		if(unknown!=0){
			m1.setValue(given[0]);
			VIin.setEnabled(false);;
		}

		JPanel Angle = new JPanel();
		panel.add(Angle);

		JLabel AngleLable = new JLabel("Angle (0-90)");
		Angle.add(AngleLable);

		Anglein = new JSpinner();
		Angle.add(Anglein);
		Anglein.setModel(m2);

		//if it's not a given, set the variable 
		if(unknown!=1){
			m2.setValue(given[1]);
			Anglein.setEnabled(false);
		}


		JLabel lblNewLabel = new JLabel("Background");
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		panel.add(lblNewLabel);

		JPanel Location = new JPanel();
		panel.add(Location);

		JLabel LocationLable = new JLabel("Planet");
		Location.add(LocationLable);

		//creating the combobox for the location
		String[] planetoption = {"Earth (9.81m/s)", "Moon(1.62 m/s)", "Mars(3.61 m/s)"};

		Planet = new JComboBox(planetoption);

		//set the location to a fix location
		Planet.setEnabled(false);
		Planet.setSelectedIndex((int)given[2]);

		Location.add(Planet);


		JPanel InitHigh = new JPanel();
		panel.add(InitHigh);

		JLabel InitHighLable = new JLabel("Initial High (m/Max 35 m)");
		InitHigh.add(InitHighLable);

		InitHighIn = new JSpinner (m3);



		//format the spinner
		InitHighIn = format(InitHighIn);

		//if it's not a given, set the variable 
		if(unknown != 3){
			InitHighIn.setEnabled(false);
			m3.setValue(given[3]);
		}

		InitHigh.add(InitHighIn);

		//the component
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);

		JLabel lblNewLabel_1 = new JLabel("Displacement x-dim (M/ Max 50)");
		panel_1.add(lblNewLabel_1);

		textField = new JSpinner ();
		textField.setModel(m4);

		//if it's not a given, set the variable 
		if(unknown != 4){
			textField.setEnabled(false);
			m4.setValue(given[4]);
		}

		//format the spinner
		textField = format(textField);

		panel_1.add(textField);

		//the bottom panel for all the buttons
		panel_1.add(textField);

		JPanel ButtonPanel = new JPanel();
		panel.add(ButtonPanel);

		JButton btnResetBackground = new JButton("Reset Background");
		ButtonPanel.add(btnResetBackground);

		JButton btnNewButton = new JButton("Launch");
		ButtonPanel.add(btnNewButton);


		JPanel ButtonPanel1 = new JPanel();
		panel.add(ButtonPanel1);

		JButton btnTutorial = new JButton("Tutorial");
		ButtonPanel1.add(btnTutorial);

		JButton btnreturnmain = new JButton("Return to Main Menu");
		ButtonPanel1.add(btnreturnmain);

		//adding the graphic panel
		add(DPanel, BorderLayout.CENTER);
		DPanel.setOpaque(false);

		reset();

		btnResetBackground.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				reset();
			}
		});

		btnreturnmain.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {

				//show the confirm message
				int exit = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?","",JOptionPane.YES_NO_OPTION);

				if(exit == 0){
					c.show(p, "MainMenu");
				}
			}
		});

		//the action for animate
		final ActionListener Launch = new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {

				//repaint the panel
				DPanel.repaint();

				//calculating the rate of the motion
				int rate = 250;

				//recalculate the dimensions 
				actx += vx/rate;
				acty += vy/rate;

				x = (int) Math.round(actx*10 +25);
				y = (int) Math.round(370 - acty*10-33);

				vy -= g/rate;

				if(acty<0){
					acty=0;

					//calculate the accurate actx
					actx = ti * vx;

					//stop timer and count score
					repaint();
					t.stop();

					if(df.format(actx).equalsIgnoreCase(df.format(actdeltax))){
						JOptionPane.showMessageDialog(null, "You hit sir Issac Newton in the head","Launch Result" , JOptionPane.INFORMATION_MESSAGE);
						
						System.out.println(lev+1);
						if(u.levelpassed  == lev+1){
							u.levelpassed++;
							try {
								u.update();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

						//ach
						if(u.achievement[1]  == false && u.levelpassed == levsel+1){
							u.achievement[1] = true;
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

					else{
						double daway = Math.abs(actx - actdeltax);
						JOptionPane.showMessageDialog(null, "You missed sir Issac Newton by " + daway + " meters","Launch Result" , JOptionPane.INFORMATION_MESSAGE);
						//ach
						if(u.achievement[2]  == false && Math.abs(daway)<0.1){
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


		btnNewButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {

				//resets the panel
				reset();

				//stop the timer
				if(t != null){
					if(t.isRunning()){
						t.stop();
					}
				}

				double velocity = m1.getNumber().doubleValue();
				double angle = m2.getNumber().doubleValue();

				//input the initial V of the apple
				vx = velocity * Math.cos(angle * (Math.PI/180));
				vy = velocity * Math.sin(angle * (Math.PI/180));

				//System.out.println(vx);
				//calculating time
				ti = (-vy + Math.sqrt(vy*vy + 2*g*m3.getNumber().doubleValue()))/(-g);

				if(ti <= 0){
					ti = (-vy - Math.sqrt(vy*vy + 2*g*m3.getNumber().doubleValue()))/(-g);
				}

				//System.out.println( Math.sqrt(2*g*inity)/(-g));

				DPanel.repaint();

				t = new Timer (1,Launch);
				t.start();
			}		

		});
	}

	public Projectial() {
		// TODO Auto-generated constructor stub
	}

	//method to reset the Background
	public void reset(){
		//stop the timer
		if(t != null){
			if(t.isRunning()){
				t.stop();
			}
		}

		//input the initial height of the apple (Display on screen)
		inity = (int) Math.round( (37 -(m3.getNumber().doubleValue())) *10);
		y = inity - 33;
		x = 25;

		//input the actual value
		actx = 0;
		acty = m3.getNumber().doubleValue() ;

		//input the initial distance of the object
		deltax = (int) Math.round(m4.getNumber().doubleValue()) *10 + 25;
		actdeltax = m4.getNumber().doubleValue();

		//get the background Planet selected
		String planet = String.valueOf(Planet.getSelectedItem());

		if(planet.equals("Earth (9.81m/s)")){
			back = 0;
			g = 9.81;
		}

		else if(planet.equals("Moon(1.62 m/s)")){
			back = 1;
			g = 1.62;
		}

		else if(planet.equals("Mars(3.61 m/s)")){
			back = 2;
			g = 3.61;
		}


		DPanel.repaint();
	}
	/**
	 * Create the panel.
	 * @param  
	 */

	class DrawPanel extends JPanel{

		//creating the initial varible
		private Image Apple = null;
		private Image BackgroundE = null;
		private Image Target = null;
		private Image BackgroundM = null;
		private Image BackgroundMa = null;


		public DrawPanel(){
			try {
				//read all of the image from the file
				File img = new File("src/Apple.png");
				Apple = ImageIO.read(img);

				img  = new File("src/newton.png");
				Target = ImageIO.read(img);

				img = new File("src/Earth.jpg");
				BackgroundE = ImageIO.read(img);

				img  = new File("src/Mars.jpg");
				BackgroundMa = ImageIO.read(img);

				img  = new File("src/Moon.jpg");
				BackgroundM = ImageIO.read(img);


			} catch (IOException e) {
				//eclipse alto generated
				e.printStackTrace();
			}
		}

		public void paint(Graphics g){

			switch (back){
			case 0:
				g.drawImage(BackgroundE, 0, 0,1000,400, this);
				break;
			case 1:
				g.drawImage(BackgroundM, 0, 0,1000,400, this);
				break;
			case 2:
				g.drawImage(BackgroundMa, 0, 0,1000,400, this);
				break;
			}

			g.setColor(Color.GREEN);
			g.fillRect(0, inity+25, 50, 10);
			g.drawImage(Apple, x, y+25,25,33, this);
			g.drawImage(Target,deltax-10,319+25,50,50,this);

			//drawing the dimensions up on the board
			g.setColor(Color.white);
			g.fillRect(750,y+25,50, 20);
			g.fillRect(x+25,0,50, 20);

			//draw the graphic
			g.setColor(Color.BLACK);
			g.drawString(df.format(acty) + " M", 750, y + 40);
			//System.out.println(acty);
			g.drawString(df.format(actx) + " M", x+25, 15);



		}
	}

	//format the JSpinner
	public static JSpinner format(JSpinner sp){

		//edit the decimals
		JSpinner.NumberEditor editor = (JSpinner.NumberEditor)sp.getEditor(); 
		editor.getFormat().setMinimumFractionDigits(1);


		JComponent field = ((JSpinner.DefaultEditor) sp.getEditor());
		Dimension prefSize = field.getPreferredSize();
		prefSize = new Dimension(30, prefSize.height);
		field.setPreferredSize(prefSize);

		return sp;
	}
}