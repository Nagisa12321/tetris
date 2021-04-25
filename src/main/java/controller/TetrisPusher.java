package controller;

import pojo.TetrisMovingDirection;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/24 23:16
 */
public class TetrisPusher implements Runnable {

	private boolean isRunning;
	private TetrisController controller;
	private int waiting;

	public TetrisPusher(TetrisController controller, int waiting) {
		this.waiting = waiting;
		this.isRunning = true;
		this.controller = controller;
	}

	public void stop() {
		isRunning = false;
	}

	@Override
	public void run() {
		while (isRunning) {
			try {
				if (waiting == -1) {
					if (Math.random() > 0.8) {
						Thread.sleep((long) (Math.random() * 1000));
						controller.turnShape();
					}
					else Thread.sleep((long) (Math.random() * 150));
				} else Thread.sleep(waiting);
				controller.move(TetrisMovingDirection.DOWN);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
