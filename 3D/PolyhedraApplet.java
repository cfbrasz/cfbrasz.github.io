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


public class PolyhedraApplet extends JApplet implements ChangeListener{

	private PolyhedraCanvas canvas1;
	private int boardWidth = 500;
	private int boardHeight = 500;
	private int mboardWidth = 110;
	private int mboardHeight = 110;
	private int defaultV = 25;
	private Vector<Coord3D> polygonPts = new Vector<Coord3D>();
	private String selectedShape = "Rectangular prism";
	private int selectedRemoveShape = 0;
	private MiniCanvas miniCanvas;
	private int defaultBrightness = 60;


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addShapeButton;
    private javax.swing.JButton addVertexButton;
    private javax.swing.JTextField allowedReflectionsTextField;
    private javax.swing.JTextField blueTextField;
    private javax.swing.JSlider brightnessSlider;
    private javax.swing.JTextField centerXTextField;
    private javax.swing.JTextField centerYTextField;
    private javax.swing.JTextField centerZTextField;
    private javax.swing.JTextField cubeXWidthTextField;
    private javax.swing.JTextField cubeYWidthTextField;
    private javax.swing.JTextField cubeZWidthTextField;
    private javax.swing.JComboBox currentShapesComboBox;
    private javax.swing.JTextField directionVectorXTextField;
    private javax.swing.JTextField directionVectorYTextField;
    private javax.swing.JTextField directionVectorZTextField;
    private javax.swing.JButton finishPolygonButton;
    private javax.swing.JTextField greenTextField;
    private javax.swing.JTextField heightTextField;
    private javax.swing.JRadioButton highResButton;
    private javax.swing.JRadioButton highestResButton;
    private javax.swing.JSlider horizontalViewSlider;
    private javax.swing.JTextField innerRadiusTextField;
    private javax.swing.JRadioButton inverseSquareLightingButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.ButtonGroup lightColorButtonGroup;
    private javax.swing.JTextField lightSourceXTextField;
    private javax.swing.JTextField lightSourceYTextField;
    private javax.swing.JTextField lightSourceZTextField;
    private javax.swing.ButtonGroup lightingDistButtonGroup;
    private javax.swing.JRadioButton lowResButton;
    private javax.swing.JRadioButton mediumResButton;
    private javax.swing.JButton newDirectionVectorButton;
    private javax.swing.JButton newLightSourceLocationButton;
    private javax.swing.JButton newViewButton;
    private javax.swing.JRadioButton noDistLightingButton;
    private javax.swing.JTextField normalXTextField;
    private javax.swing.JTextField normalYTextField;
    private javax.swing.JTextField normalZTextField;
    private javax.swing.JRadioButton orthographicButton;
    private javax.swing.JRadioButton pinHoleButton;
    private javax.swing.JComboBox presetsComboBox;
    private javax.swing.JTextField radiusTextField;
    private javax.swing.JTextField redTextField;
    private javax.swing.JTextField reflectivityTextField;
    private javax.swing.JButton removeShapeButton;
    private javax.swing.ButtonGroup resButtonGroup;
    private javax.swing.JButton resetButton;
    private javax.swing.JComboBox selectShapeComboBox;
    private javax.swing.JRadioButton sqrtLightingButton;
    private javax.swing.JButton startPolygonButton;
    private javax.swing.JButton updateReflectionsButton;
    private javax.swing.JSlider verticalViewSlider;
    private javax.swing.JComboBox verticesPlacedComboBox;
    private javax.swing.JSlider viewAngleSlider;
    private javax.swing.ButtonGroup viewTypeButtonGroup;
    private javax.swing.JTextField viewpointXTextField;
    private javax.swing.JTextField viewpointYTextField;
    private javax.swing.JTextField viewpointZTextField;
    private javax.swing.JTextField xCoordTextField;
    private javax.swing.JTextField yCoordTextField;
    private javax.swing.JTextField zCoordTextField;
    // End of variables declaration//GEN-END:variables
	
	private org.jdesktop.layout.GroupLayout layout;
	
	public void init()
	{	
	System.out.println("Initializing...");	
	
	miniCanvas = new MiniCanvas(mboardWidth,mboardHeight,this);
	canvas1 = new PolyhedraCanvas(boardWidth,boardHeight,this,miniCanvas);
	initComponents();
	setLayout(layout);
	
    canvas1.requestFocusInWindow();
	updateViewSliders();		
    updateViewpointFields();	
	updateLightSourceFields();
	}

