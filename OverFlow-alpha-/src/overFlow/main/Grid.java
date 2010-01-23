package overFlow.main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;

import com.sun.scenario.scenegraph.SGGroup;
import com.sun.scenario.scenegraph.SGNode;
import com.sun.scenario.scenegraph.SGShape;

public class Grid {
	  float spacingSize;
	  public SGGroup lineGroup = new SGGroup();
	  public Grid(int spacingWidth) {
	    spacingSize = spacingWidth;

	    for(int i = 0; i < 100; i++) {
	      SGShape xLine = new SGShape();
	      SGShape yLine = new SGShape();
	      xLine.setShape(new Line2D.Float(i * spacingWidth, 0, i * spacingWidth, 1000));
	      xLine.setDrawPaint(new Color(255,255,255,30));
	      xLine.setMode(SGShape.Mode.STROKE);
	      xLine.setDrawStroke(new BasicStroke(1));
	      xLine.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON); 

	      yLine.setShape(new Line2D.Float(0, i * spacingWidth, 1000, i * spacingWidth));
	      yLine.setDrawPaint(new Color(255,255,255,30));
	      yLine.setMode(SGShape.Mode.STROKE);
	      yLine.setDrawStroke(new BasicStroke(1));
	      yLine.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);    

	      lineGroup.add(xLine);
	      lineGroup.add(yLine);
	      lineGroup.setVisible(false); //only on when snap mode is engaged  
	  }
	  }
	  
	  public void setVisible(boolean s) {
	    lineGroup.setVisible(s);
	  }
	  
	  public SGNode returnLineGroup() {
		  return lineGroup;
	  }
	}









