package cfg;

import controller.TetrisController;
import model.TetrisModel;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import view.TetrisView;

import static org.junit.Assert.*;

public class TetrisConfigTest {
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