/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dominio;

import java.io.Serializable;

/**
 *
 * @author Aguia
 */
public class Celda implements Serializable{
    //Variables de clase Celda
    private int altura;
    private boolean color2;
    private String color;
    //Constructor sin paramatros
    public Celda(){
        this.altura=0;
        this.color2=false;
        this.color="";
    }
    //Metodos get
    public int getAltura(){
        return this.altura;
    }
    public boolean getColor(){
        return this.color2;
    }
    //Metodos set
    public void setAltura(int unaAltura){
        this.altura=unaAltura;
    }
    public void setColor(boolean unColor){
        this.color2=unColor;
    }
    //Metodo para inizializar la matriz en 0
    public boolean estoyEnLvlCero(){
    
        return altura==0;
    }
    //Metodo para obtener el color y el valor de la ficha
    public String obtenerFicha(){
        return color+altura;
    }
    
    public void setColor(String colorFicha){
        color = colorFicha;
    }
    //Metodo toString() de la ficha
    @Override
    public String toString(){
        
    
        return obtenerFicha()+ "" +"\u001B[30m";
        
    }
}
    


