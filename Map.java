package Mapping;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.DefaultBoundedRangeModel;
import javax.swing.tree.DefaultMutableTreeNode;
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
		for(int q=0;q<22;q++){
			addLoc(0,q);
		addLoc(22,q);
			addLoc(q,0);
			addLoc(q,22);
		}
		
		for(int q=0;q<6;q++){
		for(int r=0;r<=q;r++){
				addLoc(17+q, 0+r);
				
			}
		}//fills bottom left
		for(int q=0;q<6;q++){
			for(int r=0;r<=q;r++){
				addLoc(5-q, 0+r);
			}
		}//fills top left
		
		for(int q=0;q<6;q++){
			for(int r=0;r<=q;r++){
				addLoc(5-q,22-r);
			}
		}//fills top right
		
		for(int q=0;q<6;q++){
			for(int r=0;r<=q;r++){
				addLoc(17+q,22-r);
			}//fills bottom right
		}
		
		addLoc(11,11);
		addLoc(12,11);
		addLoc(13,11);
		addLoc(14,11);
		addLoc(10,11);
		addLoc(9,11);
		addLoc(8,11);
		//adds the wall
//		
//		addLoc(1,11);
//		addLoc(2,11);
//		addLoc(3,11);
//		//left obstacle
//		
//		addLoc(5,11);
//		addLoc(6,11);
//		addLoc(7,11);
//		//right obstacle
//		
//		addLoc(15,11);
//		addLoc(16,11);
//		addLoc(17,11);
//		//green obstacle
//		
//		addLoc(19,11);
//		addLoc(20,11);
//		addLoc(21,11);
//		//red obstacle
		
//		addLoc(15,11);
//		addLoc(16,11);
//		addLoc(17,11);
//		addLoc(18,11);
//		addLoc(19,11);
//		addLoc(20,11);
//		addLoc(21,11);
//		
//		addLoc(8,12);
//		addLoc(8,13);
//		addLoc(8,14);
//		addLoc(8,15);
//		addLoc(8,16);
//		addLoc(8,17);
//		addLoc(8,18);
//		addLoc(8,19);
//		addLoc(8,20);
//		addLoc(8,21);
//		//reduces map for first half
		
//		addLoc(1,11);
//		addLoc(2,11);
//		addLoc(3,11);
//		addLoc(4,11);
//		addLoc(5,11);
//		addLoc(6,11);
//		addLoc(7,11);
		//reduces map for second half
		
		
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
	Coordinate start = new Coordinate(11,4);
	Coordinate end = new Coordinate(21,11);
	Map test = new Map(23,23);
//	if(test.isFilled(1, 3)){
//		System.out.println("filled");}
//	if(test.isFilled(1, 20)){
//		System.out.println("filled");}
//	if(test.isFilled(17, 1)){
//		System.out.println("filled");}
//	if(test.isFilled(17, 16)){
//		System.out.println("filled");}
	System.out.println(Arrays.deepToString(test.Grid).replace("], ", "]\n").replaceAll("true", "X").replaceAll("false", "O"));
//	Search tS = new Search(start, end, test);
//	TreeNode[] tesnode = tS.startSearch(start);
//	for(int i=0;i<tesnode.length;i++){
//	System.out.println("X: " + ((Coordinate)((DefaultMutableTreeNode) tesnode[i]).getUserObject()).x + " Y: " + ((Coordinate)((DefaultMutableTreeNode) tesnode[i]).getUserObject()).y);
//	}
//	
//	
//}
	
	
//	if(test.isFilled(44, 0)){
//		System.out.println("filled");}
//	else{
//		System.out.println("not filled");
	}


}
