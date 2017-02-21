package Mapping;

import java.util.Arrays;

public class Map {
	boolean Grid[][];
	int xSize;
	int ySize;
	
	Map(int x, int y){
		Grid = new boolean[x][y];
		xSize=x;
		ySize=y;
		
		
//--------------------------CODE TO FLL IN CORNERS----------------------
		for(int q=0;q<19;q++){
		for(int r=0;r<=q;r++){
				addLoc(44+q, 0+r);
				
			}
		}//fills bottom right
		for(int q=0;q<19;q++){
			for(int r=0;r<=q;r++){
				addLoc(18-q, 0+r);
			}
		}//fills bottom left
		
		for(int q=0;q<19;q++){
			for(int r=0;r<=q;r++){
				addLoc(18-q,44+r);
			}
		}//fills top left
		
		for(int q=0;q<19;q++){
			for(int r=0;r<=q;r++){
				addLoc(44+q,62-r);
			}//fills top right
		}
//----------------------------------END---------------------------------------
	}
	
//---------------------------------Methods--------------------------------------
	
	public void addLoc(int x, int y){
		Grid[x][y] = true;
		System.out.println("added: " + x + "," + y);
	}
	public void removeLoc(int x, int y){
		Grid[x][y] = false;
	}
	
	public boolean isFilled(int x, int y){
		if(x<xSize&&y<ySize){
			return Grid[x][y];}
		else{return true;}
		}
	
	

////-----------------------------------------Test Stuff-----------------------------------
//public static void main(String[] args) {
//	Map test = new Map(63, 63);
//	
//	if(test.isFilled(44, 0)){
//		System.out.println("filled");}
//	else{
//		System.out.println("not filled");
//	}
//}

}
