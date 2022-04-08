package principal;
import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public String nombre;
	public double naveX;
	public double naveY;
	public int anguloNave;
	public ArrayList <Disparo> disparos = new ArrayList <Disparo>();
	public ArrayList <Explosion> explosiones = new ArrayList <Explosion>();
	public boolean damageTaken;
	public int vidaNave=100;
	public int energiaNave = 100;
	
	public Player(String nombre) {
		this.nombre=nombre;
		naveX = -StarShip.anchoFondo/2;
		naveY = -StarShip.altoFondo/2;
		anguloNave=0;
	}
	public Player(String nombre,double naveX, double naveY) {
		this.nombre=nombre;
		anguloNave=0;
		StarShip.posXFondo = -naveX;
		StarShip.posYFondo = -naveY;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Player [nombre=" + nombre + "]";
	}
}
