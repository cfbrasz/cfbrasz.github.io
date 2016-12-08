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


public class RobotFTApplet extends JApplet implements Runnable,ChangeListener{ //,ChangeListener {

	private RobotFTCanvas canvas1;
	private int boardWidth = 600;
	private int boardHeight = 600;
	private int multiplier = 20;
	private int elapsedTime = 0; //keeps track of total time elapsed in game (in ms)
    private int prevTime = 0;
	private Thread animatorThread;
	private boolean started = false;
	private boolean paused = false;
	private boolean stop = false;
	URL codeBase;  // URL for applet codebase
     Image borgAwake;
	 Image borgSleep;
     Image robotAwake;
	 Image robotSleep;
     MediaTracker mt; 


	// Variables declaration - do not modify
    //private java.awt.Canvas canvas1;
    private javax.swing.JButton clearButton;
    private javax.swing.JRadioButton dahlbergRadioButton;
    private javax.swing.JRadioButton designateAwakeButton;
    private javax.swing.JButton greedyButton;
    private javax.swing.JButton greedyButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.ButtonGroup mouseClickGroup;
    private javax.swing.JButton newOrderButton;
    private javax.swing.JButton optimalButton;
    private javax.swing.JToggleButton pauseButton;
    private javax.swing.JRadioButton placeButton;
    private javax.swing.JButton randomButton;
    private javax.swing.JRadioButton removeButton;
    private javax.swing.ButtonGroup robotButtonGroup;
    private javax.swing.JRadioButton regularButton;
    private javax.swing.JSlider speedSlider;
    private javax.swing.JButton startButton;
    private javax.swing.JButton stopButton;
    private javax.swing.JLabel timeLabel;
    private javax.swing.JCheckBox traceCheckBox;
    private javax.swing.JCheckBox yawnCheckBox;	
    private javax.swing.JButton generateRandomButton;
    private javax.swing.JTextField generateTextField;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JRadioButton robotButton;
    // End of variables declaration
	
	private org.jdesktop.layout.GroupLayout layout;
	
	public void init()
	{	
	System.out.println("Initializing...");	
	codeBase = getCodeBase();
	 mt = new MediaTracker(this);
	borgSleep = getImage(codeBase,"borgSleep.gif");
	borgAwake = getImage(codeBase,"borgAwake.gif");
	robotSleep = getImage(codeBase,"robotAsleep.gif");
	robotAwake = getImage(codeBase,"robotAwake.gif");
	mt.addImage(borgSleep,1);	
	mt.addImage(borgAwake,1);
	mt.addImage(robotSleep,1);	
	mt.addImage(robotAwake,1);
	try {
               mt.waitForAll();
          }
          catch (InterruptedException  e) {} 
	canvas1 = new RobotFTCanvas(boardWidth,boardHeight,borgSleep,borgAwake,robotSleep,robotAwake);
	initComponents();
	setLayout(layout);
	
	}

