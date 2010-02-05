package overFlow.Objects;

import java.awt.Color;

import overFlow.Atom.AtomString;
import overFlow.main.Node;
import overFlow.main.OverFlowMain;

public class Message extends Node {
		String messageString;
	  public Message(String mString, float tx, float ty) {
	    super(mString, tx, ty, 2, 1);
	    messageString = mString;
	    baseRect.setFillPaint(new Color(190,190,190));

	  }
	  public void setSelected(boolean s) {
	    selected = s;
	    if(selected) {
	      baseRect.setFillPaint(new Color(220,220,220)); 
	      if(!OverFlowMain.currentSelectedObjects.contains(this)){
	    	  OverFlowMain.currentSelectedObjects.add(this); 
	      }
	    }
	    else {
	      baseRect.setFillPaint(new Color(190,190,190));
	      OverFlowMain.currentSelectedObjects.remove(this);
	    }  
	  }
	  
	  
	  public void update() {
		  outputValues[0] = new AtomString(messageString);
	  }
	}
