package principal;

import java.awt.Color;


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;



public class StarShip extends Thread {
	
	 BufferedImage navePropia;
	 BufferedImage navePropia1;
	 BufferedImage navePropia2;
	 BufferedImage navePropia3;
	 BufferedImage disparo;
	 BufferedImage disparo1;
	 BufferedImage disparo2;
	 BufferedImage disparo3;
	 BufferedImage explosion;
	 BufferedImage explosion1;
	 BufferedImage explosion2;
	 BufferedImage explosion3;
	 BufferedImage fondo;
	 BufferedImage marcadore;
	 BufferedImage vida;
	 BufferedImage xocas;

	static boolean teclaA=false;
	static boolean teclaD=false;
	static boolean teclaW=false;
	static boolean teclaS=false;
	static boolean teclaQ=false;
	static boolean teclaE=false;
	static boolean teclaEspacio=false;
	
	private int ALTO = 768;
	private int ANCHO = 1366;
//	private int ALTO = 1080;
//	private int ANCHO = 1920;
	private int UNIT_SIZE=72;
	private int DISP_SIZE=24;
	
	private int posXNavePantalla = ANCHO/2 - UNIT_SIZE/2;
	private int posYNavePantalla = ALTO - UNIT_SIZE*2;
	static double posXFondo = 0;
	static double posYFondo = 0;
	
	public static int anchoFondo = 3840;
	public static int altoFondo = 2160;
	public int naveSpeed = 3;
	private GamePanel painter;
	private JFrame frame;
	
	private int energiaDelay = 0;
	private int disparoDelay = 0;
	private int animacionDelay = 0;
	private int animacionSwitch = 0;
	static boolean running=true;
	
	private double inerciaX=0;
	private double inerciaY=0;
	static int vidas=4;
	static boolean vidaRestada=false;
	public StarShip() {
		
		loadImages();
//		AnimacionSprites animaciones = new AnimacionSprites();
		
		painter = new GamePanel();
		painter.setPreferredSize(new Dimension(ANCHO, ALTO));
		
		frame= new JFrame();
		frame.setTitle("StarShip");
		frame.setContentPane(painter);
//		frame.setSize(ANCHO, ALTO);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		
	}
	public int getANCHO() {
		return ANCHO;
	}
	public int getALTO() {
		return ALTO;
	}
	
	public void loadImages() {
		try {
			navePropia = scale(ImageIO.read(new File("resources/nave1.png")), UNIT_SIZE, UNIT_SIZE);
			navePropia1 = scale(ImageIO.read(new File("resources/nave1.png")), UNIT_SIZE, UNIT_SIZE);
			navePropia2 = scale(ImageIO.read(new File("resources/nave2.png")), UNIT_SIZE, UNIT_SIZE);
			navePropia3 = scale(ImageIO.read(new File("resources/nave3.png")), UNIT_SIZE, UNIT_SIZE);
			disparo = ImageIO.read(new File("resources/disparo1.png"));
			disparo1 = ImageIO.read(new File("resources/disparo1.png"));
			disparo2 = ImageIO.read(new File("resources/disparo2.png"));
			disparo3 = ImageIO.read(new File("resources/disparo3.png"));
			explosion = ImageIO.read(new File("resources/explosion1.png"));
			explosion1 = ImageIO.read(new File("resources/explosion1.png"));
			explosion2 = ImageIO.read(new File("resources/explosion2.png"));
			explosion3 = ImageIO.read(new File("resources/explosion3.png"));
			fondo = ImageIO.read(new File("resources/fondo.jpg"));
			marcadore = scale(ImageIO.read(new File("resources/Interfaz.png")), ANCHO, ALTO);
			vida = ImageIO.read(new File("resources/vida.png"));
			xocas = scale(ImageIO.read(new File("resources/xocas.png")), ANCHO, ALTO);

		} catch (IOException e) {
			System.out.println("Error al cargar las imagenes...");
			e.printStackTrace();
		}	
	}
	
