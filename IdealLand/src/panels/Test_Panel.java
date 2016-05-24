package panels;
import java.awt.BorderLayout;

import javax.swing.*;
public class Test_Panel extends JFrame{
	double list[] = {0, 0,0,30,Integer.MAX_VALUE};
	int unknown = 4;
	User u;
	Projectialfree k = new Projectialfree();
	
	public Test_Panel() {

		//housekeeping
		super("ICS IdealLand V0.5");
		setVisible(true);
		setResizable(false);
		setSize(1050,425);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		add(k);
		
	}
	
}
