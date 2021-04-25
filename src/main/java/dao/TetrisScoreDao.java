package dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import pojo.TetrisScore;

import java.util.List;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/25 15:41
 */
public interface TetrisScoreDao {

	@Select("select * from tetris.score")
	List<TetrisScore> findScores();

	@Insert("insert into tetris.score (username, score) values (#{username}, #{score})")
	void updateScore(TetrisScore score);

}
