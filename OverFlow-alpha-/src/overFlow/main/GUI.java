package overFlow.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.JTextField;

import overFlow.Objects.Dial;
import overFlow.Objects.FloatDisp;
import overFlow.Objects.Message;
import overFlow.Objects.MultiSlider;
import overFlow.Objects.Slider;
import overFlow.Objects.StepSequencer;
import overFlow.Objects.Toggle;

import com.sun.scenario.scenegraph.SGComponent;
import com.sun.scenario.scenegraph.SGImage;
import com.sun.scenario.scenegraph.fx.FXShape;


//An inner class for all GUI elements.

public class GUI extends javax.swing.JFrame {

int fHeight;
int fWidth;


public GUI() {
 initComponents();
 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

}

void initComponents() {
	
    OverFlowMain.objects.add(new Dial(350, 300, 50));
    Node n = (Node)OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
    OverFlowMain.rootGroup.add(n.returnGroup());  
	
    OverFlowMain.objects.add(new Slider(100,100,50,50));
    Node m = (Node)OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
    OverFlowMain.rootGroup.add(m.returnGroup());  
    
    OverFlowMain.objects.add(new FloatDisp(500,500));
    Node s = (Node)OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
    OverFlowMain.rootGroup.add(s.returnGroup());  
    
    OverFlowMain.objects.add(new FloatDisp(570,200));
    Node q = (Node)OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
    OverFlowMain.rootGroup.add(q.returnGroup());     
    
    OverFlowMain.objects.add(new StepSequencer(400,400,400,150,16,8));
    Node t = (Node)OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
    OverFlowMain.rootGroup.add(t.returnGroup()); 
    
    OverFlowMain.objects.add(new Toggle(10,360));
    Node z = (Node)OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
    OverFlowMain.rootGroup.add(z.returnGroup()); 
    
    OverFlowMain.objects.add(new Toggle(40,140));
    Node w = (Node)OverFlowMain.objects.get(OverFlowMain.objects.size() - 1);
    OverFlowMain.rootGroup.add(w.returnGroup()); 


	
	
	
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Image image1 = toolkit.getImage("Creek.jpg");
	SGImage sliderIcon = new SGImage();
	sliderIcon.setImage(image1);
	sliderIcon.setLocation(new Point2D.Float(200,200));
	OverFlowMain.addToRootGroup(sliderIcon);
	System.out.println(sliderIcon.getLocation());
	
	
 MainFrameInput mainWindowGraphics = new MainFrameInput();

 OverFlowMain.gpanel.setBackground(Color.WHITE);
 OverFlowMain.gpanel.setScene(OverFlowMain.rootGroup);
 OverFlowMain.gpanel.setPreferredSize(new Dimension(800, 600));    //mouse events for whole scene
 OverFlowMain.gpanel.setBackground(new Color(100,100,100));
 
 OverFlowMain.editModeLabel.setFont(new Font("Helvetica", Font.PLAIN, 14));
 OverFlowMain.editModeLabel.setLocation(new Point(50, 20));
 OverFlowMain.editModeLabel.setAntialiasingHint(RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
 OverFlowMain.editModeLabel.setText("Edit Mode");
 OverFlowMain.editModeLabel.setFillPaint(Color.BLACK);
 OverFlowMain.rootGroup.add(OverFlowMain.editModeLabel);

 getContentPane().add( OverFlowMain.gpanel);
 pack();
}

////get the location of the current FXShape
Point2D.Double shapeLocation(FXShape s) {
 Rectangle2D rect = s.getBounds();
 Point2D.Double p = new Point2D.Double(rect.getX() + (rect.getWidth() - rect.getX())/2, rect.getY());
 return p;
}





}





































