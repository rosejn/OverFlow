package overFlow.Objects.Math;

import overFlow.main.Node;

public class SQRT extends Node {
	  float val_1;
	  float val_2;
	  float result;

	  public SQRT(float tx, float ty) {
	    super("sqrt", tx, ty, 1, 1);
	  }
	  public void update() {
	    if(inputValues[0] != null) {
	      value = inputValues[0] * inputValues[0];
	    }
	    outputValues[0] = value;
	    updateConnections(); 
	  }
	  
}