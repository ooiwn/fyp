package my.edu.utar.healthhelper;

import java.util.List;

public class Medicine {
    String id;
    String medName;
    String medStrength;
    int medNum;
    int refillNum;
    List<String> dayToTake;
    String time;

    public Medicine() {}

    public Medicine(String id, String medName, String medStrength, int medNum, int refillNum, List<String> dayToTake, String time) {
        this.id = id;
        this.medName = medName;
        this.medStrength = medStrength;
        this.medNum = medNum;
        this.refillNum = refillNum;
        this.dayToTake = dayToTake;
        this.time = time;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public String getMedStrength() {
        return medStrength;
    }

    public void setMedStrength(String medStrength) {
        this.medStrength = medStrength;
    }

    public int getMedNum() {
        return medNum;
    }

    public void setMedNum(int medNum) {
        this.medNum = medNum;
    }

    public int getRefillNum() {
        return refillNum;
    }

    public void setRefillNum(int refillNum) {
        this.refillNum = refillNum;
    }

    public List<String> getDayToTake() {
        return dayToTake;
    }

    public void setDayToTake(List<String> dayToTake) {
        this.dayToTake = dayToTake;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
