package overFlow.Objects;

import overFlow.Atom.AtomFloat;
import overFlow.Tools.Tools;
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
		try{
			value = inputValues[0];
			value = new AtomFloat(Tools.map(value.getFloat(), srcMin, srcMax, tgtMin, tgtMax));
			outputValues[0] = value;
			updateConnections();
		}catch (NullPointerException e){
			
		}

	}
}
