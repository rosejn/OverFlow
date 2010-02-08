package overFlow.Objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;

import overFlow.Atom.AtomFloatArray;
import overFlow.Atom.AtomInt;
import overFlow.main.Node;
import overFlow.main.OverFlowMain;

import com.sun.scenario.scenegraph.SGGroup;
import com.sun.scenario.scenegraph.SGNode;
import com.sun.scenario.scenegraph.SGShape;
import com.sun.scenario.scenegraph.event.SGMouseAdapter;
import com.sun.scenario.scenegraph.fx.FXShape;

public class StepSequencer extends Node {
	Step[][] stepGroup;
	SGShape featuresBack = new SGShape();
	SGGroup steps;
	int tracks;
	int stepCount;
	float sw;
	float sh;
	float sr;
	FXShape base = new FXShape();
	PrefBtn preffs;
	boolean overFeatures = false;

	public StepSequencer(float tx, float ty, float tw, float th, float tsteps, float ttracks) {
		super("", tx, ty, tw, th, 2, 2);
		setScaleWidthOnly(false);
		fillColor = (new Color(200,200,200));
		x = 0;
		y = 0;
		tracks = (int) ttracks;
		stepCount = (int) tsteps;
		super.w = tw;
		super.h = th;
		w = tw;
		h = th;
		r = 6;
		setBaseColor(new Color(50, 50, 50));
		preffs = new PrefBtn(tx + tw - 30, ty - 7, 5);
	    this.setOutputToolTip(0,"output array of velocities");
	    
		base.setShape(new RoundRectangle2D.Float(0, 0, tw, th, r, r));
		stepGroup = new Step[(int) stepCount][(int) tracks];
		steps = new SGGroup();
		sw = (tw / stepCount);
		sh = (th / tracks);
		sr = (tw / stepCount) / 4;
		for (int i = 0; i < stepCount; i++) {
			for (int j = 0; j < tracks; j++) {
				stepGroup[i][j] = new Step(2 + (i * sw) + tx, 2 + (j * sh) + ty, sw - 4, sh - 4, sr);
				steps.add(stepGroup[i][j].callStep());
			}
		}
		title.setFont(new Font("Helvetica", Font.PLAIN, 10));
		title.setLocation(new Point2D.Float(12 + r, h + 18));

		featuresBack.setShape(new RoundRectangle2D.Float(tx + tw - 38, ty - 10, 30, 20, 6, 6)); // create extender backing for features buttons
		featuresBack.setFillPaint(new Color(75, 75, 75));
		featuresBack.setMode(SGShape.Mode.FILL);
		featuresBack.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);
		featuresBack.addMouseListener(new SGMouseAdapter() {
			public void mouseEntered(MouseEvent e, SGNode n) {
				if (OverFlowMain.editMode) {
					overFeatures = true;
					preffs.setVisible(true);
				}
			}

			public void mouseExited(MouseEvent e, SGNode n) {
				if (OverFlowMain.editMode) {
					overFeatures = false;
					if (!over) {
						preffs.setVisible(false);
					}
				}
			}
		});

		group.add(featuresBack);
		group.add(preffs.btnGroup);
		group.add(steps);
		group.add(title);
	}

	public void nodeOver() {
		if (OverFlowMain.editMode) {
			featuresBack.setVisible(true);
			preffs.setVisible(true);
		}
	}

	public void nodeExited() {
		if (OverFlowMain.editMode) {
			if (!overFeatures) {
				featuresBack.setVisible(false);
				preffs.setVisible(false);
			}
		}
	}

	
	public void update() {
			value = new AtomInt(inputValues[0].getInt());
			outputValues[0] = new AtomFloatArray(getStepArray(inputValues[0].getInt()));
			outputValues[1] = new AtomInt(getStepArray(inputValues[0].getInt()).length);
			updateConnections();
		
	}
	
	float[] getStepArray(int step) {
		float[] returnArray = new float[tracks];
		for(int i = 0; i < tracks; i++){
			returnArray[i] = stepGroup[step][i].getVelocity();
		}
		return returnArray;
		}
	}

class Step {
	float x;
	float y;
	float w;
	float h;
	float r;
	float defaultVelocity = 100;
	float velocity = 0;
	int counter = 0;

	boolean isAlive;

	FXShape step;

	Step(float tx, float ty, float tw, float th, float tr) {
		x = tx;
		y = ty;
		w = tw;
		h = th;
		r = tr;
		FXShape tstep = new FXShape();
		tstep.setShape(new RoundRectangle2D.Float(x, y, w, h, r, r));
		tstep.setFillPaint(new Color(150,150,150));
		tstep.setMode(SGShape.Mode.STROKE_FILL);
		tstep.setDrawStroke(new BasicStroke(1.0f));
		tstep.setDrawPaint(Color.GRAY);
		tstep.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);
		tstep.addMouseListener(new SGMouseAdapter() {
			Point pos;
			public void mouseEntered(MouseEvent e, SGNode n) {
				FXShape node = (FXShape) n;
				if (OverFlowMain.editMode == true) {

				}
				else {
					node.setDrawPaint(Color.white);
					if (OverFlowMain.frameDragged) {
						if (counter % 2 == 0) {
							node.setDrawPaint(Color.orange);
							node.setFillPaint(new Color(200, 240, 255));
							counter++;
							velocity = defaultVelocity;
						} else if (counter % 2 == 1) {
							node.setFillPaint(Color.GRAY);
							counter++;
							velocity = 0;
						}
					}
				}
			}

			public void mouseExited(MouseEvent e, SGNode n) {
				FXShape node = (FXShape) n;
				if (OverFlowMain.editMode == true) {

				} else {
					node.setDrawPaint(Color.GRAY);
				}
			}

			public void mousePressed(MouseEvent e, SGNode n) {
				FXShape node = (FXShape) n;
				pos = e.getPoint();

				if (OverFlowMain.editMode != true) {
					if (counter % 2 == 0) {
						node.setDrawPaint(Color.orange);
						node.setFillPaint(new Color(200, 240, 255));
					} else if (counter % 2 == 1) {
						node.setFillPaint(Color.GRAY);
					}
					counter++;
				}
			}
		});
		step = tstep;
	}

	SGNode callStep() {
		return step;
	}

	float getVelocity() {
		return velocity;
	}
	
}
