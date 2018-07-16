package recommendations.utils;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.ByteOrderMark;
import org.apache.commons.io.input.BOMInputStream;
import org.apache.log4j.Logger;

import recommendations.pojo.NewUsers;
import recommendations.pojo.RatingsInput;

public class FileUtil {
    
	private final static Logger logger = Logger.getLogger(FileUtil.class);
	
	public List<RatingsInput> readFileRatingsInput(String filePath){
		InputStream inputStream = null;
		BOMInputStream bOMInputStream = null;
		InputStreamReader reader = null;
		CSVParser csvParser = null;
		List<RatingsInput> ratingInputData = null;
		
		try{
			
			String defaultEncoding = "UTF-8";
			inputStream = new FileInputStream(filePath);
			bOMInputStream = new BOMInputStream(inputStream);
		    ByteOrderMark bom = bOMInputStream.getBOM();
		    String charsetName = bom == null ? defaultEncoding : bom.getCharsetName();		
		    reader = new InputStreamReader(new BufferedInputStream(bOMInputStream), charsetName);
			//Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_NewUsers));
			csvParser = new CSVParser(reader, CSVFormat.DEFAULT
	                .withFirstRecordAsHeader()
	                .withIgnoreHeaderCase()
	                .withTrim());		
			
			ratingInputData = new ArrayList<RatingsInput>();
			
			for(CSVRecord csvRecord : csvParser){
				ratingInputData.add(new RatingsInput(Integer.parseInt(csvRecord.get("UserID").trim()),csvRecord.get("UserName"),Integer.parseInt(csvRecord.get("UserAge").trim()),csvRecord.get("MovieID").trim().equals("")?0:Integer.parseInt(csvRecord.get("MovieID").trim()),csvRecord.get("MovieName"),Integer.parseInt(csvRecord.get("Rating").trim())));
			}
		
		}catch(Exception e){
			logger.error("Error in reading the file!");
			e.printStackTrace();
		}finally{
			try {
				csvParser.close();
				reader.close();
				bOMInputStream.close();
				inputStream.close();
			} catch (Exception e) {
				logger.error("Error while flushing/closing reader/parser/bomInputStream/inputStream !");
				e.printStackTrace();
			}
			
		}
		return ratingInputData;
	}
	
	public void writeFile(String filePath,List<RatingsInput> userRatings){		
		BufferedWriter writer = null;
		CSVPrinter csvPrinter = null;		
		try{
			
			writer = Files.newBufferedWriter(Paths.get(filePath));
			csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
	                .withHeader("UserID","UserName", "UserAge", "MovieID", "MovieName", "Rating"));
			
			for(RatingsInput ratingInput: userRatings){
				csvPrinter.printRecord(ratingInput.getUserId(),ratingInput.getUserName(),ratingInput.getUserAge(),ratingInput.getMovieId(),ratingInput.getMovieName(),ratingInput.getRating());
			}		
						
		 }catch(Exception e){
			 logger.error("Error in writing the file");
			 e.printStackTrace();
		 }finally{			 
			 try {
				csvPrinter.flush();
				csvPrinter.close();			
				writer.close();
			} catch (IOException e) {
				logger.error("Error while flushing/closing writer/csvPrinter !");
				e.printStackTrace();
			}		
		 }		
	}
	
	public List<NewUsers> readFileNewUsers(String filePath){
		InputStream inputStream = null;
		BOMInputStream bOMInputStream = null;
		InputStreamReader reader = null;
		CSVParser csvParser = null;
		List<NewUsers> newUsers = null;
		
		try{
			
			String defaultEncoding = "UTF-8";
			inputStream = new FileInputStream(filePath);
			bOMInputStream = new BOMInputStream(inputStream);
		    ByteOrderMark bom = bOMInputStream.getBOM();
		    String charsetName = bom == null ? defaultEncoding : bom.getCharsetName();		
		    reader = new InputStreamReader(new BufferedInputStream(bOMInputStream), charsetName);			
			csvParser = new CSVParser(reader, CSVFormat.DEFAULT
	                .withFirstRecordAsHeader()
	                .withIgnoreHeaderCase()
	                .withTrim());		
			
			newUsers = new ArrayList<NewUsers>();
			
			for(CSVRecord csvRecord : csvParser){
				newUsers.add(new NewUsers(csvRecord.get("UserName"),Integer.parseInt(csvRecord.get("UserAge").trim()),Integer.parseInt(csvRecord.get("NoOfMoviesToRecommend").trim()),csvRecord.get("Movies")));
			}
		
		}catch(Exception e){
			logger.error("Error in reading the file!");
			e.printStackTrace();
		}finally{
			try {
				csvParser.close();
				reader.close();
				bOMInputStream.close();
				inputStream.close();
			} catch (Exception e) {
				logger.error("Error while flushing/closing reader/parser/bomInputStream/inputStream !");
				e.printStackTrace();
			}
			
		}
		return newUsers;
	}
	
	public void writeFileNewUsers(String filePath,List<NewUsers> newUsers){		
		BufferedWriter writer = null;
		CSVPrinter csvPrinter = null;		
		try{
			
			writer = Files.newBufferedWriter(Paths.get(filePath));
			csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
	                .withHeader("UserName","UserAge", "NoOfMoviesToRecommend", "Movies"));
			
			for(NewUsers newUser: newUsers){
				csvPrinter.printRecord(newUser.getUserName(),newUser.getUserAge(),newUser.getNoOfMoviesToRecommend(),newUser.getMovies());
			}		
						
		 }catch(Exception e){
			 logger.error("Error in writing the file");
			 e.printStackTrace();
		 }finally{			 
			 try {
				csvPrinter.flush();
				csvPrinter.close();			
				writer.close();
			} catch (IOException e) {
				logger.error("Error while flushing/closing writer/csvPrinter !");
				e.printStackTrace();
			}		
		 }		
	}
	
	
}
