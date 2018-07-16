package recommendations.pojo;

public class RatingsInput {
	
	private int userID;
	private String userName;
	private int userAge;
	private int movieId;
	private String movieName;
	private int rating;	
	 
	public RatingsInput(int userID,String userName, int userAge, int movieId, String movieName, int rating) {
		super();
		this.userID = userID;
		this.userName = userName;
		this.userAge = userAge;
		this.movieId = movieId;
		this.movieName = movieName;
		this.rating = rating;
	}
	
	public int getUserId() {
		return userID;
	}
	public void setUserId(int userID) {
		this.userID = userID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getUserAge() {
		return userAge;
	}
	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}
	public int getMovieId() {
		return movieId;
	}
	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}
	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	
	@Override
	public String toString() {
		return "UserInfo [userName=" + userName + ", userAge=" + userAge + ", movieId=" + movieId + ", movieName="
				+ movieName + ", rating=" + rating + "]";
	}	
}
