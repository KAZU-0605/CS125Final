package com.example.cs125final;

public class Countryelements {
    private String acountryName;
    private int acountryImage;
    public Countryelements(String countryName, int countryImage) {
        acountryName = countryName;
        acountryImage = countryImage;
    }
    public String getCountryName() {
        return acountryName;
    }
    public int getCountryImage() {
        return acountryImage;
    }
}
