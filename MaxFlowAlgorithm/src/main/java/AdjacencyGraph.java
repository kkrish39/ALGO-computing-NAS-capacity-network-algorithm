import java.io.IOException;
import java.util.*;

/*
    AdjacencyGraph is our network on which we need to apply the algorithm to find the flow
 */
public class AdjacencyGraph {
    //Key is the airport and value will be the list of incoming and outgoing flights.
    private Map<String, Itineraries> adjacencyGraph;

    public Map<String, Itineraries> getAdjacencyGraph(){
        return adjacencyGraph;
    }

    public void setAdjacencyGraph(Map<String, Itineraries> adjacencyGraph) {
        this.adjacencyGraph = adjacencyGraph;
    }

    AdjacencyGraph(){
        adjacencyGraph = new HashMap<>();
    }

    public void constructAdjacencyGraph(List<Itinerary> itineraries, Map<String, Integer> airBusTypeToCapacityMapping) throws IOException {
        for(Itinerary itinerary : itineraries){
            itinerary.setCapacity(airBusTypeToCapacityMapping.get(itinerary.getAirbusType()));
            itinerary.setResidualCapacity(airBusTypeToCapacityMapping.get(itinerary.getAirbusType()));
            if (adjacencyGraph.containsKey(itinerary.getFromAirport())) {
                adjacencyGraph.get(itinerary.getFromAirport()).getOutgoingFlights().add(itinerary);
                adjacencyGraph.get(itinerary.getFromAirport()).setOutgoingFlow( adjacencyGraph.get(itinerary.getFromAirport()).getOutgoingFlow() + itinerary.getCapacity());
            } else {
                Itineraries i = new Itineraries();
                i.getOutgoingFlights().add(itinerary);
                i.setOutgoingFlow(itinerary.getCapacity());
                adjacencyGraph.put(itinerary.getFromAirport(), i);
            }

            if(adjacencyGraph.containsKey(itinerary.getToAirport())){
                adjacencyGraph.get(itinerary.getToAirport()).getIncomingFlights().add(itinerary);
                adjacencyGraph.get(itinerary.getToAirport()).setIncomingFlow(adjacencyGraph.get(itinerary.getToAirport()).getIncomingFlow() +itinerary.getCapacity());
            }else{
                Itineraries i = new Itineraries();
                i.getIncomingFlights().add(itinerary);
                i.setIncomingFlow(itinerary.getCapacity());
                adjacencyGraph.put(itinerary.getToAirport(), i);
            }
        }
    }
}
