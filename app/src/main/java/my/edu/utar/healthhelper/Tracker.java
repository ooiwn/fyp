package my.edu.utar.healthhelper;

import java.util.ArrayList;
import java.util.List;

public class Tracker {

    String id;
    String trackerName;
    String unit;
    String duration;
    String untilDate;
    String frequency;
    int everyHour;
    List<String> dayToTrack;
    String time;
    List<String> status = new ArrayList<>();

    public Tracker() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrackerName() {
        return trackerName;
    }

    public void setTrackerName(String trackerName) {
        this.trackerName = trackerName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getUntilDate() {
        return untilDate;
    }

    public void setUntilDate(String untilDate) {
        this.untilDate = untilDate;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public int getEveryHour() {
        return everyHour;
    }

    public void setEveryHour(int everyHour) {
        this.everyHour = everyHour;
    }

    public List<String> getDayToTrack() {
        return dayToTrack;
    }

    public void setDayToTrack(List<String> dayToTrack) {
        this.dayToTrack = dayToTrack;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<String> getStatus() {
        return status;
    }

    public void setStatus(List<String> status) {
        this.status = status;
    }
}
