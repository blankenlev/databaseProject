package carShop;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.GridLayout;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JScrollBar;
import javax.swing.JOptionPane;
import java.awt.Toolkit;

public class searchDialog extends mainSQL {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable searchResults;
	private JScrollBar scrollBar;
	public String username;

	public searchDialog(String price, String year, String model, String make, String username) throws ParseException {
		setIconImage(Toolkit.getDefaultToolkit().getImage(searchDialog.class.getResource("/carShop/carIcon.png")));
		setTitle("Search Results");
		this.username = username;
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 615, 565);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		mainSQL obj = new mainSQL();
		obj.Connection();
		String[] headers = { "Make", "Model", "Year", "Price", "View", "+ Cart" };
		DefaultTableModel searchModel = new DefaultTableModel(obj.searchCars(model, year, price, make), headers);
		searchResults = new JTable(searchModel);
		searchResults.setDefaultEditor(Object.class, null);
		searchResults.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
		searchResults.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox()));
		searchResults.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
		searchResults.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox()));
		JScrollPane js = new JScrollPane(searchResults);
		js.setVisible(true);
		getContentPane().add(js);

		JButton backButton = new JButton("Go back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainWindow mw = null;
				try {
					mw = new mainWindow(username);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				mw.setVisible(true);
				dispose();
			}
		});
		setResizable(false);
		contentPane.add(backButton, BorderLayout.SOUTH);
	}

	class ButtonRenderer extends DefaultTableCellRenderer {
		private JButton button;

		public ButtonRenderer() {
			setOpaque(true);
			button = new JButton();
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			if (isSelected) {
				setForeground(table.getSelectionForeground());
				setBackground(table.getSelectionBackground());
			} else {
				setForeground(table.getForeground());
				setBackground(UIManager.getColor("Button.background"));
			}

			button.setText((value == null) ? "" : value.toString());

			return button;
		}
	}

	class ButtonEditor extends DefaultCellEditor {
		private JButton button;
		private boolean isPushed;
		private int row = 0;

		public ButtonEditor(JCheckBox checkBox) {
			super(checkBox);
			button = new JButton();
			button.setOpaque(true);
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					fireEditingStopped();
					String buttonPressed = e.getActionCommand();
					String model = (String) searchResults.getValueAt(row, 1);
					String year = (String) searchResults.getValueAt(row, 2);
					String price = (String) searchResults.getValueAt(row, 3);
					mainSQL obj = new mainSQL();
					obj.Connection();
					if (buttonPressed.equals("View")) {
						viewCar newCar = null;
						try {
							newCar = new viewCar(obj.getCar(model, year, price));
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						newCar.setVisible(true);
					} else {
						obj.addToCart(obj.getUserID(username), model, Integer.parseInt(year), Integer.parseInt(price));
						JOptionPane.showMessageDialog(null, "Added to cart!", "Success",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
		}

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			if (isSelected) {
				button.setForeground(table.getSelectionForeground());
				button.setBackground(table.getSelectionBackground());
			} else {
				button.setForeground(table.getForeground());
				button.setBackground(table.getBackground());
			}

			button.setText((value == null) ? "" : value.toString());
			this.row = row;
			isPushed = true;
			return button;
		}

		public Object getCellEditorValue() {
			isPushed = false;
			return button.getText();
		}
	}

}
