package overFlow.Objects;

/* Need to change draw/erase so that when you start a new note, everything is in draw
 * and when you start by erasing, everything after is erased
 */




import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import overFlow.main.Node;
import overFlow.main.OverFlowMain;

import com.sun.scenario.scenegraph.SGGroup;
import com.sun.scenario.scenegraph.SGNode;
import com.sun.scenario.scenegraph.SGShape;

import com.sun.scenario.scenegraph.event.SGMouseAdapter;
import com.sun.scenario.scenegraph.fx.FXShape;

public class NoteSequencer extends Node {
	private boolean blackKeyOver = false;
	private float widthUpdateVal;
	FXShape[] keys;
	NoteSeqStep[] keyStep;
	Float[] stepVelocity;
	FXShape smallEllipse = new FXShape();
	SGGroup subGroup = new SGGroup();
	PrefBtn preffs;
	SGGroup whiteKetStepGroup = new SGGroup();
	float cx, cy, px, py;
	private boolean overSharpKey = false;

	// Color Variables
	Color keyHighlightColor = Color.orange;
	Color keyFillColor = Color.white;
	Color keyHitFillColor = Color.green;
	Color keyStrokeColor = Color.black;
	Color sharpKeyFillColor = Color.black;
	Color sharpKeyStrokeColor = Color.gray;
	Color stepCountColor = Color.cyan;
	Color stepHighlightFillColor = Color.green;	

	// Stroke Sizes
	private float strokeSize = 0.5f;
	private float highlightStrokeSize = 1.5f;

	// Setup variables
	private int numOctaves;
	private int numSteps;
	private float lastValue;

	public NoteSequencer(float tx, float ty, int octaves, int steps) {
		super("", tx, ty, 400, 400, 1, 1);
		numOctaves = octaves + 1;			//add one for easier time dealing with the loops later
		numSteps = steps;
		widthUpdateVal = (w / (numSteps + 2) * 2) + (numSteps * (w / (numSteps + 2)) + 3);
		createKeys();
		createSequencer();
		super.setBaseColor(keyStrokeColor);
		baseRect.setShape(new RoundRectangle2D.Float(0, 0, widthUpdateVal, 400 + 2, r, r));
		baseRect.setTranslateY(y - 2);
		preffs = new PrefBtn(x + w - 8, y + 4, 5);
		group.add(preffs.btnGroup);
	}

	public NoteSequencer(float ox, float oy) {
		// TODO Auto-generated constructor stub
		super("", ox, oy, 400, 300, 1, 1);
		numOctaves = 4 + 1;			//add one for easier time dealing with the loops later
		numSteps = 32;
		widthUpdateVal = (w / (numSteps + 2) * 2) + (numSteps * (w / (numSteps + 2)) + 3);
		createKeys();
		createSequencer();
		super.setBaseColor(keyStrokeColor);
		baseRect.setShape(new RoundRectangle2D.Float(0, 0, widthUpdateVal, 300 + 2, r, r));
		baseRect.setTranslateY(y - 2);
		preffs = new PrefBtn(x + w - 8, y + 4, 5);
		group.add(preffs.btnGroup);
	}

