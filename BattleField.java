/*BattleField is a class describing the battlefield (that is to say, the game screen). It
can be broadly described as a matrix: every element of this matrix will contain an alien,
a gun, a shot, a casemate or will be empty. An instance of BattleField represents a
snapshot of the current battlefield configuration.*/

import java.io.File;

public class BattleField {

	//FIELD
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
	public BattleField(String filename) throws IllegalElementException {
		this.filename=filename;
		// just to test
		this.rows = 7;
		this.columns = 10;
		this.battlefield = new BattleFieldElement[this.rows][this.columns]; //
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				setBattleFieldElement(i, j, new Empty(i, j));

			}
		}
	}
	
	//METHODS

	public String getFilename(){				//	You should provide two methods
		return this.filename;					//	public String getFilename()
	}
										 
	public void setFilename(String s){			//	void setFilename(String) with the obvious meaning.
		this.filename= s;						
	}
	
	//utility methods to set and retrieve a specific element on the battlefield;
	void setBattleFieldElement(int x, int y, BattleFieldElement b) throws IllegalElementException{
		if (x == rows-1 && b.toString() == "G") {
			throw new IllegalElementException("Only a Gun can be placed in row "+(rows-1)+" (bottom)");
		} else {
			battlefield[x][y]= b;
		}
		
	}
	
	BattleFieldElement getBattleFieldElement(int x, int y){
		return battlefield[x][y];
	}										

	public String toString(){				// a method creating and returning a string rep-resenting the current battlefield configuration,
		return getBattleField();									// whose encoding is as specified above;
	}									

	public void setBattleField(String s){   // a method initializing the battle-field configuration as specified in the parameter: 
		this.filename=s;
	}

	public String getBattleField() {
		// to finish, not fully done yet
		// (by Andre)
		String enconding = rows + "|" + columns + "|";
		for (int i = 0 ; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				enconding += battlefield[i][j].toString() ;

			}enconding+= "$";
		}
		
		return enconding;
	}
	// method returning the current configuration of the battlefield, encoded as specified above;
	// 7|10|8-1R1-$4-3A1-1A1-$4-2C1-1A2-$4-2A4-$2-1C4A3-$2-1A7-$1-1G8-$ ("-" is a empty cell)
							

	public void write(){					// a method appending the current configuration to the current content of the file;
	}

	public void reload(){					// a method reading again from the file named filename the configuration of the battlefield 
											//(the last configuration found in the file is used);
	}
	
	public Object clone(){	
		return null; 						//a method that creates and returns a copy of this object;
	}										

	public void backup(String msg){
		System.out.println(msg);
	}										// makes a backup copy of the battlefield con-figuration, saving it onto a file whose name is passed
											// as an argument; the file is created from scratch;

	void move(){
	}										// a method that advances the configuration of one step, starting from the upper left corner and proceeding
											// left to right, one line at a time; the step must be performed in-place, without creating another copy
											// of the matrix: this can be obtained by suitably exploiting the methods provided by the
											// BattleFieldElement objects; the move() method handles all special cases (e.g., the collisions).



/*Observe that the listed signatures don’t explicitly mention exceptions that methods
should possibly throw: the implementer will have to carefully judge when the former
have to be implemented.*/

}

//read the pdf to get more info about encoding