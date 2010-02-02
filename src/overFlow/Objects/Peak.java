package overFlow.Objects;

import overFlow.main.Node;

public class Peak extends Node {

	float lastValue;

	public Peak(float tx, float ty) {
		super("peak", tx, ty, 2, 2);

	}

	public void update() {
		if (inputValues[0] != null) {
			if (inputValues[0] > lastValue) {
				value = inputValues[0];
				outputValues[0] = value;
				lastValue = inputValues[0];
			}
		}

		if (inputValues[1] != null) {
			if (inputValues[1] == -9999f) {
				lastValue = 0;
			}
		}
		outputValues[1] = value;
		updateConnections();
	}
}
