package com.example.music_catalogue.model;

public class Genre {
    private String code;
    private String label;

    public Genre() {
    }

    public Genre(String code, String label) {
        this.code  = code;
        this.label = label;
    }

    public String getCode()              { return code; }
    public void   setCode(String code)   { this.code = code; }

    public String getLabel()               { return label; }
    public void   setLabel(String label)   { this.label = label; }

    @Override
    public String toString() {
        return code + " — " + label;
    }
}
