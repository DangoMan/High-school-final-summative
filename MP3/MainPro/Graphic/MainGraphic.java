package Graphic;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/*Sorry Mr. Berry, that is a lot of codes
 * 
 * I tried to divide up the code by component by component to make it easire to mark
 * all of the main function, i.e sorting are in the Song class
 * The reading and creating new user will be in the LoginPanels
 * The Deleting, adding and saving will be under MainPanel --- MainP
 * 
 * 
 */


public class MainGraphic {

	//main frame
	private JFrame MF = new JFrame("MP3 assignment") ;
	private  JPanel F = new JPanel();
	private CardLayout c = new CardLayout();

	//key for file reading
	private final String KEY = "Þ";

	//creating the panels (refer to bottom)
	private final MainPanels M = new MainPanels();
	private final LoginPanels LP = new LoginPanels();


	//Song array and reader, this contains all variable that is needed for the 
	private Vector<Songs> mp3 = new Vector<Songs>();
	private String Accountname[] = new String[1];

	public void init() throws IOException {		

		//HouseKeeping
		MF.setVisible(true);
		MF.setResizable(true);
		MF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		F.setLayout(c);

		//Adding into cardlayout
		F.add("Start",LP);
		F.add("Main",M);

		MF.add(F);


		//start program
		c.show(F, "Start");
		MF.setSize(400, 135);

	}

	//This declares the entire login combo
	private class LoginPanels extends JPanel{


		//Creating all the component 
		private JPanel SM1 = new JPanel();
		private JComboBox User;
		private JLabel L1 = new JLabel ("Welcome to Randy's MP3, please choose your account");
		private JButton Start1 = new JButton("Login");
		private JButton Start2 = new JButton("Or new user: Create a new Account here");
		private JButton Start3 = new JButton("Delete currently selected User");

		LoginPanels(){
			
			//Declaring a new ComboBox which contains all of the component
			try {
				User = new LoginCombo();
			} 
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//adding component into the panel
			setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
			add(L1,BorderLayout.NORTH);
			SM1.setLayout(new BoxLayout(SM1,BoxLayout.X_AXIS));
			SM1.add(User);
			SM1.add(Start1);
			add(SM1,BorderLayout.CENTER);
			add(Start2,BorderLayout.NORTH);
			add(Start3,BorderLayout.NORTH);

			//adding action to buttons
			Start1.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {

					try{
						Accountname[0] = (String) User.getSelectedItem();
						BufferedReader FReader = new BufferedReader(new FileReader(Accountname[0] + ".txt")) ;

						//reading it from the file
						String input;
						while((input = FReader.readLine()) != null){

							String[] temp = input.split(KEY);

							mp3.add(new Songs(temp[0], temp[1],temp[2]));
						}

						//sort it before it gets put onto the table
						Songs.sortByTitle(mp3);

						M.Mainpan.changelist();
						
						//Shows the Main Panel
						c.show(F, "Main");
						MF.pack();
						FReader.close();

					}

					catch(IOException e){
						e.printStackTrace();
					}
				}
			});

