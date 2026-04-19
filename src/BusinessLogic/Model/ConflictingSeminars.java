package BusinessLogic.Model;

import java.util.Objects;

public class ConflictingSeminars {
    private static int counter = 1;
    public String seminarName1;
    public String seminarName2;
    public int id;

    public ConflictingSeminars(String seminarName1, String seminarName2) {
        this.seminarName1 = seminarName1;
        this.seminarName2 = seminarName2;
        this.id = counter++;
    }

    @Override
    public String toString() {
        return "ConflictingSeminars{" +
                "seminarName1='" + seminarName1 + '\'' +
                ", seminarName2='" + seminarName2 + '\'' +
                '}';
    }

    public int checkIfAlreadyConflicted(String seminarName) {
        if(seminarName.equals(seminarName1) || seminarName.equals(seminarName2))
        {
            return this.id;
        }
        return -1;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof String) {
            String seminar = (String) o;
            return seminar.equals(seminarName1) || seminar.equals(seminarName2);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return seminarName1.hashCode() + seminarName2.hashCode();
    }
}
