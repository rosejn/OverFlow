package overFlow.Objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Ellipse2D;

import javax.swing.border.EmptyBorder;

import overFlow.main.Node;

import com.sun.scenario.scenegraph.JSGPanel;
import com.sun.scenario.scenegraph.SGComponent;
import com.sun.scenario.scenegraph.SGGroup;
import com.sun.scenario.scenegraph.SGNode;
import com.sun.scenario.scenegraph.SGShape;
import com.sun.scenario.scenegraph.SGTransform;

public class PLCD extends Node{
	SGGroup panelGroup = new SGGroup();
	SGTransform.Translate lcdTransform = SGTransform.createTranslation(0, 0, panelGroup);
	public PLCD(float tx, float ty) {
	    super("", tx, ty, 300,300, 1, 2);
	    baseRect.setFillPaint(new Color(190,190,190));
	    panelGroup.add(createShapePanel());
	    lcdTransform.setTranslateX(x + 5);
	    lcdTransform.setTranslateY(y + 5);
	    group.add(lcdTransform);
	    this.setOutputToolTip(1,"output mouse values");
	}	

	private SGNode createShape() {
		SGShape circle = new SGShape();
		circle.setShape(new Ellipse2D.Float(-20f, -20f, 40f, 40f));
		circle.setFillPaint(Color.YELLOW);
		circle.setMode(SGShape.Mode.STROKE_FILL);
		circle.setDrawStroke(new BasicStroke(0.15f));
		circle.setDrawPaint(Color.black);
		SGGroup group = new SGGroup();
		group.add(circle);
		return group;
	}

	private SGComponent createShapePanel() {
		JSGPanel panel = new JSGPanel();
		panel.setBorder(new EmptyBorder(4, 4, 4, 4));
		panel.setBackground(Color.white);
		panel.setScene(createShape());
		panel.setPreferredSize(new Dimension(290,290));
		SGComponent comp = new SGComponent();
		comp.setComponent(panel);
		return comp;
	}

}