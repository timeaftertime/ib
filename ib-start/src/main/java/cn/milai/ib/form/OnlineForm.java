package cn.milai.ib.form;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.AbstractListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;

import cn.milai.ib.client.constant.SocketServerURL;
import cn.milai.ib.client.socket.SocketClient;
import cn.milai.ib.conf.SystemConf;

public class OnlineForm extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int WIDTH = SystemConf.prorate(360);
	private static final int HEIGHT = SystemConf.prorate(840);

	private SocketClient socketClient;

	public OnlineForm(String token) {
		init();
		socketClient = new SocketClient(SocketServerURL.HOST, SocketServerURL.PORT, token);
		socketClient.start();
	}

	private void init() {
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		int lableNum = 0;
		JLabel[] labels = new JLabel[lableNum];
		for (int i = 0; i < lableNum; i++) {
			labels[i] = new JLabel("用户" + i);
			labels[i].setOpaque(true);
			Icon icon = new ImageIcon(Toolkit.getDefaultToolkit().createImage("D:\\Picture\\Other\\1554215035364.jpg")
					.getScaledInstance(75, 75, Image.SCALE_DEFAULT));
			labels[i].setIcon(icon);
		}

		ListModel<JLabel> dataModel = new ResetableListModel<>(labels);
		JList<JLabel> jList = new JList<>(dataModel);
		jList.setCellRenderer(new MyCellRenderer());
		JScrollPane scrollPane = new JScrollPane(jList);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.add(scrollPane);
		setVisible(true);
	}

	private class ResetableListModel<E> extends AbstractListModel<E> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private Object[] element;

		public ResetableListModel(E[] element) {
			checkNull(element);
			this.element = element;
		}

		private void checkNull(E[] element) {
			if (element == null)
				throw new NullPointerException();
		}

		@Override
		public int getSize() {
			return element.length;
		}

		@SuppressWarnings("unchecked")
		@Override
		public E getElementAt(int index) {
			return (E) element[index];
		}
	}

	private class MyCellRenderer implements ListCellRenderer<JLabel> {

		@Override
		public Component getListCellRendererComponent(JList<? extends JLabel> list, JLabel value, int index,
				boolean isSelected, boolean cellHasFocus) {
			Color background;
			Color foreground;
			// check if this cell represents the current DnD drop location
			if (isSelected) {
				background = Color.RED;
				foreground = Color.WHITE;
				// unselected, and not the DnD drop location
			} else {
				background = Color.WHITE;
				foreground = Color.BLACK;
			}
			value.setBackground(background);
			value.setForeground(foreground);
			return value;
		}

	}
}
