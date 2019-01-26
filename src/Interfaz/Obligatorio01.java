/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import java.util.*;
//Ultima version 5/22/17 7:24. 

import Dominio.*;
import static Dominio.Partida.contarPuntaje;
import static Dominio.Partida.jugarComputadora;
import static Dominio.Partida.validacionCoordenada;
import static Dominio.Sistema.cargarSistema;
import static Dominio.Sistema.guardarSistema;
import java.io.IOException;



/**
 *
 * @author Aguia
 */
public class Obligatorio01 {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args){
        //Menu de opciones
        Scanner in = new Scanner(System.in);
        Sistema sistema;
        sistema = cargarSistema();
        int seleccion = 0;
        ArrayList<Jugador> jugadoresOrdenados = sistema.getJugadoresOrdenados();
        while (seleccion != 5) {

            System.out.println("------------------ESQUINAS POR HONOR-----------------------");
            System.out.println("1.-Registrar Jugador");
            System.out.println("2.-Raking");
            System.out.println("3.-Jugar partida 1 pa 1");
            System.out.println("4.-Jugar partida vs Computadora");
            System.out.println("5.-Salir");
            System.out.println("---------------------------------------------");

            
            System.out.println("---------------------------------------------");
            seleccion = pedirDato("No puede ingresar letras");
            switch (seleccion) {
                //Llamada a metodo para crear jugador, se agrega a la lista
                case 1:
                    System.out.println("-------------------------------------");

                    Jugador jugador = crearJugador(sistema.getJugadores());
                    sistema.agregarJugador(jugador);
                    

                    break;
                case 2:
                    //Se muestran los jugadores por ranking
                    mostrarListaJugadores(sistema.getJugadoresOrdenados());
                    break;
                case 3:
                    //Llamada de metodo para jugar una partida 1 vs 1
                    jugarPartida(jugadoresOrdenados);
                    break;
                case 4:
                    //Llamada de metodo para jugar una partida vs computadora
                    jugarVsComputadora(jugadoresOrdenados);
                    break;
                case 5:
                    guardarSistema(sistema);
                    break;
            }

        }
    }
    //Metodo para crear un jugador
    public static Jugador crearJugador(ArrayList<Jugador> jugadores) {
        Scanner in = new Scanner(System.in);
        System.out.println("Ingrese los datos del jugador: ");
        Jugador jugador = new Jugador();
        System.out.println("-Por favor ingrese su nombre: ");
        jugador.setNombre(in.nextLine());
        System.out.println("-Ingrese su edad: ");
        

        int edad = pedirDato("No puede ingresar letras");
        //Si ingresa una edad incorrecta, se le pedira de nuevo
        while (edad < 0) {
            System.out.println("Por favor ingrese una edad correcta");
            edad = pedirDato("No puede ingresar letras");
        }
        jugador.setEdad(edad);
        
        System.out.println("-Por favor ingrese su alias: ");
        String alias = in.nextLine();
        boolean existe = false;
        //Si el alias ya existe, ingresa uno nuevo
        while (!existe) {
            existe = true;
            for(int i = 0; i < jugadores.size(); i++) {
                if (alias.equals(jugadores.get(i).getAlias())) {
                    existe = false;
                    System.out.println("Ingrese el alias de nuevo, se recuerda que debe de ser unico");
                    alias = in.nextLine();
                }
            }
        }
        jugador.setAlias(alias);
        return jugador;
    }
    //Metodo para mostrar lista de jugadores
    private static void mostrarListaJugadores(ArrayList<Jugador> unaLista) {

        for (int i = 0; i < unaLista.size(); i++) {
            Jugador jugador = unaLista.get(i);
            System.out.println("Jugador " + (i + 1));
            System.out.println(jugador.getNombre() + "(" + jugador.getAlias() + ")" + " Partidas ganadas: "+jugador.getGanadas());
        }
    }
    //Metodo para imprimir tablero
    private static void imprimirTablero(Celda[][] mat) {

        System.out.print("  ");
        for (int i = 0; i < mat.length; i++) {

            System.out.print("  " + (i + 1) + " ");
        }
        System.out.println(" ");

        for (int i = 0; i < mat.length; i++) {
            System.out.println("   *** *** *** *** *** *** ");

            for (int j = 0; j < 3; j++) {
                if (j == 1) {
                    System.out.print((char) ('A' + i));
                    for (int k = 0; k < mat[0].length; k++) {
                        if (!mat[i][k].estoyEnLvlCero()) {
                            System.out.print(" * " + mat[i][k]);
                        } else {
                            System.out.print(" *  ");
                        }
                    }
                    System.out.println(" * ");
                } else {
                    System.out.println("  *   *   *   *   *   *   *");
                }
            }
        }
        System.out.println("   *** *** *** *** *** *** ");
    }
    //Metodo de jugar partida
    private static void jugarPartida(ArrayList<Jugador> unaLista) {
        Scanner in = new Scanner(System.in);
        if (unaLista.size() > 1) {
            Partida partida = new Partida();
            System.out.println("Seleccione a su jugador ");
            partida.setJugador1(seleccionarJugador(unaLista));

            System.out.println("Seleccione a su jugador ");
            partida.setJugador2(seleccionarJugador(unaLista));
            
            String coordenada;
            int surrender=0;
            //Mientras los jugadores tienen cubos
            while (partida.getJugador1().getCubos() > 0 && partida.getJugador2().getCubos() > 0) {
                
                
                if (!partida.getTurno()) {
                    //Jugada del rojo
                    //Se setea a true para el turno del azul
                    partida.setTurno(true);
                    
                    imprimirTablero(partida.getTablero());
                    System.out.println("Ingrese coordenada");
                    coordenada=in.nextLine().toLowerCase();
                    if(coordenada.equals("x"))
                    {
                        //Si se rinde, sale y su cantidad de cubos se setea a 0
                        surrender=1;
                        partida.getJugador1().setCubos(0);
                    }
                    else
                    {
                        boolean esValida = validacionCoordenada(partida.getTablero(), partida, coordenada, partida.getJugador1());
                        while(!esValida)
                        {
                        
                            System.out.println("Ingrese de nuevo su jugada");
                            coordenada=in.nextLine().toLowerCase();
                            esValida = validacionCoordenada(partida.getTablero(), partida, coordenada, partida.getJugador1()); 
                        
                        }
                        if(partida.getJugador1().getCubos()>=0){
                            System.out.println(partida.getJugador1().getNombre() + "(" + partida.getJugador1().getAlias() + ")"
                                    +" realizo jugada en: "+coordenada.toUpperCase()+" y le quedan "
                                +partida.getJugador1().getCubos()+" cubos");
                        }
                    }
                }   
                else 
                {
                    //Jugada del azul,
                    //Se setea en false para la proxima jugada del rojo
                    partida.setTurno(false);
                    imprimirTablero(partida.getTablero());
                    System.out.println("Ingrese coordenada");
                    coordenada=in.nextLine().toLowerCase();
                    if(coordenada.equals("x"))
                    {
                        surrender=2;
                        partida.getJugador2().setCubos(0);
                    }
                    else
                    {

                        boolean esValida = validacionCoordenada(partida.getTablero(), partida, coordenada, partida.getJugador2());
                        while(!esValida){

                            System.out.println("Ingrese de nuevo su jugada");
                            coordenada=in.nextLine().toLowerCase();
                            esValida = validacionCoordenada(partida.getTablero(), partida, coordenada, partida.getJugador2());

                        }
                        if(partida.getJugador2().getCubos()>=0)
                        {
                            System.out.println(partida.getJugador2().getNombre() + "(" + partida.getJugador2().getAlias() + ")"
                                    +" realizo jugada en: "+coordenada.toUpperCase()+" y le quedan "
                                    +partida.getJugador2().getCubos()+" cubos");
                        }

                    }
                }
                
            }
            //Partida finalizada
            imprimirTablero(partida.getTablero());
            if ((partida.getJugador1().getCubos() <= 0 || partida.getJugador2().getCubos() <= 0)){
                
                //Se cuentan los puntos del jugador rojo
                partida.setTurno(false);
                int puntajeRojo = contarPuntaje(partida.getTablero(),partida.getTurno());
                puntajeRojo=puntajeRojo-partida.getJugador1().getCubos();
                //Se cuentan los puntos del jugador azul
                partida.setTurno(true);
                int puntajeAzul = contarPuntaje(partida.getTablero(),partida.getTurno());
                puntajeAzul=puntajeAzul-partida.getJugador2().getCubos();
                
                //Si el surrender es igual a 1, quiere decir que el jugador rojo se rindio
                if (surrender==1)
                {
                    System.out.println("El jugador 1 se ha rendido");
                    partida.getJugador1().setPerdidas(partida.getJugador1().getPerdidas()+1);
                    partida.getJugador2().setGanadas(partida.getJugador2().getGanadas()+1);
                    //Se setea el enPartida en false para poder escogerlos otra vez en otra partida
                    partida.getJugador1().setEnPartida(false);
                    partida.getJugador2().setEnPartida(false);
                    partida.getJugador1().setCubos(25);
                    partida.getJugador2().setCubos(25);
                }
                //Si el surrender es igual a 2, quiere decir que el jugador azul se rindio
                if (surrender==2)
                {
                    System.out.println("El jugador 2 se ha rendido");
                    partida.getJugador2().setPerdidas(partida.getJugador2().getPerdidas()+1);
                    partida.getJugador1().setGanadas(partida.getJugador1().getGanadas()+1);
                    //Se setea el enPartida en false para poder escogerlos otra vez en otra partida
                    partida.getJugador1().setEnPartida(false);
                    partida.getJugador2().setEnPartida(false);
                    partida.getJugador1().setCubos(25);
                    partida.getJugador2().setCubos(25);
                }
                //Si el surrender es igual a 0, ninguno se rindio
                if(surrender==0)
                {
                    //Si el puntaje del rojo es mayor que el del azul
                    if(puntajeRojo > puntajeAzul)
                    {
                        System.out.println("El jugador Rojo ha ganado con "+ puntajeRojo);
                        partida.getJugador2().setPerdidas(partida.getJugador2().getPerdidas()+1);
                        partida.getJugador1().setGanadas(partida.getJugador1().getGanadas()+1);
                        partida.getJugador1().setEnPartida(false);
                        partida.getJugador2().setEnPartida(false);
                        partida.getJugador1().setCubos(25);
                        partida.getJugador2().setCubos(25);
                    }
                    //Si el puntaje del rojo es menor al del azul
                    if(puntajeRojo < puntajeAzul)
                    {   
                        System.out.println("El jugador Azul ha ganado con "+ puntajeAzul);
                        partida.getJugador1().setPerdidas(partida.getJugador1().getPerdidas()+1);
                        partida.getJugador2().setGanadas(partida.getJugador2().getGanadas()+1);
                        partida.getJugador1().setEnPartida(false);
                        partida.getJugador2().setEnPartida(false);
                        partida.getJugador1().setCubos(25);
                        partida.getJugador2().setCubos(25);
                    }
                    //Si los puntajes son iguales
                    if(puntajeRojo==puntajeAzul)
                    {
                        System.out.println("Empatada");
                        partida.getJugador1().setEmpatadas(partida.getJugador1().getEmpatadas()+1);
                        partida.getJugador2().setEmpatadas(partida.getJugador1().getEmpatadas()+1);
                        partida.getJugador1().setEnPartida(false);
                        partida.getJugador2().setEnPartida(false);
                        partida.getJugador1().setCubos(25);
                        partida.getJugador2().setCubos(25);
                    }
                }
            }
        } 
        //Si faltan jugadores no se juega la partida
        else 
        {
            System.out.println("No puede ingresar, faltan jugadores");
        }

    }
    //Metodo para jugar vs computadora
    private static void jugarVsComputadora(ArrayList<Jugador> unaLista){
        Scanner in = new Scanner(System.in);
        if (unaLista.size() > 0) {
            Partida partida = new Partida();
            Jugador computadora = new Jugador();
            System.out.println("Seleccione a su jugador ");
            partida.setJugador1(seleccionarJugador(unaLista));
            partida.setJugador2(computadora);
            partida.getJugador2().setNombre("Computadora");
            String coordenada;
            int surrender=0;
            //Mientras los jugadores tienen cubos
            while (partida.getJugador1().getCubos() > 0 && partida.getJugador2().getCubos() > 0) {
                
                
                if (!partida.getTurno()) {
                    //Jugada del rojo
                    //Se setea a true para el turno del azul
                    partida.setTurno(true);
                    
                    imprimirTablero(partida.getTablero());
                    System.out.println("Ingrese coordenada");
                    coordenada=in.nextLine().toLowerCase();
                    if(coordenada.equals("x"))
                    {
                        //Si se rinde, sale y su cantidad de cubos se setea a 0
                        surrender=1;
                        partida.getJugador1().setCubos(0);
                    }
                    else
                    {
                        boolean esValida = validacionCoordenada(partida.getTablero(), partida, coordenada, partida.getJugador1());
                        while(!esValida)
                        {
                        
                            System.out.println("Ingrese de nuevo su jugada");
                            coordenada=in.nextLine().toLowerCase();
                            esValida = validacionCoordenada(partida.getTablero(), partida, coordenada, partida.getJugador1()); 
                        
                        }
                        if(partida.getJugador1().getCubos()>=0){
                            System.out.println(partida.getJugador1().getNombre() + "(" + partida.getJugador1().getAlias() + ")"
                                    +" realizo jugada en: "+coordenada.toUpperCase()+" y le quedan "
                                    +partida.getJugador1().getCubos()+" cubos");
                        }
                    }
                }   
                else 
                {
                    //Jugada de la computadora,
                    //Se setea en false para la proxima jugada del rojo
                    partida.setTurno(false);
                    imprimirTablero(partida.getTablero());
                    String imp =jugarComputadora(partida.getTablero(),partida.getJugador2(),partida );
                    if(partida.getJugador2().getCubos()>=0){
                        System.out.println("La "+partida.getJugador2().getNombre()+" realizo jugada en: "+imp.toUpperCase()+" y le quedan "
                            +partida.getJugador2().getCubos()+" cubos");
                    }
                }
                
            }
            //Partida finalizada
            imprimirTablero(partida.getTablero());
            if ((partida.getJugador1().getCubos() <= 0 || partida.getJugador2().getCubos() <= 0)){
                if(partida.getJugador1().getCubos()<0){
                    
                    partida.getJugador1().setCubos(0);
                }
                if(partida.getJugador2().getCubos()<0){
                    
                    partida.getJugador2().setCubos(0);
                }
                //Se cuentan los puntajes del jugador rojo
                partida.setTurno(false);
                int puntajeRojo = contarPuntaje(partida.getTablero(),partida.getTurno());
                puntajeRojo=puntajeRojo-partida.getJugador1().getCubos();
                //Se cuentan los puntajes de la computadora
                partida.setTurno(true);
                int puntajeAzul = contarPuntaje(partida.getTablero(),partida.getTurno());
                puntajeAzul=puntajeAzul-partida.getJugador2().getCubos();
                //Si el surrender es igual a 1, el jugador 1 se rindio
                if (surrender==1)
                {
                    System.out.println("El jugador 1 se ha rendido");
                    partida.getJugador1().setPerdidas(partida.getJugador1().getPerdidas()+1);
                    partida.getJugador2().setGanadas(partida.getJugador1().getGanadas()+1);
                    partida.getJugador1().setEnPartida(false);
                    partida.getJugador2().setEnPartida(false);
                    partida.getJugador1().setCubos(25);
                    partida.getJugador2().setCubos(25);
                }
                //Si el surrender es igual a 0, el jugador 1 no se rindio
                if(surrender==0)
                {
                    //Si el puntaje del rojo es mayor al del azul
                    if(puntajeRojo > puntajeAzul)
                    {
                        System.out.println("El jugador Rojo ha ganado con "+ puntajeRojo);
                        
                        partida.getJugador1().setGanadas(partida.getJugador1().getGanadas()+1);
                        partida.getJugador1().setEnPartida(false);
                        partida.getJugador2().setEnPartida(false);
                        partida.getJugador1().setCubos(25);
                        partida.getJugador2().setCubos(25);
                    }
                    //Si es menor al del azul
                    if(puntajeRojo < puntajeAzul)
                    {   
                        System.out.println("La computadora ha ganado con "+ puntajeAzul);
                        partida.getJugador1().setPerdidas(partida.getJugador1().getPerdidas()+1);
                        
                        partida.getJugador1().setEnPartida(false);
                        partida.getJugador2().setEnPartida(false);
                        partida.getJugador1().setCubos(25);
                        partida.getJugador2().setCubos(25);
                    }
                    //Si son iguales
                    if(puntajeRojo==puntajeAzul)
                    {
                        System.out.println("Empatada");
                        
                        partida.getJugador1().setEnPartida(false);
                        partida.getJugador2().setEnPartida(false);
                        partida.getJugador1().setCubos(25);
                        partida.getJugador2().setCubos(25);
                    }
                }
            }
        } 
        else 
        {
            System.out.println("No puede ingresar, faltan jugadores");
        }

    
    }
    //Metodo para seleccionar un jugador
    private static Jugador seleccionarJugador(ArrayList<Jugador> unaLista) {
        Scanner in = new Scanner(System.in);

        mostrarListaJugadores(unaLista);
        Jugador jugadorS = new Jugador();
        int contador = 0;
        while (contador < 1) {

            System.out.println("Ingrese opcion");
            int seleccion = pedirDato("No puede ingresar letras");
            while (seleccion < 0 || seleccion > unaLista.size()) {
                System.out.println("Ingreso una opcion inexistente");
                seleccion = pedirDato("No puede ingresar letra, por favor ingrese otra vez su seleccion");
            }
            jugadorS = unaLista.get(seleccion - 1);
            if (!jugadorS.getEnPartida()) {
                System.out.println("todo bien");
                jugadorS.setEnPartida(true);
                contador++;
            } else {
                System.out.println("jugador ya seleccionado");
            }
            
        }
        return jugadorS;
    }
    //Metodo de pedir dato
    public static int pedirDato(String unMensaje) {
        Scanner in = new Scanner(System.in);
        int num = 0;
        boolean validacion = false;
        while (!validacion) {
            try {
                num = in.nextInt();
                in.nextLine();
                validacion = true;
            } catch (Exception e) {
                System.out.println(unMensaje);
                in.nextLine();
            }
        }
        return num;
    }

    
    
}
