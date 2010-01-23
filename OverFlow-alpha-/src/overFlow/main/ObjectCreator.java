package overFlow.main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.Highlighter;

import overFlow.Objects.Add;
import overFlow.Objects.Dial;
import overFlow.Objects.Divide;
import overFlow.Objects.FloatDisp;
import overFlow.Objects.Message;
import overFlow.Objects.Multiply;
import overFlow.Objects.Print;
import overFlow.Objects.Slider;
import overFlow.Objects.StepSequencer;
import overFlow.Objects.Subtract;
import overFlow.Objects.Toggle;
import overFlow.Tools.Tools;

import com.sun.scenario.scenegraph.SGGroup;
import com.sun.scenario.scenegraph.SGText;
import com.sun.scenario.scenegraph.SGTransform;
import com.sun.scenario.scenegraph.fx.FXShape;
import com.sun.scenario.scenegraph.JSGPanel;
import com.sun.scenario.scenegraph.SGComponent;
import com.sun.scenario.scenegraph.SGGroup;
import com.sun.scenario.scenegraph.SGNode;
import com.sun.scenario.scenegraph.SGShape;
import com.sun.scenario.scenegraph.SGTransform;
import com.sun.scenario.scenegraph.event.SGMouseAdapter;
import com.sun.scenario.scenegraph.fx.FXShape;
import com.sun.scenario.scenegraph.fx.FXText;


public class ObjectCreator {
	  static JTextField textEntry = new JTextField(5);

	  SGText sgText = new SGText();
	  FXShape baseRect = new FXShape(); 
	  SGGroup group = new SGGroup();
	  float x, y, w, h, r;
	  SGTransform.Translate groupT = SGTransform.createTranslation(0, 0, group);

	  //variables for newly created object
	  String title;
	  int ins;
	  int outs;
	  float ox, oy, ow, oh;
	  public boolean over;

	  String[] argumentsStrings;



	  ObjectCreator(float tx, float ty) {
	    x = tx;
	    y = ty;
	    w = 50;
	    h = 20;
	    r = 8;  
	    
		SGComponent comp = new SGComponent();
		comp.setComponent(new NodeTextEntry(x + r, y + 2, group));
		comp.setID("Object Menu");
		group.add(comp);
	    
//	    textEntry.setLocation((int) ((int)x + r), (int)y + 3);
	    textEntry.addActionListener( new TextEntryListener());

	    
	    create();
	    OverFlowMain.rootGroup.add(group);  
	  }


