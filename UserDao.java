import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;

public class UserDao {


    public static boolean createDoctor(Doctor doctor) {
        if (!createUser(doctor)) {
            return false;
        }

        int userId = getUserId(doctor);
        String query = "INSERT INTO doctors (user_id, ml_number, specialization) values (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, userId);
            statement.setString(2,doctor.getMedicialLicenseNumber());
            statement.setString(3, doctor.getSpecialization());
            int updatedRows = statement.executeUpdate();
            if (updatedRows != 0) {
                return true;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Retrieves the user ID associated with the given user.
     *
     * @param  user  the user object for which to retrieve the ID
     * @return       the ID of the user, or 0 if an error occurs
     */
    private static int getUserId(User user) {
        String userIdQuery = "SELECT id from users where email = ?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(userIdQuery);
            statement.setString(1, user.getEmail());
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getInt(1);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static boolean createUser(User user) {
        boolean success = false;
        /* insert user into database */
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

        // Prepare the SQL query
        String query = "INSERT INTO users (first_name, last_name, email, password_hash) " +
                "VALUES (?, ?, ?, ?)";

        // Database logic to insert data using PREPARED Statement
        try (Connection con = DatabaseConnection.getConnection()) {
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, hashedPassword);
            // TODO put the fact of is doctor somewhere
//            statement.setBoolean(5, user.isDoctor());
            int updatedRows = statement.executeUpdate();
            if (updatedRows != 0) {
                success = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    public User getUserById(int id) { /* get user by id from database */
        int user_id = 0;
        String firstName = null;
        String lastName = null;
        String email = null;
        String password = null;

        // Prepare the SQL query
        String query = "SELECT * FROM users WHERE id = ?";

        // Database logic to get data by ID Using Prepared Statement
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                user_id = rs.getInt("id");
                firstName = rs.getString("first_name");
                lastName = rs.getString("last_name");
                email = rs.getString("email");
                password = rs.getString("password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new User(user_id, firstName, lastName, email, password);
    }

    /**
     * Retrieves a user from the database based on their email.
     *
     * @param  email  the email of the user to retrieve
     * @return        the User object representing the retrieved user, or null if no user is found
     */
    public static User getUserByEmail(String email) { /* get user by email from database */
        // Prepare the SQL query
        String query = "SELECT * FROM users WHERE email = ?";

        // Database logic to get data by ID Using Prepared Statement
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String user_email = rs.getString("email");
                String password = rs.getString("password_hash");
                return new User(id, firstName, lastName, user_email, password);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }


    public boolean updateUser(User user) {
        boolean bool = false;
        // Prepare the SQL query
        String query = "UPDATE users " +
                "SET first_name = ?, last_name = ?, email = ?, password_hash = ?" +
                "WHERE id = ?";
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

        // Database logic to get update user Using Prepared Statement
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, hashedPassword);
            statement.setInt(5, user.getId());
            int updatedRows = statement.executeUpdate();
            if (updatedRows != 0) {
                bool = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bool;
    }

    public boolean deleteUser(int id) { /* delete user from the database */
        boolean bool = false;
        // Prepare the SQL query
        String query = "DELETE FROM users WHERE id = ?";

        // Database logic to delete user
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, id);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated != 0){
                bool = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bool;
    }

    public static boolean verifyPassword (String email, String password){
        boolean bool = false;
        String query = "SELECT password FROM users WHERE email = ?";
        //Implement logic to retrieve password using the Bcrypt
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            String hashedPassword = null;
            while (rs.next()) {
                hashedPassword = rs.getString("password");
            }
            if (BCrypt.checkpw(password, hashedPassword)) {
                bool = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bool;
    }

}