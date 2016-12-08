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


public class FluidsApplet extends JApplet implements ChangeListener{

	private FluidsCanvas canvas1;
	private ColorCanvas colorCanvas;
	private int boardWidth = 300;
	private int boardHeight = 300;


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel avgLabel;
    private javax.swing.JCheckBox equipotentialsCheckBox;
    private javax.swing.ButtonGroup extraLinesButtonGroup;
    private javax.swing.ButtonGroup flowDirectionButtonGroup;
    private javax.swing.JTextField iterTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JRadioButton leftToRightButton;
    private javax.swing.JLabel maxLabel;
    private javax.swing.JLabel minLabel;
    private javax.swing.JRadioButton noExtraLinesButton;
    private javax.swing.JTextField pixelTextField;
    private javax.swing.JRadioButton potentialButton;
    private javax.swing.JComboBox presetsComboBox;
    private javax.swing.JButton redoButton;
    private javax.swing.JRadioButton rightToLeftButton;
    private javax.swing.JRadioButton streamFunctionButton;
    private javax.swing.JRadioButton streamLinesButton;
    private javax.swing.JRadioButton velocityButton;
    private javax.swing.JRadioButton velocityVectorsButton;
    private javax.swing.JRadioButton velocityXButton;
    private javax.swing.JRadioButton velocityYButton;
    private javax.swing.ButtonGroup viewTypeButtonGroup;
    // End of variables declaration//GEN-END:variables
	
	private org.jdesktop.layout.GroupLayout layout;
	
	public void init()
	{	
	System.out.println("Initializing...");	
	
	canvas1 = new FluidsCanvas(boardWidth,boardHeight,this);
	colorCanvas = new ColorCanvas(20,112);
	initComponents();
	setLayout(layout);
	
    canvas1.requestFocusInWindow();
	}

