package overFlow.main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

import com.sun.scenario.effect.DropShadow;
import com.sun.scenario.scenegraph.SGNode;
import com.sun.scenario.scenegraph.SGGroup;
import com.sun.scenario.scenegraph.SGShape;
import com.sun.scenario.scenegraph.event.SGMouseAdapter;
import com.sun.scenario.scenegraph.fx.FXShape;


public class OutputPort {
  FXShape port = new FXShape();
  FXShape portCap = new FXShape();
  FXShape portHighlight = new FXShape();
  SGGroup portGroup = new SGGroup();
  float x, y, w;
  int ID;
  Node parent;

  public OutputPort(float tx, float ty, float tw, Node tParent, int connectionID) {     
    x = tx;
    y = ty;
    w = tw;
    ID = connectionID;
    parent = tParent;
    createPort();
    createPortHighlight();
    portGroup.add(port);
    portGroup.add(portCap);
    portGroup.add(portHighlight);
    parent.group.add(portGroup);
  }    

  void createPortHighlight(){

    portHighlight.setShape(new Ellipse2D.Float(0 , 0, w * 2, w * 2));
    portHighlight.setTranslateX(x - w/2);
    portHighlight.setTranslateY(y - w);
    portHighlight.setDrawPaint(Color.ORANGE);
    portHighlight.setFillPaint(new Color(255,255,255,0));
    portHighlight.setMode(SGShape.Mode.STROKE_FILL);
    portHighlight.setDrawStroke(new BasicStroke(2));
    portHighlight.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON); 
    portHighlight.setOpacity(0.0f);
    portHighlight.addMouseListener(new SGMouseAdapter() {
      public void mouseEntered(MouseEvent e, SGNode n) {
        if(OverFlowMain.editMode) {
          FXShape node = (FXShape)n;
          node.setOpacity(0.7f);
          Rectangle2D shapeBounds = node.getBounds();
          OverFlowMain.currentOutputShape = node;
          OverFlowMain.connectionOver = true;
        }
      }
      public void mousePressed(MouseEvent e, SGNode n) {
        if(OverFlowMain.editMode){
        	OverFlowMain.connecting = true;
          FXShape node = (FXShape) n;
          parent.activeConnection = new Connection(node, parent, ID);
          OverFlowMain.addConnection(parent.activeConnection);
          parent.addOutputConnection(parent.activeConnection);
        }
      }
      public void mouseExited(MouseEvent e, SGNode n) {
        if(OverFlowMain.editMode){
          FXShape node = (FXShape)n;
          node.setOpacity(0.0f);
          OverFlowMain.connectionOver = false;
        }
      }
      public void mouseReleased(MouseEvent e, SGNode n) {
    	  OverFlowMain.connecting = false;
        FXShape node = (FXShape)n;
        if(OverFlowMain.editMode){
          node.setOpacity(0);
          if(OverFlowMain.inputConnectionOver) {
            parent.activeConnection.release(OverFlowMain.currentInputShape, OverFlowMain.currentInputObject, OverFlowMain.currentInputIO.ID);
          }
          else {
        	  if(parent.getOutputConnectionsSize() > 0)
            parent.removeOutputConnection(parent.outputConnections.lastElement());
            parent.activeConnection.kill();
          if(OverFlowMain.connections.size() > 0){
           System.out.println(OverFlowMain.connections.size());
        	  OverFlowMain.removeConnection(OverFlowMain.connections.size() - 1);
          }
          }
        }
      }
      public void mouseDragged(MouseEvent e, SGNode n) {
        FXShape node = (FXShape)n;
        if(OverFlowMain.editMode){
          node.setOpacity(0.50f);
          Rectangle2D shapeBounds = node.getBounds();
          parent.activeConnection.drag((int)e.getX(), (int)e.getY());
        }
      }
    }
    );
  }  

  void createPort() {
    port.setShape(new Line2D.Float(0 , 0, w, 0f));
    port.setTranslateX(x);
    port.setTranslateY(y);
    port.setDrawPaint(Color.white);
    port.setMode(SGShape.Mode.STROKE_FILL);
    port.setDrawStroke(new BasicStroke(2));
    port.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);  

    portCap.setShape(new Line2D.Float(0 , 0, w - 2, 0f));
    portCap.setTranslateX(x + 1.5);
    portCap.setTranslateY(y + 2);
    portCap.setDrawPaint(Color.LIGHT_GRAY);
    portCap.setMode(SGShape.Mode.STROKE_FILL);
    portCap.setDrawStroke(new BasicStroke(2));
    portCap.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON); 
  
  }

  void updateConnection() {
    for(Iterator i = parent.outputConnections.iterator(); i.hasNext();) {
      Connection con = (Connection)i.next();
      con.updateData();
    }

  }
  
OutputPort returnThis() {
return this;
}


public void hideHighlight() {
	portHighlight.setOpacity(0f);
}
}




