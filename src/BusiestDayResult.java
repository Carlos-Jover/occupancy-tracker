import java.time.DayOfWeek;
import java.util.ArrayList;

public class BusiestDayResult {
    private ArrayList<DayOfWeek> dayOfWeek;
    private double averageTraffic;

    public BusiestDayResult(ArrayList<DayOfWeek> dayOfWeek, double averageTraffic) {
        this.dayOfWeek = dayOfWeek;
        this.averageTraffic = averageTraffic;
    }

    public ArrayList<DayOfWeek> getDayOfWeek() {
        return dayOfWeek;
    }

    public double getAverageTraffic() {
        return averageTraffic;
    }
}