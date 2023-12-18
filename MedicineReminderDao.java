import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

public class MedicineReminderDao {

    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public static void createMedicineReminders(Collection<MedicineReminder> reminders) {
        for (MedicineReminder reminder : reminders) {
            createMedicineReminder(reminder);
        }
    }

    public static void createMedicineReminder(MedicineReminder reminder) {
        String query = "INSERT INTO medicine_schedule (cron, start_date, end_date, dosage, medicine, user_id) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection()) {
            Date startDate = new Date(format.parse(reminder.getStartDate()).getTime());
            Date endDate = new Date(format.parse(reminder.getEndDate()).getTime());

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, reminder.getSchedule().name());
            statement.setDate(2, startDate);
            statement.setDate(3, endDate);
            statement.setString(4, reminder.getDosage());
            statement.setString(5, reminder.getMedicineName());
            statement.setInt(6, reminder.getUserId());
            statement.executeUpdate();
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
    }

    public static Collection<MedicineReminder> getReminders(User user) {
        Collection<MedicineReminder> reminders = new ArrayList<>();


        String query = "SELECT * from medicine_schedule WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, user.getId());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                MedicineReminder reminder = new MedicineReminder();
                reminder.setSchedule(ScheduleType.valueOf(rs.getString("cron")));
                reminder.setStartDate(rs.getString("start_date"));
                reminder.setEndDate(rs.getString("end_date"));
                reminder.setDosage(rs.getString("dosage"));
                reminder.setMedicineName(rs.getString("medicine"));
                reminder.setUserId(user.getId());
                reminders.add(reminder);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return reminders;
    }
}
