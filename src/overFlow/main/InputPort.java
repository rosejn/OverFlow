package overFlow.main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import javax.swing.JToolTip;

import com.sun.scenario.scenegraph.SGNode;
import com.sun.scenario.scenegraph.SGGroup;
import com.sun.scenario.scenegraph.SGShape;
import com.sun.scenario.scenegraph.SGTransform;
import com.sun.scenario.scenegraph.event.SGMouseAdapter;
import com.sun.scenario.scenegraph.fx.FXShape;

public class InputPort {
	FXShape port = new FXShape();
	FXShape portHighlight = new FXShape();
	SGGroup portGroup = new SGGroup();
	float x, y, w;
	int ID;
	Node parent;
	boolean toolTipActive = false;
	OToolTip toolTip = new OToolTip(this);
	protected SGTransform.Translate portTranslate = SGTransform.createTranslation(0,0, portGroup);
	
	public InputPort(float tx, float ty, float tw, Node tParent,
			int connectionId) {
		x = tx;
		y = ty;
		w = tw;
		ID = connectionId;
		parent = tParent;
		createPort();
		createPortHighlight();
		portGroup.add(port);
		portGroup.add(portHighlight);
		parent.group.add(portTranslate);
	}

	void createPortHighlight() {

		Ellipse2D shape = new Ellipse2D.Float(0, 0, w * 2, w * 2);
		portHighlight.setShape(shape);
		portHighlight.setTranslateX(x - w / 2);
		portHighlight.setTranslateY(y - w);
		portHighlight.setDrawPaint(Color.GREEN);
		portHighlight.setFillPaint(new Color(255, 255, 255, 0));
		portHighlight.setMode(SGShape.Mode.STROKE_FILL);
		portHighlight.setDrawStroke(new BasicStroke(2));
		portHighlight.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);
		portHighlight.setOpacity(0.0f);
		

		
		portHighlight.addMouseListener(new SGMouseAdapter() {
			public void mouseEntered(MouseEvent e, SGNode n) {
				if (OverFlowMain.editMode) {
					FXShape node = (FXShape) n;
					node.setOpacity(0.7f);
					OverFlowMain.connectionOver = true;
					OverFlowMain.currentInputShape = node;
					OverFlowMain.currentInputObject = parent;
					OverFlowMain.currentInputIO = returnThis();
					OverFlowMain.inputConnectionOver = true;

				}
			}

			public void mousePressed(MouseEvent e, SGNode n) {
			}

			public void mouseExited(MouseEvent e, SGNode n) {
				if (OverFlowMain.editMode) {
					FXShape node = (FXShape) n;
					node.setOpacity(0.0f);
					OverFlowMain.connectionOver = false;
					OverFlowMain.inputConnectionOver = false;
				}
			}

			public void mouseReleased(MouseEvent e, SGNode n) {
			}
		});
	}

	void createPort() {
		port.setShape(new Line2D.Float(0, 0, w, 0f));
		port.setTranslateX(x);
		port.setTranslateY(y);
		port.setDrawPaint(Color.white);
		port.setMode(SGShape.Mode.STROKE_FILL);
		port.setDrawStroke(new BasicStroke(2));
		port.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);
	}

	void setTranslateX(float x){
		portTranslate.setTranslateX(x);
	}

	void setTranslateY(float y){
		portTranslate.setTranslateY(y);
	}
	
	int returnID() {
		return ID;
	}

	public void hide() {
		portHighlight.setOpacity(0.0f);
	}

	InputPort returnThis() {
		return this;
	}

	public void setTranslation(float tx, float ty) {
		// TODO Auto-generated method stub
		portTranslate.setTranslateX(tx);
		portTranslate.setTranslateY(ty);
	}

	public void translateXBy(float tx) {
		// TODO Auto-generated method stub
		portTranslate.setTranslateX(x += tx);
	}
	
	void setToolTip(boolean b){
		toolTipActive = b;
	}
	
	void setToolTipText(String s){
		toolTipActive = true;
		toolTip.setText(s);
	}
	void setToolTipFill(Color c){
		toolTip.setFill(c);
	}
	void setToolTipDraw(Color c){
		toolTip.setDraw(c);
	}	
}