	private void initComponents() {

        robotButtonGroup = new javax.swing.ButtonGroup();
        mouseClickGroup = new javax.swing.ButtonGroup();
        //canvas1 = new java.awt.Canvas();
        startButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        timeLabel = new javax.swing.JLabel();
        speedSlider = new javax.swing.JSlider();
        jLabel4 = new javax.swing.JLabel();
        regularButton = new javax.swing.JRadioButton();
        dahlbergRadioButton = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        yawnCheckBox = new javax.swing.JCheckBox();
        traceCheckBox = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        pauseButton = new javax.swing.JToggleButton();
        stopButton = new javax.swing.JButton();
        clearButton = new javax.swing.JButton();
        placeButton = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        removeButton = new javax.swing.JRadioButton();
        designateAwakeButton = new javax.swing.JRadioButton();
        newOrderButton = new javax.swing.JButton();
        randomButton = new javax.swing.JButton();
        greedyButton = new javax.swing.JButton();
        greedyButton1 = new javax.swing.JButton();
        optimalButton = new javax.swing.JButton();
        generateRandomButton = new javax.swing.JButton();
        generateTextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        robotButton = new javax.swing.JRadioButton();

		setPreferredSize(new java.awt.Dimension(840, 700));

		speedSlider.addChangeListener(this);		
		speedSlider.setName("speedSlider");		
        speedSlider.setValue(20);
		
        startButton.setText("Start");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        jLabel2.setText("Time Elapsed:");

        timeLabel.setText("00:00.000");

        jLabel4.setText("Robot speed");

        robotButtonGroup.add(regularButton);
        regularButton.setSelected(true);
        regularButton.setText("Regular");
        regularButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                regularButtonActionPerformed(evt);
            }
        });

        robotButtonGroup.add(dahlbergRadioButton);
        dahlbergRadioButton.setText("Dahlborg");
        dahlbergRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dahlbergRadioButtonActionPerformed(evt);
            }
        });

        jLabel5.setText("Robot Type:");

        yawnCheckBox.setText("Yawns");
        yawnCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yawnCheckBoxActionPerformed(evt);
            }
        });

        traceCheckBox.setSelected(true);
        traceCheckBox.setText("Trace Paths");
        traceCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                traceCheckBoxActionPerformed(evt);
            }
        });
		
		jLabel6.setText("Redo schedule...");
		
		newOrderButton.setText("Manually");
        newOrderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newOrderButtonActionPerformed(evt);
            }
        });

        randomButton.setText("Randomly");
        randomButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                randomButtonActionPerformed(evt);
            }
        });

        greedyButton.setText("using Greedy Algorithm v1");
        greedyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                greedyButtonActionPerformed(evt);
            }
        });

        greedyButton1.setText("using Greedy Algorithm v2");
        greedyButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                greedyButton1ActionPerformed(evt);
            }
        });

        optimalButton.setText("Optimally");
        optimalButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optimalButtonActionPerformed(evt);
            }
        });
