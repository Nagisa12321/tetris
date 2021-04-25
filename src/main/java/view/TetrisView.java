package view;

import controller.TetrisController;
import model.TetrisModel;
import observer.Observer;
import pojo.TetrisState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/24 14:38
 */
public class TetrisView extends JFrame implements Observer {
	private static final int DEFAULT_WEIGTH = 500;
	private static final int DEFAULT_HEIGHT = 600;
	private static final int DEFAULT_X_LOCATION = 500;
	private static final int DEFAULT_Y_LOCATION = 100;
	private final TetrisGamePanel tetrisGamePanel;
	private TetrisModel model;
	private TetrisController controller;
	private JButton start;
	private JComboBox<String> box;

	public TetrisView() throws HeadlessException {
		tetrisGamePanel = new TetrisGamePanel();
		defaultSetting();
		addPanel();
		addListener();
	}

	public void setController(TetrisController controller) {
		this.controller = controller;
		tetrisGamePanel.setController(controller);
	}

	public void setModel(TetrisModel model) {
		model.registerObserver(this);
		this.model = model;
		this.tetrisGamePanel.setModel(model);

	}

	private void addListener() {
		start.addActionListener(e -> {
			controller.start();
		});
	}

	private void addPanel() {
		add(tetrisGamePanel, BorderLayout.WEST);
		JPanel settingPanel = new JPanel();

		JPanel scoreBoard = new JPanel();
		JPanel difficulty = new JPanel();
		JPanel tips = new JPanel();
		JPanel gameSetting = new JPanel();
		settingPanel.setPreferredSize(new Dimension(200, 600));
		scoreBoard.setPreferredSize(new Dimension(180, 280));
		difficulty.setPreferredSize(new Dimension(180, 65));
		gameSetting.setPreferredSize(new Dimension(180, 65));
		tips.setPreferredSize(new Dimension(180, 75));

		scoreBoard.setBorder(BorderFactory.createTitledBorder("score board"));
		difficulty.setBorder(BorderFactory.createTitledBorder("difficulty boxxxx :)"));
		tips.setBorder(BorderFactory.createTitledBorder("how to play? "));


		start = new JButton("( •̀ ω •́ )✧start");
		box = new JComboBox<>(new String[]{"easy", "hard", "please don't do this"});
		difficulty.add(box);
		gameSetting.add(start);

		tips.add(new JLabel("<html>[w,s,a,d] - normal play <br/>[space] - quick fall :)</html>"));

		settingPanel.add(scoreBoard);
		settingPanel.add(difficulty);
		settingPanel.add(gameSetting);
		settingPanel.add(tips);

		add(settingPanel);
	}

	@Override
	public void update() {
		TetrisState state = model.getState();
		start.setEnabled(state != TetrisState.START);
	}

	public String getDifficulty() {
		return (String) box.getSelectedItem();
	}

	public void open() {
		setVisible(true);
		setRequestFocus();
	}

	public TetrisGamePanel getTetrisGamePanel() {
		return tetrisGamePanel;
	}

	public void setRequestFocus() {
		tetrisGamePanel.requestFocus();
	}

	public void openMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "!!!!!!!!", JOptionPane.INFORMATION_MESSAGE);
	}

	// 默认设置
	private void defaultSetting() {

		// 设置图标
		ImageIcon imageIcon = new ImageIcon("src/main/resources/img/pic.jpg");
		setIconImage(imageIcon.getImage());

		// 设置标题
		// 设置方位
		setLocation(DEFAULT_X_LOCATION, DEFAULT_Y_LOCATION);
		// 设置不可变大小
		setResizable(false);
		// 设置默认长宽
		setSize(DEFAULT_WEIGTH, DEFAULT_HEIGHT);
		// 设置关闭动作
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
