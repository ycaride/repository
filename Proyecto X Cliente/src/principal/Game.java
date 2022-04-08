package principal;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
	static Player player;
	static ArrayList <Player> players =new ArrayList<>();
	public static String nombre;
	public static StarShip starShip;
	
	public static void main(String [] args) {
		String ip;
		Scanner sc = new Scanner(System.in);
		System.out.println("Introduce un nombre:");
		nombre = sc.nextLine();
		System.out.println("Introduce la IP:");
		ip = sc.nextLine();
//		String nombre = "Seraphim";
		int port = 6666;
		player= new Player(nombre);
		sc.close();
		
		ClientThread clientThread =new ClientThread(port, ip);
		clientThread.start();
		starShip = new StarShip();
		starShip.start();

	}
}
