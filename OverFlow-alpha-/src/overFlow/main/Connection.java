package overFlow.main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

import com.sun.scenario.effect.DropShadow;
import com.sun.scenario.scenegraph.SGGroup;
import com.sun.scenario.scenegraph.SGNode;
import com.sun.scenario.scenegraph.SGShape;
import com.sun.scenario.scenegraph.event.SGMouseAdapter;
import com.sun.scenario.scenegraph.fx.FXShape;

public class Connection {
	  int connectionId;
	  boolean isActive = true;
	  boolean ifIsNew;
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
	  float strokeWidth = 1;

	  Node currentObject;
	  Node srcParent;
	  Node tgtParent;

	  static SGGroup connectionGroup = new SGGroup();

	  public Connection(FXShape srcConnection, Node p, int id){
		    System.out.println(OverFlowMain.connections.size());
	    srcParent = p;
	    srcId = id;
	    srcPort = srcConnection;
	    start = getPortCenter(srcPort);

	    connectionLine.setShape(new Path2D.Double(new Line2D.Double(outLocation, outLocation)));
	    connectionLine.setDrawPaint(Color.white);
	    connectionLine.setMode(SGShape.Mode.STROKE_FILL);
	    connectionLine.setDrawStroke(new BasicStroke(1));
	    connectionLine.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON); 
	    connectionLine.setOpacity(1);

	    lineListener.setShape(createLinePath(outLocation, outLocation, strokeWidth));
	    lineListener.setOpacity(0);
	    lineListener.addMouseListener(new SGMouseAdapter() {
	      Point pos;
	      public void mouseEntered(MouseEvent e, SGNode n) {
	        over = true;
	        OverFlowMain.connectionOver = true;//global over
	        OverFlowMain.currentConnection = returnThis();
	      }
	      public void mousePressed(MouseEvent e, SGNode n) {
	        if(OverFlowMain.editMode && !OverFlowMain.connecting) {
	          if(selected) {
	            setSelected(false);
	          }
	          else {
	            setSelected(true);
	          }
	        }
	      }
	      public void mouseExited(MouseEvent e, SGNode n) {
	        over = false;
	        OverFlowMain.connectionOver = false;//global over
	      }
	      public void mouseReleased(MouseEvent e, SGNode n) {
	      }
	      public void mouseDragged(MouseEvent e, SGNode n) {
	      }
	    }
	    );

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
	    update();
	  }

	  public void drag(int mx, int my) {
	    end = new Point2D.Double(mx, my);			
	    connectionLine.setShape(new Line2D.Double(start, end));
	    lineListener.setShape(createLinePath(start, end, strokeWidth));
	  }

	  public void update() {
	    if(OverFlowMain.editMode && isActive){
	      start = getPortCenter(srcPort);
	      end = getPortCenter(tgtPort);
	      connectionLine.setShape(new Line2D.Double(start, end));
	      lineListener.setShape(createLinePath(start, end, strokeWidth));
	    }
	  }

	  public void kill() {
	    connectionLine.setVisible(false);
	    if(!isActive){
	    tgtParent.removeInputConnection(this);
		tgtParent.removeOutputConnection(this);
	    }
		srcParent.removeInputConnection(this);
		srcParent.removeOutputConnection(this);
		OverFlowMain.removeConnection(this);
	  }

	  ////get the location of the current FXShape
	  Point2D.Double shapeLocation(FXShape s) {
	    Rectangle2D nodeShape = s.getBounds();
	    returnPoint.setLocation(nodeShape.getX() + (nodeShape.getWidth() - nodeShape.getX())/2, nodeShape.getY());
	    return returnPoint;
	  }

	  Point2D getPortCenter(FXShape s) {
	    Rectangle2D rect = s.getBounds();
	    Point2D.Double p = new Point2D.Double(rect.getCenterX(), rect.getCenterY());
		return s.localToGlobal(p, p);
	  }

	  boolean isNew(FXShape startC, FXShape endC) {
	    if(srcPort == startC && tgtPort == endC) {
	      ifIsNew = false;
	    }
	    else {
	      ifIsNew = true;
	    }
	    return ifIsNew;
	  }

	  public void updateData() {
	    tgtParent.updateInputValue(tgtId, srcParent.getOutputValue(srcId));
	    tgtParent.update();
	    tgtParent.redrawNode();
	  }

	  Connection returnThis() {
	    return this;
	  }

	  public void setSelected(boolean s){
	    selected = s;
	    if(selected){
	      selected = true;
	      OverFlowMain.currentSelectedConnections.add(this);
	      connectionLine.setDrawStroke(new BasicStroke(2));
	    }
	    else {
	      selected = false;
	      OverFlowMain.currentSelectedConnections.remove(this);
	      connectionLine.setDrawStroke(new BasicStroke(1));
	    }
	  }

	  public SGGroup returnGroup(){
	    return connectionGroup;
	  }
	
	  public static void setVisible(boolean b) {
		  connectionGroup.setVisible(b);
	  }




	Path2D createLinePath(Point2D p1, Point2D p2, float stroke){
	  double x1 = p1.getX();
	  double y1 = p1.getY();
	  double x2 = p2.getX();
	  double y2 = p2.getY();

	  Path2D path = new Path2D.Double();
	  path.moveTo(x1 + (stroke/2 + 1), y1);
	  path.lineTo(x2 + (stroke/2 + 1), y2);
	  path.lineTo(x2 - (stroke/2 + 1), y2);
	  path.lineTo(x1 - (stroke/2 + 1), y1);    
	  path.closePath();

	  return path;
	}

}



























