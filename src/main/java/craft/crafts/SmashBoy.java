package craft.crafts;

import craft.AbstractTetrisCraft;
import model.TetrisModel;
import pojo.TetrisCraftPoint;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/24 14:29
 */
public class SmashBoy extends AbstractTetrisCraft {

	public SmashBoy(TetrisModel model, TetrisCraftPoint point1) {
		super(model,
				point1,
				new TetrisCraftPoint(point1.getX(), point1.getY() - 1),
				new TetrisCraftPoint(point1.getX() + 1, point1.getY()),
				new TetrisCraftPoint(point1.getX() + 1, point1.getY() - 1));
	}

}
