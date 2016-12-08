
import java.util.Scanner;
import java.io.*;

public class NBtoNP {
	
	public static void main(String args[])
	{
	String NBsrc = "DF.java";
	String trg = "LevelSetApplet.java";
	String save = "LevelSetAppletSave.txt";
	
	//******************************  Read in from NetBeans
	
	Scanner in = null;
	try {
	in = new Scanner(new File("E:/NetBeansProjects/FractalGUI/src/" + NBsrc));
	} catch (Exception e)
	    {
		System.err.println(e);
		System.exit(1);
	    }
		
	if(in != null)
		{
		StringBuffer declBuffer = new StringBuffer();
		StringBuffer initBuffer = new StringBuffer();
		StringBuffer layoutBuffer = new StringBuffer();
		boolean declarations = false;
		boolean initializations = false;
		boolean layout = false;
		
		while(in.hasNextLine())
		    {
			String line = in.nextLine() + "\n";
			if(line.contains("private void initComponents()")) 
				{
				initializations = true;
				in.nextLine();
				line = in.nextLine() + "\n";
				//System.out.println(line);
				while(initializations)
					{
					initBuffer.append(line);
					line = in.nextLine() + "\n";
					if(line.contains("canvas1")) line = in.nextLine() + "\n";
					if(line.contains("colorCanvas")) line = in.nextLine() + "\n";
					if(!line.contains("=")) initializations = false;
					}
				//System.out.println(initBuffer);
				}
				
			if(line.contains("// Variables declaration")) 
				{
				declarations = true;
				while(declarations)
					{
					declBuffer.append(line);
					line = in.nextLine() + "\n";
					if(line.contains("canvas1")) line = in.nextLine() + "\n";
					if(line.contains("colorCanvas")) line = in.nextLine() + "\n";
					line = line.replace("private","");
					if(line.contains("// End of variables declaration")) 
						{
						declarations = false;
						declBuffer.append(line);
						}
					}
				//System.out.println(declBuffer);
				}
				
			if(line.contains("layout.setHorizontalGroup")) 
				{
				layout = true;
				while(layout)
					{
					layoutBuffer.append(line);
					line = in.nextLine() + "\n";
					if(line.contains("}// </editor-fold>//GEN-END:initComponents")) 
						{
						layout = false;
						layoutBuffer.append(line);
						}
					}
				//System.out.println(layoutBuffer);
				}
		    }
			
		// ****************************** save a copy
		StringBuffer textBuffer = new StringBuffer();
		in = null;
		try {
		in = new Scanner(new File(trg));
		} catch (Exception e)
		    {
			System.err.println(e);
			System.exit(1);
		    }
		while(in.hasNextLine())
		    {
			String line = in.nextLine() + "\n";
			textBuffer.append(line);
		    }
		
		String text = textBuffer.toString();
		
		File saveF = new File(save);

		if (saveF.exists()){
			try{
			FileOutputStream fop = new FileOutputStream(saveF);
		    fop.write(text.getBytes());
		    
		    fop.flush();
		    fop.close();
			}
			catch (Exception e)
				{
				System.out.println("Problem writing to file");
				System.out.println(e);
				}
			    
			    		}
		else System.out.println("File does not exist");
	
	
	
		// ****************************** make new version
		in = null;
		try {
		in = new Scanner(new File(trg));
		} catch (Exception e)
		    {
			System.err.println(e);
			System.exit(1);
		    }
			
		declarations = false;
		initializations = false;
		layout = false;
		textBuffer = new StringBuffer();
		
		while(in.hasNextLine())
		    {
			String line = in.nextLine() + "\n";
			if(line.contains("private void initComponents()")) 
				{
				initializations = true;
				textBuffer.append(line);
				line = in.nextLine() + "\n";
				textBuffer.append(line);
				line = in.nextLine() + "\n";
				//System.out.println(line);
				while(initializations)
					{
					line = in.nextLine() + "\n";
					if(!line.contains("=")) initializations = false;
					}
				textBuffer.append(initBuffer.toString());
				}
				
			if(line.contains("// Variables declaration")) 
				{
				declarations = true;
				while(declarations)
					{
					line = in.nextLine() + "\n";
					if(line.contains("// End of variables declaration")) 
						{
						declarations = false;
						line = in.nextLine() + "\n";
						}
					}
				textBuffer.append(declBuffer.toString());
				}
				
			if(line.contains("layout.setHorizontalGroup")) 
				{
				layout = true;
				while(layout)
					{
					line = in.nextLine() + "\n";
					if(line.contains("}// </editor-fold>")) 
						{
						layout = false;	
						line = in.nextLine() + "\n";					
						}
					}
				textBuffer.append(layoutBuffer.toString());
				}
				
			textBuffer.append(line);
		    }
		
		text = textBuffer.toString();
		//System.out.println(text);
		
		
		File update = new File(trg);

		if (update.exists()){
			try{
			FileOutputStream fop = new FileOutputStream(update);
		    fop.write(text.getBytes());
		    
		    fop.flush();
		    fop.close();
			}
			catch (Exception e)
				{
				System.out.println("Problem writing to file");
				System.out.println(e);
				}
			    
			    		}
		else System.out.println("File does not exist");
		
		
		}
	
	}
	
	
}