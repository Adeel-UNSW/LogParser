import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.Test;

import logparser.LogParser;

public class LogParserTest {

	String logFilePath = "data.log";
	
    @Test
    public void testAnalyzeLog() {
        LogParser logParser = new LogParser(logFilePath);
        logParser.parseLog();
    }
    @Test
    public void testCountUniqueIPs() {
        LogParser logParser = new LogParser(logFilePath);
        int expectedUniqueIPCount = 11;
        int actualUniqueIPCount = logParser.countUniqueIPs();
        assertEquals(expectedUniqueIPCount, actualUniqueIPCount);
    }
    @Test
    public void testCountURLs() {
    	
        LogParser logParser = new LogParser(logFilePath);
        Map<String, Long> urlCounts = logParser.countURLs();
        assertEquals(2L, urlCounts.get("/docs/manage-websites/"));
        assertEquals(1L, urlCounts.get("/newsletter/"));
    }
    
    //Similar tests can be written for other methods
}
