import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ComputeNASCapacity {

    //Airport Set to keep Track of VisitedAirports to avoid cycle
    private static Set<String> visitedNodes = new LinkedHashSet<>(Arrays.asList("LAX"));


    public static Integer augmentFlowFromSourceToDestination(List<Itinerary> itineraries, Map<String,Itineraries> graph, Integer augmentingFlow, LocalDateTime startTime, String source, String destination, LocalDateTime maxTimeBound){
        Integer minValue;
        Integer maxFlow = 0;

        for(Itinerary i : itineraries){
            //Resetting the visited node to LAX
            if(i.getFromAirport().equals(source))
                visitedNodes = new LinkedHashSet<>(Arrays.asList("LAX"));

            //Neglecting the Invalid Itineraries based on time and already visited label
            if (i.getDepartureTime().compareTo(startTime) >= 0 && i.getArrivalTime().compareTo(maxTimeBound) < 0 && i.getResidualCapacity() > 0 && !visitedNodes.contains(i.getToAirport())) {
                visitedNodes.add(i.getFromAirport());
                //Finding the minimum value to be augmented
                minValue = Math.min(augmentingFlow, i.getResidualCapacity());

                //Check if we found the destination node
                if (i.getToAirport().equals(destination)){
                    //updating the residual capacity
                    i.setResidualCapacity(i.getResidualCapacity() - minValue);
                    maxFlow = maxFlow + minValue;
                    //No core Logic Block. Only to print the augmented path
                    visitedNodes.add(destination);
                    System.out.println("Path "+visitedNodes+"   :::   Augmented Value - "+maxFlow);
                    visitedNodes.remove(destination);

                    //Avoid return and code termination if we are starting from source and finding destination in the same itinerary
                    if (i.getFromAirport().equals(source) && i.getToAirport().equals(destination))
                        continue;
                    return maxFlow;
                }

                //Recursive call to find the destination node
                Integer secondaryAugmentedValue = augmentFlowFromSourceToDestination(graph.get(i.getToAirport()).getOutgoingFlights(), graph, minValue, i.getDepartureTime(), source, destination, maxTimeBound);
                maxFlow = maxFlow + secondaryAugmentedValue;

                //No core Logic Block. Only to print the augmented path
                if(secondaryAugmentedValue != 0) {
                    visitedNodes.add(destination);
                    System.out.println("Path " + visitedNodes+ "   :::   Augmented Value - "+maxFlow);
                    visitedNodes.remove(destination);
                }
                //Updating the residual capacity
                i.setResidualCapacity(i.getResidualCapacity() - secondaryAugmentedValue);
            }
        }
        return maxFlow;
    }

    public static void main(String[] args) throws IOException {
        String fromAirport = "LAX"; //Source Airport
        String destinationAirport = "JFK"; //Destination Airport

        int timeInterval = 24; //Required Time Interval
        String startTimeStr = "2020-01-06 00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startTime = LocalDateTime.parse(startTimeStr, formatter); //Start Time from which we need to calculate the flow

        //Calculating the Upper Time Bound based on startTime and the timeInterval
        LocalDateTime maxTimeLimit = startTime.plusHours(timeInterval);

        //Initial Flow is assumed to be Zero
        int maxFlow = 0;

        AdjacencyGraph a = new AdjacencyGraph();

        /*
            NetworkFlowData is the class where we will reading the data from the excel and constructing the list of possible edges(Itineraries)
         */
        NetworkFlowData networkFlowData = new NetworkFlowData();

        /*
            Construct the graph with the list of edges/Itineraries in hand.
         */
        a.constructAdjacencyGraph(networkFlowData.getListOfItineraries(), networkFlowData.getAirBusTypeToCapacityMapping());

        List<Itinerary> fromItinerary = a.getAdjacencyGraph().get(fromAirport).getOutgoingFlights();
        //If no flights from the source
        //or source and destination are the same airport
        if(fromItinerary.size() == 0 || fromAirport.equals(destinationAirport)){
            System.out.println("The maximum flow of passengers between "+fromAirport+" and "+destinationAirport+" is "+maxFlow);
            return;
        }
        //If there is at least one itinerary from the source and source and destination airports are different
        //find the maximum flow of passengers in a given time
        maxFlow = augmentFlowFromSourceToDestination(fromItinerary, a.getAdjacencyGraph(), Integer.MAX_VALUE, startTime, fromAirport, destinationAirport, maxTimeLimit);

        System.out.println("\nThe maximum flow of passengers between "+fromAirport+" and "+destinationAirport+" is "+maxFlow);
    }
}
