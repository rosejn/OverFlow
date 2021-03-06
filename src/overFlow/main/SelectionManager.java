package overFlow.main;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.Vector;

import com.sun.scenario.scenegraph.SGGroup;

public class SelectionManager {
	SelectionTools selectionTool;
	Point2D pos;
	Point2D pPos;
	private boolean selecting = false;

	private SGGroup selectionGroup = new SGGroup();

	public static Vector<Connection> currentSelectedConnections = new Vector<Connection>();
	public static Vector<Node> currentSelectedObjects = new Vector<Node>();

	public SelectionManager() {
		pos = new Point2D.Float(0, 0);
		pPos = pos;

	}

	public void keyTyped(KeyEvent e) {
		char letter = e.getKeyChar();

		// //////////Move selected objects with arrows, hold shift to move by 10
		// pixels /////////////////
		arrowKeyMover(e.getModifiers(), e.getKeyCode());
		// /////////////////////end of translation arrows////////////////////

		if (e.getKeyCode() == 8 && letter == 'b') {
			bringSelectedToFront();
		}

		if (e.getKeyCode() == 8 && letter == 'v') { // vertical alignment tools
			alignVertical();
		}

		if (e.getModifiers() == 8 && letter == 'h') { // horizontal alignment
														// tools
			alignHorizontal();
		}

		if (e.getModifiers() == 8 && letter == 'b') { // bring selected to front
			bringSelectedToFront();
		}

		if (e.getKeyCode() == 127) { // delete selected objects
			deleteSelected();
		}

		if (MainFrameInput.creatingObject) {
			if (e.getKeyCode() == 8) {

			}

			if (e.getKeyCode() == 10) {
				MainFrameInput.objCreator.setVisible(false);
				MainFrameInput.creatingObject = false;
				MainFrameInput.objCreator.createObject();
			}
		}
		if (OverFlowMain.getEditMode() && !MainFrameInput.creatingObject) {
			switch (letter) {
			case 'n':
				MainFrameInput.creatingObject = true;
				MainFrameInput.objCreator.setLocation(OverFlowMain.frameMouseX,
						OverFlowMain.frameMouseY);
				MainFrameInput.objCreator.setVisible(true);
				break;
			}

			if (e.getKeyCode() == 10) {
				MainFrameInput.objCreator.setVisible(false);
			}
		}
	}

	public static void bringSelectedToFront() {
		for (Iterator<Node> i = OverFlowMain.currentSelectedObjects.iterator(); i
				.hasNext();) {
			Node obj = (Node) i.next();
			obj.bringToFront();
			obj.redrawNode();
		}
	}

	void unselectAll() {

		for (Iterator<Connection> i = OverFlowMain.currentSelectedConnections.iterator(); i.hasNext();) {
			Connection con = (Connection) i.next();
			if(con.getSelected()){
				con.setSelected(false);
			}
		}

		for (Iterator<Node> i = OverFlowMain.currentSelectedObjects.iterator(); i
				.hasNext();) {
			Node obj = (Node) i.next();
			obj.setSelected(false);
			selectionGroup.remove(obj.returnGroup());
		}

		for (Iterator<Node> i = OverFlowMain.objects.iterator(); i.hasNext();) {
			Node obj = (Node) i.next();
			obj.movingSelected = false;
		}

		OverFlowMain.currentSelectedConnections.removeAllElements();
		OverFlowMain.currentSelectedObjects.removeAllElements();
	}

	void updateAllSelectedConnections() {
		for (int i = 0; i < OverFlowMain.currentSelectedObjects.size() - 1; i++) {
			Node obj = (Node) OverFlowMain.currentSelectedObjects.get(i);
			obj.updateConnectionLines();
		}
	}

	static void deleteSelected() {
		for (Iterator<Connection> i = OverFlowMain.currentSelectedConnections.iterator(); i
				.hasNext();) {
			Connection con = (Connection) i.next();
			con.kill();
		}
		for (Iterator<Node> i = OverFlowMain.currentSelectedObjects.iterator(); i
				.hasNext();) {
			Node obj = (Node) i.next();
			obj.killConnections();
			OverFlowMain.objects.remove(obj);
			OverFlowMain.rootGroup.remove(obj.returnGroup());
		}
		OverFlowMain.currentSelectedObjects.removeAllElements();
		OverFlowMain.currentSelectedConnections.removeAllElements();
	}

	public static void alignVertical() {
		Float averageVal = 0f;
		for (Iterator<Node> i = OverFlowMain.currentSelectedObjects.iterator(); i
				.hasNext();) {
//			Node n = (Node) i.next();
			// averageVal += n.getGlobalX() - n.groupT.getTranslateX();
		}
		averageVal /= OverFlowMain.currentSelectedObjects.size();
		for (Iterator<Node> i = OverFlowMain.currentSelectedObjects.iterator(); i
				.hasNext();) {
			Node n = (Node) i.next();
			n.groupT.setTranslateX(averageVal);
			n.updateConnectionLines();
		}
	}

	static void alignHorizontal() {

		float averageVal = 0f;
		for (Iterator<Node> i = OverFlowMain.currentSelectedObjects.iterator(); i.hasNext();) {
			Node obj = (Node) i.next();
			averageVal += obj.getGlobalY() - obj.groupT.getTranslateY();
		}
		averageVal /= OverFlowMain.currentSelectedObjects.size();
		for (Iterator<Node> i = OverFlowMain.currentSelectedObjects.iterator(); i.hasNext();) {
			Node obj = (Node) i.next();
			obj.groupT.setTranslateY(averageVal);
			obj.updateConnectionLines();
		}
	}

