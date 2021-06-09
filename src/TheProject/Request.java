package TheProject;

import java.util.ArrayList;
import java.util.List;

public class Request {


   private int isProcessed = 0; // Just 1 and 0 needs to be set as values, so that priority sort always keeps it at the top
   private Runway selectedRunway;
   private Flight flight;
   private String requestType;
   private int priority;
   private int requestWaitTime; // Wait time at the time of adding to the request

    public Request (Flight flight, int userRequestSelection) {

        switch (userRequestSelection) {
            case 1: requestType = "take off"; priority = 3;break;
            case 2: requestType = "land"; priority = 2; break;
            case 3: requestType = "immediately land"; priority =1; break;
        }
    }

    public Flight getFlight() { return flight; }

    public void setFlight(Flight flight) { this.flight = flight; }

    public int getPriority() { return priority; }

    public void setPriority(int priority) { this.priority = priority; }

    public int getRequestWaitTime() { return requestWaitTime; }

    public void setRequestWaitTime(int requestWaitTime) { this.requestWaitTime = requestWaitTime; }

    public Runway getSelectedRunway() { return selectedRunway; }

    public void setSelectedRunway(Runway selectedRunway) { this.selectedRunway = selectedRunway; }

    public int getIsProcessed() { return isProcessed; }

    public void setIsProcessed(int isProcessed) { this.isProcessed = isProcessed; }

    public String getRequestType() { return requestType; }
}
