package com.arramton.closet.rider.utils;

public class ApiError {
    String action;
    String apiUrl;
    String parameters;
    String response;
    String mobile_no;
    String time;
    String screen;

    public ApiError(String action, String apiUrl, String parameters, String response,  String time, String screen) {
        this.action = action;
        this.apiUrl = apiUrl;
        this.parameters = parameters;
        this.response = response;
        this.time = time;
        this.screen = screen;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
