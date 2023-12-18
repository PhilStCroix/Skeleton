public class Recommendation {
    public String getDiagnosis() {
        return diagnosis;
    }

    public String getResolution() {
        return resolution;
    }

    public int getCheckupId() {
        return checkupId;
    }

    private final String diagnosis;
    private final String resolution;
    private final int checkupId;

    public Recommendation(String diagnosis, String resolution, int checkupId) {
        this.diagnosis = diagnosis;
        this.resolution = resolution;
        this.checkupId = checkupId;
    }
}
