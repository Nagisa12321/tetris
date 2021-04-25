package pojo;

import junit.framework.TestCase;
import org.junit.Test;

public class TetrisLineTest {
	@Test
	public void testToString() {
		TetrisLine tetrisLine = new TetrisLine(5);
		System.out.println(tetrisLine);
	}
}