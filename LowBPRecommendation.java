public class LowBPRecommendation implements RecommendationStrategy {
    @Override
    public String getDescription() {
        return "Low blood pressure";
    }

    @Override
    public String getResolution() {
        return "Try a bag of chips!";
    }

    @Override
    public boolean isRecommended(HealthData data) {
        return (data.getBpHigh() < 90 || data.getBpLow() < 60)
                && data.getHeight() < 1.75; // postural hypotension
    }
}
