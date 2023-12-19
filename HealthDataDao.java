
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

public class HealthDataDao {
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void createHealthData(HealthData datum) {
        String query = "INSERT INTO healthdata " +
                "(checkup_date, user_Id, weight, height, steps, heartRate, bp_high, bp_low, caloric_intake)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            Timestamp checkupDate = new Timestamp(FORMAT.parse(datum.getCheckupDate()).getTime());
            statement.setTimestamp(1, checkupDate);
            statement.setInt(2, datum.getUserId());
            statement.setDouble(3, datum.getWeight());
            statement.setDouble(4, datum.getHeight());
            statement.setInt(5, datum.getSteps());
            statement.setInt(6, datum.getHeartRate());
            statement.setDouble(7, datum.getBpHigh());
            statement.setDouble(8, datum.getBpLow());
            statement.setDouble(9, datum.getCaloricIntake());
            statement.executeUpdate();
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
    }



    // public boolean createHealthData(HealthData healthData) { /* insert health data into database */ }
  //  public HealthData getHealthDataById(int id) { /* get health data by id from database */ }
  //  public List<HealthData> getHealthDataByUserId(int userId) { /* get health data by user id from database */ }
  //  public boolean updateHealthData(HealthData healthData) { /* update health data in the database */ }
  //  public boolean deleteHealthData(int id) { /* delete health data from the database */ }
}
