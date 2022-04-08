package principal;

import java.io.Serializable;

public class Disparo implements Serializable {
	
	public double disparoX;
	public double disparoY;
	public int disparoAngulo;
	public int disparoTravelled;
	public boolean hasColided=false;
	
	public Disparo(double disparoX, double disparoY, int disparoAngulo) {
		this.disparoX = disparoX;
		this.disparoY = disparoY;
		this.disparoAngulo = disparoAngulo;
		disparoTravelled = 150;
	}
	
}
