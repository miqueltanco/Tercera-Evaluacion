package Arkanoid;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;




public class Arkanoid2 extends JFrame implements KeyListener, SerialPortEventListener {

	
	/* Inicialitzacions serial
	 * 
	 */
	SerialPort serialPort;
	/** The port we're normally going to use. */
	private static final String PORT_NAMES[] = {                  
			//"/dev/tty.usbserial-A9007UX1", // Mac OS X
			"/dev/cu.usbmodem3a21",
			"/dev/ttyUSB0", // Linux
			"COM3", // Windows
	};
	protected BufferedReader input;
	private OutputStream output;
	private static final int TIME_OUT = 3000;
	private static final int DATA_RATE = 9600;
	
	
	/* Inicialitzacions serial
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String SL = System.getProperty("line.separator");

	// TAMA�OS ORIGINALES (POR SI SE HACEN PRUEBAS)

	public static final int ANCHURA_PANTALLA = 800; //800
	public static final int ALTURA_PANTALLA = 800; //800

	public static final double RADIO_BOLA = 10.0; //10.0
	public static double VELOCIDAD_BOLA = 0.4; //0.4

	public static final double ANCHURA_PALETA = 60.0; //60.0
	public static final double ALTURA_PALETA = 20.0; //20.0
	public static double VELOCIDAD_PALETA = 0.6; //0.6

	public static final double ANCHURA_BLOQUE = 60.0; //60.0
	public static final double ALTURA_BLOQUE = 20.0; //20.0

	public static int CANTIDAD_BLOQUES_X = 11; //11
	public static int CANTIDAD_BLOQUES_Y = 5; //5

	public static final int VIDAS_JUGADOR = 5; //5

	public static final double FT_SLICE = 1.0; //1.0
	public static final double FT_STEP = 1.0; //1.0

	public static int nivel = 0; //0

	public static final String FONT = "Conthrax sb";
	public static final String CORAZON = "./Items/corazonmediano.png";

	public static final String SONIDOMUERTE = "./Items/sonidomuerte.mp3";
	public static final String VICTORIA = "./Items/victoria.mp3";
	public static final String AMBIENTE = "./Items/ambiente.wav";

	public static final String BACKGROUND = "./Items/imagen 3.png";
	public static final String ICONO = "./Items/icono.png";

	public static final String PALETA = "./Items/paleta.png";
	public static final String BOLA = "./Items/bola.png";

	public static final String BLOQUEVERDE = "./Items/bloqueverde.png";
	public static final String BLOQUEROJO = "./Items/bloquerojo.png";
	public static final String BLOQUEAMARILLO = "./Items/bloqueamarillo.png";
	public static final String BLOQUEAZUL = "./Items/bloqueazul.png";
	public static final String BLOQUENARANJA = "./Items/bloquenaranja.png";

	/* VARIABLES DEL JUEGO */

	private boolean tryAgain = false;
	private boolean actualizado = false;
	private boolean running = false;
	private boolean paused = false;
	private boolean dificil = false;
	private boolean augVelocidad = false;
	private boolean derecha = false;

	private Paddle paddle = new Paddle(ANCHURA_PANTALLA / 2, ALTURA_PANTALLA - 50);
	private Ball ball = new Ball(ANCHURA_PANTALLA / 2, ALTURA_PANTALLA / 2);
	private List<Brick> bricks = new ArrayList<Arkanoid2.Brick>();
	private ScoreBoard scoreboard = new ScoreBoard();
	private reproducirSonido sonidoambiente = new reproducirSonido();
	private ArrayList<ImageIcon> listaImagenes= new ArrayList<ImageIcon>(); 
	ImageIcon corazon = new ImageIcon(new ImageIcon(getClass().getResource(CORAZON)).getImage());

	private double lastFt;
	private double currentSlice;

	class ScoreBoard {

