package view;

import controller.TetrisController;
import model.TetrisModel;
import observer.Observer;
import pojo.TetrisCraftPoint;
import pojo.TetrisMovingDirection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/24 14:48
 */
public class TetrisGamePanel extends JPanel implements Observer {
	private static final int DEFAULT_WEIGTH = 300;
	private static final int DEFAULT_HEIGHT = 500;
	private double boardSpace;
	private TetrisModel model;
	private TetrisController controller;
	private int theX;
	private int theY;



	public TetrisGamePanel() {
		theX = 30;
		theY = 40;
		defaultSetting();
		addListener();
	}

	private void addListener() {
		addKeyListener(new TetrisKeyListener());
	}

	public void setController(TetrisController controller) {
		this.controller = controller;
	}

	public void setModel(TetrisModel model) {
		model.registerObserver(this);
		this.model = model;
		boardSpace = (double) DEFAULT_WEIGTH * 0.8 / (double) model.getWidth();
	}

	// 默认设置
	private void defaultSetting() {
		// 设置默认长宽
		setPreferredSize(new Dimension(DEFAULT_WEIGTH, DEFAULT_HEIGHT));
		setBackground(Color.WHITE);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = (Graphics2D) g;
		drawBorder(g2d);
		drawLine(g2d);
		drawModel(g2d);

	}

	private void drawModel(Graphics2D g2d) {
		Collection<TetrisCraftPoint> craft = model.getCraft();
		List<TetrisCraftPoint> flyingCraft = model.getFlyingCraft();

		for (TetrisCraftPoint point : craft) {
			drawCraft(g2d, point.getX(), point.getY());
		}

		for (TetrisCraftPoint point : flyingCraft) {
			drawFlyingCraft(g2d, point.getX(), point.getY());
		}
	}

	private void drawCraft(Graphics2D g2d, int x, int y) {
		Color color = g2d.getColor();

		g2d.setColor(Color.gray);
		TetrisPanelRealPoint realPoint = parsePoint(x, y);
		Rectangle2D.Double rect = new Rectangle2D.Double(
				realPoint.getX(),
				realPoint.getY(),
				boardSpace,
				boardSpace);

		g2d.fill(rect);

		g2d.setColor(Color.darkGray);
		Rectangle2D.Double smallRect = new Rectangle2D.Double(
				realPoint.getX() + 5,
				realPoint.getY() + 5,
				boardSpace - 10,
				boardSpace - 10);

		g2d.fill(smallRect);

		g2d.setColor(color);

	}

	public void setTheX(int theX) {
		this.theX = theX;
	}

	public void setTheY(int theY) {
		this.theY = theY;
	}

	public int getTheX() {
		return theX;
	}

	public int getTheY() {
		return theY;
	}

	private void drawFlyingCraft(Graphics2D g2d, int x, int y) {
		Color color = g2d.getColor();

		g2d.setColor(new Color(144, 238, 144));
		TetrisPanelRealPoint realPoint = parsePoint(x, y);
		Rectangle2D.Double rect = new Rectangle2D.Double(
				realPoint.getX(),
				realPoint.getY(),
				boardSpace,
				boardSpace);

		g2d.fill(rect);

		g2d.setColor(new Color(46, 139, 87));
		Rectangle2D.Double smallRect = new Rectangle2D.Double(
				realPoint.getX() + 5,
				realPoint.getY() + 5,
				boardSpace - 10,
				boardSpace - 10);

		g2d.fill(smallRect);

		g2d.setColor(color);
	}

	private void drawBorder(Graphics2D g2d) {
		Rectangle2D.Double rect = new Rectangle2D.Double(theX, theY, boardSpace * 10, boardSpace * 20);
		Stroke stroke = g2d.getStroke();

		g2d.setStroke(new BasicStroke(2.5f));
		g2d.draw(rect);

		g2d.setStroke(stroke);
	}

	private void drawLine(Graphics2D g2d) {
		Stroke stroke = g2d.getStroke();
		Color color = g2d.getColor();

		g2d.setStroke(new BasicStroke(0.1f));
		g2d.setColor(Color.lightGray);
		// 画出行, 列
		Line2D.Double line;
		for (int i = 1; i < 20; i++) {
			line = new Line2D.Double(theX, theY + i * boardSpace, theX + 10 * boardSpace, theY + i * boardSpace);
			g2d.draw(line);
		}

		for (int i = 1; i < 10; i++) {
			line = new Line2D.Double(theX + i * boardSpace, theY, theX + i * boardSpace, theY + boardSpace * 20);
			g2d.draw(line);
		}
		g2d.setStroke(stroke);
		g2d.setColor(color);
	}

	@Override
	public void update() {
		repaint();
	}

	private TetrisPanelRealPoint parsePoint(int x, int y) {
		return new TetrisPanelRealPoint(theX + boardSpace * (x), theY + boardSpace * (19 - y));
	}

	private static class TetrisPanelRealPoint {
		private final double x;
		private final double y;

		public TetrisPanelRealPoint(double x, double y) {
			this.x = x;
			this.y = y;
		}

		public double getX() {
			return x;
		}

		public double getY() {
			return y;
		}

		@Override
		public String toString() {
			return new StringJoiner(", ", TetrisPanelRealPoint.class.getSimpleName() + "[", "]")
					.add("x=" + x)
					.add("y=" + y)
					.toString();
		}
	}

	private class TetrisKeyListener extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_W) {
				controller.turnShape();
			} else if (e.getKeyCode() == KeyEvent.VK_A) {
				controller.move(TetrisMovingDirection.LEFT);
			} else if (e.getKeyCode() == KeyEvent.VK_D) {
				controller.move(TetrisMovingDirection.RIGHT);
			} else if (e.getKeyCode() == KeyEvent.VK_S) {
				controller.move(TetrisMovingDirection.DOWN);
			} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				controller.quackFall();
			}
		}
	}
}
