/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

/**
 *
 * @author Julia
 */
public class Director implements Serializable{

    private String nombre;
    private LocalDate fechaNac;
    private String nacionalidad;
    private String ocupacion;
    private ArrayList<String> peliculas;
    protected final static String[] encabezado = {"Nombre", "Fecha de nacimiento", "Nacionalidad", "Ocupación", "Películas"};

    public Director(String nombre, LocalDate fechaNac, String nacionalidad, String ocupacion, ArrayList<String> peliculas) {
        this.nombre = nombre;
        this.fechaNac = fechaNac;
        this.nacionalidad = nacionalidad;
        this.ocupacion = ocupacion;
        this.peliculas = peliculas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setFechaNac(LocalDate fechaNac) {
        this.fechaNac = fechaNac;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public static Director factory(String[] tokens) {
        if (tokens.length != 5) {
            return null;
        }
        if (tokens[0].isEmpty() || tokens[4].isEmpty()) {
            return null;
        }

        ArrayList<String> tempPeliculas = new ArrayList<>();
        LocalDate tempFechaNac;
        String tempNombre, tempNacionalidad, tempOcupacion;
        try {
            tempNombre = tokens[0];
            if (tokens[1].isEmpty()) {
                tempFechaNac = LocalDate.of(9999,9, 9);
            } else {
                tempFechaNac = LocalDate.parse(tokens[1]);
            }
            if (tokens[2].isEmpty()) {
                tempNacionalidad = "-DESCONOCIDA-";
            } else {
                tempNacionalidad = tokens[2];
            }
            if (tokens[3].isEmpty()) {
                tempOcupacion = "-DESCONOCIDA-";
            } else {
                tempOcupacion = tokens[3];
            }
            String[] tempPeli = tokens[4].split("\t");
            for (int i = 0; i < tempPeli.length; i++) {
                tempPeliculas.add(tempPeli[i]);
            }
            return new Director(tempNombre, tempFechaNac, tempNacionalidad, tempOcupacion, tempPeliculas);
        } catch (Exception ex) {
            return null;
        }
    }

    public LocalDate getFechaNac() {
        return fechaNac;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public ArrayList<String> getPeliculas() {
        return peliculas;
    }

    public String[] stateAsStringList() {
        String peliculas = "";
        for (int i = 0; i < this.peliculas.size(); i++) {
            if (i >= 1) {
                peliculas = peliculas.concat("   ");
            }
            peliculas = peliculas.concat(this.peliculas.get(i));
        }

        String[] linea = {nombre, String.valueOf(fechaNac), nacionalidad, ocupacion, peliculas};
        return linea;
    }
    
    public String comoColumnas(int[] numColumnas){
        String formato= "%-"
                + numColumnas[0]
                +"s    %-"
                + numColumnas[1]
                +"s    %-"
                + numColumnas[2]
                +"s    %-"
                + numColumnas[3]
                +"s    %-"
                + numColumnas[4]
                +"s";
        
        String peliculas = "";
        for (int i = 0; i < this.peliculas.size(); i++) {
            if (i >= 1) {
                peliculas = peliculas.concat("   ");
            }
            peliculas = peliculas.concat(this.peliculas.get(i));
        }
        
        return String.format(formato,nombre, String.valueOf(fechaNac), nacionalidad, ocupacion, peliculas);
        
    }

}
