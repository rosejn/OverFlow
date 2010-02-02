package overFlow.Objects;

import overFlow.main.Node;

public class Change extends Node {
	float pastValue = 0;
	
	public Change(float tx, float ty) {
		super("change", tx, ty, 1, 2);
	}
	
		  public void update() {
		    if(inputValues[0] != null) {
		      if(value != pastValue){
		    	value = inputValues[0];
		      }
		      pastValue = inputValues[0];
		    }

		    outputValues[0] = value;
		    updateConnections(); 
		  }

}
