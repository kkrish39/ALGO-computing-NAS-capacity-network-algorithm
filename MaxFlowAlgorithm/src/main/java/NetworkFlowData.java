import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.ZoneId;
import java.util.*;

/*
    NetworkFlowData class is to translate the given excel data set to code runnable data format
 */
public class NetworkFlowData {
    private List<Itinerary> listOfItineraries = new ArrayList<>();
    private Map<String,Integer> airBusTypeToCapacityMapping = new HashMap<>();
    private String relativePath;
    public List<Itinerary> getListOfItineraries() {
        return listOfItineraries;
    }

    public void setListOfItineraries(List<Itinerary> listOfItineraries) {
        this.listOfItineraries = listOfItineraries;
    }

    public Map<String, Integer> getAirBusTypeToCapacityMapping() {
        return airBusTypeToCapacityMapping;
    }

    public void setAirBusTypeToCapacityMapping(Map<String, Integer> airBusTypeToCapacityMapping) {
        this.airBusTypeToCapacityMapping = airBusTypeToCapacityMapping;
    }

    //Initialize the class members with appropriate data, reading it from excel.
    NetworkFlowData() throws IOException {
        /*
        Relative path according to the machine from where we run.
         */
        relativePath="/Users/keerthivasankrishnamurthy/IdeaProjects/";
        initItineraryList();
        initAirBusCapacity();
    }

    private void initItineraryList() throws IOException {
        //Read the Itinerary data set from given file location
        FileInputStream itineraryFile = new FileInputStream(new File(relativePath+"MaxFlowAlgorithm/src/main/resources/Itinerary.xlsx"));

        XSSFWorkbook workbook = new XSSFWorkbook(itineraryFile);
        XSSFSheet worksheet = workbook.getSheetAt(0);

        Iterator<Row> rowIterator = worksheet.iterator();

        while(rowIterator.hasNext()){
            Row row = rowIterator.next();

            Itinerary itinerary = new Itinerary();
            itinerary.setFromAirport(row.getCell(0).getStringCellValue());
            itinerary.setToAirport(row.getCell(1).getStringCellValue());
            itinerary.setDepartureTime(row.getCell(2).getDateCellValue().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime());
            itinerary.setArrivalTime(row.getCell(3).getDateCellValue().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime());
            itinerary.setAirbusType(row.getCell(4).getStringCellValue().trim());
            itinerary.setAirlines(row.getCell(5).getStringCellValue());
            itinerary.setVisited(false);

            listOfItineraries.add(itinerary);
        }
    }

    private void initAirBusCapacity() throws IOException {
        //Read the Capacity data set from given file location
        FileInputStream capacityFile = new FileInputStream(new File(relativePath+"MaxFlowAlgorithm/src/main/resources/Capacity.xlsx"));
        XSSFWorkbook capacityWorkbook = new XSSFWorkbook(capacityFile);
        XSSFSheet capacityWorkSheet = capacityWorkbook.getSheetAt(0);

        Iterator<Row> capacityRowIterator = capacityWorkSheet.iterator();

        while(capacityRowIterator.hasNext()){
            Row row = capacityRowIterator.next();
            airBusTypeToCapacityMapping.put(row.getCell(0).getStringCellValue().trim(), (int) row.getCell(1).getNumericCellValue());
        }
    }
}
