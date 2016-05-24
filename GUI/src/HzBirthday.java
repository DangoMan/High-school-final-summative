import javax.swing.*;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.awt.*;


class Birthday extends JFrame{

	public JTextArea ta;
	public String str[] = new String[1];
	public JPanel p1 = new JPanel();


	void init() throws InterruptedException{
		
		str[0]="Happy Birthday Hz ";
		ta = new JTextArea(str[0]);
		
		//setstype  
		Font f = new Font("Courier", Font.PLAIN, 36);
		Color c = new Color(0,160,255);
		ta.setFont(f);
		ta.setBackground(c);

		//adding all of the component
		add(ta);

		//HouseKeeping
		setVisible(true);
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		
		for(;;){
			Thread.sleep(1000);
			newString();
		}

	}

	void newString(){
		ta.setText(str[0]);
		str[0] = filpchar(str[0]);
		
	}
	
	private String filpchar(String str) {
		String[] ch = new String[str.length()];
		for(int i = 0; i<str.length(); i++) {
			Character cha = str.charAt(i);
			ch[i] =  Character.toString(cha);
		}
		String str2 = ch[ch.length-1];
		for(int i = 1; i<str.length(); i++) {
			str2 = str2 + ch[i-1];
		}
		return str2;
	}
}


//the execution of the code
public class HzBirthday{
	//Running graphic,nothing have to say here
	public static void main(String args[]) throws InterruptedException {
		Birthday br = new Birthday();
		br.init();
	}
}