	void createKeys() {
//		SGGroup keyGroup = new SGGroup();
		SGGroup bGroup = new SGGroup();
		SGGroup wGroup = new SGGroup();
		FXShape[] keys = new FXShape[numOctaves * 12];
		float keyHeight = h / (12 * (numOctaves - 1));
		float keyWidth = (w / (numSteps + 2) * 2);

		for (int k = 0; k < numOctaves - 1; k++) {
			for (int j = 0; j < 12; j++) {
				keys[j + (k * 12)] = new FXShape();
				if (j == 1 || j == 3 || j == 6 || j == 8 || j == 10) {
					bGroup.add(keys[j + (k * 12)]);
					keyFillColor = Color.black;
					keys[j + (k * 12)].setFillPaint(Color.BLACK);
					keys[j + (k * 12)].setShape(new Rectangle2D.Double(0, 0,keyWidth - keyWidth/3, keyHeight + (keyHeight / 4)));
					keys[j + (k * 12)].setTranslateX(x + 2);
					keys[j + (k * 12)].setTranslateY((((k * 12) + j) * keyHeight) + y	- keyHeight / 4 - keyHeight / 16);
					keys[j + (k * 12)].setDrawPaint(keyStrokeColor);
					keys[j + (k * 12)].setMode(SGShape.Mode.STROKE_FILL);
					keys[j + (k * 12)].setDrawStroke(new BasicStroke(strokeSize));
					keys[j + (k * 12)].setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);
					keys[j + (k * 12)].addMouseListener(new SGMouseAdapter() {
						public void mouseEntered(MouseEvent e, SGNode n) {
							FXShape node = (FXShape) n;
							blackKeyOver = true;
							node.setDrawStroke(new BasicStroke(highlightStrokeSize));
							node.setDrawPaint(Color.orange);
							overSharpKey = true;
						}

						public void mouseExited(MouseEvent e, SGNode n) {
							FXShape node = (FXShape) n;
							blackKeyOver = false;
							node.setDrawStroke(new BasicStroke(strokeSize));
							node.setDrawPaint(keyStrokeColor);
							overSharpKey = false;
						}

						public void mousePressed(MouseEvent e, SGNode n) {
							FXShape node = (FXShape) n;
							node.setDrawPaint(Color.red);
						}
						
						public void mouseReleased(MouseEvent e, SGNode n) {
							FXShape node = (FXShape) n;
							node.setDrawPaint(sharpKeyFillColor);
						}
					});
				}
				
				else {
					keyFillColor = Color.white;
					keys[j + (k * 12)].setFillPaint(Color.white);
					wGroup.add(keys[j + (k * 12)]);
				
					if (j == 0) {
						keys[j + (k * 12)].setShape(new Rectangle2D.Double(0, 0, keyWidth, keyHeight + keyHeight / 2 + keyHeight / 4 - keyHeight / 4 - keyHeight / 16));
					
						if (k == 0){
							keys[j + (k * 12)].setTranslateY((((k * 12) + j) * keyHeight) + y);
						}
						
						else {
							keys[j + (k * 12)].setTranslateY((((k * 12) + j) * keyHeight) + y - keyHeight / 16);
								}
						keys[j + (k * 12)].setTranslateX(x + 2);
					}
					if (j == 2) {
						keys[j + (k * 12)].setShape(new Rectangle2D.Double(0,0, keyWidth, keyHeight + keyHeight / 2 + keyHeight / 4 + keyHeight / 8 + keyHeight / 16));
						keys[j + (k * 12)].setTranslateX(x + 2);
						keys[j + (k * 12)].setTranslateY((((k * 12) + j) * keyHeight) + y - keyHeight / 2 - keyHeight / 16);
					}
					if (j == 4) {
						keys[j + (k * 12)].setShape(new Rectangle2D.Double(0, 0, keyWidth, keyHeight + keyHeight / 2 - keyHeight / 16));
						keys[j + (k * 12)].setTranslateX(x + 2);
						keys[j + (k * 12)].setTranslateY((((k * 12) + j) * keyHeight) + y - keyHeight / 2 - keyHeight / 16);
					}
					if (j == 5) {
						keys[j + (k * 12)].setShape(new Rectangle2D.Double(0, 0, keyWidth, keyHeight + keyHeight / 2));
						keys[j + (k * 12)].setTranslateX(x + 2);
						keys[j + (k * 12)].setTranslateY((((k * 12) + j) * keyHeight) + y - keyHeight / 16);
					}
					if (j == 7) {
						keys[j + (k * 12)].setShape(new Rectangle2D.Double(0, 0, keyWidth, keyHeight + keyHeight - keyHeight / 16));
						keys[j + (k * 12)].setTranslateX(x + 2);
						keys[j + (k * 12)].setTranslateY((((k * 12) + j) * keyHeight) + y - keyHeight / 2);
					}
					if (j == 9) {
						keys[j + (k * 12)].setShape(new Rectangle2D.Double(0, 0, keyWidth, keyHeight + keyHeight / 2 + keyHeight / 4 + keyHeight / 8 + keyHeight / 8));
						keys[j + (k * 12)].setTranslateX(x + 2);
						keys[j + (k * 12)].setTranslateY((((k * 12) + j) * keyHeight) + y - keyHeight / 2 - keyHeight / 16);
					}
					if (j == 11) {
						keys[j + (k * 12)].setShape(new Rectangle2D.Double(0, 0, keyWidth, keyHeight + keyHeight / 2));
						keys[j + (k * 12)].setTranslateX(x + 2);
						keys[j + (k * 12)].setTranslateY((((k * 12) + j) * keyHeight) + y - keyHeight / 2 - keyHeight / 8);
					}
				keys[j + (k * 12)].setDrawPaint(keyStrokeColor);
				keys[j + (k * 12)].setMode(SGShape.Mode.STROKE_FILL);
				keys[j + (k * 12)].setDrawStroke(new BasicStroke(strokeSize));
				keys[j + (k * 12)].setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);
				keys[j + (k * 12)].addMouseListener(new SGMouseAdapter() {
					public void mouseEntered(MouseEvent e, SGNode n) {
						FXShape node = (FXShape) n;
						blackKeyOver = true;
						node.setDrawStroke(new BasicStroke(highlightStrokeSize));
						node.setDrawPaint(Color.orange);
					}

					public void mouseExited(MouseEvent e, SGNode n) {
						FXShape node = (FXShape) n;
						blackKeyOver = false;
						node.setDrawStroke(new BasicStroke(strokeSize));
						node.setDrawPaint(keyStrokeColor);
					}

					public void mousePressed(MouseEvent e, SGNode n) {
						if(!overSharpKey){
						FXShape node = (FXShape) n;
						node.setDrawPaint(keyHitFillColor);
						}
					}
					
					public void mouseReleased(MouseEvent e, SGNode n) {
						FXShape node = (FXShape) n;
						node.setDrawPaint(keyFillColor);
					}
				});
			}
		}
	}
		group.add(wGroup);
		group.add(bGroup);
	}

	void createSequencer() {
		
		SGGroup seqGroup = new SGGroup();
		keyStep = new NoteSeqStep[(numOctaves * 12) * (numSteps)];
		stepVelocity = new Float[(numOctaves * 12) * (numSteps)];
		float stepHeight = h / (12 * (numOctaves - 1));
		float stepWidth = w / (numSteps + 2);

		for (int i = 0; i < numSteps; i++) {
			for (int k = 0; k < numOctaves - 1; k++) {
				for (int j = 0; j < 12; j++) {
					stepVelocity[j + (k * 12)] = 0f;
					keyStep[j + (k * 12)] = new NoteSeqStep(stepWidth - 1, stepHeight - stepHeight / 8);
					keyStep[j + (k * 12)].setTranslateX(x + 2 + (i * stepWidth) + ((w / numSteps + 2) * 2) - stepWidth/2);
					keyStep[j + (k * 12)].setTranslateY((((k * 12) + j) * stepHeight) + y);
					if (j == 1 || j == 3 || j == 6 || j == 8 || j == 10) {
						keyStep[j + (k * 12)].setFillPaint(Color.darkGray);
					}

					else {
						keyStep[j + (k * 12)].setFillPaint(Color.lightGray);
					}

					seqGroup.add(keyStep[j + (k * 12)].callStep());
				}
			}
		}
		group.add(seqGroup);
	}

	public void nodeOver() {
		if (OverFlowMain.editMode) {
			preffs.setVisible(true);
		}
	}

	public void nodeExited() {
		if (OverFlowMain.editMode) {
			preffs.setVisible(false);
		}
	}
	
	public void setCount(float countNumber){
		for(int i = 0; i < numOctaves * 12; i++){
			keyStep[(int) ((countNumber * 12) + i)].setDrawPaint(keyStrokeColor);	
		}
	}

	public void setClearCount(float countNumber){
		for(int i = 0; i < numOctaves * 12; i++){
			keyStep[(int) ((countNumber * 12) + i)].setDrawPaint(stepCountColor);	
		}
	}
	
	public void update() {
		if (inputValues[0] != null) {
	//		value = inputValues[0];
			setClearCount(lastValue);
			setCount(value);
			outputValues[0] = value;
			redrawNode();
			updateConnections();
			lastValue = value;
		}
	}
}



