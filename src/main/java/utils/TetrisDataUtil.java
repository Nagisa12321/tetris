package utils;

import dao.TetrisScoreDao;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import pojo.TetrisScore;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/25 15:03
 */
public class TetrisDataUtil {

	public static List<TetrisScore> getScores() {
		try {
			InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
			SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();

			SqlSessionFactory factory = builder.build(is);
			SqlSession sqlSession = factory.openSession();

			TetrisScoreDao dao = sqlSession.getMapper(TetrisScoreDao.class);

			return dao.findScores();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	public static void addScore(TetrisScore score) {
		try {
			InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
			SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();

			SqlSessionFactory factory = builder.build(is);
			SqlSession sqlSession = factory.openSession();

			TetrisScoreDao dao = sqlSession.getMapper(TetrisScoreDao.class);

			dao.updateScore(score);

			sqlSession.commit();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
