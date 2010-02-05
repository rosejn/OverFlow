package overFlow.Objects;

import java.awt.event.KeyEvent;

import overFlow.Atom.AtomInt;
import overFlow.main.Node;


public class Key extends Node{
	public static KeyEvent keyIn;

	public Key(float tx, float ty){
		super("key", tx, ty, 0, 1);
	}

	public static void updateKey(KeyEvent e) {
		keyIn = e;
		// TODO Auto-generated method stub
	}
	
	public void update() {
		outputValues[0] = new AtomInt(keyIn.getKeyCode());
		updateConnections();
	}
	
}




