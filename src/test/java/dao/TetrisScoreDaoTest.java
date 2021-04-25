package dao;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import pojo.TetrisScore;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.*;

public class TetrisScoreDaoTest {

	@Test
	public void testFindAll() throws IOException {
		InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();

		SqlSessionFactory factory = builder.build(is);
		SqlSession sqlSession = factory.openSession();

		TetrisScoreDao dao = sqlSession.getMapper(TetrisScoreDao.class);

		dao.findScores().forEach(System.out::println);

		dao.updateScore(new TetrisScore(500, "test1"));

		sqlSession.commit();
	}
}