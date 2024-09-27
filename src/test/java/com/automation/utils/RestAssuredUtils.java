package com.automation.utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class RestAssuredUtils {

    static Response response;

    static RequestSpecification requestSpecification=RestAssured.given();

    static String endPoint;

    public static void setEndPoint(String endPoint){
        RestAssuredUtils.endPoint=endPoint;
    }

    public static Response post(){
        requestSpecification=requestSpecification.log().all();

        response = requestSpecification.post(endPoint);

        response.then().log().all();

        return response;
    }
    public static Response get(){
        requestSpecification=requestSpecification.log().all();

        response = requestSpecification.get(endPoint);

        response.then().log().all();

        return response;
    }
    public static void setHeader(String key,String value){
        requestSpecification=requestSpecification.header(key,value);
    }
    public static int statusCode(){
        return response.getStatusCode();
    }
    public static void setBody(String fileName){
        String jsonPath=ConfigReader.getConfig("json.folder.path");
        requestSpecification.body(getData(jsonPath+fileName));
    }

    public static String getData(String path){
        String context;
        try {
            context = new Scanner(new FileInputStream(path)).useDelimiter("\\Z").next();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return context;
    }
    public static void setBodyUsingPojo(Object object){
        requestSpecification=requestSpecification.body(object);
    }
    public static Response getResponse(){
        return response;
    }
}
