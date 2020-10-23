package com.hro.exercise.nbachallenge.util;

/**
 * Values that are reused several times, as constant
 */
public abstract class AppConstants {

    public static final String DAY_TIME_FORMAT = "yyyy-MM-dd";
    public static final String DAY_AND_HOUR_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String API_KEY = "c29e9e9b2fmshe04ede3c49d8164p1215edjsnf93801d1751d";
    public static final String API_URL = "https://rapidapi.p.rapidapi.com/";
    public static final String API_HOST = "free-nba.p.rapidapi.com";
    public static final String API_SEARCH_BY_DATE = "games?page='%(pageNumber)'&per_page=100&dates[]=";
    public static final String API_SEARCH_BY_ID = "stats?page=0&per_page=100&game_ids[]=";

}
