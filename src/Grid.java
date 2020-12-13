import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class Grid implements MouseListener {

	final int x = 1;
	final int o = -1;

	int currentMove = x;
	boolean moveDone = false;

	BufferedImage cross, circle;

	Game game;

	public Grid(Game game) {
		this.game = game;
		loadImage();
	}

	public void loadImage() {
		cross = ImageLoader.loadImg("Assets\\cross.png");
		circle = ImageLoader.loadImg("Assets\\download.png");
	}

	public void render(Graphics g, Game game) {
		g.setColor(Color.cyan);
		for (int i = 0; i < game.gridlayout[0].length; i++) {
			for (int j = 0; j < game.gridlayout[0].length; j++) {
				if (game.gridlayout[i][j] == x) {
					g.drawImage(cross, ((game.width / 3) * i) + 30, ((game.height / 3) * j) + 30, 75, 75, null);
				}

				else if (game.gridlayout[i][j] == o) {
					g.drawImage(circle, ((game.width / 3) * i) + 15, ((game.height / 3) * j) + 15, 100, 100, null);
				}
			}
		}
	}

	public int[] update(Game game) {
		if (checkWin(game.gridlayout)) {
			return returnLine(game.gridlayout);
		}
		return new int[0];
	}

	public int[] returnLine(int[][] layout) {
		int[] lineCoord = new int[4];
		int sumC = 0, sumR = 0;
		for (int i = 0; i < layout[0].length; i++) {
			for (int j = 0; j < layout[0].length; j++) {
				sumC += layout[i][j];
				sumR += layout[j][i];
			}
			if (Math.abs(sumC) == 3) {
				lineCoord[0] = i;
				lineCoord[1] = 0;
				lineCoord[2] = i;
				lineCoord[3] = 3;
			} else if (Math.abs(sumR) == 3) {
				lineCoord[0] = 0;
				lineCoord[1] = i;
				lineCoord[2] = 3;
				lineCoord[3] = i;
			}
			sumC = 0;
			sumR = 0;
		}
		
		if (Math.abs(layout[0][0] + layout[1][1] + layout[2][2]) == 3) {
			lineCoord[0] = 0;
			lineCoord[1] = 0;
			lineCoord[2] = 3;
			lineCoord[3] = 3;
		}
		if (Math.abs(layout[2][0] + layout[1][1] + layout[0][2]) == 3) {
			lineCoord[0] = 3;
			lineCoord[1] = 0;
			lineCoord[2] = 0;
			lineCoord[3] = 3;
		}
		return lineCoord;
	}

	public boolean checkWin(int[][] layout) {
		int sumC = 0;
		int sumR = 0;
		// Check Columns and rows
		for (int i = 0; i < layout[0].length; i++) {
			for (int j = 0; j < layout[0].length; j++) {
				sumC += layout[i][j];
				sumR += layout[j][i];
			}
			if (Math.abs(sumC) == 3)
				return true;
			if (Math.abs(sumR) == 3)
				return true;
			sumR = 0;
			sumC = 0;
		}

		// Check Diagonal
		if (Math.abs(layout[0][0] + layout[1][1] + layout[2][2]) == 3) {
			return true;
		}
		if (Math.abs(layout[2][0] + layout[1][1] + layout[0][2]) == 3) {
			return true;
		}
		return false;
	}

	public int getRow(int x) {
		double xPer = (x + 0.0) / (game.width + 0.0);
		if (xPer < 0.33) {
			return 0;
		} else if (xPer < 0.67) {
			return 1;
		}
		return 2;
	}

	public int getColumn(int y) {
		double yPer = (y + 0.0) / (game.height + 0.0);
		if (yPer < 0.33) {
			return 0;
		} else if (yPer < 0.67) {
			return 1;
		}
		return 2;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int xRow = getRow(e.getX());
		int yCol = getColumn(e.getY());
		if (game.gridlayout[xRow][yCol] == 0) {
			game.gridlayout[xRow][yCol] = currentMove;
			if (currentMove == 1) {
				currentMove = -1;
			} else {
				currentMove = 1;
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
