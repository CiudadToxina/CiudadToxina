/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Excepciones;

/**
 *
 * @author sanch
 */
public class PinDoesNotMatchException extends Exception{
    public PinDoesNotMatchException(String mensaje){
        super(mensaje);
    }
}