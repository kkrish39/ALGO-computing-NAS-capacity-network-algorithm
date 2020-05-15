import java.util.ArrayList;
import java.util.List;

/*
    Itinerary class is to encapsulate the list of possible incoming and outgoing flights along with its cumulative in and out flow
 */
public class Itineraries {
    private Integer incomingFlow;
    private Integer outgoingFlow;
    private List<Itinerary> incomingFlights;
    private List<Itinerary> outgoingFlights;

    Itineraries(){
        incomingFlights = new ArrayList<>();
        outgoingFlights = new ArrayList<>();
        incomingFlow = 0;
        outgoingFlow = 0;
    }
    public List<Itinerary> getIncomingFlights() {
        return incomingFlights;
    }

    public void setIncomingFlights(List<Itinerary> incomingFlights) {
        this.incomingFlights = incomingFlights;
    }

    public List<Itinerary> getOutgoingFlights() {
        return outgoingFlights;
    }

    public void setOutgoingFlights(List<Itinerary> outgoingFlights) {
        this.outgoingFlights = outgoingFlights;
    }

    public Integer getIncomingFlow() {
        return incomingFlow;
    }

    public void setIncomingFlow(Integer incomingFlow) {
        this.incomingFlow = incomingFlow;
    }

    public Integer getOutgoingFlow() {
        return outgoingFlow;
    }

    public void setOutgoingFlow(Integer outgoingFlow) {
        this.outgoingFlow = outgoingFlow;
    }

}
