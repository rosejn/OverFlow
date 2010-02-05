package overFlow.Objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;

import com.sun.scenario.scenegraph.SGText;

import overFlow.Atom.AtomFloat;
import overFlow.Tools.Tools;
import overFlow.main.Node;
import overFlow.main.OverFlowMain;

public class FloatDisp extends Node {
	SGText numberText = new SGText();
	Float floatVal = new Float(0);
	boolean dragging;

	public FloatDisp(float tx, float ty) {
		super("", tx, ty, 47, 20, 1, 1);
		numberText.setFont(new Font("Helvetica", Font.BOLD, 14));
		numberText.setLocation(new Point2D.Float((float) x + r - r / 4,(float) y + 15));
		numberText.setAntialiasingHint(RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		numberText.setText("0.0");
		numberText.setFillPaint(Color.GREEN);
		group.add(numberText);
	}

	public void update() {
		try {
			if (!dragging) {
				value = inputValues[0];
				try {
					if(value.getString() == "bang"){
						outputValues[0] = new AtomFloat(floatVal);
						updateConnections();
					}
				}catch (NullPointerException e){
					
				}
				floatVal = value.getFloat();
				String s = new String(floatVal.toString());
				float len = s.length();
				len = Tools.constrain(len, 3f, 5f);
				String subS = s.substring(0, (int) len);
				numberText.setText(subS);
				updateConnections();
				outputValues[0] = value;
			}
		} catch (NullPointerException e) {

		}

	}

	public void nodeDragged() {
		if (!OverFlowMain.editMode) {
			dragging = true;
			floatVal -= yVelocity;
			String s = new String(floatVal.toString());
			float len = s.length();
			len = Tools.constrain(len, 3f, 5f);
			String subS = s.substring(0, (int) len);
			numberText.setText(subS);
			value = new AtomFloat(floatVal);
			outputValues[0] = value;
			updateConnections();
			update();
		}
	}

	public void nodeReleased() {
		dragging = false;
	}
}
