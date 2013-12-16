import java.io.PrintWriter;

import processing.core.*;
import processing.pdf.*;

public class Mesh {

	PApplet p;
	Unit[][] units;
	
	int scale;
	int zScale;
	float currScale;
	
	int xNum;
	int yNum;
	
	int gap;
	int edgeGap;
	int material;
	
	float zOffset;
	PrintWriter output;
	
	PGraphics pdf;
	
	String folderName;
	
	static public float noiseOffsetX=12.750004f,noiseOffsetY=1.799995f;

	
	public Mesh(PApplet p,int xNum,int yNum,String fName){
		noiseOffsetX+=0.1f;
		
		this.p = p;
		this.xNum = xNum;
		this.yNum = yNum;
		this.folderName = fName;
		
		this.zOffset = 140f;
		
		
		// 72px -> 1in
		this.scale = 72;
		this.zScale = 750;
		
		this.gap = 10;
		this.edgeGap = (int) Math.floor(0.2f *scale);
		this.material = 1;
		
		currScale = 0.105f;
		
		units = new Unit[yNum][xNum];
		
		for(int y = 0; y < yNum; y++){
			for(int x = 0; x < xNum; x++){
				units[y][x] = new Unit(p,scale,y,x,fName);		
				
				float currZ = p.noise(x*currScale,y*currScale, currScale);	
				
				//currZ = p.noise(x*currScale, y*currScale, currScale);
		    	
//		    	units[y][x].top[0] = new PVector (0, 0, currZ*zScale- zOffset);
//	   			units[y][x].top[1] = new PVector(0, scale, p.noise(x*currScale, (y+1)*currScale,currScale)*zScale- zOffset);
//	   			units[y][x].top[2] = new PVector(scale,scale , p.noise((x+1)*currScale, (y+1)*currScale, currScale)*zScale- zOffset);
//				units[y][x].top[3] = new PVector(scale, 0, p.noise((x+1)*currScale, y*currScale, currScale)*zScale- zOffset);
		    	units[y][x].top[0] = new PVector (0, 0, getNoise(x,y));
	   			units[y][x].top[1] = new PVector(0, units[y][x].getScale(), getNoise(x,y+1));
	   			units[y][x].top[2] = new PVector(units[y][x].getScale(),units[y][x].getScale() ,getNoise(x+1,y+1));
				units[y][x].top[3] = new PVector(units[y][x].getScale(), 0, getNoise(x+1,y));
				units[y][x].setPosition();
				
			}
		}
		
		
			
	}
	
	
	
	public float getNoise(float x,float y) {
		
		float val=p.noise(x*currScale+noiseOffsetX, y*currScale+noiseOffsetY, currScale);
//		val=p.sin(val*p.HALF_PI);
//		if(val<=0.5f) val=p.sq(p.sq(val*2))*0.5f;
//		else {
////			val=(val-0.5f)*2;
////			val=1-p.sq(1-val);
//		}
		
		return val*zScale- zOffset;
	}
	
	public void display(){
		
		for(int y = 0; y < yNum; y++){
			for(int x = 0; x < xNum; x++){
				units[y][x].displayModel();
				
			}
		}
	}
	
	public void exportPDF(){
		for(int y = 0; y < yNum; y++){
			for(int x = 0; x < xNum; x++){
				units[y][x].exportToPDF();
				
			}
		}
	}
	
	public void exportBottmPDF(){
		
		int bottomWidth = (scale+material*6)*xNum + gap*(xNum-1) + edgeGap*2;
		int bottomHeight = (scale+material*4)*yNum + gap*(yNum-1) + edgeGap*2;
		
		pdf.beginDraw();
		pdf.noFill();
		pdf.stroke(255,0,0);
		pdf.pushMatrix();
		pdf.translate(10, 10);
		pdf.rect(0,0,bottomWidth,bottomHeight);
		
		for(int j = 0; j < yNum; j++){
			for(int i = 0; i < xNum; i++){
				
				pdf.rect(edgeGap + i*(scale+material*6+gap), edgeGap + j*(scale+material*4+gap), scale+material*6 ,scale + material*4 );
				
			}
		}
		
		pdf.popMatrix();
		pdf.endDraw();
		
		pdf.dispose();
		pdf.flush();
		System.out.println("bottom part export done");
		
	}
	
	
	public void detailExport(){
		output = p.createWriter( folderName + "/noiseOffsets.txt"); 
		output.println("x noiseOffset : " + Mesh.noiseOffsetX  + " Y noiseOffset : " + Mesh.noiseOffsetY );
		output.println("scale : "+ scale + " currScale : " + currScale + " zScale : " + zScale+ " zOffset : "+zOffset);
		
		output.flush();
		output.close();
	}
	
	public void getFolderName(String s){
		this.folderName = s;
		pdf = p.createGraphics(24*72 , 18*72, PApplet.PDF, folderName +"/bottomPart.pdf");
		for(int j = 0; j < yNum; j++){
			for(int i = 0; i < xNum; i++){
				
				units[j][i].getFolderName(s);
				
			}
		}
		
	}
	
}
