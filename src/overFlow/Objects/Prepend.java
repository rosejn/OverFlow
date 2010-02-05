package overFlow.Objects;

import java.util.Vector;

import overFlow.Atom.Atom;
import overFlow.Atom.AtomArray;
import overFlow.main.Node;

public class Prepend extends Node {
	Atom preAtom;
	public Prepend(String title, float tx, float ty, Atom atom){
		super(title, tx, ty, 1, 1);
		preAtom = atom;
	}
	public void update() {
		if(inputValues[0].getType() == 0 || inputValues[0].getType() == 2 || inputValues[0].getType() == 4){
			outputValues[0] = new AtomArray(createArray());		//create a return vector of atoms if the incomming list is only a single atom;
		}
		else {
			outputValues[0] = createArray(inputValues[0]);
		}
	}
	
	Vector<Atom> createArray() {
		Vector<Atom> array = new Vector<Atom>(); 
		array.insertElementAt(inputValues[0], 0);
		return array;
	}
	
	AtomArray createArray(Atom a) {
		Vector<Atom> array = a.getAtomArray();
		array.insertElementAt(preAtom, 0);
		return new AtomArray(array);
	}
}

