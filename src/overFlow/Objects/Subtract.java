package overFlow.Objects;

import overFlow.Atom.AtomFloat;
import overFlow.main.Node;

public class Subtract extends Node {
	  public Subtract(float tx, float ty) {
	    super("  -", tx, ty, 2, 1);
	  }
		public void update() {
			try {
				value = new AtomFloat(inputValues[0].getFloat() - inputValues[1].getFloat());
				outputValues[0] = value;
				updateConnections();
			} catch (NullPointerException e) {
				
			}
		}
	}
