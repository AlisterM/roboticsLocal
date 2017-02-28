package Mapping;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.tree.TreeNode;

public class Map {
	boolean Grid[][];
	int xSize;
	int ySize;
	
	Map(int x, int y){
		Grid = new boolean[x][y];
		xSize=x;
		ySize=y;
		
		
//--------------------------CODE TO FLL IN CORNERS----------------------
		for(int q=0;q<62;q++){
			addLoc(0,q);
			addLoc(61,q);
			addLoc(q,0);
			addLoc(q,61);
		}
		
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
		//System.out.println("added: " + x + "," + y);
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
public static void main(String[] args) {
	Coordinate start = new Coordinate(16,4);
	Coordinate end = new Coordinate(50,50);
	Map test = new Map(63,63);
	Search tS = new Search(start, end, test);
	ArrayList<Coordinate> tesnode = tS.startSearch(start);
	for(int i=0;i<tesnode.size();i++){
		System.out.println(tesnode.get(i).x+" " + tesnode.get(i).y);
		
	}
	
	
//	if(test.isFilled(44, 0)){
//		System.out.println("filled");}
//	else{
//		System.out.println("not filled");
//	}
}

}
