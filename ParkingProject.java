
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


    //createNewTable();
    ParkingProject app = new ParkingProject();
    app.drivein(34234, 3, 87);

    //app.initialize();

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

    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // don't use this method, use the MySQLDB.txt script instead
 public void initialize () {

     java.util.Date sTime = new java.util.Date();
     java.util.Date eTime = new java.util.Date();

     insertVehicle(23543);
     insertVehicle( 22343);
     insertVehicle( 27843);

     insertBuilding(23, "garage");
     insertBuilding(14, "ground");
     insertBuilding(41, "b92");

     insertStatus(25346, "taken");
     insertStatus(23498, "vacant");
     insertStatus(87652, "taken");

     insertVehicleType(254983, "car");
     insertVehicleType(905427, "SUV");
     insertVehicleType(984463, "motorcycle");

     insertFloor(3, 23, "3");
     insertFloor(1, 14, "1");
     insertFloor(5, 41, "5");

     //parking spots should be created in a for loop (there should be a bunch of them)
     insertParkingSpot(34, 3, 1, 254983, 87);
     insertParkingSpot(67, 1, 2, 905427, 85);
     insertParkingSpot(87, 5, 3, 984463, 32);

     insertParkingHistory(34, 1, new Date(sTime.getTime()), new Date(eTime.getTime()));
     insertParkingHistory( 67, 2, new Date(sTime.getTime()), new Date(eTime.getTime()));
     insertParkingHistory( 87, 3, new Date(sTime.getTime()), new Date(eTime.getTime()));
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
    
    public void insertParkingHistory(int parkingSpotID, int vID, java.util.Date sTime, Date eTime) {
        String sql = "INSERT INTO ParkingHistory(ParkingSpotID, VehicleID, startTime, endTime) VALUES(?, ?, ?, ?)";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, parkingSpotID);
            pstmt.setInt(2, vID);
            pstmt.setDate(3, new Date(sTime.getTime()));
            pstmt.setDate(4, eTime);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int insertVehicle(int rfID) {
        String sql = "INSERT INTO Vehicle(RFID) VALUES(?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, rfID);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return findVehicleID(rfID);
    }

    public void insertParkingSpot(int id, int floorID, int sID, int vehTypeID, int spotNum) {
        String sql = "INSERT INTO ParkingSpot(ID, FloorID, StatusID, VehicleTypeID, SpotNumber) VALUES(?, ?, ?, ?, ?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setInt(2, floorID);
            pstmt.setInt(3, sID);
            pstmt.setInt(4, vehTypeID);
            pstmt.setInt(5, spotNum);
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

    //a method that is called when a car parks in one of the parking spots
    public void drivein(int rfid, int floor, int spotNumber) {
            int id = insertVehicle(rfid);
            if (id != -1) {
                insertParkingHistory(spotNumber, id, new java.util.Date(), null);
                updateStatus(floor, spotNumber, 2);
            }
    }

    public void updateStatus(int floor, int spotNumber, int status) {
        String sql = "UPDATE ParkingSpot SET StatusID = ? WHERE FloorID = ? AND SpotNumber = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, status);
            pstmt.setInt(2, floor);
            pstmt.setInt(3, spotNumber);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int findVehicleID(int rfID) {
        String sql = "SELECT id FROM Vehicle WHERE rfID = ?";

        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, rfID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }
}
    
 
   
    
    

