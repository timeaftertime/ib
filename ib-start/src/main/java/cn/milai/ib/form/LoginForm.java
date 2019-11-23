package cn.milai.ib.form;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.alibaba.fastjson.JSONObject;

import cn.milai.ib.client.constant.HTTPServerURL;
import cn.milai.ib.client.constant.ParamName;
import cn.milai.ib.client.constant.ResponseCode;
import cn.milai.ib.client.util.RequestUtil;
import cn.milai.ib.client.util.RequestUtil.Entry;
import cn.milai.ib.conf.SystemConf;
import cn.milai.ib.util.StringUtil;

public class LoginForm extends GameForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int WIDTH = SystemConf.prorate(600);
	private static final int HEIGHT = SystemConf.prorate(420);

	private static final int USERNAME_MAX_LEN = 12;
	private static final int PASSWORD_MAX_LEN = 12;

	private JTextField jtfUsername;
	private JPasswordField jtfPassword;
	private JButton submit;
	private GameForm registerForm = new ReigisterForm(this);

	public LoginForm() {
		init();
	}

	private void init() {
		setTitle("登录");
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		GridBagLayout layout = new GridBagLayout();

		JLabel title = new JLabel("登录");
		title.setFont(new Font("微软雅黑", Font.BOLD, 30));
		JPanel usernamePane = new JPanel(new BorderLayout());
		{
			JLabel label = new JLabel("用户名");
			jtfUsername = new JTextField(12);
			Font font = new Font("楷体", Font.BOLD, 15);
			label.setFont(font);
			jtfUsername.setFont(font);
			usernamePane.add(label, BorderLayout.WEST);
			usernamePane.add(jtfUsername, BorderLayout.EAST);
		}
		JPanel passwordPane = new JPanel(new BorderLayout());
		{
			JLabel label = new JLabel("密  码");
			jtfPassword = new JPasswordField(12);
			jtfPassword.setEchoChar('*');
			Font font = new Font("楷体", Font.BOLD, 15);
			label.setFont(font);
			jtfPassword.setFont(font);
			passwordPane.add(label, BorderLayout.WEST);
			passwordPane.add(jtfPassword, BorderLayout.EAST);
		}
		JPanel buttonPane = new JPanel(new GridLayout(2, 1));
		{
			submit = new JButton("登  录");
			submit.addActionListener(this::login);
			Font font = new Font("楷体", Font.BOLD, 15);
			submit.setFont(font);
			buttonPane.add(submit, BorderLayout.CENTER);
			JLabel toRegister = new JLabel("<html><font color='black'><a href>注册</a></font></html>");
			toRegister.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					setVisible(false);
					registerForm.setVisible(true);
				}
			});
			buttonPane.add(toRegister, BorderLayout.SOUTH);
		}

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.weighty = 1;
		constraints.gridx = 0;
		constraints.gridy = 0;
		layout.addLayoutComponent(title, constraints);
		constraints.gridy = 1;
		layout.addLayoutComponent(usernamePane, constraints);
		constraints.gridy = 2;
		layout.addLayoutComponent(passwordPane, constraints);
		constraints.gridy = 3;
		layout.addLayoutComponent(buttonPane, constraints);

		add(title);
		add(usernamePane);
		add(passwordPane);
		add(buttonPane);
		setLayout(layout);

		setVisible(true);
	}

	private void login(ActionEvent e) {
		if (!inputValid()) {
			JOptionPane.showMessageDialog(this, "请检查用户名密码格式是否合法");
			return;
		}
		submit.setText("登录中...");
		submit.setEnabled(false);
		SwingUtilities.invokeLater(() -> {
			JSONObject result = null;
			try {
				result = RequestUtil.post(HTTPServerURL.LOGIN, new Entry("username", jtfUsername.getText()),
						new Entry("password", new String(jtfPassword.getPassword())));
				if (!result.containsKey(ParamName.CODE)) {
					JOptionPane.showMessageDialog(this, "服务器异常");
					return;
				}
				switch (ResponseCode.parse(result.getIntValue(ParamName.CODE))) {
				case SUCCESS:
					String token = result.getString(ParamName.TOKEN);
					this.dispose();
					SwingUtilities.invokeLater(() -> {
						new OnlineForm(token);
					});
					break;
				case USERNAME_NOT_EXISTS:
				case PASSWORD_ERROR:
					JOptionPane.showMessageDialog(this, "用户名或密码错误");
					break;
				default:
					break;
				}
			} catch (IOException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(this, "连接服务器失败，请检查网络");
				return;
			} finally {
				submit.setText("登  录");
				submit.setEnabled(true);
			}
		});
	}

	private boolean inputValid() {
		String username = jtfUsername.getText();
		if (!StringUtil.lengthBetween(username, 0, USERNAME_MAX_LEN))
			return false;
		String password = new String(jtfPassword.getPassword());
		if (!StringUtil.lengthBetween(password, 0, PASSWORD_MAX_LEN))
			return false;
		return true;
	}
}