	  void create() {
	    baseRect.setShape(new RoundRectangle2D.Float(0, 0, w, h, r, r));
	    baseRect.setFillPaint(new Color(255, 255, 255));      
	    baseRect.setMode(SGShape.Mode.STROKE_FILL); 
	    baseRect.setDrawStroke(new BasicStroke(2.0f));
	    baseRect.setDrawPaint(new Color(150,150,150));
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
	      public void mousePressed(MouseEvent e, SGNode n){
	      }
	      public void mouseReleased(MouseEvent e, SGNode n) { 
	      }
	      public void mouseDragged(MouseEvent e, SGNode n) {
	      }
	    }
	    );
	    
	    sgText.setFont(new Font("Helvetica", Font.PLAIN, 14));
	    sgText.setLocation(new Point2D.Float(r,4));
	    sgText.setAntialiasingHint(RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	    sgText.setFillPaint(Color.BLACK);

	    group.add(baseRect);
	    group.add(sgText);
	    group.setVisible(false);

	  }

	  public void setVisible(boolean visible) {
	    group.setVisible(visible);
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
	    Point2D.Float p = new Point2D.Float(tx + r, ty + 15);
	    Point2D p1 = p;
	    sgText.setLocation(p1);
	  }

	  public void setText(String s) {

	    title = s;   

	    sgText.setText(s);
	  }

	  public void updateWidth() {
	    Rectangle2D rect;
	    rect = sgText.getBounds();
	    w = Tools.constrain((float)rect.getWidth() + r * 2, 40,1000);
	    baseRect.setShape(new RoundRectangle2D.Float(0, 0, w, h, r, r));
	  }

	  public void createObject() {
	    String t;
	    t = title;
//	    parseArguments(title);

	    if(t.charAt(1) == '.' && t.charAt(0) == 'm'){
	      String subT = t.substring(2);
	      OverFlowMain.objects.add(new Message(subT, ox, oy));
	      Node n = (Node)OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
	      OverFlowMain.rootGroup.add(n.returnGroup());  
	    }

	    else if(t.equals("print")) {
	    	OverFlowMain.objects.add(new Print("print", ox, oy));
	      Node n = (Node)OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
	      OverFlowMain.rootGroup.add(n.returnGroup());  
	    }

	    else if(t.equals("toggle")) {
	      OverFlowMain.objects.add(new Toggle(ox, oy));
	      Node n = (Node)OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
	      OverFlowMain.rootGroup.add(n.returnGroup());  
	    }

//	    else if(t.equals("bang")) {
//	    	OverFlowMain.objects.add(new Bang(ox, oy));
//	      Node n = (Node)OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
//	      OverFlowMain.rootGroup.add(n.returnGroup()); 
//	    }

	    else if(t.equals("dial")) {
	    	OverFlowMain.objects.add(new Dial(ox, oy, 50));
	      Node n = (Node)OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
	      OverFlowMain.rootGroup.add(n.returnGroup()); 
	    }

	    else if(t.equals("slider")) {
	    	OverFlowMain.objects.add(new Slider(ox, oy, 30, 150));
	      Node n = (Node)OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
	      OverFlowMain.rootGroup.add(n.returnGroup()); 
	    }    

	    else if(t.equals("stepseq")) {
	      OverFlowMain.objects.add(new StepSequencer(ox, oy, 400,150,16,8));
	      Node n = (Node)OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
	      OverFlowMain.rootGroup.add(n.returnGroup()); 
	    }

	    else if(t.equals("add")) {
	    	OverFlowMain.objects.add(new Add(ox, oy));
	      Node n = (Node)OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
	      OverFlowMain.rootGroup.add(n.returnGroup()); 
	    }

	    else if(t.equals("float")) {
	    	OverFlowMain.objects.add(new FloatDisp(ox, oy));
	      Node n = (Node)OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
	      OverFlowMain.rootGroup.add(n.returnGroup()); 
	    }

	    else if(t.equals("subtract")) {
	    	OverFlowMain.objects.add(new Subtract(ox, oy));
	      Node n = (Node)OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
	      OverFlowMain.rootGroup.add(n.returnGroup()); 
	    }

	    else if(t.equals("multiply")) {
	    	OverFlowMain.objects.add(new Multiply(ox, oy));
	      Node n = (Node)OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
	      OverFlowMain.rootGroup.add(n.returnGroup()); 
	    }

	    else if(t.equals("divide")) {
	    	OverFlowMain.objects.add(new Divide(ox, oy));
	      Node n = (Node)OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
	      OverFlowMain.rootGroup.add(n.returnGroup()); 
	    }

//	    else if(t.equals("timer")) {
//	    	OverFlowMain.objects.add(new Timer(ox, oy));
//	      Node n = (Node)OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
//	      OverFlowMain.rootGroup.add(n.returnGroup()); 
//	    }


	    else {
	    	OverFlowMain.objects.add(new Node(t, ox, oy, 0, 0));
	      Node n = (Node)OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
	      OverFlowMain.rootGroup.add(n.returnGroup());
	    }
	    sgText.setText("");
	  }

	  
	  String[] parseArguments(String arguments) {
	    String[] aStrings;
	    char[] argumentList;
	    Vector argumentBreakPoints = new Vector(0);
	    for(int i = 0; i < arguments.length() - 1; i++) {
	      if(arguments.charAt(i) == ' ') {
	        argumentBreakPoints.addElement(new Integer(i)); 
	      } 
	    }
	      aStrings = new String[argumentBreakPoints.size()];
	      argumentBreakPoints.insertElementAt(0,0);

	      for(int j = 0; j < argumentBreakPoints.size() - 1; j++) {
	        int v1 = ((Integer)argumentBreakPoints.get(j)).intValue();
	        int v2 = ((Integer)argumentBreakPoints.get(j + 1)).intValue();
	        aStrings[j] = arguments.substring(v1, v2);  
	      }
	        aStrings[argumentBreakPoints.size() - 1] = arguments.substring(argumentBreakPoints.size() );
   
	    return aStrings;
	  } 
	  
	  
	  
	  class TextEntryListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent event) {
				// TODO Auto-generated method stub
				String text = textEntry.getText();
				System.out.println(text);
			}
		
		  }

	  }
























