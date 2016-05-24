package Graphic;
import java.util.*;
/*
 * This class is the main data storage
 * Sorting and searching can be also be found here
 * There are also a method for generating a 2-D array for table displaying
 * 
 */
public class Songs {
	private String title;
	private String category;
	private String artist;
	
	//init method
	public Songs(String title,String artists,String category){
		
		this.artist = artists;
		this.category = category;
		this.title = title;
		
	}
	
	//create the setter and getter 
	public void setTitles(String titles) {
		this.title = titles;
	}
	public String getTitles() {
		return title;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCategory() {
		return category;
	}
	
	public void setArtist(String artists) {
		this.artist = artists;
	}
	public String getArtist() {
		return artist;
	}

	//all sorting method
	public static void sortByArtist(Vector<Songs> s){
		for (int i = 0; i < s.size(); i++){
			
			//set up for modify bubble sort 
			boolean exitloop = false;
			
			for (int j = 0; j < s.size() - i -1; j++){
				
				//Sort Artist first
				if(s.elementAt(j).getArtist().compareTo(s.elementAt(j+1).getArtist()) > 0 ) {
					Songs temp = s.elementAt(j);
					s.setElementAt(s.elementAt(j+1), j);
					s.setElementAt(temp, j+1);
					
					exitloop = true;
				}
				
				//then titles
				else if(s.elementAt(j).getArtist().compareTo(s.elementAt(j+1).getArtist()) ==  0  && s.elementAt(j).getTitles().compareTo(s.elementAt(j+1).getTitles()) > 0){
					Songs temp = s.elementAt(j);
					s.setElementAt(s.elementAt(j+1), j);
					s.setElementAt(temp, j+1);
					
					exitloop = true;
				}
				
				//then Catagory 
				else if(s.elementAt(j).getArtist().compareTo(s.elementAt(j+1).getArtist()) ==  0  && s.elementAt(j).getTitles().compareTo(s.elementAt(j+1).getTitles()) == 0 &&s.elementAt(j).getCategory().compareTo(s.elementAt(j+1).getCategory()) >0 ){
					Songs temp = s.elementAt(j);
					s.setElementAt(s.elementAt(j+1), j);
					s.setElementAt(temp, j+1);
					
					exitloop = true;
				}
			}
			
			//exit loop if no swap
			if (exitloop == false){
				i = s.size();
			}
		}
	}
	
	public static void sortByCategory(Vector<Songs> s){
		for (int i = 0; i < s.size(); i++){
			
			//set up for modify bubble sort 
			boolean exitloop = false;
			
			for (int j = 0; j < s.size() - i -1; j++){
				
				//Sort Category first
				if(s.elementAt(j).getCategory().compareTo(s.elementAt(j+1).getCategory()) > 0 ) {
					Songs temp = s.elementAt(j);
					s.setElementAt(s.elementAt(j+1), j);
					s.setElementAt(temp, j+1);
					
					exitloop = true;
				}
				
				//then artist
				else if(s.elementAt(j).getCategory().compareTo(s.elementAt(j+1).getCategory()) == 0 && s.elementAt(j).getArtist().compareTo(s.elementAt(j+1).getArtist()) > 0 ){
					Songs temp = s.elementAt(j);
					s.setElementAt(s.elementAt(j+1), j);
					s.setElementAt(temp, j+1);
					
					exitloop = true;
				}
				
				//then titles
				else if(s.elementAt(j).getCategory().compareTo(s.elementAt(j+1).getCategory()) == 0 && s.elementAt(j).getArtist().compareTo(s.elementAt(j+1).getArtist()) == 0  &&s.elementAt(j).getTitles().compareTo(s.elementAt(j+1).getTitles()) > 0 ){
					Songs temp = s.elementAt(j);
					s.setElementAt(s.elementAt(j+1), j);
					s.setElementAt(temp, j+1);
					
					exitloop = true;
				}
				
			}
			
			//exit loop if no swap
			if (exitloop == false){
				i = s.size();
			}
		}
	}
	
	public static void sortByTitle(Vector<Songs> s){
		for (int i = 0; i < s.size(); i++){
			
			boolean exitloop = false;
			
			for (int j = 0; j < s.size() - i -1; j++){
				
				//if the Title are greater
				if(s.elementAt(j).getTitles().compareTo(s.elementAt(j+1).getTitles()) > 0 ) {
					Songs temp = s.elementAt(j);
					s.setElementAt(s.elementAt(j+1), j);
					s.setElementAt(temp, j+1);
					
					exitloop = true;
				}
				
				//if the title is the same but the Artist is larger, swap it
				else if(s.elementAt(j).getTitles().compareTo(s.elementAt(j+1).getTitles()) == 0 && s.elementAt(j).getArtist().compareTo(s.elementAt(j+1).getArtist()) > 0 ){
					Songs temp = s.elementAt(j);
					s.setElementAt(s.elementAt(j+1), j);
					s.setElementAt(temp, j+1);
					
					exitloop = true;
				}
				
				//if the title and the Artist the same but the Artist is different, swap base on Category
				else if(s.elementAt(j).getTitles().compareTo(s.elementAt(j+1).getTitles()) == 0 && s.elementAt(j).getArtist().compareTo(s.elementAt(j+1).getArtist()) == 0 && s.elementAt(j).getCategory().compareTo(s.elementAt(j+1).getCategory()) > 0  ){
					Songs temp = s.elementAt(j);
					s.setElementAt(s.elementAt(j+1), j);
					s.setElementAt(temp, j+1);
					
					exitloop = true;
				}
			}
			
			//exit loop if no swap
			if (exitloop == false){
				i = s.size();
			}
		}
	}
	
	//this generate a 2-D array of the song List
	public static String[][] GridGen (Vector<Songs> mp3){
		
		  String SongList[][] = new String[mp3.size()][3];
		  
		  //stores every element into the 2-D array
		  for(int i = 0; i < mp3.size();i++ ){
			  
			  SongList[i][0] = mp3.elementAt(i).getTitles();
			  SongList[i][1] = mp3.elementAt(i).getArtist();
			  SongList[i][2] = mp3.elementAt(i).getCategory();
			  
		  }
		return SongList;
	}
	
	//all of the searching method
	public static Vector<Songs> SearchTitle(Vector<Songs> s, String key){
		
		//create a empty Vector
		Vector<Songs> List = new Vector<Songs>();
		
		//search through the list for the song
		for (int i = 0; i < s.size();i++){
			
			//check if they are the same
			if(s.elementAt(i).getTitles().equalsIgnoreCase(key)){
				
				//adding the element into the Vector
				List.add(s.elementAt(i));
			}
		}
		
		return List;
	}
	
	public static Vector<Songs> SearchArtist(Vector<Songs> s, String key){
		
		//create a empty Vector
		Vector<Songs> List = new Vector<Songs>();
		
		//search through the list for the song
		for (int i = 0; i < s.size();i++){
			
			//check if they are the same
			if(s.elementAt(i).getArtist().equalsIgnoreCase(key)){
				
				//adding the element into the Vector
				List.add(s.elementAt(i));
			}
		}
		
		return List;
	}
	
	public static Vector<Songs> SearchCategory(Vector<Songs> s, String key){
		
		//create a empty Vector
		Vector<Songs> List = new Vector<Songs>();
		
		//search through the list for the song
		for (int i = 0; i < s.size();i++){
			
			//check if they are the same
			if(s.elementAt(i).getCategory().equalsIgnoreCase(key)){
				
				//adding the element into the Vector
				List.add(s.elementAt(i));
			}
		}
		
		return List;
	}
}
