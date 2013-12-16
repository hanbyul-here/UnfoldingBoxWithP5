import processing.core.*;
import processing.pdf.*;

public class Unit {

	PApplet p;
	
	PVector pos;
	
	float scale;

	float material; 
	
	float baseAngle;
	float bottomAngle;
	float topAngle;
	
	float angleX;
	float angleTri;
	
	float angle1;
	float angle2;
	float angle3;
	
	PVector downVector;
	
	float topDist;
	float coreDist;
	float bottomDist;
	
	PVector coreV;
	PVector topV;
	PVector bottomV;
	
	PVector topCore;
	PVector topNext;
	
	PVector offsetTop3;
	PVector offsetTop4;
	
	PVector[] top;
	PVector[] left;
	PVector[] right;
	PVector[] front;
	PVector[] back;
	PVector[] bottom;
	
	PGraphics pdf;
	
	
	int rowIndex;
	int colIndex;
	
	String folderName;
	
	public Unit(PApplet p,int scale, int rowIndex, int colIndex,String fName){
	
		this.p = p;
		
		this.material = 1.5f;
		
		this.scale = scale;
		
		this.rowIndex = rowIndex;
		this.colIndex = colIndex;
		
		
		this.pos = new PVector(colIndex*scale, rowIndex*scale);
		
		this.folderName = fName;
		
		
		// it will be better to make another array for saving converted 2d vectors for top z position
		top = new PVector[4];
		left = new PVector[4];
		right = new PVector[4];
		front = new PVector[4];
		back = new PVector[4];
		bottom = new PVector[4];
		
		//String filename = this.getClass().getSimpleName();
		//filename.substring(0,filename.length()-4)
		String filename =this.getClass().getSimpleName();
		filename.substring(0,filename.length()-4);
		String sx = Integer.toString((int)(pos.x/scale));
		String sy = Integer.toString((int)(pos.y/scale));
		
		pdf = p.createGraphics(8*72 , 14*72, PApplet.PDF, folderName +"/"+ filename+ sx +"-"+ sy +".pdf");
	
		downVector = new PVector(0,-1);
	}
	
	
	public void setPosition(){
	
		front[0] = new PVector (0f,0f,0f);
		front[1] = new PVector(scale,0f,0f);
		front[2] = new PVector(scale,0f,top[3].z);
		front[3] = new PVector(scale,0f,top[0].z);
		
		back[0] = new PVector(0f,scale,0f);
		back[1] = new PVector(scale,scale,0f);
		back[2] = new PVector(scale,scale,top[2].z);
		back[3] = new PVector(0,scale,top[1].z);
		
		left[0] = new PVector(0f, 0f, 0f);
		left[1] = new PVector(0f, scale, 0f);
		left[2] = new PVector(0f, scale, top[1].z);
		left[1] = new PVector(0f, 0,  top[0].z);
		
		right[0] = new PVector(scale,0,0);
		right[1] = new PVector(scale,scale,0);
		right[2] = new PVector(scale,top[2].z);
		right[3] = new PVector(scale,0,top[3].z);
		
		setElements();
			
	}
	
	
	private void setElements(){
		
		PVector bottom0 = new PVector(scale*4f, 0);
		PVector top0 = new PVector(scale*4f, top[0].z);
		PVector top00 = new PVector(scale*0f,top[0].z);
		PVector top1 = new PVector(scale*1f, top[1].z);
		PVector top2 = new PVector(scale*2f, top[2].z);
		PVector top3 = new PVector(scale*3f, top[3].z);
		
		
		coreV = PVector.sub(top[2], top[0]);
		topV = PVector.sub(top[1], top[0]);
		bottomV = PVector.sub(top[3], top[0]);
		
		baseAngle = PVector.angleBetween(PVector.sub(bottom0, top0), PVector.sub(top3, top0));
		topAngle = PVector.angleBetween(topV,coreV);
		bottomAngle = PVector.angleBetween(coreV,bottomV);
		
		topDist = PVector.dist(top[1],top[0]);
		coreDist = PVector.dist(top[2],top[0]);
		bottomDist = PVector.dist(top[3],top[0]);
		
		
		
		PVector pv1 = PVector.sub(top1,top00);
		pv1.normalize();
		angle1 = PVector.angleBetween(pv1, downVector);
		
		
		
		PVector pv2 = PVector.sub(top2,top1);
		pv2.normalize();
		angle2 = PVector.angleBetween(pv2, downVector);
		
		PVector pv3 = PVector.sub(top3,top2);
		pv3.normalize();
		angle3 = PVector.angleBetween(pv3, downVector);
		System.out.println(angle1);

		
	}
	
