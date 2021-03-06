package overFlow.main;

import java.awt.Cursor;
import java.awt.geom.*;
import java.util.Vector;

import overFlow.main.Connection;
import overFlow.main.Node;

import com.sun.scenario.scenegraph.JSGPanel;
import com.sun.scenario.scenegraph.SGGroup;
import com.sun.scenario.scenegraph.SGNode;
import com.sun.scenario.scenegraph.SGText;
import com.sun.scenario.scenegraph.fx.FXShape;

final public class OverFlowMain {
	public static JSGPanel gpanel = new JSGPanel();

	public static Node currentInputObject;
	public static Connection currentConnection;
	public static InputPort currentInputIO;

	public static SGText editModeLabel = new SGText();
	public static boolean overMenu = false;
	public static boolean connecting = false;
	public static boolean connectionOver = false;
	public static boolean objectOver = false;
	public static boolean inputConnectionOver = false;

	public static boolean overSelection = false; // for moving group of selected
													// objects

	public static boolean frameDragged;
	public static FXShape currentInputShape = new FXShape();
	public static FXShape currentOutputShape = new FXShape();

	final public static SGGroup rootGroup = new SGGroup(); // application root
															// scenegraph group

	public Point2D.Double inputPoint = new Point2D.Double();
	public Point2D.Double outputPoint = new Point2D.Double();

	public static int frameMouseX; // global mouse location
	public static int frameMouseY; // global mouse location

	public static Vector<Connection> currentSelectedConnections = new Vector<Connection>();
	public static Vector<Node> currentSelectedObjects = new Vector<Node>();
	public static Vector<Connection> connections = new Vector<Connection>();
	public static Vector<Node> objects = new Vector<Node>();

	public static System sys;

	public static boolean editMode; // global edit mode
	public static boolean snapMode = true;

	public static Grid grid;
	public static Menu menu;


	public static void main(String args[]) {
		grid = new Grid(20);
		new GUI().setVisible(true);
		setEditMode(true);
		menu = new Menu();
	}

	public static boolean getEditMode() {
		return editMode;
	}

	public static void setEditMode(boolean b) {
		editMode = b;
		Menu.setEditButtonMode(b);

	}

	public static boolean getSnapMode() {
		return snapMode;
	}

	public static void setSnapMode(boolean b) {
		// TODO Auto-generated method stub
		snapMode = b;
		menu.setSnapButtonMode(b);
	}

	// add and remove from the main Connection list
	static void addConnection(Connection c) {
		connections.addElement(c);
	}

	static void removeConnection(Connection c) {
		connections.removeElement(c);
	}

	// get and set connection methods
	public boolean getConnecting() {
		return connecting;
	}

	public static void setConnecting(boolean b) {
		connecting = b;
	}

	public static void addSelectedConnection(Connection c) {
		currentSelectedConnections.addElement(c);
	}

	public static void removeSelectedConnection(Connection c) {
		currentSelectedConnections.remove(c);
	}

	public static void addSelectedNode(Node n) {
		currentSelectedObjects.addElement(n);
	}

	public static boolean selectedNodesContains(Node n) {
		boolean returnBoolean = currentSelectedObjects.contains(n);
		return returnBoolean;
	}

	public static void removeSelectedNode(Node n) {
		// TODO Auto-generated method stub
		currentSelectedObjects.remove(n);
	}

	// get set rootGroup methods
	public static void addToRootGroup(SGNode g) {
		rootGroup.add(g);
	}

	public static void removeFromRootGroup(SGNode sgNode) {
		// TODO Auto-generated method stub
		rootGroup.remove(sgNode);
	}

	public static void removeConnection(Object lastElement) {
		// TODO Auto-generated method stub
		connections.remove(lastElement);
	}

}
