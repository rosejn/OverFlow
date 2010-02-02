package overFlow.main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.RenderingHints;
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
		Rectangle2D r = text.getBounds();
		
		tipBox.setShape(r);
		tipBox.setFillPaint(fillPaint);
		tipBox.setDrawPaint(drawPaint);
		tipBox.setMode(SGShape.Mode.STROKE_FILL);
		tipBox.setDrawStroke(new BasicStroke(1.0f));
		tipBox.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);
		tGroup.add(tipBox);
		tGroup.add(text);
		
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
	}
	
	void setLocation(float tx, float ty){
		x = tx;
		y = ty;
		tipBox.setTranslateX(tx);
		tipBox.setTranslateY(ty);
	}

	SGGroup getGroup() {
		return tGroup;
	}
}
