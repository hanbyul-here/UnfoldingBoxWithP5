import processing.core.*;

public class Number {

	
	static PShape[] numbers;
	PApplet p;
	
	public Number(PApplet p){
		
		this.p = p;
		numbers = new PShape[10];
	
		for(int i=0; i<10; i++){
			numbers[i] = p.loadShape( i +".svg");
			numbers[i].disableStyle();
			
		}
	
	}
	
	
	public static PShape getNumber(int i){
		
		return numbers[i];
	
	}
	
}
