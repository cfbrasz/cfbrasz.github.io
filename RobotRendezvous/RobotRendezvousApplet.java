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
import java.net.URL;

public class RobotRendezvousApplet extends JApplet implements Runnable,ChangeListener{

	RobotRendezvousCanvas canvas1;
	int boardWidth = 600;
	int boardHeight = 500;
	private Thread animatorThread;
	private int multiplier = 20;
	int elapsedTime = 0; //keeps track of total time elapsed in game (in ms)
	boolean started;
	boolean stop;
	boolean reset = false;
	boolean generate = false;
	boolean updatedt = false;
	double newv;
	int freqMax = 30; // the slowest the dragging robots will go, 1 every 50 frames
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
     javax.swing.ButtonGroup buttonGroup1;
     javax.swing.ButtonGroup buttonGroup2;
     javax.swing.ButtonGroup buttonGroup3;
     javax.swing.ButtonGroup buttonGroup4;
     javax.swing.JCheckBox ccCheckBox;
     javax.swing.JRadioButton circumcenterSchemeRadioButton;
     javax.swing.JButton clearButton;
     javax.swing.JCheckBox cmCheckBox;
     javax.swing.JCheckBox cmPolyCheckBox;
     javax.swing.JRadioButton convexRadioButton;
     javax.swing.JRadioButton dragPlaceRobotRadioButton;
     javax.swing.JSlider dragRobotFreqSlider;
     javax.swing.JRadioButton eulerRadioButton;
     javax.swing.JButton generateButton;
     javax.swing.JRadioButton implicitRadioButton;
     javax.swing.JLabel jLabel1;
     javax.swing.JLabel jLabel2;
     javax.swing.JLabel jLabel3;
     javax.swing.JLabel jLabel4;
     javax.swing.JLabel jLabel5;
     javax.swing.JLabel jLabel6;
     javax.swing.JLabel jLabel7;
     javax.swing.JLabel jLabel8;
     javax.swing.JRadioButton linearSchemeRadioButton;
     javax.swing.JCheckBox mergeRobotsCheckBox;
     javax.swing.JRadioButton mmSchemeRadioButton;
     javax.swing.JToggleButton pauseToggleButton;
     javax.swing.JRadioButton placeRobotRadioButton;
     javax.swing.JComboBox presetsComboBox;
     javax.swing.JRadioButton randomRadioButton;
     javax.swing.JTextField randomRobotNumberTextField;
     javax.swing.JCheckBox recordShapeCheckBox;
     javax.swing.JRadioButton removeRobotRadioButton;
     javax.swing.JButton resetButton;
     javax.swing.JRadioButton rungekuttaRadioButton;
     javax.swing.JSlider speedSlider;
     javax.swing.JButton starConvertButton;
     javax.swing.JCheckBox starLinesCheckBox;
     javax.swing.JRadioButton starRadioButton;
     javax.swing.JButton startButton;
     javax.swing.JLabel stepsPerFrameLabel;
     javax.swing.JSlider stepsPerFrameSlider;
     javax.swing.JLabel timeLabel;
     javax.swing.JLabel timestepLabel;
     javax.swing.JCheckBox tracePathsCheckBox;
     javax.swing.JCheckBox velVectorsCheckBox;
    // End of variables declaration//GEN-END:variables
	
	private org.jdesktop.layout.GroupLayout layout;
	
	public void init()
	{	
	System.out.println("Initializing...");	
	canvas1 = new RobotRendezvousCanvas(boardWidth,boardHeight,this);
	initComponents();
	setLayout(layout);
	
	}
	
