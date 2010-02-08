package overFlow.Objects;

import java.awt.Color;
import java.util.regex.Pattern;

import overFlow.Atom.Atom;
import overFlow.Atom.AtomArray;
import overFlow.Atom.AtomFloat;
import overFlow.Atom.AtomInt;
import overFlow.Atom.AtomString;
import overFlow.main.Node;
import overFlow.main.OverFlowMain;

public class Message extends Node {
	String messageString;
	AtomArray outputArray = new AtomArray();

	public Message(String mString, float tx, float ty) {
		super(mString, tx, ty, 2, 1);
		messageString = mString;
		baseRect.setFillPaint(new Color(190, 190, 190));
		buildArray(mString);
		this.setOutputToolTip(0, "message output");

	}

	public void setSelected(boolean s) {
		selected = s;
		if (selected) {
			baseRect.setFillPaint(new Color(220, 220, 220));
			if (!OverFlowMain.currentSelectedObjects.contains(this)) {
				OverFlowMain.currentSelectedObjects.add(this);
			}
		} else {
			baseRect.setFillPaint(new Color(190, 190, 190));
			OverFlowMain.currentSelectedObjects.remove(this);
		}
	}

	public void update() {
		outputValues[0] = outputArray;
		updateConnections();
	}

	public void nodePressed() {
		update();
	}
	
	public Atom buildArray(String mString){
		Atom atom = new Atom();
		
		
		
		
		return atom;
	}
}
