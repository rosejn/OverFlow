package overFlow.Objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTextArea;
import com.sun.scenario.scenegraph.SGComponent;
import com.sun.scenario.scenegraph.SGGroup;

import com.sun.scenario.scenegraph.SGTransform;
import overFlow.main.Node;


public class Comment extends Node {
	SGGroup commentGroup = new SGGroup();
	Dimension textDimensions;
	int textW = 150;
	int textH = 100;
	SGGroup textAreaGroup = new SGGroup();
	SGGroup panelGroup = new SGGroup();
	SGTransform.Translate textTranslate = SGTransform.createTranslation(0, 0, textAreaGroup);
	static JTextArea ta = new JTextArea();
	float oldTime = 0;
	
	public Comment(float tx, float ty) {
		super("", tx, ty, 1, 0);
		hideNode();
		createTextArea();
		this.setScaleWidthOnly(false);
		group.add(textTranslate);
	}

	private void hideNode() {
		// TODO Auto-generated method stub
		baseRect.setFillPaint(new Color(0, 0, 0, 20));
		baseRect.setDrawPaint(new Color(0, 0, 0, 50));
		this.setSize(textW, textH);

	}

	public static void killFocus(){
		ta.transferFocus();
	}
	
	
	private void createTextArea() {
		ta.setText("enter comments here");
		ta.setBackground(new Color(0, 0, 0, 0));
		ta.addKeyListener(new CommentKeyListener(this));
		ta.setBounds(0,0,150,100);
		SGComponent comp = new SGComponent();
		comp.setComponent(ta);
		comp.setID("ScrollPane Wrapper");
		panelGroup.add(comp);
		textAreaGroup.add(comp);
		textTranslate.setTranslateX(x + 3);
		textTranslate.setTranslateY(y + 3);

	}
	public void nodePressed(){
		if (System.nanoTime() - oldTime < 2.0000000E8) {
			ta.requestFocus();
		}
		oldTime = System.nanoTime();
	}


}

class CommentKeyListener implements KeyListener {
	Comment parent;
	CommentKeyListener(Comment p) {
		parent = p;

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==27){
			parent.ta.transferFocus();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println("typeing");
		//parent.setSize((int)parent.ta.getBounds().getWidth() + (parent.r * 2), (int)parent.ta.getBounds().getHeight() + (parent.r * 2));		//update node width from text area dimensions
	}
}

