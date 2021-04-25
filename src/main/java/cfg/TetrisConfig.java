package cfg;

import controller.TetrisController;
import model.TetrisModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import view.TetrisView;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/24 15:21
 */
@Configuration
public class TetrisConfig {

	@Bean
	public TetrisModel tetrisModel() {
		return new TetrisModel(10, 20, true);
	}

	@Bean
	public TetrisController tetrisController() {
		return new TetrisController(tetrisModel(), tetrisView());
	}

	@Bean
	public TetrisView tetrisView() {
		return new TetrisView();
	}

}
