import javax.swing.JApplet;
import java.awt.Graphics;
import java.util.Vector;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.text.*;
import java.util.Scanner;
import java.io.File;
import java.util.Vector;
import java.lang.Math;
import java.util.Random;
import javax.swing.*;
import javax.swing.event.*;

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
    private javax.swing.JRadioButton holeVertex;
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
	
	private javax.swing.GroupLayout layout;
	
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
        holeVertex = new javax.swing.JRadioButton();
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
        outVertex.setText("Place Outer Vertices");
        outVertex.setActionCommand("outVertex");
		outVertex.setSelected(true);
        outVertex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                outVertexActionPerformed(evt);
            }
        });

        buttonGroup1.add(holeVertex);
        holeVertex.setText("Place Hole Vertices");
        holeVertex.setActionCommand("holeVertex");
        holeVertex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                holeVertexActionPerformed(evt);
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

        selectTriangulation.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));

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

        layout = new javax.swing.GroupLayout(this.getContentPane());
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(screen, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(clearButton, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(clearGuardsButton, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(outVertex)
                            .addComponent(holeVertex)
                            .addComponent(guard)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(selectHole)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(selectCurrentHole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(connectButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(guardButton))
                            .addComponent(delete)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(generatePolyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(generateLabel))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(triangulationButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(viewTriangulation))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(selectTriangulation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(holes, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(holesLabel))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(outerVertices, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(outerVerticesLabel))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(holeVertices, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(holeVerticesLabel))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(splitButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(splitLabel)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(screen, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(outVertex)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(holeVertex)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(guard)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(delete)
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectHole, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectCurrentHole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(connectButton)
                    .addComponent(guardButton))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(triangulationButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectTriangulation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(viewTriangulation))
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(generateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(generatePolyButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(outerVertices, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(outerVerticesLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(holes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(holesLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(holeVertices, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(holeVerticesLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(splitButton)
                    .addComponent(splitLabel))
                .addGap(38, 38, 38)
                .addComponent(clearGuardsButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clearButton)
                .addContainerGap())
        );
    }// </editor-fold>

    private void outVertexActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		screen.setOut();
}

    private void holeVertexActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		screen.setHole();
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
		screen.triangulate();
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

    