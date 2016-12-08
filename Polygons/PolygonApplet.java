import javax.swing.JApplet;
import java.awt.Graphics;
import java.util.Vector;
import java.awt.event.*;
import java.awt.*;
import java.text.*;
import java.util.Scanner;
import java.io.File;
import java.util.Vector;
import java.lang.Math;
import java.util.Random;
import javax.swing.event.*;
import javax.swing.*;

public class PolygonApplet extends JApplet {//implements Runnable{ //,ChangeListener {

	private PolygonCanvas screen;
	private int boardWidth = 700;
	private int boardHeight = 600;
    private boolean changeMade = true;
	private boolean editMode = true;
	private boolean hideGuards = false;
	
	// Variables declaration - do not modify
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton clearButton;
    private javax.swing.JButton clearGuardsButton;
    private javax.swing.JToggleButton connectButton;
    private javax.swing.JRadioButton delete;
    private javax.swing.JLabel generateLabel;
    private javax.swing.JButton generatePolyButton;
    private javax.swing.JRadioButton guard;
    private javax.swing.JToggleButton guardButton;
    private javax.swing.JRadioButton site;
    private javax.swing.JTextField holeVertices;
    private javax.swing.JLabel holeVerticesLabel;
    private javax.swing.JTextField holes;
    private javax.swing.JLabel holesLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton outVertex;
    private javax.swing.JTextField outerVertices;
    private javax.swing.JLabel outerVerticesLabel;
    //private java.awt.Canvas screen;
    private javax.swing.JComboBox selectCurrentHole;
    private javax.swing.JLabel selectHole;
    private javax.swing.JComboBox selectTriangulation;
    private javax.swing.JButton splitButton;
    private javax.swing.JLabel splitLabel;
    private javax.swing.JButton triangulationButton;
    private javax.swing.JTextPane triangulationPane;
    private javax.swing.JLabel viewTriangulation;
    // End of variables declaration
	
	//private javax.swing.GroupLayout layout;
	//private GroupLayout layout;
	private org.jdesktop.layout.GroupLayout layout;
	
	public void init()
	{	
	System.out.println("Initializing...");	
	screen = new PolygonCanvas(boardWidth,boardHeight);
	initComponents();
	setLayout(layout);
	
	}
	
	private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        //screen = new java.awt.Canvas();
        outVertex = new javax.swing.JRadioButton();
        site = new javax.swing.JRadioButton();
        guard = new javax.swing.JRadioButton();
        guardButton = new javax.swing.JToggleButton();
        connectButton = new javax.swing.JToggleButton();
        clearButton = new javax.swing.JButton();
        triangulationButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        triangulationPane = new javax.swing.JTextPane();
        selectTriangulation = new javax.swing.JComboBox();
        viewTriangulation = new javax.swing.JLabel();
        outerVertices = new javax.swing.JTextField();
        generateLabel = new javax.swing.JLabel();
        outerVerticesLabel = new javax.swing.JLabel();
        selectCurrentHole = new javax.swing.JComboBox();
        selectHole = new javax.swing.JLabel();
        holes = new javax.swing.JTextField();
        holesLabel = new javax.swing.JLabel();
        holeVertices = new javax.swing.JTextField();
        holeVerticesLabel = new javax.swing.JLabel();
        splitButton = new javax.swing.JButton();
        splitLabel = new javax.swing.JLabel();
        clearGuardsButton = new javax.swing.JButton();
        delete = new javax.swing.JRadioButton();
        generatePolyButton = new javax.swing.JButton();

        setBackground(new java.awt.Color(186, 183, 166));
        setMinimumSize(new java.awt.Dimension(800, 500));
        setPreferredSize(new java.awt.Dimension(800, 500));

