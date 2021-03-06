package overFlow.Objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;

import overFlow.main.Node;

import com.sun.scenario.scenegraph.SGShape;
import com.sun.scenario.scenegraph.fx.FXShape;

public class MultiSlider extends Node {

	public int numSliders;
	FXShape[] slider;
	float sliderWidth;

	public MultiSlider(float tx, float ty, float tw, float th, int numSldrs) {
		super("", tx, ty, tw, th, 1, 1);
		numSliders = numSldrs;
		slider = new FXShape[numSliders];
		createSliders();

		sliderWidth = tw / numSliders + 1;

	}

	public void createSliders() {
		for (int i = 0; i < numSliders; i++) {
			slider[i] = new FXShape();
			Path2D p = new Path2D.Float(sliderPath(0, y + 8));
			slider[i].setShape(new Path2D.Float(p));
			slider[i].setTranslateX(tx + (i * sliderWidth + 5));
			slider[i].setTranslateY(ty + 5);
			slider[i].setFillPaint(Color.GRAY);
			slider[i].setDrawStroke(new BasicStroke(1.0f));
			slider[i].setMode(SGShape.Mode.STROKE_FILL);
			slider[i].setDrawStroke(new BasicStroke(1.0f));
			slider[i].setDrawPaint(Color.GREEN);
			slider[i].setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);
			group.add(slider[i]);
		}
	}

	Path2D sliderPath(float mx, float my) {
		float x1 = sliderWidth - 18;
		float y1 = my - y - 3;
		float x2 = x1;
		float y2 = h - 16;
		float x3 = 3;
		float y3 = y2;
		float x4 = x3;
		float y4 = y1;
		Path2D path = new Path2D.Double();
		path.moveTo(x1, y1);
		path.lineTo(x2, y2);
		path.lineTo(x3, y3);
		path.lineTo(x4, y4);
		path.closePath();
		return path;
	}
}
