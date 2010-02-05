package overFlow.main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.lang.Math;

import overFlow.Atom.Atom;

import com.sun.scenario.effect.DropShadow;
import com.sun.scenario.scenegraph.SGGroup;
import com.sun.scenario.scenegraph.SGNode;
import com.sun.scenario.scenegraph.SGShape;
import com.sun.scenario.scenegraph.event.SGMouseAdapter;
import com.sun.scenario.scenegraph.fx.FXShape;

public class Connection {
	int connectionId;
	boolean isActive = true;
	boolean creating = false;
	boolean isNew;
	boolean over = false;
	boolean selected = false;
	FXShape connectionLine = new FXShape();
	FXShape lineListener = new FXShape();
	FXShape tgtPort;
	FXShape srcPort;
	DropShadow glow = new DropShadow();
	Point2D.Double inLocation = new Point2D.Double();
	Point2D.Double outLocation = new Point2D.Double();
	Point2D.Double returnPoint = new Point2D.Double();
	Point2D start, end;
	int srcId;
	int tgtId;
	float strokeWidth = 5;

	double x1;
	double y1;
	double x2;
	double y2;

	Atom lastValue;

	Node currentObject;
	Node srcParent;
	Node tgtParent;

	SGGroup connectionGroup = new SGGroup();

	public Connection(FXShape srcConnection, Node p, int id) {
		creating = true;
		srcParent = p;
		srcId = id;
		srcPort = srcConnection;
		start = getPortCenter(srcPort);

		connectionLine.setShape(new Path2D.Double(new Line2D.Double(
				outLocation, outLocation)));
		connectionLine.setDrawPaint(Color.white);
		connectionLine.setMode(SGShape.Mode.STROKE_FILL);
		connectionLine.setDrawStroke(new BasicStroke(1));
		connectionLine.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);
		connectionLine.setOpacity(1);

		lineListener.setShape(createLinePath(outLocation, outLocation,
				strokeWidth));
		lineListener.setOpacity(0f);
		lineListener.addMouseListener(new SGMouseAdapter() {

			public void mouseEntered(MouseEvent e, SGNode n) {
				over = true;
				OverFlowMain.connectionOver = true;// global over
				OverFlowMain.currentConnection = returnThis();
			}

			public void mousePressed(MouseEvent e, SGNode n) {
				if (OverFlowMain.editMode && !OverFlowMain.connecting) {
					if (selected) {
						setSelected(false);
					} else {
						setSelected(true);
					}
				}
			}

			public void mouseExited(MouseEvent e, SGNode n) {
				over = false;
				OverFlowMain.connectionOver = false;// global over
			}

			public void mouseReleased(MouseEvent e, SGNode n) {
			}

			public void mouseDragged(MouseEvent e, SGNode n) {
			}
		});

		connectionGroup.add(connectionLine);
		connectionGroup.add(lineListener);

		OverFlowMain.addToRootGroup(connectionGroup);
	}

	public void release(FXShape endConnection, Node p, int id) {
		tgtId = id;
		tgtParent = p;
		p.addNodeInputConnection(this);
		tgtPort = endConnection;
		OverFlowMain.currentInputObject.inputConnections.add(this);
		creating = false;
		update();
	}

	public void drag(int mx, int my) {
		if (!Node.moving) {
			end = new Point2D.Double(mx, my);
			connectionLine.setShape(new Line2D.Double(start, end));
			createLinePath(start, end, strokeWidth);
			lineListener.setShape(createLinePath(start, end, strokeWidth));
		}
	}

	public void update() {
		if (OverFlowMain.editMode) {
			try {
				start = getPortCenter(srcPort);
				end = getPortCenter(tgtPort);
				connectionLine.setShape(new Line2D.Double(start, end));
				createLinePath(start, end, strokeWidth);
				lineListener.setShape(createLinePath(start, end, strokeWidth));
			} catch (NullPointerException e) {

			}
		}

	}

	public void kill() {
		connectionGroup.remove(lineListener);
		connectionLine.setVisible(false);
		if (!isActive) {
			tgtParent.removeInputConnection(this);
			tgtParent.removeOutputConnection(this);
		}
		srcParent.removeInputConnection(this);
		srcParent.removeOutputConnection(this);
		OverFlowMain.removeConnection(this);
	}

	// //get the location of the current FXShape
	Point2D.Double shapeLocation(FXShape s) {
		Rectangle2D nodeShape = s.getBounds();
		returnPoint.setLocation(nodeShape.getX()
				+ (nodeShape.getWidth() - nodeShape.getX()) / 2, nodeShape
				.getY());
		return returnPoint;
	}

	Point2D getPortCenter(FXShape s) {
		Rectangle2D rect = s.getBounds();
		Point2D.Double p = new Point2D.Double(rect.getCenterX(), rect
				.getCenterY());
		return s.localToGlobal(p, p);
	}

	boolean isNew(FXShape startC, FXShape endC) {
		if (srcPort == startC && tgtPort == endC) {
			isNew = false;
		} else {
			isNew = true;
		}
		return isNew;
	}

	public void updateData() {
		if (!creating) {
			tgtParent.updateInputValue(tgtId, srcParent.getOutputValue(srcId));
			lastValue = srcParent.getOutputValue(srcId);
		}
	}

	Connection returnThis() {
		return this;
	}

	public void setSelected(boolean s) {
		selected = s;
		if (selected) {
			selected = true;
			OverFlowMain.currentSelectedConnections.add(this);
			connectionLine.setDrawStroke(new BasicStroke(2));
		} else {
			selected = false;
			OverFlowMain.currentSelectedConnections.remove(this);
			connectionLine.setDrawStroke(new BasicStroke(1));
		}
	}
	
	public boolean getSelected() {
		return selected;
	}

	public SGGroup returnGroup() {
		return connectionGroup;
	}

	public static void setVisible(boolean b) {
		// connectionGroup.setVisible(b);
	}

	Path2D createLinePath(Point2D p1, Point2D p2, float stroke) {
		double x1 = p1.getX();
		double y1 = p1.getY();
		double x2 = p2.getX();
		double y2 = p2.getY();
		double theta = Math.atan((x1 - x2) / (float) (y1 - y2));
		// System.out.println(Math.toDegrees(theta) + "  " + Math.cos(theta -
		// Math.toRadians(180.0)));
		double xa = x1 + strokeWidth * Math.cos(theta);
		double ya = y1 + strokeWidth * Math.sin(theta);
		double xb = x1 + strokeWidth * Math.cos(theta - Math.PI);
		double yb = y1 + strokeWidth * Math.sin(theta - Math.PI);
		double xc = x2 + strokeWidth * Math.cos(theta - Math.PI);
		double yc = y2 + strokeWidth * Math.sin(theta - Math.PI);
		double xd = x2 + strokeWidth * Math.cos(theta);
		double yd = y2 + strokeWidth * Math.sin(theta);

		Path2D path = new Path2D.Double();
		path.moveTo(xa, ya);
		path.lineTo(xb, yb);
		path.lineTo(xc, yc);
		path.lineTo(xd, yd);
		path.closePath();
		return path;
	}

}
