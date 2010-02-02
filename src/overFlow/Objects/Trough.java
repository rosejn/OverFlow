package overFlow.Objects;

import overFlow.main.Node;

public class Trough extends Node {

	float lastValue;
		
	public Trough(float tx, float ty) {
		super("trough", tx, ty, 2, 2);

	}

	public void update() {
		if (inputValues[0] != null) {
			if(inputValues[0] < lastValue){
				value = inputValues[0];
				lastValue = inputValues[0];
			}
			else {
				outputValues[1] = value;
			}
		}

		if (inputValues[1] != null) {
			if(inputValues[1] == -9999f) {
				lastValue = 0;
			}
		}
		outputValues[0] = value;
		updateConnections();
	}
}
