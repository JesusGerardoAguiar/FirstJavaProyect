/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dominio;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
    import java.util.ArrayList;
    import java.util.Collections;
/**
 *
 * @author Aguia
 */
public class Sistema implements Serializable{
    //Variable de Sistema, lista de jugadores
    private ArrayList<Jugador> jugadores;
    //Constructor de sistema
    public Sistema()
    {
        jugadores= new ArrayList<Jugador> ();
    }
    //Metodo get
    public ArrayList<Jugador> getJugadores()
    {
        return this.jugadores;
    }
    //Metodo para agregar un Jugador a una lista
    public void agregarJugador(Jugador unJugador)
    {
        jugadores.add(unJugador);
    }
    //Metodo para obtener los jugadores ordenados por partidas ganadas
   
    public ArrayList<Jugador> getJugadoresOrdenados()
    {
        ArrayList<Jugador> jugadoresOrdenados = this.jugadores;
        Collections.sort(jugadoresOrdenados);
        return jugadoresOrdenados;
    }
    public static Sistema cargarSistema(){
        //abrir
        Sistema sis = null;
        try{
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("datos"));
            sis = (Sistema)in.readObject();
            in.close();
        }catch(IOException | ClassNotFoundException ex){
            sis = new Sistema();
        }
        return sis;
    }
    public static void guardarSistema(Sistema sis){
        try{
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("datos"));
            out.writeObject(sis);
            out.close();
          
        }catch(Exception ex){
            
        }
    }
}

