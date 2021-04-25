package view;

import craft.TetrisCraft;
import craft.crafts.*;
import model.TetrisModel;
import observer.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/25 15:07
 */
public class TetrisNextCraftPanel extends JPanel implements Observer {
	private double boardSpace;
	private int theX;
	private int theY;
	private TetrisModel model;

	public TetrisNextCraftPanel() throws HeadlessException {
		this.theX = 45;
		this.theY = 20;
		this.boardSpace = 20;
		setPreferredSize(new Dimension(180, 75));
		setBorder(BorderFactory.createTitledBorder("next craft"));
	}

	public void setModel(TetrisModel model) {
		model.registerObserver(this);
		this.model = model;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = (Graphics2D) g;
		drawBorder(g2d);
		drawLine(g2d);
		drawModel(g2d);
	}

	private void drawBorder(Graphics2D g2d) {
		Rectangle2D.Double rect = new Rectangle2D.Double(theX, theY, boardSpace * 4, boardSpace * 2);
		Stroke stroke = g2d.getStroke();

		g2d.setStroke(new BasicStroke(1.5f));
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
		for (int i = 1; i < 2; i++) {
			line = new Line2D.Double(theX, theY + i * boardSpace, theX + 4 * boardSpace, theY + i * boardSpace);
			g2d.draw(line);
		}

		for (int i = 1; i < 4; i++) {
			line = new Line2D.Double(theX + i * boardSpace, theY, theX + i * boardSpace, theY + boardSpace * 2);
			g2d.draw(line);
		}
		g2d.setStroke(stroke);
		g2d.setColor(color);
	}

	private void drawModel(Graphics2D g2d) {
		// if (model == null) return;
		TetrisCraft nextCraft = model.getNextCraft();

		if (nextCraft instanceof BlueRicky) {
			drawCraft(g2d, 0, 0);
			drawCraft(g2d, 1, 0);
			drawCraft(g2d, 2, 0);
			drawCraft(g2d, 0, 1);
		} else if (nextCraft instanceof Cleveland) {
			drawCraft(g2d, 0, 1);
			drawCraft(g2d, 1, 0);
			drawCraft(g2d, 1, 1);
			drawCraft(g2d, 2, 0);
		} else if (nextCraft instanceof Hero) {
			drawCraft(g2d, 0, 0);
			drawCraft(g2d, 1, 0);
			drawCraft(g2d, 2, 0);
			drawCraft(g2d, 3, 0);
		} else if (nextCraft instanceof OrangeRicky) {
			drawCraft(g2d, 0, 0);
			drawCraft(g2d, 1, 0);
			drawCraft(g2d, 2, 0);
			drawCraft(g2d, 2, 1);
		} else if (nextCraft instanceof Rhode) {
			drawCraft(g2d, 0, 0);
			drawCraft(g2d, 1, 0);
			drawCraft(g2d, 1, 1);
			drawCraft(g2d, 2, 1);
		} else if (nextCraft instanceof SmashBoy) {
			drawCraft(g2d, 0, 0);
			drawCraft(g2d, 1, 0);
			drawCraft(g2d, 1, 1);
			drawCraft(g2d, 0, 1);
		} else {
			drawCraft(g2d, 0, 0);
			drawCraft(g2d, 1, 0);
			drawCraft(g2d, 2, 0);
			drawCraft(g2d, 1, 1);
		}
	}

	private void drawCraft(Graphics2D g2d, int x, int y) {
		Color color = g2d.getColor();

		g2d.setColor(new Color(255, 105, 180));
		double realX = theX + boardSpace * (x);
		double realY = theY + boardSpace * (1 - y);


		Rectangle2D.Double rect = new Rectangle2D.Double(
				realX,
				realY,
				boardSpace,
				boardSpace);

		g2d.fill(rect);

		g2d.setColor(new Color(255, 20, 147));
		Rectangle2D.Double smallRect = new Rectangle2D.Double(
				realX + 5,
				realY + 5,
				boardSpace - 10,
				boardSpace - 10);

		g2d.fill(smallRect);

		g2d.setColor(color);

	}

	@Override
	public void update() {
		repaint();
	}
}
