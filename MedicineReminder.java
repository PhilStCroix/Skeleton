import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MedicineReminder {
    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public String getDosage() {
        return dosage;
    }

    public ScheduleType getSchedule() {
        return schedule;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    private int id;

    public MedicineReminder() {}

    public MedicineReminder(int id,
                            int userId,
                            String medicineName,
                            String dosage,
                            ScheduleType schedule,
                            String startDate,
                            String endDate) {
        this.id = id;
        this.userId = userId;
        this.medicineName = medicineName;
        this.dosage = dosage;
        this.schedule = schedule;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public void setSchedule(ScheduleType schedule) {
        this.schedule = schedule;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    private int userId;
    private String medicineName;
    private String dosage;
    private ScheduleType schedule;
    private String startDate;
    private String endDate;

    public boolean isDueToday() {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            Timestamp now = new Timestamp(System.currentTimeMillis());
            Timestamp endDate = new Timestamp(format.parse(this.endDate).getTime());
            Timestamp startDate = new Timestamp(format.parse(this.startDate).getTime());

            if (now.getTime() > endDate.getTime() || now.getTime() < startDate.getTime()) {
                return false;
            }

            return switch (this.schedule) {
                case MORNING -> now.getHours() <= 8;
                case AFTERNOON -> now.getHours() <= 13;
                case EVENING -> now.getHours() >= 13;
                case TWICE_DAILY -> now.getHours() <= 8 || now.getHours() >= 13;
                case THRICE_DAILY -> true;
                case AS_NEEDED -> false;
            };
        }
        catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Constructor, getters, and setters
}
