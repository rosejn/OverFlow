package overFlow.Objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import com.sun.scenario.scenegraph.SGText;
import overFlow.Atom.AtomFloat;
import overFlow.Atom.AtomInt;
import overFlow.main.Node;
import overFlow.main.OverFlowMain;

public class IntDisp extends Node {
	SGText numberText = new SGText();
	Integer intVal = new Integer(0);
	boolean dragging;

	public IntDisp(float tx, float ty) {
		super("", tx, ty, 47, 20, 1, 1);
		numberText.setFont(new Font("Helvetica", Font.BOLD, 14));
		numberText.setLocation(new Point2D.Float((float) x + r - r / 4,(float) y + 15));
		numberText.setAntialiasingHint(RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		numberText.setText("0");
		numberText.setFillPaint(Color.GREEN);
		group.add(numberText);
		this.setOutputToolTip(0,"int value output");
	}

	public void update() {
		try {
			if (!dragging) {
				value = inputValues[0];
				try {
					if(value.getString() == "bang"){
						outputValues[0] = new AtomInt(intVal);
						updateConnections();
					}
				}catch (NullPointerException e){
					
				}
				intVal = value.getInt();
				String s = new String(intVal.toString());
				numberText.setText(s);
				updateConnections();
				outputValues[0] = value;
			}
		} catch (NullPointerException e) {

		}

	}

	public void nodeDragged() {
		if (!OverFlowMain.editMode) {
			dragging = true;
			intVal -= (int)yVelocity;
			String s = new String(intVal.toString());
			numberText.setText(s);
			value = new AtomFloat(intVal);
			outputValues[0] = value;
			updateConnections();
			update();
		}
	}

	public void nodeReleased() {
		dragging = false;
	}
}
