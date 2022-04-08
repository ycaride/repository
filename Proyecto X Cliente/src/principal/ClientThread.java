package principal;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientThread extends Thread {
	private String ip;
	private int port;
	private boolean swap=false;
	public ClientThread(int port, String ip) {
		this.port = port;
		this.ip = ip;
	}

	@Override
	public void run() {		
//		Variables red
		Socket socket = null;
		ObjectOutputStream objOut = null;
		ObjectInputStream objIn = null;
		
		while(true) {
			try {
				socket = new Socket(ip , port);
				OutputStream outputStream = socket.getOutputStream();
				InputStream inputStream = socket.getInputStream();
				objOut = new ObjectOutputStream(outputStream);
				objIn = new ObjectInputStream(inputStream);
				socket.setTcpNoDelay(true);
				while(true) {
//				ENVIO Y RECEPCION DE OBJETOS (CLIENTE)
					objOut.writeObject(Game.player);
//					System.out.println("player enviado...");
					objOut.reset();
					
					try {
						Game.players = (ArrayList <Player>) objIn.readObject();
//						System.out.println("Matriz de Players recibida...");
					} catch (ClassNotFoundException e) {
						System.out.println("Fallo al cargar la Matriz de players...");
						e.printStackTrace();
					}	

					
//				CHECK DE RECEPCION POR CONSOLA
//					System.out.println("Tamaño de la matriz"+Game.players.size());
					for(int i=0;i<Game.players.size();i++) {
//						System.out.println(Game.players.get(i).toString());
					}
					
//				SLEEP HILO IO
					try {
						this.sleep(5);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			} catch (IOException e) {
				System.out.println("La conexion con el servidor se ha perdido...");
//				e.printStackTrace();
			}finally {
				try
				{
					if (objOut != null)
					{
						objOut.close();
					}
					if (objIn != null)
					{
						objIn.close();
					}
					if (socket != null)
					{
						socket.close();
					}
				}
				catch (IOException e)
				{
//					e.printStackTrace();
				}
				try {
					System.out.println("Reinciando conexion en 5 segundos...");
					this.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Reiniciando conexion...");
			}
		}
		
	}
}
