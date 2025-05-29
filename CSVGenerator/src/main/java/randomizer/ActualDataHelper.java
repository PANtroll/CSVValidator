package randomizer;

import java.util.HashSet;
import java.util.Set;

public class ActualDataHelper {

    private final Set<ActualData> existingValues = new HashSet<ActualData>();

    public boolean checkUniqueness(String masterKey, String dataReportType, String year, String month, String day){
        ActualData actualData = new ActualData(masterKey, dataReportType, year, month, day);
        if (existingValues.contains(actualData)){
            return false;
        }
        existingValues.add(actualData);
        return true;
    }

    private record ActualData(String masterKey, String dataReportType, String year, String month, String day) {

    }
}
