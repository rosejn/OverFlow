package overFlow.Objects;

import overFlow.main.Node;

public class Gate extends Node {

	int gateToggle = 0;

	public Gate(float tx, float ty) {
		super("gate", tx, ty, 2, 1);

	}

	public void update() {
		try {
			if (gateToggle == 1) {
				value = inputValues[1];
				outputValues[0] = value;
				updateConnections();
			}
		} catch (NullPointerException e) {
		}
		try {
			gateToggle = inputValues[0].getInt();
		} catch (NullPointerException e) {
		}
	}
}
