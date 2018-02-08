package ru.frank.exceptions;

/**
 * Created by sbt-filippov-vv on 08.02.2018.
 */
public class UserScoreListIsEmptyException extends RuntimeException{

    public UserScoreListIsEmptyException(String message){
        super(message);
    }

}
