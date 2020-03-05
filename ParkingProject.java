
/**
 * Write a description of class ParkingProject here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Date;

public class ParkingProject
{
    public static void main (String[] args) throws Exception {

    java.util.Date sTime = new java.util.Date();
    java.util.Date eTime = new java.util.Date();

    //createNewTable();
    ParkingProject app = new ParkingProject();

    //the numbers here are temporary (just to test)

    app.insertVehicle(1, "23543");
    app.insertVehicle(2, "22343");
    app.insertVehicle(3, "27843");

    app.insertBuilding(23, "garage");
    app.insertBuilding(14, "ground");
    app.insertBuilding(41, "b92");

    app.insertStatus(25346, "taken");
    app.insertStatus(23498, "vacant");
    app.insertStatus(87652, "free");

    app.insertVehicleType(254983, "car");
    app.insertVehicleType(905427, "SUV");
    app.insertVehicleType(984463, "motorcycle");

    app.insertFloor(3, 23, "3");
    app.insertFloor(1, 14, "1");
    app.insertFloor(5, 41, "5");

    app.insertParkingSpot(34, 3, 25346, 254983, "b87");
    app.insertParkingSpot(67, 1, 23498, 905427, "a85");
    app.insertParkingSpot(87, 5, 87652, 984463, "b32");

    app.insertParkingHistory(300, 34, 1, new Date(sTime.getTime()), new Date(eTime.getTime()));
    app.insertParkingHistory(1, 67, 2, new Date(sTime.getTime()), new Date(eTime.getTime()));
    app.insertParkingHistory(2, 87, 3, new Date(sTime.getTime()), new Date(eTime.getTime()));

        //app.selectAll();
 }
 
 private Connection connect() {
        // SQLite connection string
        String url = "jdbc:mysql://localhost:3306/Parking_Database";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, "root", "UWBParkingSystem");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
 
 public static void createNewTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:C:\\Users\\ao050\\Dropbox\\Winter 2020\\CSS 475 Database System\\ParkingDB\\test.db";
        
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS ParkingHistory (\n"
                + "    id integer PRIMARY KEY,\n"
                + "    name text NOT NULL,\n"
                + "    capacity real\n"
                + ");";
        
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    
    public void selectAll(){
        String sql = "SELECT id, name, capacity FROM warehouses";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("id") +  "\t" + 
                                   rs.getString("name") + "\t" +
                                   rs.getDouble("capacity"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void insertParkingHistory(int id, int parkingSpotID, int vID, Date sTime, Date eTime) {
        String sql = "INSERT INTO ParkingHistory(ID, ParkingSpotID, VehicleID, startTime, endTime) VALUES(?, ?, ?, ?, ?)";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setInt(2, parkingSpotID);
            pstmt.setInt(3, vID);
            pstmt.setDate(4, sTime);
            pstmt.setDate(5, eTime);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertVehicle(int id, String rfID) {
        String sql = "INSERT INTO Vehicle(ID, RFID) VALUES(?, ?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, rfID);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertParkingSpot(int id, int floorID, int sID, int vehTypeID, String spotNum) {
        String sql = "INSERT INTO ParkingSpot(ID, FloorID, StatusID, VehicleTypeID, SpotNumber) VALUES(?, ?, ?, ?, ?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setInt(2, floorID);
            pstmt.setInt(3, sID);
            pstmt.setInt(4, vehTypeID);
            pstmt.setString(5, spotNum);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertStatus(int id, String description){
        String sql = "INSERT INTO Status(ID, Description) VALUES(?, ?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, description);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertFloor(int id, int buildingID, String floorNum) {
        String sql = "INSERT INTO Floor(ID, buildingID, floorNumber) VALUES(?, ?, ?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setInt(2, buildingID);
            pstmt.setString(3, floorNum);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertBuilding(int id, String name) {
        String sql = "INSERT INTO Building(ID, name) VALUES(?, ?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertVehicleType(int id, String Description) {
        String sql = "INSERT INTO VehicleType(ID, Description) VALUES(?, ?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, Description);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void update(int id, String name, double capacity) {
        String sql = "UPDATE warehouses SET name = ? , "
                + "capacity = ? "
                + "WHERE id = ?";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setString(1, name);
            pstmt.setDouble(2, capacity);
            pstmt.setInt(3, id);
            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    
}
    
 
   
    
    

