package cn.milai.ib.client.game.form;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import cn.milai.ib.client.game.conf.FormSizeConf;
import cn.milai.ib.client.game.mode.EndlessBattleMode;
import cn.milai.ib.client.game.mode.OnlineMode;
import cn.milai.ib.client.game.mode.StoryMode;

public class StartForm extends GameForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StartForm() {
		init();
		
	}
	
	private void init() {
		setSize(FormSizeConf.START_WIDTH, FormSizeConf.START_HEIGHT);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JLabel title = new JLabel("敌星弹雨");
		JLabel startStory = new JLabel("故事模式");
		JLabel startEndlessBattle = new JLabel("无尽模式");
		JLabel startOnline = new JLabel("联机模式");
		
		title.setFont(new Font("华文行楷", Font.BOLD, 55));
		startStory.setFont(new Font("华文行楷", Font.BOLD, 30));
		startEndlessBattle.setFont(new Font("华文行楷", Font.BOLD, 30));
		startOnline.setFont(new Font("华文行楷", Font.BOLD, 30));
		add(title);
		add(startStory);
		add(startEndlessBattle);
		add(startOnline);
		
		startStory.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				new Thread(new StoryMode()).start();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				((JLabel)e.getSource()).setForeground(Color.RED);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				((JLabel)e.getSource()).setForeground(Color.BLACK);
			}
		});
		startEndlessBattle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				new Thread(new EndlessBattleMode()).start();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				((JLabel)e.getSource()).setForeground(Color.RED);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				((JLabel)e.getSource()).setForeground(Color.BLACK);
			}
		});
		startOnline.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				new Thread(new OnlineMode()).start();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				((JLabel)e.getSource()).setForeground(Color.RED);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				((JLabel)e.getSource()).setForeground(Color.BLACK);
			}
		});
		
		
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.weighty = 9;
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
		c.gridx = 0;
		c.gridy = 3;
		c.weighty = 4;
		layout.addLayoutComponent(startOnline, c);
		setLayout(layout);
		
		this.setVisible(true);
	}

}
