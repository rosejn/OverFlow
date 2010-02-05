package overFlow.main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import com.sun.scenario.scenegraph.SGGroup;
import com.sun.scenario.scenegraph.SGShape;
import com.sun.scenario.scenegraph.SGText;
import com.sun.scenario.scenegraph.SGTransform;
import com.sun.scenario.scenegraph.fx.FXShape;

public class OToolTip {
	
	float x;
	float y;
	float w;
	float h;
	float maxWidth;
	float maxHeight;
	Color fillPaint = new Color(200,200,255,150);
	Color drawPaint = new Color(0,0,0);
	float strokeWidth = 1;
	
	OutputPort parent;
	SGGroup tGroup = new SGGroup();
	FXShape tipBox = new FXShape();
	SGText text = new SGText();
    SGTransform.Translate tTGroup = SGTransform.createTranslation(0,0, tGroup);

	OToolTip(OutputPort port){
		parent = port;
		parent.portGroup.add(tGroup);

		tipBox.setFillPaint(fillPaint);
		tipBox.setDrawPaint(drawPaint);
		tipBox.setMode(SGShape.Mode.STROKE_FILL);
		tipBox.setDrawStroke(new BasicStroke(1.0f));
		tipBox.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);

		tGroup.add(text);
		tGroup.add(tipBox);
		tGroup.setVisible(false);
	}

	void setVisible(boolean b){
		tGroup.setVisible(b);
	}
	
	void setFill(Color c){
		tipBox.setFillPaint(c);
	}
	
	void setDraw(Color c){
		tipBox.setDrawPaint(c);
	}
	
	void setText(String s){
		text.setText(s);
		Rectangle2D r = text.getBounds();
		tipBox.setShape(r);
	}
	
	void setLocation(float tx, float ty){
		x = tx;
		y = ty;
		Point2D p = new Point2D.Float(tx + 25, ty + 20);
		text.setLocation(p);
		tipBox.setTranslateX(tx + 25);
		tipBox.setTranslateY(ty + 20);
	}

	SGGroup getGroup() {
		return tGroup;
	}
}
