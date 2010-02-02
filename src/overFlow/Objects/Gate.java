package overFlow.Objects;

import overFlow.main.Node;

public class Gate extends Node {

	public Gate(float tx, float ty) {
		super("gate", tx, ty, 2, 1);

	}

	public void update() {
		if (inputValues[0] != null) {
			if (inputValues[0] == 1) {
				if (inputValues[1] != null) {
					value = inputValues[1];
				}
			}
			outputValues[0] = value;
			updateConnections();
		}
	}
}
