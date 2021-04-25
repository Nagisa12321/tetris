package controller;

import view.TetrisGamePanel;

import javax.swing.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/24 23:55
 */
public class TetrisSpecialEffects implements Runnable {

	private final TetrisGamePanel panel;
	private final BlockingQueue<SpecialEffectsType> queue;
	private boolean isRunning;

	public TetrisSpecialEffects(TetrisGamePanel panel) {
		this.isRunning = true;
		this.panel = panel;
		this.queue = new LinkedBlockingQueue<>();
	}

	public void shake() {
		queue.offer(SpecialEffectsType.SHAKE);
	}

	public void stop() {
		isRunning = false;
	}

	@Override
	public void run() {
		while (isRunning) {
			try {
				SpecialEffectsType effectsType = queue.take();
				if (effectsType == SpecialEffectsType.SHAKE) {
					int theX = panel.getTheX();
					for (int i = 0; i < 6; i++) {
						panel.setTheX(theX + 4);
						panel.repaint();
						Thread.sleep(20);
						panel.setTheX(theX);
						panel.repaint();
						Thread.sleep(20);
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	private enum SpecialEffectsType {
		SHAKE,
		SUPER
	}
}
