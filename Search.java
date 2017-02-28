
package Mapping;
import javax.swing.tree.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
class Search{
	Coordinate startPoint;
	Coordinate endPoint;
	Map map;
	DefaultTreeModel path;
	ArrayList<Coordinate> openList;
	ArrayList<Coordinate> closedList;
	DefaultMutableTreeNode lastNode;
	boolean endFound;
	private ArrayList<Coordinate> nodePath;
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
	//TODO Just fucking redo this whole mess
	public ArrayList<Coordinate> startSearch(Coordinate sIn){
		nodePath = new ArrayList<Coordinate>();
		while(openList.size()>0&&endFound){
			expand();
		}
		//Should get the last node, add to array and call getparent repaatedly until we arrive at the root of the tree.
		if(!endFound){
			DefaultMutableTreeNode currentNode = lastNode;
			Coordinate test = (Coordinate) currentNode.getUserObject();
			System.out.println(test.x);
			System.out.println(test.y);
			System.out.println(test.g);
			System.out.println(test.h);
			System.out.println(test.f);
			nodePath.add(0, test);
			
			while(true){
				//try{
					currentNode = (DefaultMutableTreeNode) currentNode.getParent();
					test=(Coordinate) currentNode.getUserObject();
				//}catch(Exception e){
				//	break;
				//}
				//finally{
				//	break;
				//}
			
			
			}	
			
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
		ListIterator<Coordinate> openIt = openList.listIterator();
		while(openIt.hasNext()){
			Coordinate openCo = openIt.next();
			if(openCo.x==c.x && openCo.y==c.y){
				openIt.remove();
			}
		}
		

		//return null;
	}
	
	void addOpen(int xin, int yin, Coordinate parent){
		System.out.println("addOpen");
		//checks if space is occupied
		if(!map.isFilled(xin, yin)){
			//checks if the node is already in the open list
			ListIterator<Coordinate> closedIt = closedList.listIterator();
			while(closedIt.hasNext()){
				Coordinate closedCo= closedIt.next();
				if(closedCo.x==xin && closedCo.y==yin){
					return;
					}
			}
			if(openList.size()>0){
				for(int i=0;i<openList.size();i++){
					if(openList.get(i).x==xin&&openList.get(i).y==yin){
						//checks if the new f value for the node is less than its current value
						if(calcF(openList.get(i), parent.g)<openList.get(i).f){
							openList.get(i).g=parent.g+1;
							openList.get(i).f=calcF(openList.get(i), parent.g);
							break;
						}
					}
				}
				
					
					
				//creates a new coordiante and adds it to the open list
				int tempH = endPoint.manhatCompare(xin, yin, this.endPoint);
				Coordinate temp = new Coordinate(xin, yin, (parent.g+1), tempH, (tempH+parent.g+1));
				DefaultMutableTreeNode tempNode = new DefaultMutableTreeNode(temp);
				openList.add(temp);
				path.insertNodeInto(tempNode, new DefaultMutableTreeNode(parent), 0);
				System.out.println("node added 0");
				//checks if the node that was just added is the endpoint. if it is the loop exits.
				if(temp.x==endPoint.x&&temp.y==endPoint.y){
					lastNode=tempNode;
					endFound=false;
				}
				return;
					
				
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