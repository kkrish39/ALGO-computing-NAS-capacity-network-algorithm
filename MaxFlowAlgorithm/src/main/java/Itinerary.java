import java.time.LocalDateTime;

/*
    Itinerary class to store the property of any itinerary instance
 */
public class Itinerary {
    private String fromAirport;
    private String toAirport;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String airbusType;
    private String airlines;
    private Integer capacity;
    private Integer residualCapacity;

    private boolean isVisited;
    public Integer getResidualCapacity() {
        return residualCapacity;
    }

    public void setResidualCapacity(Integer residualCapacity) {
        this.residualCapacity = residualCapacity;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public String getToAirport() {
        return toAirport;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public void setToAirport(String toAirport) {
        this.toAirport = toAirport;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getAirbusType() {
        return airbusType;
    }

    public void setAirbusType(String airbusType) {
        this.airbusType = airbusType;
    }

    public String getAirlines() {
        return airlines;
    }

    public void setAirlines(String airlines) {
        this.airlines = airlines;
    }

    public String getFromAirport() {
        return fromAirport;
    }

    public void setFromAirport(String fromAirport) {
        this.fromAirport = fromAirport;
    }
}
