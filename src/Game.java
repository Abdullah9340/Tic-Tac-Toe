import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

public class Game implements Runnable {

	int[][] gridlayout = new int[3][3];
	
	Display display;
	Grid grid;
	boolean running = false;

	Graphics g;
	BufferStrategy bs;

	int width, height, FPS = 30;
	boolean isGameover = false;
	String title;
	int[] winner;

	Thread thread;

	public Game(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
		this.start();
	}

	public void init() {
		display = new Display(title, width, height);
		grid = new Grid(this);
		display.getCanvas().addMouseListener(grid);
	}
	
	public void drawGrid(Graphics g) {
		g.setColor(Color.black);
		g.drawLine(0, height/3, width, height/3);
		g.drawLine(0, (height * 2)/3, width, (height * 2) /3);
		g.drawLine(width/3,0,width/3,height);
		g.drawLine(width * 2 / 3, 0, width * 2 / 3, height);
	}

	public void update() {
		winner = grid.update(this);
		if(winner.length != 0) {
			isGameover = true;

		}
	}

	public void render() {
		bs = display.getCanvas().getBufferStrategy();
		if (bs == null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		
		g.setColor(Color.white);
		g.fillRect(0, 0, width, height);
		//Start Draw
		drawGrid(g);
		grid.render(g, this);
		if(isGameover) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(6));
			g.setColor(Color.black);
			//Check if it is diagonal
			if(Math.abs(winner[2] - winner[0]) == 3 && Math.abs(winner[3] - winner[1]) == 3) {
				g2.drawLine((width/3) * winner[0], (height/3) * winner[1], (width/3) * winner[2], (height/3) * winner[3]);
			}
			//Columns
			else if(winner[0] == winner[2]) {
				g2.drawLine((width/3) * winner[0] + 70, (height/3) * winner[1], (width/3) * winner[2] + 70, (height/3) * winner[3]);
			}else {
				g2.drawLine((width/3) * winner[0], (height/3) * winner[1] + 70, (width/3) * winner[2], (height/3) * winner[3] + 70);
			}
			running = false;
			g2.dispose();
		}
		//End Draw
		bs.show();
		g.dispose();
	}

	
	@Override
	public void run() {
		init();
		//Create constant framerate
		double timePerTick = 1000000000 / FPS;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();

		while (running) {
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			lastTime = now;

			if (delta >= 1) {
				update();
				render();
				delta--;
			}

		}

	}
	
	public void start() {
		if (running) {
			return;
		}
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public void stop() {
		if (!running) {
			return;
		}
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public int[][] getGrid(){
		return gridlayout;
	}

}
