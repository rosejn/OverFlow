package overFlow.Objects;

import overFlow.main.Node;

public class Map extends Node {

	float srcMin;
	float srcMax;
	float tgtMin;
	float tgtMax;
	float returnVal;

	public Map(String title, float tx, float ty, float sMin, float sMax, float tMin, float tMax) {
		super(title, tx, ty, 5, 1);
		srcMin = sMin;
		srcMax = sMax;
		tgtMin = tMin;
		tgtMax = tMax;

	}

	public void update() {
		if (inputValues[0] != null) {
			value = inputValues[0];
			float scaleFactor = (tgtMax - tgtMin) / (srcMax - srcMin);
			float scaledValue = (value - srcMin) * scaleFactor + tgtMin;
			value = scaledValue;
		}
		outputValues[0] = value;
		updateConnections();
	}
}
