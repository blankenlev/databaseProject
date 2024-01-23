package carShop;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.text.DecimalFormat;
import java.net.*;
import java.util.ArrayList;
import java.awt.*;

public class viewCar extends JFrame {

	private JPanel contentPane;
	private String make;
	private String model;
	private String year;
	private String fuel;
	private String horsepower;
	private String cylinders;
	private String transmission;
	private String driven_wheels;
	private String doors;
	private String category;
	private String size;
	private String style;
	private String highway_mpg;
	private String city_mpg;
	private String popularity;
	private String price;

	public viewCar(ArrayList<String> info) throws Exception {
		setIconImage(Toolkit.getDefaultToolkit().getImage(viewCar.class.getResource("/carShop/carIcon.png")));
		make = info.get(0);
		model = info.get(1);
		year = info.get(2);
		fuel = info.get(3);
		horsepower = info.get(4);
		cylinders = info.get(5);
		transmission = info.get(6);
		driven_wheels = info.get(7);
		doors = info.get(8);
		category = info.get(9);
		size = info.get(10);
		style = info.get(11);
		highway_mpg = info.get(12);
		city_mpg = info.get(13);
		popularity = info.get(14);
		price = info.get(15);
		setBounds(100, 100, 375, 445);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setTitle(make + " " + model);
		setContentPane(contentPane);
		JLabel label = new JLabel();
		label.setBounds(10, 11, 339, 187);

		URL url = new URL(BingWebSearch.searchFor("Stock " + year + " " + make + " " + model + " " + style));
		ImageIcon icon = new ImageIcon(url);
		contentPane.setLayout(null);
		Image img = icon.getImage();
		Image resizedImg = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon resizedIcon = new ImageIcon(resizedImg);
		label.setIcon(resizedIcon);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);
		contentPane.add(label);

		JLabel modelLabel = new JLabel("Model: " + model);
		modelLabel.setBounds(173, 209, 176, 14);
		contentPane.add(modelLabel);

		JLabel makeLabel = new JLabel("Make: " + make);
		makeLabel.setBounds(10, 209, 153, 14);
		contentPane.add(makeLabel);

		JLabel yearLabel = new JLabel("Year: " + year);
		yearLabel.setBounds(10, 234, 153, 14);
		contentPane.add(yearLabel);

		DecimalFormat formatter = new DecimalFormat("#,###.00");
		JLabel priceLabel = new JLabel("Price: $" + formatter.format(Double.parseDouble(price)));
		priceLabel.setBounds(173, 234, 176, 14);
		contentPane.add(priceLabel);

		JLabel fuelLabel = new JLabel("Fuel: " + fuel.split("\\(")[0]);
		fuelLabel.setBounds(10, 259, 153, 14);
		contentPane.add(fuelLabel);

		JLabel horsepowerLabel = new JLabel("Horsepower: " + horsepower);
		horsepowerLabel.setBounds(173, 259, 176, 14);
		contentPane.add(horsepowerLabel);

		JLabel cylindersLabel = new JLabel("Cylinders: " + cylinders);
		cylindersLabel.setBounds(10, 284, 153, 14);
		contentPane.add(cylindersLabel);

		JLabel transmissionLabel = new JLabel("Transmission: " + transmission);
		transmissionLabel.setBounds(173, 284, 176, 14);
		contentPane.add(transmissionLabel);

		JLabel driven_wheelsLabel = new JLabel("Drive: " + driven_wheels);
		driven_wheelsLabel.setBounds(10, 309, 153, 14);
		contentPane.add(driven_wheelsLabel);

		JLabel doorsLabel = new JLabel("Doors: " + doors);
		doorsLabel.setBounds(173, 309, 176, 14);
		contentPane.add(doorsLabel);

		JLabel categoryLabel = new JLabel("Category: " + category.split(",")[0]);
		categoryLabel.setBounds(10, 334, 153, 14);
		contentPane.add(categoryLabel);

		JLabel sizeLabel = new JLabel("Size: " + size);
		sizeLabel.setBounds(173, 334, 176, 14);
		contentPane.add(sizeLabel);

		JLabel styleLabel = new JLabel("Style: " + style);
		styleLabel.setBounds(10, 359, 153, 14);
		contentPane.add(styleLabel);

		JLabel highway_mpgLabel = new JLabel("HW MPG: " + highway_mpg);
		highway_mpgLabel.setBounds(173, 359, 176, 14);
		contentPane.add(highway_mpgLabel);

		JLabel popularityLabel = new JLabel("Popularity: " + popularity);
		popularityLabel.setBounds(10, 384, 153, 14);
		contentPane.add(popularityLabel);

		JLabel city_mpgLabel = new JLabel("City MPG: " + city_mpg);
		city_mpgLabel.setBounds(173, 384, 176, 14);
		contentPane.add(city_mpgLabel);

		setResizable(false);
		contentPane.setVisible(true);
	}
}
