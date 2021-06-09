package TheProject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Controller {

    // Runway
    Runway[] runways;

    public void runwayCreator() {
        int[] runwayTimes = {40, 60, 80, 90};
        for (int i = 0; i < runwayTimes.length; i++) {
            runways[i] = new Runway(runwayTimes[i]);
            runways[i].setRunwayNumber(i);
        }
    }
    // Implementing Threads
    // Need to implement thee processing of request using threads
    // Once the thread starts to process, it needs to be set to "engaged"
    // after the thread runs over, the request should be removed from the list.

    //1.General Request / Call the general request for take off, landing and emergency landing
    // pass the user selection as an argument.
    public void generalRequests (int userSelection) {

        Flight flight = createFlight();
        Request request = new Request(flight, userSelection);
        Runway runway = findSuitableRunway(request);
        displayMessage(runway, request);

    }


//
//    // 1. TAKE OFF
//    public void takeOff() {
//        // 1. create the flight
//        Flight flight = createFlight();
//        // 2. create a request
//        Request request = new Request(flight, Request.RequestType.TAKE_OFF);
//        //Only add it to the list if the time or the value is valid
//        findSuitableRunway(request);
//    }
//
//    // 2. LANDING
//
//
//    // 3. EMERGENCY LANDING
//    public void emergencyLanding() {
//
//        Flight flight = createFlight();
//
//        Request request = new Request(flight, Request.RequestType.EMERGENCY_LANDING);
//
//        Runway runway = findSuitableRunway(request);
//
//        displayMessage(runway, request);
//
//
//    }
//

    // select a list of suitable runways which can accommodate the landing
    // see which runway is the longest if 2 runways are long it needs to sort it based on number of emergency landings
    // when it is an emergency landing the total operation time doesn't matter, it will anyway override the priority

    // 4. RUNWAY DETAILS


    // Create a flight
    private Flight createFlight() {
        System.out.println("xxx Enter flight details xxx");
        Scanner flightScan = new Scanner(System.in);
        String flightName = null;
        int maxWeight = 0;
        int timeToHalt = 0;
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("Select Flight type:\n1.ATR\n2.AirBus\n3.Boeing\n4.Cargo\nEnter you selection: ");
            int flightType = flightScan.nextInt();
            switch (flightType) {
                case 1:
                    maxWeight = 12;
                    timeToHalt = 30;
                    flightName = "ATR";
                    isRunning = false;
                    break;
                case 2:
                    maxWeight = 20;
                    timeToHalt = 40;
                    flightName = "AirBus";
                    isRunning = false;
                    break;
                case 3:
                    maxWeight = 40;
                    timeToHalt = 50;
                    flightName = "Boeing";
                    isRunning = false;
                    break;
                case 4:
                    maxWeight = 100;
                    timeToHalt = 60;
                    flightName = "Cargo";
                    isRunning = false;
                    break;
                default:
                    System.out.println("Enter Valid Selection: ");
            }
        }
        int flightWeight = 101;
        while (flightWeight > maxWeight) {
            System.out.println("Enter the weight of the flight (Flight's Max Capacity: " + maxWeight + "kgs");
            flightWeight = flightScan.nextInt();
        }
        Flight flight = new Flight(flightName, flightWeight, maxWeight, timeToHalt);
        return flight;
    }

    private Runway findSuitableRunway(Request request) {
        Runway selectedRunway = null; // if runway selection remains null, all runways are full
        int timeToHalt = request.getFlight().getTimeOnRunway();
        List<Runway> runwayWithSurplusTime = new ArrayList<>();
        List<Runway> runwayWithoutSurplusTime = new ArrayList<>();
        boolean runwayWithSurplusTimeFound = false;

        // criterion to evaluate the best runway
        // 1. shortest runway which has 10+ sec surplus
        // 2. runway with lesser requests / lesser total waiting time

        // Two for loops, 1. To find a run way which has surplus time
        // The runway will get add to the surplus or without surplus time list only if the runways' queue size is less than 4
        for (int i = 0; i < runways.length; i++) {
            if (request.getFlight().getTimeOnRunway() + 10 < runways[i].getRunwayTime() && runways[i].getQueue().size() <= 4) {
                runwayWithSurplusTime.add(runways[i]);
                runwayWithSurplusTimeFound = true;
            }
        }
        // 2. To find a runway which at least can accommodate the request
        for (int i = 0; i < runways.length && !runwayWithSurplusTimeFound; i++) {
            if (request.getFlight().getTimeOnRunway() < runways[i].getRunwayTime() && runways[i].getQueue().size() <= 4) {
                runwayWithoutSurplusTime.add(runways[i]);
            }
        }

        // Once the the runway with and without the surplus time is segregated, we check if there
        // are any runways present in the surplus list, if present sort them in less operation time order
        // select the first element after the sort
        // if no surplus is found, check the other list and sort it by operation time and select the first element.


        // Need to see if they are engaged in this statement, if engaged, then choose another runway
        if (runwayWithSurplusTimeFound) {
            //sort it based on the lowest operation time
            runwayWithSurplusTime.sort(Comparator.comparing(Runway::getTotalOperationTime));
            // Post the sort, the list is sorted in the order of least waiting time to the highest waiting time.
            // We know that the first element in the List is the best selection so we select it before checking if it engaged
            selectedRunway = runwayWithSurplusTime.get(0);
            // In this waiting time prioritised order, we check if a runway is not not engaged and select it
            // Even though a runway with a lesser waiting time is selected we need to check if it is available, otherwise
            // check the next best in the order, if that is available select it, else continue.
            for (Runway runway : runwayWithSurplusTime) {
                if (!runway.isEngaged()) {
                    selectedRunway = runway;
                }
            }
            // if all the runways are full, the statement above wouldn't have selected any runway, in that case
            // we just stick with our decision of choosing the first element.


        } else { // There is a chance to get out of bounds exception since the
            // The same process is followed on the runway without surplus time list, if the surplus list is empty.
            runwayWithoutSurplusTime.sort(Comparator.comparing(Runway::getTotalOperationTime));
            selectedRunway = runwayWithoutSurplusTime.get(0);
            for (Runway runway : runwayWithoutSurplusTime) {
                if (!runway.isEngaged()) {
                    selectedRunway = runway;
                }
            }
        }

        return selectedRunway;
    }

    private void displayMessage(Runway selectedRunway, Request request) {

        if (selectedRunway == null) {
            // All runways are occupied at the moment, please try again after 45 seconds
        } else {
            selectedRunway.addRequestToList(request);
            selectedRunway.prioritySort();
            int waitTime = 0;
            //Loop to calculate the wait time
            for (int i = 0; selectedRunway.getQueue().get(i) != request; i++) {
                waitTime += (selectedRunway.getQueue().get(i).getFlight().getTimeOnRunway() + 15);
            }
            // If the added request is the first in the list, then 15 seconds of wait time is set instead of 0.
            // Since every flight would take at least 15 seconds of flight time.
            if (waitTime == 0) { waitTime = 15; }

            // Message:
                System.out.println("The "+ request.getFlight().getFlightName() +" carrying "+ request.getFlight().getWeight()
                + " will "+ request.getRequestType() + " on Runway- "+ selectedRunway.getRunwayNumber() +" in "+ waitTime +" seconds." );

        }


    }

}