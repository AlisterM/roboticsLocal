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
		path = new DefaultTreeModel(new DefaultMutableTreeNode(sIn));
		openList = new ArrayList<Coordinate>();
		closedList = new ArrayList<Coordinate>();
		closedList.add(sIn);
		addOpen(sIn.x+1, sIn.y, sIn);
		addOpen(sIn.x-1, sIn.y, sIn);
		addOpen(sIn.x, sIn.y+1, sIn);
		addOpen(sIn.x, sIn.y-1, sIn);
		//TODO probbaly should combine this method and the start search method into a single method.
	}
	
//----------------------------Methods--------------------------------------
	public TreeNode[] startSearch(Coordinate sIn){
		TreeNode[] nodePath = null;
		while(openList.size()>0&&endFound){
			expand();
		}
		if(!endFound){
			nodePath=path.getPathToRoot(new DefaultMutableTreeNode(sIn));
		}
		
		return nodePath;
		
	}
	
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
		System.out.println("addOpen");
		//checks if space is occupied
		if(!map.isFilled(xin, yin)){
			//checks if the node is already in the open list
			if(openList.size()>0){
				for(int i=0;i<openList.size();i++){
					if(openList.get(i).x==xin&&openList.get(i).y==yin){
						//checks if the new f value for the node is less than its current value
						if(calcF(openList.get(i), parent.g)<openList.get(i).f){
							openList.get(i).g=parent.g+1;
							openList.get(i).f=calcF(openList.get(i), parent.g);
							break;
						}
						else{
							//do nothing
						}
					} else{}
					
					//else{
						//creates a new coordiante and adds it to the open list
						int tempH = endPoint.manhatCompare(xin, yin, this.endPoint);
						Coordinate temp = new Coordinate(xin, yin, (parent.g+1), tempH, (tempH+parent.g+1));
						openList.add(temp);
						path.insertNodeInto(new DefaultMutableTreeNode(temp), new DefaultMutableTreeNode(parent), 0);
						System.out.println("node added 0");
						//checks if the node that was just added is the endpoint. if it is the loop exits.
 						if(temp.x==endPoint.x&&temp.y==endPoint.y){
							endFound=false;
						}
 						break;
					//}
				}
			}
			else{
				int tempH = endPoint.manhatCompare(xin, yin, this.endPoint);
				Coordinate temp = new Coordinate(xin, yin, (parent.g+1), tempH, (tempH+parent.g+1));
				openList.add(temp);
				path.insertNodeInto(new DefaultMutableTreeNode(temp), new DefaultMutableTreeNode( parent), 0);
				System.out.println("node added 1");
				//checks if the node that was just added is the endpoint. if it is the loop exits.
				if(temp.x==endPoint.x&&temp.y==endPoint.y){
					endFound=false;
				}
				
			}
	}
	
}


	
}