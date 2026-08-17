import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class HospitalSystemTest {

    @Test
    public void testRegisterPatient() {

        HospitalSystem hospital = new HospitalSystem();

        Patient patient = new Patient(
                "P001",
                "John",
                "Smith",
                30,
                "Male",
                "Flu",
                PatientCategory.OUTPATIENT
        );

        assertTrue(hospital.registerPatient(patient));
        assertNotNull(hospital.searchPatient("P001"));
    }

    @Test
    public void testSearchPatient() {

        HospitalSystem hospital = new HospitalSystem();

        Patient patient = new Patient(
                "P002",
                "Mary",
                "Jones",
                25,
                "Female",
                "Fever",
                PatientCategory.EMERGENCY
        );

        hospital.registerPatient(patient);

        assertEquals(
                "P002",
                hospital.searchPatient("P002").getPatientId()
        );
    }

    @Test
    public void testUpdatePatient() {

        HospitalSystem hospital = new HospitalSystem();

        Patient patient = new Patient(
                "P003",
                "John",
                "Smith",
                30,
                "Male",
                "Flu",
                PatientCategory.OUTPATIENT
        );

        hospital.registerPatient(patient);

        assertTrue(hospital.updatePatient(
                "P003",
                "James",
                "Brown",
                35,
                "Male",
                "Cold",
                PatientCategory.OUTPATIENT
        ));

        assertEquals(
                "James",
                hospital.searchPatient("P003").getFirstName()
        );
    }

    @Test
    public void testDeletePatient() {

        HospitalSystem hospital = new HospitalSystem();

        Patient patient = new Patient(
                "P004",
                "David",
                "Miller",
                40,
                "Male",
                "Infection",
                PatientCategory.OUTPATIENT
        );

        hospital.registerPatient(patient);

        assertTrue(hospital.deletePatient("P004"));
        assertNull(hospital.searchPatient("P004"));
    }

    @Test
    public void testAllocateBed() {

        HospitalSystem hospital = new HospitalSystem();

        Inpatient patient = new Inpatient(
                "P005",
                "Sarah",
                "Williams",
                45,
                "Female",
                "Pneumonia",
                "Ward 1",
                ""
        );

        hospital.registerPatient(patient);

        assertTrue(hospital.allocateBed("P005", "B01"));
        assertEquals(1, hospital.getOccupiedBedCount());
    }

    @Test
    public void testReleaseBed() {

        HospitalSystem hospital = new HospitalSystem();

        Inpatient patient = new Inpatient(
                "P006",
                "Tom",
                "Wilson",
                50,
                "Male",
                "Flu",
                "Ward 1",
                ""
        );

        hospital.registerPatient(patient);

        hospital.allocateBed("P006", "B02");

        assertTrue(hospital.releaseBed("B02"));
        assertEquals(0, hospital.getOccupiedBedCount());
    }

    @Test
    public void testDuplicatePatientId() {

        HospitalSystem hospital = new HospitalSystem();

        Patient patient1 = new Patient(
                "P007",
                "John",
                "Smith",
                30,
                "Male",
                "Flu",
                PatientCategory.OUTPATIENT
        );

        Patient patient2 = new Patient(
                "P007",
                "Peter",
                "Jones",
                40,
                "Male",
                "Cold",
                PatientCategory.OUTPATIENT
        );

        assertTrue(hospital.registerPatient(patient1));
        assertFalse(hospital.registerPatient(patient2));
    }

    @Test
    public void testPreventOccupiedBed() {

        HospitalSystem hospital = new HospitalSystem();

        Inpatient patient1 = new Inpatient(
                "P008",
                "John",
                "Smith",
                30,
                "Male",
                "Flu",
                "Ward 1",
                ""
        );

        Inpatient patient2 = new Inpatient(
                "P009",
                "Peter",
                "Jones",
                40,
                "Male",
                "Cold",
                "Ward 1",
                ""
        );

        hospital.registerPatient(patient1);
        hospital.registerPatient(patient2);

        assertTrue(hospital.allocateBed("P008", "B03"));

        assertFalse(hospital.allocateBed("P009", "B03"));
    }
}