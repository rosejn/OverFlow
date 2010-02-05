package overFlow.Objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;

import com.sun.scenario.scenegraph.SGGroup;
import com.sun.scenario.scenegraph.SGShape;
import com.sun.scenario.scenegraph.fx.FXShape;

import overFlow.Atom.AtomString;
import overFlow.main.Node;
import overFlow.main.OverFlowMain;

public class Bang extends Node {
	Color bangHitFillPaint = Color.white;
	Color bangFillPaint = Color.yellow;
	Color bangDrawPaint = new Color(100,100,100);
	FXShape circle = new FXShape();

	public Bang(float tx, float ty) {
		super("", tx, ty, 30, 30, 1, 1);
		this.addToGroup(createBang());
		value = new AtomString("bang");
	}

	public void update() {
		outputValues[0] = value;
		bangOn();
		bangOff();
		updateConnections();
	}
	
	public void nodePressed() {
		if(!OverFlowMain.editMode){
			bangOn();
			update();
		}
	}

	public void nodeReleased() {
		bangOff();
	}
	
	
	private void bangOn() {
		circle.setFillPaint(bangHitFillPaint);
	}

	private void bangOff() {
		circle.setFillPaint(bangFillPaint);
	}

	SGGroup createBang() {
		SGGroup g = new SGGroup();
		circle.setShape(new Ellipse2D.Float(x + 3, y + 3, w - 6, h - 6));
		bangOff();
		circle.setDrawPaint(bangDrawPaint);
		circle.setMode(SGShape.Mode.STROKE_FILL);
		circle.setDrawStroke(new BasicStroke(2.0f));
		circle.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.add(circle);
		
		return g;
	}
}
