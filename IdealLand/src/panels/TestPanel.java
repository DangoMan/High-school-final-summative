package panels;

import javax.swing.JApplet;
import javax.swing.JFrame;

public class TestPanel extends JFrame {

	Projectial p = new Projectial();

	public TestPanel() {
		
		//housekeeping
		super("ICS IdealLand V0.1");
		setVisible(true);
		setResizable(false);
		setSize(830,400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		

		//housekeeping 
		add(p);
	}



}
