package carShop;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Toolkit;

public class login extends mainSQL {

	private static final long serialVersionUID = 1L;
	private JPanel loginPane;
	private JTextField unameText;
	private JPasswordField pwordText;
	public mainWindow mw;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					login frame = new login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public login() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(login.class.getResource("/carShop/carIcon.png")));
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 375, 181);
		loginPane = new JPanel();
		loginPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setResizable(false);

		setContentPane(loginPane);
		loginPane.setLayout(null);

		JLabel unameLabel = new JLabel("Username:");
		unameLabel.setBounds(5, 6, 65, 36);
		loginPane.add(unameLabel);

		unameText = new JTextField();
		unameText.setBounds(80, 6, 274, 36);
		loginPane.add(unameText);
		unameText.setColumns(10);

		JLabel pwordLabel = new JLabel("Password:");
		pwordLabel.setBounds(5, 48, 65, 36);
		loginPane.add(pwordLabel);

		pwordText = new JPasswordField();
		pwordText.setBounds(80, 48, 274, 36);
		loginPane.add(pwordText);

		JLabel errorLabel = new JLabel("");
		errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		errorLabel.setBounds(43, 103, 278, 14);
		loginPane.add(errorLabel);

		JButton signupButton = new JButton("Sign Up");
		signupButton.setBounds(5, 95, 172, 36);
		signupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				errorLabel.setText("");
				String uname = unameText.getText();
				String pword = pwordText.getText();
				mainSQL obj = new mainSQL();
				obj.Connection();
				String result = obj.signUp(uname, pword);
				errorLabel.setText(result);
			}
		});
		loginPane.add(signupButton);

		JButton loginButton = new JButton("Login");
		loginButton.setBounds(182, 95, 172, 36);
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String uname = unameText.getText();
				String pword = pwordText.getText();
				mainSQL obj = new mainSQL();
				obj.Connection();
				String result = obj.login(uname, pword);
				if (result.isBlank()) {
					dispose();
					try {
						mw = new mainWindow(username);
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
					mw.setVisible(true);
				} else {
					errorLabel.setText(result);
					;
				}
			}
		});
		loginPane.add(loginButton);
	}
}
