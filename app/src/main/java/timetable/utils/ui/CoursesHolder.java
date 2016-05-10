package timetable.utils.ui;

/**
 * Created by adriana on 02/11/14.
 */
public class CoursesHolder {

    private String courseName;
    private String hour;

    public CoursesHolder(String courseName, String hour) {
        this.courseName = courseName;
        this.hour = hour;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getHour() {
        return hour;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }
}
