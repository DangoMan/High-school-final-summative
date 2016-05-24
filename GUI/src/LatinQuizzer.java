import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

/*
 * To Vlad:
 * (Delete this later lol)
 * 
 */
public class LatinQuizzer {

	//the main frame for the program
	Frame mainf = new Frame("Latin Quizzer");

	//creating all the panels for the program
	Panel levelp = new Panel();
	Panel Meunp = new Panel();
	Panel Mainp = new Panel();
	Panel levelselect = new Panel();
	Panel Result = new Panel();
	

	//the Cardlayout
	CardLayout c = new CardLayout();

	//component for the Meunp section Vlad, add Buttons and components here
	Button play = new Button("Start Quiz");
	Button tut = new Button("Tutorial");

	//the list of levels Buttons
	Button L1 = new Button("Level 1 Quiz");
	Button L2 = new Button("Level 2 Quiz");

	//the component for the Results
	Label Resultl = new Label();
	Button Ret = new Button("Return to main");

	//Component for the levelp section 
	Label questionsrigth = new Label("Hello");
	Label questions = new Label("Q");
	Panel Buttonp = new Panel();
	Panel Button1 = new Panel();
	Panel Button2 = new Panel();

	//creating the buttons for selection
	Button answers [] = new Button[4];

	Button returnmain = new Button("Return to main Meun");

	//this reads the file for the program
	BufferedReader br;
	
	//component for the tutorial 

	//the variable of the questions
	int rightans;
	int questionnum;
	int totalquestions;
	String question;
	String anschoice[] = new String[4];
	int answer;



	public void init() throws FileNotFoundException{

		//this sets the size of the program
		mainf.setSize(300,200);
		mainf.setResizable(false);
		mainf.add(Mainp);

		//setting the Layout for the main Panel
		Mainp.setLayout(c);

		//setting the meun panel Vlad
		//this is really simple, and please add on to this if you can lol
		Meunp.add(play);
		Meunp.add(tut);

		//setting for the Level select
		//adding new level: Create a new Button, use the code below, except replace Questions with the text name
		L1.addActionListener(new ListAction("Quiz1"));
		levelselect.add(L1);
		L2.addActionListener(new ListAction("Quiz2"));
		levelselect.add(L2);

		//designing level panel, (Vlad, this pt you can leave it alone)
		levelp.setLayout(new BorderLayout()); 

		//adding the top answerbar
		levelp.add(questionsrigth, BorderLayout.NORTH);

		//adding the questions
		levelp.add(questions, BorderLayout.CENTER);

		//creating the Layouts for Button Panel
		Buttonp.setLayout(new BoxLayout(Buttonp, BoxLayout.Y_AXIS));

		//creating all the Buttons
		for(int i = 0; i<4;i++){
			answers[i] = new Button();
			answers[i].addActionListener(new ButtonAction(i));
		}

		//creating the Button Panel
		Button1.add(answers[0]);
		Button1.add(answers[1]);

		Buttonp.add(Button1);

		Button2.add(answers[2]);
		Button2.add(answers[3]);

		Buttonp.add(Button2);

		Buttonp.add(returnmain);

		levelp.add(Buttonp,BorderLayout.SOUTH);

		//Adding actions to the Panels
		play.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				c.show(Mainp, "LevelS");

			}
		});
		
		//buttons that returns to the main menu
		returnmain.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				c.show(Mainp, "Menu");

			}

		});

		//label panel
		Result.setLayout(new BorderLayout()); 
		Result.add(Resultl,BorderLayout.CENTER);
		Result.add(Ret,BorderLayout.SOUTH);

		Ret.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				c.show(Mainp, "Menu");
			}
		});
		

		Mainp.add("Level",levelp);
		Mainp.add("Menu", Meunp);
		Mainp.add("LevelS", levelselect);
		Mainp.add("Res",Result);


		c.show(Mainp, "Menu");

		//housekeeping, don't touch
		mainf.setVisible(true);
		mainf.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				System.exit(0);
			}
		});
		
		//adding action to tutorial
		tut.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0)  {
				String st = null; 
				String st2 = null; 
				
				//reading the file from text
				try {
					br = new BufferedReader(new FileReader(" Tutorial.txt"));
					st = br.readLine();
					st2 = br.readLine();
				}
				catch (IOException err){
					err.printStackTrace();
				}
				
				//formating the Text and adding a scroll functions to it
				JOptionPane.showMessageDialog(mainf,scrollPangen(st),"Tutorials",JOptionPane.PLAIN_MESSAGE);
				JOptionPane.showMessageDialog(mainf,scrollPangen(st2),"Tutorials",JOptionPane.PLAIN_MESSAGE);
				

			}

		});

	}

	void nextq() throws IOException {
		questionnum++;
		answer = Integer.parseInt(br.readLine());

		question = br.readLine();
		for(int i = 0; i<4;i++){
			anschoice[i] = br.readLine();
			answers[i].setLabel(anschoice[i]);

		}

		questions.setText(question);
		questionsrigth.setText("You are at " + questionnum + " out of " + totalquestions + " questions. Your score is " + rightans);


	}

	class ButtonAction implements ActionListener{
		int num;

		//init the starting variable about the program
		ButtonAction(int i){
			num = i;
		}

		public void actionPerformed(ActionEvent e) {


			if(answer == num){
				JOptionPane.showMessageDialog(mainf,"Congratulations, you got the correct answer","Result",JOptionPane.PLAIN_MESSAGE);
				rightans++;
			}

			else{

				JOptionPane.showMessageDialog(mainf,"Sorry, wrong answer","Result",JOptionPane.PLAIN_MESSAGE);
			}


			if(questionnum != totalquestions){
				try {
					nextq();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			//exit to see your result
			else{
				Resultl.setText( "Your score is " + rightans);
				c.show(Mainp, "Res");
			}

		}
	}
	
	//this generates the 
	public JLabel scrollPangen(String st){
		
		//creating the text area
		JLabel text = new JLabel(st);
		text.setSize(500, 1000);
		
		//return the objects
		return text;
	}

	//the actions for the options
	class ListAction implements ActionListener{
		String s;

		//init the starting variable about the program
		ListAction(String str){
			s = str;
		}

		public void actionPerformed(ActionEvent e) {
			try {
				br = new BufferedReader(new FileReader("src/" + s + ".txt"));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}


			try {
				totalquestions = Integer.parseInt(br.readLine());
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			rightans = 0;
			questionnum = 0;

			try {
				nextq();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			c.show(Mainp, "Level");

		}
	}
}
