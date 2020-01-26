package cn.milai.ib.form;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;

import cn.milai.ib.conf.SystemConf;
import cn.milai.ib.mode.EndlessBattleMode;
import cn.milai.ib.mode.StoryMode;

/**
 * 启动窗口
 * @author milai
 */
public class StartForm extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int WIDTH = SystemConf.prorate(600);
	private static final int HEIGHT = SystemConf.prorate(780);

	public StartForm() {
		init();
	}

	private void init() {
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JLabel title = new JLabel("敌星弹雨");
		JLabel startStory = new JLabel("故事模式");
		JLabel startEndlessBattle = new JLabel("无尽模式");

		title.setFont(new Font("华文行楷", Font.BOLD, 55));
		startStory.setFont(new Font("华文行楷", Font.BOLD, 30));
		startEndlessBattle.setFont(new Font("华文行楷", Font.BOLD, 30));
		add(title);
		add(startStory);
		add(startEndlessBattle);

		startStory.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				new StoryMode().start();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				((JLabel) e.getSource()).setForeground(Color.RED);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				((JLabel) e.getSource()).setForeground(Color.BLACK);
			}
		});
		startEndlessBattle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				new EndlessBattleMode().start();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				((JLabel) e.getSource()).setForeground(Color.RED);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				((JLabel) e.getSource()).setForeground(Color.BLACK);
			}
		});

		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.weighty = 3;
		layout.addLayoutComponent(title, c); //这里的 GridBagConstraints 只是保存一个副本
		c.anchor = GridBagConstraints.NORTH;
		c.ipady = 30;
		c.gridx = 0;
		c.gridy = 1;
		c.weighty = 1;
		layout.addLayoutComponent(startStory, c);
		c.gridx = 0;
		c.gridy = 2;
		c.weighty = 1;
		layout.addLayoutComponent(startEndlessBattle, c);
		setLayout(layout);

		this.setVisible(true);
	}

}
