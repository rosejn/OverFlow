package overFlow.Objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;

import overFlow.Atom.AtomFloat;
import overFlow.Tools.Tools;
import overFlow.main.Node;
import overFlow.main.OverFlowMain;



import com.sun.scenario.effect.InnerShadow;
import com.sun.scenario.scenegraph.SGNode;
import com.sun.scenario.scenegraph.SGShape;
import com.sun.scenario.scenegraph.SGTransform;
import com.sun.scenario.scenegraph.event.SGMouseAdapter;
import com.sun.scenario.scenegraph.fx.FXShape;

public class Slider extends Node {
	  Float sliderVal = new Float(0);
	  float xOffset;
	  float yOffset;
	  float widthOffset;
	  float heightOffset;
	  Color strokeColor = Color.GRAY;
	  boolean strokeOn = true;
	  FXShape slider = new FXShape();
	  FXShape triangleIndicator = new FXShape();
	  PrefBtn preffs;

	  public Slider(float tx, float ty, float tw, float th){
	    super("", tx, ty, tw, th, 1, 1);
	    super.setScaleWidthOnly(false);
	    this.setOutputToolTip(0,"value output");
	    
	    Path2D tp = new Path2D.Float(trianglePath(0,y+8));
	    triangleIndicator.setShape(new Path2D.Float(tp));      
	    triangleIndicator.setTranslateX( tx + 5);
	    triangleIndicator.setTranslateY(ty + 5);
	    triangleIndicator.setFillPaint(Color.GRAY);
	    triangleIndicator.setDrawStroke(new BasicStroke(1.0f));
	    triangleIndicator.setMode(SGShape.Mode.STROKE_FILL);
	    triangleIndicator.setDrawStroke(new BasicStroke(1.0f));
	    triangleIndicator.setDrawPaint(Color.GREEN);
	    triangleIndicator.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);

	    Path2D p = new Path2D.Float(sliderPath(0,y+8));
	    slider.setShape(new Path2D.Float(p));      
	    slider.setTranslateX( tx + 5);
	    slider.setTranslateY(ty + 5);
	    slider.setFillPaint(Color.GRAY);
	    slider.setDrawStroke(new BasicStroke(1.0f));
	    slider.setMode(SGShape.Mode.STROKE_FILL);
	    slider.setDrawStroke(new BasicStroke(1.0f));
	    slider.setDrawPaint(Color.GREEN);
	    slider.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);
	    //slider.setEffect(iShadow);			//scenario has a shitty bug with using the inner shadow in a collapsing shape (it makes the shape move around and tweak out).
	    group.add(slider);
	    group.add(triangleIndicator);

	    preffs = new PrefBtn(x + w - 8, y + 4, 5);
	    group.add(preffs.btnGroup);

	    groupT = SGTransform.createTranslation(0, 0, group);


	    slider.addMouseListener(new SGMouseAdapter() {
	      Point pos;
	      public void mouseMoved(MouseEvent e, SGNode n) {
	        pos = e.getPoint();
	      }
	      public void mouseDragged(MouseEvent e, SGNode n) {

	        pos = e.getPoint();
	      }

	      public void mousePressed(MouseEvent e, SGNode n){
	        pos = e.getPoint();
	        if(!OverFlowMain.getEditMode()) {
	          slider.setShape(new Path2D.Float(sliderPath(mousePressedX, Tools.constrain(mousePressedY, y + 5, y + h - 16))));
	          triangleIndicator.setShape(new Path2D.Float(trianglePath(mousePressedX, Tools.constrain(mousePressedY, y + 8, y + h - 16))));
	          sliderVal = Tools.map(Tools.constrain(mouseDraggedY, y, y + h), y, y + h, 100, 0);
	          value = new AtomFloat(sliderVal);
	          outputValues[0] = value;
	          updateConnections(); 
	        }
	      }  
	    }
	   );  
	 }
	  
	  public void nodeDragged() {
	    if(!OverFlowMain.getEditMode()) {
	      slider.setShape(new Path2D.Float(sliderPath(mouseDraggedX, Tools.constrain(mouseDraggedY, y + 8, y + h - 16))));
	      triangleIndicator.setShape(new Path2D.Float(trianglePath(mouseDraggedX, Tools.constrain(mouseDraggedY, y + 8, y + h - 16))));
          sliderVal = Tools.map(Tools.constrain(mouseDraggedY, y, y + h), y, y + h, 100, 0);
          value = new AtomFloat(sliderVal);
          outputValues[0] = value;
	      updateConnections(); 
	    }
	  }

	  public void update() {
	    if(inputValues[0] != null) {
	      value = inputValues[0];
	      sliderVal = value.getFloat();
	      outputValues[0] = value;
	      float yVal = Tools.map(sliderVal, 0, 100, y + h - 16, y + 8);
	      slider.setShape(new Path2D.Float(sliderPath(mouseDraggedX, Tools.constrain(yVal, y + 8, y + h - 16))));
	      triangleIndicator.setShape(new Path2D.Float(trianglePath(mouseDraggedX, Tools.constrain(yVal, y + 8, y + h - 16))));
	      updateConnections(); 
	    } 
	  }

	  public void nodeOver() {
	    if(OverFlowMain.getEditMode()){
	      preffs.setVisible(true);    
	    }
	  }

	  public void nodeExited() {
	    if(OverFlowMain.getEditMode()) {
	      preffs.setVisible(false); 
	    }
	  }

	  Path2D sliderPath(float mx, float my) {
	    float x1 = w - 18;
	    float y1 = my - y - 3;
	    float x2 = x1;
	    float y2 = h - 16;
	    float x3 = 3;
	    float y3 = y2;
	    float x4 = x3;
	    float y4 = y1;
	    Path2D path = new Path2D.Double();
	    path.moveTo(x1 , y1);
	    path.lineTo(x2 , y2);
	    path.lineTo(x3 , y3);
	    path.lineTo(x4 , y4);    
	    path.closePath();
	    return path;
	  }

	  Path2D trianglePath(float mx, float my) {
	    float x1 = w - 10;
	    float y1 = my - y - 3;
	    float x2 = x1;
	    float y2 = y1 + 5;
	    float x3 = w - 10 - 5;
	    float y3 = y1;

	    Path2D path = new Path2D.Double();
	    path.moveTo(x1 , y1);
	    path.lineTo(x2 , y2);
	    path.lineTo(x3 , y3);    
	    path.closePath();
	    return path;
	  }
	}

