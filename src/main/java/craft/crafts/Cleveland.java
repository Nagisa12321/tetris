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
public class Cleveland extends AbstractTetrisCraft {

	public Cleveland(TetrisModel model, TetrisCraftPoint point1) {
		super(model,
				point1,
				new TetrisCraftPoint(point1.getX(), point1.getY() - 1),
				new TetrisCraftPoint(point1.getX() - 1, point1.getY()),
				new TetrisCraftPoint(point1.getX() + 1, point1.getY() - 1));
	}

	@Override
	public void turnShape() {
		Set<TetrisCraftPoint> crafts = model.getCraft();
		int x = point1.getX();
		int y = point1.getY();
		if (state == CraftState.state1) {
			int[][] map = {
					{x + 1, y},
					{x, y},
					{x + 1, y + 1},
					{x, y - 1}
			};

			if (setNewCraft(crafts, map)) return;
			state = CraftState.state2;
		} else {
			int[][] map = {
					{x - 1, y},
					{x - 1, y - 1},
					{x - 2, y},
					{x, y - 1}
			};

			if (setNewCraft(crafts, map)) return;
			state = CraftState.state1;
		}
		model.notifyObserver();
	}
}
