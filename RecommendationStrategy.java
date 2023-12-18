public interface RecommendationStrategy {
    String getDescription();
    String getResolution();
    boolean isRecommended(HealthData data);
}
