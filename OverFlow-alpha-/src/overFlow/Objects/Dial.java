package overFlow.Objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import overFlow.Tools.Tools;
import overFlow.main.Node;
import overFlow.main.OverFlowMain;
import overFlow.main.MainFrameInput;

import com.sun.scenario.effect.DropShadow;
import com.sun.scenario.scenegraph.SGGroup;
import com.sun.scenario.scenegraph.SGNode;
import com.sun.scenario.scenegraph.SGShape;
import com.sun.scenario.scenegraph.SGTransform;
import com.sun.scenario.scenegraph.event.SGMouseAdapter;
import com.sun.scenario.scenegraph.fx.FXShape;

public class Dial extends Node {
  FXShape bigArc = new FXShape();
  FXShape smallEllipse = new FXShape();
  SGGroup subGroup = new SGGroup();  
  DropShadow iShadow = new DropShadow();
  double theta = 0;
  SGTransform.Affine dialAffine;
  PrefBtn preffs;

  float cx, cy, px, py;
  double rotationStep = 30.0;

  public Dial(float tx, float ty, float size) {
    super("", tx, ty, size, size, 1, 1);
    createDial();
    addShadow();
  }

  void createDial(){
	super.setBaseColor(new Color(200,200,200));
    bigArc.setShape(new Arc2D.Double(0, 0, w - 10, h - 10, 125, 290, 0));
    bigArc.setTranslateX(x + 5);
    bigArc.setTranslateY(y + 5);
    bigArc.setDrawPaint(new Color(50,50,50));
    bigArc.setMode(SGShape.Mode.STROKE);
    bigArc.setDrawStroke(new BasicStroke(2));
    bigArc.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON); 

    smallEllipse.setShape(new Ellipse2D.Double(0, 0, w / 5, h / 5));
    smallEllipse.setTranslateX(x + w/2 - w/10);
    smallEllipse.setTranslateY(y + 5);
    smallEllipse.setDrawPaint(Color.GREEN);
    smallEllipse.setMode(SGShape.Mode.STROKE);
    smallEllipse.setDrawStroke(new BasicStroke(2));
    smallEllipse.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON); 
 
    iShadow.setColor(new Color(0,255,0,150));
    iShadow.setRadius(5);
    iShadow.setOffsetX(3);
    iShadow.setOffsetY(3);
    
    smallEllipse.setEffect(iShadow);
    preffs = new PrefBtn(x + w - 8, y + 4, 5);
    group.add(preffs.btnGroup);

    shadow.setOffsetX(2);
    shadow.setOffsetY(2);
    shadow.setRadius(7);
    subGroup.add(smallEllipse);
    subGroup.add(bigArc);

    dialAffine = SGTransform.createAffine(new AffineTransform(), subGroup);

    group.add(dialAffine);
  }

  public void nodeDragged() {
    if(!OverFlowMain.editMode){
      theta -= MainFrameInput.yVel * 0.01;
      theta = Tools.constrain((float)theta, (float)-2.6, (float)2.6); 
      rotate(theta);
      value = (float)((theta * -2.6) / (2.6 - -2.6) * ((100 - 0) + 0));
      outputValues[0] = value;
      super.updateConnections();
    }
  }

  public void nodeOver() {
    if(OverFlowMain.editMode){
      preffs.setVisible(true);    
    }
  }

  public void nodeExited() {
    if(OverFlowMain.editMode) {
      preffs.setVisible(false); 
    }
  }

  public void update() {
    if(inputValues[0] != null) {
      value = inputValues[0];
      value = Tools.constrain(value, 0, 100);
      outputValues[0] = value;
      updateConnections(); 
      theta = Tools.map(value, 0f, 100f, -2.6f, 2.6f);
      theta = Tools.constrain((float)theta, -2.6f, 2.6f); 
      rotate(theta);
      redrawNode();
    }
  }


  private void rotate(double theta) {
    AffineTransform at = new AffineTransform();
    at.rotate(theta, x + w/2 - xOffset,  y + h/2 - yOffset);
    dialAffine.setAffine(at);

  }

}