	void arrowKeyMover(int modifier, int keyId) {
		// System.out.println(modifier + "  " + keyId);
		if (keyId == 27) {
			System.exit(0);
		}
		for (Iterator<Node> i = OverFlowMain.currentSelectedObjects.iterator(); i.hasNext();) {
			Node n = (Node) i.next();
			if (keyId == 37) {
				if (modifier == 1) {
					n.groupT.translateBy(-10, 0);
					n.setGlobal(-10, 0);
					n.updateConnectionLines();
				}
				n.groupT.translateBy(-1, 0);
				n.updateConnectionLines();
			}
			if (keyId == 39) {
				if (modifier == 1) {
					n.groupT.translateBy(10, 0);
					n.setGlobal(10, 0);
					n.updateConnectionLines();
				}
				n.groupT.translateBy(1, 0);
				n.updateConnectionLines();
				n.setGlobal(1, 0);
			}
			if (keyId == 38) {
				if (modifier == 1) {
					n.groupT.translateBy(0, -10);
					n.setGlobal(0, -10);
					n.updateConnectionLines();
				}
				n.groupT.translateBy(0, -1);
				n.setGlobal(0, -1);
				n.updateConnectionLines();
			}
			if (keyId == 40) {
				if (modifier == 1) {
					n.groupT.translateBy(0, 10);
					n.setGlobal(0, 10);
					n.updateConnectionLines();
				}
				n.groupT.translateBy(0, 1);
				n.setGlobal(0, 1);
				n.updateConnectionLines();
			}
		}
	}

	public void setSelectionTool(SelectionTools tool) {
		selectionTool = tool;
	}

	public void pressed(MouseEvent e) {
		if (OverFlowMain.editMode) {
			pPos = e.getPoint();
		}
		if (!OverFlowMain.objectOver) {
			selectionTool.setSelecting(true); // only show if not over an object
			if (!OverFlowMain.connectionOver) {
				if(OverFlowMain.currentSelectedObjects.size() != 0 || OverFlowMain.currentSelectedConnections.size() != 0 ) {
					if(OverFlowMain.currentSelectedObjects.size() != 0 && OverFlowMain.currentSelectedConnections.size() != 0 ) {
						unselectAll(); // if click over nothing, unselect everything
					}
				}
			}
		}
	}

	public void released(MouseEvent e) {
		// TODO Auto-generated method stub
		selectionTool.setSelecting(false);
		if (OverFlowMain.currentSelectedObjects.size() > 1) {
			for (Iterator<?> i = OverFlowMain.currentSelectedObjects.iterator(); i.hasNext();) {
				Node obj = (Node) i.next();
				obj.movingSelected = false;
			}
		}
	}

	public void dragged(MouseEvent e) {
		pos = e.getPoint();
		for (Iterator<Node> i = OverFlowMain.currentSelectedObjects.iterator(); i
				.hasNext();) { // sets a boolean telling the nodes whether they
								// are being moved by a group translatino or a
								// local, individual translation
			Node tn = (Node) i.next();
			if (tn.selected && !selecting) {
				// tn.movingSelected = true;
				// tn.groupT.translateBy(MainFrameInput.xVel,
				// MainFrameInput.yVel);
				tn.setGlobal(MainFrameInput.xVel, MainFrameInput.yVel);
				tn.updateConnectionLines();
			} else {
				tn.movingSelected = false;
			}
		}

		if (OverFlowMain.currentSelectedObjects.size() > 1) {
			for (Iterator<?> i = OverFlowMain.currentSelectedObjects.iterator(); i
					.hasNext();) { // sets a boolean telling the nodes whether
									// they are being moved by a group
									// translatino or a local, individual
									// translation
				Node tn = (Node) i.next();
				if (tn.selected && !selecting) {
					tn.movingSelected = true;
					tn.groupT.translateBy(MainFrameInput.xVel,
							MainFrameInput.yVel);
					tn.setGlobal(MainFrameInput.xVel, MainFrameInput.yVel);
					tn.updateConnectionLines();
				} else {
					tn.movingSelected = false;
				}
			}
		}

		if (OverFlowMain.editMode && !OverFlowMain.connecting) {
			SelectionTools.draggUpdate(pPos, pos);

			Rectangle2D.Double rect = selectionTool.getSelectionRect(pPos, pos);
			selectionTool.selectionBox.setShape(new Rectangle2D.Double(rect
					.getX(), rect.getY(), rect.getWidth(), rect.getHeight()));

			for (Iterator<Node> i = OverFlowMain.objects.iterator(); i.hasNext();) {
				Node obj = (Node) i.next();

				if (obj.getGlobalX() > rect.getX()
						&& obj.getGlobalY() > rect.getY()
						&& obj.getGlobalX() < rect.getWidth() + rect.getX()
						&& obj.getGlobalY() < rect.getY() + rect.getHeight()
						|| obj.getGlobalX() + obj.w > rect.getX()
						&& obj.getGlobalY() + obj.h > rect.getY()
						&& obj.getGlobalX() + obj.w < rect.getWidth()
								+ rect.getX()
						&& obj.getGlobalY() + obj.h < rect.getY()
								+ rect.getHeight()) { // check if an object is
														// with in the selection
														// area
//					if (!OverFlowMain.currentSelectedObjects.contains(obj)) {
//						obj.setSelected(true);
//						obj.movingSelected = true;
//					}
//				} else {
//					if (OverFlowMain.currentSelectedObjects.contains(obj)) {
//						obj.setSelected(false);
//						obj.movingSelected = false;
//					}
				}
			}
		}
		pPos = pos;
	}

	public void setSelecting(boolean b) {
		selecting = b;
	}

}
