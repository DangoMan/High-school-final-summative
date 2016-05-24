package panels;
import java.io.*;
public class User {

	//creating all the varible needed for the program 
	int levelpassed;
	String username;
	boolean achievement[] = new boolean[5];

	//new user base on input file
	public User(String username) throws IOException{

		//creating the FileReader;
		BufferedReader reader = new BufferedReader (new FileReader("src/user/user_" + username + ".txt"));

		//initialing all the variable
		levelpassed = Integer.parseInt(reader.readLine());
		this.username = username;
		for(int i = 0; i<achievement.length;i++){
			achievement[i] = Boolean.parseBoolean(reader.readLine());
		}
		
		reader.close();
	}

	//update for new user
	public User(File username, String name) throws IOException{

		//creating the FileReader;
		BufferedWriter writer = new BufferedWriter (new FileWriter(username));

		//initialing all the variable
		levelpassed = 1;
		this.username = name;

		//updating the file
		writer.write("1");
		writer.newLine();

		for(int i = 0; i<achievement.length;i++){
			achievement[i] = false;
			writer.write("false");
			writer.newLine();
		}

		//close the writer
		writer.close();
	}
	
	//update the file
	void update() throws IOException{

		//creating the FileReader;
		BufferedWriter writer = new BufferedWriter (new FileWriter("src/user/user_" + username + ".txt"));

		//updating the file
		writer.write(Integer.toString(levelpassed));
		writer.newLine();

		for(int i = 0; i<achievement.length;i++){
			writer.write(Boolean.toString(achievement[i]));
			writer.newLine();
		}

		//close the writer
		writer.close();
	}
}
