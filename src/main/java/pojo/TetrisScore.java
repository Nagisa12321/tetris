package pojo;

import java.util.StringJoiner;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/25 15:05
 */
public class TetrisScore {
	private int score;
	private String username;

	public TetrisScore(int score, String playerName) {
		this.score = score;
		this.username = playerName;
	}

	public TetrisScore() {
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getPlayerName() {
		return username;
	}

	public void setPlayerName(String playerName) {
		this.username = playerName;
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", TetrisScore.class.getSimpleName() + "[", "]")
				.add("score=" + score)
				.add("playerName=" + username)
				.toString();
	}
}
