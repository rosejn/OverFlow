package overFlow.Objects;

import overFlow.Atom.AtomFloat;
import overFlow.main.Node;

public class Peak extends Node {

	float lastValue;
	float peakValue;

	public Peak(float tx, float ty) {
		super("peak", tx, ty, 2, 2);

	}

	public void update() {
		try {
			if (inputValues[0].getFloat() > peakValue) {
				try {
					peakValue = inputValues[0].getFloat();
					value = new AtomFloat(peakValue);
					outputValues[0] = value;
					updateConnections();
				} catch (NullPointerException e) {

				}
			}
		} catch (NullPointerException e) {

		}

		if (inputValues[1] != null) {
			try {
				if (inputValues[1].getString() == "bang") {
					peakValue = 0;
					value = new AtomFloat(peakValue);
					updateConnections();
				}
			} catch (NullPointerException e) {
				System.out.println("must use a bang to reset");
			}
		}
	}
}
