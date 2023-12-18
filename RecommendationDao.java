import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class RecommendationDao {

    public static void createRecommendations(Collection<Recommendation> recommendations) {
        for (Recommendation r : recommendations) {
            createRecommendation(r);
        }
    }

    public static void createRecommendation(Recommendation recommendation) {
        String query = "INSERT INTO recommendations (checkup_id, diagnosis,resolution) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, recommendation.getCheckupId());
            statement.setString(2, recommendation.getDiagnosis());
            statement.setString(3, recommendation.getResolution());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void generateRecommendations() {
        RecommendationSystem recommendationSystem = new RecommendationSystem();
        Collection<Recommendation> recommendations = new ArrayList<>();
        Collection<HealthData> healthData = new ArrayList<>();

// TODO
//        Collection<HealthData> healthData = HealthDataDao.getUpToDateHealthData();
        // if not possible, just generate the recommendations one time whenever the health data is entered

        for (HealthData data : healthData) {
            recommendations.addAll(recommendationSystem.generateRecommendations(data));
        }
        RecommendationDao.createRecommendations(recommendations);
    }

}
