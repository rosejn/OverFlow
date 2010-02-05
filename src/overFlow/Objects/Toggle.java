package overFlow.Objects;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;

import overFlow.Atom.AtomFloat;
import overFlow.Tools.Tools;
import overFlow.main.Node;
import overFlow.main.OverFlowMain;

import com.sun.scenario.scenegraph.SGGroup;
import com.sun.scenario.scenegraph.fx.FXShape;
import com.sun.scenario.scenegraph.SGShape;


public class Toggle extends Node{
	  int counter = 0;
	  FXShape x1 = new FXShape();
	  FXShape x2 = new FXShape();
	  SGGroup subGroup = new SGGroup();
	  float outputValue = 0;

	  public Toggle(float mx, float my){
	    super("",mx,my, 20,20, 1, 1);
	    super.setScaleWidthOnly(false);
	    x1.setDrawPaint(Color.BLACK);
	    x1.setShape(new Line2D.Float(0, 0, (float)w - 8, (float)h - 8));
	    x1.setTranslateX(x + 4f);
	    x1.setTranslateY(y + 4f);
	    x1.setMode(SGShape.Mode.STROKE_FILL);
	    x1.setDrawStroke(new BasicStroke(2));
	    x1.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON); 
	    x2.setDrawPaint(Color.black);
	    x2.setShape(new Line2D.Float(0, 0, w - 8, (-h) + 8));
	    x2.setTranslateX(x + 4f);
	    x2.setTranslateY(y + h - 4f);
	    x2.setMode(SGShape.Mode.STROKE_FILL);
	    x2.setDrawStroke(new BasicStroke(2));
	    x2.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);
	    shadow.setRadius(5);
	    subGroup.add(x1);
	    subGroup.add(x2);
	    addToGroup(subGroup);

	    subGroup.setVisible(false);
	    //super.addShadow();
	    //super.setShadowRadius(4);

	    outputValues[0] = new AtomFloat(outputValue);
	  }

	  public void nodePressed(){
	    if(!OverFlowMain.editMode) {
	      if(counter % 2 >= 1){
	        subGroup.setVisible(false); 
	      }
	      else if(counter % 2 <= 0){
	        subGroup.setVisible(true);
	      }
	      counter++; 
	      outputValue = counter % 2;
	      outputValues[0] = new AtomFloat(outputValue);
	      super.updateConnections();
	    }

	  }
	  public void update() {
	    try {
	      value = inputValues[0];
	      outputValues[0] = new AtomFloat(Tools.constrain(value.getInt(), 0, 1));
	      super.updateConnections(); 
	      if(inputValues[0].getInt() <= 0) {
	        toggleOff();
	      }
	      else {
	        toggleOn();
	      }
	      counter++; 
	    }
	    catch (NullPointerException e){
	    	
	    }
	  }

	public void toggleOn() {
		baseRect.setDrawPaint(Color.GRAY);
		subGroup.setVisible(true);  //toggle x visibility
	}

	public void toggleOff() {
		baseRect.setDrawPaint(Color.GRAY);
		subGroup.setVisible(false);  //toggle x visibility
	}
	  
	  
	}