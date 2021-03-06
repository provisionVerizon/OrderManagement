package com.processes.DAOClasses;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import com.processes.ServiceDAOInf;
import com.processes.BeanClasses.Bean;
import com.processes.BeanClasses.ServiceBean;

public class ServiceDAO implements ServiceDAOInf {


	@Override
	public ServiceBean view(int id) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:xe", "verizon", "password");

			ServiceBean sbean = new ServiceBean();
			Statement st = con.createStatement();
			ResultSet rs = st
					.executeQuery("select * from services where customer_id="
							+ id);

			while (rs.next()) {
				sbean.setCustomerID(rs.getInt(1));
				sbean.setListOfServices(rs.getString(2));
			}
			con.close();
			return sbean;
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("hello");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int add(Bean record) {
		try {
			CallableStatement stmt;
			Class.forName("oracle.jdbc.driver.OracleDriver");

			Connection con = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:xe", "verizon", "password");
			System.out.println("connected1");
			stmt = con
					.prepareCall("{? = call verizon.insert_values.serv(?,?)}");
			System.out.println("connected2");
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setInt(2,((ServiceBean) record).getCustomerID());
			stmt.setString(3,((ServiceBean) record).getListOfServices());
			
			
			System.out.println();
			System.out.println("conn3");
			System.out.println(stmt.execute());
			int output = stmt.getInt(1);
			System.out.println(output);


			return output;
		} 
		catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

	
	}
	@Override
	public int update(String name,String value,int id) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			Connection con = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:xe", "verizon",
					"password");

			PreparedStatement ps = con.prepareStatement("update services set "+name+"=? where customer_id=?");
			ps.setString(1, value);
			ps.setInt(2,id);
		int i = ps.executeUpdate();
		return i;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return 0;
		}

	}

	@Override
	public int update(String name, int value, int id) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			Connection con = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:xe", "verizon",
					"password");

			PreparedStatement ps = con.prepareStatement("update services set "+name+"=? where customer_id=?");
			ps.setInt(1, value);
			ps.setInt(2,id);
		int i = ps.executeUpdate();
		return i;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int update(String name, Date value, int id) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			Connection con = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:xe", "verizon",
					"password");

			PreparedStatement ps = con.prepareStatement("update services set "+name+"=? where customer_id=?");
			ps.setDate(1, value);
			ps.setInt(2,id);
		int i = ps.executeUpdate();
		return i;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return 0;
		}
	}
}