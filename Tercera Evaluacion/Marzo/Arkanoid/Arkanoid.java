package Arkanoid;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Arkanoid extends JFrame implements KeyListener {

	/* CONSTANTES */

	private static final long serialVersionUID = 1L;
	private static final String SL = System.getProperty("line.separator");

	// TAMAÑOS ORIGINALES (POR SI SE HACEN PRUEBAS)

	public static final int ANCHURA_PANTALLA = 800; //800
	public static final int ALTURA_PANTALLA = 800; //800

	public static final double RADIO_BOLA = 10.0; //10.0
	public static double VELOCIDAD_BOLA = 0.4; //0.4

	public static final double ANCHURA_PALETA = 60.0; //60.0
	public static final double ALTURA_PALETA = 20.0; //20.0
	public static final double VELOCIDAD_PALETA = 0.6; //0.6

	public static final double ANCHURA_BLOQUE = 60.0; //60.0
	public static final double ALTURA_BLOQUE = 20.0; //20.0

	public static final int CANTIDAD_BLOQUES_X = 2; //11
	public static final int CANTIDAD_BLOQUES_Y = 1; //4

	public static final int VIDAS_JUGADOR = 5; //5

	public static final double FT_SLICE = 1.0; //1.0
	public static final double FT_STEP = 1.0; //1.0

	public static int nivel = 0;

	public static final String FONT = "Conthrax sb";

	public static final String SONIDOMUERTE = ".\\bin\\Arkanoid\\Items\\sonidomuerte.mp3";
	public static final String VICTORIA = ".\\bin\\Arkanoid\\Items\\victoria.mp3";

	public static final String BACKGROUND = "\\Items\\imagen 3.png";
	public static final String ICONO = "\\Items\\icono.png";

	public static final String PALETA = "\\Items\\paleta.png";
	public static final String BOLA = "\\Items\\bola.png";

	public static final String BLOQUEVERDE = "\\Items\\bloqueverde.png";
	public static final String BLOQUEROJO = "\\Items\\bloquerojo.png";
	public static final String BLOQUEAMARILLO = "\\Items\\bloqueamarillo.png";
	public static final String BLOQUEAZUL = "\\Items\\bloqueazul.png";

	/* VARIABLES DEL JUEGO */

	private boolean tryAgain = false;
	private boolean running = false;
	private boolean paused = false;

	private Paddle paddle = new Paddle(ANCHURA_PANTALLA / 2, ALTURA_PANTALLA - 50);
	private Ball ball = new Ball(ANCHURA_PANTALLA / 2, ALTURA_PANTALLA / 2);
	private List<Brick> bricks = new ArrayList<Arkanoid.Brick>();
	private ScoreBoard scoreboard = new ScoreBoard();

	private double lastFt;
	private double currentSlice;

	class ScoreBoard {

		int score = 0;
		int score_lvl1 = 0;
		int lives = VIDAS_JUGADOR;
		boolean win = false;
		boolean gameOver = false;
		String text = "";

		Font font;

		ScoreBoard() {
			font = new Font(FONT, Font.PLAIN, 12);
			text = "Bienvenido a Arkanoid - CIDE";
		}

		void increaseScore() {

			score++;

			if (bricks.size() == 1) {

				reproducirSonido sonido = new reproducirSonido();

				try {
					//sonido.AbrirFichero(VICTORIA);
					//sonido.Play();
				} catch (Exception e) {
					System.out.println(" ");
				}

				win = true;
				text = "¡Has ganado!" + SL  + "Puntuacion: " + (score+score_lvl1)
						+ SL + SL +"Apreta Enter" + SL + "para reiniciar";
			} else {
				updateScoreboard();
			}
		}

		void die() {

			reproducirSonido sonido = new reproducirSonido();

			try {
				sonido.AbrirFichero(SONIDOMUERTE);
				sonido.Play();
			} catch (Exception e) {
				System.out.println(" ");
			}

			lives--;

			if (lives == 0) {
				gameOver = true;
				text = "¡Has perdido!" + SL  + "Puntuacion: " + (score+score_lvl1)
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
			text = "Puntuación: " + score + "  Vidas: " + lives;
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
				g.drawString(text, (ANCHURA_PANTALLA / 2) - (titleLen / 2),
						titleHeight + 15);
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
			else {			
				g.setColor(Color.YELLOW);
				g.drawImage(bloque_amarillo.getImage(), (int) left(), (int) top(), (int) sizeX, (int) sizeY, null);
			}

			//g.fillRect((int) left(), (int) top(), (int) sizeX, (int) sizeY);

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
			//g.fillOval((int) left(), (int) top(), (int) radius * 2,(int) radius * 2);
		}

		void update(ScoreBoard scoreBoard, Paddle paddle) {

			x += velocityX * FT_STEP;
			y += velocityY * FT_STEP;

			if (left() < 0)
				velocityX = VELOCIDAD_BOLA;
			else if (right() > ANCHURA_PANTALLA)
				velocityX = -VELOCIDAD_BOLA;
			if (top() < 0) {
				velocityY = VELOCIDAD_BOLA;
			} else if (bottom() > ALTURA_PANTALLA) {
				velocityY = -VELOCIDAD_BOLA;
				x = paddle.x;
				y = paddle.y - 50;
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
		if (mBall.x < mPaddle.x)
			mBall.velocityX = -VELOCIDAD_BOLA;
		else
			mBall.velocityX = VELOCIDAD_BOLA;
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
		// deallocate old bricks
		bricks.clear();

		for (int iX = 0; iX < CANTIDAD_BLOQUES_X; ++iX) {
			for (int iY = 0; iY < CANTIDAD_BLOQUES_Y; ++iY) {
				bricks.add(new Brick((iX + 1) * (ANCHURA_BLOQUE + 3) + 22,
						(iY + 2) * (ALTURA_BLOQUE + 3) + 30));
			}
		}
	}

	public Arkanoid() {

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

		BufferStrategy bf = this.getBufferStrategy();
		Graphics g = bf.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());

		running = true;
		drawScene(ball, bricks, scoreboard);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		while (running) {

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

			} else {
				if (tryAgain) {
					tryAgain = false;
					initializeBricks(bricks);
					scoreboard.lives = VIDAS_JUGADOR;
					scoreboard.score = 0;
					scoreboard.win = false;
					scoreboard.gameOver = false;
					scoreboard.updateScoreboard();
					ball.x = ANCHURA_PANTALLA / 2;
					ball.y = ALTURA_PANTALLA / 2;
					paddle.x = ANCHURA_PANTALLA / 2;
				}
				else if(paused && !scoreboard.gameOver){
					scoreboard.paused();
					drawScene(ball, bricks, scoreboard);

					if(!paused) {
						scoreboard.updateScoreboard();
					}

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
				}
			}

		}
	}

	private void drawScene(Ball ball, List<Brick> bricks, ScoreBoard scoreboard) {
		// Code for the drawing goes here.

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
		if (event.getKeyCode() == KeyEvent.VK_ENTER) {
			tryAgain = true;
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

	public static void main(String[] args) {

		new Arkanoid().run();

	}
}