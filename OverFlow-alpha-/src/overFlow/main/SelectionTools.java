package overFlow.main;

import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

import com.sun.scenario.scenegraph.SGGroup;
import com.sun.scenario.scenegraph.SGShape;
import com.sun.scenario.scenegraph.fx.FXShape;

public class SelectionTools {
	  public static SGGroup selectionGroup = new SGGroup();
	  public static FXShape selectionBox = new FXShape();
	  public static boolean selecting = false;
	  
	  
	  public SelectionTools() {
		 createSlectionBox();
		 OverFlowMain.addToRootGroup(selectionGroup);
	}
	  
	  
	  public static void createSlectionBox() {
		    selectionBox.setShape(new Rectangle2D.Double(0, 0, 0, 0));
		    selectionBox.setFillPaint(new Color(0, 255, 0, 10));      
		    selectionBox.setMode(SGShape.Mode.STROKE_FILL); 
		    selectionBox.setDrawPaint(new Color(0,0,255));
		    selectionBox.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);
		    selectionBox.setVisible(false);
		    selectionGroup.add(selectionBox);
		  }

		  static Rectangle2D.Double getSelectionRect(Point2D pPos, Point2D pos) {    //this handles the selection box so that when you draw in any direction in draws the shape correctly
		    double pX = pPos.getX();
		    double pY = pPos.getY();
		    double x = pos.getX();
		    double y = pos.getY();
		    double xLeft = 0;
		    double xRight = 0;
		    double yTop = 0;
		    double yBottom = 0;

		    if(pX < x) {
		      xLeft = pX;
		      xRight = x;
		    }
		    else if (pX > x){
		      xLeft = x;
		      xRight = pX;
		    }
		    if(pY < y) {
		      yTop = pY;
		      yBottom = y; 
		    }  
		    else {
		      yTop = y;
		      yBottom = pY;   
		    } 
		    Rectangle2D.Double rect = new Rectangle2D.Double(xLeft, yTop, xRight - xLeft, yBottom - yTop);
		    return rect; 
		  }


		public static void draggUpdate(Point2D pPos, Point2D pos) {
			// TODO Auto-generated method stub
	          Rectangle2D.Double rect = getSelectionRect(pPos, pos); 
	          selectionBox.setShape(new Rectangle2D.Double(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight()));	

		}

		public static void setSelecting(boolean b) {
			selecting = b;
			selectionBox.setVisible(b);
			if(!b){
		      selectionBox.setShape(new Rectangle2D.Float(0,0,0,0));
			}
		}
		
		public static boolean getVisible() {
			return selectionBox.isVisible();
		}
		
		public static void setVisible(boolean b) {
			selectionGroup.setVisible(b);
		}
		
		public SGGroup returnGroup() {
		  return selectionGroup;
	  }
	  
	  
}