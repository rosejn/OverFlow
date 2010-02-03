package overFlow.main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.Scanner;
import java.util.Vector;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import overFlow.Objects.Add;
import overFlow.Objects.Bang;
import overFlow.Objects.Change;
import overFlow.Objects.ConstrainNode;
import overFlow.Objects.Counter;
import overFlow.Objects.Dial;
import overFlow.Objects.Divide;
import overFlow.Objects.FloatDisp;
import overFlow.Objects.Gate;
import overFlow.Objects.Line;
import overFlow.Objects.Loop;
import overFlow.Objects.Map;
import overFlow.Objects.Message;
import overFlow.Objects.Multiply;
import overFlow.Objects.NoteSequencer;
import overFlow.Objects.Peak;
import overFlow.Objects.Print;
import overFlow.Objects.Random;
import overFlow.Objects.Slider;
import overFlow.Objects.StepSequencer;
import overFlow.Objects.Subtract;
import overFlow.Objects.TimerNode;
import overFlow.Objects.Toggle;
import overFlow.Objects.Trough;
import overFlow.Objects.Uzi;
import overFlow.Objects.Math.Cos;
import overFlow.Objects.Math.Drunk;
import overFlow.Objects.Math.SQRT;
import overFlow.Objects.Math.Sin;
import overFlow.Objects.Math.Tan;
import overFlow.Tools.Tools;

import com.sun.scenario.scenegraph.SGGroup;
import com.sun.scenario.scenegraph.SGText;
import com.sun.scenario.scenegraph.SGTransform;
import com.sun.scenario.scenegraph.fx.FXShape;
import com.sun.scenario.scenegraph.SGComponent;
import com.sun.scenario.scenegraph.SGNode;
import com.sun.scenario.scenegraph.SGShape;
import com.sun.scenario.scenegraph.event.SGMouseAdapter;

public class ObjectCreator2 {
	Font font = new Font("Helvetica", Font.BOLD, 14);
	static JTextField textEntry = new JTextField(5);
	int keyCount = 0;
	SGComponent comp = new SGComponent();
	SGText sgText = new SGText();
	FXShape baseRect = new FXShape();
	SGGroup group = new SGGroup();
	SGGroup textGroup = new SGGroup();
	float x, y, w, h, r;
	SGTransform.Translate groupT = SGTransform.createTranslation(0, 0, group);
	SGTransform.Translate textTranslate = SGTransform.createTranslation(0, 0, textGroup);
	public Vector<String> arguments = new Vector<String>();
	// variables for newly created object
	String title;
	int ins;
	int outs;
	float ox, oy, ow, oh;
	public boolean over;
	static boolean creating = false;
	private String argumentArray;
	String text;

	ObjectCreator2(float tx, float ty) {
		x = tx;
		y = ty;
		w = 50;
		h = 20;
		r = 8;
		textEntry.setBackground(new Color(0,0,0,0));
		textEntry.setFont(font);
		textEntry.setBorder(new EmptyBorder(ins, ins, ins, ins));
		textEntry.addKeyListener(new TextKeyListener());
		textGroup.add(comp);
		comp.setComponent(textEntry);
		comp.setID("Object Menu");
		group.add(baseRect);
		group.add(textTranslate);

		textEntry.addActionListener(new TextEntryListener());
		textEntry.addKeyListener(new TextEntryKeyListener());
		create();
		OverFlowMain.rootGroup.add(group);
	}

	void create() {
		baseRect.setShape(new RoundRectangle2D.Float(0, 0, w, h, r, r));
		baseRect.setFillPaint(new Color(255, 255, 255));
		baseRect.setMode(SGShape.Mode.STROKE_FILL);
		baseRect.setDrawStroke(new BasicStroke(2.0f));
		baseRect.setDrawPaint(new Color(150, 150, 150));
		baseRect.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);
		baseRect.addMouseListener(new SGMouseAdapter() {
			public void mouseEntered(MouseEvent e, SGNode n) {
				over = true;
			}

			public void mouseMoved(MouseEvent e, SGNode n) {
			}

			public void mouseExited(MouseEvent e, SGNode n) {
				over = false;
			}

			public void mousePressed(MouseEvent e, SGNode n) {
			}

			public void mouseReleased(MouseEvent e, SGNode n) {
			}

			public void mouseDragged(MouseEvent e, SGNode n) {
			}
		});