/*
        scheduleButtonGroup.add(randomButton);
        randomButton.setText("Random");
        randomButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                randomButtonActionPerformed(evt);
            }
        });

        scheduleButtonGroup.add(optimalButton);
        optimalButton.setText("Optimal");
        optimalButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optimalButtonActionPerformed(evt);
            }
        });

        jLabel6.setText("Schedule Type:");

        scheduleButtonGroup.add(userButton);
        userButton.setSelected(true);
        userButton.setText("Clicking Order");
        userButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userButtonActionPerformed(evt);
            }
        });
		*/

        pauseButton.setText("Pause");
        pauseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pauseButtonActionPerformed(evt);
            }
        });

        stopButton.setText("Reset");
        stopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopButtonActionPerformed(evt);
            }
        });

        clearButton.setText("Clear All Robots");
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

		mouseClickGroup.add(placeButton);
        placeButton.setSelected(true);
        placeButton.setText("Place sleeping robot");
        placeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                placeButtonActionPerformed(evt);
            }
        });

        jLabel3.setText("Mouse Click Action");

        mouseClickGroup.add(removeButton);
        removeButton.setText("Remove robot");
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });

        mouseClickGroup.add(designateAwakeButton);
        designateAwakeButton.setText("Designate starting robot");
        designateAwakeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                designateAwakeButtonActionPerformed(evt);
            }
        });
		
		generateRandomButton.setText("Generate n random robots");
        generateRandomButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateRandomButtonActionPerformed(evt);
            }
        });

        generateTextField.setText("10");
        generateTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateTextFieldActionPerformed(evt);
            }
        });

		robotButtonGroup.add(robotButton);
		robotButton.setText("Real Robot");
        robotButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                robotButtonActionPerformed(evt);
            }
        });
		
        jLabel1.setText("where n =");

        //org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
		layout = new org.jdesktop.layout.GroupLayout(this.getContentPane());
        //this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(canvas1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 600, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(46, 46, 46)
                        .add(jLabel1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(generateTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(22, 22, 22)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(layout.createSequentialGroup()
                                .add(jLabel2)
                                .add(35, 35, 35)
                                .add(timeLabel))
                            .add(jLabel4)
                            .add(layout.createSequentialGroup()
                                .add(newOrderButton)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(randomButton))
                            .add(greedyButton)
                            .add(greedyButton1)
                            .add(jLabel6)
                            .add(jLabel3)
                            .add(placeButton)
                            .add(removeButton)
                            .add(designateAwakeButton)
                            .add(optimalButton)
                            .add(speedSlider, 0, 0, Short.MAX_VALUE)
                            .add(clearButton)
                            .add(layout.createSequentialGroup()
                                .add(pauseButton)
                                .add(10, 10, 10)
                                .add(stopButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .add(layout.createSequentialGroup()
                                .add(traceCheckBox)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(yawnCheckBox))
                            .add(layout.createSequentialGroup()
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jSeparator1))
                            .add(generateRandomButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(startButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(layout.createSequentialGroup()
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(regularButton)
                                    .add(jLabel5))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(dahlbergRadioButton)
                                    .add(robotButton))))))
                .addContainerGap(58, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(canvas1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 600, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(startButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(pauseButton)
                            .add(stopButton))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(clearButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel2)
                            .add(timeLabel))
                        .add(13, 13, 13)
                        .add(jLabel4)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(speedSlider, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(1, 1, 1)
                        .add(jLabel3)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(placeButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(removeButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(designateAwakeButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jLabel6)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(newOrderButton)
                            .add(randomButton))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(greedyButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(greedyButton1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(optimalButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jSeparator1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(5, 5, 5)
                        .add(generateRandomButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel1)
                            .add(generateTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel5)
                            .add(robotButton))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(regularButton)
                            .add(dahlbergRadioButton))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(traceCheckBox)
                            .add(yawnCheckBox))))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>

	public void stateChanged(ChangeEvent e) {
	
        JSlider source = (JSlider)e.getSource();
		
            int value = source.getValue();
			String name = source.getName();
			if (name.equals("speedSlider"))
				{
				canvas1.setSpeed((double)value/5.0 + 0.1);
				}
				
		canvas1.repaint();
       
    }
	    private void regularButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		canvas1.regularMode();
}                                    

    private void yawnCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
		canvas1.switchYawn();
    }                                            

    private void traceCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {                                              
        // TODO add your handling code here:
		canvas1.switchTrace();
}                                             

    private void optimalButtonActionPerformed(java.awt.event.ActionEvent evt) {                                              
        // TODO add your handling code here:
		canvas1.generateOptimalOrderTree();
    }                                             
                    

    private void greedyButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		canvas1.runGreedyAlg();
}

    private void greedyButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		canvas1.runGreedyAlgV2();
		
    }
	
    private void randomButtonActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
		canvas1.setRandomMode();
    }                                            

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
		if(canvas1.start() && !canvas1.checkIfAllAwake())
			{
			animatorThread = new Thread(this);        
	        animatorThread.start();
			}
    }                                           

    private void pauseButtonActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
		if(started && !canvas1.checkIfAllAwake()) {
			if(paused)
				{
				animatorThread = new Thread(this);        
				animatorThread.start();
				}
			paused = !paused;
			}
		else paused = !paused;
    }                                           

    private void userButtonActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
		canvas1.setUserMode();
    }                                          

    private void dahlbergRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                    
        // TODO add your handling code here:		
		canvas1.borgMode();
    }                                                   

    private void stopButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		stopRun();
		canvas1.reset();
}                                           

    private void newOrderButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		stopRun();
		canvas1.reset();
		//canvas1.chooseNewSchedule();
    }

public void stopRun()
	{
	if(started)
		{
		if(paused) 
			{	
			paused = false;
			pauseButton.setSelected(false);
			}
		stop = true;
		}
	elapsedTime = 0;
	timeLabel.setText("00:00.000");
	}

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		canvas1.clearAll();
		canvas1.clearPoints();
		stopRun();
}

    private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		canvas1.setRemoveMode();
}

    private void designateAwakeButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:		
		canvas1.setDesignateMode();
    }

    private void placeButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		canvas1.setPlaceMode();
    }        
	
	private void generateRandomButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		canvas1.clearAll();
		canvas1.clearPoints();
		stopRun();
		int num = Integer.parseInt(generateTextField.getText());
		canvas1.generateRandomRobots(num);
    }

    private void generateTextFieldActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }
	
	    private void robotButtonActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
		canvas1.robotMode();
	}

	public void run()
	{
	started = true;
	stop = false;
	
	while (Thread.currentThread() == animatorThread && !paused && !stop)
	{
			
		canvas1.repaint();
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
		if(canvas1.checkIfAllAwake()) break;
	}
		
		
	
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