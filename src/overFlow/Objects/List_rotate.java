package overFlow.Objects;

import overFlow.Atom.AtomArray;
import overFlow.main.Node;

public class List_rotate extends Node{
	AtomArray valueArray = new AtomArray();
	int rotateAmount;

	public List_rotate(String text, float tx, float ty, int num) {
		super(text, tx, ty, 1, 1);
		rotateAmount = num;
	    this.setOutputToolTip(0,"rotated list output");

	}

	public void update() {
		valueArray = inputValues[0].getAtomArray();
		if(rotateAmount >= 0){
			for(int i = 0; i < rotateAmount; i++){
				valueArray.moveElementTo(0, valueArray.getSize() - 1);
			}
		}
		else {
			for(int i = 0; i < rotateAmount; i--){
				valueArray.moveElementTo(valueArray.getSize() - 1, 0);
			}
		}

		outputValues[0] = valueArray;
		updateConnections();
	}
}
