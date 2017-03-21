package navigateBot;

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
//		for(int q=0;q<22;q++){
//			addLoc(0,q);
//			addLoc(22,q);
//			addLoc(q,0);
//			addLoc(q,22);
//		}
		
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
	
	public static ArrayList<Coordinate> getPath(TreeNode[] p){
		ArrayList<Coordinate> path = new ArrayList<Coordinate>();
		for(int i = 0; i < p.length; ++i){
			path.add(((Coordinate)((DefaultMutableTreeNode) p[i]).getUserObject()));
		}
		/*for(int i = 0; i < path.size(); ++i){
			System.out.println(path.get(i).x + " " + path.get(i).y);
		}*/
		return path;
	}
	
	

////-----------------------------------------Test Stuff-----------------------------------
public static void main(String[] args) {
	Coordinate start = new Coordinate(11,5);
	Coordinate end = new Coordinate(15,17);
	Map test = new Map(23,23);
	if(test.isFilled(1, 3)){
		System.out.println("filled");}
	if(test.isFilled(1, 20)){
		System.out.println("filled");}
	if(test.isFilled(17, 1)){
		System.out.println("filled");}
	if(test.isFilled(17, 16)){
		System.out.println("filled");}
	System.out.println(Arrays.deepToString(test.Grid).replace("], ", "]\n").replaceAll("true", "X").replaceAll("false", "O"));
	//Search tS = new Search(start, end, test);
	//TreeNode[] tesnode = tS.startSearch(start);
	//for(int i=0;i<tesnode.length;i++){
	//System.out.println("X: " + ((Coordinate)((DefaultMutableTreeNode) tesnode[i]).getUserObject()).x + " Y: " + ((Coordinate)((DefaultMutableTreeNode) tesnode[i]).getUserObject()).y);
	//}
	
	
}
	
	
//	if(test.isFilled(44, 0)){
//		System.out.println("filled");}
//	else{
//		System.out.println("not filled");
//	}


}
