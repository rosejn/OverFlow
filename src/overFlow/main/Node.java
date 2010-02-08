package overFlow.main;

import org.ho.yaml.Yaml;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.Iterator;
import java.util.Vector;


import overFlow.Atom.Atom;
import overFlow.Tools.Tools;

import com.sun.scenario.effect.DropShadow;
import com.sun.scenario.effect.InnerShadow;
import com.sun.scenario.scenegraph.SGGroup;
import com.sun.scenario.scenegraph.SGShape;
import com.sun.scenario.scenegraph.SGNode;
import com.sun.scenario.scenegraph.SGText;
import com.sun.scenario.scenegraph.SGTransform;
import com.sun.scenario.scenegraph.event.SGMouseAdapter;
import com.sun.scenario.scenegraph.fx.FXShape;

public class Node {
	
	double tempx;
	double tempy;
	public float tx;
	public float ty;
	public float cx;
	public float cy;
	public float px;
	public float py;
	public float x;
	public float y;
	public float globalX;
	public float globalY;
	public float w;
	public float bw;
	public float updateWidth;
	private float nodeWidth;
	public float h = 20;
	public int r;
	public Atom value;
	public int outputs;
	public int inputs;
	public int outLockedID;
	public int inOrOut;
	public float mX, mY;
	public float xOffset, yOffset;
	public float outConnectionWidth;
	public float outConnectionGap;
	public float inConnectionGap;
	public float inConnectionWidth;
	public int counter = 0;

	int currentConnectionIDout;
	int currentConnectionIDin1;
	SGNode connectionOverNode;
	boolean connectionOver = false;
	// for interaction
	public boolean dragged = false;
	boolean press = false;
	boolean rollover;
	boolean locked;
	public boolean over;
	boolean highLight = false;
	public boolean connecting = false;
	public boolean selected = false;
	public boolean movingSelected = false;
	public static boolean moving = false;
	boolean scaleable = true;
	private boolean scaleWidthOnly = true;
	public boolean scaling = false;

	protected Color strokeColor = new Color(150, 150, 150);
	protected Color fillColor = new Color(250,250,250);
	Color borderColor = new Color(0xA5A5A5);
	Color highlightColor = new Color(205, 255, 240);
	Color cfillColor = new Color(0xFFFFFF);
	Color cborderColor = new Color(0xA5A5A5);
	Color cHighlightColor = new Color(0xB9FFEB);
	protected String titleString;
	boolean outputConnectionPressed;

	public OutputPort[] outputPorts;
	public InputPort[] inputPorts;

	int currentInputConnectionID;

	public Vector<Connection> outputConnections = new Vector<Connection>();
	public Vector<Connection> inputConnections = new Vector<Connection>();
	public Atom[] outputValues;
	public Atom[] inputValues;

	Point anchorGlobal = new Point();
	Point NMouseDragged;
	Point NMousePressedPos;
	private FXShape liner = new FXShape();
	public SGGroup subGroup = new SGGroup();
	protected SGText title = new SGText();
	public SGGroup group = new SGGroup();
	protected DropShadow shadow = new DropShadow();
	private InnerShadow iShadow = new InnerShadow();
	protected FXShape baseRect = new FXShape();
	protected SGTransform.Translate groupT = SGTransform.createTranslation(0,0, group);

	Node currentObject;

	public Connection activeConnection;
	String[] inputIDs;
	String[] outputIDs;

	public float xVelocity = 0;
	public float yVelocity = 0;
	float pMouseDraggedX;
	float pMouseDraggedY;
	protected float mouseDraggedX;
	protected float mouseDraggedY;
	public float mousePressedX;
	public float mousePressedY;
	float mouseMovedX;
	float mouseMovedY;
	Yaml yaml;
	boolean isInSelectionGroup = false;

	long oldTime;
	System nondeSys;
	Rectangle2D rect;
	
	public Node() {
	}

	public Node(String tTitle, float mx, float my, float tw, float th, int ins, int outs) {

		inputs = ins;
		outputs = outs;
		titleString = tTitle;
		title.setText(titleString);
		rollover = false;
		locked = false;
		over = false;
		x = mx;
		y = my;
		globalX = x;
		globalY = y;
		tempx = x;
		tempy = y;
		r = 8;
		w = tw;
		h = th;
		xOffset = 0;
		yOffset = 0;
		updateWidth = w;

		rect = title.getBounds();
		inputValues = new Atom[inputs];
		outputValues = new Atom[outputs];

		outputConnectionPressed = false;
		group.add(subGroup);
		group.add(createBox());
		group.add(createTitle());
		createInputPorts();
		createOutputPorts();
		group.add(liner);

		constructor();

	}

