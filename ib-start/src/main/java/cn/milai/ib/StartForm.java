package cn.milai.ib;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;

import cn.milai.ib.conf.SystemConf;
import cn.milai.ib.mode.GameMode;

/**
 * 启动窗口
 * @author milai
 */
public class StartForm extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String FONT_NAME = "华文行楷";

	private static final String TITLE = "敌星弹雨";
	private static final Font TITLE_FONT = new Font(FONT_NAME, Font.BOLD, 55);
	private static final Font LABEL_FONT = new Font(FONT_NAME, Font.BOLD, 30);

	private static final int WIDTH = SystemConf.prorate(500);
	private static final int TITLE_HEIGHT = SystemConf.prorate(260);
	private static final int PER_MODE_HEIGHT = SystemConf.prorate(200);

	public StartForm() {
		init();
	}

	private void init() {
		List<GameMode> gameModes = IBCore.getBeansOrdered(GameMode.class);
		setSize(WIDTH, TITLE_HEIGHT + PER_MODE_HEIGHT * gameModes.size());
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JLabel title = new JLabel(TITLE);
		title.setFont(TITLE_FONT);
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		c.ipady = 30;
		c.gridx = 0;
		c.gridy = 0;
		c.weighty = 2;
		layout.addLayoutComponent(title, c);
		add(title);

		// addLayoutComponent 使用的是 layout 的一个副本，所以这里可以重复使用
		c.anchor = GridBagConstraints.NORTH;
		c.ipady = 10;
		int index = 1;
		for (GameMode gameMode : gameModes) {
			add(newGameModeLabel(layout, c, index, gameMode));
			index++;
		}

		setLayout(layout);
		this.setVisible(true);
	}

	private JLabel newGameModeLabel(GridBagLayout layout, GridBagConstraints c, int index, GameMode gameMode) {
		JLabel label = new JLabel(gameMode.name());
		label.setFont(LABEL_FONT);
		while (getFontMetrics(label.getFont()).stringWidth(label.getText()) > getWidth()) {
			label.setFont(new Font(
				label.getFont().getFontName(),
				label.getFont().getStyle(),
				label.getFont().getSize() * 2 / 3));
		}
		c.gridx = 0;
		c.gridy = index;
		c.weighty = 1;
		layout.addLayoutComponent(label, c);
		label.addMouseListener(newMouseListener(gameMode, label));
		return label;
	}

	private MouseListener newMouseListener(GameMode gameMode, JLabel label) {
		return new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				gameMode.start();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				label.setForeground(Color.RED);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				label.setForeground(Color.BLACK);
			}
		};
	}

}
