package recommendations;

import java.io.IOException;

import org.apache.log4j.Logger;

import recommendations.service.Task1Service;
import recommendations.service.Task2Service;
import recommendations.service.Task5Service;
import recommendations.utils.ConstanUtil;

public class App {
	
	private final static Logger logger = Logger.getLogger(App.class);
	
	public static void main(String args[]) throws IOException{
		
		ConstanUtil constants = new ConstanUtil();
		
		//Task 1
		Task1Service task1 = new Task1Service();
		task1.seperateIDandName(constants.RATINGS_INPUT_CSV);
		
		//Task 2
		Task2Service task2 = new Task2Service();
		task2.stringCapitalization(constants.TASK1_OUTPUT_CSV);
		
		//Task 3,4 and 5
		Task5Service task5 = new Task5Service();
		task5.recommendMovies(constants.NEW_USERS_CSV, constants.TASK2_OUTPUT_CSV);
		
		logger.info("Recommendations generated!");
		
	}
}
