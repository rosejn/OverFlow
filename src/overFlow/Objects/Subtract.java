package overFlow.Objects;

import overFlow.main.Node;

public class Subtract extends Node {
	  float val_1;
	  float val_2;
	  float result;

	  public Subtract(float tx, float ty) {
	    super("  -", tx, ty, 2, 1);
	    val_1 = 0;
	    val_2 = 0;
	  }
	  public void update() {
	    if(inputValues[1] != null) {
	      val_2 = inputValues[1];
	    }
	    if(inputValues[0] != null) {
	      val_1 = inputValues[0];
	    }
	    value = val_1 - val_2;
	    outputValues[0] = value;
	    updateConnections(); 
	  }
	}