package logparser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LogParser {

	private String logFilePath; // Path to log file
	private List<String> lines; // List of log file lines
	 
	//Constructor
	public LogParser(String logFilePath) {
		
		this.logFilePath = logFilePath;
        try {
            this.lines = Files.readAllLines(Paths.get(logFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
	 }
	
	//Getter and setters
    public String getLogFilePath() {
		return logFilePath;
	}

	public void setLogFilePath(String logFilePath) {
		this.logFilePath = logFilePath;
	}

	public List<String> getLines() {
		return lines;
	}

	public void setLines(List<String> lines) {
		this.lines = lines;
	}

	//Method to parse log file
	public void parseLog() {
		
	    if (lines == null) {
	        System.err.println("Error reading log file.");
	        return;
	    }

        // Count unique IP addresses
        int uniqueIPCount = countUniqueIPs();
        System.out.println("The number of unique IP addresses: " + uniqueIPCount);

        // Count occurrences of each URL
        Map<String, Long> urlCounts = countURLs();

        // Find top 3 URLs
        List<Map.Entry<String, Long>> topUrls = findTopUrls(urlCounts);

        System.out.println("The top 3 most visited URLs:");
        printTopEntries(topUrls);

        // Count occurrences of each IP address
        Map<String, Long> ipCounts = countIPs();

        // Find top 3 IPs
        List<Map.Entry<String, Long>> topIPs = findTopIPs(ipCounts);

        System.out.println("\nThe top 3 most active IP addresses:");
        printTopEntries(topIPs);
    }
	
	//Method to count unique IP addresses
    public int countUniqueIPs() {
        Set<String> uniqueIPs = new HashSet<>();
        for (String line : lines) {
            String[] parts = line.split(" ");
            uniqueIPs.add(parts[0]);
        }
        return uniqueIPs.size();
    }
	
    //Method to count occurrences of each URL
    public Map<String, Long> countURLs() {
        Map<String, Long> urlCounts = new HashMap<>();
        for (String line : lines) {
            String[] parts = line.split(" ");
            String url = parts[6];
            urlCounts.put(url, urlCounts.getOrDefault(url, 0L) + 1);
        }
        return urlCounts;
    }
    //Method to find top 3 URLs
    private List<Map.Entry<String, Long>> findTopUrls(Map<String, Long> urlCounts) {
        List<Map.Entry<String, Long>> topUrls = new ArrayList<>(urlCounts.entrySet());
        topUrls.sort((a, b) -> b.getValue().compareTo(a.getValue()));
        return topUrls.subList(0, Math.min(3, topUrls.size()));
    }

    //Method to count occurrences of each IP address
    private Map<String, Long> countIPs() {
        Map<String, Long> ipCounts = new HashMap<>();
        for (String line : lines) {
            String[] parts = line.split(" ");
            String ip = parts[0];
            ipCounts.put(ip, ipCounts.getOrDefault(ip, 0L) + 1);
        }
        return ipCounts;
    }

    //Method to find top 3 IPs
    private List<Map.Entry<String, Long>> findTopIPs(Map<String, Long> ipCounts) {
        List<Map.Entry<String, Long>> topIPs = new ArrayList<>(ipCounts.entrySet());
        topIPs.sort((a, b) -> b.getValue().compareTo(a.getValue()));
        return topIPs.subList(0, Math.min(3, topIPs.size()));
    }
    
    //Method to print top entries
    private void printTopEntries(List<Map.Entry<String, Long>> topEntries) {
        for (int i = 0; i < topEntries.size(); i++) {
            Map.Entry<String, Long> entry = topEntries.get(i);
            System.out.println((i + 1) + ": " + entry.getKey() + " - Visited " + entry.getValue() + " times");
        }
    }
}