package craft.crafts;

import craft.AbstractTetrisCraft;
import model.TetrisModel;
import pojo.TetrisCraftPoint;

import java.util.Set;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/24 14:29
 */
public class TeeWee extends AbstractTetrisCraft {

	public TeeWee(TetrisModel model, TetrisCraftPoint point1) {
		super(model,
				point1,
				new TetrisCraftPoint(point1.getX() - 1, point1.getY()),
				new TetrisCraftPoint(point1.getX() + 1, point1.getY()),
				new TetrisCraftPoint(point1.getX(), point1.getY() - 1));
	}

	@Override
	public void turnShape() {
		Set<TetrisCraftPoint> crafts = model.getCraft();
		int x = point1.getX();
		int y = point1.getY();
		if (state == CraftState.state1) {

			int[][] map = {
					{x, y},
					{x, y + 1},
					{x, y - 1},
					{x - 1, y}
			};

			if (setNewCraft(crafts, map)) return;
			state = CraftState.state2;
		} else if (state == CraftState.state2) {
			int[][] map = {
					{x, y - 1},
					{x + 1, y - 1},
					{x - 1, y - 1},
					{x, y}
			};

			if (setNewCraft(crafts, map)) return;
			state = CraftState.state3;
		} else if (state == CraftState.state3) {
			int[][] map = {
					{x, y + 1},
					{x, y},
					{x, y + 2},
					{x + 1, y + 1}
			};

			if (setNewCraft(crafts, map)) return;
			state = CraftState.state4;
		} else {
			int[][] map = {
					{x, y},
					{x - 1, y},
					{x + 1, y},
					{x, y - 1}
			};

			if (setNewCraft(crafts, map)) return;
			state = CraftState.state1;
		}
		model.notifyObserver();
	}
}
