//package application;
	
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.Calendar;

public class SensorSystemSimulation extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
	        Pane root = new Pane(); 
			Text Errorm = new Text();
			
			Errorm.setFill(Color.RED);
			Errorm.setLayoutX(120);
			Errorm.setLayoutY(200);
//			TextField floorID = new TextField();
//			floorID.setLayoutX(100);
//			floorID.setLayoutY(70);
			TextField cbsoptID = new TextField();
            cbsoptID.setLayoutX(100);
            cbsoptID.setLayoutY(100);
            TextField cbvID = new TextField();
            cbvID.setLayoutX(100);
            cbvID.setLayoutY(130);
			Button insertbtn = new Button();
			insertbtn.setLayoutX(100);
			insertbtn.setLayoutY(160);
			insertbtn.setText("Car arrives");
			insertbtn.setOnAction(new EventHandler<ActionEvent>() {
				 
	            @Override
	            public void handle(ActionEvent event) {
	            	//the query
	            	try{
	            		Errorm.setText("");
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
	        			ResultSet rs=stmt.executeQuery("SELECT Status.Description\r\n" + 
                                "FROM Status\r\n" + 
                                "    JOIN ParkingSpot ON (ParkingSpot.StatusID = Status.ID)\r\n" + 
                                "WHERE ParkingSpot.ID = "+ Integer.toString(Integer.parseInt(cbsoptID.getText())) + ";");
	        			//boolean abc = rs.next();
	        			//System.out.println(abc);
	        			if(rs.next())  {
	        			//if (abc == true) {
	        				String test1 = rs.getString(1);
	        				//if(rs.getString(1) == "Available") {
	        				if (test1.equals("Available")) {
                                PreparedStatement pstmt = con.prepareStatement("INSERT INTO ParkingHistory(ParkingSpotID, VehicleID, startTime, endTime) VALUES(?, ?, ?, ?)");
                                long millis=System.currentTimeMillis() - 25200000;
                                java.sql.Timestamp date=new java.sql.Timestamp(millis);
                                pstmt.setInt(1, Integer.parseInt(cbsoptID.getText()));
                                pstmt.setInt(2, Integer.parseInt(cbvID.getText()));
                                pstmt.setTimestamp(3,date);
                                pstmt.setNull(4, java.sql.Types.DATE);
                                pstmt.executeUpdate();
                                pstmt = con.prepareStatement("UPDATE ParkingSpot SET StatusID = ? WHERE ParkingSpot.ID = ?");
                                pstmt.setInt(1, 2);
                                pstmt.setInt(2, Integer.parseInt(cbsoptID.getText()));
                                pstmt.executeUpdate();
                            }
                            else Errorm.setText("Car already parked");
	        			}
	        			else {
	        				Errorm.setText("No such spot!");
	        			}
	        			
//	        			else {result.setText("no space in this building");}
//	        			while(rs.next());
	        			con.close();  
	        			}catch(Exception e){Errorm.setText("Error occur in sql make an exception, please check your data!");}  
	            }
	        });
			Button removebtn = new Button();
			removebtn.setOnAction(new EventHandler<ActionEvent>() {
				 
	            @Override
	            public void handle(ActionEvent event) {
	            	//the query
	            	try{  
	            		Errorm.setText("");
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
	        			ResultSet rs=stmt.executeQuery("SELECT Status.Description\r\n" + 
	        					"FROM Status\r\n" + 
	        					"    JOIN ParkingSpot ON (ParkingSpot.StatusID = Status.ID)\r\n" + 
	        					"WHERE ParkingSpot.ID = "+ Integer.toString(Integer.parseInt(cbsoptID.getText())) + ";");

	        			if(rs.next())  {

                            if(rs.getString(1).equals("Occupied")) {
                                String sql = "UPDATE ParkingHistory set endTime = ? WHERE ParkingSpotID = ? AND endTime IS NULL";
                                PreparedStatement pstmt = con.prepareStatement(sql);
                                long millis=System.currentTimeMillis() - 25200000;
                                java.sql.Timestamp date=new java.sql.Timestamp(millis);
                                pstmt.setTimestamp(1, date);
                                pstmt.setInt(2, Integer.parseInt(cbsoptID.getText()));
                                pstmt.executeUpdate();
                                pstmt = con.prepareStatement("UPDATE ParkingSpot SET StatusID = ? WHERE ParkingSpot.ID = ?");
                                pstmt.setInt(1, 1);
                                pstmt.setInt(2, Integer.parseInt(cbsoptID.getText()));
                                pstmt.executeUpdate();
                            }
                            else Errorm.setText("Car not parked");
                        }
                        else {
                            Errorm.setText("No such spot!");
                        }
	        			
//	        			else {result.setText("no space in this building");}
//	        			while(rs.next());
	        			con.close();  
	        			}catch(Exception e){Errorm.setText("Error occur in sql make an exception, \n please check your data!");}  
	            }
	        });
			
			removebtn.setLayoutX(200);
			removebtn.setLayoutY(160);
			removebtn.setText("Car leaves");
			Text Title = new Text();
			Title.setText("Sensor System Simulation");
			Title.setLayoutX(120);
			Title.setLayoutY(90);
			Text fID = new Text();
//			fID.setText("FloorID  :");
//			fID.setLayoutX(40);
//			fID.setLayoutY(85);
			Text sID = new Text();
			sID.setText("SpotID   :");
			sID.setLayoutX(40);
			sID.setLayoutY(115);
			Text vID = new Text();
			vID.setText("VehicleID:");
			vID.setLayoutX(40);
			vID.setLayoutY(145);
			root.getChildren().addAll(insertbtn,removebtn,cbsoptID,cbvID,sID,vID,Title,Errorm,fID);
			Scene scene = new Scene(root,400,250);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
