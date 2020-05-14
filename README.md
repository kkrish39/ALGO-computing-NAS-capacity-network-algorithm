# computing-NAS-capacity-network-algorithm
Finding the capacity of the National Airspace System of the United States using the Ford-Fulkerson algorithm with additional temporal aspect into consideration

# About the Code Repository:
- The project is developed as a Java Maven project in IntelliJ IDE.
- To execute, change relative path according to your machine in “NetworkFlowData.java”
file.
- The dataset is in resources folder of the project. I’ve also added a copy of a dataset
outside of the project folder reference.
- ComputeNASCapacity.java is the main file from where code needs to be executed.

# Preprocessing Steps:
- Read the list of all itineraries from the collected dataset and construct a list of Itinerary class(edge).
- Read the Airbus type to capacity data from the excel file.
- Construct an adjacency graph in such a way that each node is identified by a Label
(Name of the Airport) along with the possible incoming and outgoing flights with a specific capacity based on its airbus type which can be calculated from AirBusType to capacity mapping.
Input to the algorithm:
- List of all possible itineraries from the source along with the constructed adjacency graph, start time, source, destination, and the time interval.

# Pseudo Code:
augmentFlowFromSourceToDestination(List<Itinerary> itineraries, Map<String,Itineraries> graph, Integer augmentingFlow, LocalDateTime startTime, String source, String destination, LocalDateTime maxTimeBound)
//itineraries – list of edges from the source node
//graph – adjacencyGraph.
//augmentingFlow – Integer.MAX_VALUE
//startTime, source, destination, maxTimeBound – Based on the input.
1) Based on the start time and the given time Interval finds the maximum time-bound above which flights should not exceed while augmenting the flow. (In our case start time is midnight and Time Interval – 24)
  
2) Call the above function with the base parameters as stated above.

3) Initialize maxflow to be zero.

4) Start iterating the itineraries one by one.

5) Pick an itinerary and check the following constraints.
- The Departure time must be greater than the start time
- Arrival time at the destination airport must be less than the maximum time limit.
- The given itinerary’s source airport must not be visited already in a given iteration. (It is checked to avoid loops).
- The given itinerary/edge should have residual Capacity > 0 to get augmented. If all the constraints satisfy GOTO step 6. If not GOTO step 5.

6) Calculate the flow that can be augmented using this itinerary. It will be the minimum of
the current itinerary’s residual capacity and the flow to be augmented. (Flow to be augmented will be Integer.MAX_VALUE while we start augmenting the path and it will get changed based on the possible minimum value)

7) If the destination airport of the itinerary is the destination, then add augmented Flow to
maxflow and return maxFlow. Recursively update the residual capacity of the visited edges based on the augmented value. Move to step 4.

8) If the destination airport of the itinerary is not the destination, recursively call the above algorithm (GOTO step 2) with the following inputs
- list of possible itineraries from the destination airport of the current itinerary.
- start time as the arrival time of the current itinerary to the destination airport. (To make sure that we are choosing the flights after the current flight reaches the destination)
- max possible flow till now as the augmentingFlow. (Bottleneck flow till now)

# Time Complexity:
Let E be the number of itineraries and V be the vertices. Considering the given graph be a complete graph (Worst Case).

Since there will not be any iteration for the sink node, only V-1 vertices are taken under consideration.

For each edge (e) from source, there are (V-1) possible vertex flow using e edge
= Total number of Edges from Source * ((V-1) * (Total Number of Edges – Total number of Edges from Source)) = E *((V-1) *E)
= O (𝑽𝑬^𝟐)

For constructing the graph, we need to iterate over all the itineraries (Edges) – O(E).

Total Complexity = Graph Construction + Algorithm Execution. = O(E) + O (𝑉𝐸^2)

The Complexity of the Algorithm is - O (𝑽𝑬^𝟐) Output:

The maximum flow of passengers between LAX and JFK is 7660. (Without Rounding off the time to the nearest hour).

The maximum flow of passengers between LAX and JFK is 8014. (With Rounding off the time).
