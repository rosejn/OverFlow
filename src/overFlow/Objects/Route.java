package overFlow.Objects;

import overFlow.main.Node;

public class Route extends Node{
	int[] intArray;
	public Route(String title, float tx, float ty, int[] iArray){
		super(title, tx, ty, 1, iArray.length);	//use input array length to set how many outputs
		intArray = iArray;
		
	}
	
	public Route(String title, float tx, float ty, float[] fArray){
		
	}
	
	
	public void update() {
		for(int i = 0; i < intArray.length - 1; i++)	{
			if(inputValues[i].getInt() == intArray[i]){
				outputValues[i] = inputValues[i];
			}
		}
		updateConnections();
	}
}
