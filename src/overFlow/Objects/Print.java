package overFlow.Objects;

import overFlow.main.Node;

public class Print extends Node {
	  boolean prepend;
	  String prependString;

	  public Print(String pString, float tx, float ty) {
	    super("print " + pString, tx, ty, 1, 0);
	    prepend = true;
	    prependString = pString;
	  }

	  public Print(float tx, float ty) {
		    super("print", tx, ty, 1, 0);
		    prepend = false;
		  }
	  
	  public void update() {
	    if(inputValues[0] != null) {
	    	if(inputValues[0] == -9999f){
	    		messageToPrint("bang");
	    	}
	    	else {
	    		messageToPrint(inputValues[0]); 
	    	}
	    }
	  }

	  void messageToPrint(float f){
		  if(prepend){
			  System.out.println(prependString + " " + f);
		  }
		  else {
			  System.out.println(f);
		  }
	  }

	  void messageToPrint(String s) {
		  if(prepend){
			  System.out.println(prependString + " " + s);
		  }
		  else {
			  System.out.println(s);
		  }
	  }


	  void valueChanged () {

	  }
	}
