package overFlow.Objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.Iterator;
import java.util.Vector;

import overFlow.Tools.Tools;

import com.sun.scenario.effect.DropShadow;
import com.sun.scenario.effect.InnerShadow;
import com.sun.scenario.scenegraph.SGGroup;
import com.sun.scenario.scenegraph.SGShape;
import com.sun.scenario.scenegraph.SGNode;
import com.sun.scenario.scenegraph.SGText;
import com.sun.scenario.scenegraph.SGTransform;
import com.sun.scenario.scenegraph.event.SGMouseAdapter;
import com.sun.scenario.scenegraph.fx.FXShape;

public class PrefBtn {
	  private static final String SGShape = null;
	float x, y, r;
	  boolean overPrefBtn = false;
	  public FXShape button = new FXShape();
	  public SGGroup btnGroup = new SGGroup();
	  PrefBtn(float tx, float ty, float tr){

	    button.setDrawPaint(Color.BLACK);
	    button.setShape(new Ellipse2D.Float(tx, ty, tr, tr));
	    button.setFillPaint(Color.ORANGE);
	    button.setDrawStroke(new BasicStroke(2));
	    button.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON); 
	    button.addMouseListener(new SGMouseAdapter() {
	      Point2D pos;
	      public void mousePressed(MouseEvent e, SGNode n){
	        button.setFillPaint(Color.YELLOW);
	        System.out.println("laucnh this node's preff window");
	      }
	      public void mouseReleased(MouseEvent e, SGNode n) {
	        button.setFillPaint(Color.ORANGE); 
	      }
	    }
	    );

	    btnGroup.add(button);

	  }
	  SGGroup returnShape() {
	    return btnGroup;
	  }
	  public void setVisible(boolean b) {
	    button.setVisible(b); 
	  }
	}













