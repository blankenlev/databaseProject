package carShop;

import java.awt.EventQueue;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.*;
import java.util.Date;
import java.util.Calendar;
import java.awt.Toolkit;

public class mainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel mainPane;
	private JTable cart;
	JComboBox modelDropdown;
	public JSpinner yearSpinner;
	public JSpinner priceSpinner;
	public String makeSelected;
	public String modelSelected;

	/**
	 * Create the frame.
	 * 
	 * @throws ParseException
	 * 
	 */
	public mainWindow(String username) throws ParseException {
		setIconImage(Toolkit.getDefaultToolkit().getImage(mainWindow.class.getResource("/carShop/carIcon.png")));
		setTitle("Car Shop");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		mainPane = new JPanel();
		mainPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(mainPane);
		mainPane.setLayout(null);
		setResizable(false);

		JLabel signedinLabel = new JLabel("");
		signedinLabel.setOpaque(true);
		signedinLabel.setBackground(SystemColor.activeCaption);
		signedinLabel.setHorizontalAlignment(SwingConstants.CENTER);
		signedinLabel.setBounds(0, 0, 184, 46);
		mainPane.add(signedinLabel);

		signedinLabel.setText("Signed in as: " + username);

		mainSQL obj = new mainSQL();
		obj.Connection();
		String[] headers = { "Make", "Model", "Year" };
		DefaultTableModel model = new DefaultTableModel(obj.getCart(obj.getUserID(username)), headers);
		cart = new JTable(model);
		cart.setColumnSelectionAllowed(true);
		cart.setCellSelectionEnabled(true);
		cart.setBackground(new Color(0, 255, 255));
		cart.setDefaultEditor(Object.class, null);
		cart.setBounds(0, 87, 174, 341);
		JScrollPane js = new JScrollPane(cart);
		js.setBounds(0, 87, 184, 221);
		js.setVisible(true);
		mainPane.add(js);

		JLabel shoppingLabel = new JLabel("Shopping  Cart");
		shoppingLabel.setOpaque(true);
		shoppingLabel.setBackground(new Color(0, 255, 0));
		shoppingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		shoppingLabel.setBounds(0, 43, 184, 26);
		mainPane.add(shoppingLabel);

		JLabel lblNewLabel_1 = new JLabel("Search:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(203, 16, 79, 30);
		mainPane.add(lblNewLabel_1);

		modelDropdown = new JComboBox();
		modelDropdown.setBounds(324, 100, 149, 22);
		modelDropdown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox) e.getSource();
				modelSelected = (String) cb.getSelectedItem();
			}
		});
		mainPane.add(modelDropdown);

		JComboBox makeDropdown = new JComboBox();
		makeDropdown.setModel(new DefaultComboBoxModel(obj.getMakes()));
		makeDropdown.setBounds(203, 100, 99, 22);
		mainPane.add(makeDropdown);
		makeDropdown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox) e.getSource();
				makeSelected = (String) cb.getSelectedItem();
				modelDropdown.setModel(new DefaultComboBoxModel(obj.getModels(makeSelected)));
			}
		});

		JLabel makeLabel = new JLabel("Make");
		makeLabel.setBounds(203, 75, 46, 14);
		mainPane.add(makeLabel);

		JLabel modelLabel = new JLabel("Model");
		modelLabel.setBounds(324, 75, 46, 14);
		mainPane.add(modelLabel);

		JLabel priceLabel = new JLabel("Price");
		priceLabel.setBounds(203, 165, 46, 14);
		mainPane.add(priceLabel);

		JLabel yearLabel = new JLabel("Year");
		yearLabel.setBounds(324, 165, 46, 14);
		mainPane.add(yearLabel);

		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchDialog sd = null;
				try {
					sd = new searchDialog(priceSpinner.getValue().toString(), yearSpinner.getValue().toString(),
							((modelSelected != (null)) ? modelSelected : "All"), makeSelected, username);
					dispose();
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				sd.setVisible(true);
			}
		});

		searchButton.setBounds(485, 331, 89, 23);
		mainPane.add(searchButton);

		ButtonGroup priceGroup = new ButtonGroup();

		ButtonGroup yearGroup = new ButtonGroup();

		JButton btnNewButton = new JButton("View Whole Cart");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shopCart sc = null;
				sc = new shopCart(username);
				dispose();
				sc.setVisible(true);
			}
		});
		btnNewButton.setBounds(15, 325, 154, 23);
		mainPane.add(btnNewButton);

		priceSpinner = new JSpinner();
		priceSpinner.setModel(new SpinnerListModel(
				new String[] { "All", "< $5,000", "> $5,000", "< $10,000", "> $10,000", "< $20,000", "> $20,000",
						"< $50,000", "> $50,000", "< $100,000", "> $100,000", "< $500,000", "> $500,000" }));
		priceSpinner.setBounds(203, 190, 98, 22);
		mainPane.add(priceSpinner);
		priceSpinner.commitEdit();

		yearSpinner = new JSpinner();
		yearSpinner.setModel(new SpinnerListModel(new String[] { "All", "< 2000", "> 2000", "< 2010", "> 2010" }));
		yearSpinner.setBounds(324, 190, 98, 22);
		mainPane.add(yearSpinner);

		JButton btnNewButton_1 = new JButton("Logout");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new login().setVisible(true);
				dispose();
			}
		});
		btnNewButton_1.setBounds(485, 12, 89, 23);
		mainPane.add(btnNewButton_1);
		yearSpinner.commitEdit();

	}
}