			Start2.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {

					try{
						BufferedWriter FWriter = new BufferedWriter (new FileWriter ("LoginN.txt",true));

						String username = JOptionPane.showInputDialog(null, "Please enter your username: ", "Username", 1);

						//making sure the user didn't open an empty string
						if(username!=null){

							//if the user is not found in the file
							if(!checkuser(username)){

								//creating the new textfile
								BufferedWriter f = new BufferedWriter (new FileWriter (username + ".txt"));
								f.close();

								//updating the filelist
								FWriter.newLine();
								FWriter.write(username);
								FWriter.close();

								M.Mainpan.changelist();

								//changing the accountname
								Accountname[0] = username;

								c.show(F, "Main");
								MF.pack();

								//resetting the combo box because there are a new user
								SM1.removeAll();

								User = new LoginCombo();
								
								SM1.setLayout(new BoxLayout(SM1,BoxLayout.X_AXIS));
								SM1.add(User);
								SM1.add(Start1);

								//refresh screen so the new combo shows
								SM1.revalidate();

							}
							
							//if there are duplicated user name
							else{
								JOptionPane.showMessageDialog(MF, "You already have a account name " + username);
							}
						}
						
						//close the file writer
						FWriter.close();
					}
					
					//complete the try-catch
					catch(IOException e){
						e.printStackTrace();
					}
				}
			});

			Start3.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {

					String deletename = (String) User.getSelectedItem();
					int n = JOptionPane.showConfirmDialog(MF,"Are you sure you want to delete " + deletename, null,JOptionPane.YES_NO_OPTION);

					//if the user choice yes
					if (n == 0){
						try {

							//delete the element from the Vector, Then re-write it back to file
							BufferedReader LoginRead = new BufferedReader(new FileReader("LoginN.txt"));
							Vector<String> Logins = new Vector<String>();
							
							//This writes it in to a temporary Vertor Login
							String input;
							
							while((input = LoginRead.readLine()) != null){
								Logins.add(input);
							}

							LoginRead.close();
							
							//Check if it have been remove
							boolean isremove= Logins.remove(deletename);

							if(isremove){
								
								//writes back to the file 
								BufferedWriter FWriter = new BufferedWriter (new FileWriter ("LoginN.txt"));
								for(int i =0; i < Logins.size()-1;i++){
									FWriter.write((String) Logins.get(i));
									FWriter.newLine();
								}

								if(!(Logins.size()==0)){
									FWriter.write((String) Logins.get((Logins.size()-1)));
								}

								FWriter.close();

								//Physically delete the file
								File f = new File(deletename +".txt");
								f.delete();

								//re-make the ComboBox and re-set the user list
								SM1.removeAll();

								User = new LoginCombo();

								SM1.setLayout(new BoxLayout(SM1,BoxLayout.X_AXIS));
								SM1.add(User);
								SM1.add(Start1);

								//refresh screen
								SM1.revalidate();


							}
								
							//in case of an error
							else{
								JOptionPane.showMessageDialog(MF, "An error have occur, please re-selected the name");
							}
						}
						
						//Complete the try - catch
						catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			});
		}
	}

	//This declares the entire Main user interphase 
	private class MainPanels extends JPanel{


		//creating all of the Panels and the layout
		private JPanel Basepan = new JPanel();
		private MainP Mainpan = new MainP();
		private SearchP Searchpan = new SearchP();
		private EditP Editpan = new EditP();
		private CardLayout MainLayout = new CardLayout();

		//Creating all of the component for the Song Display
		final String ColumnTitle[] ={"Title", "Artists","Category"};
		private JTable SongDis;

		MainPanels(){

			//adding the layout into the panel
			Basepan.setLayout(MainLayout);
			Basepan.add(Mainpan,"Main");
			Basepan.add(Searchpan,"Search");
			Basepan.add(Editpan,"Edit");
			add(Basepan);

			//start the panel with the main panel
			MainLayout.show(Basepan, "Main");
		}

		//this class displays all of the graphics
		private class MainP extends JPanel{

			//Creating all of the private component in the Mainpanel
			private JPanel LeftMain = new JPanel();

			private JPanel RigthMain = new JPanel();
			private JButton Addcol = new JButton("Add songs to the list");
			private JButton Sortl = new JButton("Sort by title");
			private JButton Sort2 = new JButton("Sort by artists");
			private JButton Sort3 = new JButton("Sort by category");
			private JButton EditSong = new JButton("Edit song");
			private JButton Search1 = new JButton("Search by title");
			private JButton Search2 = new JButton("Search by artists");
			private JButton Search3 = new JButton("Search by category");
			private JButton Delete = new JButton("Delete selected songs");
			private JButton Save = new JButton("Log off");

			//A temporary song for Storing Edited Songs
			private int Eloctaion[] = new int[1];

			MainP(){

				//adding component into the MainP
				RigthMain.setLayout(new BoxLayout(RigthMain,BoxLayout.Y_AXIS));
				RigthMain.add(Addcol);
				RigthMain.add(Sortl);
				RigthMain.add(Sort2);
				RigthMain.add(Sort3);
				RigthMain.add(EditSong);
				RigthMain.add(Search1);
				RigthMain.add(Search2);
				RigthMain.add(Search3);
				RigthMain.add(Delete);
				RigthMain.add(Save);

				add(LeftMain, BorderLayout.WEST);
				add(RigthMain, BorderLayout.EAST);

				//adding functions to the button
				Addcol.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						String title = JOptionPane.showInputDialog(null, "Please enter the title of your song: \nIf you want to cancel, please leave it blank or press cancel", "Title", 1);

						//making sure the user does not enter a blank data file
						if(title != null){
							String artists= JOptionPane.showInputDialog(null, "Please enter the artists of your song: \nIf you want to cancel, please leave it blank or press cancel", "Artists", 1);

							//making sure the user does not enter a blank data file
							if(artists != null ){
								String category = JOptionPane.showInputDialog(null, "Please enter the category of your song: \nIf you want to cancel, please leave it blank or press cancel", "Category", 1);

								//making sure the user does not enter a blank data file
								if(category != null){
									mp3.add(new Songs(title, artists,category));

									//making the Jtable, GridGen change the Song object into a 3 * Length String table
									SongDis = new JTable(Songs.GridGen(mp3),ColumnTitle);

									SongDis.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

									//Putting the New table on the list 
									LeftMain.removeAll();

									LeftMain.add(new JScrollPane(SongDis));
									
									//Refresh the screen
									LeftMain.revalidate();
								}
							}
						}
					}
				});
				
				//Sorting display
				Sortl.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						//sort it before it gets put onto the table
						Songs.sortByTitle(mp3);

						//resetting the list
						changelist();
					}
				});

				Sort2.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						//sort it before it gets put onto the table
						Songs.sortByArtist(mp3);

						//resetting the list
						changelist();
					}
				});

				Sort3.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						//sort it before it gets put onto the table
						Songs.sortByCategory(mp3);

						//resetting the list
						changelist();
					}
				});

				//all of the searching function
				Search1.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {

						//prompt the user to enter a key
						String key =JOptionPane.showInputDialog(null, "Please enter the Title of the song ", "Title", 1);


						//check if the user enter anything
						if(key != null){

							//search for the song
							boolean isfound = Searchpan.STitle(key);

							//if song is found
							if (isfound){
								//show the panel
								MainLayout.show(Basepan,"Search");
							}

							//if song is not found
							else{
								JOptionPane.showMessageDialog(MF, "No songs found");
							}
						}

					}
				});

				Search2.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {

						//prompt the user to enter a key
						String key =JOptionPane.showInputDialog(null, "Please enter the Artist's name", "Artists", 1);

						//check if the user enter anything
						if(key != null){

							//search for the song
							boolean isfound = Searchpan.SArtist(key);

							//if song is found
							if (isfound){
								//show the panel
								MainLayout.show(Basepan,"Search");
							}

							//if song is not found
							else{
								JOptionPane.showMessageDialog(MF, "No songs found");
							}
						}

					}
				});

				Search3.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {

						//prompt the user to enter a key
						String key =JOptionPane.showInputDialog(null, "Please enter the Category of the song ", "Category", 1);


						//check if the user enter anything
						if(key != null){

							//search for the song
							boolean isfound = Searchpan.SCategory(key);

							//if song is found
							if (isfound){
								//show the panel
								MainLayout.show(Basepan,"Search");
							}

							//if song is not found
							else{
								JOptionPane.showMessageDialog(MF, "No songs found");
							}
						}

					}
				});
				
				//editing song
				EditSong.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {

						//gets the position of the song
						int rowNum = SongDis.getSelectedRow();

						//check if the user selected any thing
						if(rowNum != -1){

							//updating the information on the current updating song
							Eloctaion[0] = rowNum;

							//init the edit screen
							Editpan.init(1, mp3.elementAt(Eloctaion[0]));
							
							

							//Change Panel
							MainLayout.show(Basepan, "Edit");
							
							//setting the size of the main frame
							MF.pack();

						}

						else{

							//display an error message
							JOptionPane.showMessageDialog(MF, "Please select a song");
						}
					}
				});

				Delete.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {

						//gets the position of the song
						int rowNum = SongDis.getSelectedRow();

						//check if the user selected any thing
						if(rowNum != -1){

							//make sure that the user actually want to delete the song
							int n = JOptionPane.showConfirmDialog(MF,"Are you sure you want to delete " + mp3.get(rowNum).getTitles() + " by " + mp3.get(rowNum).getArtist() + "?", null,JOptionPane.YES_NO_OPTION );

							//if user choice yes
							if (n == 0){

								//remove the song from the vector
								mp3.remove(rowNum);

								//resetting the list
								changelist();							
							}
						}

						else{

							//display an error message
							JOptionPane.showMessageDialog(MF, "Please select a song");
						}


					}
				});

				Save.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						int n = JOptionPane.showConfirmDialog(MF,"Do you want to save", null,JOptionPane.YES_NO_CANCEL_OPTION );

						//selected no exit right to main menu
						if (n == 0){

							//writing the content in the file 
							try {

								//writing the content onto the file
								BufferedWriter writer = new BufferedWriter(new FileWriter (Accountname[0]+".txt"));

								for(int i = 0; i < mp3.size(); i++){
									writer.write(mp3.elementAt(i).getTitles() + KEY + mp3.elementAt(i).getArtist() + KEY + mp3.elementAt(i).getCategory());
									writer.newLine();
								}

								writer.close();
							}
							catch (IOException e) {

								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							//resetting all of the user profile
							mp3 = new Vector<Songs>();
							Accountname = new String[1];

							//exit into the user login screen
							c.show(F, "Start");
							MF.setSize(400, 135);
						}

						//selected no exit right to main menu
						else if (n == 1){

							//resetting all of the user profile
							mp3 = new Vector<Songs>();
							Accountname = new String[1];

							//exit into the user login screen
							c.show(F, "Start");
							MF.setSize(400, 135);
						}

						//if the user choice cancel
						else if(n == 2);
					}
				});
			}

			//A method that is design for resetting the list
			public void changelist(){

				//making the Jtable, GridGen change the Song object into a 3 * Length String table
				SongDis = new JTable(Songs.GridGen(mp3),ColumnTitle){

					//this overrides the isCellEditable option so that the cell cannot be edit
					public boolean isCellEditable ( int row, int column )
					{
						return false;
					}
				};

				SongDis.getTableHeader().setReorderingAllowed(false);
				SongDis.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

				//reset the screen and re-add everything
				LeftMain.removeAll();

				LeftMain.add(new JScrollPane(SongDis));

				//refresh graphic
				LeftMain.revalidate();

			}

			public void changeEditSongs(Songs g){
				
				//change the song current selected
				mp3.set(Eloctaion[0],g);
				
				//refresh the list
				changelist();
			}

		}

		//this class is used to show the result of search 
		private class SearchP extends JPanel{

			//Declaring all of the variables for the array
			private JPanel LeftSearch = new JPanel();
			private JTable SearchSongDis;
			private Vector<Songs> Search = new Vector<Songs>();

			private JPanel RightSearch = new JPanel();
			private JButton SearchSortl = new JButton("Sort by title");
			private JButton SearchSort2 = new JButton("Sort by artists");
			private JButton SearchSort3 = new JButton("Sort by category");
			private JButton SearchEditSong = new JButton("Edit song");
			private JButton SearchDelete = new JButton("Delete selected songs");
			private JButton SearchReturn = new JButton("Return to main");

			//A temporary song for Storing Edited Songs
			private int SELocation[] = new int[1];


			SearchP(){
				//change the button layout for right panel
				RightSearch.setLayout(new BoxLayout(RightSearch,BoxLayout.Y_AXIS));

				//adding the component into the panel, other component will be added later because will change base on the user
				add(LeftSearch,BorderLayout.WEST);
				add(RightSearch,BorderLayout.EAST);

				//adding function to the button

				//all sorting method
				SearchSortl.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						//sort it before it gets put onto the table
						Songs.sortByTitle(Search);

						//resetting the list
						changeSearchlist();
					}
				});


				SearchSort2.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						//sort it before it gets put onto the table
						Songs.sortByArtist(Search);

						//resetting the list
						changeSearchlist();
					}
				});

				SearchSort3.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						//sort it before it gets put onto the table
						Songs.sortByCategory(Search);

						//resetting the list
						changeSearchlist();
					}
				});

				//This is used to delete Songs from the List
				SearchDelete.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {

						//gets the position of the song
						int rowNum = SearchSongDis.getSelectedRow();

						//check if the user selected any thing
						if(rowNum != -1){

							//make sure that the user actually want to delete the song
							int n = JOptionPane.showConfirmDialog(MF,"Are you sure you want to delete " +  Search.get(rowNum).getTitles() + " by " + Search.get(rowNum).getArtist() + "?", null,JOptionPane.YES_NO_OPTION );

							//if user choice yes
							if (n == 0){

								Songs DeletedSongs = Search.elementAt(rowNum);

								//remove the song from both vector
								Search.remove(rowNum);
								mp3.remove(DeletedSongs);

								//resetting the list
								changeSearchlist();							
							}
						}

						else{

							//display an error message
							JOptionPane.showMessageDialog(MF, "Please select a song");
						}

					}
				});
				
				//editing song
				SearchEditSong.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {

						//gets the position of the song
						int rowNum = SearchSongDis.getSelectedRow();

						//check if the user selected any thing
						if(rowNum != -1){

							//updating the information on the current updating song
							SELocation[0] = rowNum;

							//init the edit screen
							Editpan.init(2, Search.elementAt(SELocation[0]));
							
							

							//Change Panel
							MainLayout.show(Basepan, "Edit");
							
							//setting the size of the main frame
							MF.pack();

						}

						else{

							//display an error message
							JOptionPane.showMessageDialog(MF, "Please select a song");
						}


					}
				});

				//This returns to the main menu 
				SearchReturn.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {

						//reseting the RightFrame, others will be re-set as the Frame gets created
						RightSearch.removeAll();

						//resetting the table from mainmeun
						Mainpan.changelist();

						//Exit to MainMeun 
						MainLayout.show(Basepan, "Main");
					}
				});

			}

			//this following method setup the screen for function		
			boolean SArtist(String key){

				//search the song in the mp3 Vector
				Search = Songs.SearchArtist(mp3, key);

				//if there are songs found
				if(Search.size() != 0){

					//setup meun (No Sort by Artist)
					RightSearch.add(SearchSortl);
					RightSearch.add(SearchSort3);
					RightSearch.add(SearchEditSong);
					RightSearch.add(SearchDelete);
					RightSearch.add(SearchReturn);

					//setup the table
					changeSearchlist();

					return true;
				}

				//if no Songs are found
				return false;
			}

			boolean STitle(String key){

				//search the song in the mp3 Vector
				Search = Songs.SearchTitle(mp3, key);

				//if there are songs found
				if(Search.size() != 0){

					//setup meun (No Sort by Title)
					RightSearch.add(SearchSort2);
					RightSearch.add(SearchSort3);
					RightSearch.add(SearchEditSong);
					RightSearch.add(SearchDelete);
					RightSearch.add(SearchReturn);

					//setup the table
					changeSearchlist();

					return true;
				}

				//if no Songs are found
				return false;
			}

			boolean SCategory(String key){

				//search the song in the mp3 Vector
				Search = Songs.SearchCategory(mp3, key);

				//if there are songs found
				if(Search.size() != 0){

					//setup menu (No Sort by Category)
					RightSearch.add(SearchSortl);
					RightSearch.add(SearchSort2);
					RightSearch.add(SearchEditSong);
					RightSearch.add(SearchDelete);
					RightSearch.add(SearchReturn);

					//setup the table
					changeSearchlist();

					return true;
				}

				//if no Songs are found
				return false;
			}

			//this is use to update the list
			public void changeSearchlist(){

				//making the Jtable, GridGen change the Song object into a 3 * Length String table
				SearchSongDis = new JTable(Songs.GridGen(Search),ColumnTitle){

					//this overrides the isCellEditable option so that the cell cannot be edit
					public boolean isCellEditable ( int row, int column )
					{
						return false;
					}
				};

				SearchSongDis.getTableHeader().setReorderingAllowed(false);
				SearchSongDis.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

				//reset the screen and re-add everything
				LeftSearch.removeAll();

				LeftSearch.add(new JScrollPane(SearchSongDis));

				//refresh graphic
				LeftSearch.revalidate();
			}

			public void changeEditSongs(Songs g){

				//located the location of the vector in the main vector
				int mainLocation = mp3.indexOf(Search.elementAt(SELocation[0]));

				//replace the edited element
				Search.set(SELocation[0], g);
				mp3.set(mainLocation,g);
				
				//resetting the screen
				changeSearchlist();
			}
		}


		private class EditP extends JPanel {

			//creating all of the component for the JLable
			private JPanel Layer1 = new JPanel();
			private JLabel TitleLabel = new JLabel("Title:");
			private JTextField TitleField;
			private JPanel Layer2 = new JPanel() ;
			private JLabel ArtistLabel= new JLabel("Artist:");
			private JTextField ArtistField;
			private JPanel Layer3 = new JPanel();
			private JLabel CatagoryLabel = new JLabel("Catagory:");
			private JTextField CatagoryField;

			private JPanel BottomMeun = new JPanel();
			private JButton EditSave = new JButton("Save");
			private JButton EditExit = new JButton("Cancel");

			//This is used to determent what panel is it coming from
			int key[] = new int[1] ;

			//setting up the Basic
			EditP(){

				//setting the Layout to the Panels
				setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));


				//adding the item to the panel
				add(Layer1);
				add(Layer2);
				add(Layer3);
				add(BottomMeun, BorderLayout.SOUTH);

				//adding buttons to the Bottom menu
				BottomMeun.add(EditSave);
				BottomMeun.add(EditExit);

				EditSave.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						//created the new object
						Songs s = new Songs(TitleField.getText(),ArtistField.getText(),CatagoryField.getText());
						
						//if the main activity comes from the main Panel
						if(key[0] == 1){
							
							//save the song in to the mainpanel
							Mainpan.changeEditSongs(s);
							
							//set the panel back to main
							MainLayout.show(Basepan, "Main");
						}

						//if the main activity comes from the Search Panel
						if(key[0] == 2){
							
							//save the song in to the Search
							Searchpan.changeEditSongs(s);
							
							//set the panel back to Search
							MainLayout.show(Basepan, "Search");
						}
						
						//resetting Frame size
						MF.pack();
					}
				});

				EditExit.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {

						//if the main activity comes from the main Panel
						if(key[0] == 1){
							MainLayout.show(Basepan, "Main");
						}

						//if the main activity comes from the Search Panel
						if(key[0] == 2){
							MainLayout.show(Basepan, "Search");
						}
						
						//resetting Frame size
						MF.pack();
					}
				});
			}

			public void init(int key, Songs g){
				
				//updating so you know what key did you come from
				this.key[0] = key;

				//updating the Panel
				TitleField = new JTextField(g.getTitles(),20);
				ArtistField = new JTextField(g.getArtist(),20);
				CatagoryField = new JTextField(g.getCategory(),20);

				//clearing the panel
				Layer1.removeAll();
				Layer2.removeAll();
				Layer3.removeAll();

				//re-adding it into the panel
				Layer1.add(TitleLabel);
				Layer1.add(TitleField);
				Layer2.add(ArtistLabel);
				Layer2.add(ArtistField);
				Layer3.add(CatagoryLabel);
				Layer3.add(CatagoryField);
				
				//setting the size of the main frame
				MF.pack();


			}
		}
	}
	//this checks if the name is in the file (Since it have been used twice)
	private boolean checkuser(String name){
		try {
			//set up the variable 
			BufferedReader LoginRead = new BufferedReader(new FileReader("LoginN.txt"));
			String input;

			//search if there any user
			while((input = LoginRead.readLine()) != null){

				//if a user is found
				if(input.equals(name)){
					return true;
				}
			}

		} 
		
		//Complete the try-choice
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}



	//This generate a new ComboList for the start menu (Since it have been used twice)
	private static class LoginCombo extends JComboBox{

		//the method return a Vector that stores 
		static Vector<String> getitem() throws IOException{
			BufferedReader LoginRead = new BufferedReader(new FileReader("LoginN.txt"));


			//Using a Vector to stores all of the user names
			Vector<String> Logins = new Vector<String>();

			//reading 
			String inputuser;

			while((inputuser = LoginRead.readLine()) != null){
				Logins.add(inputuser);
			}

			//sort the username
			Collections.sort(Logins);

			//closing the file reader
			LoginRead.close();

			return Logins;
		}

		//creating the object
		LoginCombo() throws IOException{
			super(getitem());
		}
	}
}
