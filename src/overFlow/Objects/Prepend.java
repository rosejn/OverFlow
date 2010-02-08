package overFlow.Objects;

import java.util.Vector;

import overFlow.Atom.Atom;
import overFlow.Atom.AtomArray;
import overFlow.main.Node;

public class Prepend extends Node {
	AtomArray atomArray;
	public Prepend(String title, float tx, float ty, AtomArray aArray){
		super(title, tx, ty, aArray.getSize(), aArray.getSize());
		atomArray = aArray;
	}
	public void update() {
		for(int i = 0; i < atomArray.getSize(); i++){
			if(inputValues[i].getType() == 0 || inputValues[i].getType() == 2 || inputValues[i].getType() == 4){
				outputValues[i] = new AtomArray(createArray(i));		//create a return vector of atoms if the incomming list is only a single atom;
			}
			else {
				outputValues[i] = createArray(inputValues[i], i);
			}
		//	System.out.println(outputValues[i]);
		}
		
		updateConnections();
	}
	
	Vector<Atom> createArray(int j) {
		Vector<Atom> array = new Vector<Atom>(); 
		array.insertElementAt(inputValues[j], 0);
		return array;
	}
	
	AtomArray createArray(Atom a, int j) {
		Vector<Atom> array = new Vector<Atom>();
		array.add(a);
		array.add(atomArray.getElementAt(j));
		return new AtomArray(array);
	}
}