	private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        clearButton = new javax.swing.JButton();
        startButton = new javax.swing.JButton();
        pauseToggleButton = new javax.swing.JToggleButton();
        resetButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        timeLabel = new javax.swing.JLabel();
        generateButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        randomRobotNumberTextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        starRadioButton = new javax.swing.JRadioButton();
        convexRadioButton = new javax.swing.JRadioButton();
        randomRadioButton = new javax.swing.JRadioButton();
        tracePathsCheckBox = new javax.swing.JCheckBox();
        recordShapeCheckBox = new javax.swing.JCheckBox();
        speedSlider = new javax.swing.JSlider();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        placeRobotRadioButton = new javax.swing.JRadioButton();
        removeRobotRadioButton = new javax.swing.JRadioButton();
        dragPlaceRobotRadioButton = new javax.swing.JRadioButton();
        starConvertButton = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        linearSchemeRadioButton = new javax.swing.JRadioButton();
        mmSchemeRadioButton = new javax.swing.JRadioButton();
        jLabel8 = new javax.swing.JLabel();
        eulerRadioButton = new javax.swing.JRadioButton();
        implicitRadioButton = new javax.swing.JRadioButton();
        rungekuttaRadioButton = new javax.swing.JRadioButton();
        dragRobotFreqSlider = new javax.swing.JSlider();
        timestepLabel = new javax.swing.JLabel();
        circumcenterSchemeRadioButton = new javax.swing.JRadioButton();
        stepsPerFrameLabel = new javax.swing.JLabel();
        stepsPerFrameSlider = new javax.swing.JSlider();
        mergeRobotsCheckBox = new javax.swing.JCheckBox();
        presetsComboBox = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        cmCheckBox = new javax.swing.JCheckBox();
        cmPolyCheckBox = new javax.swing.JCheckBox();
        velVectorsCheckBox = new javax.swing.JCheckBox();
        ccCheckBox = new javax.swing.JCheckBox();
        starLinesCheckBox = new javax.swing.JCheckBox();

        setPreferredSize(new java.awt.Dimension(750, 660));
		
		speedSlider.addChangeListener(this);		
		speedSlider.setName("speedSlider");		
        speedSlider.setValue(10);
        speedSlider.setMinimum(1);
		
		dragRobotFreqSlider.addChangeListener(this);		
		dragRobotFreqSlider.setName("dragRobotFreqSlider");		
        dragRobotFreqSlider.setValue(25);
        dragRobotFreqSlider.setMaximum(freqMax);

