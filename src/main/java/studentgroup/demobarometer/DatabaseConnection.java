package studentgroup.demobarometer;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;

//Class to establish connection to database
public class DatabaseConnection {
    static final Logger logger = Logger.getLogger(DatabaseConnection.class.getName());
    public Connection databaseLink;

    //Function to identify and access database
    public Connection getConnection() {
        String databaseName = "IoTBarDB";
        String databaseUser = "Ghost";
        String databasePassword = "25052024";
        String url = "jdbc:mysql://localhost/" + databaseName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
        }
        catch (Exception ex){
            logger.log(Level.SEVERE, "An error occurred while connecting to the database", ex);
        }

        return databaseLink;
    }

    //Function to insert measurments from sensors into table of database
    public void insertItem(Measurement measurement) {
        String sql = "INSERT INTO Measurements (temperature, pressure, humidity, approxHeight, time) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, Double.toString(measurement.getTemperature()));
            pstmt.setString(2, Double.toString(measurement.pressure));
            pstmt.setString(3, Double.toString(measurement.getHumidity()));
            pstmt.setString(4, Double.toString(measurement.getApproxHeight()));
            pstmt.setString(5, measurement.getMeasurementDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            pstmt.executeUpdate();

        }
        catch (SQLException ex) {
            logger.log(Level.SEVERE, "An error occurred while connecting to the database", ex);
        }
    }

    //Function to get each latest measurement from database
    public Measurement getLastItem() {
        String sql = "SELECT * FROM Measurements ORDER BY id DESC LIMIT 1;";
        Measurement measurement = null;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                measurement = new Measurement();
                measurement.setId(rs.getInt("id"));
                measurement.setTemperature(rs.getDouble("temperature"));
                measurement.setPressure(rs.getDouble("pressure"));
                measurement.setHumidity(rs.getDouble("humidity"));
                measurement.setApproxHeight(rs.getDouble("approxHeight"));
                measurement.setMeasurementDateTime(rs.getTimestamp("time").toLocalDateTime());
            }

        }
        catch (SQLException ex) {
            logger.log(Level.SEVERE, "An error occurred while connecting to the database", ex);
        }

        return measurement;
    }

    //Function to get measurements for last 7 days
    public ArrayList<Measurement> getLastItemsForWeek() {
        ArrayList<Measurement> measurements = new ArrayList<>();
        String sql = "SELECT \n" +
                "    DATE(time) AS date, \n" +
                "    AVG(temperature) AS avg_temperature, \n" +
                "    AVG(pressure) AS avg_pressure, \n" +
                "    AVG(humidity) AS avg_humidity \n" +
                "FROM \n" +
                "    Measurements \n" +
                "WHERE \n" +
                "    time > DATE_SUB(NOW(), INTERVAL 1 WEEK) \n" +
                "GROUP BY \n" +
                "    DATE(time) \n" +
                "ORDER BY \n" +
                "    DATE(time);";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Measurement measurement = new Measurement();
                measurement.setMeasurementDateTime(rs.getDate("date").toLocalDate().atStartOfDay());
                measurement.setTemperature(rs.getDouble("avg_temperature"));
                measurement.setPressure(rs.getDouble("avg_pressure"));
                measurement.setHumidity(rs.getDouble("avg_humidity"));
                measurements.add(measurement);
            }
        }
        catch (SQLException ex) {
            logger.log(Level.SEVERE, "An error occurred while connecting to the database", ex);
        }

        return measurements;
    }

    //Function to get measurements for last 30 days
    public ArrayList<Measurement> getLastItemsForMonth() {
        ArrayList<Measurement> measurements = new ArrayList<>();
        String sql = "SELECT \n" +
                "    DATE(time) AS date, \n" +
                "    AVG(temperature) AS avg_temperature, \n" +
                "    AVG(pressure) AS avg_pressure, \n" +
                "    AVG(humidity) AS avg_humidity \n" +
                "FROM \n" +
                "    Measurements \n" +
                "WHERE \n" +
                "    time > DATE_SUB(NOW(), INTERVAL 1 MONTH) \n" +
                "GROUP BY \n" +
                "    DATE(time) \n" +
                "ORDER BY \n" +
                "    DATE(time);";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Measurement measurement = new Measurement();
                measurement.setMeasurementDateTime(rs.getDate("date").toLocalDate().atStartOfDay());
                measurement.setTemperature(rs.getDouble("avg_temperature"));
                measurement.setPressure(rs.getDouble("avg_pressure"));
                measurement.setHumidity(rs.getDouble("avg_humidity"));
                measurements.add(measurement);
            }
        }
        catch (SQLException ex) {
            logger.log(Level.SEVERE, "An error occurred while connecting to the database", ex);
        }
        return measurements;
    }
}