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

public class BuoyancyApplet extends JApplet implements Runnable,ChangeListener {

	private double multiplier = 10.0;
	private BuoyancyCanvas screen;
	private boolean loop = false;
	private Thread animatorThread;
	private double scale = 100;
	
	// Variables declaration - do not modify
    private javax.swing.JSlider AngleSlider;
    private javax.swing.JSlider AngularSpeedSlider;
    private javax.swing.JToggleButton DampingOffButton;
    private javax.swing.JSlider DensitySlider;
    private javax.swing.JSlider HeightSlider;
    private javax.swing.JButton StartButton;
    private javax.swing.JSlider VerticalPositionSlider;
    private javax.swing.JSlider VerticalSpeedSlider;
    private javax.swing.JSlider WidthSlider;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    //private java.awt.Canvas screen;
	private org.jdesktop.layout.GroupLayout layout;
    // End of variables declaration
	
	public void init()
	{	
	System.out.println("Initializing...");
	screen = new BuoyancyCanvas(multiplier);
	add(screen);
	initComponents();	
	}
	
	public void start()
	{
	System.out.println("Starting...");
	
	if (animatorThread == null) {
            animatorThread = new Thread(this);
        }
        animatorThread.start();

	}
	
	private void initComponents() {

        //screen = new java.awt.Canvas();
        StartButton = new javax.swing.JButton();
        WidthSlider = new javax.swing.JSlider();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        HeightSlider = new javax.swing.JSlider();
        AngleSlider = new javax.swing.JSlider();
        jLabel4 = new javax.swing.JLabel();
        AngularSpeedSlider = new javax.swing.JSlider();
        jLabel5 = new javax.swing.JLabel();
        VerticalSpeedSlider = new javax.swing.JSlider();
        jLabel6 = new javax.swing.JLabel();
        DampingOffButton = new javax.swing.JToggleButton();
        VerticalPositionSlider = new javax.swing.JSlider();
        jLabel7 = new javax.swing.JLabel();
        DensitySlider = new javax.swing.JSlider();
        jLabel8 = new javax.swing.JLabel();

        StartButton.setText("Restart Simulation");
        StartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StartButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Configure settings below");

        jLabel2.setText("Width");

		WidthSlider.setValue(50);
		
        jLabel3.setText("Height");
		
		HeightSlider.setValue(8);

        AngleSlider.setMaximum(90);
        AngleSlider.setMinimum(-90);
        AngleSlider.setValue(35);

        jLabel4.setText("Starting Angle");

        AngularSpeedSlider.setMaximum(50);
        AngularSpeedSlider.setMinimum(-50);
        AngularSpeedSlider.setValue(0);

        jLabel5.setText("Starting Angular Speed");

        VerticalSpeedSlider.setMaximum(50);
        VerticalSpeedSlider.setMinimum(-50);
        VerticalSpeedSlider.setValue(0);

        jLabel6.setText("Starting Vertical Speed");

        DampingOffButton.setText("Turn Off Damping");
        DampingOffButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DampingOffButtonActionPerformed(evt);
            }
        });

        VerticalPositionSlider.setMaximum(500);
        VerticalPositionSlider.setMinimum(0);
        VerticalPositionSlider.setValue(250);

        jLabel7.setText("Starting Vertical Position");

        DensitySlider.setMaximum(120);
        DensitySlider.setValue(40);

        jLabel8.setText("Density (0.0 to 1.2, water's is 1.0)");
		
		AngleSlider.addChangeListener(this);
		AngularSpeedSlider.addChangeListener(this);
		DensitySlider.addChangeListener(this);
		HeightSlider.addChangeListener(this);
		VerticalPositionSlider.addChangeListener(this);
		VerticalSpeedSlider.addChangeListener(this);
		WidthSlider.addChangeListener(this);
		
		AngleSlider.setName("AngleSlider");
		AngularSpeedSlider.setName("AngularSpeedSlider");
		DensitySlider.setName("DensitySlider");
		HeightSlider.setName("HeightSlider");
		VerticalPositionSlider.setName("VerticalPositionSlider");
		VerticalSpeedSlider.setName("VerticalSpeedSlider");
		WidthSlider.setName("WidthSlider");

        //org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        				
		layout = new org.jdesktop.layout.GroupLayout(this.getContentPane());
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(screen, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 600, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(StartButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 164, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(WidthSlider, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel2)
                    .add(jLabel1)
                    .add(HeightSlider, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel3)
                    .add(AngularSpeedSlider, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel5)
                    .add(VerticalSpeedSlider, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel6)
                    .add(VerticalPositionSlider, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel7)
                    .add(AngleSlider, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel4)
                    .add(DampingOffButton)
                    .add(DensitySlider, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel8))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(screen, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 500, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(StartButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jLabel1)
                        .add(18, 18, 18)
                        .add(jLabel2)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(WidthSlider, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jLabel3)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(HeightSlider, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(11, 11, 11)
                        .add(jLabel4)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(AngleSlider, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jLabel7)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(VerticalPositionSlider, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jLabel5)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(AngularSpeedSlider, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jLabel6)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(VerticalSpeedSlider, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jLabel8)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(DensitySlider, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(DampingOffButton)))
                .addContainerGap())
        );
    }// </editor-fold>

    private void StartButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		screen.createBlock();
    }

    private void DampingOffButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		screen.switchDamping();
    }    
	
	public void stateChanged(ChangeEvent e) {
	
        JSlider source = (JSlider)e.getSource();
		
        //if (!source.getValueIsAdjusting()) {
            int value = source.getValue();
			String name = source.getName();
			//changeMade = true;
			if (name.equals("WidthSlider"))
				{
				screen.setWidth(value*4);
				}
			else if (name.equals("HeightSlider"))
				{
				screen.setHeight(value*4);
				}
			else if (name.equals("AngleSlider"))
				{
				screen.setAngle(-(double)value*Math.PI/180.);
				}
			else if (name.equals("VerticalPositionSlider"))
				{
				screen.setStartVertPosition(500-value);
				}
			else if (name.equals("VerticalSpeedSlider"))
				{
				screen.setStartVerticalSpeed(-(double)value/2.);
				}
			else if (name.equals("AngularSpeedSlider"))
				{
				screen.setStartAngularSpeed(-(double)value/100.);
				}
			else if (name.equals("DensitySlider"))
				{
				screen.setStartDensity((double)value/100.);
				}
				
		screen.repaint();
       
    }
	
	public void stop()
	{
	System.out.println("Stopping...");
	//screen.repaint();
	}
	
	public void run()
	{
	while (Thread.currentThread() == animatorThread)
	{
	screen.iterate();
	
	screen.repaint();
		try {
		    Thread.sleep((int)multiplier);
		}
		catch (InterruptedException e) {
		    System.out.println ("Sleep Interrupted");
		}
	}
	}
	
	public void destroy()
	{
	System.out.println("Destroying...");
	}
	
}
	