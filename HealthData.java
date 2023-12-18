import java.sql.Timestamp;

public class HealthData {
    private final int id;
    private final int userId;
    private final double weightKg;
    private final double heightMeters;
    private final int steps;
    private final int heartRate;
    private final int bpHigh;
    private final int bpLow;
    private final int caloricIntake;
    private final String checkupDate;

    public HealthData(int id,
                      int userId,
                      double weight,
                      double height,
                      int steps,
                      int heartRate,
                      String checkupDate,
                      int bpHigh,
                      int bpLow,
                      int caloricIntake) {
        this.id = id;
        this.userId = userId;
        this.weightKg = weight;
        this.heightMeters = height;
        this.steps = steps;
        this.heartRate = heartRate;
        this.bpHigh = bpHigh;
        this.bpLow = bpLow;
        this.caloricIntake = caloricIntake;
        this.checkupDate = checkupDate;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public double getWeight() {
        return weightKg;
    }

    public double getHeight() {
        return heightMeters;
    }

    public int getSteps() {
        return steps;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public int getBpHigh() {
        return bpHigh;
    }

    public int getBpLow() {
        return bpLow;
    }

    public int getCaloricIntake() {
        return caloricIntake;
    }

    public String getCheckupDate() {
        return checkupDate;
    }

}
