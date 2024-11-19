package com.example.internationalisation.model;

public class Country {

    private String commonName;
    private String officialName;
    private String capitalCity;

    private String currencyTrigram;
    private String currencyName;
    private String currencySymbol;

    private double latitude;
    private double longitude;

    private String flagURL;

    public Country(String commonName, String officialName, String capitalCity,
                   String currencyTrigram, String currencyName, String currencySymbol,
                   double latitude, double longitude, String flagURL) {
        this.commonName = commonName;
        this.officialName = officialName;
        this.capitalCity = capitalCity;
        this.currencyTrigram = currencyTrigram;
        this.currencyName = currencyName;
        this.currencySymbol = currencySymbol;
        this.latitude = latitude;
        this.longitude = longitude;
        this.flagURL = flagURL;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getOfficialName() {
        return officialName;
    }

    public void setOfficialName(String officialName) {
        this.officialName = officialName;
    }

    public String getCapitalCity() {
        return capitalCity;
    }

    public void setCapitalCity(String capitalCity) {
        this.capitalCity = capitalCity;
    }

    public String getCurrencyTrigram() {
        return currencyTrigram;
    }

    public void setCurrencyTrigram(String currencyTrigram) {
        this.currencyTrigram = currencyTrigram;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getFlagURL() {
        return flagURL;
    }

    public void setFlagURL(String flagURL) {
        this.flagURL = flagURL;
    }
}
