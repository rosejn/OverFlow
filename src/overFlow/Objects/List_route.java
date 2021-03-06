package overFlow.Objects;

import java.util.Vector;

import overFlow.Atom.Atom;
import overFlow.Atom.AtomArray;
import overFlow.main.Node;

public class List_route extends Node {
	Vector<Atom> valueArray0 = new Vector<Atom>();
	Vector<Atom> valueArray1 = new Vector<Atom>();
	int sliceAmount;

	public List_route(String text, float tx, float ty, int num) {
		super(text, tx, ty, 1, 2);
		sliceAmount = num;

	}

	public void update() {
		valueArray0.clear();
		valueArray1.clear();
		if (inputValues[0].getSize() > 1) {
			if (inputValues[0].getSize() > 1) {

				for (int i = 0; i < sliceAmount; i++) { // create list 1 for left output
					valueArray0.insertElementAt(inputValues[0].getElementAt(i + (inputValues[0].getSize() - sliceAmount - 1)),0);
				}
				
				for (int i = sliceAmount - 1; i < (inputValues[0].getSize() - 1); i++) { // create list 2 for right output
					valueArray1.insertElementAt(inputValues[0].getElementAt(i), 0);
				}

				System.out.println(valueArray1.size());
				outputValues[1] = new AtomArray(valueArray1);
				outputValues[0] = new AtomArray(valueArray0);
				updateConnections();
			}
		}
		else {
			outputValues[0] = inputValues[0];
			updateConnections();
		}
	}
}
