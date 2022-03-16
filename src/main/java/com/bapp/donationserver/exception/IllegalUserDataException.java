package com.bapp.donationserver.exception;

public class IllegalUserDataException extends IllegalArgumentException{
    public IllegalUserDataException(String s) {
        super(s);
    }
}
