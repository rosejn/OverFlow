package overFlow.main;


import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.sun.scenario.scenegraph.JSGPanel;
import com.sun.scenario.scenegraph.SGComponent;
import com.sun.scenario.scenegraph.SGGroup;
import com.sun.scenario.scenegraph.SGTransform;

public class NodeTextEntry extends JSGPanel{
	SGGroup group = new SGGroup();
    SGTransform.Translate groupT = SGTransform.createTranslation(0, 0, group);

	NodeTextEntry(float tx, float ty, SGGroup g) {
				this.setOpaque(false);

	            JPanel panel = new JPanel();
	            //panel.setBackground(new Color(255,0,0));
	            panel.setOpaque(false);
	            JTextField ta = new JTextField(12);
	       //     ta.setText("Hello Scenario!");
	       //     JScrollPane sp = new JScrollPane(ta);
	            panel.add(ta);

	       //     sp.setPreferredSize(new Dimension(200, 200));
	         //   panel.add(new JButton("test"));
	            

	            SGComponent comp = new SGComponent();
	            comp.setComponent(panel);
	            comp.setID("ScrollPane Wrapper");
	            setScene(SGTransform.createTranslation(0, 0, 
	            		SGTransform.createRotation(0,comp)));
	            group.add(comp); 
	            g.add(group);
	            groupT.setTranslateX(tx);
	            groupT.setTranslateY(ty);
	}
		
	}

