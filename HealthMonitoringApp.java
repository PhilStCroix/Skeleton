

// import com.DataBaseConnection;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;


public class HealthMonitoringApp {
    private static UserDao userDao = new UserDao();

    private final RecommendationSystem recommendationSystem = new RecommendationSystem();

    public static void main(String[] args) {
        systemTest();
        // prompt the user for their email and password, or register a new user

        // should be different for doctors and patients
        // show a menu for
            // 1. Add health data
            // 2. Generate recommendations
            // 3. Add a medicine reminder
        //
    }

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
    public static void systemTest() {
        HealthMonitoringApp app = new HealthMonitoringApp();

        Collection<User> testUsers = new ArrayList<>();
        User testUser = new User(1, "Ainee", "Malik", "qmalik@gmail.com", "guggu");
        Doctor testDoctor = new Doctor(2, "Stephen", "Strange", "strangephd@avengers.com", "thorIsH0t", "123123", "Sorcery");
        testUsers.add(testUser);
        testUsers.add(testDoctor);

        Collection<HealthData> testHealthData = new ArrayList<>();
        HealthData hd1 = new HealthData(1, 1, 100, 1.8, 100, 100, "2022-09-01 12:00:00", 45, 30, 5000);
        HealthData hd2 = new HealthData(2, 1, 100, 1.7, 50, 70, "2022-10-01 11:45:00", 120, 80, 2000);
        HealthData hd3 = new HealthData(3, 2, 75, 2.1, 16500, 86, "2023-12-17 23:59:59", 160, 110, 2400);

        testHealthData.add(hd1);
        testHealthData.add(hd2);
        testHealthData.add(hd3);

        Collection<MedicineReminder> testMedicineReminders = new ArrayList<>();
        MedicineReminder mr1 = new MedicineReminder(1, 1, "Paracetamol", "500mg", ScheduleType.AS_NEEDED, "2023-09-01", "2024-09-01");
        MedicineReminder mr2 = new MedicineReminder(2, 2, "Welbutrin", "10kg", ScheduleType.THRICE_DAILY, "1995-01-23", "2075-08-02");
        MedicineReminder mr3 = new MedicineReminder(3, 2, "Aderall", "9.5ug", ScheduleType.EVENING, "2023-12-15", "2023-12-31");
        MedicineReminder mr4 = new MedicineReminder(4, 2, "Vitamin D", "1 pill please", ScheduleType.MORNING, "1995-01-23", "2075-08-02");
        testMedicineReminders.add(mr1);
        testMedicineReminders.add(mr2);
        testMedicineReminders.add(mr3);
        testMedicineReminders.add(mr4);

        // test register a new user
        app.registerUsers(testUsers);

        // test Login user (call testLoginUser() here)
        app.testLoginUser("qmalik@gmail.com", "groggu");
        app.testLoginUser("qmalik@gmail.com", "guggu");
        // Add health data
        app.addHealthData(testHealthData);

        // Generate recommendations
        app.generateRecommendations(testHealthData);

        // This stuff is _kinda_ part of just the user portal


        // Add a medicine reminder
        app.addMedicineReminders(testMedicineReminders);

        // Get reminders for a specific user
        Collection<MedicineReminder> reminders = app.getReminders(testDoctor);

        // Get due reminders for a specific user
        Collection<MedicineReminder> dueToday = app.getRemindersToday(testDoctor);



        //test doctor portal (call testDoctorPortal() here)
        //app.testDoctorPortal(testDoctor);


    }

    private void addMedicineReminders(Collection<MedicineReminder> reminders) {
        MedicineReminderDao.createMedicineReminders(reminders);
    }

    private Collection<MedicineReminder> getRemindersToday(User user) {
        Collection<MedicineReminder> reminders = new ArrayList<>();
        for (MedicineReminder reminder : getReminders(user)) {
            if (reminder.isDueToday()) {
                reminders.add(reminder);
            }
        }
        return reminders;
    }

    private Collection<MedicineReminder> getReminders(User user) {
        return MedicineReminderDao.getReminders(user);
    }

    private void generateRecommendations(Collection<HealthData> healthData) {
        Collection<Recommendation> recommendations = new ArrayList<>();
        for (HealthData data : healthData) {
            recommendations.addAll(recommendationSystem.generateRecommendations(data));
        }
        RecommendationDao.createRecommendations(recommendations);
    }

    private void addHealthData(Collection<HealthData> healthData) {
        for (HealthData datum : healthData) {
            HealthDataDao.createHealthData(datum);
        }
    }

    private void registerUsers(Collection<User> users) {
        for (User user : users) {
            if (user instanceof Doctor) {
                UserDao.createDoctor((Doctor) user);
            } else {
                UserDao.createUser(user);
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
    /*
    public void testDoctorPortal(Doctor testDoctor) {

        // asks for his list of patients
        Collection<User> patients = DoctorPortalDao.getPatients(testDoctor);

        User patient = ???? from prompt

        // gets the health data for a prompted patient
        Collection<HealthData> perUser = HealthDataDao.getHealthDataForPatient(patient);
        Collection<Recommendation> recommendationsForPatient = RecommendationDao.getRecommendations(patient);

        // Add code to Fetch health data for the patient

    }*/


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
