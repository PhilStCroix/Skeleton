public class StepCountRecommendation implements RecommendationStrategy {
    @Override
    public String getDescription() {
        return "Your daily step count is too low";
    }

    @Override
    public String getResolution() {
        return "Try to get out for a 20 minute morning walk";
    }

    @Override
    public boolean isRecommended(HealthData data) {
        return data.getSteps() < 2000;
    }
}
