package carShop;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;

import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import java.awt.Toolkit;

public class compareCars extends JFrame {

	private JPanel contentPane;
	ArrayList<String> car1 = new ArrayList<String>();
	ArrayList<String> car2 = new ArrayList<String>();

	/**
	 * Create the frame.
	 * 
	 * @throws Exception
	 * @throws MalformedURLException
	 */
	public compareCars(ArrayList<String[]> selectedCars) throws MalformedURLException, Exception {
		setIconImage(Toolkit.getDefaultToolkit().getImage(compareCars.class.getResource("/carShop/carIcon.png")));
		setBounds(100, 100, 600, 579);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setTitle("Compare");
		setResizable(false);
		mainSQL obj = new mainSQL();
		obj.Connection();

		car1 = obj.getCar(selectedCars.get(0)[1], selectedCars.get(0)[2], selectedCars.get(0)[3]);
		car2 = obj.getCar(selectedCars.get(1)[1], selectedCars.get(1)[2], selectedCars.get(1)[3]);

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel car1Picture = new JLabel("");
		car1Picture.setBounds(215, 11, 140, 117);
		contentPane.add(car1Picture);

		JLabel car2Picture = new JLabel("");
		car2Picture.setBounds(396, 11, 140, 117);
		contentPane.add(car2Picture);

		// First car
		URL url1 = new URL(BingWebSearch
				.searchFor("Stock " + car1.get(2) + " " + car1.get(0) + " " + car1.get(1) + " " + car1.get(11)));
		ImageIcon icon1 = new ImageIcon(url1);
		Image img1 = icon1.getImage();
		Image resizedImg1 = img1.getScaledInstance(car1Picture.getWidth(), car1Picture.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon resizedIcon1 = new ImageIcon(resizedImg1);
		car1Picture.setIcon(resizedIcon1);
		car1Picture.setHorizontalAlignment(JLabel.CENTER);
		car1Picture.setVerticalAlignment(JLabel.CENTER);

		// Second car
		URL url2 = new URL(BingWebSearch
				.searchFor("Stock " + car2.get(2) + " " + car2.get(0) + " " + car2.get(1) + " " + car2.get(11)));
		ImageIcon icon2 = new ImageIcon(url2);
		Image img2 = icon2.getImage();
		Image resizedImg2 = img2.getScaledInstance(car2Picture.getWidth(), car2Picture.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon resizedIcon2 = new ImageIcon(resizedImg2);
		car2Picture.setIcon(resizedIcon2);
		car2Picture.setHorizontalAlignment(JLabel.CENTER);
		car2Picture.setVerticalAlignment(JLabel.CENTER);

		JLabel makeLabel = new JLabel("Make:");
		makeLabel.setBounds(10, 139, 140, 14);
		contentPane.add(makeLabel);

		JLabel modelLabel = new JLabel("Model: ");
		modelLabel.setBounds(10, 164, 140, 14);
		contentPane.add(modelLabel);

		JLabel yearLabel = new JLabel("Year: ");
		yearLabel.setBounds(10, 189, 140, 14);
		contentPane.add(yearLabel);

		JLabel priceLabel = new JLabel("Price: ");
		priceLabel.setBounds(10, 214, 140, 14);
		contentPane.add(priceLabel);

		JLabel fuelLabel = new JLabel("Fuel:");
		fuelLabel.setBounds(10, 239, 140, 14);
		contentPane.add(fuelLabel);

		JLabel horsepowerLabel = new JLabel("Horsepower: ");
		horsepowerLabel.setBounds(10, 264, 140, 14);
		contentPane.add(horsepowerLabel);

		JLabel cylindersLabel = new JLabel("Cylinders: ");
		cylindersLabel.setBounds(10, 289, 140, 14);
		contentPane.add(cylindersLabel);

		JLabel transmissionLabel = new JLabel("Transmission: ");
		transmissionLabel.setBounds(10, 314, 140, 14);
		contentPane.add(transmissionLabel);

		JLabel driveLabel = new JLabel("Drive: ");
		driveLabel.setBounds(10, 339, 140, 14);
		contentPane.add(driveLabel);

		JLabel doorsLabel = new JLabel("Doors: ");
		doorsLabel.setBounds(10, 364, 140, 14);
		contentPane.add(doorsLabel);

		JLabel categoryLabel = new JLabel("Category: ");
		categoryLabel.setBounds(10, 389, 140, 14);
		contentPane.add(categoryLabel);

		JLabel sizeLabel = new JLabel("Size: ");
		sizeLabel.setBounds(10, 414, 140, 14);
		contentPane.add(sizeLabel);

		JLabel styleLabel = new JLabel("Style: ");
		styleLabel.setBounds(10, 439, 140, 14);
		contentPane.add(styleLabel);

		JLabel popularityLabel = new JLabel("Popularity:");
		popularityLabel.setBounds(10, 464, 140, 14);
		contentPane.add(popularityLabel);

		JLabel highwayLabel = new JLabel("Highway MPG: ");
		highwayLabel.setBounds(10, 489, 140, 14);
		contentPane.add(highwayLabel);

		JLabel cityLabel = new JLabel("City MPG: ");
		cityLabel.setBounds(10, 514, 140, 14);
		contentPane.add(cityLabel);

		JLabel lblNewLabel = new JLabel("Comparing:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNewLabel.setBounds(10, 11, 140, 106);
		contentPane.add(lblNewLabel);

		DecimalFormat formatter = new DecimalFormat("#,###.00");

		// Car 1
		JLabel car1Make = new JLabel(car1.get(0));
		car1Make.setBounds(200, 139, 140, 14);
		contentPane.add(car1Make);

		JLabel car1Model = new JLabel(car1.get(1));
		car1Model.setBounds(200, 164, 140, 14);
		contentPane.add(car1Model);

		JLabel car1Year = new JLabel(car1.get(2));
		car1Year.setBounds(200, 189, 140, 14);
		contentPane.add(car1Year);

		JLabel car1Price = new JLabel("$" + formatter.format(Double.parseDouble(car1.get(15))));
		car1Price.setBounds(200, 214, 140, 14);
		contentPane.add(car1Price);

		JLabel car1Fuel = new JLabel(car1.get(3).split("\\(")[0]);
		car1Fuel.setBounds(200, 239, 140, 14);
		contentPane.add(car1Fuel);

		JLabel car1Horsepower = new JLabel(car1.get(4));
		car1Horsepower.setBounds(200, 264, 140, 14);
		contentPane.add(car1Horsepower);

		JLabel car1Cylinders = new JLabel(car1.get(5));
		car1Cylinders.setBounds(200, 289, 140, 14);
		contentPane.add(car1Cylinders);

		JLabel car1Transmission = new JLabel(car1.get(6));
		car1Transmission.setBounds(200, 314, 140, 14);
		contentPane.add(car1Transmission);

		JLabel car1Drive = new JLabel(car1.get(7));
		car1Drive.setBounds(200, 339, 140, 14);
		contentPane.add(car1Drive);

		JLabel car1Doors = new JLabel(car1.get(8));
		car1Doors.setBounds(200, 364, 140, 14);
		contentPane.add(car1Doors);

		JLabel car1Category = new JLabel(car1.get(9).split(",")[0]);
		car1Category.setBounds(200, 389, 140, 14);
		contentPane.add(car1Category);

		JLabel car1Size = new JLabel(car1.get(10));
		car1Size.setBounds(200, 414, 140, 14);
		contentPane.add(car1Size);

		JLabel car1Style = new JLabel(car1.get(11));
		car1Style.setBounds(200, 439, 140, 14);
		contentPane.add(car1Style);

		JLabel car1Popularity = new JLabel(car1.get(14));
		car1Popularity.setBounds(200, 464, 140, 14);
		contentPane.add(car1Popularity);

		JLabel car1HWMPG = new JLabel(car1.get(12));
		car1HWMPG.setBounds(200, 489, 140, 14);
		contentPane.add(car1HWMPG);

		JLabel car1CMPG = new JLabel(car1.get(13));
		car1CMPG.setBounds(200, 514, 140, 14);
		contentPane.add(car1CMPG);

		// Car 2
		JLabel car2Make = new JLabel(car2.get(0));
		car2Make.setBounds(360, 139, 140, 14);
		contentPane.add(car2Make);

		JLabel car2Model = new JLabel(car2.get(1));
		car2Model.setBounds(360, 164, 140, 14);
		contentPane.add(car2Model);

		JLabel car2Year = new JLabel(car2.get(2));
		car2Year.setBounds(360, 189, 140, 14);
		contentPane.add(car2Year);

		JLabel car2Price = new JLabel("$" + formatter.format(Double.parseDouble(car2.get(15))));
		car2Price.setBounds(360, 214, 140, 14);
		contentPane.add(car2Price);

		JLabel car2Fuel = new JLabel(car2.get(3).split("\\(")[0]);
		car2Fuel.setBounds(360, 239, 140, 14);
		contentPane.add(car2Fuel);

		JLabel car2Horsepower = new JLabel(car2.get(4));
		car2Horsepower.setBounds(360, 264, 140, 14);
		contentPane.add(car2Horsepower);

		JLabel car2Cylinders = new JLabel(car2.get(5));
		car2Cylinders.setBounds(360, 289, 140, 14);
		contentPane.add(car2Cylinders);

		JLabel car2Transmission = new JLabel(car2.get(6));
		car2Transmission.setBounds(360, 314, 140, 14);
		contentPane.add(car2Transmission);

		JLabel car2Drive = new JLabel(car2.get(7));
		car2Drive.setBounds(360, 339, 140, 14);
		contentPane.add(car2Drive);

		JLabel car2Doors = new JLabel(car2.get(8));
		car2Doors.setBounds(360, 364, 140, 14);
		contentPane.add(car2Doors);

		JLabel car2Category = new JLabel(car2.get(9).split(",")[0]);
		car2Category.setBounds(360, 389, 140, 14);
		contentPane.add(car2Category);

		JLabel car2Size = new JLabel(car2.get(10));
		car2Size.setBounds(360, 414, 140, 14);
		contentPane.add(car2Size);

		JLabel car2Style = new JLabel(car2.get(11));
		car2Style.setBounds(360, 439, 140, 14);
		contentPane.add(car2Style);

		JLabel car2Popularity = new JLabel(car2.get(14));
		car2Popularity.setBounds(360, 464, 140, 14);
		contentPane.add(car2Popularity);

		JLabel car2HWMPG = new JLabel(car2.get(12));
		car2HWMPG.setBounds(360, 489, 140, 14);
		contentPane.add(car2HWMPG);

		JLabel car2CMPG = new JLabel(car2.get(13));
		car2CMPG.setBounds(360, 514, 140, 14);
		contentPane.add(car2CMPG);

		JLabel lblNewLabel_1 = new JLabel("VS");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(352, 66, 46, 14);
		contentPane.add(lblNewLabel_1);
	}
}
