package Classes;

public class MovieDoc {

    private String id;//movie id
    private String Title;//movie title
    private String Rating;//movie rating
    private String Synopsis;//movie content
    private String Director;//movie director
    private String IMDb_URL;//movie url
    private String Genres;//movie genres
    private String Actors;//movie actors
    private String Poster;//movie poster
    private String Duration;//movie duration
    private String ReleaseDate;//movie releaseDare

    public MovieDoc() {
        super();
        // TODO Auto-generated constructor stub
    }

    public MovieDoc(String id, String title, String rating, String director,
                    String synopsis, String genres, String uRL, String actors,
                    String poster, String duration, String releaseDate) {
        super();
        this.id = id;
        Title = title;
        Rating = rating;
        Director = director;
        Synopsis = synopsis;
        Genres = genres;
        IMDb_URL = uRL;
        this.Actors = actors;
        this.Poster = poster;
        this.Duration = duration;
        this.ReleaseDate = releaseDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDuration() {
        return Duration;
    }

    public String getReleaseDate() {
        return ReleaseDate;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getRaing() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public String getDirector() {
        return Director;
    }

    public void setDirector(String director) {
        Director = director;
    }

    public String getSynopsis() {
        return Synopsis;
    }

    public void setSynopsis(String synopsis) {
        Synopsis = synopsis;
    }

    public String getGenres() {
        return Genres;
    }

    public void setGenres(String genres) {
        Genres = genres;
    }

    public String getURL() {
        return IMDb_URL;
    }

    public void setURL(String uRL) {
        IMDb_URL = uRL;
    }
    //改
    public String getActors() {
        return Actors;
    }

    public String getPoster() {
        return Poster;
    }

    @Override
    public String toString() {
        return "MovieDoc [id=" + id + ", Title=" + Title + ", Director=" + Director + ", Rating=" + Rating
                + ", Genres=" + Genres + ", URL=" + IMDb_URL + ",Actors" + Actors + ", Duration=" + Duration
                + ", ReleaseDate=" + ReleaseDate
                + "]";
    }

}
