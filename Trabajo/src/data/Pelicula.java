/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Julia
 */
public class Pelicula implements Serializable {

    private String titulo;
    private int anio;
    private int duracion;
    private String pais;
    private ArrayList<String> direccion;
    private String guionista;
    private String musica;
    private ArrayList<String> reparto;
    private String productora;
    private String genero;
    private String sinopsis;
    protected final static String[] encabezado = {"Título", "Año", "Duración", "País", "Dirección", "Guionista", "Música", "Reparto", "Productora", "Género", "Sinopsis"};

    public Pelicula(String titulo, int anio, int duracion, String pais, ArrayList<String> direccion, String guionista, String musica, ArrayList<String> reparto, String productora, String genero, String sinopsis) {
        this.titulo = titulo;
        this.anio = anio;
        this.duracion = duracion;
        this.pais = pais;
        this.direccion = direccion;
        this.guionista = guionista;
        this.musica = musica;
        this.reparto = reparto;
        this.productora = productora;
        this.genero = genero;
        this.sinopsis = sinopsis;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public void setGuionista(String guionista) {
        this.guionista = guionista;
    }

    public void setMusica(String musica) {
        this.musica = musica;
    }

    public void setProductora(String productora) {
        this.productora = productora;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public static Pelicula factory(String[] tokens) {

        if (tokens.length != 11) {
            System.out.printf("AAAA");
            return null;
        }

        for (String s : tokens) {
            if (s.isEmpty()) {
                System.out.printf("hola4");
                return null;
            }
        }

        int tempAnio, tempDuracion;
        ArrayList<String> tempDireccion = new ArrayList<>();
        ArrayList<String> tempReparto = new ArrayList<>();

        try {
            tempAnio = Integer.parseInt(tokens[1]);
            tempDuracion = Integer.parseInt(tokens[2]);
            String[] tempDir = tokens[4].split("\t");
            for (int i = 0; i < tempDir.length; i++) {
                tempDireccion.add(tempDir[i]);
            }
            String[] tempRep = tokens[7].split("\t");
            for (int i = 0; i < tempRep.length; i++) {
                tempReparto.add(tempRep[i]);
            }
        } catch (Exception ex) {
            System.out.printf("hola5");
            return null;
        }

        String tempTitulo = tokens[0];
        String tempPais = tokens[3];
        String tempGuionista = tokens[5];
        String tempMusica = tokens[6];
        String tempProductora = tokens[8];
        String tempGenero = tokens[9];
        String tempSinopsis = tokens[10];

        return new Pelicula(tempTitulo, tempAnio, tempDuracion, tempPais, tempDireccion, tempGuionista, tempMusica, tempReparto, tempProductora, tempGenero, tempSinopsis);
    }

    public String[] stateAsStringList() {
        String direccion = "";
        for (int i = 0; i < this.direccion.size(); i++) {
            if (i >= 1) {
                direccion = direccion.concat("   ");
            }
            direccion = direccion.concat(this.direccion.get(i));
        }
        String reparto = "";
        for (int i = 0; i < this.reparto.size(); i++) {
            if (i >= 1) {
                reparto = reparto.concat("   ");
            }
            reparto = reparto.concat(this.reparto.get(i));
        }
        String[] datos = {titulo, String.valueOf(anio), String.valueOf(this.duracion), pais, direccion, guionista, musica, reparto, productora, genero, sinopsis};
        return datos;
    }

    public String getTitulo() {
        return titulo;
    }

    protected static String tableHeader(String[] encabezado) {
        String linea;
        linea = String.format("<TR>"
                + "<TH>%s</TH>"
                + "<TH>%s</TH>"
                + "<TH>%s</TH>"
                + "<TH>%s</TH>"
                + "<TH>%s</TH>"
                + "<TH>%s</TH>"
                + "<TH>%s</TH>"
                + "<TH>%s</TH>"
                + "<TH>%s</TH>"
                + "<TH>%s</TH>"
                + "<TH>%s</TH>"
                + "</TR>",
                encabezado[0], encabezado[1], encabezado[2], encabezado[3], encabezado[4], encabezado[5],
                encabezado[6], encabezado[7], encabezado[8], encabezado[9], encabezado[10]);
        return linea;
    }

    public String stateAsStringHTML() {
        String linea;
        String direccion = "";
        for (int i = 0; i < this.direccion.size(); i++) {
            if (i >= 1) {
                direccion = direccion.concat(", ");
            }
            direccion = direccion.concat(this.direccion.get(i));
        }
        String reparto = "";
        for (int i = 0; i < this.reparto.size(); i++) {
            if (i >= 1) {
                reparto = reparto.concat(", ");
            }
            reparto = reparto.concat(this.reparto.get(i));
        }
        linea = String.format("<TR>"
                + "<TD>%s</TD>"
                + "<TD>%d</TD>"
                + "<TD>%d</TD>"
                + "<TD>%s</TD>"
                + "<TD>%s</TD>"
                + "<TD>%s</TD>"
                + "<TD>%s</TD>"
                + "<TD>%s</TD>"
                + "<TD>%s</TD>"
                + "<TD>%s</TD>"
                + "<TD>%s</TD>",
                titulo, anio, duracion, pais, direccion, guionista, musica, reparto, productora, genero, sinopsis);
        return linea;
    }

}
