package carShop;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.CallableStatement;
import javax.swing.JFrame;

public class mainSQL extends JFrame {

	private static final long serialVersionUID = 1L;
	static final String netID = "redacted";
	static final String passwordSQL = "redacted";
	static final String databasePrefix = "redacted";
	static final String hostName = "redacted";
	static final String databaseURL = "jdbc:mariadb://" + hostName + "/" + databasePrefix + "_" + netID;
	public static String username;
	public static String password;

	public Connection con;
	public ResultSet rs;

	public void Connection() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			con = DriverManager.getConnection(databaseURL, netID, passwordSQL);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String login(String uname, String pword) {
		String result = null;
		username = uname;
		password = pword;
		try {
			CallableStatement stmt = con.prepareCall("{call login(?,?)}");
			stmt.setNString(1, uname);
			stmt.setNString(2, getMd5(pword));
			rs = stmt.executeQuery();
			if (rs.next() && !uname.isBlank() && !pword.isBlank()) {
				result = "";
			} else if (uname.isBlank() || pword.isBlank()) {
				result = "Please type in a username/password";
			} else {
				result = "Wrong username or password";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String signUp(String uname, String pword) {
		String result = null;
		username = uname;
		password = pword;
		int ulength = uname.length();
		int plength = pword.length();
		try {
			CallableStatement stmt = con.prepareCall("{call createUser(?,?)}");
			CallableStatement checkStmt = con.prepareCall("{call checkUser(?)}");
			checkStmt.setNString(1, uname);
			stmt.setNString(1, uname);
			stmt.setNString(2, getMd5(pword));
			ResultSet checkRs = checkStmt.executeQuery();
			if (checkRs.next()) {
				result = "User already exists";
			} else if (ulength < 5 || ulength > 14) {
				result = "Username too short/long";
			} else if (plength > 40 || plength < 8) {
				result = "Password too short/long";
			} else if (!uname.isBlank() && !pword.isBlank()) {
				rs = stmt.executeQuery();
				result = "Successfully created";
			} else {
				result = "Please type in a username/password";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public void addToCart(int userID, String model, int year, int price) {
		try {
			System.out.println(userID);
			CallableStatement stmt = con.prepareCall("{call addToCart(?,?,?,?)}");
			stmt.setInt(1, userID);
			stmt.setNString(2, model);
			stmt.setInt(3, year);
			stmt.setInt(4, price);
			stmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteFromCart(int userID, String model, int year, int price) {
		try {
			System.out.println(userID);
			CallableStatement stmt = con.prepareCall("{call deleteFromCart(?,?,?,?)}");
			stmt.setInt(1, userID);
			stmt.setNString(2, model);
			stmt.setInt(3, year);
			stmt.setInt(4, price);
			stmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String[][] getCart(int userid) {
		Vector<Vector<String>> cart = new Vector<Vector<String>>();
		String[][] array = null;
		try {
			CallableStatement stmt = con.prepareCall("{call getCart(?)}");
			stmt.setInt(1, userid);
			rs = stmt.executeQuery();
			while (rs.next()) {
				Vector<String> curr = new Vector<String>();
				curr.add(rs.getString("make"));
				curr.add(rs.getString("model"));
				curr.add(rs.getString("year"));
				curr.add(rs.getString("price"));
				cart.add(curr);

			}
			array = vectorTo2DArray(cart);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return array;
	}

	public ArrayList<String> getCar(String model, String year, String price) {
		ArrayList<String> car = new ArrayList<String>();
		try {
			CallableStatement stmt = con.prepareCall("{call getCar(?,?,?)}");
			stmt.setNString(1, model);
			stmt.setInt(2, Integer.parseInt(year));
			stmt.setInt(3, Integer.parseInt(price));
			rs = stmt.executeQuery();
			rs.next();
			car.add(rs.getString("make"));
			car.add(rs.getString("model"));
			car.add(rs.getString("year"));
			car.add(rs.getString("fuel"));
			car.add(rs.getString("horsepower"));
			car.add(rs.getString("cylinders"));
			car.add(rs.getString("transmission"));
			car.add(rs.getString("driven_wheels"));
			car.add(rs.getString("doors"));
			car.add(rs.getString("category"));
			car.add(rs.getString("size"));
			car.add(rs.getString("style"));
			car.add(rs.getString("highway_mpg"));
			car.add(rs.getString("city_mpg"));
			car.add(rs.getString("popularity"));
			car.add(rs.getString("price"));

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return car;
	}

	public static String[][] vectorTo2DArray(Vector<Vector<String>> vector) {
		int numRows = vector.size();
		int numCols = 0;
		try {
			numCols = vector.get(0).size();
		} catch (IndexOutOfBoundsException e) {
			return null;
		}

		String[][] array = new String[numRows][numCols];

		for (int i = 0; i < numRows; i++) {
			Vector<String> row = vector.get(i);
			for (int j = 0; j < numCols; j++) {
				array[i][j] = row.get(j);
			}
		}

		return array;
	}

	public String[][] searchCars(String model, String year, String price, String make) {
		Vector<Vector<String>> results = new Vector<Vector<String>>();
		String[][] array = null;
		try {
			Statement stmt = con.createStatement();
			System.out.println(year);
			StringBuilder sb = new StringBuilder();
			sb.append(" WHERE 1=1");

			if (!make.equals("All")) {
				sb.append(" AND m.make = '" + make + "'");
			}

			if (!model.equals("All")) {
				sb.append(" AND c.model = '" + model + "'");
			}

			if (!year.equals("All")) {
				sb.append(" AND c.year " + year);
			}

			if (!price.equals("All")) {
				sb.append(" AND c.price " + price.replace("$", "").replace(",", ""));
			}

			sb.append(" ORDER BY c.price ASC;");
			rs = stmt.executeQuery("SELECT m.make, c.model, c.year, s.price" + " FROM Car c"
					+ " INNER JOIN Manufacturer m ON (c.model = m.model)"
					+ " INNER JOIN Stats s ON (c.model = s.model AND c.year = s.year AND c.price = s.price)" + sb);
			System.out.println(sb);
			while (rs.next()) {
				Vector<String> curr = new Vector<String>();
				curr.add(rs.getString("make"));
				curr.add(rs.getString("model"));
				curr.add(rs.getString("year"));
				curr.add(rs.getString("price"));
				curr.add("View");
				curr.add("+ Cart");
				results.add(curr);

			}
			array = vectorTo2DArray(results);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return array;
	}

	public String[] getModels(String make) {
		ArrayList<String> models = new ArrayList<String>();
		try {
			CallableStatement stmt = con.prepareCall("{call getModels(?)}");
			stmt.setString(1, make);
			rs = stmt.executeQuery();
			models.add("All");
			while (rs.next()) {
				models.add(rs.getString("model"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return models.toArray(new String[models.size()]);
	}

	public String[] getMakes() {
		ArrayList<String> makes = new ArrayList<String>();
		try {
			CallableStatement stmt = con.prepareCall("{call getMakes()}");
			rs = stmt.executeQuery();
			makes.add("All");
			while (rs.next()) {
				makes.add(rs.getString("make"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return makes.toArray(new String[makes.size()]);
	}

	public int getUserID(String user) {
		int id = 0;
		try {
			Statement stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT userid FROM Users WHERE uname = " + "'" + user + "'");
			if (rs.next()) {
				id = rs.getInt("userid");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	public static String getMd5(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input.getBytes());
			BigInteger no = new BigInteger(1, messageDigest);
			String hashtext = no.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
}
