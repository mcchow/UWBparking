/*
 * Javafx UWN Parking UI
 * Developer: Chow Sheung Him Martin 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * */
package application;
	
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			ArrayList<Button> Hboxbutton = new ArrayList<Button>();
			Button btn1 = new Button();
	        btn1.setText("find space");
	        btn1.setOnAction(new EventHandler<ActionEvent>() {
	       	 
	            @Override
	            public void handle(ActionEvent event) {
	            	
	       
	            }
	        });
	        
			Button btn2 = new Button();
			btn2.setText("find space");
			btn2.setOnAction(new EventHandler<ActionEvent>() {
				 
	            @Override
	            public void handle(ActionEvent event) {
	            	

	            }
	        });

			Button btn3 = new Button();
	        btn3.setText("find space");
	        btn3.setOnAction(new EventHandler<ActionEvent>() {
	       	 
	            @Override
	            public void handle(ActionEvent event) {
	            	

	            }
	        });
			Button btn4 = new Button();
	        btn4.setText("find space");
			Button btn5 = new Button();
	        btn5.setText("find space");
			BorderPane root = new BorderPane();
			VBox vbox = new VBox();
			vbox.setSpacing(10);
			vbox.getChildren().addAll(btn1,btn2,btn3,btn4,btn5);
			
			root.setLeft(vbox);
			root.setCenter(findplace());
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	        primaryStage.setTitle("UWBParking");
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//creating page for finding space
	public Pane findplace() {
		try {
			//create all element
			Button btn = new Button();
			Text result = new Text();
			result.setX(100);
			result.setY(200);
			
	        btn.setText("avalible space");
	        btn.setLayoutX(130);
	        btn.setLayoutY(220);
	        // only 2 location is need
	        ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList(
	        	    "garage", "ground")
	        	);
	        cb.setLayoutX(50);
	        cb.setLayoutY(220);
	        btn.setOnAction(new EventHandler<ActionEvent>() {
	 
	            @Override
	            public void handle(ActionEvent event) {
	            	//the query
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
	        			ResultSet rs=stmt.executeQuery("SELECT Floor.FloorNumber, SpotNumber\r\n" + 
	        					"FROM ParkingSpot\r\n" + 
	        					"  JOIN Floor on (Floor.id = ParkingSpot.floorId)\r\n" + 
	        					"  JOIN Status ON (Status.id = ParkingSpot.statusId)\r\n" + 
	        					"  JOIN Building ON (Building.id = floor.buildingId)\r\n" + 
	        					"Where status.description = 'Available' AND Building.name = \""+ (String)cb.getValue()+"\" LIMIT 1;\r\n"
	        					);
	        			if(rs.next())  {
	        				result.setText("Floor: "+rs.getInt(1)+"  SpotID: "+rs.getInt(2));  
	        			}
	        			else {result.setText("no space in this building");}
	        			while(rs.next());
	        			con.close();  
	        			}catch(Exception e){ System.out.println(e);}  
	            }
	        });
	        
	        Pane root = new Pane(); 
			root.getChildren().add(btn);  
			root.getChildren().add(result);  
			root.getChildren().add(cb);
			return root;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return new Pane();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
