import org.junit.Test;

import java.util.Arrays;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/25 12:21
 */
public class TestRound {
	@Test
	public void test1() {
		int[] a = {	1, 1, 1,
					0, 0, 1,
					0, 0, 0	};
		int[] b = {	2, 5, 8,
					1, 4, 7,
					0, 3, 6	};
		int[] newA = new int[a.length];

		for (int i = 0; i < 9; i++) {
			newA[i] = a[b[i]];
		}

		System.out.println(Arrays.toString(newA));

	}
}
