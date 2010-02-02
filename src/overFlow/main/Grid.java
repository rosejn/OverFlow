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

	    for(int i = 0; i < 2000/spacingWidth; i++) {
	      SGShape xLine = new SGShape();
	      xLine.setShape(new Line2D.Float(i * spacingWidth, 0, i * spacingWidth, 2000));
	      xLine.setDrawPaint(new Color(255,255,255,30));
	      xLine.setMode(SGShape.Mode.STROKE);
	      xLine.setDrawStroke(new BasicStroke(1));
	      xLine.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON); 
	      lineGroup.add(xLine);
	    }
	    for(int j = 0; j < 3000/spacingWidth; j++) {
		  SGShape yLine = new SGShape();
	      yLine.setShape(new Line2D.Float(0, j * spacingWidth, 3000, j * spacingWidth));
	      yLine.setDrawPaint(new Color(255,255,255,30));
	      yLine.setMode(SGShape.Mode.STROKE);
	      yLine.setDrawStroke(new BasicStroke(1));
	      yLine.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);    
	      lineGroup.add(yLine);

	  }
	      lineGroup.setVisible(false); //only on when snap mode is engaged  
	  }
	  
	  public void setVisible(boolean s) {
	    lineGroup.setVisible(s);
	  }
	  
	  public SGNode returnLineGroup() {
		  return lineGroup;
	  }
	}









