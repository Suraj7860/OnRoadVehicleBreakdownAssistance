package com.cg.ora.exception;

public class MechanicNotFoundException  extends Exception{
private static final long serialVersionUID = 1L;
    
    public MechanicNotFoundException(String message) {
        super("Mechanic Doesnt exist at given location");
    }

}
