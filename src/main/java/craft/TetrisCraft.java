package craft;

import pojo.TetrisCraftPoint;
import pojo.TetrisMovingDirection;

import java.util.List;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/24 13:26
 */
public interface TetrisCraft {
	/**
	 * 改变形状
	 */
	void turnShape();

	/**
	 * 往某个方向移动
	 *
	 * @param direction 移动方向
	 */
	boolean move(TetrisMovingDirection direction);

	/**
	 * 提交该craft当前的位置
	 *
	 * @return 当前的位置
	 */
	List<TetrisCraftPoint> commitPoints();
}
