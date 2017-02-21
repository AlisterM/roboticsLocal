package Mapping;
import javax.swing.tree.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;
import java.util.TreeMap;
//Should method return a stack or an arraylist?
class Search{
	Coordinate startPoint;
	Coordinate endPoint;
	Map map;
	DefaultTreeModel path;
	ArrayList<Coordinate> openList;
	ArrayList<Coordinate> closedList;
	boolean endFound;
	//should search be an actual object
	//What should be done in constructor vs in method?
	Search(Coordinate sIn, Coordinate gIn, Map mIn){
		//This is a negative so it will be true when the end hasnt beeen found and false when the end has been found
		endFound=true;
		startPoint = sIn;
		endPoint = gIn;
		map=mIn;
		//should we cast coordinates to treenodes or make new treenodes from the coordinate
		path = new DefaultTreeModel((TreeNode) sIn);
		openList = new ArrayList<Coordinate>();
		//should obstacles be added before search starts or each coordiante checked as we go?
		closedList = new ArrayList<Coordinate>();
		closedList.add(sIn);
		addOpen(sIn.x+1, sIn.y, sIn);
		addOpen(sIn.x-1, sIn.y, sIn);
		addOpen(sIn.x, sIn.y+1, sIn);
		addOpen(sIn.x, sIn.y-1, sIn);
		
	}
	
//----------------------------Methods--------------------------------------
	public ArrayList startSearch(){
		while(openList.size()>0&&endFound){
			expand();
		}
		
		
		return null;
		
	}
	
	//g actual
	public int calcF(Coordinate in){
		int g = startPoint.manhatCompare(this.startPoint, in);
		int h = endPoint.manhatCompare(this.endPoint, in);
		in.g=g;
		in.h=h;
		
	return g+h;
		
	}
	
	public int calcF(Coordinate in, int g){
		return g+in.h+1;
	}
	
	public void expand(){
		
		//Finds the node in the list with the lowest F value.
		Coordinate c= new Coordinate(0,0,9999999);
		for(int i=0;i<openList.size();i++){
			if(openList.get(i).f<c.f){
				c=openList.get(i);
			}
		}
		//adds the parent to the close list and adds the adjacent nodes to the open list.
		closedList.add(c);
		this.addOpen(c.x+1, c.y, c);
		this.addOpen(c.x-1, c.y, c);
		this.addOpen(c.x, c.y+1, c);
		this.addOpen(c.x, c.y-1, c);
		openList.remove(c);
		

		//return null;
	}
	
	void addOpen(int xin, int yin, Coordinate parent){
		//checks if space is occupied
		if(!map.isFilled(xin, yin)){
			//checks if the node is already in the open list
			for(int i=0;i<openList.size();i++){
				if(openList.get(i).x==xin&&openList.get(i).y==yin){
					//checks if the new f value for the node is less than its current value
					if(calcF(openList.get(i), parent.g)<openList.get(i).f){
						openList.get(i).g=parent.g+1;
						openList.get(i).f=calcF(openList.get(i), parent.g);
					}
					else{
						//do nothing
					}
				}
				else{
					//creates a new coordiante and adds it to the open list
					int tempH = endPoint.manhatCompare(xin, yin, this.endPoint);
					Coordinate temp = new Coordinate(xin, yin, (parent.g+1), tempH, (tempH+parent.g+1));
					openList.add(temp);
					path.insertNodeInto((MutableTreeNode) temp, (MutableTreeNode) parent, 0);
					if(temp.x==endPoint.x&&temp.y==endPoint.y){
						endFound=false;
					}
				}
			}
	}
	
}


	
}