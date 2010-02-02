package overFlow.Objects;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import overFlow.Tools.Tools;
import overFlow.main.Node;

public class TimerNode extends Node {

	float delayTime = 100;
	int trigger = 0;
	TimerListener listener = new TimerListener(this);
	Timer timer = new Timer((int) delayTime, listener);
	int count = 0;

	public TimerNode(String title, float tx, float ty, float delay) {
		super(title, tx, ty, 2, 1);
		value = 0;
		delayTime = delay;
		timer.addActionListener(new TimerListener(this));
	}

	public void update() {
		if (inputValues[0] != null) {
			if (inputValues[0] == 0) {
				timer.stop();
			} else if (inputValues[0] == 1) {
				timer.start();
			}
			if(inputValues[1] != null){
				delayTime = inputValues[1];
				timer.setDelay((int) Tools.constrain(delayTime, 0));
			}
			outputValues[0] = value;
			updateConnections();
		}
	}
}

class TimerListener implements ActionListener {
	Node n;

	TimerListener(Node node) {
		n = node;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		n.value++;
		n.update();
	}

}
