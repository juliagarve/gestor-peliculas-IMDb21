/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

/**
 *
 * @author Julia
 */
import data.Modelo;
import java.time.LocalDate;
import java.util.ArrayList;

public class Controlador {

    Modelo modelo = new Modelo();

    public void arranque() {
        modelo.arranque();
    }

    public String exportarDirectoresCol() {
        return modelo.exportarDirectoresCol();
    }

    public String getNombreArchivoCol() {
        return modelo.getNombreArchivoCol();
    }

    public int getNumDirectores() {
        return modelo.getNumDirectores();
    }

    public String exportarPeliculasHTML() {
        return modelo.exportarPeliculasHTML();
    }

    public String getNombreArchivoHTML() {
        return modelo.getNombreArchivoHTML();
    }

    public int getNumPeliculas() {
        return modelo.getNumPeliculas();
    }

    public void altaPelicula(String titulo, int anio, int duracion, String pais, ArrayList<String> direccion, String guionista, String musica, ArrayList<String> reparto, String productora, String genero, String sinopsis) {
        modelo.altaPelicula(titulo, anio, duracion, pais, direccion, guionista, musica, reparto, productora, genero, sinopsis);
    }

    public void eliminarPelicula(String titulo) {
        modelo.eliminarPelicula(titulo);
    }

    public String setAnioPelicula(String titulo, int anio) {
        return modelo.setAnioPelicula(titulo, anio);
    }

    public String setDuracionPelicula(String titulo, int duracion) {
        return modelo.setDuracionPelicula(titulo, duracion);
    }

    public String setPaisPelicula(String titulo, String pais) {
        return modelo.setPaisPelicula(titulo, pais);
    }

    public String setGuionistaPelicula(String titulo, String guionista) {
        return modelo.setGuionistaPelicula(titulo, guionista);
    }

    public String setMusicaPelicula(String titulo, String musica) {
        return modelo.setMusicaPelicula(titulo, musica);
    }

    public String setProductoraPelicula(String titulo, String productora) {
        return modelo.setProductoraPelicula(titulo, productora);
    }

    public String setGeneroPelicula(String titulo, String genero) {
        return modelo.setGeneroPelicula(titulo, genero);
    }

    public String setSinopsisPelicula(String titulo, String sinopsis) {
        return modelo.setSinopsisPelicula(titulo, sinopsis);
    }

    public String[][] getPelicula(String titulo) {
        return modelo.getPelicula(titulo);
    }

    public void altaDirector(String nombre, LocalDate fechaNac, String nacionalidad, String ocupacion, ArrayList<String> peliculas) {
        modelo.altaDirector(nombre, fechaNac, nacionalidad, ocupacion, peliculas);
    }

    public ArrayList<String> eliminarDirector(String nombre) {
        return modelo.eliminarDirector(nombre);
    }

    public String setFechaNacDirector(String nombre, LocalDate fechaNac) {
        return modelo.setFechaNacDirector(nombre, fechaNac);
    }

    public String setNacionalidadDirector(String nombre, String nacionalidad) {
        return modelo.setNacionalidadDirector(nombre, nacionalidad);
    }

    public String setOcupacionDirector(String nombre, String ocupacion) {
        return modelo.setOcupacionDirector(nombre, ocupacion);
    }

    public void altaActor(String nombre, LocalDate fechaNac, String nacionalidad, int anio, ArrayList<String> peliculas) {
        modelo.altaActor(nombre, fechaNac, nacionalidad, anio, peliculas);
    }

    public ArrayList<String> eliminarActor(String nombre) {
        return modelo.eliminarActor(nombre);
    }

    public String setFechaNacActor(String nombre, LocalDate fechaNac) {
        return modelo.setFechaNacActor(nombre, fechaNac);
    }

    public String setNacionalidadActor(String nombre, String nacionalidad) {
        return modelo.setNacionalidadActor(nombre, nacionalidad);
    }

    public String setAnioActor(String nombre, int anio) {
        return modelo.setAnioActor(nombre, anio);
    }

    public String[][] getPeliculasActor(String nombre) {
        return modelo.getPeliculasActor(nombre);
    }

    public String[][] getColeccionDePeliculasOrdenadas() {
        return modelo.getColeccionDePeliculasOrdenadas();
    }

    public String[][] getColeccionDeDirectoresOrdenados() {
        return modelo.getColeccionDeDirectoresOrdenados();
    }

    public String[][] getColeccionDeActoresOrdenados() {
        return modelo.getColeccionDeActoresOrdenados();
    }

    public void salida() {
        modelo.salida();
    }

}
