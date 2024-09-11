package com.engineeringdigest.journalApp.api_response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


//Remove unwanted fields
@Getter
@Setter
public class WheatherResponse{

    private Location location;
    private Current current;
    private Condition condition;


    @Getter
    @Setter
    public static class Condition{
        private String text;
        private String icon;
        private int code;
    }

    @Getter
    @Setter
    public class Current{

        @JsonProperty("temp_c")
        private double tempC;
        private Condition condition;

        private int humidity;
        private int cloud;

    }

    @Getter
    @Setter
    public  class Location{
        private String name;
        private String region;
        private String country;
        private double lat;
        private double lon;
        @JsonProperty("tz_id")
        private String tzID;
        @JsonProperty("localtime_epoch")
        private int localtimeEpoch;
        private String localtime;
    }

}




