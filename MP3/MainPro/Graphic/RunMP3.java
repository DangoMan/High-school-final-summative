package Graphic;

import java.io.IOException;
/*
 * This is just used to execute the code 
 */

public class RunMP3{
	
	//Running graphic,nothing have to say here
	static void runB() throws InterruptedException, IOException {
		new MainGraphic().init();
	}
	
	public static void main(String args[]) throws InterruptedException, IOException  {
		runB();
	}
	
}