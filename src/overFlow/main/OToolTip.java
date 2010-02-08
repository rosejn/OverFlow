package overFlow.main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import com.sun.scenario.scenegraph.SGGroup;
import com.sun.scenario.scenegraph.SGNode;
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
	InputPort inParent;
	SGGroup tGroup = new SGGroup();
	FXShape tipBox = new FXShape();
    SGTransform.Translate translateGroup = SGTransform.createTranslation(0,0, tGroup);
	SGText text = new SGText();
	OToolTip(OutputPort port){
		parent = port;
		tGroup.add(createBox());
		tGroup.add(createText());
		tGroup.setVisible(false);
		parent.portGroup.add(translateGroup);

	}
	

	public OToolTip(InputPort inputPort) {
		// TODO Auto-generated constructor stub
		inParent = inputPort;
		tGroup.add(createBox());
		tGroup.add(createText());
		tGroup.setVisible(false);
		inParent.portGroup.add(translateGroup);
	}


	SGNode createBox() {
		SGGroup group = new SGGroup();
		tipBox.setShape(new RoundRectangle2D.Float(0, 0, w + 20, h, 3, 3));
		tipBox.setTranslateX(x);
		tipBox.setTranslateY(y);
		tipBox.setFillPaint(new Color(230,230,170));
		tipBox.setMode(SGShape.Mode.STROKE_FILL);
		tipBox.setDrawStroke(new BasicStroke(1.0f));
		tipBox.setDrawPaint(new Color(50, 50, 50, 100));
		tipBox.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);
		group.add(tipBox);
		return group;
	}
	
	SGNode createText() {
		SGGroup tg = new SGGroup();
		text.setText("");
		tg.add(text);
		return tg;
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
		Point2D p = new Point2D.Float(tx + 12, ty + 32);
		text.setLocation(p);

	}

	SGNode getGroup() {
		return translateGroup;
	}
}