        buttonGroup1.add(outVertex);
        outVertex.setText("Place Polygon Vertices");
        outVertex.setActionCommand("outVertex");
		outVertex.setSelected(true);
        outVertex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                outVertexActionPerformed(evt);
            }
        });

		buttonGroup1.add(site);
        site.setText("Place Site (for general point set)");
        site.setActionCommand("holeVertex");
        site.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                siteActionPerformed(evt);
            }
        });

        buttonGroup1.add(guard);
        guard.setText("Place Guards");
        guard.setActionCommand("guard");
        guard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardActionPerformed(evt);
            }
        });

        guardButton.setText("Hide Visibility");
        guardButton.setActionCommand("Hide Visibility");
        guardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardButtonActionPerformed(evt);
            }
        });

        connectButton.setText("Connect");
        connectButton.setActionCommand("Hide Visibility");
        connectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectButtonActionPerformed(evt);
            }
        });

        clearButton.setText("Clear All");
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        triangulationButton.setText("Find Triangulations");
        triangulationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                triangulationButtonActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(triangulationPane);

		ItemListener selectionListener = new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED)
				{				
				String select = e.getItem().toString();
				int curTri = Integer.valueOf(select)-1;
				screen.setCurTri(curTri);				
				}
			}	        
		};
		
        selectTriangulation.setModel(new javax.swing.DefaultComboBoxModel());
        selectTriangulation.addItemListener(selectionListener);
		
        viewTriangulation.setText("View Triangulation:");

        outerVertices.setText("3");
        outerVertices.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                outerVerticesActionPerformed(evt);
            }
        });

        generateLabel.setText("random polygon with...");

        outerVerticesLabel.setText("outer vertices");

        selectCurrentHole.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        selectCurrentHole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectCurrentHoleActionPerformed(evt);
            }
        });

        selectHole.setText("Select current hole to add to");

        holes.setText("0");
        holes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                holesActionPerformed(evt);
            }
        });

        holesLabel.setText("holes");

        holeVertices.setText("0");
        holeVertices.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                holeVerticesActionPerformed(evt);
            }
        });

        holeVerticesLabel.setText("hole vertices");

        splitButton.setText("Split");
        splitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                splitButtonActionPerformed(evt);
            }
        });

        splitLabel.setText("into two polygons");

        clearGuardsButton.setText("Clear Guards");
        clearGuardsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearGuardsButtonActionPerformed(evt);
            }
        });

        buttonGroup1.add(delete);
        delete.setText("Delete Vertices/Guards");
        delete.setActionCommand("guard");
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });

        generatePolyButton.setText("Generate");
        generatePolyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generatePolyButtonActionPerformed(evt);
            }
        });

        //layout = new javax.swing.GroupLayout(this.getContentPane());
		layout = new org.jdesktop.layout.GroupLayout(this.getContentPane());
		//layout = new GroupLayout(this.getContentPane());
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(screen, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 600, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, clearButton)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, clearGuardsButton)
                            .add(outVertex)
                            .add(site)
                            .add(guard)
                            .add(layout.createSequentialGroup()
                                .add(selectHole)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(selectCurrentHole, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(layout.createSequentialGroup()
                                .add(connectButton)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(guardButton))
                            .add(delete)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                                .add(generatePolyButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(generateLabel))
                            .add(layout.createSequentialGroup()
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                                    .add(org.jdesktop.layout.GroupLayout.LEADING, triangulationButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .add(viewTriangulation))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                    .add(selectTriangulation, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 52, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))))
                    .add(layout.createSequentialGroup()
                        .add(19, 19, 19)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(30, 30, 30)
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(layout.createSequentialGroup()
                                        .add(holes, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .add(18, 18, 18)
                                        .add(holesLabel))
                                    .add(layout.createSequentialGroup()
                                        .add(outerVertices, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .add(18, 18, 18)
                                        .add(outerVerticesLabel))
                                    .add(layout.createSequentialGroup()
                                        .add(holeVertices, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .add(18, 18, 18)
                                        .add(holeVerticesLabel))))
                            .add(layout.createSequentialGroup()
                                .add(splitButton)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(splitLabel)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(screen, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
            .add(layout.createSequentialGroup()
                .add(outVertex)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(site)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(guard)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(delete)
                .add(9, 9, 9)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(selectHole, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 14, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(selectCurrentHole, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(connectButton)
                    .add(guardButton))
                .add(30, 30, 30)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(triangulationButton))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(selectTriangulation, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(viewTriangulation))
                .add(37, 37, 37)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(generateLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 14, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(generatePolyButton))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(outerVertices, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(outerVerticesLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(holes, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(holesLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(holeVertices, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(holeVerticesLabel))
                .add(18, 18, 18)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(splitButton)
                    .add(splitLabel))
                .add(38, 38, 38)
                .add(clearGuardsButton)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(clearButton)
                .addContainerGap())
        );
    }// </editor-fold>         

    private void outVertexActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		screen.setOut();
}

    private void siteActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		screen.setSite();		
}

    private void guardActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		screen.setGuard();
		if(!connectButton.isSelected())
			{
			connectButton.setSelected(true);
			connectButtonActionPerformed(new ActionEvent(connectButton, 1, "connect"));
			}
    }

    private void triangulationButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		if(site.isSelected()) 
			{
			int totalTris = screen.triangulateSites();
			for(int i=0; i<totalTris; i++)
				{				
				selectTriangulation.addItem(Integer.toString(i+1));
				}
			}
		else if(connectButton.isSelected()) screen.triangulatePolygon();
    }

    private void guardButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		hideGuards = !hideGuards;
		if(!hideGuards)
			{			
			screen.setHideGuards(false);
			screen.showVisibility();
			}
		else
			{
			screen.setHideGuards(true);
			screen.removeVisibility();
			}
    }

    private void connectButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		if(editMode)
			{
			screen.createShapeFromVector();
			}		
		else
			{
			screen.removePolygon();
			}
		editMode = !editMode;
		
    }

    private void outerVerticesActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
}

    private void holesActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
}

    private void holeVerticesActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
}

    private void splitButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
}

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		screen.clearAll();
		connectButton.setSelected(false);
		guardButton.setSelected(false);
		editMode = true;
		hideGuards = false;
		screen.setHideGuards(false);
		selectTriangulation.removeAllItems();
    }

    private void selectCurrentHoleActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }
	
	private void clearGuardsButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		screen.clearGuards();
		guardButton.setSelected(false);		
		hideGuards = false;
		screen.setHideGuards(false);
}

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
}

    private void generatePolyButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
}

	public void start()
	{
	System.out.println("Starting...");
	}
	
	public void stop()
	{
	System.out.println("Stopping...");
	}	
	
	
}

    