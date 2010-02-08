package overFlow.Objects;

import java.util.Vector;

import overFlow.Atom.Atom;
import overFlow.main.Node;

public class Route extends Node{
	Vector<Atom> atomArray;
	public Route(String title, float tx, float ty, Vector<Atom> arguments){
		super(title, tx, ty, 1, arguments.size() - 1);	//use input array length to set how many outputs
		atomArray = arguments;
		
	}
	
	public Route(String title, float tx, float ty, float[] fArray){
		
	}
	
	
	public void update() {
		
	}
}
