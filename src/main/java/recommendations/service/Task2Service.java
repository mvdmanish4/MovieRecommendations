package recommendations.service;

import java.util.List;

import org.apache.log4j.Logger;

import recommendations.pojo.RatingsInput;
import recommendations.utils.ConstanUtil;
import recommendations.utils.FileUtil;

public class Task2Service {
    
	private final static Logger logger = Logger.getLogger(Task2Service.class);
	/*
	 * Task 2 - Uses the output from task 1 and capitalizes the 
	 * 			first character of every word in the string
	 */
	public void stringCapitalization(String filePath){
		FileUtil fileObj = new FileUtil();
		List<RatingsInput> userRatings = fileObj.readFileRatingsInput(filePath);
		
		for(RatingsInput ratingInput: userRatings){
			String [] arrOfStr = ratingInput.getMovieName().split(" ");
         	StringBuilder sb  = new StringBuilder();
         	for(String str: arrOfStr){
         		sb.append(Character.toUpperCase(str.charAt(0))).append(str.substring(1)).append(" ");
         	}						
         	ratingInput.setMovieName(sb.toString());
		}
		
		fileObj.writeFile(new ConstanUtil().TASK2_OUTPUT_CSV,userRatings);	
		logger.info("Task 2 Completed!");
	}
}