	public void displayModel(){
		
		p.pushMatrix();
		p.translate(pos.x,pos.y,pos.z);
		
		  //front
		  p.fill(255,0,0);
	      p.beginShape(p.QUAD);
	      p.vertex(0f, 0f, 0f);
	      p.vertex(scale, 0f, 0f);
	      p.vertex(scale, 0f, top[3].z);
	      p.vertex(0, 0, top[0].z);
	      p.endShape();
	      
	      
	  //right
	  p.fill(0,0,255);
	  p.beginShape(p.QUAD);
	  p.vertex(scale,0,0);
	  p.vertex(scale,scale,0);
	  p.vertex(scale,scale,top[2].z);
	  p.vertex(scale,0,top[3].z);
	  p.endShape();
	  
	  
	  //left part
	  
	  p.fill(0,255,0);
	  p.beginShape(p.QUAD);
	  p.vertex(0,0,0);
	  p.vertex(0,scale,0);
	  p.vertex(0,scale,top[1].z);
	  p.vertex(0,0,top[0].z);
	  p.endShape();
	  
	  
	  //back part
	  p.fill(0,255,255);
	  p.beginShape(p.QUAD);
	  p.vertex(0,scale,0);
	  p.vertex(scale,scale,0);
	  p.vertex(scale,scale,top[2].z);
	  p.vertex(0,scale,top[1].z);
	  p.endShape();
	  
	  
	  
	  //top part!!!
	  p.fill(255,255,255);
	   p.beginShape(p.QUAD);
	   for(int i=0; i<top.length; i++){
	    p.vertex(top[i].x, top[i].y,top[i].z); 
	   }
	   p.endShape();
	  
	  p.stroke(0,255,0);
	  p.line(top[0].x, top[0].y, top[0].z ,
	              top[2].x, top[2].y, top[2].z);
	  
	  p.stroke(0);
	  p.popMatrix();
		
	}
	
	public void exportToPDF(){
		
		
	//oh ohohoh 

		pdf.beginDraw();
		pdf.background(255);
	//	pdf.fill(255);
		pdf.noFill();
		pdf.strokeWeight(.1f);
	
		
		pdf.pushMatrix();
		pdf.translate(72*5,29);

//		
		topCore = new PVector((float)(scale*4 + Math.cos((3f*Math.PI/2.f) - baseAngle - bottomAngle) * (coreDist - p.sqrt(material*material + material*material))),(float)( top[0].z + Math.sin((3f*Math.PI/2.f) - baseAngle - bottomAngle)* (coreDist - p.sqrt(material*material + material*material))));
		topNext = new PVector((float)( scale*4 + Math.cos((3f*Math.PI/2.f) - baseAngle - bottomAngle - topAngle) * (topDist-material)), (float)(top[0].z + Math.sin((3f*Math.PI/2.f) - baseAngle - bottomAngle -topAngle)* (topDist-material)));
//		
		PVector tempTop3 = new PVector(scale*3,top[3].z);
		PVector tempTop4 = new PVector(scale*4,top[0].z);
//		
		float distBtw34 = PVector.dist(tempTop3,tempTop4);
//		
//	
		offsetTop3 = tempTop3.get();
		offsetTop4 =  tempTop4.get();
//		
		offsetTop3.lerp(tempTop4, material/distBtw34);
		offsetTop4.lerp(tempTop3, material/distBtw34);

		
		
		drawFlap();
		
		etchingPart();
	    pdf.line(tempTop3.x, tempTop3.y, topCore.x, topCore.y);
	    pdf.line(topCore.x, topCore.y,topNext.x,topNext.y);
	    pdf.line(topNext.x,topNext.y,tempTop4.x,tempTop4.y);


	    pdf.line(topCore.x, topCore.y, tempTop4.x,tempTop4.y);
	    

		pdf.beginShape();
		pdf.vertex(scale*3+material,0);
		pdf.vertex(scale*3+material,offsetTop3.y);
		pdf.vertex(scale*4-material,offsetTop4.y);
		pdf.vertex(scale*4-material,0);
		pdf.endShape();
		
		
		cutPart();
		
		pdf.line(scale*3,0,scale*4,0);
		pdf.line(offsetTop3.x,offsetTop3.y,tempTop3.x,tempTop3.y);
		pdf.line(offsetTop4.x,offsetTop4.y,tempTop4.x,tempTop4.y);
		
		drawWing();
		

		String sx = Integer.toString((int)(pos.x/scale));
		String sy = Integer.toString((int)(pos.y/scale));
		
		int intsx = (int)(pos.x/scale);
		int intsy = (int)(pos.y/scale);
		

		if(top[3].z>12f) drawNumber(intsx,intsy);
		
	    pdf.popMatrix();
		
		pdf.endDraw();
		pdf.dispose();
		pdf.flush();
		


		
		System.out.println("heew, exporting for " + sx + " - " + sy+ " done");
	}
	
	
	
