package overFlow.Objects;

import overFlow.Atom.AtomFloat;
import overFlow.Tools.Tools;
import overFlow.main.Node;

public class ConstrainNode extends Node {

	float minVal = 0;
	float maxVal = 0;
	float returnVal;
	
	public ConstrainNode(String t, float tx, float ty, float min, float max) {
		super(t, tx, ty, 3, 1);
		minVal = min;
		maxVal = max;

	}

	public void update() {
		try {
			value = new AtomFloat(Tools.constrain(inputValues[0].getFloat(), minVal, maxVal));
			outputValues[0] = value;
			updateConnections();
		} catch (NullPointerException e) {
			
		}
	}
}