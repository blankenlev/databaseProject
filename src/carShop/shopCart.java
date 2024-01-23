package carShop;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import java.util.ArrayList;

import java.awt.GridLayout;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.AbstractCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JScrollBar;
import javax.swing.JOptionPane;
import java.awt.FlowLayout;
import java.awt.Toolkit;

public class shopCart extends JFrame {

	private JPanel contentPane;
	public JTable table;
	DefaultTableModel tableModel;
	private JButton compareButton;
	String[][] data;
	int selectedRow;
	String username;
	private JButton backButton;
	ArrayList<Integer> selectedForCompare = new ArrayList<Integer>();
	ArrayList<String[]> selectedCars = new ArrayList<String[]>();

	public shopCart(String username) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(shopCart.class.getResource("/carShop/carIcon.png")));
		setTitle("Shopping Cart");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 600, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.username = username;
		setResizable(false);

		setContentPane(contentPane);

		String[] headers = { "Make", "Model", "Year", "Price", "View", "Compare", "Delete" };
		mainSQL obj = new mainSQL();
		obj.Connection();
		data = obj.getCart(obj.getUserID(username));

		tableModel = new DefaultTableModel(data, headers);
		table = new JTable(tableModel);
		table.setFillsViewportHeight(true);
		table.setRowHeight(30);

		contentPane.setLayout(null);

		table.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
		table.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox()));

		table.getColumnModel().getColumn(5).setCellRenderer(new CheckBoxRenderer());
		table.getColumnModel().getColumn(5).setCellEditor(new CheckBoxEditor(new JCheckBox()));

		table.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
		table.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox()));

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 0, 584, 261);
		contentPane.add(scrollPane);

		compareButton = new JButton("Compare");
		compareButton.setBounds(485, 277, 89, 23);
		contentPane.add(compareButton);

		tableModel.addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent e) {
				int column = e.getColumn();
				if (column == 5) {
					int firstRow = e.getFirstRow();
					int lastRow = e.getLastRow();
					for (int i = firstRow; i <= lastRow; i++) {
						boolean isChecked = (Boolean) table.getValueAt(i, 5);
						if (isChecked) {
							selectedForCompare.add(i);
						} else {
							selectedForCompare.remove(selectedForCompare.indexOf(i));
						}
					}
				}
			}
		});
		compareButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedCars = new ArrayList<>();
				if (selectedForCompare.size() >= 3) {
					JOptionPane.showMessageDialog(null, "Please select two items only!", "Error",
							JOptionPane.WARNING_MESSAGE);
				} else {
					selectedCars.add(data[selectedForCompare.get(0)]);
					selectedCars.add(data[selectedForCompare.get(1)]);
					try {
						new compareCars(selectedCars).setVisible(true);
					} catch (MalformedURLException e1) {
						e1.printStackTrace();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		backButton = new JButton("Go Back");
		backButton.setBounds(10, 277, 89, 23);
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
		contentPane.add(backButton);
		table.setDefaultEditor(Object.class, null);
	}

	class ButtonRenderer extends JButton implements TableCellRenderer {
		public ButtonRenderer() {
			setOpaque(true);
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
			setText((column == 4) ? "View" : "Delete");
			return this;
		}
	}

	class ButtonEditor extends DefaultCellEditor {
		protected JButton button;

		private String label;

		private boolean isPushed;

		public ButtonEditor(JCheckBox checkBox) {
			super(checkBox);
			button = new JButton();
			button.setOpaque(true);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fireEditingStopped();
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
			selectedRow = row;
			label = (column == 4) ? "View" : "Delete";
			button.setText(label);
			isPushed = true;
			return button;
		}

		public Object getCellEditorValue() {
			mainSQL obj = new mainSQL();
			obj.Connection();
			String[] selectedCar = data[selectedRow];
			if (isPushed && label.equals("Delete")) {
				tableModel.removeRow(selectedRow);
				obj.deleteFromCart(obj.getUserID(username), selectedCar[1], Integer.parseInt(selectedCar[2]),
						Integer.parseInt(selectedCar[3]));
			} else if (isPushed && label.equals("View")) {
				viewCar newCar = null;
				try {
					newCar = new viewCar(obj.getCar(selectedCar[1], selectedCar[2], selectedCar[3]));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				newCar.setVisible(true);
			}
			isPushed = false;
			return new String(label);
		}

		protected void fireEditingStopped() {
			super.fireEditingStopped();
		}
	}

	class CheckBoxRenderer extends JCheckBox implements TableCellRenderer {
		public CheckBoxRenderer() {
			setHorizontalAlignment(JLabel.CENTER);
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			setSelected((value != null && ((Boolean) value).booleanValue()));
			return this;
		}
	}

	class CheckBoxEditor extends DefaultCellEditor {
		public CheckBoxEditor(JCheckBox checkBox) {
			super(checkBox);
		}
	}
}