		int score = 0;
		int lives = VIDAS_JUGADOR;
		boolean win = false; //false
		boolean gameOver = false;
		String text = "";

		Font font;

		ScoreBoard() {
			font = new Font(FONT, Font.PLAIN, 12);
			text = "Bienvenido a Arkanoid - CIDE";
		}

		void increaseScore() {
			score++;

			/* PARA CAMBIAR DE NIVEL UTILIZAMOS ESTAS CONDICIONES */

			if (bricks.size() == 1 && nivel == 0) {
				nivel = 1;
				win = true;
			} else if (bricks.size() == 1 && nivel == 1) {
				nivel = 2;
				actualizado = false;
				win = true;
			} else if (bricks.size() == 1 && nivel == 2){
				nivel = 3;
			}

			/* COMO EL NIVEL 3 ES EL ULTIMO NIVEL, SALTAR� UN SONIDO DE VICTORIA */

			if (bricks.size() == 1 && nivel == 3) {
				reproducirSonido sonido = new reproducirSonido();

				try {
					sonido.AbrirFichero(VICTORIA);
					sonido.Play();
				} catch (Exception e) {
					System.out.println(" ");
				}

				try {
					sonidoambiente.Stop();
				} catch (Exception e) {
					e.printStackTrace();
				}

				gameOver = true;
				win = true;
				text = "�Has ganado!" + SL  + "Puntuacion: " + (score)
						+ SL + SL +"Apreta Enter" + SL + "para reiniciar";

			} else {
				updateScoreboard();
			}
		}

		void die() {
			lives--;
			listaImagenes.remove(0);

			if (lives == 0) {
				gameOver = true;

				try {
					sonidoambiente.Stop();
				} catch (Exception e) {
					e.printStackTrace();
				}				

				text = "�Has perdido!" + SL  + "Puntuacion: " + (score)
						+ SL + SL +"Apreta Enter" + SL + "para reiniciar";

			} else {
				updateScoreboard();
			}
		}

		void paused() {

			if (paused) {
				text = "Pausa";
			} else {
				updateScoreboard();
			}
		}

		void updateScoreboard() {
			text = "Puntuaci�n: " + score + "  Vidas: ";

			if(nivel == 1 && win)
				text = "NIVEL 1";
			if(nivel == 2 && win)
				text = "NIVEL 2";			
		}

		void draw(Graphics g) {
			if (win || gameOver || paused) {
				font = font.deriveFont(50f);
				FontMetrics fontMetrics = g.getFontMetrics(font);
				g.setColor(Color.WHITE);
				g.setFont(font);
				int titleHeight = fontMetrics.getHeight();
				int lineNumber = 1;
				for (String line : text.split("\n")) {
					int titleLen = fontMetrics.stringWidth(line);
					g.drawString(line, (ANCHURA_PANTALLA / 2) - (titleLen / 2),
							(ALTURA_PANTALLA / 4) + (titleHeight * lineNumber));
					lineNumber++;
				}
			} else {
				font = font.deriveFont(34f);
				FontMetrics fontMetrics = g.getFontMetrics(font);
				g.setColor(Color.WHITE);
				g.setFont(font);
				int titleLen = fontMetrics.stringWidth(text);
				int titleHeight = fontMetrics.getHeight();
				g.drawString(text, (ANCHURA_PANTALLA / 2) - (titleLen / 2), titleHeight + 15);

				if(!text.contains("Bienvenido a Arkanoid - CIDE"))
					for(int i = 0; i < listaImagenes.size();i++)
						listaImagenes.get(i).paintIcon(null, g,(ANCHURA_PANTALLA / 2) - (titleLen/2)+titleLen+(i*20), titleHeight-17);
			}
		}
	}

	class Paddle extends Rectangle {

		double velocity = 0.0;

		public Paddle(double x, double y) {
			this.x = x;
			this.y = y;
			this.sizeX = ANCHURA_PALETA;
			this.sizeY = ALTURA_PALETA;
		}

