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

public class LevelSetApplet extends JApplet implements Runnable{

	LevelSetCanvas canvas1;
  int bwMax = 500;
  int bhMax = 500;
	int boardWidth = bwMax;
	int boardHeight = bhMax;
  int startInd = 4; // 0 to 4 for 100 x 100 to 500 x 500
	private Thread animatorThread;
	private int multiplier = 100;
	private int elapsedTime = 0; //keeps track of total time elapsed in game (in ms)
	private org.jdesktop.layout.GroupLayout layout;
	boolean started;
	boolean stop;
	int pixelsWide;
	int pixelsHigh;
	int res;
  double dt = 0.5;
  double t;
	String resStart = "20";

    // Variables declaration - do not modify//GEN-BEGIN:variables
     javax.swing.ButtonGroup buttonGroup1;
     javax.swing.JButton calculateButton;
     javax.swing.JButton clearButton;
     javax.swing.JCheckBox colorCheckBox;
     javax.swing.JCheckBox contoursCheckBox;
     javax.swing.JCheckBox drawGridCheckBox;
     javax.swing.JTextField dtTextField;
     javax.swing.JComboBox examplesComboBox;
     javax.swing.JLabel jLabel1;
     javax.swing.JLabel jLabel10;
     javax.swing.JLabel jLabel12;
     javax.swing.JLabel jLabel13;
     javax.swing.JLabel jLabel2;
     javax.swing.JLabel jLabel3;
     javax.swing.JLabel jLabel4;
     javax.swing.JLabel jLabel5;
     javax.swing.JLabel jLabel6;
     javax.swing.JLabel jLabel7;
     javax.swing.JLabel jLabel8;
     javax.swing.JLabel jLabel9;
     javax.swing.JSeparator jSeparator1;
     javax.swing.JSeparator jSeparator2;
     javax.swing.JSeparator jSeparator3;
     javax.swing.JSeparator jSeparator4;
     javax.swing.JToggleButton pauseToggleButton;
     javax.swing.JCheckBox recalculateDistanceFunctionCheckBox;
     javax.swing.JTextField resTextField;
     javax.swing.JComboBox screenSizeComboBox;
     javax.swing.JCheckBox showBoundaryCheckBox;
     javax.swing.JComboBox speedFunctionComboBox;
     javax.swing.JButton startAdvectionButton;
     javax.swing.JButton stopAdvectionButton;
    // End of variables declaration//GEN-END:variables
	
	public void init()
	{	
	System.out.println("Initializing...");	
	canvas1 = new LevelSetCanvas(boardWidth,boardHeight,this);
	initComponents();
	setLayout(layout);
	updateRes();
	//canvas1.loadPreset("Signed distance of sample curve");
  
  screenSizeComboBoxActionPerformed(null);
	}
	
	private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        calculateButton = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        resTextField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        colorCheckBox = new javax.swing.JCheckBox();
        drawGridCheckBox = new javax.swing.JCheckBox();
        clearButton = new javax.swing.JButton();
        showBoundaryCheckBox = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();
        examplesComboBox = new javax.swing.JComboBox();
        startAdvectionButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        dtTextField = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        speedFunctionComboBox = new javax.swing.JComboBox();
        pauseToggleButton = new javax.swing.JToggleButton();
        stopAdvectionButton = new javax.swing.JButton();
        contoursCheckBox = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        screenSizeComboBox = new javax.swing.JComboBox();
        recalculateDistanceFunctionCheckBox = new javax.swing.JCheckBox();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();

        setName("Form"); // NOI18N
        setPreferredSize(new java.awt.Dimension(800, 600));
        
        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Level set applet");

