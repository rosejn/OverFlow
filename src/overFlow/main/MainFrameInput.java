package overFlow.main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import overFlow.Objects.Key;

import com.sun.scenario.scenegraph.SGGroup;
import com.sun.scenario.scenegraph.SGShape;
import com.sun.scenario.scenegraph.SGNode;
import com.sun.scenario.scenegraph.SGTransform;
import com.sun.scenario.scenegraph.event.SGKeyListener;
import com.sun.scenario.scenegraph.event.SGMouseAdapter;
import com.sun.scenario.scenegraph.fx.FXShape;

/*
 this class is used to control:
 - selection tool
 - menubar (not implemented yet)
 - manage main frame key and mouse events for object creation and selection tool
 */

public class MainFrameInput {
	public SGGroup selectionGroup = new SGGroup();
	public SGTransform.Translate sGroup = SGTransform.createTranslation(0, 0, selectionGroup);
	public SGGroup selectedGroup = new SGGroup();
	public SGGroup group = new SGGroup();
	public SGGroup menu = new SGGroup();

	public FXShape selectionBox = new FXShape();
	public FXShape menuBackground = new FXShape();
	public FXShape editModeButton = new FXShape();
	public FXShape snapModeButton = new FXShape();

	public SGShape window = new SGShape();

	public static boolean shiftDown = false;
	public boolean selecting = false;
	public static boolean creatingObject;

	public char[] newObjectName = new char[1];
	public static ObjectCreator2 objCreator;

	public static float windowX;
	public static float windowY;
	public static float pWindowX;
	public static float pWindowY;
	public static float yVel;
	public static float xVel;

	public static int modifier;
	public static long oldTime;

	//public SelectionTools selectionTool = new SelectionTools();
	public static SelectionManager selectionManager = new SelectionManager();
	public static KeyEvent key;

	public MainFrameInput() {

		OverFlowMain.grid.setVisible(true);

		group.add(selectedGroup);
		group.add(window);
		OverFlowMain.rootGroup.add(OverFlowMain.grid.returnLineGroup());
		OverFlowMain.rootGroup.add(group);

		objCreator = new ObjectCreator2(OverFlowMain.frameMouseX,OverFlowMain.frameMouseY);

		window.setShape(new Rectangle2D.Float(0, 0, 1000, 1000));
		window.setMode(SGShape.Mode.STROKE);
		window.setDrawStroke(new BasicStroke(0.0f));
		window.setDrawPaint(new Color(150, 150, 150, 0));
		window.addKeyListener(new KeyListener(this));
		window.addMouseListener(new SGMouseAdapter() { // Contains all of the
					// main window mouse
					// interaction, object
					// creation calls, etc
					Point2D pos;
					Point2D pPos;
					Point2D p;
					Point2D new_p;

					public void mouseMoved(MouseEvent e, SGNode n) {
						pos = e.getPoint();
						windowX = (float) pos.getX();
						windowY = (float) pos.getY();
					}

					public void mousePressed(MouseEvent e, SGNode n) {
						selectionManager.pressed(e);
						new_p = e.getPoint();

						if (OverFlowMain.editMode) {
							pPos = e.getPoint(); // set global variables
							windowX = (float) pPos.getX();
							windowY = (float) pPos.getY();

							if (creatingObject && !objCreator.over) { // if you click outside of a creator node, finish creating
								objCreator.setVisible(false);
								creatingObject = false;
							}

							if (System.nanoTime() - oldTime < 2.0000000E8) {
								if (!OverFlowMain.objectOver
										&& !OverFlowMain.overMenu) {
									creatingObject = true;
									objCreator.setLocation(
											MainFrameInput.windowX,
											MainFrameInput.windowY);
									objCreator.setVisible(true);
								}
							}
							oldTime = System.nanoTime();
							p = e.getPoint();
						}
					}

					public void mouseExited(MouseEvent e, SGNode n) {
					}

					public void mouseReleased(MouseEvent e, SGNode n) {
						selectionManager.released(e);
				//		selectionTool.active = false;
						OverFlowMain.frameDragged = false;
					}

					public void mouseDragged(MouseEvent e, SGNode n) {
						selectionManager.dragged(e);
						if (OverFlowMain.currentSelectedObjects.size() > 1) {
							sGroup.translateBy(xVel, yVel);

						}
						OverFlowMain.frameDragged = true;
						pos = e.getPoint();
						new_p = pos;

						// ///Global window variables
						windowX = (float) pos.getX();
						windowY = (float) pos.getY();

						if (windowX - pWindowX < 100) {
							xVel = windowX - pWindowX;
						} else {
							xVel = 0;
						}
						if (windowY - pWindowY < 100) {
							yVel = windowY - pWindowY;
						} else {
							yVel = 0;
						}
						// ///end globals

						// if in edit mode, not connecting, and not over an
						// object, or already using a selection box, create
						// selection box
					//	if (OverFlowMain.editMode && !OverFlowMain.connecting
					//			&& !OverFlowMain.objectOver
					//			|| selectionTool.active) {
							//selectionTool.active = true;
							//SelectionTools.draggUpdate(pPos, pos);

						//}

						pWindowX = windowX;
						pWindowY = windowY;

						p = pos;
					}

				});
	}



	static Rectangle2D.Double getSelectionRect(Point2D pPos, Point2D pos) { // this
		// handles
		// the
		// selection
		// box
		// so
		// that
		// when
		// you
		// draw
		// in
		// any
		// direction
		// in
		// draws
		// the
		// shape
		// correctly
		double pX = pPos.getX();
		double pY = pPos.getY();
		double x = pos.getX();
		double y = pos.getY();
		double xLeft = 0;
		double xRight = 0;
		double yTop = 0;
		double yBottom = 0;

		if (pX < x) {
			xLeft = pX;
			xRight = x;
		} else if (pX > x) {
			xLeft = x;
			xRight = pX;
		}
		if (pY < y) {
			yTop = pY;
			yBottom = y;
		} else {
			yTop = y;
			yBottom = pY;
		}
		Rectangle2D.Double rect = new Rectangle2D.Double(xLeft, yTop, xRight
				- xLeft, yBottom - yTop);
		return rect;
	}
	
	class KeyListener implements SGKeyListener {
		MainFrameInput mainWindow;
		KeyListener(MainFrameInput m){
			mainWindow = m;
		}
		public void keyPressed(java.awt.event.KeyEvent e, SGNode node){
			Key.updateKey(e);
			modifier = e.getModifiers();
			if(e.getKeyCode() == 16){
				shiftDown = true;
			}
			
			if(e.getKeyChar() == 'n'){		//create new object if you hit hot key 'n' for new
				if (!OverFlowMain.objectOver
						&& !OverFlowMain.overMenu) {
						creatingObject = true;
						objCreator.setLocation(mainWindow.windowX, mainWindow.windowY);
						objCreator.setVisible(true);
				}
			}
			
			if(e.getKeyChar() == 'm'){		//create new message if you hit hot key 'm' for message
				if (!OverFlowMain.objectOver
						&& !OverFlowMain.overMenu) {
						creatingObject = true;
						objCreator.setLocation(MainFrameInput.windowX, MainFrameInput.windowY);
						objCreator.setMessageBuilder(true);
						objCreator.setVisible(true);
				}
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
			selectionManager.keyTyped(e);
		}

		public void keyReleased(java.awt.event.KeyEvent e, SGNode node) {
		//	modifier = KeyEvent.SHIFT_MASK;
			shiftDown = false;
		}

		public void keyTyped(java.awt.event.KeyEvent e, SGNode node) {

		}
	};

}

class EditModeListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}