package principal;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyKeyAdapter implements KeyListener{

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_A:
			StarShip.teclaA=true;
			break;
		case KeyEvent.VK_D:
			StarShip.teclaD=true;
			break;
		case KeyEvent.VK_W:
			StarShip.teclaW=true;
			break;
		case KeyEvent.VK_S:
			StarShip.teclaS=true;
			break;
		case KeyEvent.VK_Q:
			StarShip.teclaQ=true;
			break;
		case KeyEvent.VK_E:
			StarShip.teclaE=true;
			break;
		case KeyEvent.VK_P:
			if (Game.player.vidaNave<=0 && StarShip.vidas>0) {
				StarShip.vidaRestada = false;
				Game.player = new Player(Game.nombre, Math.random() * (StarShip.anchoFondo-Game.starShip.getANCHO() - 1) + 1, Math.random() * (StarShip.altoFondo-Game.starShip.getALTO() - 1) + 1 );
				
			}
			break;
		

		case KeyEvent.VK_SPACE:
			StarShip.teclaEspacio=true;


		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_A:
			StarShip.teclaA=false;
			break;
		case KeyEvent.VK_D:
			StarShip.teclaD=false;
			break;
		case KeyEvent.VK_W:
			StarShip.teclaW=false;
			break;
		case KeyEvent.VK_S:
			StarShip.teclaS=false;
			break;
		case KeyEvent.VK_Q:
			StarShip.teclaQ=false;
			break;
		case KeyEvent.VK_E:
			StarShip.teclaE=false;
			break;
			
		case KeyEvent.VK_SPACE:
			StarShip.teclaEspacio=false;
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
