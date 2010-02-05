package overFlow.Objects;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import overFlow.Atom.AtomFloat;
import overFlow.Tools.Tools;
import overFlow.main.Node;

public class Line extends Node{
	float duration;
	float startValue;
	float endValue;
	float delayTime;
	int count = 0;
	int countMax;
	int trigger = 0;
	timerListener listener = new timerListener(this);
	Timer timer = new Timer((int) delayTime, listener);
	
	public Line(String title, float tx, float ty, float dur, float start, float end) {
		super(title, tx, ty, 4, 1);
		duration = dur;
		startValue = start;
		endValue = end;
		timer.addActionListener(new TimerListener(this));
		delayTime = duration/(endValue - startValue);
		timer.setDelay((int) delayTime);
		countMax = (int) endValue;
		count = (int) startValue;
	}
	
	public void update() {
		if (inputValues[0] != null) {
				timer.start();
			}
			if(inputValues[1] != null){
				duration = inputValues[1].getInt();
			}
			if(inputValues[2] != null){
				startValue = inputValues[2].getFloat();
			}			
			if(inputValues[3] != null){
				endValue = inputValues[3].getFloat();
			}			
						
			if(count >= countMax + startValue) {
				timer.stop();
				count = 0;
			}
			value = new AtomFloat(Tools.map(count, 0, duration, startValue, endValue));
			outputValues[0] = value;
			updateConnections();
	}
	
	
	class timerListener implements ActionListener {
		Line n;

		timerListener(Line node) {
			n = node;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			n.count++;
			n.update();
		}

	}
}