        clearButton.setText("Remove all robots");
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
				canvas1.repaint();
            }
        });

        startButton.setText("Start rendezvous");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
				canvas1.repaint();
            }
        });

        pauseToggleButton.setText("Pause");
        pauseToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pauseToggleButtonActionPerformed(evt);
				canvas1.repaint();
            }
        });

        resetButton.setText("Reset");
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetButtonActionPerformed(evt);
				canvas1.repaint();
            }
        });

        jLabel1.setText("Time elapsed:");

        timeLabel.setText("00:00:00");

        generateButton.setText("Generate n random robots");
        generateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateButtonActionPerformed(evt);
				canvas1.repaint();
            }
        });

        jLabel3.setText("where n =");

        randomRobotNumberTextField.setText("50");

        jLabel4.setText("and the starting formation is");

        buttonGroup1.add(starRadioButton);
        starRadioButton.setSelected(true);
        starRadioButton.setText("star formation");
        starRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                starRadioButtonActionPerformed(evt);
            }
        });

        buttonGroup1.add(convexRadioButton);
        convexRadioButton.setText("a convex polygon");
        convexRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                convexRadioButtonActionPerformed(evt);
            }
        });

        buttonGroup1.add(randomRadioButton);
        randomRadioButton.setText("random");
        randomRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                randomRadioButtonActionPerformed(evt);
            }
        });

        tracePathsCheckBox.setSelected(true);
        tracePathsCheckBox.setText("Trace paths");
        tracePathsCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tracePathsCheckBoxActionPerformed(evt);
				canvas1.repaint();
            }
        });

        recordShapeCheckBox.setSelected(true);
        recordShapeCheckBox.setText("Record shape");
        recordShapeCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recordShapeCheckBoxActionPerformed(evt);
				canvas1.repaint();
            }
        });

        jLabel5.setText("Timestep size");

        jLabel2.setText("Mouse click action:");

        buttonGroup2.add(placeRobotRadioButton);
        placeRobotRadioButton.setText("Place robot");

        buttonGroup2.add(removeRobotRadioButton);
        removeRobotRadioButton.setText("Remove robot");

        buttonGroup2.add(dragPlaceRobotRadioButton);
        dragPlaceRobotRadioButton.setText("Drag to create robots with freq.");
        dragPlaceRobotRadioButton.setSelected(true);


        starConvertButton.setText("Convert to star ordering");
        starConvertButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                starConvertButtonActionPerformed(evt);
            }
        });

        jLabel7.setText("Polygon shortening scheme:");

        buttonGroup3.add(linearSchemeRadioButton);
        linearSchemeRadioButton.setSelected(true);
        linearSchemeRadioButton.setText("Linear");
        linearSchemeRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                linearSchemeRadioButtonActionPerformed(evt);
            }
        });

        buttonGroup3.add(mmSchemeRadioButton);
        mmSchemeRadioButton.setText("Menger-Melnikov curvature");
        mmSchemeRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mmSchemeRadioButtonActionPerformed(evt);
            }
        });

        jLabel8.setText("Integrator type:");

        buttonGroup4.add(eulerRadioButton);
        eulerRadioButton.setText("Forward Euler");
        eulerRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eulerRadioButtonActionPerformed(evt);
            }
        });

        buttonGroup4.add(implicitRadioButton);
        implicitRadioButton.setSelected(true);
        implicitRadioButton.setText("Implicit");
        implicitRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                implicitRadioButtonActionPerformed(evt);
            }
        });
		
        buttonGroup4.add(rungekuttaRadioButton);
        rungekuttaRadioButton.setText("Runge-Kutta");
        rungekuttaRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rungekuttaRadioButtonActionPerformed(evt);
            }
        });

        timestepLabel.setText("0.05");

        buttonGroup3.add(circumcenterSchemeRadioButton);
        circumcenterSchemeRadioButton.setText("Circumcenter");
        circumcenterSchemeRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                circumcenterSchemeRadioButtonActionPerformed(evt);
            }
        });

        stepsPerFrameLabel.setText("Steps per frame: 2");

        stepsPerFrameSlider.setMaximum(100);
        stepsPerFrameSlider.setMinimum(1);
        stepsPerFrameSlider.setValue(2);
		stepsPerFrameSlider.addChangeListener(this);		
		stepsPerFrameSlider.setName("stepsPerFrameSlider");		

        mergeRobotsCheckBox.setText("Merge close robots");
        mergeRobotsCheckBox.setEnabled(false);
		
        jLabel6.setText("Preset Scenarios:");
		
		presetsComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Choose here","Spiral", "Area increase", "Snake" }));
        		
		
		ItemListener presetSelectionListener = new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED)
				{				
				String preset = e.getItem().toString();
				//loadPreset = true;
				stopRun();
				canvas1.loadPreset(preset);	
				}
			}	        
		};
		
        presetsComboBox.addItemListener(presetSelectionListener);

        cmCheckBox.setSelected(true);
        cmCheckBox.setText("Show center of mass of robots");
        cmCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmCheckBoxActionPerformed(evt);
            }
        });

        cmPolyCheckBox.setSelected(true);
        cmPolyCheckBox.setText("Show center of mass of polygon");
        cmPolyCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmPolyCheckBoxActionPerformed(evt);
            }
        });

        //velVectorsCheckBox.setSelected(true);
        velVectorsCheckBox.setText("Show velocity vectors");
        velVectorsCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                velVectorsCheckBoxActionPerformed(evt);
            }
        });

        ccCheckBox.setText("Show circumcircles used for MM scheme");
        ccCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ccCheckBoxActionPerformed(evt);
            }
        });

        //starLinesCheckBox.setSelected(true);
        starLinesCheckBox.setText("Show radial lines");
        starLinesCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                starLinesCheckBoxActionPerformed(evt);
            }
        });

        //org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
		layout = new org.jdesktop.layout.GroupLayout(this.getContentPane());
        //this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(canvas1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 600, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(jLabel5)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(timestepLabel))
                            .add(clearButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .add(startButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .add(layout.createSequentialGroup()
                                .add(pauseToggleButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 73, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(resetButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE))
                            .add(layout.createSequentialGroup()
                                .add(jLabel1)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 90, Short.MAX_VALUE)
                                .add(timeLabel))
                            .add(speedSlider, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(dragPlaceRobotRadioButton)
                            .add(layout.createSequentialGroup()
                                .add(convexRadioButton)
                                .add(48, 48, 48))
                            .add(randomRadioButton)
                            .add(layout.createSequentialGroup()
                                .add(starRadioButton)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(starLinesCheckBox))
                            .add(jLabel4)
                            .add(layout.createSequentialGroup()
                                .add(jLabel3)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(randomRobotNumberTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 26, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(generateButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .add(starConvertButton)
                            .add(jLabel7)
                            .add(dragRobotFreqSlider, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(circumcenterSchemeRadioButton)
                            .add(linearSchemeRadioButton)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                                .add(placeRobotRadioButton)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 28, Short.MAX_VALUE)
                                .add(removeRobotRadioButton))
                            .add(layout.createSequentialGroup()
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jLabel2)
                                    .add(stepsPerFrameLabel))
                                .add(10, 10, 10)
                                .add(stepsPerFrameSlider, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                .add(mergeRobotsCheckBox)
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jLabel8)
                                    .add(eulerRadioButton)
                                    .add(implicitRadioButton)
                                    .add(rungekuttaRadioButton)
                                    .add(mmSchemeRadioButton)))
                            .add(layout.createSequentialGroup()
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(recordShapeCheckBox)
                                    .add(tracePathsCheckBox))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 18, Short.MAX_VALUE)
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                                    .add(presetsComboBox, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .add(jLabel6, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .add(layout.createSequentialGroup()
                        .add(cmCheckBox)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(cmPolyCheckBox)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(velVectorsCheckBox)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(ccCheckBox)))
                .add(97, 97, 97))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(canvas1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 700, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(layout.createSequentialGroup()
                        .add(clearButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(startButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(pauseToggleButton)
                            .add(resetButton))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel1)
                            .add(timeLabel))
                        .add(13, 13, 13)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel5)
                            .add(timestepLabel))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(speedSlider, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(stepsPerFrameLabel)
                                .add(9, 9, 9)
                                .add(jLabel2))
                            .add(stepsPerFrameSlider, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(removeRobotRadioButton)
                            .add(placeRobotRadioButton))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(dragPlaceRobotRadioButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(dragRobotFreqSlider, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(3, 3, 3)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(recordShapeCheckBox)
                            .add(jLabel6))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(tracePathsCheckBox)
                            .add(presetsComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(generateButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel3)
                            .add(randomRobotNumberTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel4)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(starRadioButton)
                            .add(starLinesCheckBox))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(convexRadioButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(randomRadioButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(starConvertButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel7)
                        .add(5, 5, 5)
                        .add(circumcenterSchemeRadioButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(linearSchemeRadioButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(mmSchemeRadioButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(mergeRobotsCheckBox)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel8)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(eulerRadioButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(implicitRadioButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(rungekuttaRadioButton)))
                .add(2, 2, 2)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(cmCheckBox)
                    .add(cmPolyCheckBox)
                    .add(velVectorsCheckBox)
                    .add(ccCheckBox))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

	public void stateChanged(ChangeEvent e) {
	
        JSlider source = (JSlider)e.getSource();
		
            int value = source.getValue();
			String name = source.getName();
			if (name.equals("speedSlider"))
				{
				//Robot.globalSpeedModifier = (double)value/20.0 + 0.01; //0 to 5, starts at 1
				newv= 0.05*(double)value*value*value/1000.0; //0 to 50 , starts at 0.05
				//if(newv==0) newv = 0.001;
				updatedt = true;
				
				Double val = newv;
				String str = val.toString();
				if(str.length() > 5) str = str.substring(0,6);
				
				timestepLabel.setText(str);
				//canvas1.dt = 0.05*(double)value*value*value/1000.0 + 0.001; //0 to 50 , starts at 
				//canvas1.updatedt();
				}
			else if(name.equals("dragRobotFreqSlider"))
				{
				canvas1.slowFactor = freqMax+1-value;
				}
			else if(name.equals("stepsPerFrameSlider"))
				{
				canvas1.stepsPerFrame = value;
				stepsPerFrameLabel.setText("Steps per frame: "+value);				
				}
			//System.out.println(canvas1.dt);
				
		canvas1.repaint();
       
    }
	
	public void run()
	{
	started = true;
	stop = false;
	canvas1.addEllipse();
	
	while (Thread.currentThread() == animatorThread && !pauseToggleButton.isSelected() && !stop)
		{
			if(updatedt) 
				{
				canvas1.dt = newv; //0 to 50 , starts at 
				canvas1.updatedt();
				updatedt = false;
				}
			
			
			
			canvas1.moveRobots();		
			elapsedTime += multiplier;
			timer(multiplier);
			Integer sec = elapsedTime/1000;
			Integer ms = elapsedTime%1000;
			Integer min = sec/60;
			String minutes = min.toString();
			String millis = ms.toString();
			while(millis.length() < 3) millis = "0" + millis;
			sec = sec%60;
			String seconds = sec.toString();
			if(seconds.length() == 1) seconds = "0"+seconds;
			if(minutes.length() == 1) minutes = "0"+minutes;
			timeLabel.setText(minutes + ":" + seconds + "." + millis);
			
			if(elapsedTime%1000 == 0) canvas1.addEllipse();
			
			if(reset) resetAction();
			if(generate) generateAction();
			
			canvas1.repaint();
		}
	}
    
	    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		if(started && !pauseToggleButton.isSelected())reset = true;
		else resetAction();
}

void resetAction()
{
		stopRun();
		canvas1.reset();
		reset = false;

}

public void stopRun()
	{
	if(started)
		{
		if(pauseToggleButton.isSelected()) 
			{	
			pauseToggleButton.setSelected(false);
			}
		stop = true;
		started = false;
		eulerRadioButton.setEnabled(true);
		if(!circumcenterSchemeRadioButton.isSelected())
			{
			implicitRadioButton.setEnabled(true);
			rungekuttaRadioButton.setEnabled(true);
			}
		if(mmSchemeRadioButton.isSelected() && !implicitRadioButton.isSelected()) mergeRobotsCheckBox.setEnabled(true);
		linearSchemeRadioButton.setEnabled(true);
		mmSchemeRadioButton.setEnabled(true);
		circumcenterSchemeRadioButton.setEnabled(true);
		}
	elapsedTime = 0;
	timeLabel.setText("00:00.000");
	}

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		stopRun();
		canvas1.removeAllRobots();
    }
	
    private void circumcenterSchemeRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
			implicitRadioButton.setEnabled(false);
			rungekuttaRadioButton.setEnabled(false);
			eulerRadioButton.setSelected(true);
			mergeRobotsCheckBox.setEnabled(false);
			mergeRobotsCheckBox.setSelected(false);
			canvas1.calculateCircumcenter();
    }

    private void linearSchemeRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
			implicitRadioButton.setEnabled(true);
			rungekuttaRadioButton.setEnabled(true);
			RobotRendezvousCanvas.circumcenter = null;
			mergeRobotsCheckBox.setEnabled(false);
			mergeRobotsCheckBox.setSelected(false);
			canvas1.repaint();
    }

    private void mmSchemeRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
			implicitRadioButton.setEnabled(true);
			rungekuttaRadioButton.setEnabled(true);
			RobotRendezvousCanvas.circumcenter = null;
			if(!implicitRadioButton.isSelected()) mergeRobotsCheckBox.setEnabled(true);
			canvas1.repaint();
    }

    private void eulerRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
			if(mmSchemeRadioButton.isSelected()) mergeRobotsCheckBox.setEnabled(true);
    }

    private void implicitRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
			mergeRobotsCheckBox.setEnabled(false);
			mergeRobotsCheckBox.setSelected(false);
    }

    private void rungekuttaRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
			if(mmSchemeRadioButton.isSelected()) mergeRobotsCheckBox.setEnabled(true);
    }

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		if(!started && canvas1.start())
			{
			animatorThread = new Thread(this);        
	        animatorThread.start();
			eulerRadioButton.setEnabled(false);
			implicitRadioButton.setEnabled(false);
			rungekuttaRadioButton.setEnabled(false);
			linearSchemeRadioButton.setEnabled(false);
			mmSchemeRadioButton.setEnabled(false);
			circumcenterSchemeRadioButton.setEnabled(false);
			mergeRobotsCheckBox.setEnabled(false);
			}
    }

    private void pauseToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
	if(started && !pauseToggleButton.isSelected())
			{
			animatorThread = new Thread(this);        
			animatorThread.start();
			}
		
    }
	

    private void recordShapeCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void tracePathsCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void generateButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:	
		if(started && !pauseToggleButton.isSelected())generate = true;
		else generateAction();		
    }
	
	public void generateAction()
	{
	stopRun();	
	canvas1.removeAllRobots();
	int num = Integer.parseInt(randomRobotNumberTextField.getText());
	canvas1.generateRandomRobots(num);
	generate = false;
	}

    private void starRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void convexRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void randomRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }
	
    private void starConvertButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		canvas1.putInStarForm();
    }                                            

    private void cmCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		canvas1.repaint();
    }

    private void cmPolyCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		canvas1.repaint();
    }

    private void velVectorsCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		canvas1.repaint();
    }

    private void ccCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		canvas1.repaint();
    }
	
    private void starLinesCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		canvas1.repaint();
}
	
	public void start()
	{
	System.out.println("Starting...");
            
	}
	
	public void stop()
	{
	System.out.println("Stopping...");
	}	
	
	public void timer(int t)
    {
	try {
	    Thread.sleep(t);
	}
	catch (InterruptedException e) {
	    System.out.println ("Sleep Interrupted");
	}
    }
	
}
