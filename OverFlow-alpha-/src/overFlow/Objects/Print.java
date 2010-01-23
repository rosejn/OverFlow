package overFlow.Objects;

import overFlow.main.Node;

public class Print extends Node {

	  public Print(String prependString, float tx, float ty) {
	    super("print", tx, ty, 1, 0);

	  }

	  public void update() {
	    if(inputValues[0] != null) {
	messageToPrint(inputValues[0]); 
	    }
	  }

	  void messageToPrint(float f){
		System.out.println(f);
	  }

	  void messageToPrint(String s) {
	    System.out.println(s);
	  }


	  void valueChanged () {

	  }
	}
