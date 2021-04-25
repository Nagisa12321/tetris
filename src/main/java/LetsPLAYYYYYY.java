import cfg.TetrisConfig;
import controller.TetrisController;
import model.TetrisModel;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import view.TetrisView;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/25 13:49
 */
public class LetsPLAYYYYYY {
	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(TetrisConfig.class);
		TetrisModel model = context.getBean(TetrisModel.class);
		TetrisController controller = context.getBean(TetrisController.class);
		TetrisView view = context.getBean(TetrisView.class);

		view.setModel(model);
		view.setController(controller);

		view.open();
	}
}
