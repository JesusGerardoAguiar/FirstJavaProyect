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
public class Jugador implements Comparable<Jugador>, Serializable{
    //Variables del jugador
    private String nombre;
    private String alias;
    private int edad;
    private int ganadas;
    private int perdidas;
    private int empatadas;
    private int cubos;
    private boolean enPartida;
    //Constructor sin parametros del jugador
    public Jugador()
    {
        this.nombre="";
        this.alias="";
        this.edad=0;
        this.ganadas=0;
        this.empatadas=0;
        this.perdidas=0;
        this.cubos=25;
        this.enPartida=false;
    }
    //Metodos get
    public String getNombre(){
        return this.nombre;
    }
    public String getAlias(){
        return this.alias;
    }
    public int getEdad(){
        return this.edad;
    }
    public int getGanadas(){
        return this.ganadas;
    }
    public int getPerdidas(){
        return this.perdidas;
    }
    public int getEmpatadas(){
        return this.empatadas;
    }
    public int getCubos(){
        return this.cubos;
    }
    public boolean getEnPartida(){
        return this.enPartida;
    }
    //Metodos set
    public void setNombre(String unNombre){
        this.nombre=unNombre;
    }
    public void setAlias(String unAlias){
        this.alias=unAlias;
    }
    public void setEdad(int unaEdad){
        this.edad=unaEdad;
    }
    public void setGanadas(int ganada){
    
        this.ganadas=ganada;
    }
    public void setPerdidas(int perdida){
    
        this.perdidas=perdida;
    }
    public void setEmpatadas(int empate){
    
        this.empatadas=empate;
    }
    public void setCubos(int fichas){
        this.cubos=fichas;
    }
    public void setEnPartida(boolean unaPartida)
    {
        this.enPartida=unaPartida;
    }
    @Override
    //Metodo toString()
    public String toString(){
        return "-Nombre: "+this.getNombre()+"\n-Alias: "+this.getAlias()+
                "\n-Edad: "+this.getEdad()+"\n-Partidas ganadas: "+this.getGanadas()
                +"\n-Partidas perdidas: "+this.getPerdidas()
                +"\n-Partidas empatadas: "+this.getEmpatadas();
    }
    //Comparacion de jugadores por partidas ganadas
    @Override
    public int compareTo(Jugador o) 
    {
        
        int resultado = o.getGanadas() - this.getGanadas(); 
        if(resultado == 0)//Las partidas ganadas son iguales, compara por nombre
        {
            resultado = this.getNombre().compareTo (o.getNombre());
        }   
        return resultado;
    }

    
}
