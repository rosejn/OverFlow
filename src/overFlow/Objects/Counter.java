package overFlow.Objects;

import overFlow.Atom.AtomInt;
import overFlow.main.Node;

public class Counter extends Node {

	int counter = 0;
	float direction = 1;
	int maxCount;

	public Counter(String title, float tx, float ty, int mCount) {
		super(title, tx, ty, 4, 1);
		maxCount = mCount;
	}

	public void update() {
		try {
			direction = inputValues[1].getInt();
		}
		catch (NullPointerException e){
		}
		
			if(direction == 0) {
				counter--;
			}
			else if(direction == 1){
				counter++;
			}

		
		if (counter >= maxCount) {
			counter = 1;
		} else if (counter == 0) {
			counter = maxCount;

		}
		if (counter <= 0) {
			counter = maxCount;
		}
		outputValues[0] = new AtomInt(counter);
		updateConnections();
	}
}