		void update() {
			x += velocity * FT_STEP;

			/* CON ESTO NO SE SALE DEL MAPA */

			if(x > 764) {
				x = 764.2;
			}

			if(x < 36) {
				x = 36.2;
			}
		}

		void stopMove() {
			velocity = 0.0;
		}

		void moveLeft() {
			if (left() > 0.0) {
				velocity = -VELOCIDAD_PALETA;
			} else {
				velocity = 0.0;
			}
		}

		void moveRight() {
			if (right() < ANCHURA_PANTALLA) {
				velocity = VELOCIDAD_PALETA;
			} else {
				velocity = 0.0;
			}
		}

		void draw(Graphics g) {
			ImageIcon paleta = new ImageIcon(new ImageIcon(getClass().getResource(PALETA)).getImage());			

			g.drawImage(paleta.getImage(), (int) (left()), (int) (top()), (int) sizeX, (int) sizeY, null);
		}
	}

	class Brick extends Rectangle {

		boolean destroyed = false;

		Brick(double x, double y) {
			this.x = x;
			this.y = y;
			this.sizeX = ANCHURA_BLOQUE;
			this.sizeY = ALTURA_BLOQUE;
		}

		void draw(Graphics g) {
			ImageIcon bloque_verde = new ImageIcon(new ImageIcon(getClass().getResource(BLOQUEVERDE)).getImage());			
			ImageIcon bloque_azul = new ImageIcon(new ImageIcon(getClass().getResource(BLOQUEAZUL)).getImage());
			ImageIcon bloque_rojo = new ImageIcon(new ImageIcon(getClass().getResource(BLOQUEROJO)).getImage());	
			ImageIcon bloque_amarillo = new ImageIcon(new ImageIcon(getClass().getResource(BLOQUEAMARILLO)).getImage());
			ImageIcon bloque_naranja = new ImageIcon(new ImageIcon(getClass().getResource(BLOQUENARANJA)).getImage());


			if(y == 76) {
				g.setColor(Color.BLUE);
				g.drawImage(bloque_azul.getImage(), (int) left(), (int) top(), (int) sizeX, (int) sizeY, null);
			}
			else if(y == 99) {
				g.setColor(Color.RED);
				g.drawImage(bloque_rojo.getImage(), (int) left(), (int) top(), (int) sizeX, (int) sizeY, null);
			}
			else if(y == 122) {
				g.setColor(Color.GREEN);
				g.drawImage(bloque_verde.getImage(), (int) left(), (int) top(), (int) sizeX, (int) sizeY, null);
			}
			else if(y == 145) {			
				g.setColor(Color.YELLOW);
				g.drawImage(bloque_amarillo.getImage(), (int) left(), (int) top(), (int) sizeX, (int) sizeY, null);
			}
			else if(y == 168) {			
				g.setColor(Color.ORANGE);
				g.drawImage(bloque_naranja.getImage(), (int) left(), (int) top(), (int) sizeX, (int) sizeY, null);
			}
			else {
				g.setColor(Color.gray);
			}
		}
	}

	class Ball extends GameObject {

		double x, y;
		double radius = RADIO_BOLA;
		double velocityX = VELOCIDAD_BOLA;
		double velocityY = VELOCIDAD_BOLA;

		Ball(int x, int y) {
			this.x = x;
			this.y = y;
		}

		void draw(Graphics g) {
			ImageIcon bola = new ImageIcon(new ImageIcon(getClass().getResource(BOLA)).getImage());			

			g.drawImage(bola.getImage(), (int) left(), (int) top(), (int) radius * 2,(int) radius * 2, null);
		}

