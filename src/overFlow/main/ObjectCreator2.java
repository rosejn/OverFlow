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
import java.util.Vector;

import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import overFlow.Atom.Atom;
import overFlow.Atom.AtomArray;
import overFlow.Atom.AtomFloat;
import overFlow.Atom.AtomInt;
import overFlow.Atom.AtomString;
import overFlow.Objects.Add;
import overFlow.Objects.Bang;
import overFlow.Objects.Change;
import overFlow.Objects.Comment;
import overFlow.Objects.ConstrainNode;
import overFlow.Objects.Counter;
import overFlow.Objects.Dial;
import overFlow.Objects.Divide;
import overFlow.Objects.FloatDisp;
import overFlow.Objects.Gate;
import overFlow.Objects.IntDisp;
import overFlow.Objects.Key;
import overFlow.Objects.Line;
import overFlow.Objects.List_rotate;
import overFlow.Objects.List_size;
import overFlow.Objects.List_slice;
import overFlow.Objects.Loop;
import overFlow.Objects.Map;
import overFlow.Objects.Message;
import overFlow.Objects.Metro;
import overFlow.Objects.MetroList;
import overFlow.Objects.Multiply;
import overFlow.Objects.NoteSequencer;
import overFlow.Objects.PLCD;
import overFlow.Objects.Peak;
import overFlow.Objects.Prepend;
import overFlow.Objects.Print;
import overFlow.Objects.Random;
import overFlow.Objects.Route;
import overFlow.Objects.Slider;
import overFlow.Objects.StepSequencer;
import overFlow.Objects.Subtract;
import overFlow.Objects.Toggle;
import overFlow.Objects.Trough;
import overFlow.Objects.Uzi;

import overFlow.Tools.Tools;