	public Node(String tTitle, float mx, float my, int ins, int outs) {
		inputs = ins;
		outputs = outs;
		titleString = tTitle;
		title.setText(titleString);
		rollover = false;
		locked = false;
		over = false;
		x = mx;
		y = my;
		r = 8;
		Rectangle2D rect;
		rect = title.getBounds();
		w = Tools.constrain((float) rect.getWidth() + 20, 20, 100000);
		updateWidth = w;
		inputValues = new Atom[inputs];
		outputValues = new Atom[outputs];

		outputConnectionPressed = false;
		group.add(subGroup);
		group.add(createBox());
		group.add(createTitle());
		createInputPorts();
		createOutputPorts();
		group.add(liner);

		constructor();

	}

	// Methods to build the node components
	void createInputPorts() {
		inConnectionWidth = 6;
		inConnectionGap = (nodeWidth / inputs    );
		inputPorts = new InputPort[inputs];

		if (inputs == 2) {
			inputPorts[0] = new InputPort(r + x, y, inConnectionWidth,thisNode(), 0);
			inputPorts[1] = new InputPort((x + w) - inConnectionWidth - r, y,inConnectionWidth, thisNode(), 1);
		}

		else {
			for (int i = 0; i < inputs; i++) {
				inputPorts[i] = new InputPort(i * (inConnectionWidth + inConnectionGap) + r + x, y,inConnectionWidth, thisNode(), i);
			}
		}
	}

	void createOutputPorts() {
		outputPorts = new OutputPort[outputs];
		outConnectionGap = (((w - 2 * r) / outputs) / 2);
		outConnectionWidth = 6;

		if (outputs == 2) {
			outputPorts[0] = new OutputPort(r + x, y + h, outConnectionWidth,thisNode(), 0);
			outputPorts[1] = new OutputPort((x + w) - outConnectionWidth - r, y	+ h, outConnectionWidth, thisNode(), 1);
		} else {
			for (int i = 0; i < outputs; i++) {
				outputPorts[i] = new OutputPort(i * (outConnectionWidth + outConnectionGap) + r + x, y + h, outConnectionWidth, thisNode(), i);
			}
		}
	}

	SGNode createTitle() {
		title.setFont(new Font("Helvetica", Font.BOLD, 14));
		title.setLocation(new Point2D.Float(x + r, y + 15));
		title.setAntialiasingHint(RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		title.setFillPaint(Color.black);
		updateWidth();
		return title;
	}

	SGNode createBox() {
		SGGroup group = new SGGroup();
		baseRect.setShape(new RoundRectangle2D.Float(0, 0, w, h, r, r));
		nodeWidth = w;
		baseRect.setTranslateX(x);
		baseRect.setTranslateY(y);
		baseRect.setFillPaint(fillColor);
		baseRect.setMode(SGShape.Mode.STROKE_FILL);
		baseRect.setDrawStroke(new BasicStroke(2.0f));
		baseRect.setDrawPaint(new Color(50, 50, 50));
		baseRect.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);
		
		// baseRect.setEffect(shadow); // this should be called via the
		// addShadow() method (too many shadows kills speed, this makes you set
		// it for objects which want it).
		baseRect.addMouseListener(new SGMouseAdapter() {
			Point pos;

			public void mouseEntered(MouseEvent e, SGNode n) {
				over = true;
				OverFlowMain.objectOver = true;
				nodeOver();
				if (isInSelectionGroup) {
					OverFlowMain.overSelection = true;
				}

			}

			public void mouseMoved(MouseEvent e, SGNode n) {
				pos = e.getPoint();
				mX = pos.x;
				mY = pos.y;
				mouseMovedX = e.getX();
				mouseMovedY = e.getY();
				if(mX < x + w && mX > (x + w - 5) && mY > y + h - 5 && mY < y + h) {
					scaling = true;
					}
				else {
					scaling = false;
				}

			}

			public void mouseExited(MouseEvent e, SGNode n) {
				over = false;
				OverFlowMain.objectOver = false;
				nodeExited();
				if (isInSelectionGroup) {
					OverFlowMain.overSelection = false;
				}

			}

			public void mousePressed(MouseEvent e, SGNode n) {
				pos = e.getPoint();
				nodePressed();
				mousePressedX = e.getX();
				mousePressedY = e.getY();
				if (OverFlowMain.editMode) {
					if (selected) {
						if (OverFlowMain.currentSelectedObjects.contains(thisNode())) {
							setSelected(false);
							OverFlowMain.currentSelectedObjects.remove(thisNode());
							movingSelected = false;
						}
					} else if (!selected && MainFrameInput.shiftDown) {
						if (!OverFlowMain.currentSelectedObjects.contains(thisNode())) {
							setSelected(true);
							OverFlowMain.currentSelectedObjects.addElement(thisNode());
						}
					}
				}
				oldTime = System.nanoTime();
			}

			public void mouseReleased(MouseEvent e, SGNode n) {
				dragged = false;
				moving = false;
				nodeReleased();
			}

			public void mouseDragged(MouseEvent e, SGNode n) {
				nodeDragged();
				dragged = true;
				mouseDraggedX = e.getX();
				mouseDraggedY = e.getY();
				if (mouseDraggedX - pMouseDraggedX < 100) {			//this stops the velocity values from initial being something like 100
					xVelocity = mouseDraggedX - pMouseDraggedX;
				}
				if (mouseDraggedY - pMouseDraggedY < 100) {			//this stops the velocity values from initial being something like 100
					yVelocity = mouseDraggedY - pMouseDraggedY;
				}
				if (OverFlowMain.editMode) {
					if (pos != null) {
						if(scaling){
							if(isScaleWidthOnly()){
								setSize(mouseDraggedX, mouseDraggedY);
							}
							else {
								h = mouseDraggedY - y;
								w = mouseDraggedX - x;
								baseRect.setShape(new RoundRectangle2D.Float(0, 0, w, h, r, r));
								System.out.println("scaling width + height");
							}
						}
						else if(!connectionOver && !scaling) {		//if not creating connections or scaling the object, move it
							NMouseDragged = e.getPoint();
							Point new_pos = e.getPoint();
							tx = new_pos.x - pos.x;
							ty = new_pos.y - pos.y;
							pos = new_pos;
							x += tx;
							y += ty;
							xOffset += tx;
							yOffset += ty;

							if (!movingSelected) { // update location
								moving = true;
								groupT.translateBy(tx, ty);
								setGlobal(tx, ty); // update the global location
													// of the Node for group
													// selection transform calls
							}
						}
					}
					
					updateConnectionLines();			//update connection line drawing after interacting with the object
				}
				pMouseDraggedX = e.getX();
				pMouseDraggedY = e.getY();
			}
		});
		group.add(baseRect);
		return group;
	}

