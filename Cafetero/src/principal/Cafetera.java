package principal;

public class Cafetera {
	private int capacidadMaxima;
	private int capacidadActual;
	private int capacidadTaza;
	private int capacidadActualTaza;
	
//GETTERS Y SETTERS
	
	public int getCapacidadMaxima() {
		return capacidadMaxima;
	}

	public int getCapacidadActual() {
		return capacidadActual++;
	}

	public void setCapacidadMaxima(int capacidadMaxima) {
		this.capacidadMaxima = capacidadMaxima;
	}

	public void setCapacidadActual(int capacidadActual) {
		this.capacidadActual = checkPositivo(capacidadActual);
	}

//CONSTRUCTORES
	
	public Cafetera() {
		capacidadMaxima=1000;
		capacidadActual=0;
	}
	
	public Cafetera(int capacidadMaxima) {
		capacidadActual=capacidadMaxima;
	}
	
	public Cafetera(int capacidadMaxima, int capacidadActual) {
		super();
		this.capacidadMaxima = capacidadMaxima;
		this.capacidadActual = checkPositivo(capacidadActual);
	}
	
//METODOS
	
	public void llenarCafetera() {
		capacidadActual=capacidadMaxima;
	}
	
	public void servirTaza(int capacidadTaza) {
		
		capacidadTaza=checkPositivo(capacidadTaza);
		
		if (checkEmpty(capacidadTaza)) {
			System.out.println("No hay cafe suficiente para llenar tanto la taza.");
			capacidadActualTaza=capacidadActual;
			capacidadActual=0;
		}
		else {
			capacidadActualTaza=capacidadTaza;
			capacidadActual=capacidadActual-capacidadTaza;
		}
	}
	
	public void vaciarCafetera() {
		capacidadActual=0;
	}
	
	public void vaciarTaza() {
		capacidadActualTaza=0;
	}
	
	public void agregarCafe(int agregaCafe) {
		
		agregaCafe=checkPositivo(agregaCafe);
		
		if (checkFull(agregaCafe)) {
			capacidadActual=capacidadMaxima;
			System.out.println("Atencion, el cafe agregado sobrepasa el maximo de capacidad");
		}
		else {
			capacidadActual+=agregaCafe;
		}
	}
	
//METODOS AUXILIARES
	
	public boolean checkFull(int agregaCafe) {
		if(capacidadActual+agregaCafe>capacidadMaxima) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean checkEmpty(int capacidadTaza) {
		if (capacidadActual<capacidadTaza) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public int checkPositivo(int cantidad) {
		if (cantidad>0) {
			return cantidad;
		}
		else {
			System.out.println("Error. El volumen es menor o igual a cero.");
			return 0;
		}
	}
	
	@Override
	public String toString() {
		return "Cafetera [capacidadMaxima=" + capacidadMaxima + ", capacidadActual=" + capacidadActual
				+ ", capacidadTaza=" + capacidadTaza + ", capacidadActualTaza=" + capacidadActualTaza + "]";
	}
	
	//MAIN PRUEBAS
	
	public static void main(String[] Args) {
		Cafetera cafetera = new Cafetera();
		System.out.println("\n"+cafetera);
		cafetera.agregarCafe(-200);
		System.out.println("\n"+cafetera);
		cafetera.agregarCafe(200);
		System.out.println("\n"+cafetera);
		cafetera.servirTaza(250);
		System.out.println("\n"+cafetera);
		cafetera.agregarCafe(2000);
		System.out.println("\n"+cafetera);
		cafetera.llenarCafetera();
		System.out.println("\n"+cafetera);
		cafetera.vaciarCafetera();
		System.out.println("\n"+cafetera);
		cafetera.vaciarTaza();
		System.out.println("\n"+cafetera);
		
	}
}
