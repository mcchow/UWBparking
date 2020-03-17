/*
 * Javafx UWN Parking UI
 * Developer: Chow Sheung Him Martin 
 * This is the UI for UWB parking
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
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
			BorderPane root = new BorderPane();
			Scene scene;
			ArrayList<Button> Hboxbutton = new ArrayList<Button>();
			Button btn1 = new Button();
	        btn1.setText("View all available parking of a building");
	        btn1.setOnAction(new EventHandler<ActionEvent>() {
	       	 
	            @Override
	            public void handle(ActionEvent event) {
	    			root.setCenter(findplace());
	            }
	        });
	        
			Button btn2 = new Button();
			btn2.setText("View available parking statistics from the past");
			btn2.setOnAction(new EventHandler<ActionEvent>() {
				 
	            @Override
	            public void handle(ActionEvent event) {
	    			root.setCenter(findplacebytime());
	    			//Scene scene2 = new Scene(root,400,400);
	    			//primaryStage.setScene(scene2);
	            }
	        });

			Button btn3 = new Button();
	        btn3.setText("View number of available parking: search by building");
	        btn3.setOnAction(new EventHandler<ActionEvent>() {
	       	 
	            @Override
	            public void handle(ActionEvent event) {
	            	root.setCenter(countspace());

	            }
	        });
			Button btn4 = new Button();
	        btn4.setText("View available parking statistics from the past: search by building");
	        btn4.setOnAction(new EventHandler<ActionEvent>() {
		       	 
	            @Override
	            public void handle(ActionEvent event) {
	            	root.setCenter(countspacetime());

	            }
	        });
			Button btn5 = new Button();
	        btn5.setText("Most frequently used parking spots");
	        btn5.setOnAction(new EventHandler<ActionEvent>() {
		       	 
	            @Override
	            public void handle(ActionEvent event) {
	            	root.setCenter(mostpark());
	            }
	        });
	        
	        Button btn6 = new Button();
	        btn6.setText("Most frequently used floors");
	        btn6.setOnAction(new EventHandler<ActionEvent>() {
		       	 
	            @Override
	            public void handle(ActionEvent event) {
	            	root.setCenter(mostparkinbuilding());
	            }
	        });
	        
	        Button btn7 = new Button();
	        btn7.setText("Most frequently used building");
	        btn7.setOnAction(new EventHandler<ActionEvent>() {
		       	 
	            @Override
	            public void handle(ActionEvent event) {
	            	root.setCenter(mostbuilding());
	            }
	        });
	        
	        Button btn8 = new Button();
	        btn8.setText("Most common type of vehicles");
	        btn8.setOnAction(new EventHandler<ActionEvent>() {
		       	 
	            @Override
	            public void handle(ActionEvent event) {
	            	root.setCenter(mostcar());
	            }
	        });
	        
			VBox vbox = new VBox();
			vbox.setSpacing(10);
			vbox.getChildren().addAll(btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8);
			
			root.setLeft(vbox);
			root.setCenter(findplace());
			scene = new Scene(root,700,700);
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
	        Pane root = new Pane(); 

	        //create all element
			Button btn = new Button();
	 
	        btn.setText("avalible space");
	        btn.setLayoutX(130);
	        btn.setLayoutY(400);
	        // only 2 location is need
	        ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList(
	        	    "A", "B")
	        	);
	        cb.setLayoutX(50);
	        cb.setLayoutY(400);
	        
	        btn.setOnAction(new EventHandler<ActionEvent>() {
	 
	            @Override
	            public void handle(ActionEvent event) {
	            	//the query
	            	try{  
	        			TableView table = new TableView();
	        			
	        	        TableColumn floor = new TableColumn("Floor");
	        	        floor.setMinWidth(50);
	        	        floor.setCellValueFactory(
	        	                new PropertyValueFactory<ParkingSpot, String>("floor"));

	        	        TableColumn parkingSpot = new TableColumn("Parking Spot");
	        	        parkingSpot.setMinWidth(100);
	        	        parkingSpot.setCellValueFactory(
	        	                new PropertyValueFactory<ParkingSpot, String>("parkingSpot"));
	        	        
	        	        table.getColumns().addAll(floor, parkingSpot);
	        	 
	        	        final VBox vbox = new VBox();
	        	        vbox.setSpacing(5);
	        	        vbox.setPadding(new Insets(0, 0, 0, 0));
	        	        vbox.getChildren().add(table);

	        	        ObservableList<ParkingSpot> data =
	        	                FXCollections.observableArrayList();
	        	        table.setItems(data);

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
	        			ResultSet rs=stmt.executeQuery("SELECT Floor.FloorNumber, SpotNumber " + 
	        					"FROM ParkingSpot " + 
	        					"  JOIN Floor on (Floor.id = ParkingSpot.floorId) " + 
	        					"  JOIN Status ON (Status.id = ParkingSpot.statusId) " + 
	        					"  JOIN Building ON (Building.id = floor.buildingId) " + 
	        					"Where status.description = 'Available' AND Building.name = \""+ (String)cb.getValue()+"\";"
	        					);
	        			while(rs.next()) {
	        				data.add(new ParkingSpot(new Integer(rs.getInt(1)).toString(), new Integer(rs.getInt(2)).toString()));  
	        			}
	        	        root.getChildren().add(vbox);
	        			con.close();  
	        		} catch (Exception e) { 
	        			System.out.println(e);
	        		}  
	            }
	        });
	        
			root.getChildren().add(btn);  
			root.getChildren().add(cb);
			return root;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return new Pane();
	}
	
	//creating page for finding space record in the past
		public Pane findplacebytime() {
			try {
		        
				//create all element
				Pane root = new Pane(); 
				Button btn = new Button();
				ListView result = new ListView();
				
		        btn.setText("avalible space record");
		        btn.setLayoutX(300);
		        btn.setLayoutY(400);
		        // only 2 location is need
		        TextField cb = new TextField();
		        cb.setLayoutX(300);
		        cb.setLayoutY(450);
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
		        			ResultSet rs=stmt.executeQuery("SELECT Building.name, Floor.FloorNumber, SpotNumber\r\n" + 
		        					"FROM ParkingSpot\r\n" + 
		        					"  JOIN Floor on (Floor.id = ParkingSpot.floorId)\r\n" + 
		        					"  JOIN ParkingHistory ON (ParkingSpot.id = ParkingHistory.id)\r\n" + 
		        					"  JOIN Building ON (Building.id = floor.buildingId)\r\n" + 
		        					"Where StartTime >= '"+ cb.getText()+"' OR EndTime <= '"+ cb.getText()+ "';");
		        			result.getItems().clear();
		        			while(rs.next())  {
		        				result.getItems().add("Building: " + rs.getString(1)+" Floor: "+rs.getInt(2)+"  SpotID: "+rs.getInt(3));  
		        			}
//		        			else {result.setText("no space in this building");}
//		        			while(rs.next());
		        			con.close();  
		        			}catch(Exception e){ System.out.println(e);}  
		            }
		        });
		        
				root.getChildren().add(btn);  
				root.getChildren().add(result);  
				root.getChildren().add(cb);
				return root;
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			return new Pane();
		}
	
	//creating page for finding space
	
		public Pane countspace() {
			try {
				//create all element
				Button btn = new Button();
				Text result = new Text();
				result.setX(50);
				result.setY(100);
				
		        btn.setText("avalible space");
		        btn.setLayoutX(130);
		        btn.setLayoutY(200);
		        // only 2 location is need
		        ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList(
		        	    "A", "B")
		        	);
		        cb.setLayoutX(50);
		        cb.setLayoutY(200);
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
		        			ResultSet rs=stmt.executeQuery("SELECT Floor.FloorNumber, COUNT(SpotNumber) AS Available\r\n" + 
		        					"FROM ParkingSpot\r\n" + 
		        					"  JOIN Floor on (Floor.id = ParkingSpot.floorId)\r\n" + 
		        					"  JOIN Status ON (Status.id = ParkingSpot.statusId)\r\n" + 
		        					"  JOIN Building ON (Building.id = floor.buildingId)\r\n" + 
		        					"Where status.description = 'Available' AND Building.name = \""+ (String)cb.getValue() + "\";\r\n"
		        					);
		        			String resulttext = "";
		        			while(rs.next())  {
		        				resulttext+="Floor: "+rs.getInt(1)+"  remaining spaces: "+rs.getInt(2) +"\n";  
		        			}
		        			result.setText(resulttext);
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
		
		public Pane countspacetime() {
			try {
				//create all element
				Button btn = new Button();
				Text result = new Text();
				result.setX(50);
				result.setY(100);
				
		        btn.setText("avalible space");
		        btn.setLayoutX(400);
		        btn.setLayoutY(200);
		        // only 2 location is need
		        ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList(
		        	    "A", "B")
		        	);
		        cb.setLayoutX(50);
		        cb.setLayoutY(200);
		        TextField cb2 = new TextField();
		        cb2.setLayoutX(130);
		        cb2.setLayoutY(200);
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
		        			ResultSet rs=stmt.executeQuery("SELECT Building.name, COUNT(*) AS Available\r\n" + 
                                    "FROM ParkingSpot\r\n" + 
                                    "  JOIN Floor on (Floor.id = ParkingSpot.floorId)\r\n" + 
                                    "  JOIN ParkingHistory ON (ParkingSpot.id = ParkingHistory.id)\r\n" +
                                    "  JOIN Building ON (Building.id = floor.buildingId)\r\n" +
                                    "Where (StartTime >= '"+ cb2.getText()+"' OR EndTime <= '"+ cb2.getText()+"') AND Building.name = '"+ cb.getValue()+"';");
		        			String resulttext = "";
		        			while(rs.next())  {
                                resulttext+="building: "+cb.getValue()+"  remain space: "+rs.getInt(2) +"\n";
                            }
		        			result.setText(resulttext);
		        			con.close();  
		        			}catch(Exception e){ System.out.println(e);}  
		            }
		        });
		        
		        Pane root = new Pane(); 
				root.getChildren().add(btn);  
				root.getChildren().add(result);  
				root.getChildren().add(cb);
				root.getChildren().add(cb2);
				return root;
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			return new Pane();
		}	
		
		public Pane mostpark() {
			try {
				//create all element
				Button btn = new Button();
				Text result = new Text();
				result.setX(50);
				result.setY(100);
				
		        btn.setText("max park");
		        btn.setLayoutX(200);
		        btn.setLayoutY(200);
		        // only 2 location is need
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
		        			ResultSet rs=stmt.executeQuery("SELECT Building.name, Floor.FloorNumber, SpotNumber, COUNT(*) AS Frequency\r\n" + 
		        					"FROM ParkingHistory\r\n" + 
		        					"  JOIN parkingSpot ON (ParkingSpot.ID = parkingHistory.parkingSpotID)\r\n" + 
		        					"  JOIN Floor on (Floor.id = ParkingSpot.floorId)\r\n" + 
		        					"  JOIN Building ON (Building.id = Floor.buildingId)\r\n" + 
		        					"GROUP BY ParkingSpotID\r\n" + 
		        					"ORDER BY Frequency DESC\r\n" + 
		        					"LIMIT 1;"
		        					);
		        			String resulttext = "";
		        			while(rs.next())  {
		        				resulttext+="Building: "+rs.getString(1)+"  Floor: "+rs.getInt(2) +"  Spotnumber: "+rs.getInt(3) +"\n";  
		        			}
		        			result.setText(resulttext);
		        			con.close();  
		        			}catch(Exception e){ System.out.println(e);}  
		            }
		        });
		        
		        Pane root = new Pane(); 
				root.getChildren().add(btn);  
				root.getChildren().add(result);  
				return root;
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			return new Pane();
		}
		
		public Pane mostparkinbuilding() {
			try {
				//create all element
				Button btn = new Button();
				Text result = new Text();
				result.setX(50);
				result.setY(100);
		        ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList(
		        	    "A", "B")
		        	);
		        cb.setLayoutX(50);
		        cb.setLayoutY(200);
		        btn.setText("max floor");
		        btn.setLayoutX(200);
		        btn.setLayoutY(200);
		        // only 2 location is need
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
		        			ResultSet rs=stmt.executeQuery("SELECT Building.name, Floor.FloorNumber, COUNT(*) AS Frequency\r\n" + 
		        					"FROM ParkingHistory\r\n" + 
		        					"  JOIN parkingSpot ON (ParkingSpot.ID = parkingHistory.parkingSpotID)\r\n" + 
		        					"  JOIN Floor on (Floor.id = ParkingSpot.floorId)\r\n" + 
		        					"  JOIN Building ON (Building.id = floor.buildingId)\r\n" + 
		        					"WHERE Building.name = \""+ cb.getValue() +"\"\r\n" + 
		        					"GROUP BY Floor.floorNumber\r\n" + 
		        					"ORDER BY Frequency DESC\r\n" + 
		        					"LIMIT 1;"
		        					);
		        			String resulttext = "";
		        			while(rs.next())  {
		        				resulttext+="Building: "+rs.getString(1)+"  Floor: "+rs.getInt(2) +"  frequency: "+rs.getInt(3) +"\n";  
		        			}
		        			result.setText(resulttext);
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
		
		public Pane mostbuilding() {
			try {
				//create all element
				Button btn = new Button();
				Text result = new Text();
				result.setX(50);
				result.setY(100);
				
		        btn.setText("max building");
		        btn.setLayoutX(200);
		        btn.setLayoutY(200);
		        // only 2 location is need
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
		        			ResultSet rs=stmt.executeQuery("SELECT Building.name, COUNT(*) AS Frequency\r\n" + 
		        					"FROM ParkingHistory\r\n" + 
		        					"  JOIN parkingSpot ON (ParkingSpot.ID = parkingHistory.parkingSpotID)\r\n" + 
		        					"  JOIN Floor on (Floor.id = ParkingSpot.floorId)\r\n" + 
		        					"  JOIN Building ON (Building.id = floor.buildingId)\r\n" + 
		        					"GROUP BY Building.id\r\n" + 
		        					"ORDER BY Frequency DESC\r\n" + 
		        					"LIMIT 1;"
		        					);
		        			String resulttext = "";
		        			while(rs.next())  {
		        				resulttext+="Building: "+rs.getString(1)+"  Number: "+rs.getInt(2) +"\n";  
		        			}
		        			result.setText(resulttext);
		        			con.close();  
		        			}catch(Exception e){ System.out.println(e);}  
		            }
		        });
		        
		        Pane root = new Pane(); 
				root.getChildren().add(btn);  
				root.getChildren().add(result);  
				return root;
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			return new Pane();
		}
		
		public Pane mostcar() {
			try {
				//create all element
				Button btn = new Button();
				Text result = new Text();
				result.setX(50);
				result.setY(100);
				
		        btn.setText("find common car");
		        btn.setLayoutX(200);
		        btn.setLayoutY(200);
		        // only 2 location is need
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
		        			ResultSet rs=stmt.executeQuery("SELECT VehicleType.description AS MostCommonType, COUNT(*) AS Frequency\r\n" + 
		        					"FROM ParkingHistory\r\n" + 
		        					"   JOIN ParkingSpot ON (ParkingHistory.parkingSpotId = parkingSpot.Id)\r\n" + 
		        					"   JOIN VehicleType ON (VehicleType.ID = ParkingSpot.vehicleTypeId)\r\n" + 
		        					"GROUP BY VehicleType.Id\r\n" + 
		        					"ORDER BY Frequency;"
		        					);
		        			String resulttext = "";
		        			while(rs.next())  {
		        				resulttext+="MostCommonType: "+rs.getString(1)+"  Number: "+rs.getInt(2) +"\n";  
		        			}
		        			result.setText(resulttext);
		        			con.close();  
		        			}catch(Exception e){ System.out.println(e);}  
		            }
		        });
		        
		        Pane root = new Pane(); 
				root.getChildren().add(btn);  
				root.getChildren().add(result);  
				return root;
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			return new Pane();
		}
	public static class ParkingSpot {
		private final SimpleStringProperty floor;
		private final SimpleStringProperty parkingSpot;
        private ParkingSpot(String floor, String parkingSpot) {
            this.floor = new SimpleStringProperty(floor);
            this.parkingSpot = new SimpleStringProperty(parkingSpot);
        }
		public String getFloor() {
			return floor.get();
		}
		public String getParkingSpot() {
			return parkingSpot.get();
		}
		public void setFloor(String strFloor) {
			floor.set(strFloor);
		}
		public void setParkingSpot(String strParkingSpot) {
			parkingSpot.set(strParkingSpot);
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
	

}