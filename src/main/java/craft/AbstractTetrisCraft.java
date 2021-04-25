package craft;

import model.TetrisModel;
import pojo.TetrisCraftPoint;
import pojo.TetrisMovingDirection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/24 14:09
 */
public abstract class AbstractTetrisCraft implements TetrisCraft {
	protected final TetrisCraftPoint point1;
	private final TetrisCraftPoint point2;
	private final TetrisCraftPoint point3;
	private final TetrisCraftPoint point4;
	protected final List<TetrisCraftPoint> points;
	protected final TetrisModel model;
	protected CraftState state;

	public AbstractTetrisCraft(TetrisModel model, TetrisCraftPoint point1, TetrisCraftPoint point2, TetrisCraftPoint point3, TetrisCraftPoint point4) {
		this.state = CraftState.state1;
		this.model = model;
		this.point1 = point1;
		this.point2 = point2;
		this.point3 = point3;
		this.point4 = point4;
		this.points = new ArrayList<>();
		points.add(point1);
		points.add(point2);
		points.add(point3);
		points.add(point4);
	}


	@Override
	public boolean move(TetrisMovingDirection direction) {
		Set<TetrisCraftPoint> modelPoints = model.getCraft();
		if (direction == TetrisMovingDirection.DOWN) {

			for (TetrisCraftPoint point : points) {
				if (modelPoints.contains(new TetrisCraftPoint(point.getX(), point.getY() - 1))) return false;
				else if (point.getY() == 0) return false;
			}
			for (TetrisCraftPoint point : points) {
				point.setY(point.getY() - 1);
			}

			return true;
		} else if (direction == TetrisMovingDirection.LEFT) {
			for (TetrisCraftPoint point : points) {
				if (modelPoints.contains(new TetrisCraftPoint(point.getX() - 1, point.getY()))) return true;
				else if (point.getX() == 0) return true;
			}
			for (TetrisCraftPoint point : points) {
				point.setX(point.getX() - 1);
			}
			return true;
		} else {
			for (TetrisCraftPoint point : points) {
				if (modelPoints.contains(new TetrisCraftPoint(point.getX() + 1, point.getY()))) return true;
				else if (point.getX() == model.getWidth() - 1) return true;
			}
			for (TetrisCraftPoint point : points) {
				point.setX(point.getX() + 1);
			}
			return true;
		}
	}

	@Override
	public List<TetrisCraftPoint> commitPoints() {
		return Arrays.asList(point1, point2, point3, point4);
	}

	protected boolean setNewCraft(Set<TetrisCraftPoint> crafts, int[][] map) {
		for (int i = 0; i < 4; i++) {
			int[] pair = map[i];
			int newx = pair[0];
			int newy = pair[1];
			if (newx < 0 || newx >= model.getWidth()) return true;
			if (newy < 0) return true;
			if (crafts.contains(new TetrisCraftPoint(newx, newy))) return true;
		}

		for (int i = 0; i < 4; i++) {
			points.get(i).setX(map[i][0]);
			points.get(i).setY(map[i][1]);
		}
		return false;
	}

	@Override
	public void turnShape() {

	}

	protected enum CraftState {
		state1,
		state2,
		state3,
		state4
	}
}
