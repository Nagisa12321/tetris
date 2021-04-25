package pojo;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/24 13:47
 */
public class TetrisLine {

	private final TetrisLinePoint[] points;

	public TetrisLine(int width) {
		this.points = new TetrisLinePoint[width];
		for (int i = 0; i < width; i++) {
			this.points[i] = new TetrisLinePoint(true, i);
		}
	}

	public TetrisLinePoint getTetrisLinePoint(int idx) {
		return points[idx];
	}

	public boolean isFull() {
		for (TetrisLinePoint point : points) {
			if (point.isEmpty()) return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TetrisLine[");

		for (int i = 0; i < points.length; i++) {
			builder.append(points[i].isEmpty() ? "O" : "X");
			if (i != points.length - 1) builder.append(" - ");
		}
		builder.append(']');

		return builder.toString();
	}
}
