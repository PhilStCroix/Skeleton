public class HighBPRecommendation implements RecommendationStrategy {
    @Override
    public String getDescription() {
        return "High Blood Pressure";
    }

    @Override
    public String getResolution() {
        return "Eat less sodium, and drink more water.";
    }

    @Override
    public boolean isRecommended(HealthData data) {
        return data.getBpHigh() > 140
            || data.getBpLow() > 100;
    }
}
