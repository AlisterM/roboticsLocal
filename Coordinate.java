package Mapping;

import java.util.Objects;
import java.lang.Object;

public class Coordinate {
 int x;
 int y;
 int g;
 int h;
 int f;
	
	public Coordinate(int xin, int yin){
		x=xin;
		y=yin;
		
	}
	
	public Coordinate(int xin, int yin, int fin){
		x=xin;
		y=yin;
		f=fin;
	}
	
	public Coordinate(int xin, int yin, int gin, int hin, int fin){
		x=xin;
		y=yin;
		g=gin;
		h=hin;
		f=fin;
	}
	
	public int manhatCompare(Coordinate start, Coordinate end){
		int xDif = java.lang.Math.abs(end.x - start.x);
		int yDif = java.lang.Math.abs(end.y - start.y);
		return xDif+yDif;
	}
	
	public int manhatCompare(int x, int y, Coordinate end){
		int xDif = java.lang.Math.abs(end.x - x);
		int yDif = java.lang.Math.abs(end.y - y);
		return xDif+yDif;
	}
	public boolean uals(Coordinate other){
		if(this.x==other.x&&this.y==other.y){
		return true;
		}
		else{
			return false;
		}
	}
	@Override
    public int hashCode() {
        return Objects.hash(x,y);
    }
	
	public String toString(){
		
		return "x: " + this.x + " y: " + this.y;
		
	}
}