	private void initComponents() {

        viewTypeButtonGroup = new javax.swing.ButtonGroup();
        extraLinesButtonGroup = new javax.swing.ButtonGroup();
        flowDirectionButtonGroup = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        potentialButton = new javax.swing.JRadioButton();
        velocityButton = new javax.swing.JRadioButton();
        streamFunctionButton = new javax.swing.JRadioButton();
        redoButton = new javax.swing.JButton();
        iterTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        pixelTextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        velocityXButton = new javax.swing.JRadioButton();
        velocityYButton = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        velocityVectorsButton = new javax.swing.JRadioButton();
        streamLinesButton = new javax.swing.JRadioButton();
        noExtraLinesButton = new javax.swing.JRadioButton();
        equipotentialsCheckBox = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        leftToRightButton = new javax.swing.JRadioButton();
        rightToLeftButton = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        presetsComboBox = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        maxLabel = new javax.swing.JLabel();
        minLabel = new javax.swing.JLabel();
        avgLabel = new javax.swing.JLabel();


        setPreferredSize(new java.awt.Dimension(700, 300));

		jLabel1.setText("View Type:");

        viewTypeButtonGroup.add(potentialButton);
        potentialButton.setText("Potential");
        potentialButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                potentialButtonActionPerformed(evt);
				updateLabels();
				canvas1.requestFocusInWindow();
            }
        });

        viewTypeButtonGroup.add(velocityButton);
        velocityButton.setSelected(true);
        velocityButton.setText("Velocity (abs)");
        velocityButton.setSelected(true);
        velocityButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                velocityButtonActionPerformed(evt);
				updateLabels();
				canvas1.requestFocusInWindow();
            }
        });

        viewTypeButtonGroup.add(streamFunctionButton);
        streamFunctionButton.setText("Stream Function");
        streamFunctionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                streamFunctionButtonActionPerformed(evt);
				updateLabels();
				canvas1.requestFocusInWindow();
            }
        });

        redoButton.setText("Redo with");
        redoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                redoButtonActionPerformed(evt);
				canvas1.requestFocusInWindow();
            }
        });

        iterTextField.setColumns(4);
        iterTextField.setText("5000");

        jLabel2.setText("iterations");

        jLabel3.setText("and effective pixel size of");

        pixelTextField.setColumns(2);
        pixelTextField.setText("2");

        jLabel4.setText("pixels");

        viewTypeButtonGroup.add(velocityXButton);
        velocityXButton.setText("x-Velocity");
        velocityXButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                velocityXButtonActionPerformed(evt);
				updateLabels();
				canvas1.requestFocusInWindow();
            }
        });

        viewTypeButtonGroup.add(velocityYButton);
        velocityYButton.setText("y-Velocity");
        velocityYButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                velocityYButtonActionPerformed(evt);
				updateLabels();
				canvas1.requestFocusInWindow();
            }
        });

        jLabel5.setText("Extra lines:");

        extraLinesButtonGroup.add(velocityVectorsButton);
        velocityVectorsButton.setText("Velocity vectors");
        velocityVectorsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                velocityVectorsButtonActionPerformed(evt);
				canvas1.requestFocusInWindow();
            }
        });

        extraLinesButtonGroup.add(streamLinesButton);
        streamLinesButton.setSelected(true);
        streamLinesButton.setText("Streamlines");
        streamLinesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                streamLinesButtonActionPerformed(evt);
				canvas1.requestFocusInWindow();
            }
        });

        extraLinesButtonGroup.add(noExtraLinesButton);
        noExtraLinesButton.setText("None");
        noExtraLinesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noExtraLinesButtonActionPerformed(evt);
				canvas1.requestFocusInWindow();
            }
        });

        equipotentialsCheckBox.setText("Equipotentials");
        equipotentialsCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                equipotentialsCheckBoxActionPerformed(evt);
				canvas1.requestFocusInWindow();
            }
        });

        jLabel6.setText("Flow direction:");

        flowDirectionButtonGroup.add(leftToRightButton);
        leftToRightButton.setSelected(true);
        leftToRightButton.setText("Left to right");
        leftToRightButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                leftToRightButtonActionPerformed(evt);
				canvas1.requestFocusInWindow();
            }
        });

        flowDirectionButtonGroup.add(rightToLeftButton);
        rightToLeftButton.setText("Right to left");
        rightToLeftButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rightToLeftButtonActionPerformed(evt);
				canvas1.requestFocusInWindow();
            }
        });

        jLabel7.setText("Select object:");	
		
		ItemListener presetSelectionListener = new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED)
				{				
				String preset = e.getItem().toString();
				try
				{
				int nIters = Integer.parseInt(iterTextField.getText());
				int res = Integer.parseInt(pixelTextField.getText());
				canvas1.redo(nIters, res, preset);
				}
				catch (NumberFormatException er)
					{
					System.out.println("Error: Invalid number");
					System.out.println(er);
					}	
				canvas1.requestFocusInWindow();	
				}
			}	        
		};
		
        presetsComboBox.addItemListener(presetSelectionListener);

        presetsComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "None", "Vertical wall", "Diagonal wall", "Square", "Diamond", "Circle" }));
        presetsComboBox.setSelectedIndex(4);

        jLabel8.setText("Max");

        jLabel9.setText("Min");

        maxLabel.setText("1.0");

        minLabel.setText("-1.0");

        avgLabel.setText("0.0");
		
        //org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
		layout = new org.jdesktop.layout.GroupLayout(this.getContentPane());
        //this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(canvas1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 300, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel5)
                    .add(jLabel1)
                    .add(velocityButton)
                    .add(velocityXButton)
                    .add(velocityYButton)
                    .add(potentialButton)
                    .add(streamFunctionButton)
                    .add(streamLinesButton)
                    .add(noExtraLinesButton)
                    .add(velocityVectorsButton)
                    .add(equipotentialsCheckBox))
                .add(29, 29, 29)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(rightToLeftButton)
                    .add(leftToRightButton)
                    .add(jLabel6)
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(layout.createSequentialGroup()
                            .add(redoButton)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(iterTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 41, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(jLabel2))
                        .add(layout.createSequentialGroup()
                            .add(jLabel3)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(pixelTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 10, Short.MAX_VALUE)
                            .add(jLabel4))
                        .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                            .add(presetsComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(colorCanvas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(avgLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                                .add(minLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                                .add(maxLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE))
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(jLabel9)
                                .add(jLabel8))))
                    .add(jLabel7))
                .add(85, 85, 85))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(canvas1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 300, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(layout.createSequentialGroup()
                        .add(jLabel1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(velocityButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(velocityXButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(velocityYButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(potentialButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(streamFunctionButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jLabel5)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(streamLinesButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(velocityVectorsButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(noExtraLinesButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(equipotentialsCheckBox))
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(redoButton)
                            .add(iterTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel2))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel3)
                            .add(pixelTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel4))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel6)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(leftToRightButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(rightToLeftButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jLabel7)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(presetsComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                                .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                        .add(maxLabel)
                                        .add(jLabel8))
                                    .add(38, 38, 38)
                                    .add(avgLabel)
                                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                        .add(minLabel)
                                        .add(jLabel9)))
                                .add(org.jdesktop.layout.GroupLayout.LEADING, colorCanvas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 122, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))))
                .add(21, 21, 21))
        );
		updateLabels();
    }// </editor-fold>//GEN-END:initComponents

	public void stateChanged(ChangeEvent e) {
	
        JSlider source = (JSlider)e.getSource();
		
            int value = source.getValue();
			String name = source.getName();
			if (name.equals("horizontalViewSlider"))
				{
				}
			else if (name.equals("verticalViewSlider"))
				{
				}
				
		//canvas1.repaint();
       canvas1.requestFocusInWindow();
    }	
	
    private void velocityButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		canvas1.setViewType(canvas1.VELOCITY);
}

    private void potentialButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		canvas1.setViewType(canvas1.POTENTIAL);
}

    private void streamFunctionButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		canvas1.setViewType(canvas1.STREAM);
}

    private void redoButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:		
		try
		{
		String preset = presetsComboBox.getSelectedItem().toString();
		int nIters = Integer.parseInt(iterTextField.getText());
		int res = Integer.parseInt(pixelTextField.getText());
		canvas1.redo(nIters, res, preset);
		}
		catch (NumberFormatException e)
			{
			System.out.println("Error: Invalid number");
			System.out.println(e);
			}
}

    private void velocityXButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		canvas1.setViewType(canvas1.VX);
}

    private void velocityYButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		canvas1.setViewType(canvas1.VY);
}                              

    private void equipotentialsCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void streamLinesButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		canvas1.setExtraLinesType(canvas1.STREAMLINES);
    }

    private void velocityVectorsButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		canvas1.setExtraLinesType(canvas1.VECTORS);
    }

    private void noExtraLinesButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		canvas1.setExtraLinesType(canvas1.NONE);
    }

    private void leftToRightButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		canvas1.setLeftToRight(true);
		try
		{
		String preset = presetsComboBox.getSelectedItem().toString();
		int nIters = Integer.parseInt(iterTextField.getText());
		int res = Integer.parseInt(pixelTextField.getText());
		canvas1.redo(nIters, res, preset);
		}
		catch (NumberFormatException e)
			{
			System.out.println("Error: Invalid number");
			System.out.println(e);
			}
}

    private void rightToLeftButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		canvas1.setLeftToRight(false);
		try
		{
		String preset = presetsComboBox.getSelectedItem().toString();
		int nIters = Integer.parseInt(iterTextField.getText());
		int res = Integer.parseInt(pixelTextField.getText());
		canvas1.redo(nIters, res, preset);
		}
		catch (NumberFormatException e)
			{
			System.out.println("Error: Invalid number");
			System.out.println(e);
			}
}

	public void updateLabels()
	{
	String max = String.valueOf(canvas1.getMax());
	String avg = String.valueOf(canvas1.getAvg());
	String min = String.valueOf(canvas1.getMin());
	System.out.println(max);
	System.out.println(avg);
	System.out.println(min);
	
	if(max.length() > 6) 
		{
		if(max.contains("E"))
			{
			if(max.charAt(0) == '-') max = max.substring(0,4) + max.substring(max.length()-3,avg.length());
			else max = max.substring(0,3) + max.substring(max.length()-3,avg.length());
			}
		else if(max.charAt(0) == '-') max = max.substring(0,7);
		else max = max.substring(0,6);
		}
	if(avg.length() > 6) 
		{
		if(avg.contains("E"))
			{
			if(avg.charAt(0) == '-') avg = avg.substring(0,4) + avg.substring(avg.length()-3,avg.length());
			else avg = avg.substring(0,3) + avg.substring(avg.length()-3,avg.length());
			}
		else if(avg.charAt(0) == '-') avg = avg.substring(0,7);
		else avg = avg.substring(0,6);
		}
	if(min.length() > 6)
		{
		if(min.contains("E"))
			{
			if(min.charAt(0) == '-') min = min.substring(0,4) + min.substring(max.length()-3,avg.length());
			else min = min.substring(0,3) + min.substring(min.length()-3,avg.length());
			}
		else if(min.charAt(0) == '-') min = min.substring(0,7);
		else min = min.substring(0,6);
		}
	maxLabel.setText(max);
	avgLabel.setText(avg);
	minLabel.setText(min);
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