class NoteSeqStep {
	float x;
	float y;
	float w;
	float h;
	float r;
	float defaultVelocity = 100;
	float velocity = 0;
	int counter = 0;
	Color keyStrokeColor = Color.gray;
	boolean isAlive;
	Color fillColor;
	FXShape step = new FXShape();

	NoteSeqStep(float tw, float th) {
		step.setShape(new Rectangle2D.Double(0, 0,tw, th));	
		step.setMode(SGShape.Mode.STROKE_FILL);
		step.setDrawStroke(new BasicStroke(0.5f));
		step.setFillPaint(Color.lightGray);
		step.setDrawPaint(Color.GRAY);
		step.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);
		step.addMouseListener(new SGMouseAdapter() {
			Point pos;

			public void mouseEntered(MouseEvent e, SGNode n) {
				FXShape node = (FXShape) n;
				if (OverFlowMain.editMode == true) {

				}
				else {
					node.setDrawPaint(Color.blue);
					node.setDrawStroke(new BasicStroke(2f));
					if (OverFlowMain.frameDragged) {
						if (counter % 2 == 0) {
							node.setDrawPaint(Color.orange);
							node.setFillPaint(new Color(200, 240, 255));
							counter++;
							velocity = defaultVelocity;
						} else if (counter % 2 == 1) {
							node.setFillPaint(fillColor);
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
					step.setDrawStroke(new BasicStroke(0.5f));
					node.setDrawPaint(Color.gray);
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
	}
	
	void setTranslateX(float tx){
		step.setTranslateX(tx);
	}
	
	void setTranslateY(float ty) {
		step.setTranslateY(ty);
	}

	void setFillPaint(Color c) 	{
		fillColor = c;
		step.setFillPaint(c);
	}

	void setDrawPaint(Color c) 	{
		step.setDrawPaint(c);
	}
	
	SGNode callStep() {
		return step;
	}

}
