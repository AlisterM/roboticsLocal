package navigateBot;

import java.util.ArrayList;

public class main {
	
	
	public static void main(String[] args) {
		Robby rob = new Robby();

		int bayPos = localization.startL();
		
		Coordinate start = localization.getCoordinates(bayPos);
		
//		Coordinate start = new Coordinate(12,2);
		Coordinate end = new Coordinate(5,16);
		Map test = new Map(23,23);
		
		Search tS = new Search(start, end, test);
		
		ArrayList<Coordinate> path = Map.getPath(tS.startSearch(start));
		
		ArrayList<Coordinate> optimized = rob.optimisePath(path);
		
		
		rob.followPathTrig(optimized);
		
		float [] colour = rob.enterGoal();
		
		Coordinate colourPos = null;
		
		if (colour[1] >= 0.11){
			colourPos = new Coordinate(16, 10); //x:16 
		}
		if (colour[0] >= 0.3){
			colourPos = new Coordinate(18, 10);
		}
		
		rob.exixG();
		
		Search findColour = new Search(end, colourPos, test);
		
		ArrayList<Coordinate> colourPath = Map.getPath(findColour.startSearch(start));
		
		ArrayList<Coordinate> colourOP = rob.optimisePath(colourPath);
		
		rob.followPathTrig(colourOP);
		
	}
	
}
