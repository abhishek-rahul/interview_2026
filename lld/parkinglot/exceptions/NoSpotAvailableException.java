package lld.parkinglot.exceptions;

public class NoSpotAvailableException extends RuntimeException {
    public NoSpotAvailableException(String message) { super(message); }
}