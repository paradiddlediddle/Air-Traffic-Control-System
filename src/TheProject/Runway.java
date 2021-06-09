package TheProject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;

public class Runway {
    private int runwayNumber;
    private int runwayTime;
    private int totalOperationTime;
    private boolean isEngaged;
    private int timeLeftUntilReady;
    private List <Request> queue = new ArrayList<>();
    int numberOfEmergencyLandings = 0;

    public Runway (int runwayTime) {
       runwayTime = this.runwayTime;
        isEngaged = false;
        timeLeftUntilReady = 0;
    }

    public int getRunwayTime() { return runwayTime; }

    public List<Request> getQueue() { return queue; }

    public void addRequestToList (Request request) { this.queue.add(request); }

    public boolean isEngaged() { return isEngaged; }

    public int getRunwayNumber() { return runwayNumber; }

    public void setRunwayNumber(int runwayNumber) { this.runwayNumber = runwayNumber; }

    public void setEngaged(boolean engaged) { isEngaged = engaged; }

    public int getTimeLeftUntilReady() { return timeLeftUntilReady; }

    public void setTimeLeftUntilReady(int timeLeftUntilReady) { this.timeLeftUntilReady = timeLeftUntilReady; }

    public int getNumberOfEmergencyLandings() { return numberOfEmergencyLandings; }

    public void setNumberOfEmergencyLandings(int numberOfEmergencyLandings) { this.numberOfEmergencyLandings = numberOfEmergencyLandings; }

    public int getTotalOperationTime() { return totalOperationTime; }

    public void setTotalOperationTime() {
        this.totalOperationTime = 0;
        for (Request request : queue) { this.totalOperationTime+=  request.getFlight().getTimeOnRunway(); }
    }

    // List sort based on priority
    public void prioritySort () {
        // isProcessed will always be "0" for all the requests, except for the top most element which is being processed
        // when we sort it based on zero, the value which contains 1 will be always at the top, while the rest contains "0"
        // By doing this, the priority sort will be done only on the requests which have zero value in it.
        queue.sort(Comparator.comparing(Request::getIsProcessed).reversed().thenComparing(Request::getPriority));
    }



}
