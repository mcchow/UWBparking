////////////////////////////////////////////////////////////////
//	This
//
//

import java.sql.*;
import javafx.application.*;
import javafx.*;

public class UWBparking {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("this is the program to read the sql");
		try{  
			Class.forName("com.mysql.cj.jdbc.Driver");
			//the first thing is the host id, plz also put serverTimezone=UTC to set up srever time. 
			//root is the name of the user, password 123456 , 
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/UWB_Parking?serverTimezone=UTC","root","123456");  
			//here Parking is database name, root is username and password  
			//System.out.println("test");
			//run query
			Statement stmt=con.createStatement();
			//stmt.executeQuery("Insert Into Status (ID,Description) Value(3,'noonecare');");  
			ResultSet rs=stmt.executeQuery("select * from Status");  
			//print all result
			while(rs.next())  
			System.out.println(rs.getInt(1)+"  "+rs.getString(2));  
			con.close();  
			}catch(Exception e){ System.out.println(e);}  
	}
	
	void setUpdatabase() {
		try{  
			Class.forName("com.mysql.cj.jdbc.Driver");
			//the first thing is the host id, plz also put serverTimezone=UTC to set up srever time. 
			//root is the name of the user, password 123456 , 
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/UWB_Parking?serverTimezone=UTC","root","123456");  
			//here Parking is database name, root is username and password  
			//System.out.println("test");
			//run query
			Statement stmt=con.createStatement();
			//
			//this is adding data to the database, use a for loop to do it
			stmt.executeQuery("Insert Into Status (ID,Description) Value(3,'noonecare');");
			//
			con.close();  
			}catch(Exception e){ System.out.println(e);}  
	}
	
}
