/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dominio;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Aguia
 */
public class Partida implements Serializable{
    //Variables de Clase Partida
    private Celda[][] tablero;
    private Jugador jugador1;
    private Jugador jugador2;
    private boolean turno;
    //Constructor sin parametros de Partida
    public Partida() {
        this.tablero = new Celda[6][6];
        this.jugador1 = null;
        this.jugador2 = null;
        
        this.turno = false;
        inicializarMat();
        
    }
    //Metodos get
    public Jugador getJugador1() {
        return this.jugador1;
    }

    public Jugador getJugador2() {
        return this.jugador2;
    }
    public Celda[][] getTablero() {
        return this.tablero;
    }

    public boolean getTurno() {
        return this.turno;
    }
    //Metodos set
    public void setTurno(boolean unTurno) {
        this.turno = unTurno;
    }

    public void setJugador1(Jugador unJugador1) {
        this.jugador1 = unJugador1;
    }

    public void setJugador2(Jugador unJugador2) {
        this.jugador2 = unJugador2;
    }
    //Metodo de inicializacion de la matriz
    public void inicializarMat() {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length; j++) {

                tablero[i][j] = new Celda();
            }
        }
    }
    /*Metodo de validacion de es una posicion valida, no hay fichas en ese lugar,
    es adyacente y no forma cuadrado*/
    public static boolean validacionGeneral(Celda mat[][], int i, int j) {
        boolean validacionG = true;
        if (esPosicionValida(mat, i, j) && esColocable(mat, i, j) && esAdyacente(mat, i, j) && noHayCuadrado(mat, i, j)) {
            validacionG = true;
        } else {
            validacionG = false;
        }/*Aca se chequea que en la primera jugada permita jugar, ya que si todas 
        las posiciones son iguales a 0, puede realizar la jugada*/
        int contador = 0;
        for (int k = 0; k < mat.length; k++) {
            for (int l = 0; l < mat.length; l++) {
                if (mat[k][l].getAltura() == 0) {
                    contador++;
                }
            }
        }
        if (contador == 36) {
            validacionG = true;
        }
        return validacionG;
    }
    //Metodo para controlar que no haya una ficha ya colocada en esa posicion
    public static boolean esColocable(Celda mat[][], int i, int j) {
        boolean valida = true;

        if (valida) {
            if (mat[i][j].getAltura() > 0) {
                valida = false;
            }
        }
        return valida;

    }
    //Metodo para controlar que las posiciones adyacentes esten dentro de la matriz
    public static boolean esPosicionValida(Celda mat[][], int i, int j) {
        boolean valida = true;
        if (i < 0 || i >= mat.length || j < 0 || j >= mat[0].length) {
            valida = false;
        }
        return valida;
    }
    //Metodo para controlar que no forme un cuadrado
    public static boolean noHayCuadrado(Celda mat[][], int i, int j) {
        boolean valida1 = true;
        //Buscar cuadrado en esquina superior izquierda
        if (esPosicionValida(mat, i, j - 1) && mat[i][j - 1].getAltura() != 0 && esPosicionValida(mat, i - 1, j) && mat[i - 1][j].getAltura() != 0
                && esPosicionValida(mat, i - 1, j - 1) && mat[i - 1][j - 1].getAltura() != 0) {
            valida1 = false;
        }
        //Buscar cuadrado en esquina superior derecha
        if (esPosicionValida(mat, i - 1, j) && mat[i - 1][j].getAltura() != 0 && esPosicionValida(mat, i - 1, j + 1) && mat[i - 1][j + 1].getAltura() != 0
                && esPosicionValida(mat, i, j + 1) && mat[i][j + 1].getAltura() != 0) {
            valida1 = false;
        }
        //Buscar cuadrado en esquina inferior izquierda
        if (esPosicionValida(mat, i, j - 1) && mat[i][j - 1].getAltura() != 0 && esPosicionValida(mat, i + 1, j - 1) && mat[i + 1][j - 1].getAltura() != 0
                && esPosicionValida(mat, i + 1, j) && mat[i + 1][j].getAltura() != 0) {
            valida1 = false;
        }
        //Buscar cuadrado en esquina inferior derecha
        if (esPosicionValida(mat, i + 1, j) && mat[i + 1][j].getAltura() != 0 && esPosicionValida(mat, i + 1, j + 1) && mat[i + 1][j + 1].getAltura() != 0
                && esPosicionValida(mat, i, j + 1) && mat[i][j + 1].getAltura() != 0) {
            valida1 = false;
        }
        return valida1;
    }
    //Metodo para controlar que sea adyacente
    public static boolean esAdyacente(Celda mat[][], int i, int j) {
        boolean valida2 = true;
        int contador = 0;

        for (int f = i - 1; f <= i + 1 && valida2; f++) {
            for (int c = j - 1; c <= j + 1 && valida2; c++) {
                if (!esPosicionValida(mat, f, c)) {
                    contador++;
                }
                if (esPosicionValida(mat, f, c) && mat[f][c].getAltura() == 0 && (f != i || c != j)) {
                    contador++;
                }
                if (contador == 8) {
                    valida2 = false;
                }
            }
        }
        return valida2;
    }
    /*Metodo para controlar que la coordenada que ingreso sea valida*/
    public static boolean validacionCoordenada(Celda mat[][], Partida p, String verificacion, Jugador j) {
        boolean esCoordenadaValida = false;
        boolean largo = true;

        while (!esCoordenadaValida && largo) {

            Pattern pat = Pattern.compile("[aA-fF][1-6]");
            Matcher verificacion2 = pat.matcher(verificacion);
            if (String.valueOf(verificacion).length() == 2) {
                if (verificacion2.matches()) {
                    esCoordenadaValida = true;
                    char prueba1 = verificacion.charAt(0);
                    int ascii = (int) prueba1;
                    ascii = ascii - 97;
                    int num = Integer.parseInt(String.valueOf(verificacion.charAt(1)));
                    num--;
                    boolean validacionG = validacionGeneral(mat, ascii, num);
                    if (validacionG) {
                        //si validacionGeneral es igual a true, procede a colocar una ficha
                        mat[ascii][num].setAltura(mat[ascii][num].getAltura() + 1);
                        //Se le resta a ese jugador un cubo
                        j.setCubos(j.getCubos() - 1);
                        //Aca llamamos a esEsquina
                        esEsquina(mat, ascii, num, p, j);
                    } else {
                        largo = false;
                    }
                    //Si el getTurno es true, es la jugada del rojo
                    if (p.getTurno() && validacionG) {
                        mat[ascii][num].setColor("\u001B[31m");
                        /*se setea la variable color en false ya que si 
                        jugo el jugador rojo su setColor es false*/
                        mat[ascii][num].setColor(false);

                    }
                    //Si el getTurno es false, es la jugada del azul
                    if (!p.getTurno() && validacionG) {
                        mat[ascii][num].setColor("\u001B[34m");
                        /*se setea la variable color en true ya que si 
                        jugo el jugador azul su setColor es true*/
                        mat[ascii][num].setColor(true);
                    }
                } else {
                    largo = false;
                }
            } else {
                largo = false;
            }
        }
        return largo;
    }
    //Metodo para controlar las esquinas
    public static void esEsquina(Celda mat[][], int i, int j, Partida p, Jugador m) {

        metodoEsquina1(mat, i, j, p, m);
        metodoEsquina2(mat, i, j, p, m);

        metodoEsquina3(mat, i, j, p, m);
        metodoEsquina4(mat, i, j, p, m);
        metodoEsquina5(mat, i, j, p, m);
    }
    //Metodo de esquina1
    public static void metodoEsquina1(Celda mat[][], int i, int j, Partida p, Jugador m) {
        for (int fila = i + 1; esPosicionValida(mat, fila, j) && mat[fila][j].getAltura() != 0; fila++) {
            if ((esPosicionValida(mat, fila, j - 1) && mat[fila][j - 1].getAltura() != 0) || (esPosicionValida(mat, fila, j + 1) && mat[fila][j + 1].getAltura() != 0)) {
                if(m.getCubos()>0){
                    if (mat[fila][j].getAltura() < 5) {

                        mat[fila][j].setAltura(mat[fila][j].getAltura() + 1);

                        if (!p.getTurno()) {
                            mat[fila][j].setColor("\u001B[34m");
                            mat[fila][j].setColor(true);
                        }
                        if (p.getTurno()) {
                            mat[fila][j].setColor("\u001B[31m");
                            mat[fila][j].setColor(false);
                        }

                        m.setCubos(m.getCubos() - 1);
                    }
                }
            }
        }
    }
    //Metodo de esquina2
    public static void metodoEsquina2(Celda mat[][], int i, int j, Partida p, Jugador m) {
        for (int fila = i - 1; esPosicionValida(mat, fila, j) && mat[fila][j].getAltura() != 0; fila--) {
            if ((esPosicionValida(mat, fila, j - 1) && mat[fila][j - 1].getAltura() != 0) || (esPosicionValida(mat, fila, j + 1) && mat[fila][j + 1].getAltura() != 0)) {
                if(m.getCubos()>0){
                    if (mat[fila][j].getAltura() < 5) {

                        mat[fila][j].setAltura(mat[fila][j].getAltura() + 1);

                        if (!p.getTurno()) {
                            mat[fila][j].setColor("\u001B[34m");
                            mat[fila][j].setColor(true);
                        }
                        if (p.getTurno()) {
                            mat[fila][j].setColor("\u001B[31m");
                            mat[fila][j].setColor(false);
                        }
                        m.setCubos(m.getCubos() - 1);
                    }
                }
            }
        }
    }
    //Metodo de esquina3
    public static void metodoEsquina3(Celda mat[][], int i, int j, Partida p, Jugador m) {
        for (int columna = j - 1; esPosicionValida(mat, i, columna) && mat[i][columna].getAltura() != 0; columna--) {
            if ((esPosicionValida(mat, i - 1, columna) && mat[i - 1][columna].getAltura() != 0) || (esPosicionValida(mat, i + 1, columna) && mat[i + 1][columna].getAltura() != 0)) {
                if (m.getCubos()>0){
                    if (mat[i][columna].getAltura() < 5) {
                        mat[i][columna].setAltura(mat[i][columna].getAltura() + 1);
                        if (!p.getTurno()) {
                            mat[i][columna].setColor("\u001B[34m");
                            mat[i][columna].setColor(true);
                        }
                        if (p.getTurno()) {
                            mat[i][columna].setColor("\u001B[31m");
                            mat[i][columna].setColor(false);
                        }
                        m.setCubos(m.getCubos() - 1);
                    }
                }
            }
        }
    }
    //Metodo de esquina4
    public static void metodoEsquina4(Celda mat[][], int i, int j, Partida p, Jugador m) {
        for (int columna = j + 1; esPosicionValida(mat, i, columna) && mat[i][columna].getAltura() != 0; columna++) {
            if ((esPosicionValida(mat, i - 1, columna) && mat[i - 1][columna].getAltura() != 0) || (esPosicionValida(mat, i + 1, columna) && mat[i + 1][columna].getAltura() != 0)) {
                if (m.getCubos()>0) {
                    if (mat[i][columna].getAltura() < 5) {
                        mat[i][columna].setAltura(mat[i][columna].getAltura() + 1);
                        if (!p.getTurno()) {
                            mat[i][columna].setColor("\u001B[34m");
                            mat[i][columna].setColor(true);
                        }
                        if (p.getTurno()) {
                            mat[i][columna].setColor("\u001B[31m");
                            mat[i][columna].setColor(false);
                        }
                        m.setCubos(m.getCubos() - 1);
                    }
                }
            }
        }
    }
    //Metodo de esquina5
    public static void metodoEsquina5(Celda mat[][], int i, int j, Partida p, Jugador m) {
        if (esPosicionValida(mat, i, j - 1) && mat[i][j - 1].getAltura() != 0 && esPosicionValida(mat, i - 1, j) && mat[i - 1][j].getAltura() != 0
                || esPosicionValida(mat, i, j - 1) && mat[i][j - 1].getAltura() != 0 && esPosicionValida(mat, i + 1, j) && mat[i + 1][j].getAltura() != 0
                || esPosicionValida(mat, i - 1, j) && mat[i - 1][j].getAltura() != 0 && esPosicionValida(mat, i, j + 1) && mat[i][j + 1].getAltura() != 0
                || esPosicionValida(mat, i, j + 1) && mat[i][j + 1].getAltura() != 0 && esPosicionValida(mat, i + 1, j) && mat[i + 1][j].getAltura() != 0) {
            if (m.getCubos()>0) {
                if (mat[i][j].getAltura() < 5) {
                    mat[i][j].setAltura(mat[i][j].getAltura() + 1);
                    if (!p.getTurno()) {
                        mat[i][j].setColor("\u001B[34m");
                        mat[i][j].setColor(true);
                    }
                    if (p.getTurno()) {
                        mat[i][j].setColor("\u001B[31m");
                        mat[i][j].setColor(false);
                    }
                    m.setCubos(m.getCubos() - 1);
                }

            }
        }
    }
    //Metodo para contar el puntaje de un jugador, se verifica que en la celda donde esta parado sea > 1 y de su color
    public static int contarPuntaje(Celda mat[][], boolean color) {
        int puntaje = 0;
        for (int k = 0; k < mat.length; k++) {
            for (int l = 0; l < mat.length; l++) {
                if (mat[k][l].getAltura() > 1 && mat[k][l].getColor() == color) {
                    puntaje = puntaje + mat[k][l].getAltura();
                }
            }
        }
        return puntaje;
    }
    //Metodo de juego de la computadora
    public static String jugarComputadora(Celda mat[][], Jugador m, Partida p) {
        String coordenada;
        int cantFichasColocables = 0;
        int puntaje1 = 0;
        int puntaje2 = 0;
        int puntajeTotal = 0;
        int puntajeTotalm = -1;
        int x = 0;
        int y = 0;
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat.length; j++) {
                Celda[][] matCopia = matrizCopiada(mat);
                puntaje1 = contarPuntaje(mat, true);
                if (validacionGeneral(matCopia, i, j)) {
                    matCopia[i][j].setAltura(matCopia[i][j].getAltura() + 1);
                    
                    puntaje2 = contarPuntaje(matCopia, true);
                    puntajeTotal = puntaje2 - puntaje1;
                    if (puntajeTotalm < puntajeTotal) {
                        puntajeTotalm = puntajeTotal;
                        x = i;
                        y = j;
                        
                    }
                }

            }
        }
        mat[x][y].setAltura(mat[x][y].getAltura() + 1);
        esEsquina(mat, x, y, p, m);
        m.setCubos(m.getCubos() - 1);
        mat[x][y].setColor("\u001B[34m");
        mat[x][y].setColor(true);
        x = x+48+17;
        char ch = (char) x;
        y = y+1;
        coordenada= ch+""+y; 
        return coordenada;
    }
    //Metodo para hacer una copia de la matriz
    public static Celda[][] matrizCopiada(Celda[][] mat) {
        Celda[][] matCopia = new Celda[6][6];
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat.length; j++) {
                //Se inicializa cada celda
                matCopia[i][j] = new Celda();
                matCopia[i][j].setAltura(mat[i][j].getAltura());
                //Lo mismo con el color
                matCopia[i][j].setColor(mat[i][j].getColor());
            }
        }
        return matCopia;
    }

}
