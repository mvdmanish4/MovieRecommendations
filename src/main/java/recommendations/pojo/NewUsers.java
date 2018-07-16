package recommendations.pojo;

public class NewUsers {
	
	private String userName;
	private int userAge;
	private int noOfMoviesToRecommend;
	private String movies;	
	
	public NewUsers(String userName, int userAge, int noOfMoviesToRecommend, String movies) {
		super();
		this.userName = userName;
		this.userAge = userAge;
		this.noOfMoviesToRecommend = noOfMoviesToRecommend;
		this.movies = movies;
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
	public int getNoOfMoviesToRecommend() {
		return noOfMoviesToRecommend;
	}
	public void setNoOfMoviesToRecommend(int noOfMoviesToRecommend) {
		this.noOfMoviesToRecommend = noOfMoviesToRecommend;
	}
	public String getMovies() {
		return movies;
	}
	public void setMovies(String movies) {
		this.movies = movies;
	}
	
	@Override
	public String toString() {
		return "NewUsers [userName=" + userName + ", userAge=" + userAge + ", noOfMoviesToRecommend="
				+ noOfMoviesToRecommend + ", movies=" + movies + "]";
	}	
}
