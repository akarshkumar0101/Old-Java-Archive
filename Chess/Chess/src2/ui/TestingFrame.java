package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import game.Board;
import game.ChessGame;
import game.Piece;
import game.Utility;

public class TestingFrame extends ChessGameUI {

	private final JFrame frame;

	private final GridLayout gridLayout;
	private final PieceLabel[][] labels;

	private final Board board;

	public TestingFrame(ChessGame game, Board board) {
		super(game);
		frame = new JFrame("Temporary testing frame");

		gridLayout = new GridLayout(8, 8);
		frame.setContentPane(contentPane);
		frame.getContentPane().setLayout(gridLayout);
		labels = new PieceLabel[8][8];
		for (int y = 7; y >= 0; y--) {
			for (int x = 0; x < 8; x++) {
				labels[x][y] = new PieceLabel(x, y);
				frame.getContentPane().add(labels[x][y]);
			}
		}
		this.board = board;

		frame.setSize(1000, 1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		revalidateFrame();

		frame.setVisible(true);

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				repaintFrame();
			}
		});

	}

	private int[] from = null;

	public void click(int... coor) {
		if (from == null) {
			if (board.get(coor) != Piece.NONE) {
				from = coor;
				frame.repaint();
			}
		} else {
			int[][] move = { from, coor };
			if (board.playerCanMakeMove(move)) {
				board.doMove(move);
				from = null;
				revalidateFrame();
				frame.repaint();
			} else {
				from = null;
				frame.repaint();
			}
		}
	}

	public void repaintFrame() {
		frame.repaint();
	}

	public void revalidateFrame() {
		for (int y = 7; y >= 0; y--) {
			for (int x = 0; x < 8; x++) {
				// labels[x][y].setText("" + Piece.toChar(board.get(x, y)));
				labels[x][y].updateImage();
			}
		}
		possibleMoves.clear();
		shifts.clear();
		for (int[] from : Board.ALL_LOCALES) {
			for (int[] to : Board.ALL_LOCALES) {
				int[][] move = { from, to };
				if (board.playerCanMakeMove(move)) {
					possibleMoves.add(move);
					shifts.add(new double[] { genRandShift(), genRandShift(), genRandShift(), genRandShift() });
				}
			}
		}
		System.out.println(possibleMoves.size());

		frame.revalidate();
	}

	@Override
	protected int promptPawnUpgrade(int side, int... locale) {
		return 0;
	}

	private double genRandShift() {
		double randShift = (Math.random() / 56);
		randShift *= ((Math.random() > .5) ? 1 : -1);
		return randShift;
	}

	private static final Color lightSquareColor = Color.lightGray;
	private static final Color darkSquareColor = Color.gray;
	private static final Color hoverColor = new Color(150, 100, 100);// orange
	private static final Color clickColor = new Color(200, 150, 150);// cyan
	private static final Color availMoveColor = new Color(150, 200, 150);// green

	private class PieceLabel extends JComponent {
		private static final long serialVersionUID = -3703647811411435788L;

		private int[] coor;

		private Image imageToDraw;

		boolean mouseIn = false;
		boolean mousePressed = false;

		private MouseListener mouseListener = new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (from == null && board.get(coor) == Piece.NONE)
					return;
				click(coor);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (from == null && board.get(coor) == Piece.NONE)
					return;
				mousePressed = true;
				repaint();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (from == null && board.get(coor) == Piece.NONE)
					return;
				mousePressed = false;
				repaint();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				mouseIn = true;
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				mouseIn = false;
				repaint();
			}
		};

		public PieceLabel(int... coor) {
			this.coor = coor;
			addMouseListener(mouseListener);

			setEnabled(false);

			setOpaque(false);
		}

		public void updateImage() {
			imageToDraw = Piece.getTempImage(board.get(coor));
		}

		@Override
		public void paint(Graphics g) {
			// super.paint(g); // no need for this bc paint will be hidden
			int piece = board.get(coor);

			g.setColor(Color.black);
			g.drawRect(0, 0, getWidth(), getHeight());

			g.setColor(lightSquareColor);
			if ((coor[0] % 2 == 0 && coor[1] % 2 == 0) || ((coor[0] % 2 == 1 && coor[1] % 2 == 1))) {
				g.setColor(darkSquareColor);
			}
			g.fillRect(1, 1, getWidth() - 1, getHeight() - 1);

			if (from != null && Utility.arrayListContainsMove(possibleMoves, new int[][] { from, coor })) {
				g.setColor(availMoveColor);
				g.fillRect(1, 1, getWidth() - 1, getHeight() - 1);
			}
			if (from != null && from[0] == coor[0] && from[1] == coor[1]) {
				g.setColor(clickColor);
				g.fillRect(1, 1, getWidth() - 1, getHeight() - 1);
			}
			if (mouseIn) {
				g.setColor(hoverColor);
				g.fillRect(1, 1, getWidth() - 1, getHeight() - 1);
			}
			if (mousePressed) {
				g.setColor(clickColor);
				g.fillRect(1, 1, getWidth() - 1, getHeight() - 1);
			}

			if (piece == Piece.NONE)
				return;
			// g.drawImage(imageToDraw, 1, 1, getWidth() - 1, getHeight() - 1,
			// null);
			g.drawImage(imageToDraw, (int) (PieceLabel.this.getWidth() * .15),
					(int) (PieceLabel.this.getHeight() * .15), (int) (PieceLabel.this.getWidth() * .7),
					(int) (PieceLabel.this.getHeight() * .7), null);
		}
	}

	private ArrayList<Object> possibleMoves = new ArrayList<Object>();
	private ArrayList<double[]> shifts = new ArrayList<double[]>();

	private final JPanel contentPane = new JPanel() {
		private static final long serialVersionUID = -5298039325426565984L;

		@Override
		public void paint(Graphics g) {
			super.paint(g);
			int halfxSquare = getWidth() / Board.WIDTH / 2;
			int halfySquare = getHeight() / Board.HEIGHT / 2;

			g.setColor(Color.red);
			for (int i = 0; i < possibleMoves.size(); i++) {
				int[][] move = (int[][]) possibleMoves.get(i);
				// if (from != null && move.fx() == from[0] && move.fy() ==
				// from[1]) {
				// g.setColor(Color.green);
				// } else {
				// g.setColor(Color.red);
				// }
				double[] shift = shifts.get(i);
				if (board.get(move[0]) > 0) {
					g.setColor(Color.red);
				} else {
					g.setColor(Color.blue);
				}
				g.drawLine(move[0][0] * getWidth() / 8 + halfxSquare + ((int) (shift[0] * getWidth())),
						(7 - move[0][1]) * getHeight() / 8 + halfySquare + ((int) (shift[1] * getHeight())),
						move[1][0] * getWidth() / 8 + halfxSquare + ((int) (shift[2] * getWidth())),
						(7 - move[1][1]) * getHeight() / 8 + halfySquare + ((int) (shift[3] * getHeight())));
			}
		}
	};

}
