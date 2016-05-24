package panels;

import javax.imageio.*;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.DecimalFormat;


public class Projectialfree extends JPanel {

	//Creating all the global component needed for the project
	private JSpinner  VIin;
	private JSpinner  Anglein;
	private JSpinner  InitHighIn;
	private JSpinner  textField;

	//all the spinner models
	//m1 = init velociry, m2 = angle, m3 = initial high, m4 = displacement
	SpinnerNumberModel m1 = new SpinnerNumberModel(20,0,100,5);
	SpinnerNumberModel m2 = new SpinnerNumberModel(45,0,90,1); 
	SpinnerNumberModel m3 = new SpinnerNumberModel(25.00,0,35,5); 
	SpinnerNumberModel m4 = new SpinnerNumberModel(30.00,0,65,5); 
	private JPanel panel;

	//a button that is connected to outside
	public JButton btnreturn;


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

	//calculating the actual time
	double ti = 0;

	//the decimal formatter
	DecimalFormat df = new DecimalFormat ("#.#");

	//user 
	User u;

	public void update(User u){
		this.u = u;
	}
	//parameter
	public Projectialfree(User us,final JPanel p, final CardLayout c) {
		this.u = us;
		//sets the initial layout of the project
		setLayout(new BorderLayout(0, 0));

		//here is the layout for the different panels
		//note that this is all alto generated so the commenting will be limited
		//the basic structures for the main panel
		JPanel panel = new JPanel();
		add(panel, BorderLayout.WEST);
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

		JLabel Descriptions1 = new JLabel("Object");
		Descriptions1.setAlignmentX(Component.CENTER_ALIGNMENT);
		Descriptions1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		panel.add(Descriptions1);

		JPanel VI = new JPanel();
		panel.add(VI);

		JLabel VILable = new JLabel("Initial Velocity (m/s)");
		VI.add(VILable);

		VIin = new JSpinner ();
		VI.add(VIin);
		VIin.setModel(m1);

		JPanel Angle = new JPanel();
		panel.add(Angle);

		JLabel AngleLable = new JLabel("Angle (0-90)");
		Angle.add(AngleLable);

		Anglein = new JSpinner();
		Angle.add(Anglein);

		Anglein.setModel(m2);


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
		Location.add(Planet);


		JPanel InitHigh = new JPanel();
		panel.add(InitHigh);

		JLabel InitHighLable = new JLabel("Initial High (m/Max 35 m)");
		InitHigh.add(InitHighLable);

		InitHighIn = new JSpinner (m3);

		//format the spinner
		InitHighIn = format(InitHighIn);
		InitHigh.add(InitHighIn);

		//the component
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);

		JLabel lblNewLabel_1 = new JLabel("Displacement x-dim (M/ Max 50)");
		panel_1.add(lblNewLabel_1);

		textField = new JSpinner ();
		textField.setModel(m4);

		//format the spinner
		textField = format(textField);

		panel_1.add(textField);

		//the bottom panel for all the buttons
		panel_1.add(textField);

		Component verticalStrut = Box.createVerticalStrut(75);
		panel.add(verticalStrut);

		JPanel ButtonPanel = new JPanel();
		panel.add(ButtonPanel);

		JButton btnResetBackground = new JButton("Reset Background");
		ButtonPanel.add(btnResetBackground);

		JButton btnNewButton = new JButton("Launch");
		ButtonPanel.add(btnNewButton);

		btnreturn = new JButton("Return to main");
		ButtonPanel.add(btnreturn);

		//adding the graphic panel
		add(DPanel, BorderLayout.CENTER);
		DPanel.setOpaque(false);

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


		btnResetBackground.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {

				//reset the background
				reset();

				DPanel.repaint();
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

					repaint();
					t.stop();

					if(df.format(actx).equalsIgnoreCase(df.format(actdeltax))){
						JOptionPane.showMessageDialog(null, "You hit sir Issac Newton in the head\nPlease reset for another Demo","Launch Result" , JOptionPane.INFORMATION_MESSAGE);
					}

					else{
						double daway = Math.abs(actx - actdeltax);
						JOptionPane.showMessageDialog(null, "You missed sir Issac Newton by " + df.format(daway) +" meters\nPlease reset for another Demo","Launch Result" , JOptionPane.INFORMATION_MESSAGE);

						//ach
						//System.out.println(actx);
						if(u.achievement[4] == false && actx >=1000){
							u.achievement[4] = true;
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

				//reset the background
				reset();

				//init the variable 
				double velocity = m1.getNumber().doubleValue();
				double angle = m2.getNumber().doubleValue();

				//input the initial V of the apple
				vx = velocity * Math.cos(angle * (Math.PI/180));
				vy = velocity * Math.sin(angle * (Math.PI/180));

				//System.out.println(g);
				//calculating time
				ti = (-vy + Math.sqrt(vy*vy + 2*g*m3.getNumber().doubleValue()))/(-g);

				if(ti <= 0){
					ti = (-vy - Math.sqrt(vy*vy + 2*g*m3.getNumber().doubleValue()))/(-g);
				}

				//System.out.println(ti);	


				DPanel.repaint();

				t = new Timer (1,Launch);
				t.start();
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

		//input the initial height of the apple (Display on screen)
		inity = (int) Math.round( (37 -(m3.getNumber().doubleValue())) *10);
		y = inity - 33;
		x = 25;

		//resets time
		ti = 0;

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
			g.fillRect(645,y+25,50, 20);
			g.fillRect(x+25,0,50, 20);

			//draw the graphic
			g.setColor(Color.BLACK);
			g.drawString(df.format(acty) + " M", 645, y + 40);
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