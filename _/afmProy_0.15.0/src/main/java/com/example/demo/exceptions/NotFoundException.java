package com.example.demo.exceptions;

public class NotFoundException extends Exception{
    public NotFoundException (){
        super("Elemento no encontrado");
    }
    public NotFoundException (String msg){
        super (msg);
    }
}
