/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.util.ArrayList;

/**
 *
 * @author Julia
 */
public class Filmoteca {

    private ArrayList<Pelicula> coleccionDePeliculas;
    private ArrayList<Director> coleccionDeDirectores;
    private ArrayList<Actor> coleccionDeActores;
    private final String NOMBRE_CARPETA = "IMDB21";
    private final String[] NOMBRES_ARCHIVOS_BINARIOS = {"peliculas.bin", "directores.bin", "actores.bin"};
    private final String nombreArchivoCol = "directores.col";
    private final String nombreArchivoHTML = "peliculas.html";

    public String getNOMBRE_CARPETA() {
        return NOMBRE_CARPETA;
    }

    public String[] getNOMBRES_ARCHIVOS_BINARIOS() {
        return NOMBRES_ARCHIVOS_BINARIOS;
    }

    public String getNombreArchivoCol() {
        return nombreArchivoCol;
    }

    public String getNombreArchivoHTML() {
        return nombreArchivoHTML;
    }

    public Filmoteca() {
        this.coleccionDePeliculas = new ArrayList<>();
        this.coleccionDeActores = new ArrayList<>();
        this.coleccionDeDirectores = new ArrayList<>();
    }

    public Filmoteca(ArrayList<Pelicula> coleccionDePeliculas, ArrayList<Director> coleccionDeDirectores, ArrayList<Actor> coleccionDeActores) {
        this.coleccionDePeliculas = coleccionDePeliculas;
        this.coleccionDeDirectores = coleccionDeDirectores;
        this.coleccionDeActores = coleccionDeActores;
    }

    public void setColeccionDePeliculas(ArrayList<Pelicula> coleccionDePeliculas) {
        this.coleccionDePeliculas = coleccionDePeliculas;
    }

    public void setColeccionDeDirectores(ArrayList<Director> coleccionDeDirectores) {
        this.coleccionDeDirectores = coleccionDeDirectores;
    }

    public void setColeccionDeActores(ArrayList<Actor> coleccionDeActores) {
        this.coleccionDeActores = coleccionDeActores;
    }

    public ArrayList<Pelicula> getColeccionDePeliculas() {
        return coleccionDePeliculas;
    }

    public ArrayList<Director> getColeccionDeDirectores() {
        return coleccionDeDirectores;
    }

    public ArrayList<Actor> getColeccionDeActores() {
        return coleccionDeActores;
    }

}
