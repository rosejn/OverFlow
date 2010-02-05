package overFlow.Objects;

import overFlow.Atom.AtomFloat;
import overFlow.main.Node;

public class Trough extends Node {

	AtomFloat lastValue;
		
	public Trough(float tx, float ty) {
		super("trough", tx, ty, 2, 2);
		lastValue = new AtomFloat(0);
	}

	public void update() {
		try {
			if(inputValues[1].getString() == "bang") {
				lastValue = new AtomFloat(value.getFloat());
			}
		}catch (NullPointerException e){
		}
		try {
			if(inputValues[0].getFloat() < lastValue.getFloat()){
				value = inputValues[0];
				lastValue = new AtomFloat(inputValues[0].getFloat());
				outputValues[0] = value;
			}
			else {
				outputValues[1] = value;
			}
		
			updateConnections();
		}catch (NullPointerException e){
			
		}


	}
}
