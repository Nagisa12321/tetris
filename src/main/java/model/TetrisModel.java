package model;

import craft.TetrisCraft;
import craft.crafts.*;
import observer.Observable;
import observer.Observer;
import pojo.TetrisCraftPoint;
import pojo.TetrisLine;
import pojo.TetrisScore;
import pojo.TetrisState;
import utils.TetrisDataUtil;

import java.util.*;

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
	private final TreeSet<TetrisScore> highScores;
	private final boolean load; // 是否从数据库加载
	private int nowScore;


	public TetrisModel(int x, int y, boolean load) {
		this.observers = new ArrayList<>();
		this.state = TetrisState.NOT_START;
		this.lines = new ArrayList<>();
		this.width = x;
		this.height = y;
		this.nextCraft = randomCraft();
		this.highScores = new TreeSet<>((o1, o2) -> o2.getScore() - o1.getScore());
		this.load = load;
		this.nowScore = 0;

		if (load) {
			// 从数据库里边拿出成绩
			List<TetrisScore> scores = TetrisDataUtil.getScores();
			highScores.addAll(scores);
		}

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

	public TreeSet<TetrisScore> getHighScores() {
		return highScores;
	}

	public void addScore(TetrisScore score) {
		highScores.add(score);
		notifyObserver();
	}

	private void coverLines() {
		int coverLines = height - lines.size();
		for (int i = 0; i < coverLines; i++) {
			lines.add(new TetrisLine(width));
		}
		notifyObserver();
	}

	public TetrisCraft getNextCraft() {
		return nextCraft;
	}

	private void checkLines() {
		int removeLines = 0;
		Iterator<TetrisLine> iterator = lines.iterator();
		while (iterator.hasNext()) {
			TetrisLine line = iterator.next();
			if (line.isFull()) {
				iterator.remove();
				removeLines++;
			}
		}
		// TODO: 2021/4/24 添加算分操作
		/*
			1.消除1行得10分，因为单行消除时最简单的，分数高不到哪去。
			2.消除2行得30分，分数开始激增了，30分还不错。
			3.消除3行得60分，分数更高，但是难度也是随之增加。
			4.消除4行得100分，用这个来打高分是最快的，但是一不小心就可能挂掉。
		 */
		if (removeLines == 1) nowScore += 10;
		else if (removeLines == 2) nowScore += 30;
		else if (removeLines == 3) nowScore += 60;
		else if (removeLines == 4) nowScore += 100;
		else nowScore += 5;

		coverLines();
		notifyObserver();
	}

	public void init() {
		this.nowScore = 0;
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

	public int getNowScore() {
		return nowScore;
	}

	public boolean isLoad() {
		return load;
	}
}
