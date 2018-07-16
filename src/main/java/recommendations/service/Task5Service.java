package recommendations.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;

import recommendations.pojo.NewUsers;
import recommendations.pojo.RatingsInput;
import recommendations.utils.ConstanUtil;
import recommendations.utils.FileUtil;

public class Task5Service {
   
	private final static Logger logger = Logger.getLogger(Task5Service.class);
	
	/*
	 * Task 3 - Reads the output file from task 2 
	 * 			and stores the data in the form of map of maps.  
	 */
	private HashMap<Integer, HashMap<Integer,ArrayList<Object>>> parseRatingsInput(String filePathRatingsInput) throws IOException{
		FileUtil fileObj = new FileUtil();
		List<RatingsInput> userRatings = fileObj.readFileRatingsInput(filePathRatingsInput);		
		Set<Integer> uniqueAges = new HashSet<Integer>();
		
		for(RatingsInput ratingInput: userRatings){
			uniqueAges.add(ratingInput.getUserAge());
		}
		
		HashMap<Integer, HashMap<Integer,ArrayList<Object>>> ratingsByAge = new HashMap<Integer,HashMap<Integer,ArrayList<Object>>>();
		for(Integer age: uniqueAges){
			HashMap<Integer,ArrayList<Object>> ratings = new HashMap<Integer,ArrayList<Object>>();
			for(RatingsInput ratingInput: userRatings){
				if(ratingInput.getUserAge() == age){
					ArrayList<Object> moviesRecmd = ratings.get(ratingInput.getRating());
					if(moviesRecmd == null){
						moviesRecmd = new ArrayList<Object>();
						moviesRecmd.add(ratingInput.getMovieName());
					}else moviesRecmd.add(ratingInput.getMovieName());
					ratings.put(ratingInput.getRating(), moviesRecmd);
				}
			}
			ratingsByAge.put(age, ratings);			
		}	    	
		logger.info("RatingsInput parsed data: ");
		logger.info(ratingsByAge);
		return ratingsByAge;
	}	
	
	private Integer calculateAge(Set<Integer> ageList, int age){
		return ageList.stream()
		            .min(Comparator.comparingInt(i -> Math.abs(i - age)))
		            .orElseThrow(() -> new NoSuchElementException("No age present"));		
	}
	
	/*
	 * Task 4 - Uses the map of maps from task 3 and generates 
	 *          recommendations on the basis of age, rating and number of movies requested.
	 */	
	private String filterByAgeAndRatings(HashMap<Integer, HashMap<Integer,ArrayList<Object>>> ratingsByAge, int age, int numOfMovies){
		Set<Integer> ageList = ratingsByAge.keySet();	
		   int finalAge = calculateAge(ageList,age);
		   logger.info("Nearest Age: "+ finalAge);
		   HashMap<Integer,ArrayList<Object>> ageWiseMovieRating = ratingsByAge.get(finalAge);
		   int rating = 5; 
		   StringBuilder userRecomList = new StringBuilder();
		   while(numOfMovies > 0){
			   ArrayList<Object> moviesList = ageWiseMovieRating.get(rating);
			   if(moviesList != null){
				   if(numOfMovies <= moviesList.size()){
					   if(userRecomList == null || userRecomList.toString().isEmpty() || userRecomList.toString().trim().substring(userRecomList.toString().length() - 2).trim().equalsIgnoreCase(",")){				   				   
					     userRecomList.append(moviesList.subList(0, numOfMovies).stream().map(Object::toString)
			                   .collect(Collectors.joining(", ")));
					   }else userRecomList.append(",").append(moviesList.subList(0, numOfMovies).stream().map(Object::toString)
					                   .collect(Collectors.joining(", ")));					   
					   break;
				   }else{
					   if(moviesList.size() == 1){
						   userRecomList.append(moviesList.stream().map(Object::toString)
				                   .collect(Collectors.joining(", "))).append(", ");   
					   }else userRecomList.append(moviesList.stream().map(Object::toString)
			                   .collect(Collectors.joining(", ")));				   
					   numOfMovies = numOfMovies - moviesList.size();
					   rating--;
				   }	
			   }else rating--;
		   } 
		   logger.info("Movies recommended: "+userRecomList.toString());
		   return userRecomList.toString();		
	}
	
	/*
	 * Task 5 - Reads the NewUsers.csv and passes the parameters to the
	 *          methods of task 3 and 4 there by generating the final output.
	 */	
	public void recommendMovies(String filePathNewUsers, String filePathRatingsInput) throws IOException{
		FileUtil fileObj = new FileUtil();
		List<NewUsers> newUsers = fileObj.readFileNewUsers(filePathNewUsers);
		
		HashMap<Integer, HashMap<Integer,ArrayList<Object>>> ratingsByAge = parseRatingsInput(filePathRatingsInput);
		
		for(NewUsers newUser: newUsers){
			String movies = filterByAgeAndRatings(ratingsByAge,newUser.getUserAge(),newUser.getNoOfMoviesToRecommend());
			newUser.setMovies(movies);
		}
		
		fileObj.writeFileNewUsers(new ConstanUtil().RESULT_CSV, newUsers);
		logger.info("Task 3,4 and 5 Completed!");
	}
	
}
