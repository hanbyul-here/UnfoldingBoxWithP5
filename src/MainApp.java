import java.io.*;
import processing.core.*;
import processing.opengl.*;
import peasy.*;



public class MainApp extends PApplet{

	Mesh mesh;
	PeasyCam cam;
	
	float material;
	
	String folderName;
	String currentPath;
	String folderPath;
	
	
	
	static Number number;
	
	boolean folderCreated;
	
	public static void main(String[] args) {
		
		PApplet.main(new String[] {"MainApp"});
		
	}
	
	public void setup(){
	
		size(1200,800,OPENGL);
		noiseSeed(1);
		
		number = new Number(this);
		

		
		cam = new PeasyCam(this, 1000);
		cam.setMinimumDistance(50);
		cam.setMaximumDistance(5000);
		 
		//railroad board : less than 1/16 in.. 
		material = 4;		

	//	folderName = "scape_d_" + Integer.toString(day()) +"_h_"+Integer.toString(hour()) +"_m_" + Integer.toString(minute()) +"_s_"+ Integer.toString(second());
//		currentPath = System.getProperty("user.dir");
		
//		folderCreated = false;
		
		
		mesh = new Mesh(this,15,8,folderName);
	
	}
	
	public void draw(){
		
		lights();
		background(0);
		mesh.display();
		
	}
	
	
	public void keyPressed(){
	
		if(key=='y') {
			Mesh.noiseOffsetY+=0.05f;
		}
		if(key=='x') {
			Mesh.noiseOffsetX+=0.05f;
		}
		if(key =='X'){
			Mesh.noiseOffsetX-=0.05f;
			
		}
		if(key == 'Y'){
			Mesh.noiseOffsetY-=0.05f;
		}
		if(key=='x' || key=='y' || key=='X' ||key=='Y' ) mesh = new Mesh(this,17,10,folderName);
		
		
		if(keyCode==' '){

			folderName = "scape_d_" + Integer.toString(day()) +"_h_"+Integer.toString(hour()) +"_m_" + Integer.toString(minute()) +"_s_"+ Integer.toString(second());
			currentPath = System.getProperty("user.dir");
			
			try{
					 boolean success = (new File(currentPath)).mkdir();
					 if (success)   println("Directory: " + folderName + " created");
			}catch (Exception e){//Catch exception if any
					System.err.println("Error: " + e.getMessage());
			}
			
			mesh.getFolderName(folderName);
			mesh.exportPDF();
			mesh.detailExport();
			mesh.exportBottmPDF();
			

		}
		if(key=='f') saveFrame(folderName + "/front.png");
		if(key=='b') saveFrame(folderName + "/back.png");
		if(key=='l') saveFrame(folderName + "/left.png");
		if(key=='r') saveFrame(folderName + "/right.png");
		if(key=='t') saveFrame(folderName + "/top.png");
		if(key=='B') saveFrame(folderName + "/bigPicture.png");
	}
	
}
