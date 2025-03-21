package edu.gcc.comp350;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProfessorTest {

    @Test
    public void testProfessorCreation() {
        Professor prof1 = new Professor(1, 0, "Inman, John G.", "ABRD");
        Professor prof2 = new Professor(2, 0, "Graybill, Keith B.", "ACCT");
        Professor prof3 = new Professor(3, 0, "Shultz, Tricia Michele", "ACCT");

        assertEquals(1, prof1.getID());
        assertEquals("Inman, John G.", prof1.getName());
        assertEquals("ABRD", prof1.getDepartment());

        assertEquals(2, prof2.getID());
        assertEquals("Graybill, Keith B.", prof2.getName());
        assertEquals("ACCT", prof2.getDepartment());

        assertEquals(3, prof3.getID());
        assertEquals("Shultz, Tricia Michele", prof3.getName());
        assertEquals("ACCT", prof3.getDepartment());
    }

    @Test
    public void testEdgeCases() {
        // Professor with an empty name
        Professor emptyNameProf = new Professor(4, 0, "", "ACCT");
        assertEquals("", emptyNameProf.getName());

        // Professor with missing department
        Professor missingDeptProf = new Professor(5, 0, "Bauer, Christian Jr.", "");
        assertEquals("", missingDeptProf.getDepartment());
    }
}