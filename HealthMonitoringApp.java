

// import com.DataBaseConnection;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class HealthMonitoringApp {
    private static UserDaoExample userDao = new UserDaoExample();
    /**
     * Test the following functionalities within the Main Application
     *  1. Register a new user
     *  2. Log in the user
     *  3. Add health data
     *  4. Generate recommendations
     *  5. Add a medicine reminder
     *  6. Get reminders for a specific user
     *  7. Get due reminders for a specific user
     *  8. test doctor portal
     */
    public static void main(String[] args) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        UserDaoExample userDao = new UserDaoExample();

        HealthMonitoringApp app = new HealthMonitoringApp();

        Collection<User> testUsers = new ArrayList<>();
        User testUser = new User(1, "Ainee", "Malik", "qmalik@gmail.com", "guggu");
        Doctor testDoctor = new Doctor(2, "Stephen", "Strange", "strangephd@avengers.com", "thorIsH0t", "123123", "Sorcery");
        testUsers.add(testUser);
        testUsers.add(testDoctor);

        Collection<HealthData> testHealthData = new ArrayList<>();
        HealthData hd1 = new HealthData(1, 1, 100, 1.8, 100, 100, "2022-09-01 12:00:00", 120, 80, 5000);
        HealthData hd2 = new HealthData(2, 1, 100, 1.7, 50, 70, "2022-10-01 11:45:00", 120, 80, 2000);
        HealthData hd3 = new HealthData(3, 2, 75, 2.1, 16500, 86, "2023-12-17 23:59:59", 160, 110, 2400);

        testHealthData.add(hd1);
        testHealthData.add(hd2);
        testHealthData.add(hd3);

        Collection<MedicineReminder> testMedicineReminders = new ArrayList<>();

        // test register a new user
        app.registerUsers(testUsers);

        // test Login user (call testLoginUser() here)
        app.testLoginUser("qmalik@gmail.com", "groggu");
        app.testLoginUser("qmalik@gmail.com", "guggu");
        // Add health data
        app.addHealthData(testHealthData);

        // Generate recommendations
        app.generateRecommendations();

        // Add a medicine reminder
        app.addMedicineReminders(testMedicineReminders);

        // Get reminders for a specific user
        Collection<MedicineReminder> reminders = app.getReminders(testUser);

        // Get due reminders for a specific user
        Collection<MedicineReminder> dueToday = app.getRemindersToday(testUser);

        //test doctor portal (call testDoctorPortal() here)
        app.testDoctorPortal(testDoctor);


    }

    private void addMedicineReminders(Collection<MedicineReminder> reminders) {

    }

    private Collection<MedicineReminder> getRemindersToday(User user) {
        return getReminders(user)
                .stream()
                .filter(MedicineReminder::isDueToday)
                .collect(Collectors.toList());
    }

    private Collection<MedicineReminder> getReminders(User user) {
        return Collections.emptyList();
    }

    private void generateMedicineReminders() {

    }

    private void generateRecommendations() {

    }

    private void addHealthData(Collection<HealthData> healthData) {
        for (HealthData datum : healthData) {
            HealthDataDao.createHealthData(datum);
        }
    }

    private void registerUsers(Collection<User> users) {
        for (User user : users) {
            if (user instanceof Doctor) {
                UserDaoExample.createDoctor((Doctor) user);
            } else {
                UserDaoExample.createUser(user);
            }
        }
    }


    public boolean loginUser(String email, String password) {
        //implement method to login user.
        User user = userDao.getUserByEmail(email);

        if (user != null) {
            return BCrypt.checkpw(password, user.getPassword());
        }

        return false;

    }


    /**
     * To test the Doctor Portal in your Health Monitoring System, provide a simple test code method that you can add
     * to your main application class.
     * In this method, we'll test the following functionalities:
     * 1. Fetching a doctor by ID
     * 2. Fetching patients associated with a doctor
     * 3. Fetching health data for a specific patient
      */
    public void testDoctorPortal(Doctor testDoctor) {


        // Add code to Fetch the doctor by ID


        // Add code to Fetch patients associated with the doctor

        // Add code to Fetch health data for the patient

    }


    /**
     * To test the login user functionality in your Health Monitoring System, you can
     * add a test method to your main application class
     */
    public boolean testLoginUser(String userEmail, String userPassword) {
        if (!loginUser(userEmail, userPassword)) {
            System.out.println("Logged in failed");
            return false;
        }
        System.out.println("Logged in succesfully");
        return true;
    }

}
