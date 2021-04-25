package pojo;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/24 13:46
 */
public class TetrisCraftPoint {
	private int x;
	private int y;

	public TetrisCraftPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", TetrisCraftPoint.class.getSimpleName() + "[", "]")
				.add("x=" + x)
				.add("y=" + y)
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof TetrisCraftPoint)) return false;
		TetrisCraftPoint point = (TetrisCraftPoint) o;
		return x == point.x && y == point.y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
}