import com.sun.scenario.effect.light.SpotLight;
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
	SGTransform.Translate textTranslate = SGTransform.createTranslation(0, 0,
			textGroup);
	public Vector<Atom> arguments = new Vector<Atom>();
	Vector newArgs = new Vector();
	// variables for newly created object
	String title;
	int ins;
	int outs;
	float ox, oy, ow, oh;
	public boolean over;
	static boolean creating = false;
	boolean messageType = false;
	private String argumentArray;
	int argumentCount = 0;
	String text;
	SpotLight spotLight = new SpotLight(0f,0f,-4f, Color.red);

	ObjectCreator2(float tx, float ty) {
		x = tx;
		y = ty;
		w = 50;
		h = 20;
		r = 8;
		textEntry.setBackground(new Color(0, 0, 0, 0));
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
		if (visible) {
			textEntry.setText("");
			textEntry.setEditable(true);
			textEntry.requestFocus(true);
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
		t = arguments.get(0).getString();
		System.out.println(arguments.toString());
		
		if(!messageType){

		if (t.equals("print")) {
			if (argumentCount == 1) {
				OverFlowMain.objects.add(new Print("", ox, oy));
				Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
				OverFlowMain.rootGroup.add(n.returnGroup());
				arguments.clear();

				} else {
					OverFlowMain.objects.add(new Print(arguments.get(1).getString(), ox, oy));
					Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
					OverFlowMain.rootGroup.add(n.returnGroup());
					arguments.clear(); // always clear the argument array for
										// the next creation or else you have
										// left overs
				}
		}

		else if (t.equals("comment")) {
			OverFlowMain.objects.add(new Comment(ox, oy));
			Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
			OverFlowMain.rootGroup.add(n.returnGroup());
			arguments.clear();
		}

		else if (t.equals("plcd")) {
			OverFlowMain.objects.add(new PLCD(ox, oy));
			Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
			OverFlowMain.rootGroup.add(n.returnGroup());
			arguments.clear();
		}

		else if (t.equals("gate")) {
			OverFlowMain.objects.add(new Gate(ox, oy));
			Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
			OverFlowMain.rootGroup.add(n.returnGroup());
			arguments.clear();
		}

		else if (t.equals("peak")) {
			OverFlowMain.objects.add(new Peak(ox, oy));
			Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
			OverFlowMain.rootGroup.add(n.returnGroup());
			arguments.clear();
		}

		else if (t.equals("trough")) {
			OverFlowMain.objects.add(new Trough(ox, oy));
			Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
			OverFlowMain.rootGroup.add(n.returnGroup());
			arguments.clear();
		}

		else if (t.equals("noteseq") || t.equals("ns")) {
			OverFlowMain.objects.add(new NoteSequencer(ox, oy));
			Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
			OverFlowMain.rootGroup.add(n.returnGroup());
			arguments.clear(); // always clear the argument array for the next
								// creation or else you have left overs
		}

		else if (t.equals("key")) { // output keyboard key events
			OverFlowMain.objects.add(new Key(ox, oy));
			Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
			OverFlowMain.rootGroup.add(n.returnGroup());
			arguments.clear(); // always clear the argument array for the next
								// creation or else you have left overs
		}

		else if (t.equals("bang") || t.equals("b")) {
			OverFlowMain.objects.add(new Bang(ox, oy));
			Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
			OverFlowMain.rootGroup.add(n.returnGroup());
			arguments.clear(); // always clear the argument array for the next
								// creation or else you have left overs
		}

		else if (t.equals("change")) {
			OverFlowMain.objects.add(new Change(ox, oy));
			Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
			OverFlowMain.rootGroup.add(n.returnGroup());
			arguments.clear(); // always clear the argument array for the next
								// creation or else you have left overs
		}

		else if (t.equals("lsSize")) {
			OverFlowMain.objects.add(new List_size(text, ox, oy));
			Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
			OverFlowMain.rootGroup.add(n.returnGroup());
			arguments.clear();
		}

		else if (t.equals("ls")) {
			if(arguments.get(1).getString().equals("slice")){
					OverFlowMain.objects.add(new List_slice(text, ox, oy, arguments.get(2).getInt()));
					Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
					OverFlowMain.rootGroup.add(n.returnGroup());
					arguments.clear(); // always clear the argument array for
				}
			else if(arguments.get(1).getString().equals("size")){
				OverFlowMain.objects.add(new List_size(text, ox, oy));
				Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
				OverFlowMain.rootGroup.add(n.returnGroup());
				arguments.clear(); // always clear the argument array for
			}
			else if(arguments.get(1).getString().equals("rot")){
				OverFlowMain.objects.add(new List_rotate(text, ox, oy, arguments.get(1).getInt()));
				Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
				OverFlowMain.rootGroup.add(n.returnGroup());
				arguments.clear(); // always clear the argument array for
			}
		
		}

		else if (t.equals("uzi")) {
			if (argumentArray != null) {
				if (arguments.size() > 0) {
					OverFlowMain.objects.add(new Uzi(text, ox, oy,arguments.get(0).getInt()));
					Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
					OverFlowMain.rootGroup.add(n.returnGroup());
					arguments.clear(); // always clear the argument array for
										// the next creation or else you have
										// left overs
				}
			}
		}

		else if (t.equals("constrain")) {
					OverFlowMain.objects.add(new ConstrainNode(text, ox, oy,arguments.get(0).getInt(), arguments.get(1).getInt()));
					Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
					OverFlowMain.rootGroup.add(n.returnGroup());
					arguments.clear(); // always clear the argument array for
		}

		else if (t.equals("route")) {
			if (argumentArray != null) {
				OverFlowMain.objects.add(new Route(text, ox, oy, arguments));
				Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
				OverFlowMain.rootGroup.add(n.returnGroup());
				arguments.clear(); // always clear the argument array for the
									// next creation or else you have left overs
			}
		}

		else if (t.equals("loop")) {
			if (argumentArray != null) {
				if (arguments.size() > 0) { // set to minimum arguments needed
					OverFlowMain.objects.add(new Loop(ox, oy, arguments.get(1).getInt()));
					Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
					OverFlowMain.rootGroup.add(n.returnGroup());
					arguments.clear(); // always clear the argument array for
										// the next creation or else you have
										// left overs
				}
			}
		}

		else if (t.equals("counter")) {
					OverFlowMain.objects.add(new Counter(text, ox, oy, arguments.get(1).getInt()));
					Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
					OverFlowMain.rootGroup.add(n.returnGroup());
					arguments.clear(); // always clear the argument array for
										// the next creation or else you have
										// left overs
		}

		else if (t.equals("map")) {
					OverFlowMain.objects.add(new Map(text, ox, oy, arguments.get(1).getFloat(),arguments.get(2).getFloat(), arguments.get(3).getFloat(), arguments.get(4).getFloat()));
					Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
					OverFlowMain.rootGroup.add(n.returnGroup());
					arguments.clear(); // always clear the argument array for
										// the next creation or else you have
										// left overs
		}

		else if (t.equals("line")) {
					OverFlowMain.objects.add(new Line(text, ox, oy, arguments.get(1).getFloat(),arguments.get(2).getFloat(), arguments.get(3).getFloat()));
					Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
					OverFlowMain.rootGroup.add(n.returnGroup());
					arguments.clear(); // always clear the argument array for
										// the next creation or else you have
										// left overs
			}

		
		else if (t.equals("prepend")) {
					arguments.remove(0);
					AtomArray atomArray = new AtomArray(arguments);
					OverFlowMain.objects.add(new Prepend(text, ox, oy, atomArray));	//create an atom array from the arguments
					Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
					OverFlowMain.rootGroup.add(n.returnGroup());
					arguments.clear(); // always clear the argument array
		}
		
		
		else if (t.equals("random")) {
			if (argumentArray != null) {
				if (arguments.size() > 1) { // set to minimum arguments needed
					OverFlowMain.objects.add(new Random(text, ox, oy, arguments.get(1).getFloat(),arguments.get(2).getFloat()));
					Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
					OverFlowMain.rootGroup.add(n.returnGroup());
					arguments.clear(); // always clear the argument array for
										// the next creation or else you have
										// left overs
				}
			}
		}

		else if (t.equals("metrolist")) {
				OverFlowMain.objects.add(new MetroList(text, ox, oy, arguments.get(1).getFloat()));
				Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
				OverFlowMain.rootGroup.add(n.returnGroup());
				arguments.clear(); // always clear the argument array for the
									// next creation or else you have left overs
		}

		else if (t.equals("metro")) {;
				OverFlowMain.objects.add(new Metro(text, ox, oy, arguments.get(1).getFloat()));
				Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
				OverFlowMain.rootGroup.add(n.returnGroup());
				arguments.clear(); // always clear the argument array for the
									// next creation or else you have left overs
		}

		else if (t.equals("toggle") || t.equals("t")) {
			OverFlowMain.objects.add(new Toggle(ox, oy));
			Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
			OverFlowMain.rootGroup.add(n.returnGroup());
		}

		else if (t.equals("dial")) {
			OverFlowMain.objects.add(new Dial(ox, oy, 50));
			Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
			OverFlowMain.rootGroup.add(n.returnGroup());
		}

		else if (t.equals("slider") || t.equals("sl")) {
			OverFlowMain.objects.add(new Slider(ox, oy, 30, 150));
			Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
			OverFlowMain.rootGroup.add(n.returnGroup());
		}

		else if (t.equals("stepseq") || t.equals("ss")) {
			OverFlowMain.objects
					.add(new StepSequencer(ox, oy, 400, 150, 16, 8));
			Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
			OverFlowMain.rootGroup.add(n.returnGroup());
		}

		else if (t.equals("+")) {
			OverFlowMain.objects.add(new Add(ox, oy));
			Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
			OverFlowMain.rootGroup.add(n.returnGroup());
		}

		else if (t.equals("int") || t.equals("i")) {
			OverFlowMain.objects.add(new IntDisp(ox, oy));
			Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
			OverFlowMain.rootGroup.add(n.returnGroup());
		}

		else if (t.equals("float") || t.equals("f")) {
			OverFlowMain.objects.add(new FloatDisp(ox, oy));
			Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
			OverFlowMain.rootGroup.add(n.returnGroup());
		}

		else if (t.equals("subtract")) {
			OverFlowMain.objects.add(new Subtract(ox, oy));
			Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
			OverFlowMain.rootGroup.add(n.returnGroup());
		}

		else if (t.equals("multiply")) {
			OverFlowMain.objects.add(new Multiply(ox, oy));
			Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
			OverFlowMain.rootGroup.add(n.returnGroup());
		}

		else if (t.equals("divide")) {
			OverFlowMain.objects.add(new Divide(ox, oy));
			Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
			OverFlowMain.rootGroup.add(n.returnGroup());
		}

		else {
			OverFlowMain.objects.add(new Node(t, ox, oy, 0, 0));
			Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
			OverFlowMain.rootGroup.add(n.returnGroup());
		}
		sgText.setText("");
		creating = false;
		}
		
		else if(messageType){		//if it is a message being created
			OverFlowMain.objects.add(new Message(text, ox, oy));
			Node n = (Node) OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
			OverFlowMain.rootGroup.add(n.returnGroup());
			arguments.clear(); // always clear the argument array for the next
								// creation or else you have left overs
			this.setMessageBuilder(false);
		}
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
			if (arg0.getKeyCode() == 8) {
				if (keyCount > 0) {
					keyCount -= 2;
				}
			}

			if (arg0.getKeyCode() == 10) {		//if enter is hit on the object creator
				setVisible(false);
				argumentCount = 0;
				arguments.clear();

				text = textEntry.getText();

				keyCount = 0;
				textEntry.setText(" ");
				textEntry.setColumns(keyCount / 2 + 5);
				w = 60;
				baseRect.setShape(new RoundRectangle2D.Float(0, 0, w, h, r, r));

				String[] stringPattern = text.split(" ");

				for (int i = 0; i < stringPattern.length; i++) {	//skip first element since it is the object name
					String arg = stringPattern[i];
					arguments.add(Atom.parseAtom(arg));
				}
				System.out.println(stringPattern.length + "  " + arguments.toString());
				argumentCount = arguments.size();
				textEntry.setEditable(false);
				textEntry.requestFocus(false);
				createObject();
			}
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			// System.out.println(arg0.getKeyCode());
			keyCount++;
			MainFrameInput.key = arg0;
			if (keyCount % 3 == 0) {
				textEntry.setColumns(keyCount / 2 + 5);
				w = textEntry.getWidth() + r * 2;
				baseRect.setShape(new RoundRectangle2D.Float(0, 0, w, h, r, r));
			}
		}
	}

	public void setMessageBuilder(boolean b) {
		// TODO Auto-generated method stub
		if(b){
			baseRect.setFillPaint(new Color(150, 150, 150));
			messageType = true;
		}
		else {
			baseRect.setFillPaint(new Color(250, 250, 250));
			messageType = false;
		}

		
	}
}

class TextKeyListener implements KeyListener {
	@Override
	public void keyPressed(KeyEvent e) {
		if (!ObjectCreator2.creating) {
			MainFrameInput.selectionManager.keyTyped(e); // can't get the key
															// events to be sent
															// to the main frame
															// listener after I
															// start using the
															// textentry so I
															// pass it on
		}
		// TODO Auto-generated method stub
		MainFrameInput.modifier = e.getModifiers();
		if (e.getKeyCode() == 16) {
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




//int ai = 0;
//float af = 0;
//try {
//	af = Float.parseFloat(stringPattern[i]);
//	arguments.add(new AtomFloat(af));
//} catch (NumberFormatException e) {
//	
//	try {
//		ai = Integer.parseInt(stringPattern[i]);
//		arguments.add(new AtomInt(ai));
//	} catch (NumberFormatException f) {
//			arguments.add( new AtomString(stringPattern[i]));
//	}