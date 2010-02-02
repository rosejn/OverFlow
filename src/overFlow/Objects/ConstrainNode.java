package overFlow.Objects;

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
		if (inputValues[0] != null) {
			value = inputValues[0];
			returnVal = Tools.constrain(value, minVal , maxVal);
			value = returnVal;
			outputValues[0] = value;
			updateConnections();
		}
	}
} 