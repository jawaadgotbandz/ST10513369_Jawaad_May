import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class HospitalSystem {

    private ArrayList<Patient> patients = new ArrayList<>();
    private String[] beds = new String[20];
    private Scanner input = new Scanner(System.in);

    public HospitalSystem() {
        for (int i = 0; i < beds.length; i++) {
            beds[i] = null;
        }
    }

    // PATIENT MANAGEMENT

    public boolean registerPatient(Patient patient) {

        if (searchPatient(patient.getPatientId()) != null) {
            System.out.println("Patient ID already exists.");
            return false;
        }

        patients.add(patient);
        System.out.println("Patient registered successfully.");
        return true;
    }

    public Patient searchPatient(String patientId) {

        for (Patient patient : patients) {
            if (patient.getPatientId().equals(patientId)) {
                return patient;
            }
        }

        return null;
    }

    public boolean updatePatient(String patientId, String firstName,
                                 String lastName, int age, String gender,
                                 String condition, PatientCategory category) {

        Patient patient = searchPatient(patientId);

        if (patient == null) {
            return false;
        }

        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setAge(age);
        patient.setGender(gender);
        patient.setMedicalCondition(condition);
        patient.setCategory(category);

        return true;
    }

    public boolean deletePatient(String patientId) {

        Patient patient = searchPatient(patientId);

        if (patient == null) {
            return false;
        }

        // Release the patient's bed if they are an inpatient
        if (patient instanceof Inpatient) {
            Inpatient inpatient = (Inpatient) patient;
            releaseBed(inpatient.getBedNumber());
        }

        patients.remove(patient);
        return true;
    }

    public void displayAllPatients() {

        if (patients.isEmpty()) {
            System.out.println("No registered patients.");
            return;
        }

        for (Patient patient : patients) {
            System.out.println("----------------------------");
            patient.displayDetails();
        }
    }

    // BED MANAGEMENT

    public boolean allocateBed(String patientId, String bedNumber) {

        Patient patient = searchPatient(patientId);

        if (patient == null) {
            System.out.println("Patient not found.");
            return false;
        }

        if (patient.getCategory() != PatientCategory.INPATIENT) {
            System.out.println("Only inpatients may be allocated a bed.");
            return false;
        }

        int bedIndex = getBedIndex(bedNumber);

        if (bedIndex == -1) {
            System.out.println("Invalid bed number.");
            return false;
        }

        if (beds[bedIndex] != null) {
            System.out.println("Bed is already occupied.");
            return false;
        }

        // Check if inpatient already has a bed
        for (int i = 0; i < beds.length; i++) {
            if (patientId.equals(beds[i])) {
                System.out.println("Patient already has a bed.");
                return false;
            }
        }

        beds[bedIndex] = patientId;

        // Convert patient to Inpatient if necessary
        if (patient instanceof Inpatient) {
            Inpatient inpatient = (Inpatient) patient;
            inpatient.setBedNumber(bedNumber);
        }

        System.out.println("Bed " + bedNumber + " allocated successfully.");
        return true;
    }

    public boolean releaseBed(String bedNumber) {

        int bedIndex = getBedIndex(bedNumber);

        if (bedIndex == -1) {
            return false;
        }

        if (beds[bedIndex] == null) {
            return false;
        }

        String patientId = beds[bedIndex];

        Patient patient = searchPatient(patientId);

        if (patient instanceof Inpatient) {
            Inpatient inpatient = (Inpatient) patient;
            inpatient.setBedNumber("");
        }

        beds[bedIndex] = null;

        return true;
    }

    private int getBedIndex(String bedNumber) {

        if (bedNumber == null || bedNumber.length() != 3) {
            return -1;
        }

        if (!bedNumber.startsWith("B")) {
            return -1;
        }

        try {
            int number = Integer.parseInt(bedNumber.substring(1));

            if (number < 1 || number > 20) {
                return -1;
            }

            return number - 1;

        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void displayWardLayout() {

        System.out.println("\nWARD LAYOUT");

        for (int i = 0; i < 20; i++) {

            String bedNumber = String.format("B%02d", i + 1);

            if (beds[i] == null) {
                System.out.print("[" + bedNumber + " Available] ");
            } else {
                System.out.print("[" + bedNumber + " Occupied] ");
            }

            if ((i + 1) % 5 == 0) {
                System.out.println();
            }
        }
    }

    public void displayAvailableBeds() {

        System.out.println("\nAVAILABLE BEDS:");

        for (int i = 0; i < 20; i++) {

            if (beds[i] == null) {
                System.out.print(String.format("B%02d ", i + 1));
            }
        }

        System.out.println();
    }

    public void displayOccupiedBeds() {

        System.out.println("\nOCCUPIED BEDS:");

        boolean found = false;

        for (int i = 0; i < 20; i++) {

            if (beds[i] != null) {

                System.out.println(
                        String.format("B%02d -> Patient %s",
                                i + 1, beds[i])
                );

                found = true;
            }
        }

        if (!found) {
            System.out.println("No beds are occupied.");
        }
    }

    // REPORTS

    public void displayReport() {

        System.out.println("\n========== WARD REPORT ==========");

        System.out.println("\nREGISTERED PATIENTS:");
        displayAllPatients();

        System.out.println("\nAVAILABLE BEDS:");
        displayAvailableBeds();

        System.out.println("\nOCCUPIED BEDS:");
        displayOccupiedBeds();

        int occupied = getOccupiedBedCount();

        double percentage = (occupied / 20.0) * 100;

        System.out.println("\nTotal registered patients: "
                + patients.size());

        System.out.println("Total occupied beds: "
                + occupied);

        System.out.println("Ward occupancy: "
                + percentage + "%");

        System.out.println("=================================");
    }

    public int getOccupiedBedCount() {

        int count = 0;

        for (String bed : beds) {
            if (bed != null) {
                count++;
            }
        }

        return count;
    }

    public int getAvailableBedCount() {
        return 20 - getOccupiedBedCount();
    }

    // SORTING

    public void sortBySurname() {

        Collections.sort(patients,
                Comparator.comparing(Patient::getLastName));

        System.out.println("Patients sorted by surname.");
    }

    public void sortByPatientId() {

        Collections.sort(patients,
                Comparator.comparing(Patient::getPatientId));

        System.out.println("Patients sorted by Patient ID.");
    }

    // MENU

    public void run() {

        boolean running = true;

        while (running) {

            System.out.println("\n=================================");
            System.out.println("   MEDICARE HOSPITAL SYSTEM");
            System.out.println("=================================");
            System.out.println("1. Register Patient");
            System.out.println("2. Search Patient");
            System.out.println("3. Update Patient");
            System.out.println("4. Delete Patient");
            System.out.println("5. Display All Patients");
            System.out.println("6. Allocate Bed");
            System.out.println("7. Release Bed");
            System.out.println("8. Display Ward Layout");
            System.out.println("9. Display Available Beds");
            System.out.println("10. Display Occupied Beds");
            System.out.println("11. Ward Report");
            System.out.println("12. Sort by Surname");
            System.out.println("13. Sort by Patient ID");
            System.out.println("14. Exit");
            System.out.print("Choose an option: ");

            String choice = input.nextLine();

            switch (choice) {

                case "1":
                    registerPatientMenu();
                    break;

                case "2":
                    searchPatientMenu();
                    break;

                case "3":
                    updatePatientMenu();
                    break;

                case "4":
                    deletePatientMenu();
                    break;

                case "5":
                    displayAllPatients();
                    break;

                case "6":
                    allocateBedMenu();
                    break;

                case "7":
                    releaseBedMenu();
                    break;

                case "8":
                    displayWardLayout();
                    break;

                case "9":
                    displayAvailableBeds();
                    break;

                case "10":
                    displayOccupiedBeds();
                    break;

                case "11":
                    displayReport();
                    break;

                case "12":
                    sortBySurname();
                    break;

                case "13":
                    sortByPatientId();
                    break;

                case "14":
                    running = false;
                    System.out.println("Thank you for using Medicare Hospital System.");
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    // MENU METHODS

    private void registerPatientMenu() {

        System.out.print("Patient ID: ");
        String id = input.nextLine();

        if (searchPatient(id) != null) {
            System.out.println("Patient ID already exists.");
            return;
        }

        System.out.print("First Name: ");
        String firstName = input.nextLine();

        System.out.print("Last Name: ");
        String lastName = input.nextLine();

        System.out.print("Age: ");
        int age = Integer.parseInt(input.nextLine());

        System.out.print("Gender: ");
        String gender = input.nextLine();

        System.out.print("Medical Condition: ");
        String condition = input.nextLine();

        System.out.println("Category:");
        System.out.println("1. Inpatient");
        System.out.println("2. Outpatient");
        System.out.println("3. Emergency");

        System.out.print("Choose category: ");
        int categoryChoice = Integer.parseInt(input.nextLine());

        PatientCategory category;

        if (categoryChoice == 1) {
            category = PatientCategory.INPATIENT;
        } else if (categoryChoice == 2) {
            category = PatientCategory.OUTPATIENT;
        } else {
            category = PatientCategory.EMERGENCY;
        }

        Patient patient;

        if (category == PatientCategory.INPATIENT) {

            System.out.print("Ward Number: ");
            String ward = input.nextLine();

            patient = new Inpatient(
                    id,
                    firstName,
                    lastName,
                    age,
                    gender,
                    condition,
                    ward,
                    ""
            );

        } else {

            patient = new Patient(
                    id,
                    firstName,
                    lastName,
                    age,
                    gender,
                    condition,
                    category
            );
        }

        registerPatient(patient);
    }

    private void searchPatientMenu() {

        System.out.print("Enter Patient ID: ");
        String id = input.nextLine();

        Patient patient = searchPatient(id);

        if (patient == null) {
            System.out.println("Patient not found.");
        } else {
            System.out.println("\nPATIENT DETAILS");
            System.out.println("----------------------------");
            patient.displayDetails();
        }
    }

    private void updatePatientMenu() {

        System.out.print("Enter Patient ID: ");
        String id = input.nextLine();

        Patient patient = searchPatient(id);

        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        System.out.print("First Name: ");
        String firstName = input.nextLine();

        System.out.print("Last Name: ");
        String lastName = input.nextLine();

        System.out.print("Age: ");
        int age = Integer.parseInt(input.nextLine());

        System.out.print("Gender: ");
        String gender = input.nextLine();

        System.out.print("Medical Condition: ");
        String condition = input.nextLine();

        System.out.println("1. Inpatient");
        System.out.println("2. Outpatient");
        System.out.println("3. Emergency");

        System.out.print("Category: ");
        int choice = Integer.parseInt(input.nextLine());

        PatientCategory category;

        if (choice == 1) {
            category = PatientCategory.INPATIENT;
        } else if (choice == 2) {
            category = PatientCategory.OUTPATIENT;
        } else {
            category = PatientCategory.EMERGENCY;
        }

        updatePatient(
                id,
                firstName,
                lastName,
                age,
                gender,
                condition,
                category
        );

        System.out.println("Patient updated successfully.");
    }

    private void deletePatientMenu() {

        System.out.print("Enter Patient ID: ");
        String id = input.nextLine();

        System.out.print("Are you sure you want to delete this patient? (Y/N): ");
        String answer = input.nextLine();

        if (answer.equalsIgnoreCase("Y")) {

            if (deletePatient(id)) {
                System.out.println("Patient deleted successfully.");
            } else {
                System.out.println("Patient not found.");
            }
        }
    }

    private void allocateBedMenu() {

        System.out.print("Enter Patient ID: ");
        String id = input.nextLine();

        System.out.print("Enter Bed Number (B01-B20): ");
        String bed = input.nextLine().toUpperCase();

        allocateBed(id, bed);
    }

    private void releaseBedMenu() {

        System.out.print("Enter Bed Number (B01-B20): ");
        String bed = input.nextLine().toUpperCase();

        if (releaseBed(bed)) {
            System.out.println("Bed released successfully.");
        } else {
            System.out.println("Bed could not be released.");
        }
    }
}
