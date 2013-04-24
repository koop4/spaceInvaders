/*BattleField is a class describing the battlefield (that is to say, the game screen). It
can be broadly described as a matrix: every element of this matrix will contain an alien,
a gun, a shot, a casemate or will be empty. An instance of BattleField represents a
snapshot of the current battlefield configuration.*/

import java.io.*;
import java.util.StringTokenizer;


public class BattleField {

	//FIELD
	protected int gunCounter=0;
	private BattleFieldElement[][] battlefield;  //a field storing the initial battlefield configuration. Every element of this matrix must 
												 //contain a non-null value.
	private int rows;
	private int columns;						 //overall number of rows and columns of the battlefield.
	private String filename;					 //field containing the name of the file where the configurations should be saved and that is used 
													//to restore a saved configuration. 
										 
	// CONSTRUCTOR
	// a constructor configuring the battlefield, where filename is the filename
	// of a file containing some configurations(one per line): the last such
	// configuration becomes the current // configuration of the battlefield.
	public BattleField(String filename) throws IllegalElementException, IllegalPositionException {
		setFilename(filename);
		reload();
	}
	
	//METHODS

	public String getFilename() {				//	You should provide two methods
		return this.filename;					//	public String getFilename()
	}
										 
	public void setFilename(String s) {			//	void setFilename(String) with the obvious meaning.
		this.filename= s;						
	}
	
	//utility methods to set and retrieve a specific element on the battlefield;
	  
    void setBattleFieldElement(int x, int y, BattleFieldElement b) throws IllegalElementException, IllegalPositionException {
	  	
        if ((x == rows-1) && (!b.toString().equals("G")) && (!b.toString().equals(" "))) { 
  				throw new IllegalElementException("Only a Gun can be placed in row "+(rows-1)+" (bottom)");
  	  	} else { 
  				battlefield[x][y]= b;
  	  	}
        
  	    if(b.toString().equals("G") && x != rows-1) {
           throw new IllegalPositionException("The Gun must be placed in the bottom line of the BattleField");
		} else {
			if(b.toString().equals("G") && gunCounter>0) {
              throw new IllegalElementException("Only one Gun per BattleField");
			} else {
    		    battlefield[x][y]= b;
    		    gunCounter++; 
			}
		}
  	}
	
	BattleFieldElement getBattleFieldElement(int x, int y){
		return battlefield[x][y];
	}										

	public String toString(){				// a method creating and returning a string rep-resenting the current battlefield configuration,
		return getBattleField();									// whose encoding is as specified above;
	}									

	public void setBattleField(String s) throws IllegalElementException, IllegalPositionException {   // a method initializing the battle-field configuration as specified in the parameter: 
		
		String config_line = s;
		StringTokenizer st = new StringTokenizer(config_line,"|");
		
		//Get the rows from the String
		String s_rows = st.nextToken();
		rows = Integer.parseInt(s_rows);
		
		//Get the columns from the String
		String s_columns = st.nextToken();
		columns = Integer.parseInt(s_columns);
		
		//The rest of the String (Positions of the BattlefieldElements
		String result = st.nextToken();

		int i = 0; //i : to trough the String; 
		int l = 0, k = 0; //l & k : to place the BattlefieldElements in the matrix; 
		int	itemCounter; //itemCounter : to count how many items of a BattlefieldElement there are
		
		char c = result.charAt(i); 
		String stringCounter = "";

		//Until the end of the String
        while (i < result.length()-1) {
        	
        	//While there isn't detection of a BattlefieldElement
	        while (c != ' ' && c != '$' && c != 'R' && c != 'A' && c != 'G' && c != 'C' && c != 's' && c != 'S') {
	        	stringCounter += String.valueOf(c); //to converting the String into a integer
	        	i++;
	        	c = result.charAt(i);
	        }

	        //Processing when BattlefieldElement detected
	        switch (c) { 
	        	case ' ': 
	        		for (itemCounter = Integer.parseInt(stringCounter);itemCounter>0;itemCounter--) {
	        			setBattleFieldElement(l, k, new Empty(l, k));
	        			k++;
	        		}
	        		break; 
	        	case '$':
	        		l++;
	        		k = 0;
	        		break;
	        	case 'R':
	        		setBattleFieldElement(l, k, new RedSpacecraft(l, k));
	        		break; 
	        	case 'A': 
	        		for (itemCounter = Integer.parseInt(stringCounter);itemCounter>0;itemCounter--) {
	        			setBattleFieldElement(l, k, new OneStepAlien(l, k));
	        			k++;
	        		}
	        		break;  
	        	case 'C': 
	        		for (itemCounter = Integer.parseInt(stringCounter);itemCounter>0;itemCounter--) {
	        			setBattleFieldElement(l, k, new Casemate(l, k));
	        			k++;
	        		}
	        		break;  
	        	case 'G': 
	        		setBattleFieldElement(l, k, new Gun(l, k));
	        		break;
	        	case 's': 
	        		for (itemCounter = Integer.parseInt(stringCounter);itemCounter>0;itemCounter--) {
	        			setBattleFieldElement(l, k, new GunShot(l, k));
	        			k++;
	        		}
	        		break;  
	        	case 'S': 
	        		for (itemCounter = Integer.parseInt(stringCounter);itemCounter>0;itemCounter--) {
	        			setBattleFieldElement(l, k, new AlienShot(l, k));
	        			k++;
    				}
	        		break;  
	        	default: 
	        		break;
	        }
	        
	        stringCounter = ""; //Reset of the stringCounter
	        
	        i++;
        	c = result.charAt(i); //
        }
	}

