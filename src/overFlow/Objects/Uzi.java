package overFlow.Objects;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import overFlow.Atom.AtomString;
import overFlow.main.Node;


/*
 * The UZI object sends a series of bangs in the amount specified in the argument
 */

public class Uzi extends Node{
	float delayTime = 1;
	int trigger = 0;
	UziTimerListener listener = new UziTimerListener(this);
	Timer timer = new Timer((int) delayTime, listener);
	int count = 0;
	int numBangs = 0;
	
	public Uzi(String title, float tx, float ty, int nBangs){
		super(title, tx, ty, 1, 1);
		numBangs = nBangs;
		
		
	}

	public void update(){
		System.out.println(count);
		if (inputValues[0] != null) {
				timer.start();
			if(numBangs != count){
				timer.stop();
			}
		}
		outputValues[0] = new AtomString("bang");	//-9999f == bang for now (until there is string support)
		updateConnections();
		
		if(count == numBangs){
			timer.stop();
		}
	}
}

class UziTimerListener implements ActionListener {
	Uzi u;

	UziTimerListener(Uzi node) {
		u = node;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		u.count++;
		u.update();
	}

}