		group.setVisible(false);

	}

	public void setVisible(boolean visible) {
		group.setVisible(visible);
		if(visible){
   			 textEntry.setText("");
		     textEntry.setEditable(true);
		     creating = true;
		}
	}

	SGGroup returnGroup() {
		return group;
	}

	void setLocation(float tx, float ty) {
		tx += r - 15;
		ty -= 5;
		ox = tx;
		oy = ty;
		baseRect.setTranslateX(tx);
		baseRect.setTranslateY(ty);
		
		textTranslate.setTranslateX(tx + r);
		textTranslate.setTranslateY(ty);
	}

	public void setText(String s) {
		title = s;
		sgText.setText(s);
	}

	public void updateWidth() {
		Rectangle2D rect;
		rect = sgText.getBounds();
		w = Tools.constrain((float) rect.getWidth() + r * 2, 40, 1000);
		baseRect.setShape(new RoundRectangle2D.Float(0, 0, w, h, r, r));
	}

	public void createObject() {
		String t;
		t = title;

//		if (t.charAt(1) == '.' && t.charAt(0) == 'm') {
//			String subT = t.substring(2);
//			OverFlowMain.objects.add(new Message(subT, ox, oy));
//			Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
//			OverFlowMain.rootGroup.add(n.returnGroup());
//		}
		
		if (t.equals("print")) {	
			if(argumentArray != null){
				if(arguments.size() > 0){		//set to minimum arguments needed

					OverFlowMain.objects.add(new Print(arguments.get(0), ox, oy));
					Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
					OverFlowMain.rootGroup.add(n.returnGroup());
					arguments.clear();				//always clear the argument array for the next creation or else you have left overs
				}
				else if(arguments.size() < 0){
					OverFlowMain.objects.add(new Print("print", ox, oy));
					Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
					OverFlowMain.rootGroup.add(n.returnGroup());
				}
				else {
				}
			}
		}

		else if (t.equals("sqrt")) {
			
			OverFlowMain.objects.add(new SQRT(ox, oy));
			Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
			OverFlowMain.rootGroup.add(n.returnGroup());
		}
		
		else if (t.equals("tan")) {
			
			OverFlowMain.objects.add(new Tan(ox, oy));
			Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
			OverFlowMain.rootGroup.add(n.returnGroup());
		}

		else if (t.equals("cos")) {
			
			OverFlowMain.objects.add(new Cos(ox, oy));
			Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
			OverFlowMain.rootGroup.add(n.returnGroup());
		}
		
		else if (t.equals("sin")) {
			
			OverFlowMain.objects.add(new Sin(ox, oy));
			Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
			OverFlowMain.rootGroup.add(n.returnGroup());
		}
		
		else if (t.equals("drunk")) {
			
			OverFlowMain.objects.add(new Drunk(ox, oy));
			Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
			OverFlowMain.rootGroup.add(n.returnGroup());
		}
		
		else if (t.equals("gate")) {
			
			OverFlowMain.objects.add(new Gate(ox, oy));
			Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
			OverFlowMain.rootGroup.add(n.returnGroup());
		}
		
		else if (t.equals("peak")) {
			
			OverFlowMain.objects.add(new Peak(ox, oy));
			Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
			OverFlowMain.rootGroup.add(n.returnGroup());
		}
		
		else if (t.equals("trough")) {
			
			OverFlowMain.objects.add(new Trough(ox, oy));
			Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
			OverFlowMain.rootGroup.add(n.returnGroup());
		}
		
		else if (t.equals("noteseq")) {	
				OverFlowMain.objects.add(new NoteSequencer(ox, oy));
				Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
				OverFlowMain.rootGroup.add(n.returnGroup());
				arguments.clear();				//always clear the argument array for the next creation or else you have left overs
		}

		else if (t.equals("bang")) {	
			OverFlowMain.objects.add(new Bang(ox, oy));
			Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
			OverFlowMain.rootGroup.add(n.returnGroup());
			arguments.clear();				//always clear the argument array for the next creation or else you have left overs
		}
			
		else if (t.equals("change")) {	
			OverFlowMain.objects.add(new Change(ox, oy));
			Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
			OverFlowMain.rootGroup.add(n.returnGroup());
			arguments.clear();				//always clear the argument array for the next creation or else you have left overs
		}
		
		else if (t.equals("uzi")) {	
			if(argumentArray != null){
				if(arguments.size() > 0){
				float numBangs = Float.parseFloat(arguments.get(0));
				OverFlowMain.objects.add(new Uzi(text, ox, oy, (int)numBangs));
				Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
				OverFlowMain.rootGroup.add(n.returnGroup());
				arguments.clear();				//always clear the argument array for the next creation or else you have left overs
				}
			}
		}
		
		else if (t.equals("constrain")) {	
			if(argumentArray != null){
				if(arguments.size() > 1){
				float minVal = Float.parseFloat(arguments.get(0));
				float maxVal = Float.parseFloat(arguments.get(1));
				OverFlowMain.objects.add(new ConstrainNode(text, ox, oy, minVal, maxVal));

				Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
				OverFlowMain.rootGroup.add(n.returnGroup());
				arguments.clear();				//always clear the argument array for the next creation or else you have left overs
				}
			}
		}

		else if (t.equals("loop")) {	
			if(argumentArray != null){
				if(arguments.size() > 0){		//set to minimum arguments needed
				int count = Integer.parseInt(arguments.get(0));
				OverFlowMain.objects.add(new Loop(ox, oy, count));
				Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
				OverFlowMain.rootGroup.add(n.returnGroup());
				arguments.clear();				//always clear the argument array for the next creation or else you have left overs
				}
			}
		}
		
		else if (t.equals("counter")) {	
			if(argumentArray != null){
				if(arguments.size() > 0){		//set to minimum arguments needed
				float max = Float.parseFloat(arguments.get(0));
				OverFlowMain.objects.add(new Counter(text, ox, oy, (int)max));
				Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
				OverFlowMain.rootGroup.add(n.returnGroup());
				arguments.clear();				//always clear the argument array for the next creation or else you have left overs
				}
			}
		}

		else if (t.equals("math.>")) {
		System.out.println(">>>>>>>");
		}
		
		else if (t.equals("map")) {	
			if(argumentArray != null){
				if(arguments.size() > 3){		//set to minimum arguments needed
				float srcMin = Float.parseFloat(arguments.get(0));
				float srcMax = Float.parseFloat(arguments.get(1));
				float tgtMin = Float.parseFloat(arguments.get(2));
				float tgtMax = Float.parseFloat(arguments.get(3));
				OverFlowMain.objects.add(new Map(text, ox, oy, srcMin, srcMax, tgtMin, tgtMax));
				Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
				OverFlowMain.rootGroup.add(n.returnGroup());
				arguments.clear();				//always clear the argument array for the next creation or else you have left overs
				}
			}
		}

		else if (t.equals("line")) {	
			if(argumentArray != null){
				if(arguments.size() > 2){		//set to minimum arguments needed
				float duration = Float.parseFloat(arguments.get(0));
				float srcMin = Float.parseFloat(arguments.get(1));
				float srcMax = Float.parseFloat(arguments.get(2));
				OverFlowMain.objects.add(new Line(text, ox, oy, duration, srcMin, srcMax));
				Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
				OverFlowMain.rootGroup.add(n.returnGroup());
				arguments.clear();				//always clear the argument array for the next creation or else you have left overs
				}
			}
		}
		
		else if (t.equals("random")) {	
			if(argumentArray != null){
				if(arguments.size() > 1){		//set to minimum arguments needed
				float srcMin = Float.parseFloat(arguments.get(0));
				float srcMax = Float.parseFloat(arguments.get(1));
				OverFlowMain.objects.add(new Random(text, ox, oy, srcMin, srcMax));
				Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
				OverFlowMain.rootGroup.add(n.returnGroup());
				arguments.clear();				//always clear the argument array for the next creation or else you have left overs
				}
			}
		}
		
		else if (t.equals("timer")) {	
				if(arguments.size() > 0){		//set to minimum arguments needed
				float delayTime = Float.parseFloat(arguments.get(0));
				OverFlowMain.objects.add(new TimerNode(text, ox, oy, delayTime));
				Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
				OverFlowMain.rootGroup.add(n.returnGroup());
				arguments.clear();				//always clear the argument array for the next creation or else you have left overs
			}
		}
		
		else if (t.equals("toggle") || t.equals("t")) {
			OverFlowMain.objects.add(new Toggle(ox, oy));
			Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects
					.size() - 1);
			OverFlowMain.rootGroup.add(n.returnGroup());
		}

		else if (t.equals("dial")) {
			OverFlowMain.objects.add(new Dial(ox, oy, 50));
			Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects
					.size() - 1);
			OverFlowMain.rootGroup.add(n.returnGroup());
		}

		else if (t.equals("slider") || t.equals("sl")) {
			OverFlowMain.objects.add(new Slider(ox, oy, 30, 150));
			Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects
					.size() - 1);
			OverFlowMain.rootGroup.add(n.returnGroup());
		}

		else if (t.equals("stepseq")) {
			OverFlowMain.objects
					.add(new StepSequencer(ox, oy, 400, 150, 16, 8));
			Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects
					.size() - 1);
			OverFlowMain.rootGroup.add(n.returnGroup());
		}

		else if (t.equals("+")) {
			OverFlowMain.objects.add(new Add(ox, oy));
			Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects
					.size() - 1);
			OverFlowMain.rootGroup.add(n.returnGroup());
		}

		else if (t.equals("float") || t.equals("f")) {
			OverFlowMain.objects.add(new FloatDisp(ox, oy));
			Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects
					.size() - 1);
			OverFlowMain.rootGroup.add(n.returnGroup());
		}

		else if (t.equals("subtract")) {
			OverFlowMain.objects.add(new Subtract(ox, oy));
			Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects
					.size() - 1);
			OverFlowMain.rootGroup.add(n.returnGroup());
		}

		else if (t.equals("multiply")) {
			OverFlowMain.objects.add(new Multiply(ox, oy));
			Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects
					.size() - 1);
			OverFlowMain.rootGroup.add(n.returnGroup());
		}

		else if (t.equals("divide")) {
			OverFlowMain.objects.add(new Divide(ox, oy));
			Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects
					.size() - 1);
			OverFlowMain.rootGroup.add(n.returnGroup());
		}

		else {
			OverFlowMain.objects.add(new Node(t, ox, oy, 0, 0));
			Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects
					.size() - 1);
			OverFlowMain.rootGroup.add(n.returnGroup());
		}
		sgText.setText("");
		creating = false;
	}


	class TextEntryListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			// TODO Auto-generated method stub

		}
	}
	class TextEntryKeyListener implements KeyListener {
		@Override
		public void keyPressed(KeyEvent arg0) {
			// TODO Auto-generated method stub
			if(arg0.getKeyCode() == 8){
				if(keyCount > 0){
					keyCount-=2;
				}
			}
			
			if(arg0.getKeyCode() == 10) {
			     setVisible(false);

				int argumentCount = 0;	
				text = textEntry.getText();

				keyCount = 0;	
				textEntry.setText(" ");
				textEntry.setColumns(keyCount/2 + 5);
				w = 60;
				baseRect.setShape(new RoundRectangle2D.Float(0,0,w,h, r, r));
			     
				 Scanner s = new Scanner(text);
				 
			     while(s.hasNext()){

			     argumentCount++;
			     if(argumentCount == 1){
			    	 title = s.next();
			     }
			     else if(argumentCount > 1){
			     	arguments.addElement(s.next()); 
			     	argumentArray = arguments.toString();
			     }
			     createObject();
			     textEntry.setEditable(false);	     
			     }
				 s.close();
			}
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
//			System.out.println(arg0.getKeyCode());
				keyCount++;
						
			if(keyCount % 3 == 0){
				textEntry.setColumns(keyCount/2 + 5);
				w = textEntry.getWidth() + r * 2;
				baseRect.setShape(new RoundRectangle2D.Float(0,0,w,h, r, r));
			}
			

		}
	}

}


class TextKeyListener implements KeyListener {
	@Override
	public void keyPressed(KeyEvent e) {
		if(!ObjectCreator2.creating){
			MainFrameInput.selectionManager.keyTyped(e);		//can't get the key events to be sent to the main frame listener after I start using the textentry so I pass it on 
		}
		// TODO Auto-generated method stub
		MainFrameInput.modifier = e.getModifiers();
		if(e.getKeyCode() == 16){
			MainFrameInput.shiftDown = true;
		}
		if (e.getModifiers() == 8 && e.getKeyCode() == 69) {
			;
			if (OverFlowMain.getEditMode()) {
				OverFlowMain.setEditMode(false);
			} else {
				OverFlowMain.setEditMode(true);
			}
		}

		if (e.getModifiers() == 8 && e.getKeyCode() == 83) {
			if (OverFlowMain.getSnapMode()) {
				OverFlowMain.setSnapMode(false);
			} else {
				OverFlowMain.setSnapMode(true);
			}
		}

		if (e.getKeyCode() == 27) { // esc button to quit program
			System.exit(0);
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		MainFrameInput.shiftDown = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}