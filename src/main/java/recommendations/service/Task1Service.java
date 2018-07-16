package recommendations.service;

import java.util.List;

import org.apache.log4j.Logger;

import recommendations.pojo.RatingsInput;
import recommendations.utils.ConstanUtil;
import recommendations.utils.FileUtil;

public class Task1Service {
	
	private final static Logger logger = Logger.getLogger(Task1Service.class);
	/*
	 * Task 1 - Reads the RatingsInput.csv and separates 
	 *          MovieID and MovieName
	 */    
	public void seperateIDandName(String filePath){
		FileUtil fileObj = new FileUtil();
		List<RatingsInput> userRatings = fileObj.readFileRatingsInput(filePath);
		
		for(RatingsInput ratingInput: userRatings){
			String [] arrOfStr = ratingInput.getMovieName().split(",", 2);
			ratingInput.setMovieId(Integer.parseInt(arrOfStr[0].trim()));
			ratingInput.setMovieName(arrOfStr[1]);			
		}
		
		fileObj.writeFile(new ConstanUtil().TASK1_OUTPUT_CSV,userRatings);
		logger.info("Task 1 Completed!");
	}
}
