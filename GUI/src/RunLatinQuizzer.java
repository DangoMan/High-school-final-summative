import java.io.FileNotFoundException;


public class RunLatinQuizzer {
	public static void run() throws FileNotFoundException{
		LatinQuizzer l = new LatinQuizzer();
		l.init();
		
	}
	
	public static void main(String args[]) throws FileNotFoundException{
		run();
		//System.out.println("Hello");
	}
}
