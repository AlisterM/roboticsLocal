package navigateBot;

import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

public class main {
	
	
	public static void main(String[] args) {
		Robby rob = new Robby();
//		rob.turnTo(-90);
		/*ArrayList<CoordinateTest> path = new ArrayList<CoordinateTest>();
		CoordinateTest start = new CoordinateTest(0,0);
		CoordinateTest fwd = new CoordinateTest(0,5);
		CoordinateTest left = new CoordinateTest(5, 5);
		CoordinateTest right = new CoordinateTest(5,16);
		CoordinateTest qwe = new CoordinateTest(3, 18);
		path.add(start);
		path.add(fwd);
		path.add(left);
		path.add(right);
		path.add(qwe);
		rob.followPathTrig(path);*/
//		
//		rob.trig(start,fwd, 6);
		localization.startL();
		
		
		//Final stuff (hopefully) Not quite
		/*Coordinate start = new Coordinate(31,15);
		Coordinate end = new Coordinate(31,20);
		
		Map test = new Map(63,63);
		
		Search tS = new Search(start, end, test);
		
		
		rob.followPathTrig(Map.getPath(tS.startSearch(start)));*/
		
//		Coordinate start = new Coordinate(11,5);
//		Coordinate end = new Coordinate(15,17);
//		Map test = new Map(23,23);
//		
//		Search tS = new Search(start, end, test);
//		
//		
//		rob.followPathTrig(Map.getPath(tS.startSearch(start)));
	}
	
}
