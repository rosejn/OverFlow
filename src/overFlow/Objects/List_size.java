package overFlow.Objects;

import overFlow.Atom.AtomArray;
import overFlow.Atom.AtomInt;
import overFlow.main.Node;

public class List_size extends Node {
	AtomArray valueArray;
	int sliceAmount;
	
	public List_size(String text, float tx, float ty){
		super(text, tx, ty, 1, 2);
	    this.setOutputToolTip(0,"list output");
	    this.setOutputToolTip(0,"list size (int)");
	}
	
	
	public void update() {
			outputValues[0] = inputValues[0];
			outputValues[1] = new AtomInt(inputValues[0].getSize());
			updateConnections();
	}
}
