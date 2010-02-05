package overFlow.Objects;

import overFlow.Atom.AtomFloat;
import overFlow.Tools.Tools;
import overFlow.main.Node;

public class Random extends Node {

	float rangeMin;
	float rangeMax;

		
		public Random(String t, float tx, float ty, float min, float max) {
			super(t, tx, ty, 3, 1);
			rangeMin = min;
			rangeMax = max;

		}
		
		public Random(String t, float tx, float ty) {
			super(t, tx, ty, 3, 1);
			rangeMin = 0;
			rangeMax = 0;

		}

		public void update() {
			if (inputValues[0] != null) {
				value = inputValues[0];
				value = new AtomFloat(Tools.random(rangeMin, rangeMax));
				outputValues[0] = value;
				updateConnections();
			}
		}
	} 