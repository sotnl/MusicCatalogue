package com.example.music_catalogue.model;

public class Song {
    private int id;
    private String title;
    private String artist;
    private String album;
    private String genre;
    private int releaseYear;
    private int rating;       // 1–5 stars
    private String notes;

    public Song() {
    }

    public Song(int id, String title, String artist, String album,
                String genre, int releaseYear, int rating, String notes) {
        this.id          = id;
        this.title       = title;
        this.artist      = artist;
        this.album       = album;
        this.genre       = genre;
        this.releaseYear = releaseYear;
        this.rating      = rating;
        this.notes       = notes;
    }

    public int    getId()          { return id; }
    public void   setId(int id)    { this.id = id; }

    public String getTitle()                { return title; }
    public void   setTitle(String title)    { this.title = title; }

    public String getArtist()               { return artist; }
    public void   setArtist(String artist)  { this.artist = artist; }

    public String getAlbum()                { return album; }
    public void   setAlbum(String album)    { this.album = album; }

    public String getGenre()                { return genre; }
    public void   setGenre(String genre)    { this.genre = genre; }

    public int    getReleaseYear()                   { return releaseYear; }
    public void   setReleaseYear(int releaseYear)    { this.releaseYear = releaseYear; }

    public int    getRating()               { return rating; }
    public void   setRating(int rating)     { this.rating = rating; }

    public String getNotes()                { return notes; }
    public void   setNotes(String notes)    { this.notes = notes; }
}
