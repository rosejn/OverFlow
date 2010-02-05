package overFlow.Objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Arc2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.Timer;

import com.sun.scenario.scenegraph.SGGroup;
import com.sun.scenario.scenegraph.SGShape;
import com.sun.scenario.scenegraph.fx.FXShape;

import overFlow.Tools.Tools;
import overFlow.main.Node;

public class Loop extends Node {
	int numSteps;
	LoopTimerListener listener = new LoopTimerListener(this);
	private int delayTime = 250;
	Timer timer = new Timer((int) delayTime, listener);
	SGGroup loopGroup = new SGGroup();
	FXShape loopArc = new FXShape();
	FXShape loopBack = new FXShape();
	Color loopFillPaint = Color.orange;
	float strokeWidth = 10;
	float stepSize = 0;
	private int counter = 0;




	public Loop(float ox, float oy, int count) {
		// TODO Auto-generated constructor stub
		super("", ox, oy, 100, 100, 2, 2);
		numSteps = count;
		stepSize = (360 / (float)(numSteps - 1));
		System.out.println(stepSize);
		createBack();
		createArc();
		addToGroup(loopGroup);
		timer.start();
	}

	void createArc() {
		loopArc.setShape(new Arc2D.Double(x + strokeWidth, y + strokeWidth, w - (strokeWidth * 2), h - (strokeWidth * 2), 0, 0, 0));	
		loopArc.setDrawPaint(loopFillPaint);
		loopArc.setMode(SGShape.Mode.STROKE);
		loopArc.setDrawStroke(new BasicStroke(strokeWidth));
		loopArc.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);
		
		loopGroup.add(loopArc);
	}

	void createBack() {
		loopBack.setShape(new RoundRectangle2D.Double(x + 2, y + 2, w - 4, h - 4, 6, 6));	
		loopBack.setFillPaint(new Color(100,100,100));
		loopBack.setMode(SGShape.Mode.FILL);
		loopBack.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);
		
		loopGroup.add(loopBack);
	}
	
	public void update() {
		if (inputValues[0] != null) {
			if (inputValues[0].getInt() == 0) {
				timer.stop();
			} else if (inputValues[0].getInt() == 1) {
				timer.start();
			}
			if(inputValues[1] != null){
				delayTime = inputValues[1].getInt();
				timer.setDelay((int) Tools.constrain(delayTime, 0));
			}
		}
	}
	
	void updateLoop() {
		if(counter >= numSteps - 1) {
			counter = 0;
		}
		counter++;
		double startAngle = 0.0;
		double endAngle = -355;

		loopArc.setShape(new Arc2D.Double(x + strokeWidth, y + strokeWidth, w - (strokeWidth * 2), h - (strokeWidth * 2), startAngle, endAngle, 0));
	}

}


class LoopTimerListener implements ActionListener {
	Loop n;

	LoopTimerListener(Loop node) {
		n = node;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		n.updateLoop();
		
	}

}

