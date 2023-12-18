

import java.util.ArrayList;
import java.util.Collection;

/**
 * In this basic version of the
 * RecommendationSystem class, complete the generateRecommendations to take a
 * HealthData object as input and generates recommendations based on the user's heart rate and step count.
 * You can also expand this class to include more health data analysis and generate more specific
 * recommendations based on the user's unique health profile
 * NOTE:
 * To integrate this class into your application, you'll need to pass the HealthData object to the generateRecommendations method
 * and store the generated recommendations in the recommendations table in the database.
 */

public class RecommendationSystem {
    private final Collection<RecommendationStrategy> strategies;

    public RecommendationSystem() {
        strategies = new ArrayList<>();
        strategies.add(new StepCountRecommendation());
        strategies.add(new HighBPRecommendation());
        strategies.add(new LowBPRecommendation());
        // TODO add some stuff for weight, calories, etc
    }

    public Collection<Recommendation> generateRecommendations(HealthData healthData) {
        Collection<Recommendation> recommendations = new ArrayList<>();
        for (RecommendationStrategy r : strategies) {
            if (r.isRecommended(healthData)) {
                Recommendation recommendation = new Recommendation(r.getDescription(), r.getResolution(), healthData.getId());
                recommendations.add(recommendation);
            }
        }
        return recommendations;
    }
}
