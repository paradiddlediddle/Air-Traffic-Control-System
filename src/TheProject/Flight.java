package TheProject;

public class Flight {

    private int maxWeight;
    private int maxTimeToHalt;
    private int weight;
    private int timeOnRunway;
    private String flightName;

    public Flight (String flightName, int weight, int maxWeight, int maxTimeToHalt) {
        this.flightName = flightName;
        this.weight = weight;
        this.maxWeight = maxWeight;
        this.maxTimeToHalt = maxTimeToHalt;
       int ratio = (weight / maxWeight);
       if (ratio >= 0.75) { this.timeOnRunway = this.maxTimeToHalt;  }
       else if (ratio >= 0.50 && ratio < 0.75) { this.timeOnRunway = (int) (this.maxTimeToHalt * 0.90 ); }
       else if (ratio > 0.50 ) { this.timeOnRunway =  (int) (this.maxTimeToHalt * 0.80); }

    }


    public int getMaxWeight() { return maxWeight; }

    public void setMaxWeight(int maxWeight) { this.maxWeight = maxWeight; }

    public int getMaxTimeToHalt() { return maxTimeToHalt; }

    public void setMaxTimeToHalt(int maxTimeToHalt) { this.maxTimeToHalt = maxTimeToHalt; }

    public int getWeight() { return weight; }

    public void setWeight(int weight) { this.weight = weight; }

    public int getTimeOnRunway() { return timeOnRunway; }

    public void setTimeOnRunway(int timeOnRunway) { this.timeOnRunway = timeOnRunway; }

    public String getFlightName() { return flightName; }

    public void setFlightName(String flightName) { this.flightName = flightName; }

}
