package overFlow.Objects;

import overFlow.Atom.Atom;
import overFlow.main.Node;

public class Change extends Node {
	Atom pastValue;

	public Change(float tx, float ty) {
		super("change", tx, ty, 1, 2);
	}

	public void update() {
		try {
			if (value.getFloat() != pastValue.getFloat()) {
				value = inputValues[0];
				outputValues[0] = value;
				updateConnections();
			}
			pastValue = inputValues[0];
		}

		catch (NullPointerException e) {
		}
	}
}
