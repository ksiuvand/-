package com.example.filmapi.models;

import java.util.ArrayList;

public class Film {

    public int filmId;
    public String nameRu;
    public String nameEn;
    public String year;
    public String filmLength;
    public ArrayList<Country> countries;
    public ArrayList<Genre> genres;
    public String rating;
    public int ratingVoteCount;
    public String posterUrl;
    public String posterUrlPreview;
    public Object ratingChange;

    public Film(int filmId, String nameRu, String nameEn, String year, String filmLength, ArrayList<Country> countries, ArrayList<Genre> genres, String rating, int ratingVoteCount, String posterUrl, String posterUrlPreview, Object ratingChange) {
        this.filmId = filmId;
        this.nameRu = nameRu;
        this.nameEn = nameEn;
        this.year = year;
        this.filmLength = filmLength;
        this.countries = countries;
        this.genres = genres;
        this.rating = rating;
        this.ratingVoteCount = ratingVoteCount;
        this.posterUrl = posterUrl;
        this.posterUrlPreview = posterUrlPreview;
        this.ratingChange = ratingChange;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public String getNameRu() {
        return nameRu;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getFilmLength() {
        return filmLength;
    }

    public void setFilmLength(String filmLength) {
        this.filmLength = filmLength;
    }

    public ArrayList<Country> getCountries() {
        return countries;
    }

    public void setCountries(ArrayList<Country> countries) {
        this.countries = countries;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Genre> genres) {
        this.genres = genres;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getRatingVoteCount() {
        return ratingVoteCount;
    }

    public void setRatingVoteCount(int ratingVoteCount) {
        this.ratingVoteCount = ratingVoteCount;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getPosterUrlPreview() {
        return posterUrlPreview;
    }

    public void setPosterUrlPreview(String posterUrlPreview) {
        this.posterUrlPreview = posterUrlPreview;
    }

    public Object getRatingChange() {
        return ratingChange;
    }

    public void setRatingChange(Object ratingChange) {
        this.ratingChange = ratingChange;
    }
}