	@Override
	public void run() {
		while(running) {	
		
			if (Game.player.vidaNave>0 && vidas>0) {
				move();
			}
			collisions();
			tickDelays();
			animations();
			painter.repaint();
			
				try {
					this.sleep(5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (vidaRestada == false && Game.player.vidaNave<=0) {
					vidas--;
					vidaRestada = true;
				}
		}
	}
	
	public void animations() {
		if (animacionDelay==0) {
			if (animacionSwitch<2) {
				animacionSwitch++;
			}else {
				animacionSwitch=0;
			}
			animacionDelay=30;
			switch(animacionSwitch) {
			case 0:
				navePropia = navePropia1;
				disparo = disparo1;
				explosion = explosion1;
				break;
			case 1:
				navePropia = navePropia2;
				disparo = disparo2;
				explosion = explosion2;
				break;
			case 2:
				navePropia = navePropia3;
				disparo = disparo3;
				explosion = explosion3;
				break;
			}
		}
	}
	
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		rotate(g2, Game.player.anguloNave*Math.PI/180, posXNavePantalla+UNIT_SIZE/2, posYNavePantalla+UNIT_SIZE/2);
		g.drawImage(navePropia,posXNavePantalla, posYNavePantalla, painter);
		g.setColor(Color.RED);
		g.fillRect(ANCHO/45, ALTO/27, ANCHO/16, ALTO/7);
		g.setColor(Color.BLACK);
		g.fillRect(ANCHO/45, ALTO/27, ANCHO/16, ALTO/7*(100-Game.player.vidaNave)/100);
		
		g.setColor(Color.GREEN);
		g.fillRect((int)Math.round(ANCHO/1.08), ALTO/27, ANCHO/16, ALTO/7);
		g.setColor(Color.BLACK);
		g.fillRect((int)Math.round(ANCHO/1.08), ALTO/27, ANCHO/16, ALTO/7*(100-Game.player.energiaNave)/100);
		
		g.drawImage(marcadore, 0, 0, painter);
		
		int switchVidas=5-vidas;
		switch(switchVidas) {
		case 1:
			g.drawImage(vida, (int)Math.round(ANCHO/2.5)+(int)Math.round(3*ANCHO/19), (int)Math.round(ALTO/38.4), painter);
		case 2:
			g.drawImage(vida, (int)Math.round(ANCHO/2.5)+(int)Math.round(2*ANCHO/19), (int)Math.round(ALTO/38.4), painter);
		case 3:
			g.drawImage(vida, (int)Math.round(ANCHO/2.5)+(int)Math.round(ANCHO/19), (int)Math.round(ALTO/38.4), painter);
		case 4:
			g.drawImage(vida, (int)Math.round(ANCHO/2.5), (int)Math.round(ALTO/38.4), painter);
			break;
		}
		if (StarShip.vidas<=0) {
			g.drawImage(xocas, 0, 0, painter);
		}
	}
	
	public void rotate(Graphics2D g2, double angle, double rX, double rY) {
		AffineTransform backup = g2.getTransform();
		AffineTransform a = AffineTransform.getRotateInstance(angle, rX, rY);
		g2.setTransform(a);
		
		
		g2.drawImage(fondo, (int) Math.round(posXFondo), (int) Math.round(posYFondo), null);
		
		for(int i=0;i<Game.player.disparos.size();i++) {
			rotateImage(g2, disparo, (360-Game.player.disparos.get(i).disparoAngulo)*Math.PI/180, Game.player.disparos.get(i).disparoX+posXFondo-DISP_SIZE/2, Game.player.disparos.get(i).disparoY+posYFondo-DISP_SIZE/2, DISP_SIZE/2, DISP_SIZE/2);
		}
		
		for(int i=0;i<Game.players.size();i++) {
			if(!Game.players.get(i).nombre.equals(Game.player.nombre)){
				rotateImage(g2,navePropia, (360-Game.players.get(i).anguloNave)*Math.PI/180, Game.players.get(i).naveX+posXFondo-UNIT_SIZE/2, Game.players.get(i).naveY-UNIT_SIZE/2+posYFondo, UNIT_SIZE/2, UNIT_SIZE/2);
				for(int j=0;j<Game.players.get(i).disparos.size();j++) {
					rotateImage(g2, disparo, (360-Game.players.get(i).disparos.get(j).disparoAngulo)*Math.PI/180, Game.players.get(i).disparos.get(j).disparoX+posXFondo-DISP_SIZE/2, Game.players.get(i).disparos.get(j).disparoY+posYFondo-DISP_SIZE/2, DISP_SIZE/2, DISP_SIZE/2);
				}
			}
		}
		for(int i=0;i<Game.players.size();i++) {
			if(Game.players.get(i).vidaNave<=0) {
				rotateImage(g2, explosion, (360-Game.players.get(i).anguloNave)*Math.PI/180, Game.players.get(i).naveX+posXFondo-UNIT_SIZE/2, Game.players.get(i).naveY-UNIT_SIZE/2+posYFondo, UNIT_SIZE/2, UNIT_SIZE/2);
			}
		}
		g2.setTransform(backup);
	}
	public void rotateImage(Graphics2D g2, BufferedImage image, double angulo, double drawX, double drawY, int X, int Y) {
		AffineTransform tx = AffineTransform.getRotateInstance(angulo, X, Y);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		g2.drawImage(op.filter(image, null), (int) Math.round(drawX), (int) Math.round(drawY), null);
	}
	
	public static BufferedImage scale(BufferedImage src, int w, int h)
	{
		BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		int x, y;
		int ww = src.getWidth();
		int hh = src.getHeight();
		int[] ys = new int[h];
		for (y = 0; y < h; y++)
			ys[y] = y * hh / h;
		for (x = 0; x < w; x++) {
			int newX = x * ww / w;
			for (y = 0; y < h; y++) {
				int col = src.getRGB(newX, ys[y]);
				img.setRGB(x, y, col);
			}
		}
		return img;
	}	
	
	public void tickDelays() {
		if (disparoDelay>0) {
			disparoDelay--;
		}
		for(int i=0;i<Game.player.disparos.size();i++) {
			if (Game.player.disparos.get(i).disparoTravelled > 0) {
				Game.player.disparos.get(i).disparoTravelled--;
			}
		}
		if (animacionDelay>0) {
			animacionDelay--;
		}
		
		if (energiaDelay>0) {
			energiaDelay--;
		}
		if (Game.player.energiaNave<100 && energiaDelay == 0) {
			Game.player.energiaNave++;
			energiaDelay=10;
		}
	}
	
	public void collisions() {
		
		for(int i=0;i<Game.player.disparos.size();i++) {
			if (Game.player.disparos.get(i).disparoTravelled==0) {
				Game.player.disparos.remove(i);
			}
		}
		Rectangle rectNave = new Rectangle((int) Math.round(Game.player.naveX-UNIT_SIZE/2), (int) Math.round(Game.player.naveY-UNIT_SIZE/2), UNIT_SIZE, UNIT_SIZE);
		for(int i=0;i<Game.players.size();i++) {
			if(!Game.players.get(i).nombre.equals(Game.player.nombre)) {
				for(int j=0;j<Game.players.get(i).disparos.size();j++) {
					Rectangle rectDisparo = new Rectangle((int)Math.round(Game.players.get(i).disparos.get(j).disparoX-12), (int)Math.round(Game.players.get(i).disparos.get(j).disparoY-12), 24, 24);
					if(rectNave.intersects(rectDisparo)) {
						if (Game.player.vidaNave>0) {
							Game.player.vidaNave--;
						}
						
					}
				}
			}
			
		}
		
		for(int i=0;i<Game.player.disparos.size();i++) {
			Rectangle rectDisparo = new Rectangle((int)Math.round(Game.player.disparos.get(i).disparoX-12), (int)Math.round(Game.player.disparos.get(i).disparoY-12), 24, 24);
			for(int j=0;j<Game.players.size();j++) {
				if(!Game.players.get(j).nombre.equals(Game.player.nombre)) {
					Rectangle rectNave2 = new Rectangle((int) Math.round(Game.players.get(j).naveX-20), (int) Math.round(Game.players.get(j).naveY-20), 40, 40);
					if(rectDisparo.intersects(rectNave2)) {
						Game.player.disparos.remove(i);
					}
				}
			}
		}
		
		
		
		
	}
		
	public void move(){
		
		if (teclaW && Game.player.energiaNave>0) {
			if (inerciaY<200) {
				inerciaY = inerciaY+2;
			}
		}else {
			if(inerciaY>0) {
				inerciaY--;
			}
		}
		
		if (teclaS && Game.player.energiaNave>0) {
			if (inerciaY>-200) {
				inerciaY = inerciaY-2;
			}
		}else {
			if(inerciaY<0) {
				inerciaY++;
			}
		}
		
		if (teclaQ && Game.player.energiaNave>0) {
			if (inerciaX>-200) {
				inerciaX = inerciaX-2;
			}
		}else {
			if(inerciaX<0) {
				inerciaX++;
			}
		}
		
		if (teclaE && Game.player.energiaNave>0) {
			if (inerciaX<200) {
				inerciaX = inerciaX+2;
			}
		}else {
			if(inerciaX>0) {
				inerciaX--;
			}
		}
	
		if (teclaD) {
			Game.player.anguloNave--;
			if (Game.player.anguloNave<0) {
				Game.player.anguloNave=360;
			}
		}
		if (teclaA) {
			Game.player.anguloNave++;
			if (Game.player.anguloNave>360) {
				Game.player.anguloNave=0;
			}
		}
		
		
//		MOVIMIENTO BASADO EN INERCIA
		if (inerciaY > 0) {
			if(Game.player.naveX-(Math.sin(Game.player.anguloNave*Math.PI/180) * naveSpeed)<anchoFondo && Game.player.naveX-(Math.sin(Game.player.anguloNave*Math.PI/180) * naveSpeed)>0) {
				posXFondo =posXFondo + (Math.sin(Game.player.anguloNave*Math.PI/180) * inerciaY * naveSpeed / 200);
			}
			if(Game.player.naveY-(Math.cos(Game.player.anguloNave*Math.PI/180) * naveSpeed)<altoFondo && Game.player.naveY-(Math.cos(Game.player.anguloNave*Math.PI/180) * naveSpeed)>0) {
				posYFondo =posYFondo + (Math.cos(Game.player.anguloNave*Math.PI/180) * inerciaY *  naveSpeed / 200);
			}
		}
		if (inerciaY <0) {
			if(Game.player.naveX+(Math.sin(Game.player.anguloNave*Math.PI/180) * naveSpeed)<anchoFondo && Game.player.naveX+(Math.sin(Game.player.anguloNave*Math.PI/180) * naveSpeed)>0) {
				posXFondo =posXFondo - (Math.sin(Game.player.anguloNave*Math.PI/180) * -inerciaY *  naveSpeed / 200);
			}
			if(Game.player.naveY+(Math.cos(Game.player.anguloNave*Math.PI/180) * naveSpeed)<altoFondo && Game.player.naveY+(Math.cos(Game.player.anguloNave*Math.PI/180) * naveSpeed)>0) {
				posYFondo =posYFondo - (Math.cos(Game.player.anguloNave*Math.PI/180) * -inerciaY *  naveSpeed / 200);
			}
		}
		if (inerciaX > 0) {
			if(Game.player.naveX-(Math.sin((Game.player.anguloNave-90)*Math.PI/180) * naveSpeed)<anchoFondo && Game.player.naveX-(Math.sin((Game.player.anguloNave-90)*Math.PI/180) * naveSpeed)>0) {
				posXFondo =posXFondo + (Math.sin((Game.player.anguloNave-90)*Math.PI/180) * inerciaX * naveSpeed / 200);
			}
			if(Game.player.naveY-(Math.cos((Game.player.anguloNave-90)*Math.PI/180) * naveSpeed)<altoFondo && Game.player.naveY-(Math.cos((Game.player.anguloNave-90)*Math.PI/180) * naveSpeed)>0) {
				posYFondo =posYFondo + (Math.cos((Game.player.anguloNave-90)*Math.PI/180) * inerciaX *  naveSpeed / 200);
			}
		}
		if (inerciaX < 0) {
			if(Game.player.naveX-(Math.sin((Game.player.anguloNave+90)*Math.PI/180) * naveSpeed)<anchoFondo && Game.player.naveX-(Math.sin((Game.player.anguloNave+90)*Math.PI/180) * naveSpeed)>0) {
				posXFondo =posXFondo + (Math.sin((Game.player.anguloNave+90)*Math.PI/180) * -inerciaX * naveSpeed / 200);
			}
			if(Game.player.naveY-(Math.cos((Game.player.anguloNave+90)*Math.PI/180) * naveSpeed)<altoFondo && Game.player.naveY-(Math.cos((Game.player.anguloNave+90)*Math.PI/180) * naveSpeed)>0) {
				posYFondo =posYFondo + (Math.cos((Game.player.anguloNave+90)*Math.PI/180) * -inerciaX *  naveSpeed / 200);
			}
		}
		
		
		
		
//		------------------------------
		
		Game.player.naveX = -posXFondo + posXNavePantalla+UNIT_SIZE/2;
		Game.player.naveY = -posYFondo + posYNavePantalla+UNIT_SIZE/2;
		
		
		if (teclaEspacio && disparoDelay==0 && Game.player.energiaNave > 0) {
			Disparo disp = new Disparo(Game.player.naveX, Game.player.naveY, Game.player.anguloNave);
			Game.player.disparos.add(disp);
			disparoDelay = 50;
			Game.player.energiaNave-=10;
	
			
		}
		for(int i=0;i<Game.player.disparos.size();i++) {
			Game.player.disparos.get(i).disparoX = Game.player.disparos.get(i).disparoX - (Math.sin(Game.player.disparos.get(i).disparoAngulo*Math.PI/180) *  6);
			Game.player.disparos.get(i).disparoY = Game.player.disparos.get(i).disparoY - (Math.cos(Game.player.disparos.get(i).disparoAngulo*Math.PI/180) *  6);

		}
		
	}
	
	public class GamePanel extends JPanel {
		
		public GamePanel() {
			setFocusable(true);
			requestFocus();
			setBackground(Color.BLACK);
			addKeyListener(new MyKeyAdapter());
		}
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			render(g);
		}
	}
	
}