	public void scaleObject(){
		if(isScaleWidthOnly()){
			w += xVelocity;
			System.out.println("scaling width");
		}
		else {
			w += xVelocity;
			y += yVelocity;
			System.out.println("scaling width + height");
		}
	}
	
	public void createShadow() {
		iShadow.setColor(new Color(255, 255, 255));
		iShadow.setRadius(5);
		shadow.setRadius(4);
		shadow.setOffsetX(2);
		shadow.setOffsetY(2);

	}

	protected void addShadow() {
		baseRect.setEffect(shadow);
	}

	void setShadowRadius(float radius) {
		shadow.setRadius(radius);
	}

	// These are dead methods to be called when extending the basic Node for
	// easy access to mouse events
	public void nodeDragged() {
	}

	public void nodeOver() {
	}

	public void nodeExited() {
	}

	public void nodePressed() {
	}

	public void nodeReleased() {
	}

	//

	public void update() { // updates inputValues to node values
	}

	public void update(int id, float value) { // updates inputValues to node values
	}
	
	protected void updateConnections() { // update the current values to the
											// current output connections
		for (Iterator<Connection> i = this.outputConnections.iterator(); i.hasNext();) {
			Connection con = (Connection) i.next();
			con.updateData();
		}
	}

	public void updateConnectionLines() { // update the connection lines drawing
											// locations
		for (Iterator<Connection> i = inputConnections.iterator(); i.hasNext();) {
			Connection con = (Connection) i.next();
			con.update();
		}
		for (Iterator<Connection> i = outputConnections.iterator(); i.hasNext();) {
			Connection con = (Connection) i.next();
			con.update();
		}
	}

	public void updateWidth() { // update node width based on text string bounds
		if (titleString.length() > 1) {
			Rectangle2D rect;
			rect = title.getBounds();
			w = Tools.constrain((float) rect.getWidth() + r * 2, 40, 1000);
			baseRect.setShape(new RoundRectangle2D.Float(0, 0, w, h, r, r));
		}
	}

	public void redrawNode() { // any scenario change redraws the scene graph
		groupT.translateBy(0, 0);
		// update graphics here

	}

	public void updateInputValue(int id, Atom inputValue) {
		inputValues[id] = inputValue;
		update();
	}

	public void setGlobalX(float gx) {
		globalX += gx;
	}

	public void setGlobalY(float gy) {
		globalY += gy;
	}

	public void setGlobal(float gx, float gy) {
		globalX += gx;
		globalY += gy;
	}

	public float getGlobalX() {
		return globalX;
	}

	public float getGlobalY() {
		return globalY;
	}

	public Atom getOutputValue(int id) {
		Atom returnValue = null;
		if (outputValues[id] != null) {
			returnValue = outputValues[id];
		} 
		return returnValue;
	}

	public void setBaseColor(Color c) {
		baseRect.setFillPaint(c);

	}