	private void drawNumber(int x, int y ){
		boolean becomeOne = false;
		
		if(x > 9){
			becomeOne = true;
		}
		
		pdf.noFill();
		pdf.stroke(0,0,255);
		pdf.strokeWeight(0.1f);
		pdf.pushMatrix();
		pdf.translate(scale*3.8f, 0.3f*72);
		pdf.rotate((float)Math.PI);
		
		
		if(becomeOne) pdf.shape(Number.getNumber(1),0,0,7,12);
		//else pdf.shape(Number.getNumber(0),0,0,7,12);
		
		if(becomeOne) pdf.shape(Number.getNumber(x-10),15,0,7,12);
		else pdf.shape(Number.getNumber(x),15,0,7,12);
		
		pdf.shape(Number.getNumber(0),30,10,3,3);
		
		pdf.shape(Number.getNumber(y),40,0,7,12);
		//pdf.//text(sx+"-"+sy,0,0);
		pdf.popMatrix();
	    
	}
	
	
	
	
	private void drawWing(){
		//wing
		cutPart();
		
		//float slope = top[0].z/10.f;
		
		//pdf.line(-(float)scale/2.5f, slope, 0, 0);
		//pdf.line(-(float)scale/2.5f, slope,-(float)scale/2.5f, top[0].z - slope );
		//pdf.line(0, top[0].z - material, -(float)scale/2.5f, top[0].z - slope);
		
		
		
		PVector[] wings = new PVector[6];
		
		PVector tempTop3 = new PVector(scale*3,top[3].z);
		PVector tempTop4 = new PVector(scale*4,top[0].z);
		
		PVector topCoreTemp = topCore.get();
		PVector topNextTemp = topNext.get();
		
		float distBtwnc = PVector.dist(topCore,topNext);

		topCoreTemp.lerp(topNext, material/distBtwnc);
		topNextTemp.lerp(topCore, material/distBtwnc);
		
		//float angleX = PVector.angleBetween(PVector.sub(topCore,tempTop3), PVector.sub(topCore,tempTop4));
		float angleTri = PVector.angleBetween(PVector.sub(topCore,tempTop4),PVector.sub(topCore,topNext));
		
		float wingScale = 50f;

		
		wings[0] =  PVector.sub(topCore,tempTop3);
		wings[0].normalize();
		wings[0].rotate((float)Math.PI-angle3);
		wings[0].mult(top[2].z);
		wings[0].add(topCore);
		
		wings[1] =  PVector.sub(topCore,tempTop3);
		wings[1].normalize();
		wings[1].rotate((float)Math.PI-angle3);
		wings[1].mult(top[3].z);
		wings[1].add(tempTop3);
		
		
		float flapScale = 50f;
		
		wings[2] =  PVector.sub(topNextTemp,topCoreTemp);
		wings[2].normalize();
		wings[2].rotate((float)Math.PI-angle2);
		
		PVector flap0 = wings[2].get();
		flap0.rotate(-(float)Math.PI*1/8f);
		flap0.mult(flapScale);
		flap0.add(topNextTemp);
		
		PVector flap1 = wings[2].get();
		flap1.normalize();
		flap1.rotate(-7f*(float)Math.PI/8f);	
		flap1.mult(flapScale);	
		
		wings[2].mult(top[1].z);
		wings[2].add(topNextTemp);
		
	
		flap1.add(wings[2]);
		
		
		wings[3] =  PVector.sub(topNextTemp,topCoreTemp);
		wings[3].normalize();
		wings[3].rotate((float)Math.PI-angle2);
		
		PVector flap2 = wings[3].get();
		flap2.rotate(1f*(float)Math.PI/8f);	
		flap2.mult(flapScale);	
		flap2.add(topCoreTemp);
		
		
		PVector flap3 = wings[3].get();
		flap3.normalize();
		flap3.rotate(7f*(float)Math.PI/8f);
		flap3.mult(flapScale);
		
		wings[3].mult(top[2].z);
		wings[3].add(topCoreTemp);
		

		flap3.add(wings[3]);

		/*
		float angleBtw = PVector.angleBetween(PVector.sub(topCore, new PVector(scale * 3f,top[3].z)),PVector.sub(new PVector(scale*2f,top[2].z),new PVector(scale * 3f,top[3].z)));
		angleBtw = p.map(angleBtw,0f,(float)Math.PI,0.7f,1.8f);
		
		wings[2] =  PVector.sub(new PVector(scale*(3),top[3].z),new PVector(scale*2f,top[2].z));
		wings[2].normalize();
		wings[2].rotate(angleX);
		wings[2].mult(wingScale*angleBtw);
		wings[2].add(new PVector(scale*2,top[2].z));
		*/
		
		wings[4] =  PVector.sub(topNext,tempTop4);
		wings[4].normalize();
		wings[4].rotate(-angle1);
		wings[4].mult(top[0].z);
		wings[4].add(tempTop4);		

		
		wings[5] =  PVector.sub(topNext,tempTop4);
		wings[5].normalize();
		wings[5].rotate(-angle1);
		wings[5].mult(top[1].z);
		wings[5].add(topNext);		

		
		
		//flap part
		
		
		pdf.stroke(255,0,0);
		pdf.beginShape();

		pdf.vertex(topNextTemp.x,topNextTemp.y);
		pdf.vertex(flap0.x , flap0.y);
		pdf.vertex(flap1.x , flap1.y);
		pdf.vertex(wings[2].x , wings[2].y);

		pdf.endShape();
		
		
		pdf.beginShape();

		pdf.vertex(topCoreTemp.x,topCoreTemp.y);
		pdf.vertex(flap2.x , flap2.y);
		pdf.vertex(flap3.x , flap3.y);
		pdf.vertex(wings[3].x , wings[3].y);

		pdf.endShape();
		
		
		
		// body part
		
		
		pdf.beginShape();
		pdf.vertex(topCore.x , topCore.y);
		pdf.vertex(wings[0].x , wings[0].y);
		pdf.vertex(wings[1].x , wings[1].y);
		pdf.vertex(tempTop3.x , tempTop3.y);
		pdf.endShape();
		
		
		
		
		//pdf.beginShape();
		
		etchingPart();

		pdf.line(topNextTemp.x, topNextTemp.y, wings[2].x , wings[2].y);
		pdf.line(topCoreTemp.x, topCoreTemp.y, wings[3].x , wings[3].y);
		
		cutPart();

		pdf.line(wings[2].x , wings[2].y,wings[3].x , wings[3].y);
		
	
		pdf.beginShape();
		pdf.vertex(tempTop4.x , tempTop4.y);
		pdf.vertex(wings[4].x , wings[4].y);
		pdf.vertex(wings[5].x , wings[5].y);
		pdf.vertex(topNext.x , topNext.y);

		pdf.endShape();
		
		cutPart();
		pdf.line(topCore.x, topCore.y, topCoreTemp.x, topCoreTemp.y);
		pdf.line(topNext.x, topNext.y, topNextTemp.x, topNextTemp.y);
		
		


		
	}
	
	
	private void drawFlap(){
		
		float slope = top[3].z/10.f;
		cutPart();
		pdf.line(-(float)scale/2.5f + scale*3+material, slope, scale*3+material, 0);
		pdf.line(-(float)scale/2.5f+ scale*3+material, slope, -(float)scale/2.5f+ scale*3+material, offsetTop3.y - slope);
		pdf.line(-(float)scale/2.5f+ scale*3+material, offsetTop3.y - slope, offsetTop3.x, offsetTop3.y);
		
		
		pdf.line(+(float)scale/2.5f + scale*4-material, slope, scale*4-material, 0);
		pdf.line(+(float)scale/2.5f+ scale*4-material, slope, (float)scale/2.5f+ scale*4-material, offsetTop4.y - slope);
		pdf.line(+(float)scale/2.5f+ scale*4-material, offsetTop4.y - slope, offsetTop4.x, offsetTop4.y);
	
		
	}
	
	public void getFolderName(String s){
		folderName = s;
		
		String filename =this.getClass().getSimpleName();
		filename.substring(0,filename.length()-4);
		String sx = Integer.toString((int)(pos.x/scale));
		String sy = Integer.toString((int)(pos.y/scale));
		pdf = p.createGraphics(20*72 , 20*72, PApplet.PDF, folderName +"/"+ filename+ sx +"-"+ sy +".pdf");

	}	
	private void cutPart(){
		pdf.stroke(255,0,0);
	}
	
	private void etchingPart(){
		pdf.stroke(0,0,255);
	}
	
	public float getScale(){
		return scale;
	}
	
}
