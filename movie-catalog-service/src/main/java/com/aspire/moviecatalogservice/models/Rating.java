package com.aspire.moviecatalogservice.models;

public class Rating {
    private String movieId;
    private int rating;

    public Rating() {
    }

    public Rating(String movieId, int raiting) {
        this.movieId = movieId;
        this.rating = raiting;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
