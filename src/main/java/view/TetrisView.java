package view;

import controller.TetrisController;
import model.TetrisModel;
import observer.Observer;
import pojo.TetrisScore;
import pojo.TetrisState;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.Vector;

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
	private final TetrisNextCraftPanel nextCraftPanel;
	private TetrisModel model;
	private TetrisController controller;
	private JButton start;
	private JComboBox<String> box;
	private JList<String> scores;
	private JTextField name;
	private JLabel nowScore;

	public TetrisView() throws HeadlessException {
		nextCraftPanel = new TetrisNextCraftPanel();
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
		this.nextCraftPanel.setModel(model);

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
		scoreBoard.setPreferredSize(new Dimension(180, 120));
		difficulty.setPreferredSize(new Dimension(180, 65));
		gameSetting.setPreferredSize(new Dimension(180, 140));
		tips.setPreferredSize(new Dimension(180, 75));

		scoreBoard.setBorder(BorderFactory.createTitledBorder("score board"));
		difficulty.setBorder(BorderFactory.createTitledBorder("difficulty boxxxx :)"));
		tips.setBorder(BorderFactory.createTitledBorder("how to play? "));

		nowScore = new JLabel("0");
		nowScore.setHorizontalAlignment(JLabel.CENTER);
		nowScore.setBackground(settingPanel.getBackground());
		nowScore.setPreferredSize(new Dimension(180, 45));
		nowScore.setBorder(BorderFactory.createTitledBorder("now score? "));

		name = new JTextField();
		name.setBackground(settingPanel.getBackground());
		name.setPreferredSize(new Dimension(180, 45));
		name.setBorder(BorderFactory.createTitledBorder("your name? "));
		start = new JButton("( •̀ ω •́ )✧start");
		box = new JComboBox<>(new String[]{"easy", "hard", "please don't do this"});

		scores = new JList<>();
		scores.setBackground(settingPanel.getBackground());
		scores.setPreferredSize(new Dimension(160, 80));
		scores.setEnabled(false);
		scoreBoard.add(scores);

		difficulty.add(box);
		gameSetting.add(nowScore);
		gameSetting.add(name);
		gameSetting.add(start);

		tips.add(new JLabel("<html>[w,s,a,d] - normal play <br/>[space] - quick fall :)</html>"));

		settingPanel.add(scoreBoard);
		settingPanel.add(nextCraftPanel);
		settingPanel.add(difficulty);
		settingPanel.add(gameSetting);
		settingPanel.add(tips);

		add(settingPanel);
	}

	@Override
	public void update() {

		TetrisState state = model.getState();
		start.setEnabled(state != TetrisState.START);
		name.setEnabled(state != TetrisState.START);
		if (state == TetrisState.START) nowScore.setText(String.valueOf(model.getNowScore()));
		else nowScore.setText("0");

		Vector<String> vt = new Vector<>();
		Iterator<TetrisScore> iterator = model.getHighScores().iterator();

		for (int i = 0; i < 4 && iterator.hasNext(); i++) {
			TetrisScore score = iterator.next();
			vt.add("[" + score.getPlayerName() + "] - " + score.getScore());
		}

		scores.setListData(vt);
	}

	public String getPlayerName() {
		return name.getText();
	}

	public String getDifficulty() {
		return (String) box.getSelectedItem();
	}

	public void open() {
		setVisible(true);
		model.notifyObserver();
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
