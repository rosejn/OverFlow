package overFlow.Objects.Math;

import overFlow.Tools.Tools;
import overFlow.main.Node;

public class Drunk extends Node {
	float val_1;
	float val_2;
	float result;

	public Drunk(float tx, float ty) {
		super("drunk", tx, ty, 2, 1);
		val_1 = 0;
		val_2 = 1;
	}

	public void update() {
		if (inputValues[1] != null) {
			val_2 = inputValues[1];
		}
		if (inputValues[0] != null) {
			val_1 = inputValues[0];
			value = (float) val_1 + (Tools.random(-(val_2 / 2), val_2 / 2));
			outputValues[0] = value;
			updateConnections();
		}
	}
}