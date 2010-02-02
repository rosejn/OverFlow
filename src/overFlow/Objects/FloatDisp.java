package overFlow.Objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;

import com.sun.scenario.scenegraph.SGText;

import overFlow.Tools.Tools;
import overFlow.main.Node;
import overFlow.main.OverFlowMain;

public class FloatDisp extends Node {
	SGText numberText = new SGText();

	public FloatDisp(float tx, float ty) {
		super("", tx, ty, 47, 20, 1, 1);
		numberText.setFont(new Font("Helvetica", Font.BOLD, 14));
		numberText.setLocation(new Point2D.Float((float) x + r - r / 4, (float) y + 15));
		numberText.setAntialiasingHint(RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		numberText.setText("0");
		numberText.setFillPaint(Color.GREEN);
		group.add(numberText);
		value = 0f;
	}

	public void update() {
		if(inputValues[0] != null) {
			if(inputValues[0] != -9999f) {
				value = inputValues[0];
				String s = new String(Float.toString(value));
				float len = s.length();
				len = Tools.constrain(len, 3f, 5f);
				String subS = s.substring(0, (int) len);
				numberText.setText(subS);	
				
			}
			else {

				updateConnections();
			}
			outputValues[0] = value;
		}
	}

	public void nodeDragged() {
		if (!OverFlowMain.editMode) {
			value -= yVelocity;
			String s = new String(Float.toString(value));
			float len = s.length();
			len = Tools.constrain(len, 3f, 5f);
			String subS = s.substring(0, (int) len);
			numberText.setText(subS);
			outputValues[0] = value;
			updateConnections();
			update();
		}
	}
}
