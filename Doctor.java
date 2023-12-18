
public class Doctor extends User {
    private final String medicalLicenseNumber;
    private final String specialization;

    public Doctor(int id, String firstName, String lastName, String email, String password, String medicalLicenseNumber, String specialization) {
        super(id, firstName, lastName, email, password);
        this.medicalLicenseNumber = medicalLicenseNumber;
        this.specialization = specialization;
    }

    public String getMedicialLicenseNumber() {
        return medicalLicenseNumber;
    }

    public String getSpecialization() {
        return specialization;
    }
}

