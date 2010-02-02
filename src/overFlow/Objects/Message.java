package overFlow.Objects;

import java.awt.Color;

import overFlow.main.Node;
import overFlow.main.OverFlowMain;

public class Message extends Node {

	  public Message(String messageString, float tx, float ty) {
	    super(messageString, tx, ty, 2, 1);
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
	}