		void update(ScoreBoard scoreBoard, Paddle paddle) {

			x += velocityX * FT_STEP;
			y += velocityY * FT_STEP;

			if (left() < 0 && !augVelocidad) {
				velocityX = VELOCIDAD_BOLA;
				derecha = false;
			}
			else if (left() < 0 && augVelocidad) {
				velocityX = VELOCIDAD_BOLA*2;
				derecha = false;
			}
			else if (right() > ANCHURA_PANTALLA && !augVelocidad) {
				velocityX = -VELOCIDAD_BOLA;
				derecha = true;
			}
			else if (right() > ANCHURA_PANTALLA && augVelocidad) {
				velocityX = -VELOCIDAD_BOLA*2;
				derecha = true;
			}
			if (top() < 0) {
				velocityY = VELOCIDAD_BOLA;
			} else if (bottom() > ALTURA_PANTALLA-35) {
				velocityY = -VELOCIDAD_BOLA;
				x = paddle.x;
				y = paddle.y - 20;

				scoreBoard.die();
			}		
		}

		double left() {
			return x - radius;
		}

		double right() {
			return x + radius;
		}

		double top() {
			return y - radius;
		}

		double bottom() {
			return y + radius;
		}

	}

	boolean isIntersecting(GameObject mA, GameObject mB) {
		return mA.right() >= mB.left() && mA.left() <= mB.right()
				&& mA.bottom() >= mB.top() && mA.top() <= mB.bottom();
	}

	void testCollision(Paddle mPaddle, Ball mBall) {

		if (!isIntersecting(mPaddle, mBall))
			return;

		mBall.velocityY = -VELOCIDAD_BOLA;

		double calculo = (mBall.x - mPaddle.x);

		if(calculo >= -10.0 && calculo <= 10){
			System.out.println("EN EL CENTRO");
			if(derecha)
				mBall.velocityX = -VELOCIDAD_BOLA*0.4;
			else
				mBall.velocityX = VELOCIDAD_BOLA*0.4;
		} 
		else if(calculo <= 40.0 && calculo >= 30.0){
			System.out.println("ESQUINA DERECHA");
			if(derecha)
				mBall.velocityX = -VELOCIDAD_BOLA*2;
			else
				mBall.velocityX = VELOCIDAD_BOLA*2;
			augVelocidad = true;
		} else if(calculo <= -30.0 && calculo >= -40.0){
			System.out.println("ESQUINA IZQUIERDA");
			if(derecha)
				mBall.velocityX = -VELOCIDAD_BOLA*2;
			else
				mBall.velocityX = VELOCIDAD_BOLA*2;
			augVelocidad = true;
		} else if (calculo <= -11.0 && calculo >= -29.0) {
			System.out.println("IZQUIERDA MEDIA");
			if(derecha)
				mBall.velocityX = -VELOCIDAD_BOLA;
			else
				mBall.velocityX = VELOCIDAD_BOLA;	
		}
		else {
			System.out.println("DERECHA MEDIA");
			if(derecha)
				mBall.velocityX = -VELOCIDAD_BOLA;
			else
				mBall.velocityX = VELOCIDAD_BOLA;	
		}
	}

	void testCollision(Brick mBrick, Ball mBall, ScoreBoard scoreboard) {
		if (!isIntersecting(mBrick, mBall))
			return;

		mBrick.destroyed = true;

		scoreboard.increaseScore();

		double overlapLeft = mBall.right() - mBrick.left();
		double overlapRight = mBrick.right() - mBall.left();
		double overlapTop = mBall.bottom() - mBrick.top();
		double overlapBottom = mBrick.bottom() - mBall.top();

		boolean ballFromLeft = overlapLeft < overlapRight;
		boolean ballFromTop = overlapTop < overlapBottom;

		double minOverlapX = ballFromLeft ? overlapLeft : overlapRight;
		double minOverlapY = ballFromTop ? overlapTop : overlapBottom;

		if (minOverlapX < minOverlapY) {
			mBall.velocityX = ballFromLeft ? -VELOCIDAD_BOLA : VELOCIDAD_BOLA;
		} else {
			mBall.velocityY = ballFromTop ? -VELOCIDAD_BOLA : VELOCIDAD_BOLA;
		}
	}