	private void initComponents() {

        resButtonGroup = new javax.swing.ButtonGroup();
        lightColorButtonGroup = new javax.swing.ButtonGroup();
        lightingDistButtonGroup = new javax.swing.ButtonGroup();
        viewTypeButtonGroup = new javax.swing.ButtonGroup();
        xCoordTextField = new javax.swing.JTextField();
        yCoordTextField = new javax.swing.JTextField();
        zCoordTextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        addVertexButton = new javax.swing.JButton();
        startPolygonButton = new javax.swing.JButton();
        viewpointXTextField = new javax.swing.JTextField();
        viewpointYTextField = new javax.swing.JTextField();
        viewpointZTextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        horizontalViewSlider = new javax.swing.JSlider();
        newViewButton = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        verticalViewSlider = new javax.swing.JSlider();
        presetsComboBox = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        newLightSourceLocationButton = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        lightSourceXTextField = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        lightSourceYTextField = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        lightSourceZTextField = new javax.swing.JTextField();
        highestResButton = new javax.swing.JRadioButton();
        mediumResButton = new javax.swing.JRadioButton();
        lowResButton = new javax.swing.JRadioButton();
        highResButton = new javax.swing.JRadioButton();
        jLabel13 = new javax.swing.JLabel();
        noDistLightingButton = new javax.swing.JRadioButton();
        sqrtLightingButton = new javax.swing.JRadioButton();
        inverseSquareLightingButton = new javax.swing.JRadioButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        pinHoleButton = new javax.swing.JRadioButton();
        orthographicButton = new javax.swing.JRadioButton();
        resetButton = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        centerXTextField = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        centerYTextField = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        centerZTextField = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        cubeXWidthTextField = new javax.swing.JTextField();
        cubeYWidthTextField = new javax.swing.JTextField();
        cubeZWidthTextField = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        blueTextField = new javax.swing.JTextField();
        greenTextField = new javax.swing.JTextField();
        redTextField = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        normalXTextField = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        normalYTextField = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        normalZTextField = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        radiusTextField = new javax.swing.JTextField();
        viewAngleSlider = new javax.swing.JSlider();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel35 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        reflectivityTextField = new javax.swing.JTextField();
        finishPolygonButton = new javax.swing.JButton();
        newDirectionVectorButton = new javax.swing.JButton();
        jLabel36 = new javax.swing.JLabel();
        directionVectorXTextField = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        directionVectorYTextField = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        directionVectorZTextField = new javax.swing.JTextField();
        currentShapesComboBox = new javax.swing.JComboBox();
        jLabel39 = new javax.swing.JLabel();
        removeShapeButton = new javax.swing.JButton();
        verticesPlacedComboBox = new javax.swing.JComboBox();
        addShapeButton = new javax.swing.JButton();
        selectShapeComboBox = new javax.swing.JComboBox();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        heightTextField = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        innerRadiusTextField = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel44 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        allowedReflectionsTextField = new javax.swing.JTextField();
        updateReflectionsButton = new javax.swing.JButton();
        jLabel45 = new javax.swing.JLabel();
        brightnessSlider = new javax.swing.JSlider();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();


        setPreferredSize(new java.awt.Dimension(920, 650));

        xCoordTextField.setColumns(3);

        yCoordTextField.setColumns(3);

        zCoordTextField.setColumns(3);

        jLabel1.setText("x");

        jLabel2.setText("y");

        jLabel3.setText("z");

        addVertexButton.setText("Add vertex with coordinates:");
        addVertexButton.setEnabled(false);
        addVertexButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addVertexButtonActionPerformed(evt);
				canvas1.requestFocusInWindow();
            }
        });

        startPolygonButton.setText("Start new polygon");
        startPolygonButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startPolygonButtonActionPerformed(evt);
				canvas1.requestFocusInWindow();
            }
        });
		
		viewpointXTextField.setColumns(3);
        viewpointXTextField.setText("0");

        viewpointYTextField.setColumns(3);
        viewpointYTextField.setText("0");

        viewpointZTextField.setColumns(3);
        viewpointZTextField.setText("0");

        jLabel4.setText("x");

        jLabel5.setText("y");

        jLabel6.setText("z");

        newViewButton.setText("Set new viewpoint to:");
        newViewButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newViewButtonActionPerformed(evt);
				canvas1.requestFocusInWindow();
            }
        });

        jLabel7.setText("Rotate view horizontally:");

        jLabel8.setText("Rotate view vertically:");

        verticalViewSlider.setOrientation(javax.swing.JSlider.VERTICAL);	
		verticalViewSlider.setName("verticalViewSlider");		
		verticalViewSlider.setMaximum(90);
		verticalViewSlider.setMinimum(-90);
		verticalViewSlider.setValue(0);
		
		horizontalViewSlider.setName("horizontalViewSlider");			
		horizontalViewSlider.setMaximum(180);
		horizontalViewSlider.setMinimum(-180);
		horizontalViewSlider.setValue(0);
		
		viewAngleSlider.setName("viewAngleSlider");	
		viewAngleSlider.setMaximum(100);
		viewAngleSlider.setMinimum(1);
		viewAngleSlider.setValue(defaultV);
		
		brightnessSlider.setName("brightnessSlider");	
		brightnessSlider.setMaximum(100);
		brightnessSlider.setMinimum(1);
		brightnessSlider.setValue(defaultBrightness);
		
		verticalViewSlider.addChangeListener(this);
		horizontalViewSlider.addChangeListener(this);
		viewAngleSlider.addChangeListener(this);
		brightnessSlider.addChangeListener(this);

        presetsComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Blank", "Simple room", "Room with spheres", "Room with cylinders, disks", "Mirror room", "Reflective cylinder and sphere" }));
        /*
		presetsComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                presetsComboBoxActionPerformed(evt);
				canvas1.requestFocusInWindow();
            }
        });
		*/					
		ItemListener presetSelectionListener = new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED)
				{				
				String select = e.getItem().toString();
				canvas1.loadPreset(select);		
				updateCurrentShapes();			
				}
				canvas1.requestFocusInWindow();	
			}	        
		};
		
        presetsComboBox.addItemListener(presetSelectionListener);
		presetsComboBox.setSelectedIndex(2);
		
					
		ItemListener doNothingListener = new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
			
			canvas1.requestFocusInWindow();			
				
			}	        
		};
		verticesPlacedComboBox.addItemListener(doNothingListener);

        jLabel9.setText("Select preset configuration:");

        newLightSourceLocationButton.setText("Move light source to:");
        newLightSourceLocationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newLightSourceLocationButtonActionPerformed(evt);
				canvas1.requestFocusInWindow();
            }
        });

        jLabel10.setText("x");

        lightSourceXTextField.setColumns(3);
        lightSourceXTextField.setText("0");

        jLabel11.setText("y");

        lightSourceYTextField.setColumns(3);
        lightSourceYTextField.setText("0");

        jLabel12.setText("z");

        lightSourceZTextField.setColumns(3);
        lightSourceZTextField.setText("0");

		resButtonGroup.add(highestResButton);
        highestResButton.setText("Highest");
        highestResButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                highestResButtonActionPerformed(evt);
				canvas1.requestFocusInWindow();
            }
        });

        resButtonGroup.add(mediumResButton);
        mediumResButton.setSelected(true);
        mediumResButton.setText("Medium");
        mediumResButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mediumResButtonActionPerformed(evt);
				canvas1.requestFocusInWindow();
            }
        });

        resButtonGroup.add(lowResButton);
        lowResButton.setText("Lowest");
        lowResButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lowResButtonActionPerformed(evt);
				canvas1.requestFocusInWindow();
            }
        });

        resButtonGroup.add(highResButton);
        highResButton.setText("High");
        highResButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                highResButtonActionPerformed(evt);
				canvas1.requestFocusInWindow();
            }
        });

        jLabel13.setText("Resolution:");

        lightingDistButtonGroup.add(noDistLightingButton);
        noDistLightingButton.setText("No distance dependance");
        noDistLightingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noDistLightingButtonActionPerformed(evt);
				canvas1.requestFocusInWindow();
            }
        });

        lightingDistButtonGroup.add(sqrtLightingButton);
        sqrtLightingButton.setSelected(true);
        sqrtLightingButton.setText("Inverse square root");
        sqrtLightingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sqrtLightingButtonActionPerformed(evt);
				canvas1.requestFocusInWindow();
            }
        });

        lightingDistButtonGroup.add(inverseSquareLightingButton);
        inverseSquareLightingButton.setText("Inverse square");
        inverseSquareLightingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inverseSquareLightingButtonActionPerformed(evt);
				canvas1.requestFocusInWindow();
            }
        });

        jLabel14.setText("Lighting distance dependance:");
		
		jLabel16.setText("View type:");

        viewTypeButtonGroup.add(pinHoleButton);
        pinHoleButton.setSelected(true);
        pinHoleButton.setText("Pinhole Camera");
        pinHoleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pinHoleButtonActionPerformed(evt);
				canvas1.requestFocusInWindow();
            }
        });

        viewTypeButtonGroup.add(orthographicButton);
        orthographicButton.setText("Orthographic");
        orthographicButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orthographicButtonActionPerformed(evt);
				canvas1.requestFocusInWindow();
            }
        });

        resetButton.setText("Reset to defaults");
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetButtonActionPerformed(evt);
				canvas1.requestFocusInWindow();
            }
        });

        jLabel17.setText("x");

        centerXTextField.setColumns(3);
        centerXTextField.setText("50");

        jLabel18.setText("y");

        centerYTextField.setColumns(3);
        centerYTextField.setText("40");

        jLabel19.setText("z");

        centerZTextField.setColumns(3);
        centerZTextField.setText("10");

        jLabel20.setText("x width");

        cubeXWidthTextField.setColumns(3);
        cubeXWidthTextField.setText("20");

        cubeYWidthTextField.setColumns(3);
        cubeYWidthTextField.setText("20");

        cubeZWidthTextField.setColumns(3);
        cubeZWidthTextField.setText("20");

        jLabel21.setText("y width");

        jLabel22.setText("z width");

        jLabel23.setText("Center (corner for rect. prisms)");

        blueTextField.setColumns(3);
        blueTextField.setText("0");

        greenTextField.setColumns(3);
        greenTextField.setText("0");

        redTextField.setColumns(3);
        redTextField.setText("255");

        jLabel24.setText("Red");

        jLabel25.setText("Green");

        jLabel26.setText("Blue");

        jLabel27.setText("with specified center, radius, and color");

        jLabel28.setText("x");

        normalXTextField.setColumns(3);
        normalXTextField.setText("0");
        normalXTextField.setEnabled(false);

        jLabel29.setText("y");

        normalYTextField.setColumns(3);
        normalYTextField.setText("0");
        normalYTextField.setEnabled(false);

        jLabel30.setText("z");

        normalZTextField.setColumns(3);
        normalZTextField.setText("1");
        normalZTextField.setEnabled(false);

        jLabel31.setText("Radius:");

        radiusTextField.setColumns(3);
        radiusTextField.setText("10");
        radiusTextField.setEnabled(false);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
		
		jLabel35.setText("Angle of view:");
        
        jLabel32.setText("Color and reflectivity for new shape:");

        jLabel33.setText("Reflectivity: 0 (not) to 1 (mirror)");

        jLabel34.setText("(0-255 each)");
		
        reflectivityTextField.setColumns(3);
        reflectivityTextField.setText("0");

        finishPolygonButton.setText("Finish polygon");
        finishPolygonButton.setEnabled(false);
        finishPolygonButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                finishPolygonButtonActionPerformed(evt);
				canvas1.requestFocusInWindow();
            }
        });

        newDirectionVectorButton.setText("Set direction vector to:");
        newDirectionVectorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newDirectionVectorButtonActionPerformed(evt);
				canvas1.requestFocusInWindow();
            }
        });

        jLabel36.setText("x");

        directionVectorXTextField.setColumns(3);
        directionVectorXTextField.setText("1");

        jLabel37.setText("y");

        directionVectorYTextField.setColumns(3);
        directionVectorYTextField.setText("0");

        jLabel38.setText("z");

        directionVectorZTextField.setColumns(3);
        directionVectorZTextField.setText("0");

        jLabel39.setText("Vertices placed:");

        removeShapeButton.setText("Remove selected shape");
        removeShapeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeShapeButtonActionPerformed(evt);
				canvas1.requestFocusInWindow();
            }
        });

        addShapeButton.setText("Add specified shape...");
        addShapeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addShapeButtonActionPerformed(evt);
				canvas1.requestFocusInWindow();
            }
        });
		
		selectShapeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Rectangular prism", "Cube", "Sphere", "Circle", "Cylinder", "Uncapped Cylinder"})); //, "Trapezoidal Cylinder" }));
			
		ItemListener shapeSelectionListener = new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED)
				{				
				String select = e.getItem().toString();
				selectedShape = select;			
				}
			updateActiveTextFields();
				canvas1.requestFocusInWindow();
			}	        
		};
		
        selectShapeComboBox.addItemListener(shapeSelectionListener);
		selectShapeComboBox.setSelectedIndex(0);		
		
		ItemListener removeShapeSelectionListener = new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED)
				{				
				selectedRemoveShape = currentShapesComboBox.getSelectedIndex()-1;		
				canvas1.setSelectedRemoveShape(currentShapesComboBox.getSelectedIndex()-1);	
				canvas1.repaint();
				canvas1.requestFocusInWindow();
				}
			}	        
		};		
		
		currentShapesComboBox.addItemListener(removeShapeSelectionListener);
		updateCurrentShapes();
		
        jLabel40.setText("the relevant parameters and click below");

        jLabel41.setText("... then specify");

        jLabel27.setText("Normal vector");

        jLabel42.setText("Height:");

        heightTextField.setColumns(3);
        heightTextField.setText("20");
        heightTextField.setEnabled(false);

        jLabel43.setText("Inner radius:");

        innerRadiusTextField.setColumns(3);
        innerRadiusTextField.setText("10");
        innerRadiusTextField.setEnabled(false);

        jLabel44.setText("To add an object, select a shape type...");

        jLabel15.setText("Number of allowed reflections:");

        allowedReflectionsTextField.setColumns(3);
        allowedReflectionsTextField.setText("10");

        updateReflectionsButton.setText("Update");
        updateReflectionsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateReflectionsButtonActionPerformed(evt);
				canvas1.requestFocusInWindow();
            }
        });

        jLabel45.setText("Light strength:");

        brightnessSlider.setOrientation(javax.swing.JSlider.VERTICAL);

        jLabel46.setForeground(new java.awt.Color(255, 0, 0));
        jLabel46.setText("Positive x axis");

        jLabel47.setForeground(new java.awt.Color(0, 255, 0));
        jLabel47.setText("Positive y axis");

        jLabel48.setText("Arrows point in");

        jLabel49.setText("direction of...");

        jLabel50.setForeground(new java.awt.Color(0, 0, 255));
        jLabel50.setText("Positive z axis");
		
        //org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
		layout = new org.jdesktop.layout.GroupLayout(this.getContentPane());
        //this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(canvas1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 500, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jLabel13)
                                    .add(highestResButton)
                                    .add(highResButton)
                                    .add(mediumResButton))
                                .add(18, 18, 18)
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(inverseSquareLightingButton)
                                    .add(noDistLightingButton)
                                    .add(sqrtLightingButton)
                                    .add(jLabel14, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 155, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                            .add(lowResButton))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(2, 2, 2)
                                .add(jLabel16)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(jLabel15))
                            .add(layout.createSequentialGroup()
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(pinHoleButton)
                                    .add(viewAngleSlider, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 98, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(orthographicButton)
                                    .add(jLabel35))
                                .add(18, 18, 18)
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(layout.createSequentialGroup()
                                        .add(10, 10, 10)
                                        .add(jLabel45)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(brightnessSlider, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                    .add(layout.createSequentialGroup()
                                        .add(allowedReflectionsTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                        .add(updateReflectionsButton)))))))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                            .add(resetButton)
                            .add(36, 36, 36))
                        .add(layout.createSequentialGroup()
                            .add(newViewButton)
                            .add(14, 14, 14))
                        .add(layout.createSequentialGroup()
                            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                                .add(org.jdesktop.layout.GroupLayout.LEADING, jLabel7, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                                    .add(jLabel4)
                                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                    .add(viewpointXTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                    .add(jLabel5)
                                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                    .add(viewpointYTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(6, 6, 6)
                                    .add(jLabel6)
                                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                    .add(viewpointZTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                            .add(16, 16, 16))
                        .add(layout.createSequentialGroup()
                            .add(horizontalViewSlider, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 119, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(32, 32, 32))
                        .add(layout.createSequentialGroup()
                            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                                .add(org.jdesktop.layout.GroupLayout.LEADING, presetsComboBox, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(org.jdesktop.layout.GroupLayout.LEADING, jLabel9))
                            .add(18, 18, 18)))
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                        .add(layout.createSequentialGroup()
                            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                        .add(jLabel47)
                                        .add(jLabel46)
                                        .add(jLabel50))
                                    .add(jLabel49))
                                .add(jLabel48))
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(miniCanvas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 110, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(layout.createSequentialGroup()
                            .add(jLabel8)
                            .add(18, 18, 18)
                            .add(verticalViewSlider, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(6, 6, 6)))
                    .add(newDirectionVectorButton)
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, newLightSourceLocationButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                            .add(jLabel10)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(lightSourceXTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(14, 14, 14)
                            .add(jLabel11)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(lightSourceYTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(18, 18, 18)
                            .add(jLabel12)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(lightSourceZTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(6, 6, 6))
                        .add(org.jdesktop.layout.GroupLayout.LEADING, removeShapeButton)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, currentShapesComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(layout.createSequentialGroup()
                        .add(jLabel36)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(directionVectorXTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(14, 14, 14)
                        .add(jLabel37)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(directionVectorYTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(jLabel38)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(directionVectorZTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jSeparator1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(24, 24, 24)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(addShapeButton)
                    .add(layout.createSequentialGroup()
                        .add(jLabel43)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(innerRadiusTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jLabel27)
                    .add(layout.createSequentialGroup()
                        .add(jLabel28)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(normalXTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(12, 12, 12)
                        .add(jLabel29)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(normalYTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jLabel30)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(normalZTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(layout.createSequentialGroup()
                        .add(jLabel42)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(heightTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(layout.createSequentialGroup()
                        .add(jLabel31)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(radiusTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(startPolygonButton)
                    .add(layout.createSequentialGroup()
                        .add(jLabel1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(xCoordTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(15, 15, 15)
                        .add(jLabel2)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(yCoordTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(jLabel3)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(zCoordTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(reflectivityTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel33)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(redTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(layout.createSequentialGroup()
                                .add(2, 2, 2)
                                .add(jLabel24)))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(greenTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel25))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(blueTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(layout.createSequentialGroup()
                                .add(jLabel26)
                                .add(18, 18, 18)
                                .add(jLabel34))))
                    .add(layout.createSequentialGroup()
                        .add(selectShapeComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel41))
                    .add(jLabel32)
                    .add(layout.createSequentialGroup()
                        .add(2, 2, 2)
                        .add(jLabel17)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(cubeXWidthTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel20)
                            .add(centerXTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(11, 11, 11)
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(cubeYWidthTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(jLabel21)))
                            .add(layout.createSequentialGroup()
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jLabel18)
                                .add(1, 1, 1)
                                .add(centerYTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(jLabel19)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(centerZTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(jLabel22)
                            .add(cubeZWidthTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .add(jLabel23)
                    .add(jLabel40)
                    .add(finishPolygonButton)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jLabel44, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .add(jLabel39)
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, verticesPlacedComboBox, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, jSeparator2)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, addVertexButton)))
                .add(44, 44, 44))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(canvas1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 500, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(jLabel13)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(highestResButton)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(highResButton)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(mediumResButton)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(lowResButton))
                            .add(layout.createSequentialGroup()
                                .add(jLabel14)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(layout.createSequentialGroup()
                                        .add(46, 46, 46)
                                        .add(inverseSquareLightingButton))
                                    .add(layout.createSequentialGroup()
                                        .add(noDistLightingButton)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(sqrtLightingButton))))
                            .add(layout.createSequentialGroup()
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                    .add(jLabel16)
                                    .add(jLabel15))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(layout.createSequentialGroup()
                                        .add(pinHoleButton)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(orthographicButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .add(18, 18, 18)
                                        .add(jLabel35)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(viewAngleSlider, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                    .add(layout.createSequentialGroup()
                                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                            .add(allowedReflectionsTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                            .add(updateReflectionsButton))
                                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                            .add(layout.createSequentialGroup()
                                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                .add(brightnessSlider, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 72, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                            .add(layout.createSequentialGroup()
                                                .add(33, 33, 33)
                                                .add(jLabel45))))))))
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(miniCanvas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 110, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(layout.createSequentialGroup()
                                .add(jLabel48)
                                .add(4, 4, 4)
                                .add(jLabel49)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(jLabel46)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jLabel47)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jLabel50)))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(resetButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel9)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(presetsComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(11, 11, 11)
                        .add(newViewButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(1, 1, 1)
                                .add(jLabel4))
                            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                .add(viewpointXTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jLabel6)
                                .add(viewpointZTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(viewpointYTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jLabel5)))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel7)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(horizontalViewSlider, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                            .add(jLabel8)
                            .add(verticalViewSlider, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 77, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(1, 1, 1)
                        .add(newDirectionVectorButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(1, 1, 1)
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                    .add(jLabel38)
                                    .add(jLabel36)
                                    .add(directionVectorZTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                .add(directionVectorXTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(directionVectorYTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jLabel37)))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(newLightSourceLocationButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(1, 1, 1)
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                    .add(jLabel12)
                                    .add(jLabel10)
                                    .add(lightSourceZTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                .add(lightSourceXTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(lightSourceYTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jLabel11)))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(removeShapeButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(currentShapesComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jSeparator1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 628, Short.MAX_VALUE))
                    .add(layout.createSequentialGroup()
                        .add(20, 20, 20)
                        .add(jLabel44)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(selectShapeComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel41))
                        .add(3, 3, 3)
                        .add(jLabel40)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel32)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                    .add(jLabel24)
                                    .add(jLabel25))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                    .add(redTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(greenTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                            .add(layout.createSequentialGroup()
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                    .add(jLabel26)
                                    .add(jLabel34))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(blueTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel33)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(reflectivityTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel23)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(centerXTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel18)
                            .add(centerYTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(centerZTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel19)
                            .add(jLabel17))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                    .add(jLabel21)
                                    .add(jLabel20))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                    .add(cubeXWidthTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(cubeYWidthTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                            .add(layout.createSequentialGroup()
                                .add(jLabel22)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(cubeZWidthTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel31)
                            .add(radiusTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(2, 2, 2)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel43)
                            .add(innerRadiusTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel42)
                            .add(heightTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel27)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(1, 1, 1)
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                    .add(jLabel30)
                                    .add(normalZTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                .add(jLabel28)
                                .add(normalYTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jLabel29)
                                .add(normalXTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(addShapeButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jSeparator2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(3, 3, 3)
                        .add(startPolygonButton)
                        .add(10, 10, 10)
                        .add(addVertexButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel3)
                            .add(jLabel1)
                            .add(xCoordTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(yCoordTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel2)
                            .add(zCoordTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(17, 17, 17)
                        .add(finishPolygonButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel39)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(verticesPlacedComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

	public void stateChanged(ChangeEvent e) {
	
        JSlider source = (JSlider)e.getSource();
		
            int value = source.getValue();
			String name = source.getName();
			if (name.equals("horizontalViewSlider"))
				{
				canvas1.setHorizontalView(value);
				updateDirectionFields();
				}
			else if (name.equals("verticalViewSlider"))
				{
				canvas1.setVerticalView(value);
				updateDirectionFields();
				//System.out.println(value);
				}
			else if (name.equals("viewAngleSlider"))
				{
				canvas1.setViewAngleSlider(value);
				//System.out.println(value);
				}
			else if (name.equals("brightnessSlider"))
				{
				canvas1.setBrightness(value);
				}
				
		//canvas1.repaint();
       canvas1.requestFocusInWindow();
    }
	
	private void presetsComboBoxActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		
}

    private void newLightSourceLocationButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		try
		{
		double x = Double.parseDouble(lightSourceXTextField.getText());
		double y = Double.parseDouble(lightSourceYTextField.getText());
		double z = Double.parseDouble(lightSourceZTextField.getText());
		canvas1.setLightSource(x,y,z);
		}
		catch (NumberFormatException e)
			{
			System.out.println("Error: Invalid number");
			System.out.println(e);
			}
    }

    private void addVertexButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		int nEmpty = 0;
		int curSize = polygonPts.size();
		Coord3D[] coords = new Coord3D[3];
		Triangle3D tri = null;
		Plane pl = null;
		if(xCoordTextField.getText().equals("")) nEmpty++;
		if(yCoordTextField.getText().equals("")) nEmpty++;
		if(zCoordTextField.getText().equals("")) nEmpty++;
		try
		{
		if(curSize > 2)
			{
			coords = new Coord3D[3];
			for(int i=0; i<3; i++) coords[i] = polygonPts.get(i);
			tri = new Triangle3D(coords);
			pl = tri.getPlane();
			}
		if(nEmpty == 1 && curSize > 2)
			{
			double x = -1234567;
			double y = -1234567;
			double z = -1234567;
			if(!xCoordTextField.getText().equals("")) x = Double.parseDouble(xCoordTextField.getText());
			if(!yCoordTextField.getText().equals("")) y = Double.parseDouble(yCoordTextField.getText());
			if(!zCoordTextField.getText().equals("")) z = Double.parseDouble(zCoordTextField.getText());
			if(xCoordTextField.getText().equals("")) 
				{
				x = pl.xOf(y,z);
				if(x != -1234567)
					{
					String xS = Double.toString(x);
					if(xS.length() > 5) xS = xS.substring(0,5);
					xCoordTextField.setText(xS);
					Coord3D addC = new Coord3D(x,y,z);
					polygonPts.add(addC);
					}
				else System.out.println("Error: point does not lie on plane");
				}
			else if(yCoordTextField.getText().equals("")) 
				{
				y = pl.yOf(z,x);
				if(y != -1234567)
					{
					String yS = Double.toString(y);
					if(yS.length() > 5) yS = yS.substring(0,5);
					yCoordTextField.setText(yS);
					polygonPts.add(new Coord3D(x,y,z));
					}
				else System.out.println("Error: point does not lie on plane");
				}				
			else if(zCoordTextField.getText().equals("")) 
				{
				z = pl.zOf(x,y);
				if(z != -1234567)
					{
					String zS = Double.toString(z);
					if(zS.length() > 5) zS = zS.substring(0,5);
					zCoordTextField.setText(zS);
					polygonPts.add(new Coord3D(x,y,z));
					}
				else System.out.println("Error: point does not lie on plane");
				}			
			}
		else if(nEmpty == 0)
			{
			double x = Double.parseDouble(xCoordTextField.getText());
			double y = Double.parseDouble(yCoordTextField.getText());
			double z = Double.parseDouble(zCoordTextField.getText());
			Coord3D cNew = new Coord3D(x,y,z);
			//System.out.println(x + " " + y + " " + z);
			if(curSize > 2)
				{
				Vector3D ab = new Vector3D(coords[0],coords[1]);
				Vector3D vCur = new Vector3D(coords[0],cNew);
				Vector3D nCur = ab.cross(vCur);
				if(nCur.parallelTo(tri.unitNormal())) polygonPts.add(cNew);
				else System.out.println("Error: point not on plane already defined by polygon");
				}
			else polygonPts.add(cNew);
			}
			
			String[] curPts = new String[polygonPts.size()];
			for(int i=0; i<polygonPts.size(); i++)
				{
				curPts[i] = (i+1) + ": " + polygonPts.get(i);
				}
			verticesPlacedComboBox.setModel(new javax.swing.DefaultComboBoxModel(curPts));
			verticesPlacedComboBox.setSelectedIndex(polygonPts.size()-1);
		}
		catch (NumberFormatException e)
			{
			System.out.println("Error: Invalid number");
			System.out.println(e);
			}
		if(polygonPts.size() == 3) finishPolygonButton.setEnabled(true);
    }

    private void startPolygonButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        addVertexButton.setEnabled(true);
        startPolygonButton.setEnabled(false);
    }

    private void newViewButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		try
		{
		double x = Double.parseDouble(viewpointXTextField.getText());
		double y = Double.parseDouble(viewpointYTextField.getText());
		double z = Double.parseDouble(viewpointZTextField.getText());
		canvas1.setViewpoint(x,y,z);
		}
		catch (NumberFormatException e)
			{
			System.out.println("Error - Invalid number:");
			System.out.println(e);
			}
    }                                              

    private void lowResButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		canvas1.setLowResolution();
    }

    private void mediumResButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		canvas1.setMediumResolution();
    }

    private void highResButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		canvas1.setHighResolution();
    }

    private void highestResButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		canvas1.setHighestResolution();
    }

    private void noDistLightingButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		canvas1.setNoDistLighting();
    }

    private void sqrtLightingButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		canvas1.setSqrtLighting();
    }

    private void inverseSquareLightingButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		canvas1.setInverseSquareLighting();
    }

    private void baseBlackButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		canvas1.setBaseColorToBlack();
    }

    private void whiteBaseButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		canvas1.setWhiteToBaseColor();
    }                                              

    private void orthographicButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		canvas1.setOrthographicView();
}

    private void pinHoleButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		canvas1.setPinHoleView();
    }

    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		canvas1.setLowResolution(); //to make repainting faster
		
		canvas1.reset();
		
		String select = presetsComboBox.getSelectedItem().toString();
		canvas1.loadPreset(select);	
		
		sqrtLightingButton.setSelected(true);
		canvas1.setSqrtLighting();
		
		pinHoleButton.setSelected(true);
		canvas1.setPinHoleView();
		
		canvas1.setBaseColorToBlack();
				
		updateViewSliders();	

		viewAngleSlider.setValue(defaultV);
		canvas1.setViewAngleSlider(defaultV);
		
        updateViewpointFields();
		updateDirectionFields();
		
        updateLightSourceFields();
		updateCurrentShapes();
		
		canvas1.setBrightness(defaultBrightness);
		brightnessSlider.setValue(defaultBrightness);
		
		allowedReflectionsTextField.setText("10");
		canvas1.setMaxReflections(10); 
		
		mediumResButton.setSelected(true);
		canvas1.setMediumResolution();
		// includes a repaint
    }             

    private void removeShapeButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		if(currentShapesComboBox.getSelectedIndex() > 0) canvas1.removeShape(currentShapesComboBox.getSelectedIndex()-1);
		//System.out.println(currentShapesComboBox.getSelectedIndex());
		canvas1.setSelectedRemoveShape(-1);	
		updateCurrentShapes();
		
	}                                                    

    private void updateReflectionsButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		try
		{
		int n = Integer.parseInt(allowedReflectionsTextField.getText());
		canvas1.setMaxReflections(n);
		}
		catch (NumberFormatException e)
			{
			System.out.println("Error - Invalid number, must be an integer.");
			System.out.println(e);
			}
}      

    private void updateCurrentShapes() {
        // TODO add your handling code here:
		String[] shapeNames = canvas1.shapeNames();
		currentShapesComboBox.setModel(new javax.swing.DefaultComboBoxModel(shapeNames));
	}     

		
    private void addSphere() {
        // TODO add your handling code here:
		try
		{
		double x = Double.parseDouble(centerXTextField.getText());
		double y = Double.parseDouble(centerYTextField.getText());
		double z = Double.parseDouble(centerZTextField.getText());
		double radius = Double.parseDouble(radiusTextField.getText());
		Coord3D cen = new Coord3D(x,y,z);
		int r = Integer.parseInt(redTextField.getText())%256;
		int g = Integer.parseInt(greenTextField.getText())%256;
		int b = Integer.parseInt(blueTextField.getText())%256;
		if(r<0) r=0;
		if(g<0) g=0;
		if(b<0) b=0;
		Color col = new Color(r,g,b);
		double ref = Double.parseDouble(reflectivityTextField.getText());
		if(ref>1) ref=1;
		if(ref<0) ref=0;
		canvas1.addSphere(cen,radius,col,ref);	
		}
		catch (NumberFormatException e)
			{
			System.out.println("Error - Invalid number (Colors must be integers):");
			System.out.println(e);
			}		
}                                      

    private void addCircle() {
        // TODO add your handling code here:
		try
		{
		double x = Double.parseDouble(centerXTextField.getText());
		double y = Double.parseDouble(centerYTextField.getText());
		double z = Double.parseDouble(centerZTextField.getText());
		double xN = Double.parseDouble(normalXTextField.getText());
		double yN = Double.parseDouble(normalYTextField.getText());
		double zN = Double.parseDouble(normalZTextField.getText());
		Vector3D norm = new Vector3D(xN,yN,zN);
		double radius = Double.parseDouble(radiusTextField.getText());
		Coord3D cen = new Coord3D(x,y,z);
		int r = Integer.parseInt(redTextField.getText())%256;
		int g = Integer.parseInt(greenTextField.getText())%256;
		int b = Integer.parseInt(blueTextField.getText())%256;
		if(r<0) r=0;
		if(g<0) g=0;
		if(b<0) b=0;
		Color col = new Color(r,g,b);
		double ref = Double.parseDouble(reflectivityTextField.getText());
		if(ref>1) ref=1;
		if(ref<0) ref=0;
		canvas1.addCircle(cen,radius,norm,col,ref);	
		}
		catch (NumberFormatException e)
			{
			System.out.println("Error - Invalid number (Colors must be integers):");
			System.out.println(e);
			}		
}                              

    private void addCylinder() {
        // TODO add your handling code here:
		try
		{
		double x = Double.parseDouble(centerXTextField.getText());
		double y = Double.parseDouble(centerYTextField.getText());
		double z = Double.parseDouble(centerZTextField.getText());
		double xN = Double.parseDouble(normalXTextField.getText());
		double yN = Double.parseDouble(normalYTextField.getText());
		double zN = Double.parseDouble(normalZTextField.getText());
		Vector3D norm = new Vector3D(xN,yN,zN);
		double radius = Double.parseDouble(radiusTextField.getText());
		double height = Double.parseDouble(heightTextField.getText());
		Coord3D cen = new Coord3D(x,y,z);
		int r = Integer.parseInt(redTextField.getText())%256;
		int g = Integer.parseInt(greenTextField.getText())%256;
		int b = Integer.parseInt(blueTextField.getText())%256;
		if(r<0) r=0;
		if(g<0) g=0;
		if(b<0) b=0;
		Color col = new Color(r,g,b);
		double ref = Double.parseDouble(reflectivityTextField.getText());
		if(ref>1) ref=1;
		if(ref<0) ref=0;
		canvas1.addCylinder(cen,radius,height,norm,col,ref);	
		}
		catch (NumberFormatException e)
			{
			System.out.println("Error - Invalid number (Colors must be integers):");
			System.out.println(e);
			}		
}                          

    private void addCappedCylinder() {
        // TODO add your handling code here:
		try
		{
		double x = Double.parseDouble(centerXTextField.getText());
		double y = Double.parseDouble(centerYTextField.getText());
		double z = Double.parseDouble(centerZTextField.getText());
		double xN = Double.parseDouble(normalXTextField.getText());
		double yN = Double.parseDouble(normalYTextField.getText());
		double zN = Double.parseDouble(normalZTextField.getText());
		Vector3D norm = new Vector3D(xN,yN,zN);
		double radius = Double.parseDouble(radiusTextField.getText());
		double height = Double.parseDouble(heightTextField.getText());
		Coord3D cen = new Coord3D(x,y,z);
		int r = Integer.parseInt(redTextField.getText())%256;
		int g = Integer.parseInt(greenTextField.getText())%256;
		int b = Integer.parseInt(blueTextField.getText())%256;
		if(r<0) r=0;
		if(g<0) g=0;
		if(b<0) b=0;
		Color col = new Color(r,g,b);
		double ref = Double.parseDouble(reflectivityTextField.getText());
		if(ref>1) ref=1;
		if(ref<0) ref=0;
		canvas1.addCappedCylinder(cen,radius,height,norm,col,ref);	
		}
		catch (NumberFormatException e)
			{
			System.out.println("Error - Invalid number (Colors must be integers):");
			System.out.println(e);
			}		
}

	private void addTrapezoidalCylinder() {
	
	}

    private void addRectPrism() {
        // TODO add your handling code here:
		try
		{
		double x = Double.parseDouble(centerXTextField.getText());
		double y = Double.parseDouble(centerYTextField.getText());
		double z = Double.parseDouble(centerZTextField.getText());
		Coord3D blt = new Coord3D(x,y,z);
		double xWidth = Double.parseDouble(cubeXWidthTextField.getText());
		double yWidth = Double.parseDouble(cubeYWidthTextField.getText());
		double zWidth = Double.parseDouble(cubeZWidthTextField.getText());
		int r = Integer.parseInt(redTextField.getText())%256;
		int g = Integer.parseInt(greenTextField.getText())%256;
		int b = Integer.parseInt(blueTextField.getText())%256;
		if(r<0) r=0;
		if(g<0) g=0;
		if(b<0) b=0;
		Color col = new Color(r,g,b);
		double ref = Double.parseDouble(reflectivityTextField.getText());
		canvas1.addRectPrism(blt,xWidth,yWidth,zWidth,col,ref);
		}
		catch (NumberFormatException e)
			{
			System.out.println("Error - Invalid number (Colors must be integers):");
			System.out.println(e);
			}	
    }
	
	private void addCube() {
        // TODO add your handling code here:
		try
		{
		double x = Double.parseDouble(centerXTextField.getText());
		double y = Double.parseDouble(centerYTextField.getText());
		double z = Double.parseDouble(centerZTextField.getText());
		Coord3D blt = new Coord3D(x,y,z);
		double xWidth = Double.parseDouble(cubeXWidthTextField.getText());
		double yWidth = xWidth;
		double zWidth = xWidth;
		int r = Integer.parseInt(redTextField.getText())%256;
		int g = Integer.parseInt(greenTextField.getText())%256;
		int b = Integer.parseInt(blueTextField.getText())%256;
		if(r<0) r=0;
		if(g<0) g=0;
		if(b<0) b=0;
		Color col = new Color(r,g,b);
		double ref = Double.parseDouble(reflectivityTextField.getText());
		canvas1.addRectPrism(blt,xWidth,yWidth,zWidth,col,ref);
		}
		catch (NumberFormatException e)
			{
			System.out.println("Error - Invalid number (Colors must be integers):");
			System.out.println(e);
			}	
    }

	private void updateActiveTextFields()
	{
	disableAllTextFields();
	if(selectedShape.equals("Rectangular prism")) 
		{
		cubeXWidthTextField.setEnabled(true);
		cubeYWidthTextField.setEnabled(true);
		cubeZWidthTextField.setEnabled(true);		
		}
	else if(selectedShape.equals("Cube"))
		{
		cubeXWidthTextField.setEnabled(true);
		}
	else if(selectedShape.equals("Sphere"))
		{
		radiusTextField.setEnabled(true);
		}
	else if(selectedShape.equals("Circle"))
		{
		radiusTextField.setEnabled(true);
		normalXTextField.setEnabled(true);
		normalYTextField.setEnabled(true);
		normalZTextField.setEnabled(true);
		}
	else if(selectedShape.equals("Cylinder") || selectedShape.equals("Uncapped Cylinder"))
		{
		radiusTextField.setEnabled(true);
		heightTextField.setEnabled(true);
		normalXTextField.setEnabled(true);
		normalYTextField.setEnabled(true);
		normalZTextField.setEnabled(true);
		}
	else if(selectedShape.equals("Trapezoidal Cylinder"))
		{
		/*
		radiusTextField.setEnabled(true);
		innerRadiusTextField.setEnabled(false);
		heightTextField.setEnabled(true);
		normalXTextField.setEnabled(true);
		normalYTextField.setEnabled(true);
		normalZTextField.setEnabled(true);	
*/		
		}
	}
	
	private void disableAllTextFields()
	{
	cubeXWidthTextField.setEnabled(false);
	cubeYWidthTextField.setEnabled(false);
	cubeZWidthTextField.setEnabled(false);
	radiusTextField.setEnabled(false);
	innerRadiusTextField.setEnabled(false);
	heightTextField.setEnabled(false);
	normalXTextField.setEnabled(false);
	normalYTextField.setEnabled(false);
	normalZTextField.setEnabled(false);
	}
	
    private void finishPolygonButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		Coord3D[] coords = new Coord3D[polygonPts.size()];
		for(int i=0; i<polygonPts.size(); i++)
			{
			coords[i] = polygonPts.get(i);
			//System.out.println(coords[i]);
			}
		int r = Integer.parseInt(redTextField.getText())%256;
		int g = Integer.parseInt(greenTextField.getText())%256;
		int b = Integer.parseInt(blueTextField.getText())%256;
		if(r<0) r=0;
		if(g<0) g=0;
		if(b<0) b=0;
		Color col = new Color(r,g,b);
		double ref = Double.parseDouble(reflectivityTextField.getText());
		canvas1.addPolygon3D(coords,col,ref);
		polygonPts.removeAllElements();
		finishPolygonButton.setEnabled(false);
		addVertexButton.setEnabled(false);
		startPolygonButton.setEnabled(true);
		updateCurrentShapes();
		verticesPlacedComboBox.setModel(new javax.swing.DefaultComboBoxModel());
}                               

    private void newDirectionVectorButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
		try
		{
		double x = Double.parseDouble(directionVectorXTextField.getText());
		double y = Double.parseDouble(directionVectorYTextField.getText());
		double z = Double.parseDouble(directionVectorZTextField.getText());
		canvas1.setDirection(x,y,z);
		updateViewSliders();
		updateDirectionFields();
		}
		catch (NumberFormatException e)
			{
			System.out.println("Error - Invalid number:");
			System.out.println(e);
			}	
}

    private void addShapeButtonActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
		if(selectedShape.equals("Rectangular prism")) addRectPrism();
		else if(selectedShape.equals("Cube")) addCube();
		else if(selectedShape.equals("Sphere")) addSphere();
		else if(selectedShape.equals("Circle")) addCircle();
		else if(selectedShape.equals("Cylinder")) addCappedCylinder();
		else if(selectedShape.equals("Uncapped Cylinder")) addCylinder();
		else if(selectedShape.equals("Trapezoidal Cylinder")) addTrapezoidalCylinder();
		updateCurrentShapes();
}      
	
	public void updateDirectionFields()
	{
		Vector3D direction = canvas1.getDirection();
		String xS = Double.toString(direction.getX());
		String yS = Double.toString(direction.getY());
		String zS = Double.toString(direction.getZ());
		if(xS.length() > 5) xS = xS.substring(0,5);
		if(yS.length() > 5) yS = yS.substring(0,5);
		if(zS.length() > 5) zS = zS.substring(0,5);
		directionVectorXTextField.setText(xS);		
		directionVectorYTextField.setText(yS);		
		directionVectorZTextField.setText(zS);		
	}
	
	public void updateViewSliders()
	{
		horizontalViewSlider.setValue(canvas1.getHorizontalSliderValue());
		verticalViewSlider.setValue(canvas1.getVerticalSliderValue());			
	}
	
	public void updateViewpointFields()
	{
	String xS = Double.toString(canvas1.getOrigin().getX());
	String yS = Double.toString(canvas1.getOrigin().getY());
	String zS = Double.toString(canvas1.getOrigin().getZ());
	if(xS.length() > 5) xS = xS.substring(0,5);
	if(yS.length() > 5) yS = yS.substring(0,5);
	if(zS.length() > 5) zS = zS.substring(0,5);
	viewpointXTextField.setText(xS);
	viewpointYTextField.setText(yS);
	viewpointZTextField.setText(zS);
	}
	
	public void updateLightSourceFields()
	{
	String xS = Double.toString(canvas1.getLightSource().getX());
	String yS = Double.toString(canvas1.getLightSource().getY());
	String zS = Double.toString(canvas1.getLightSource().getZ());
	if(xS.length() > 5) xS = xS.substring(0,5);
	if(yS.length() > 5) yS = yS.substring(0,5);
	if(zS.length() > 5) zS = zS.substring(0,5);
	lightSourceXTextField.setText(xS);
	lightSourceYTextField.setText(yS);
	lightSourceZTextField.setText(zS);
	}
	
	
	
	public void start()
	{
	System.out.println("Starting...");
		
	
	canvas1.reset();
            
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
