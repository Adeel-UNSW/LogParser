package logparser;

public class LogParserRunner {

	public static void main(String[] args) {
		LogParser logParser = new LogParser("data.log");
		logParser.parseLog();
	}

}
