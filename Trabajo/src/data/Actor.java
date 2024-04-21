/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Julia
 */
public class Actor implements Serializable {

    private String nombre;
    private LocalDate fechaNac;
    private String nacionalidad;
    private int anio;
    private ArrayList<String> peliculas;
    protected final static String[] encabezado = {"Nombre", "Fecha de nacimiento", "Nacionalidad", "Año de debut", "Películas"};

    public Actor(String nombre, LocalDate fechaNac, String nacionalidad, int anio, ArrayList<String> peliculas) {
        this.nombre = nombre;
        this.fechaNac = fechaNac;
        this.nacionalidad = nacionalidad;
        this.anio = anio;
        this.peliculas = peliculas;
    }

    public Actor(String nombre, ArrayList<String> peliculas) {
        this.nombre = nombre;
        this.peliculas = peliculas;
    }

    public String getNombre() {
        return nombre;
    }

    public int getAnio() {
        return anio;
    }

    public void setFechaNac(LocalDate fechaNac) {
        this.fechaNac = fechaNac;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public ArrayList<String> getPeliculas() {
        return peliculas;
    }

    public static Actor factory(String[] tokens) {
        if (tokens.length != 5) {
            return null;
        }
        if (tokens[0].isEmpty() || tokens[4].isEmpty()) {
            return null;
        }

        ArrayList<String> tempPeliculas = new ArrayList<>();
        LocalDate tempFechaNac;
        String tempNombre, tempNacionalidad;
        int tempAnio;
        try {
            tempNombre = tokens[0];
            if (tokens[1].isEmpty()) {
                tempFechaNac = LocalDate.of(9999, 9, 9);
            } else {
                tempFechaNac = LocalDate.parse(tokens[1]);
            }
            if (tokens[2].isEmpty()) {
                tempNacionalidad = "-DESCONOCIDA-";
            } else {
                tempNacionalidad = tokens[2];
            }
            if (tokens[3].isEmpty()) {
                tempAnio = 9999;
            } else {
                tempAnio = Integer.parseInt(tokens[3]);
            }
            String[] tempPeli = tokens[4].split("\t");
            for (String tempPeli1 : tempPeli) {
                tempPeliculas.add(tempPeli1);
            }
            return new Actor(tempNombre, tempFechaNac, tempNacionalidad, tempAnio, tempPeliculas);
        } catch (Exception ex) {
            return null;
        }
    }

    public String[] stateAsStringList() {
        String peliculas = "";
        for (int i = 0; i < this.peliculas.size(); i++) {
            if (i >= 1) {
                peliculas = peliculas.concat("   ");
            }
            peliculas = peliculas.concat(this.peliculas.get(i));
        }

        String[] linea = {nombre, String.valueOf(fechaNac), nacionalidad, String.valueOf(anio), peliculas};
        return linea;
    }
}