	public void addToGroup(SGGroup groupToAdd) {
		group.add(groupToAdd);
	}

	public Point2D returnLocation() {
		Point2D p = new Point2D.Double(x, y);
		return p;
	}

	public void setSelected(boolean s) {
		selected = s;
		if (!selected) {
			baseRect.setFillPaint(fillColor);
			baseRect.setDrawPaint(new Color(50, 50, 50));
			if (!OverFlowMain.selectedNodesContains(this)) {
				OverFlowMain.addSelectedNode(this);
			}
		} else {
			baseRect.setFillPaint(new Color(200, 250, 200, 150));
			baseRect.setDrawPaint(new Color(50, 50, 50));
			OverFlowMain.removeSelectedNode(this);
		}
	}

	public void deselect() {
		selected = false;
		OverFlowMain.removeSelectedNode(thisNode());
	}

	// Handle Connections
	public void setActiveConnection(Connection c) {
		activeConnection = c;
	}

	public Connection getActiveConnection(Connection c) {
		return activeConnection;
	}

	public void addNodeInputConnection(Connection c) {
		inputConnections.addElement(c);
	}

	public void removeInputConnection(Connection c) {
		inputConnections.remove(c);
	}

	public void addOutputConnection(Connection connection) {
		// TODO Auto-generated method stub
		outputConnections.addElement(connection);
	}

	public void removeOutputConnection(Connection connection) {
		// TODO Auto-generated method stub
		outputConnections.remove(connection);
	}

	public void removeOutputConnection(Object lastElement) {
		// TODO Auto-generated method stub
		outputConnections.remove(lastElement);
	}

	public int getOutputConnectionsSize() {
		return outputConnections.size();
	}

	public int getInputConnectionsSize() {
		return inputConnections.size();
	}

	public void killConnections() {
		for (Iterator<Connection> i = outputConnections.iterator(); i.hasNext();) {
			Connection con = (Connection) i.next();
			OverFlowMain.removeFromRootGroup(con.returnGroup());
		}
		for (Iterator<Connection> i = inputConnections.iterator(); i.hasNext();) {
			Connection con = (Connection) i.next();
			OverFlowMain.removeFromRootGroup(con.returnGroup());
		}
	}

	public void bringToFront() {
		OverFlowMain.removeFromRootGroup(this.returnGroup());
		OverFlowMain.addToRootGroup(this.returnGroup());
	}

	void constructor() {
	}

	public float getX() {

		return x;
	}

	public float getY() {
		return y;
	}

	public SGGroup returnG() {
		return group;
	}

	public SGNode returnGroup() {
		return groupT;
	}

	public Node thisNode() {
		return this;
	}
	
	public int numObjectMoving() {
		int numObjects = 0;
		for (Iterator<?> i = OverFlowMain.objects.iterator(); i.hasNext();) {
			if(Node.moving) {
				numObjects++;
			}
		}
		return numObjects;
	}
	
	public void setSize(float tx, float ty){
		w = Tools.constrain(mouseDraggedX - x, nodeWidth + r + 5, 1000);
		baseRect.setShape(new RoundRectangle2D.Float(0, 0, w, h, r, r));
		
		if(tx > x + nodeWidth){
			if (inputs == 2) {
				inputPorts[1].translateXBy(xVelocity);
			}
		
			else {
				for (int i = 0; i < inputs; i++) {
					inputPorts[i].translateXBy(i * (inConnectionWidth + inConnectionGap) + r + x);
				}
			}
		}
	}
	
	/**
	 * set output tool tip with output id and tool tip string
	 */
	protected void setOutputToolTip(int id, String s){
		outputPorts[id].setToolTipText(s);
	}
	
	/**
	 * set output tool tip fill paint with output id and color
	 */
	void setOutputToolTipFillPaint(int id, Color c){
		outputPorts[id].setToolTipFill(c);
	}
	
	/**
	 * set output tool tip draw paint with output id and color
	 */
	void setOutputToolTipDrawPaint(int id, Color c){
		outputPorts[id].setToolTipDraw(c);
	}

	/**
	 * set input tool tip with input id and tool tip string
	 */
	protected void setInputToolTip(int id, String s){
		inputPorts[id].setToolTipText(s);
	}
	
	/**
	 * set input tool tip fill paint with input id and color
	 */
	void setInputToolTipFillPaint(int id, Color c){
		inputPorts[id].setToolTipFill(c);
	}
	
	/**
	 * set input tool tip draw paint with input id and color
	 */
	void setInputToolTipDrawPaint(int id, Color c){
		inputPorts[id].setToolTipDraw(c);
	}
	

	
	
	public void setScaleWidthOnly(boolean scaleWidthOnly) {
		this.scaleWidthOnly = scaleWidthOnly;
	}

	public boolean isScaleWidthOnly() {
		return scaleWidthOnly;
	}
}