	void initializeBricks(List<Brick> bricks) {

		/* QUITAMOS LOS BLOQUES ANTIGUOS DE LA LISTA, POR SI ACASO */

		bricks.clear();

		for (int iX = 0; iX < CANTIDAD_BLOQUES_X; ++iX) {
			for (int iY = 0; iY < CANTIDAD_BLOQUES_Y; ++iY) {
				bricks.add(new Brick((iX + 1) * (ANCHURA_BLOQUE + 3) + 22/*22*/,
						(iY + 2) * (ALTURA_BLOQUE + 3) + 30));
			}
		}

		/* ESCRIBE CIDE */

		if(nivel == 0) {

			for(int i = 0; i < 3; i++)
				bricks.remove(6);
			for(int i = 0; i < 5; i++)
				bricks.remove(7); 
			for(int i = 0; i < 5; i++)
				bricks.remove(12);
			for(int i = 0; i < 3; i++)
				bricks.remove(18);

			bricks.remove(19);	bricks.remove(22);

			for(int i = 0; i < 5; i++)
				bricks.remove(22);

			bricks.remove(28);	bricks.remove(29);
		}

		/* ESCRIBE NIT */	

		if(nivel == 1) {

			bricks.remove(5);
			for(int i = 0; i < 5; i++)
				bricks.remove(6);
			for(int i = 0; i < 2; i++)
				bricks.remove(7);
			for(int i = 0; i < 5; i++)
				bricks.remove(12);
			for(int i = 0; i < 3; i++)
				bricks.remove(13);
			for(int i = 0; i < 4; i++)
				bricks.remove(19);
			for(int i = 0; i < 4; i++)
				bricks.remove(21);
			for(int i = 0; i < 4; i++)
				bricks.remove(27);
		}

		/* ESCRIBE ART */

		if(nivel == 2) {

			bricks.remove(6);
			for(int i = 0; i < 2; i++)
				bricks.remove(7);
			for(int i = 0; i < 5; i++)
				bricks.remove(12);
			for(int i = 0; i < 2; i++)
				bricks.remove(18);
			for(int i = 0; i < 2; i++)
				bricks.remove(19);	

			bricks.remove(21);

			for(int i = 0; i < 5; i++)
				bricks.remove(22);
			for(int i = 0; i < 4; i++)
				bricks.remove(23);
			for(int i = 0; i < 4; i++)
				bricks.remove(29);
		}

	}

	public Arkanoid2() {

		ImageIcon icono = new ImageIcon(new ImageIcon(getClass().getResource(ICONO)).getImage());

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setUndecorated(false);
		this.setResizable(false);
		this.setSize(ANCHURA_PANTALLA, ALTURA_PANTALLA);
		this.setVisible(true);
		this.addKeyListener(this);
		this.setIconImage(icono.getImage());
		this.setLocationRelativeTo(null);

		this.createBufferStrategy(2);

		initializeBricks(bricks);
	}

	void run() {
	    
		for(int i = 0; i < VIDAS_JUGADOR; i++)
			listaImagenes.add(corazon);

		BufferStrategy bf = this.getBufferStrategy();
		Graphics g = bf.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());

		running = true;
		drawScene(ball, bricks, scoreboard);

		try {
			sonidoambiente.AbrirFichero(AMBIENTE);
			sonidoambiente.Play();
		} catch (Exception e) {
			System.out.println("Ha fallado");
		}

		while (running) {

			/* PAUSA ENTRE NIVEL 0 Y NIVEL 1 */

			if(nivel == 1 && actualizado == false) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			} 

			/* PAUSA ENTRE NIVEL 1 Y NIVEL 2 */

