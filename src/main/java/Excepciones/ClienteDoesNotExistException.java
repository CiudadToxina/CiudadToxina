/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Excepciones;

/**
 *
 * @author valef
 */
public class ClienteDoesNotExistException extends Exception{
    public ClienteDoesNotExistException(String mensaje){
        super(mensaje);
    }
}