        calculateButton.setText("Calculate distance function");
        calculateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calculateButtonActionPerformed(evt);
            }
        });

        jLabel4.setText("on grid with resolution of");

        jLabel5.setText("1 grid point per");

        resTextField.setText(resStart);
        resTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resTextFieldActionPerformed(evt);
            }
        });

        jLabel6.setText("pixels");

        jLabel7.setText("Visualization options:");

        colorCheckBox.setSelected(true);
        colorCheckBox.setText("Color based on distance");
        colorCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorCheckBoxActionPerformed(evt);
            }
        });

        drawGridCheckBox.setSelected(true);
        drawGridCheckBox.setText("Draw grid");
        drawGridCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                drawGridCheckBoxActionPerformed(evt);
            }
        });

        clearButton.setText("Clear screen");
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });


        showBoundaryCheckBox.setSelected(true);
        showBoundaryCheckBox.setText("Show original (true) boundary");
        showBoundaryCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showBoundaryCheckBoxActionPerformed(evt);
            }
        });

        jLabel8.setText("Examples:");

        examplesComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Signed distance of sample curve", "Nonsimple curve", "Completely random points", "Random walk", "Test rectangle" }));

		/*
		ItemListener presetSelectionListener = new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED)
				{				
				String preset = e.getItem().toString();
				clearAction();						
				updateRes();
				
				canvas1.loadPreset(preset);	
				canvas1.repaint();
				}
			}	        
		};
        examplesComboBox.addItemListener(presetSelectionListener);*/
        
        examplesComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                examplesComboBoxActionPerformed(evt);
            }
        });
		
    startAdvectionButton.setText("Start");
        startAdvectionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startAdvectionButtonActionPerformed(evt);
            }
        });

        jLabel3.setText("Level set advection:");

        jLabel9.setText("with time step = ");

        jLabel10.setText("and speed function F =");

        speedFunctionComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "sin(t/10)", "-1", "-kappa", "kappa" }));
        
    		ItemListener speedFunctionSelectionListener = new ItemListener() {
    			public void itemStateChanged(ItemEvent e) {
    			if (e.getStateChange() == ItemEvent.SELECTED)
    				{				
    				//String preset = e.getItem().toString();
            if(canvas1.cd != null) {
              canvas1.cd.speedChoice = speedFunctionComboBox.getSelectedIndex();
              canvas1.cd.speedConst = ( canvas1.cd.speedChoice < 3 );
            }
    				}
    			}	        
    		};
        speedFunctionComboBox.addItemListener(speedFunctionSelectionListener);
        
        pauseToggleButton.setText("Pause");
        pauseToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pauseToggleButtonActionPerformed(evt);
            }
        });

        stopAdvectionButton.setText("Stop");
        stopAdvectionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopAdvectionButtonActionPerformed(evt);
            }
        });

        contoursCheckBox.setSelected(true);
        contoursCheckBox.setText("Plot contours only");
        contoursCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contoursCheckBoxActionPerformed(evt);
            }
        });
        
        dtTextField.setText(Double.toString(dt));
        
        

        jLabel2.setText("Screen size (pixels):");
        jLabel2.setName("jLabel2"); // NOI18N

        screenSizeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "100 x 100", "200 x 200", "300 x 300", "400 x 400", "500 x 500" }));
        screenSizeComboBox.setSelectedIndex(startInd);
        screenSizeComboBox.setName("screenSizeComboBox"); // NOI18N
        screenSizeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                screenSizeComboBoxActionPerformed(evt);
            }
        });

        recalculateDistanceFunctionCheckBox.setSelected(true);
        recalculateDistanceFunctionCheckBox.setText("Recalculate distance function");
        recalculateDistanceFunctionCheckBox.setName("recalculateDistanceFunctionCheckBox"); // NOI18N
        recalculateDistanceFunctionCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recalculateDistanceFunctionCheckBoxActionPerformed(evt);
            }
        });

        jSeparator1.setName("jSeparator1"); // NOI18N

        jSeparator2.setName("jSeparator2"); // NOI18N

        jSeparator3.setName("jSeparator3"); // NOI18N

        jSeparator4.setName("jSeparator4"); // NOI18N

        jLabel12.setText("Draw curves with the mouse, then evolve");
        jLabel12.setName("jLabel12"); // NOI18N

        jLabel13.setText("the interface with normal speed function F");
        jLabel13.setName("jLabel13"); // NOI18N
    
        //org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
		layout = new org.jdesktop.layout.GroupLayout(this.getContentPane());
        //this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(canvas1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 500, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jSeparator4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, calculateButton)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(22, 22, 22)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(jLabel5)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(resTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 26, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jLabel6))
                            .add(jLabel4)))
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jLabel12)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jLabel1)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jSeparator1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jLabel3)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(startAdvectionButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel9)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(dtTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE))
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(10, 10, 10)
                        .add(jLabel10)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(speedFunctionComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 73, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(pauseToggleButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(stopAdvectionButton))
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(10, 10, 10)
                        .add(recalculateDistanceFunctionCheckBox))
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jLabel13)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jSeparator2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jLabel7)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(10, 10, 10)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(contoursCheckBox)
                            .add(colorCheckBox)
                            .add(drawGridCheckBox)
                            .add(showBoundaryCheckBox)))
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jSeparator3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, clearButton)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jLabel8)
                    .add(examplesComboBox, 0, 280, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(2, 2, 2)
                        .add(jLabel2)
                        .add(14, 14, 14)
                        .add(screenSizeComboBox, 0, 168, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(jLabel1)
                        .add(5, 5, 5)
                        .add(jLabel12, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 14, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(3, 3, 3)
                        .add(jLabel13, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 14, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jSeparator4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(calculateButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel4)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel5)
                            .add(resTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel6))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jSeparator1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel3)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(startAdvectionButton)
                            .add(jLabel9)
                            .add(dtTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel10)
                            .add(speedFunctionComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(pauseToggleButton)
                            .add(stopAdvectionButton))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(recalculateDistanceFunctionCheckBox)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jSeparator2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(1, 1, 1)
                        .add(jLabel7)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(colorCheckBox)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(contoursCheckBox)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(drawGridCheckBox)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(showBoundaryCheckBox)
                        .add(4, 4, 4)
                        .add(jSeparator3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(clearButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jLabel8)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(examplesComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel2)
                            .add(screenSizeComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .add(canvas1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 501, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(56, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    private void calculateButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		
		updateRes();
		canvas1.cd = new CalculateDistance(this,canvas1);
		canvas1.repaint();
    }
	
	void updateRes()
	{
	res = Integer.parseInt(resTextField.getText());
	canvas1.res = res;
	pixelsWide = 1+boardWidth/res;
	pixelsHigh = 1+boardHeight/res;
	canvas1.pixelsWide = pixelsWide;
	canvas1.pixelsHigh = pixelsHigh;
	}

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {
		clearAction();
    }
	
	public void clearAction()
	{
		canvas1.surface.removeAllElements();
		canvas1.destinations.removeAllElements();
		canvas1.cd = null;
		canvas1.repaint();	
	}

    private void colorCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		canvas1.repaint();
    }

    private void drawPathsCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void drawGridCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		canvas1.repaint();
    }

    private void animateAlgorithmCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }                                                  

    private void resTextFieldActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		updateRes();
		canvas1.repaint();
    }

    private void showBoundaryCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		canvas1.repaint();
    }   

    private void startAdvectionButtonActionPerformed(java.awt.event.ActionEvent evt) {
        
      updateRes();
      if(canvas1.cd == null) canvas1.cd = new CalculateDistance(this,canvas1);
      else canvas1.cd.loadSavedPhi();	
      canvas1.cd.initializeAdvection();
      canvas1.cd.speedChoice = speedFunctionComboBox.getSelectedIndex();
      canvas1.cd.speedConst = ( canvas1.cd.speedChoice < 3 );
      dt = Double.parseDouble(dtTextField.getText());
      
      animatorThread = new Thread(this);        
      animatorThread.start();        
    }

    private void pauseToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {
      if(started && !pauseToggleButton.isSelected())
  			{
  			animatorThread = new Thread(this);        
  			animatorThread.start();
  			}
    }

    private void stopAdvectionButtonActionPerformed(java.awt.event.ActionEvent evt) {
        stop = true;
        if(pauseToggleButton.isSelected())
          {
          // Reset shape:
          canvas1.cd.loadSavedPhi();
      		canvas1.repaint();
          pauseToggleButton.setSelected(false);
          }
    }

    private void contoursCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }
    
                                                  

    private void recalculateDistanceFunctionCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void screenSizeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {    
      int ind = screenSizeComboBox.getSelectedIndex();
      clearAction();
			canvas1.fillBG();
      boardWidth = 100*(ind+1);
      boardHeight = boardWidth;
    	canvas1.boardWidth = boardWidth;
    	canvas1.boardHeight = boardHeight;
      
      
    	updateRes();
      canvas1.repaint();
    }
	  
    private void examplesComboBoxActionPerformed(java.awt.event.ActionEvent evt) {
        String preset = examplesComboBox.getSelectedItem().toString();
				clearAction();						
				updateRes();
				
				canvas1.loadPreset(preset);	
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
	
	
	public void run()
	{
	started = true;
	stop = false;
  
  t = 0;
      
	while (Thread.currentThread() == animatorThread && !pauseToggleButton.isSelected() && !stop)
		{
      canvas1.cd.advectLevelSet(dt);
      if(recalculateDistanceFunctionCheckBox.isSelected()) canvas1.cd.recalculateDistanceFunction();
      t += dt;
			
			canvas1.repaint();
			timer(multiplier);
		}

  if(stop) {
    // Reset shape:
    canvas1.cd.loadSavedPhi();
		canvas1.repaint();
    }
	}
}