			if(nivel == 2 && actualizado == false) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}

			long time1 = System.currentTimeMillis();

			if (!scoreboard.gameOver && !scoreboard.win && !paused) {
				tryAgain = false;
				update();
				drawScene(ball, bricks, scoreboard);

				// to simulate low FPS
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			} else if(nivel == 1) {

				actualizado = true;
				CANTIDAD_BLOQUES_X = 11;
				CANTIDAD_BLOQUES_Y = 5;

				if(dificil)
					VELOCIDAD_BOLA = 0.6;
				else
					VELOCIDAD_BOLA = 0.5;

				scoreboard.win = false;
				VELOCIDAD_PALETA = 0.6;

				initializeBricks(bricks);
				scoreboard.updateScoreboard();

				ball.x = ANCHURA_PANTALLA / 2;
				ball.y = ALTURA_PANTALLA / 2;
				paddle.x = ANCHURA_PANTALLA / 2;

				/* INICIAMOS EL NIVEL 2 */

			} else if(nivel == 2) {

				actualizado = true;
				CANTIDAD_BLOQUES_X = 11;
				CANTIDAD_BLOQUES_Y = 5;

				if(dificil)
					VELOCIDAD_BOLA = 0.8;
				else
					VELOCIDAD_BOLA = 0.6;

				scoreboard.win = false;
				VELOCIDAD_PALETA = 0.7;

				initializeBricks(bricks);
				scoreboard.updateScoreboard();

				ball.x = ANCHURA_PANTALLA / 2;
				ball.y = ALTURA_PANTALLA / 2;
				paddle.x = ANCHURA_PANTALLA / 2;

				/* SI SE QUIERE VOLVER A EMPEZAR EL JUEGO */

			}

			if (tryAgain) {

				try {
					sonidoambiente.Play();
				} catch (Exception e) {
					System.out.println("Ha fallado");
				}

				nivel = 0;
				VELOCIDAD_BOLA = 0.4;
				CANTIDAD_BLOQUES_X = 11;
				CANTIDAD_BLOQUES_Y = 5;

				listaImagenes.clear();

				for(int i = 0; i < VIDAS_JUGADOR; i++)
					listaImagenes.add(corazon);

				initializeBricks(bricks);
				scoreboard.lives = VIDAS_JUGADOR;

				if(!scoreboard.win)
					scoreboard.score = 0;
				scoreboard.win = false;
				scoreboard.gameOver = false;

				scoreboard.updateScoreboard();

				ball.x = ANCHURA_PANTALLA / 2;
				ball.y = ALTURA_PANTALLA / 2;
				paddle.x = ANCHURA_PANTALLA / 2;

				tryAgain = false;
				actualizado = false;

			}
			else if(paused && !scoreboard.gameOver){
				scoreboard.paused();
				drawScene(ball, bricks, scoreboard);

				if(!paused) {
					scoreboard.updateScoreboard();
				}
			}


			long time2 = System.currentTimeMillis();
			double elapsedTime = time2 - time1;

			lastFt = elapsedTime;

			double seconds = elapsedTime / 1000.0;
			if (seconds > 0.0) {
				double fps = 1.0 / seconds;
				this.setTitle("FPS: " + (int)fps);
			}
		}

		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));

	}

	private void update() {

		currentSlice += lastFt;

		for (; currentSlice >= FT_SLICE; currentSlice -= FT_SLICE) {

			ball.update(scoreboard, paddle);
			paddle.update();
			testCollision(paddle, ball);

			Iterator<Brick> it = bricks.iterator();
			while (it.hasNext()) {
				Brick brick = it.next();
				testCollision(brick, ball, scoreboard);
				if (brick.destroyed) {
					it.remove();
					augVelocidad = false;
				}
			}
		}
	}

	private void drawScene(Ball ball, List<Brick> bricks, ScoreBoard scoreboard) {

		/* EN ESTE METODO SE DIBUJA LA ESCENA */

		ImageIcon fondo = new ImageIcon(new ImageIcon(getClass().getResource(BACKGROUND)).getImage());

		BufferStrategy bf = this.getBufferStrategy();
		Graphics g = null;

		try {
			g = bf.getDrawGraphics();
			g.setColor(Color.black);												
			g.fillRect(0, 0, getWidth(), getHeight());
			g.drawImage(fondo.getImage(), 0, 0, ANCHURA_PANTALLA, ALTURA_PANTALLA, null);
			ball.draw(g);
			paddle.draw(g);
			for (Brick brick : bricks) {
				brick.draw(g);
			}
			scoreboard.draw(g);

		} finally {
			g.dispose();
		}

		bf.show();

		Toolkit.getDefaultToolkit().sync();
	}

	@Override
	public void keyPressed(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
			running = false;
		}
		switch (event.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			paddle.moveLeft();
			break;
		case KeyEvent.VK_RIGHT:
			paddle.moveRight();
			break;
		case KeyEvent.VK_P:
			if(!scoreboard.win) {
				paused = !paused;
			}
			break;
		case KeyEvent.VK_ENTER:
			if(scoreboard.gameOver) {
				tryAgain = true;
			}			
		default:
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {
		switch (event.getKeyCode()) {
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_RIGHT:
			paddle.stopMove();
			break;
		default:
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

	/* 
	 * Metodes serial
	 */
	public void initialize() {
	    CommPortIdentifier portId = null;
	    Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

	    //First, Find an instance of serial port as set in PORT_NAMES.
	    while (portEnum.hasMoreElements()) {
	        CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
	        for (String portName : PORT_NAMES) {
	            if (currPortId.getName().equals(portName)) {
	                portId = currPortId;
	                break;
	            }
	        }
	    }
	    if (portId == null) {
	        System.out.println("Could not find COM port.");
	        return;
	    }

	    try {
	        serialPort = (SerialPort) portId.open(this.getClass().getName(),
	                TIME_OUT);
	        serialPort.setSerialPortParams(DATA_RATE,
	                SerialPort.DATABITS_8,
	                SerialPort.STOPBITS_1,
	                SerialPort.PARITY_NONE);

	        // open the streams
	        input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
	        output = serialPort.getOutputStream();

	        serialPort.addEventListener(this);
	        serialPort.notifyOnDataAvailable(true);
	    } catch (Exception e) {
	        System.err.println(e.toString());
	    }
	}


	public synchronized void close() {
	    if (serialPort != null) {
	        serialPort.removeEventListener();
	        serialPort.close();
	    }
	}
	public synchronized void serialEvent(SerialPortEvent oEvent) {
		KeyEvent e;
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				String inputLine=null;
				if (input.ready()) {
					inputLine = input.readLine();
					//System.out.println(inputLine);
					if (inputLine.equals("esquerra")) {
					    /*e = new KeyEvent(this, KeyEvent.KEY_PRESSED, 0, 0, 37,(char)KeyEvent.VK_LEFT);
					    keyPressed(e);*/
						paddle.moveLeft();
					}
					else if (inputLine.equals("dreta")) { 
						//e = new KeyEvent(this, 1, 20, 1, 39,(char)KeyEvent.VK_RIGHT);
						/*e = new KeyEvent(this, KeyEvent.KEY_PRESSED, 0, 0, 39,(char)KeyEvent.VK_RIGHT);
						keyPressed(e);*/
						paddle.moveRight();
					}
					else if (inputLine.equals("res")) {
						paddle.stopMove();
					}
				}

			} catch (Exception exc) {
				System.err.println(exc.toString());
			}
		} else 
			paddle.stopMove();
		// Ignore all the other eventTypes, but you should consider the other ones.
	}

	/*
	 * Metodes serial
	 *
	 */
	
	public static void main(String[] args) {
		Arkanoid2 arkanoid=new Arkanoid2();
		//arkanoid.initialize();
		arkanoid.run();

	}
}