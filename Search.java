package navigateBot;
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
	private TreeNode[] nodePath;
	//Test Code----------------------------------
	Search(DefaultMutableTreeNode root){
		
		path = new DefaultTreeModel(root);
	}
	Search(Coordinate sIn, Coordinate gIn, Map mIn){
		//This is a negative so it will be true when the end hasnt beeen found and false when the end has been found
		endFound=true;
		startPoint = sIn;
		endPoint = gIn;
		map=mIn;
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(sIn);
		path = new DefaultTreeModel(root);
		openList = new ArrayList<Coordinate>();
		closedList = new ArrayList<Coordinate>();
		closedList.add(sIn);
		addOpen(sIn.x+1, sIn.y, sIn);
		path.insertNodeInto(new DefaultMutableTreeNode(new Coordinate(sIn.x+1, sIn.y)), root, 0);
		addOpen(sIn.x-1, sIn.y, sIn);
		path.insertNodeInto(new DefaultMutableTreeNode(new Coordinate(sIn.x-1, sIn.y)), root, 0);
		addOpen(sIn.x, sIn.y+1, sIn);
		path.insertNodeInto(new DefaultMutableTreeNode(new Coordinate(sIn.x, sIn.y+1)), root, 0);
		addOpen(sIn.x, sIn.y-1, sIn);
		path.insertNodeInto(new DefaultMutableTreeNode(new Coordinate(sIn.x, sIn.y-1)), root, 0);
		//TODO probbaly should combine this method and the start search method into a single method.
	}
//----------------------------Methods--------------------------------------
	//TODO Just fucking redo this whole mess
	public TreeNode[] startSearch(Coordinate sIn){
		//nodePath = new ArrayList<TreeNode>();
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
			//nodePath.add(0, test);
			nodePath=currentNode.getPath();
			//while(true){
				//try{
					//currentNode = (DefaultMutableTreeNode) currentNode.getParent();
					//test=(Coordinate) currentNode.getUserObject();
				//}catch(Exception e){
				//	break;
				//}
				//finally{
				//	break;
				//}
			//}	
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
			}//if
		}//for
		System.out.println("Expand chosen: "+c.x + " " +c.y);
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
		System.out.println("add open: "+xin +" " +yin);
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
							//also update value in the tree.
							break;//TODO multiple coordiantes added to the open list/ the tree. problem with this peice of code.
						}
						else{return;}
					}
				}
				//creates a new coordiante and adds it to the open list
				int tempH = endPoint.manhatCompare(xin, yin, this.endPoint);
				Coordinate temp = new Coordinate(xin, yin, (parent.g+1), tempH, (tempH+parent.g+1));
				DefaultMutableTreeNode tempNode = new DefaultMutableTreeNode(temp);
				openList.add(temp);
				path.insertNodeInto(tempNode, searchByCoord(parent), 0);
				System.out.println("node added: " +xin +" "+yin);
				//checks if the node that was just added is the endpoint. if it is the loop exits.
				if(temp.x==endPoint.x&&temp.y==endPoint.y){
					lastNode=tempNode;
					endFound=false;
				}
				return;	
			}
			else{//TODO fix this
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
	
	public DefaultMutableTreeNode searchByCoord(Coordinate c){
		//sets current node to the root of the tree
		DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) path.getRoot();
		//while(!(currentNode.children()==null)){
		//if the node has a user object
			if(currentNode.getUserObject()!=null){	
				//if the user object has the same xy as c
				if(((Coordinate) currentNode.getUserObject()).uals(c)){
					return currentNode;
				}//if
			else{
				//repeats for each child of the current node
				for(int i=0;i< currentNode.getChildCount();++i){
					DefaultMutableTreeNode recSerNode = recursiveSearch((DefaultMutableTreeNode) currentNode.getChildAt(i), c);
					if(!(recSerNode.getUserObject()==null)){
						return recSerNode;
					}//if
				}//for
			}//else
			}//if
	//	}//while
		return null;
	}//method
	
	public DefaultMutableTreeNode recursiveSearch(DefaultMutableTreeNode m, Coordinate c){
		DefaultMutableTreeNode	foundNode = new DefaultMutableTreeNode();
		DefaultMutableTreeNode emptyNode = foundNode;
		//checks if the node sent to the method is equal to c
		if(((Coordinate) m.getUserObject()).uals(c)){
			return m;
		}
		else{
			//if the node has children
			if(!(m.children()==null)){//note this section doesnt quite work as intended
				//iterates through the child list
				for(int i=0;i< m.getChildCount();++i){
					//calls this method on the child node
					foundNode = recursiveSearch((DefaultMutableTreeNode) m.getChildAt(i), c);
					try{
						if(((Coordinate) foundNode.getUserObject()).uals(c)){//TODO the problem is here
							return foundNode;
						}//if
					}//try
					catch(Exception e){
						//do nothing
					}//catch
					
					
					
				}//for
				return foundNode;
			}//if
			else{
				return null;
			}//else
		}//else
		
	}//method
//------------------------------Test Code------------------------------
//	public static void main(String[] args) {
//		Coordinate a = new Coordinate(0, 0);
//		Coordinate b = new Coordinate(0, 1);
//		Coordinate c = new Coordinate(0, 2);
//		Coordinate d = new Coordinate(0, 3);
//		
//		Coordinate test = new Coordinate(0, 3);
//		
//		DefaultMutableTreeNode root = new DefaultMutableTreeNode(a);
//		DefaultMutableTreeNode u = new DefaultMutableTreeNode(b);
//		DefaultMutableTreeNode v = new DefaultMutableTreeNode(c);
//		DefaultMutableTreeNode w = new DefaultMutableTreeNode(d);
//		
//		DefaultMutableTreeNode testNode = new DefaultMutableTreeNode(test);
//		Search testSearch = new Search(root);
//		testSearch.path.insertNodeInto(u, root, 0);
//		testSearch.path.insertNodeInto(v, root, 1);
//		testSearch.path.insertNodeInto(w, u, 0);
//		DefaultMutableTreeNode find = testSearch.searchByCoord(test);
//		//if(find.equals(testNode)){
//			System.out.println("found: "+find.getUserObject().toString());
//		//}else{System.out.println("fail");}
//	
//	}
}//class

//random side note, but does this project count as test first development as we're building the code in order to pass the end of term challenge.