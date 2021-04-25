package controller;

import craft.TetrisCraft;
import model.TetrisModel;
import pojo.TetrisCraftPoint;
import pojo.TetrisMovingDirection;
import pojo.TetrisScore;
import pojo.TetrisState;
import utils.TetrisDataUtil;
import view.TetrisView;

import java.util.List;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/24 14:37
 */
public class TetrisController {
	private final TetrisModel model;
	private final TetrisView view;
	private TetrisPusher pusher;
	private TetrisSpecialEffects effects;

	public TetrisController(TetrisModel model, TetrisView view) {
		this.model = model;
		this.view = view;
	}

	public void turnShape() {
		model.getTetrisCraft().turnShape();
	}

	public void start() {
		TetrisState state = model.getState();
		if (state == TetrisState.START) {
			view.openMessage("you just have started, and you are in the game, why start again?");
		} else if (view.getPlayerName().equals("")) {
			view.openMessage("please input your name");
		} else {
			String difficulty = view.getDifficulty();
			if (difficulty == null) {
				view.openMessage("you have not chose any difficulty");
				return;
			}
			if (difficulty.equals("easy")) this.pusher = new TetrisPusher(this, 600);
			else if (difficulty.equals("hard")) this.pusher = new TetrisPusher(this, 80);
			else this.pusher = new TetrisPusher(this, -1);
			this.effects = new TetrisSpecialEffects(view.getTetrisGamePanel());
			new Thread(pusher).start();
			new Thread(effects).start();
			model.setState(TetrisState.START);
			model.newCraft();
		}

		view.setRequestFocus();
	}

	public void quackFall() {
		if (model.getState() != TetrisState.START) {
			view.openMessage("you have not start the game!");
			return;
		}
		TetrisCraft tetrisCraft = model.getTetrisCraft();
		while (tetrisCraft.move(TetrisMovingDirection.DOWN)) {
		}
		effects.shake();
		addPoints(tetrisCraft);

		model.notifyObserver();
	}

	private void addPoints(TetrisCraft tetrisCraft) {
		List<TetrisCraftPoint> tetrisCraftPoints = tetrisCraft.commitPoints();
		model.addPoints(tetrisCraftPoints);

		if (model.gameOver()) {
			model.setState(TetrisState.OVER);

			TetrisScore tetrisScore = new TetrisScore(model.getNowScore(), view.getPlayerName());
			if (model.isLoad()) {
				TetrisDataUtil.addScore(tetrisScore);
			}
			model.addScore(tetrisScore);

			pusher.stop();
			effects.stop();
			view.openMessage("game over!");
			model.init();
		} else model.newCraft();
	}

	public void move(TetrisMovingDirection direction) {
		if (model.getState() != TetrisState.START) {
			view.openMessage("you have not start the game!");
			return;
		}
		TetrisCraft tetrisCraft = model.getTetrisCraft();
		if (!tetrisCraft.move(direction)) {
			addPoints(tetrisCraft);
		}

		model.notifyObserver();
	}
}