	public String getBattleField() {

		String string = ""; //The string which will return the Battlefield's configuration
		String c1 = battlefield[0][0].toString(); //The precedent column of the matrix
		String c2; //The current column of the row
		int i,j; //To run through the table
		int occ = 1; //This is the occurence of an element
		string = rows + "|" + columns + "|";
		//end = "7|10|8 1R1 $4 3A1 1A1 $4 2C1 1A2 $4 2A4 $2 1C4A3 $2 1A7 $1 1G8 $" 
		for (i = 0; i<rows ; i++ ) {
			c1 = battlefield[i][0].toString(); //Initialisation of the first case of the row
			for ( j = 1; j< columns ; j++ ) {
				c2 = battlefield[i][j].toString();
				//System.out.println(i + ";" +j+ " : " +c1+ " "+c2+" - "+occ);
				if (c2 == c1){
					occ++;
					c1 = battlefield[i][j].toString();
				} else {
					if (c1 == " ") {
						string += occ + " ";
						occ = 1;
						c1 = battlefield[i][j].toString();
					} else {
						string += occ + "" + c1;
						occ = 1;
						c1 = battlefield[i][j].toString();
					}
				}
				
				if (j==columns-1) { // Detection if this is the last column of the row
					string += occ + " ";
					occ = 1;
					c1 = battlefield[i][j].toString();
				}
			}
			string += "$"; 
		}
		return string;

	}
	// method returning the current configuration of the battlefield, encoded as specified above;
	// 7|10|8-1R1-$4-3A1-1A1-$4-2C1-1A2-$4-2A4-$2-1C4A3-$2-1A7-$1-1G8-$ ("-" is a empty cell)
							

	public void write(){					// a method appending the current configuration to the current content of the file;
		
	}

	public void reload() throws IllegalElementException, IllegalPositionException{					// a method reading again from the file named filename the configuration of the battlefield 
											//(the last configuration found in the file is used);
	
		try {
			FileReader fr = new FileReader(this.filename);
			BufferedReader bf = new BufferedReader(fr);
			String str = "", line = "";
			try {
				while (str != null) {
					line = str;
					str = bf.readLine();
				}
				setBattleField(line); //Send to setBattleField method, the lastest configuration of the file
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("File not found !");
		}							
	
	}
	
	public Object clone() {
		return null; 						//a method that creates and returns a copy of this object;
	}										

	public void backup(String file) {
		File f = new File (file); 
			try {
			    FileWriter fw = new FileWriter (f);
			 
				String backup = this.getBattleField();
		
			    fw.write(backup);

			    fw.close();
			} catch (IOException exception) {
			    System.out.println ("Impossible to create the backup file !");
			}
	}										// makes a backup copy of the battlefield con-figuration, saving it onto a file whose name is passed
											// as an argument; the file is created from scratch;

	void move() {
	}										// a method that advances the configuration of one step, starting from the upper left corner and proceeding
											// left to right, one line at a time; the step must be performed in-place, without creating another copy
											// of the matrix: this can be obtained by suitably exploiting the methods provided by the
											// BattleFieldElement objects; the move() method handles all special cases (e.g., the collisions).



/*Observe that the listed signatures don’t explicitly mention exceptions that methods
should possibly throw: the implementer will have to carefully judge when the former
have to be implemented.*/

}

//read the pdf to get more info about encoding