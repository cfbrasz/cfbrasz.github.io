import javax.swing.JApplet;
import java.awt.Graphics;
import java.util.Vector;
import java.awt.event.*;
import java.awt.*;
import java.awt.image.PixelGrabber;
import java.text.*;
import java.util.Scanner;
import java.io.File;
import javax.imageio.*;
import java.util.Vector;
import java.lang.Math;
import java.util.Random;
import javax.swing.event.*;
import javax.swing.*;
import java.net.URL;
import java.awt.image.BufferedImage;
import java.io.*;

public class GenerateStage {
		
	Color bgColor = Color.BLACK;
	static boolean bmp = false; //load bmp instead of jpg (doesn't work)
	static int stageN;
	static String fileName;
	static String stageFileName;
	static String stageFileNameJava;
	BufferedImage img;
	int w;
	int h;
	int pixW;
	int pixH;
	int pixelsWide;
	int pixelsHigh;
	//int maxPixelsHigh = 21;
	static int maxPixelsHigh = 40;
	int r[][];
	int g[][];
	int b[][];
	int r2[];
	int g2[];
	int b2[];
	int count[][];
	int rgbs[];
	//double pixW = (double)w/Constants.blocksPerRow;
	//double pixH = (double)h/Constants.blocksPerRow;
	static int blocksPerRowCurrent;
	static double heightWidthRatio;
	
	
	public GenerateStage()
	{
	loadImage();
	
	fillColorArrays();
	String textData = "import java.awt.*;\n\n";
	textData = textData + "public interface " + stageFileName + " {\n\n";
	textData = textData + "public static final int nBlocks = " + pixelsWide*pixelsHigh +";\n\n";
	
	textData = textData + "public static final int blocksPerRow = " + blocksPerRowCurrent + ";\n";
	textData = textData + "public static final double heightWidthRatio = " + heightWidthRatio + ";\n";	
	textData = textData + "public static final Color bgColor = new Color(" + bgColor.getRed() + "," + bgColor.getGreen() + "," + bgColor.getBlue() + ");\n\n";
	
	textData = textData + "public static final int[] r = {"; 	
	for(int i=0; i<pixelsWide*pixelsHigh; i++)
		{
		if(i>0) textData = textData + ",";
		textData = textData + r2[i];
		}
	textData = textData + "};\n";
	
	textData = textData + "public static final int[] g = {"; 	
	for(int i=0; i<pixelsWide*pixelsHigh; i++)
		{
		if(i>0) textData = textData + ",";
		textData = textData + g2[i];
		}
	textData = textData + "};\n";
	
	textData = textData + "public static final int[] b = {"; 	
	for(int i=0; i<pixelsWide*pixelsHigh; i++)
		{
		if(i>0) textData = textData + ",";
		textData = textData + b2[i];
		}
	textData = textData + "};\n";	
	
	textData = textData + "\n}";			
		
		
	File stageFile = new File(stageFileNameJava);
	//Update the high Score

	if (!stageFile.exists()) 
	{
	try {
		stageFile.createNewFile();
		} 
	catch (IOException e) 
		{  
		e.printStackTrace();  
		}  
	}
	else System.out.println("File already exists, will be overwritten");
		  
	
	try {
		FileOutputStream fop = new FileOutputStream(stageFile);
			  
	try {
	    fop.write(textData.getBytes());
	    
	    fop.flush();
	    fop.close();
		} 
	catch (IOException e) 
		{  
		e.printStackTrace();  
		}  
		
		}
	catch (FileNotFoundException e) 
		{  
		e.printStackTrace();  
		}  
	}
	
	public void loadImage()
	{		
	img = null;
	try {
	    img = ImageIO.read(new File(fileName));
	} catch (IOException e) {
	}
		  
	w = img.getWidth();
	h = img.getHeight();
	System.out.println(w);
	System.out.println(h);
	 pixW = w/blocksPerRowCurrent; //width of megapixel
	 pixH = (int)(pixW*heightWidthRatio); //height of megapixel
	System.out.println(pixW);
	System.out.println(pixH);
	 pixelsWide = blocksPerRowCurrent;
	 pixelsHigh = h/pixH;
	System.out.println(pixelsWide);
	System.out.println(pixelsHigh);
	if(pixelsHigh > maxPixelsHigh) pixelsHigh = maxPixelsHigh;
	 r = new int[pixelsWide][pixelsHigh];
	 g = new int[pixelsWide][pixelsHigh];
	 b = new int[pixelsWide][pixelsHigh];
	 r2 = new int[pixelsWide*pixelsHigh];
	 g2 = new int[pixelsWide*pixelsHigh];
	 b2 = new int[pixelsWide*pixelsHigh];
	 count = new int[pixelsWide][pixelsHigh];
	
	rgbs = new int[w*h];
	PixelGrabber pg = new PixelGrabber(img, 0, 0, w, h, rgbs, 0, w);
	try {
	    pg.grabPixels();
	} catch (InterruptedException e) {
	    System.err.println("interrupted waiting for pixels!");
	    return;
	}  	
	}	
	
	public void fillColorArrays()
	{
	for(int i=0; i<w; i++)
		{
		for(int j=0; j<h; j++) 
			{
			Color cCur = new Color(rgbs[i+j*w]);
			int iC = i/pixW;
			int jC= j/pixH;
			int rC = cCur.getRed();
			int gC = cCur.getGreen();
			int bC = cCur.getBlue();
			if(iC < pixelsWide && jC < pixelsHigh)
				{
				r[iC][jC]+=rC;
				g[iC][jC]+=gC;
				b[iC][jC]+=bC;
				count[iC][jC]++;
				}
			}
		}
	
	for(int i=0; i<pixelsWide; i++)
		{
		for(int j=0; j<pixelsHigh; j++)
			{
			if(count[i][j] > 0)
				{
				r[i][j]=r[i][j]/count[i][j];
				g[i][j]=g[i][j]/count[i][j];
				b[i][j]=b[i][j]/count[i][j];
				}
			else System.out.println("i,j have 0 count: " + i + ", " + j);
			}
		}
	
	for(int i=0; i<pixelsWide; i++)
		{
		for(int j=0; j<pixelsHigh; j++)
			{
			r2[i+j*pixelsWide]=r[i][j];
			g2[i+j*pixelsWide]=g[i][j];
			b2[i+j*pixelsWide]=b[i][j];
			}
		}
	}
	
	
	public static void main(String args[])
	{
	//usage: stageN, blocksPerRow, heightWidthRatio, maxPixelsHigh
	blocksPerRowCurrent = 11; //default
	heightWidthRatio = 0.5; //default
	
	
	
	stageN = 1;
	if(args.length > 0)
		{
		stageN = Integer.valueOf(args[0]);
		}
	if(args.length > 1)
		{
		blocksPerRowCurrent = Integer.valueOf(args[1]);
		}
	if(args.length > 2)
		{
		heightWidthRatio = Double.valueOf(args[2]);
		}
	if(args.length > 3)
		{
		maxPixelsHigh = Integer.valueOf(args[3]);
		}
	if(!bmp) fileName = "image" + stageN + ".jpg";
	else fileName = "image" + stageN + ".bmp";
	stageFileName = "GenStage" + stageN;
	stageFileNameJava = "GenStage" + stageN + ".java";
	new GenerateStage();		    
	}

}