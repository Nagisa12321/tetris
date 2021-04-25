package model;

import craft.TetrisCraft;
import craft.crafts.*;
import observer.Observable;
import observer.Observer;
import pojo.TetrisCraftPoint;
import pojo.TetrisLine;
import pojo.TetrisState;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/24 13:22
 */
public class TetrisModel implements Observable {
	private final int width;
	private final int height;
	private final List<TetrisLine> lines;
	// 游戏状态
	private TetrisState state;
	private TetrisCraft tetrisCraft;
	private TetrisCraft nextCraft;
	private final List<Observer> observers;


	public TetrisModel(int x, int y) {
		this.observers = new ArrayList<>();
		this.state = TetrisState.NOT_START;
		this.lines = new ArrayList<>();
		this.width = x;
		this.height = y;
		this.nextCraft = randomCraft();

		checkLines();
	}

	public List<TetrisCraftPoint> getFlyingCraft() {
		if (tetrisCraft == null) return new ArrayList<>();
		return tetrisCraft.commitPoints();
	}

	public Set<TetrisCraftPoint> getCraft() {
		Set<TetrisCraftPoint> points = new HashSet<>();
		for (int i = 0; i < lines.size(); i++) {
			for (int j = 0; j < width; j++) {
				// 不为空
				if (!lines.get(i).getTetrisLinePoint(j).isEmpty())
					points.add(new TetrisCraftPoint(j, i));
			}
		}
		return points;
	}

	public void newCraft() {
		tetrisCraft = nextCraft;
		nextCraft = randomCraft();

		notifyObserver();
	}

	public TetrisLine getLine(int idx) {
		return lines.get(idx);
	}


	private void coverLines() {
		int coverLines = height - lines.size();
		for (int i = 0; i < coverLines; i++) {
			lines.add(new TetrisLine(width));
		}
		notifyObserver();
	}

	private void checkLines() {
		lines.removeIf(TetrisLine::isFull);
		// TODO: 2021/4/24 添加算分操作

		// ...
		coverLines();
		notifyObserver();

	}

	public void init() {
		lines.clear();
		checkLines();

		tetrisCraft = null;
		nextCraft = randomCraft();

		notifyObserver();
	}

	public List<TetrisLine> getLines() {
		return lines;
	}

	public void addPoints(List<TetrisCraftPoint> points) {
		for (TetrisCraftPoint point : points) {
			int idx = point.getX();
			int lines = point.getY();
			if (lines >= this.lines.size()) continue;
			this.lines.get(lines).getTetrisLinePoint(idx).setEmpty(false);
		}
		// 检查是否为满
		checkLines();
		notifyObserver();
	}

	public boolean gameOver() {
		TetrisLine tetrisLine = lines.get(height - 1);
		for (int i = 0; i < width; i++) {
			if (!tetrisLine.getTetrisLinePoint(i).isEmpty()) return true;
		}
		return false;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public TetrisState getState() {
		return state;
	}

	public void setState(TetrisState state) {
		this.state = state;
		notifyObserver();
	}

	public TetrisCraft getTetrisCraft() {
		return tetrisCraft;
	}

	private TetrisCraft randomCraft() {
		int random = (int) (Math.random() * 7);
		if (random == 0) {
			return new BlueRicky(this, new TetrisCraftPoint(this.width / 2, this.height));
		} else if (random == 1) {
			return new Cleveland(this, new TetrisCraftPoint(this.width / 2, this.height));
		} else if (random == 2) {
			return new Hero(this, new TetrisCraftPoint(this.width / 2, this.height));
		} else if (random == 3) {
			return new OrangeRicky(this, new TetrisCraftPoint(this.width / 2, this.height));
		} else if (random == 4) {
			return new Rhode(this, new TetrisCraftPoint(this.width / 2, this.height));
		} else if (random == 5) {
			return new SmashBoy(this, new TetrisCraftPoint(this.width / 2, this.height));
		} else {
			return new TeeWee(this, new TetrisCraftPoint(this.width / 2, this.height));
		}
	}

	@Override
	public void notifyObserver() {
		observers.forEach(Observer::update);
	}

	@Override
	public void registerObserver(Observer observer) {
		this.observers.add(observer);
	}
}
