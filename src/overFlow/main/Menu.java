package overFlow.main;

import com.sun.scenario.effect.DropShadow;
import com.sun.scenario.scenegraph.SGGroup;
import com.sun.scenario.scenegraph.SGNode;
import com.sun.scenario.scenegraph.SGShape;
import com.sun.scenario.scenegraph.SGText;
import com.sun.scenario.scenegraph.event.SGMouseAdapter;
import com.sun.scenario.scenegraph.fx.FXShape;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.ImageIcon;

public class Menu {
	private static SGGroup mGroup = new SGGroup();
	private static SGGroup menu = new SGGroup();
	private static FXShape snapModeButton = new FXShape();
	private static FXShape editModeButton = new FXShape();
	private static FXShape menuBackground = new FXShape();

	
	int ICON_WIDTH = 30;
	int ICON_HEIGHT = 30;
	ImageIcon sliderIcon = new ImageIcon ("Slider.png");	
	ImageIcon toggleIcon = new ImageIcon ("Toggle.png");	
	ImageIcon stepSeqIcon = new ImageIcon ("StepSequencer.png");

	// All icons and app screen images are hard-coded along with their captions


	public Menu() {
		createMenu();
		createButtonMenu();

		// Image sliderIcon = getImage(getCodeBase(), "schumanator.svg");

	}

	public void createMenu() {
		menuBackground.setShape(new Rectangle2D.Float(0, 0, 1000, 20));
		menuBackground.setFillPaint(new Color(230, 230, 230, 170));
		menuBackground.setMode(SGShape.Mode.FILL);
		menuBackground.setDrawPaint(new Color(150, 150, 150));
		menuBackground.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);
		menuBackground.addMouseListener(new SGMouseAdapter() {
			public void mouseEntered(MouseEvent e, SGNode n) {
				if (OverFlowMain.editMode) {
					OverFlowMain.overMenu = true;
				}
			}

			public void mouseExited(MouseEvent e, SGNode n) {
				if (OverFlowMain.editMode) {
					OverFlowMain.overMenu = false;
				}
			}
		});

		modeButtons();

		menu.add(menuBackground);
		menu.add(mGroup);
		OverFlowMain.addToRootGroup(menu);
	}

	public void modeButtons() {
		DropShadow shadow = new DropShadow();
		shadow.setRadius(3);
		SGText editBtnText = new SGText();
		editBtnText.setFont(new Font("Helvetica", Font.PLAIN, 12));
		editBtnText.setLocation(new Point(34, 14));
		editBtnText.setAntialiasingHint(RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		editBtnText.setText("e");
		editBtnText.setFillPaint(Color.BLACK);

		SGText snapBtnText = new SGText();
		snapBtnText.setFont(new Font("Helvetica", Font.PLAIN, 12));
		snapBtnText.setLocation(new Point(9, 14));
		snapBtnText.setAntialiasingHint(RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		snapBtnText.setText("s");
		snapBtnText.setFillPaint(Color.BLACK);

		snapModeButton.setShape(new RoundRectangle2D.Float(0, 0, 14, 14, 6, 6));
		snapModeButton.setTranslateX(5);
		snapModeButton.setTranslateY(3);
		snapModeButton.setFillPaint(new Color(250, 250, 250));
		snapModeButton.setMode(SGShape.Mode.STROKE_FILL);
		snapModeButton.setDrawStroke(new BasicStroke(2.0f));
		snapModeButton.setDrawPaint(new Color(150, 150, 150));
		snapModeButton.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);
		snapModeButton.setEffect(shadow);
		snapModeButton.addMouseListener(new SGMouseAdapter() {
			public void mousePressed(MouseEvent e, SGNode n) {
				if (OverFlowMain.getSnapMode()) {
					OverFlowMain.setSnapMode(false);
					OverFlowMain.grid.setVisible(false);
				} else {
					OverFlowMain.setSnapMode(true);
					OverFlowMain.grid.setVisible(true);
				}
			}
		});

		editModeButton.setShape(new RoundRectangle2D.Float(0, 0, 14, 14, 6, 6));
		editModeButton.setTranslateX(30);
		editModeButton.setTranslateY(3);
		editModeButton.setFillPaint(new Color(250, 250, 250));
		editModeButton.setMode(SGShape.Mode.STROKE_FILL);
		editModeButton.setDrawStroke(new BasicStroke(2.0f));
		editModeButton.setDrawPaint(new Color(150, 150, 150));
		editModeButton.setAntialiasingHint(RenderingHints.VALUE_ANTIALIAS_ON);
		editModeButton.setEffect(shadow);
		editModeButton.addMouseListener(new SGMouseAdapter() {
			public void mousePressed(MouseEvent e, SGNode n) {
				if (OverFlowMain.getEditMode()) {
					OverFlowMain.setEditMode(false);
					Connection.setVisible(false);
				} else {
					OverFlowMain.setEditMode(true);
					Connection.setVisible(true);
				}
			}
		});
		mGroup.add(snapModeButton);
		mGroup.add(snapBtnText);
		mGroup.add(editModeButton);
		mGroup.add(editBtnText);
	}

	public static void setEditButtonMode(boolean b) {
		if (b) {
			System.out.println("edit mode");
			editModeButton.setFillPaint(new Color(150, 150, 150));
		} else {
			System.out.println("user mode");
			editModeButton.setFillPaint(new Color(250, 250, 250));
		}
	}

	public void setSnapButtonMode(boolean b) {
		if (b) {
			System.out.println("set Snap off");
			snapModeButton.setFillPaint(new Color(150, 150, 150));
		} else {
			System.out.println("set Snap on");
			snapModeButton.setFillPaint(new Color(250, 250, 250));
		}
	}

	public boolean getEditModeButton() {
		return OverFlowMain.editMode;
	}

	public void createButtonMenu() {

	}

}
