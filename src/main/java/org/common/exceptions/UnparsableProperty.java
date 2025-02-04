package org.common.exceptions;

public class UnparsableProperty extends RuntimeException{
    
    public UnparsableProperty(String property){
        super("Impossible de parser la propriété " + property);
    }    
}
