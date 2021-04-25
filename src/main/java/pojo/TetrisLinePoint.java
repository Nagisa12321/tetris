package pojo;

import java.util.StringJoiner;

/**
 * 俄罗斯方块中的每个格子
 *
 * @author jtchen
 * @version 1.0
 * @date 2021/4/24 13:31
 */
public class TetrisLinePoint {
	private final int idx;
	private boolean empty;

	public TetrisLinePoint(boolean empty, int idx) {
		this.empty = empty;
		this.idx = idx;
	}

	public boolean isEmpty() {
		return empty;
	}

	public void setEmpty(boolean empty) {
		this.empty = empty;
	}

	public int getIdx() {
		return idx;
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", TetrisLinePoint.class.getSimpleName() + "[", "]")
				.add("empty=" + empty)
				.add("idx=" + idx)
				.toString();
	}
}
