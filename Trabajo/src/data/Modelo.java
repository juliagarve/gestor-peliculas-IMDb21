/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import com.coti.tools.OpMat;
import com.coti.tools.Rutas;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Julia
 */
public class Modelo {

    private Filmoteca filmoteca;

    public Modelo() {
        this.filmoteca = new Filmoteca();
    }

    public void arranque() {
        final String NOMBRE_CARPETA = filmoteca.getNOMBRE_CARPETA();
        final String[] NOMBRES_ARCHIVOS_BINARIOS = filmoteca.getNOMBRES_ARCHIVOS_BINARIOS();
        for (String nombreArchivo : NOMBRES_ARCHIVOS_BINARIOS) {
            File f = Rutas.pathToFileInFolderOnDesktop(NOMBRE_CARPETA, nombreArchivo).toFile();
            if (!f.exists()) {
                try {
                    f = Rutas.pathToFileInFolderOnDesktop(NOMBRE_CARPETA, nombreArchivo.replace("bin", "txt")).toFile();
                    String[][] datos = OpMat.importFromDisk(f, "#");
                    for (int i = 0; i < datos.length; i++) {
                        String[] linea = datos[i];
                        if (nombreArchivo.contentEquals(NOMBRES_ARCHIVOS_BINARIOS[0])) {
                            Pelicula pelicula = Pelicula.factory(linea);
                            if (pelicula != null) {
                                filmoteca.getColeccionDePeliculas().add(pelicula);
                            }
                        }
                        if (nombreArchivo.contentEquals(NOMBRES_ARCHIVOS_BINARIOS[1])) {
                            Director director = Director.factory(linea);
                            if (director != null) {
                                filmoteca.getColeccionDeDirectores().add(director);
                            }
                        }
                        if (nombreArchivo.contentEquals(NOMBRES_ARCHIVOS_BINARIOS[2])) {
                            Actor actor = Actor.factory(linea);
                            if (actor != null) {
                                filmoteca.getColeccionDeActores().add(actor);
                            }
                        }
                    }

                } catch (Exception ex) {
                    System.err.printf("Error al cargar el archivo %s", nombreArchivo);
                }
            } else {
                try {
                    FileInputStream fis = new FileInputStream(f);
                    BufferedInputStream bis = new BufferedInputStream(fis);
                    ObjectInputStream ois = new ObjectInputStream(bis);
                    if (nombreArchivo.contentEquals(NOMBRES_ARCHIVOS_BINARIOS[0])) {
                        filmoteca.setColeccionDePeliculas((ArrayList<Pelicula>) ois.readObject());
                    }
                    if (nombreArchivo.contentEquals(NOMBRES_ARCHIVOS_BINARIOS[1])) {
                        filmoteca.setColeccionDeDirectores((ArrayList<Director>) ois.readObject());
                    }
                    if (nombreArchivo.contentEquals(NOMBRES_ARCHIVOS_BINARIOS[2])) {
                        filmoteca.setColeccionDeActores((ArrayList<Actor>) ois.readObject());
                    }
                    ois.close();
                } catch (IOException | ClassNotFoundException ex) {
                    System.err.printf("%nNo fue posible leer el archivo %s%n", nombreArchivo);
                    System.err.printf("Por favor, copie el archivo %s en la carpeta %s en el Escritorio%n%n", nombreArchivo, filmoteca.getNOMBRE_CARPETA());
                }
            }

        }

    }

    public String exportarDirectoresCol() {
        ArrayList<Director> coleccionDeDirectores = filmoteca.getColeccionDeDirectores();
        if (coleccionDeDirectores == null || coleccionDeDirectores.isEmpty()) {
            return null;
        }
        String[][] matrizDeDirectores = new String[coleccionDeDirectores.size() + 1][];
        matrizDeDirectores[0] = Director.encabezado;
        for (int i = 0; i < coleccionDeDirectores.size(); i++) {
            matrizDeDirectores[i + 1] = coleccionDeDirectores.get(i).stateAsStringList();
        }

        int[] maxAnchoColumna = new int[matrizDeDirectores[0].length];
        final int NUM_FILAS = matrizDeDirectores.length;
        final int NUM_COLUMNAS = matrizDeDirectores[0].length;
        Arrays.fill(maxAnchoColumna, 0);

        for (int numFila = 0; numFila < NUM_FILAS; numFila++) {
            for (int numColumna = 0; numColumna < NUM_COLUMNAS; numColumna++) {
                if (matrizDeDirectores[numFila][numColumna].length() > maxAnchoColumna[numColumna]) {
                    maxAnchoColumna[numColumna] = matrizDeDirectores[numFila][numColumna].length();
                }
            }
        }

        String formato = "%-"
                + maxAnchoColumna[0]
                + "s    %-"
                + maxAnchoColumna[1]
                + "s    %-"
                + maxAnchoColumna[2]
                + "s    %-"
                + maxAnchoColumna[3]
                + "s    %-"
                + maxAnchoColumna[4]
                + "s";

        String encabezado = String.format(formato, Director.encabezado[0], Director.encabezado[1], Director.encabezado[2], Director.encabezado[3], Director.encabezado[4]);
        ArrayList<String> temp = new ArrayList<>();
        temp.add(encabezado);
        for (Director d : coleccionDeDirectores) {
            temp.add(d.comoColumnas(maxAnchoColumna));
        }

        File f = Rutas.fileToFileInFolderOnDesktop(filmoteca.getNOMBRE_CARPETA(), filmoteca.getNombreArchivoCol());
        try {
            Files.write(f.toPath(), temp, Charset.defaultCharset(), StandardOpenOption.CREATE);
            return "";
        } catch (Exception ex) {
            return "Error. No se ha creado el archivo " + filmoteca.getNombreArchivoCol();
        }
    }

    public int getNumDirectores() {
        return filmoteca.getColeccionDeDirectores().size();
    }

    public String getNombreArchivoCol() {
        return filmoteca.getNombreArchivoCol();
    }

    public String exportarPeliculasHTML() {

        File f = Rutas.fileToFileInFolderOnDesktop(filmoteca.getNOMBRE_CARPETA(), filmoteca.getNombreArchivoHTML());
        try {
            PrintWriter pw = new PrintWriter(f);
            pw.printf("<!DOCTYPE html>%n"
                    + "<HTML>%n"
                    + "<HEAD>%n"
                    + "<meta charser=\"UTF.8\">%n"
                    + "<H1>Listado de películas</H1>%n"
                    + "</HEAD>%n"
                    + "<BODY>%n");
            pw.printf("<TABLE BORDER=1>%n");
            pw.printf("%s%n", Pelicula.tableHeader(Pelicula.encabezado));
            for (Pelicula p : filmoteca.getColeccionDePeliculas()) {
                pw.printf("%s%n", p.stateAsStringHTML());
            }
            pw.printf("</TABLE>%n</BODY>%n</HTML>%n");
            pw.close();
            return "";
        } catch (FileNotFoundException ex) {
            return "Error. No se ha creado el archivo " + filmoteca.getNombreArchivoHTML();
        }
    }

    public int getNumPeliculas() {
        return filmoteca.getColeccionDePeliculas().size();
    }

    public String getNombreArchivoHTML() {
        return filmoteca.getNombreArchivoHTML();
    }

    public void altaPelicula(String titulo, int anio, int duracion, String pais, ArrayList<String> direccion, String guionista, String musica, ArrayList<String> reparto, String productora, String genero, String sinopsis) {
        Pelicula p = new Pelicula(titulo, anio, duracion, pais, direccion, guionista, musica, reparto, productora, genero, sinopsis);
        filmoteca.getColeccionDePeliculas().add(p);
        ArrayList<Director> coleccionDeDirectores = filmoteca.getColeccionDeDirectores();
        boolean encontrado;
        String tempNombre, nombre;
        for (int i = 0; i < direccion.size(); i++) {
            encontrado = false;
            nombre = direccion.get(i);
            for (int j = 0; j < coleccionDeDirectores.size() && !encontrado; j++) {
                tempNombre = coleccionDeDirectores.get(j).getNombre();
                if (nombre.equalsIgnoreCase(tempNombre)) {
                    encontrado = true;
                    filmoteca.getColeccionDeDirectores().get(j).getPeliculas().add(titulo);
                }
            }
            if (!encontrado) {
                ArrayList<String> peliculas = new ArrayList<>();
                peliculas.add(titulo);
                Director d = new Director(nombre, LocalDate.of(9999, 9, 9), "-DESCONOCIDA-", "-DESCONOCIDA-", peliculas);
                filmoteca.getColeccionDeDirectores().add(d);
            }
        }

        ArrayList<Actor> coleccionDeActores = filmoteca.getColeccionDeActores();
        for (int i = 0; i < reparto.size(); i++) {
            encontrado = false;
            nombre = reparto.get(i);
            for (int j = 0; j < coleccionDeActores.size() && !encontrado; j++) {
                tempNombre = coleccionDeActores.get(j).getNombre();
                if (nombre.equalsIgnoreCase(tempNombre)) {
                    encontrado = true;
                    filmoteca.getColeccionDeActores().get(j).getPeliculas().add(titulo);
                }
            }
            if (!encontrado) {
                ArrayList<String> peliculas = new ArrayList<>();
                peliculas.add(titulo);
                Actor a = new Actor(nombre, LocalDate.of(9999, 9, 9), "-DESCONOCIDA-", 9999, peliculas);
                filmoteca.getColeccionDeActores().add(a);
            }
        }
    }

    public void eliminarPelicula(String titulo) {
        ArrayList<Pelicula> coleccionDePeliculas = filmoteca.getColeccionDePeliculas();
        ArrayList<Director> coleccionDeDirectores = filmoteca.getColeccionDeDirectores();
        ArrayList<Actor> coleccionDeActores = filmoteca.getColeccionDeActores();
        coleccionDePeliculas.removeIf(pelicula -> pelicula.getTitulo().equalsIgnoreCase(titulo));
        for (Director d : coleccionDeDirectores) {
            d.getPeliculas().removeIf(pelicula -> pelicula.equalsIgnoreCase(titulo));
        }
        coleccionDeDirectores.removeIf(director -> director.getPeliculas().isEmpty());
        for (Actor a : coleccionDeActores) {
            a.getPeliculas().removeIf(pelicula -> pelicula.equalsIgnoreCase(titulo));
        }
        coleccionDeActores.removeIf(actor -> actor.getPeliculas().isEmpty());

    }

    public String setAnioPelicula(String titulo, int anio) {
        ArrayList<Pelicula> coleccionDePeliculas = filmoteca.getColeccionDePeliculas();
        boolean encontrado = false;
        for (int i = 0; i < coleccionDePeliculas.size() && !encontrado; i++) {
            if (coleccionDePeliculas.get(i).getTitulo().equalsIgnoreCase(titulo)) {
                encontrado = true;
                coleccionDePeliculas.get(i).setAnio(anio);
            }
        }
        if (!encontrado) {
            return "%nLa película cuyos datos desea modificar no está registrada.%n";
        } else {
            return "";
        }
    }

    public String setDuracionPelicula(String titulo, int duracion) {
        ArrayList<Pelicula> coleccionDePeliculas = filmoteca.getColeccionDePeliculas();
        boolean encontrado = false;
        for (int i = 0; i < coleccionDePeliculas.size() && !encontrado; i++) {
            if (coleccionDePeliculas.get(i).getTitulo().equalsIgnoreCase(titulo)) {
                encontrado = true;
                coleccionDePeliculas.get(i).setDuracion(duracion);
            }
        }
        if (!encontrado) {
            return "%nLa película cuyos datos desea modificar no está registrada.%n";
        } else {
            return "";
        }
    }

    public String setPaisPelicula(String titulo, String pais) {
        ArrayList<Pelicula> coleccionDePeliculas = filmoteca.getColeccionDePeliculas();
        boolean encontrado = false;
        for (int i = 0; i < coleccionDePeliculas.size() && !encontrado; i++) {
            if (coleccionDePeliculas.get(i).getTitulo().equalsIgnoreCase(titulo)) {
                encontrado = true;
                coleccionDePeliculas.get(i).setPais(pais);
            }
        }
        if (!encontrado) {
            return "%nLa película cuyos datos desea modificar no está registrada.%n";
        } else {
            return "";
        }
    }

    public String setGuionistaPelicula(String titulo, String guionista) {
        ArrayList<Pelicula> coleccionDePeliculas = filmoteca.getColeccionDePeliculas();
        boolean encontrado = false;
        for (int i = 0; i < coleccionDePeliculas.size() && !encontrado; i++) {
            if (coleccionDePeliculas.get(i).getTitulo().equalsIgnoreCase(titulo)) {
                encontrado = true;
                coleccionDePeliculas.get(i).setGuionista(guionista);
            }
        }
        if (!encontrado) {
            return "%nLa película cuyos datos desea modificar no está registrada.%n";
        } else {
            return "";
        }
    }

    public String setMusicaPelicula(String titulo, String musica) {
        ArrayList<Pelicula> coleccionDePeliculas = filmoteca.getColeccionDePeliculas();
        boolean encontrado = false;
        for (int i = 0; i < coleccionDePeliculas.size() && !encontrado; i++) {
            if (coleccionDePeliculas.get(i).getTitulo().equalsIgnoreCase(titulo)) {
                encontrado = true;
                coleccionDePeliculas.get(i).setMusica(musica);
            }
        }
        if (!encontrado) {
            return "%nLa película cuyos datos desea modificar no está registrada.%n";
        } else {
            return "";
        }
    }

    public String setProductoraPelicula(String titulo, String productora) {
        ArrayList<Pelicula> coleccionDePeliculas = filmoteca.getColeccionDePeliculas();
        boolean encontrado = false;
        for (int i = 0; i < coleccionDePeliculas.size() && !encontrado; i++) {
            if (coleccionDePeliculas.get(i).getTitulo().equalsIgnoreCase(titulo)) {
                encontrado = true;
                coleccionDePeliculas.get(i).setProductora(productora);
            }
        }
        if (!encontrado) {
            return "%nLa película cuyos datos desea modificar no está registrada.%n";
        } else {
            return "";
        }
    }

    public String setGeneroPelicula(String titulo, String genero) {
        ArrayList<Pelicula> coleccionDePeliculas = filmoteca.getColeccionDePeliculas();
        boolean encontrado = false;
        for (int i = 0; i < coleccionDePeliculas.size() && !encontrado; i++) {
            if (coleccionDePeliculas.get(i).getTitulo().equalsIgnoreCase(titulo)) {
                encontrado = true;
                coleccionDePeliculas.get(i).setGenero(genero);
            }
        }
        if (!encontrado) {
            return "%nLa película cuyos datos desea modificar no está registrada.%n";
        } else {
            return "";
        }
    }

    public String setSinopsisPelicula(String titulo, String sinopsis) {
        ArrayList<Pelicula> coleccionDePeliculas = filmoteca.getColeccionDePeliculas();
        boolean encontrado = false;
        for (int i = 0; i < coleccionDePeliculas.size() && !encontrado; i++) {
            if (coleccionDePeliculas.get(i).getTitulo().equalsIgnoreCase(titulo)) {
                encontrado = true;
                coleccionDePeliculas.get(i).setSinopsis(sinopsis);
            }
        }
        if (!encontrado) {
            return "%nLa película cuyos datos desea modificar no está registrada.%n";
        } else {
            return "";
        }
    }

    public String[][] getPelicula(String titulo) {
        ArrayList<Pelicula> coleccionDePeliculas = filmoteca.getColeccionDePeliculas();
        if (coleccionDePeliculas == null || coleccionDePeliculas.isEmpty()) {
            return null;
        }
        String[][] DatosPelicula = new String[2][];
        String[] encabezado = Pelicula.encabezado;
        DatosPelicula[0] = encabezado;
        boolean flag = false;
        for (int i = 0; i < coleccionDePeliculas.size() && !flag; i++) {
            String tempTitulo = coleccionDePeliculas.get(i).getTitulo();
            if (tempTitulo.equalsIgnoreCase(titulo)) {
                DatosPelicula[1] = coleccionDePeliculas.get(i).stateAsStringList();
                flag = true;
            }
        }

        if (!flag) {
            return null;
        } else {
            return DatosPelicula;
        }

    }

    public void altaDirector(String nombre, LocalDate fechaNac, String nacionalidad, String ocupacion, ArrayList<String> peliculas) {
        ArrayList<Director> coleccionDeDirectores = filmoteca.getColeccionDeDirectores();
        Director director = new Director(nombre, fechaNac, nacionalidad, ocupacion, peliculas);
        coleccionDeDirectores.add(director);
    }

    public ArrayList<String> eliminarDirector(String nombre) {
        ArrayList<Director> coleccionDeDirectores = filmoteca.getColeccionDeDirectores();
        ArrayList<Pelicula> coleccionDePeliculas = filmoteca.getColeccionDePeliculas();
        ArrayList<String> peliculas = new ArrayList<>();
        boolean encontrado1, encontrado2;
        encontrado1 = false;
        for (int i = 0; i < coleccionDeDirectores.size() && !encontrado1; i++) {
            if (coleccionDeDirectores.get(i).getNombre().equalsIgnoreCase(nombre)) {
                encontrado1 = true;
                for (String p : coleccionDeDirectores.get(i).getPeliculas()) {
                    encontrado2 = false;
                    for (int j = 0; j < coleccionDePeliculas.size() && !encontrado2; j++) {
                        if (coleccionDePeliculas.get(j).getTitulo().equalsIgnoreCase(p)) {
                            encontrado2 = true;
                        }
                    }
                    if (encontrado2) {
                        peliculas.add(p);
                    }
                }
                if (peliculas.isEmpty()) {
                    coleccionDeDirectores.remove(i);
                }
            }
        }

        return peliculas;
    }

    public String setFechaNacDirector(String nombre, LocalDate fechaNac) {
        ArrayList<Director> coleccionDeDirectores = filmoteca.getColeccionDeDirectores();
        boolean encontrado = false;
        for (int i = 0; i < coleccionDeDirectores.size() && !encontrado; i++) {
            if (coleccionDeDirectores.get(i).getNombre().equalsIgnoreCase(nombre)) {
                encontrado = true;
                coleccionDeDirectores.get(i).setFechaNac(fechaNac);
            }
        }
        if (!encontrado) {
            return "%nEl director cuyos datos desea modificar no está registrado.%n";
        } else {
            return "";
        }
    }

    public String setNacionalidadDirector(String nombre, String nacionalidad) {
        ArrayList<Director> coleccionDeDirectores = filmoteca.getColeccionDeDirectores();
        boolean encontrado = false;
        for (int i = 0; i < coleccionDeDirectores.size() && !encontrado; i++) {
            if (coleccionDeDirectores.get(i).getNombre().equalsIgnoreCase(nombre)) {
                encontrado = true;
                coleccionDeDirectores.get(i).setNacionalidad(nacionalidad);
            }
        }
        if (!encontrado) {
            return "%nEl director cuyos datos desea modificar no está registrado.%n";
        } else {
            return "";
        }
    }

    public String setOcupacionDirector(String nombre, String ocupacion) {
        ArrayList<Director> coleccionDeDirectores = filmoteca.getColeccionDeDirectores();
        boolean encontrado = false;
        for (int i = 0; i < coleccionDeDirectores.size() && !encontrado; i++) {
            if (coleccionDeDirectores.get(i).getNombre().equalsIgnoreCase(nombre)) {
                encontrado = true;
                coleccionDeDirectores.get(i).setOcupacion(ocupacion);
            }
        }
        if (!encontrado) {
            return "%nEl director cuyos datos desea modificar no está registrado.%n";
        } else {
            return "";
        }
    }

    public void altaActor(String nombre, LocalDate fechaNac, String nacionalidad, int anio, ArrayList<String> peliculas) {
        ArrayList<Actor> coleccionDeActores = filmoteca.getColeccionDeActores();
        Actor actor = new Actor(nombre, fechaNac, nacionalidad, anio, peliculas);
        coleccionDeActores.add(actor);
    }

    public ArrayList<String> eliminarActor(String nombre) {
        ArrayList<Actor> coleccionDeActores = filmoteca.getColeccionDeActores();
        ArrayList<Pelicula> coleccionDePeliculas = filmoteca.getColeccionDePeliculas();
        ArrayList<String> peliculas = new ArrayList<>();
        boolean encontrado1, encontrado2;
        encontrado1 = false;
        for (int i = 0; i < coleccionDeActores.size() && !encontrado1; i++) {
            if (coleccionDeActores.get(i).getNombre().equalsIgnoreCase(nombre)) {
                encontrado1 = true;
                for (String p : coleccionDeActores.get(i).getPeliculas()) {
                    encontrado2 = false;
                    for (int j = 0; j < coleccionDePeliculas.size() && !encontrado2; j++) {
                        if (coleccionDePeliculas.get(j).getTitulo().equalsIgnoreCase(p)) {
                            encontrado2 = true;
                        }
                    }
                    if (encontrado2) {
                        peliculas.add(p);
                    }
                }
                if (peliculas.isEmpty()) {
                    coleccionDeActores.remove(i);
                }
            }
        }

        return peliculas;
    }

    public String setFechaNacActor(String nombre, LocalDate fechaNac) {
        ArrayList<Actor> coleccionDeActores = filmoteca.getColeccionDeActores();
        boolean encontrado = false;
        for (int i = 0; i < coleccionDeActores.size() && !encontrado; i++) {
            if (coleccionDeActores.get(i).getNombre().equalsIgnoreCase(nombre)) {
                encontrado = true;
                coleccionDeActores.get(i).setFechaNac(fechaNac);
            }
        }
        if (!encontrado) {
            return "%nEl director cuyos datos desea modificar no está registrado.%n";
        } else {
            return "";
        }
    }

    public String setNacionalidadActor(String nombre, String nacionalidad) {
        ArrayList<Actor> coleccionDeActores = filmoteca.getColeccionDeActores();
        boolean encontrado = false;
        for (int i = 0; i < coleccionDeActores.size() && !encontrado; i++) {
            if (coleccionDeActores.get(i).getNombre().equalsIgnoreCase(nombre)) {
                encontrado = true;
                coleccionDeActores.get(i).setNacionalidad(nacionalidad);
            }
        }
        if (!encontrado) {
            return "%nEl director cuyos datos desea modificar no está registrado.%n";
        } else {
            return "";
        }
    }

    public String setAnioActor(String nombre, int anio) {
        ArrayList<Actor> coleccionDeActores = filmoteca.getColeccionDeActores();
        boolean encontrado = false;
        for (int i = 0; i < coleccionDeActores.size() && !encontrado; i++) {
            if (coleccionDeActores.get(i).getNombre().equalsIgnoreCase(nombre)) {
                encontrado = true;
                coleccionDeActores.get(i).setAnio(anio);
            }
        }
        if (!encontrado) {
            return "%nEl director cuyos datos desea modificar no está registrado.%n";
        } else {
            return "";
        }
    }

    public String[][] getPeliculasActor(String nombre) {
        ArrayList<Actor> coleccionDeActores = filmoteca.getColeccionDeActores();
        ArrayList<Pelicula> coleccionDePeliculas = filmoteca.getColeccionDePeliculas();
        ArrayList<String> peliculas = new ArrayList<>();
        ArrayList<Pelicula> coleccionDePeliculasDelActor = new ArrayList<>();
        boolean encontrado = false;
        for (int i = 0; i < coleccionDeActores.size() && !encontrado; i++) {
            if (coleccionDeActores.get(i).getNombre().equalsIgnoreCase(nombre)) {
                encontrado = true;
                peliculas = coleccionDeActores.get(i).getPeliculas();
            }
        }

        String[][] matrizDePeliculas = new String[peliculas.size() + 1][];
        String[] encabezado = {Pelicula.encabezado[0], Pelicula.encabezado[1], Pelicula.encabezado[2], Pelicula.encabezado[3], Pelicula.encabezado[9]};
        matrizDePeliculas[0] = encabezado;
        coleccionDePeliculas.sort(Comparator.comparing(Pelicula::getAnio));
        int z = 1;
        for (int j = 0; j < peliculas.size(); j++) {
            encontrado = false;
            for (int i = 0; i < coleccionDePeliculas.size() && !encontrado; i++) {
                if (coleccionDePeliculas.get(i).getTitulo().equalsIgnoreCase(peliculas.get(j))) {
                    encontrado = true;
                    String[] temp = coleccionDePeliculas.get(i).stateAsStringList();
                    String[] linea = {temp[0], temp[1], temp[2], temp[3], temp[9]};
                    matrizDePeliculas[j + 1] = linea;
                }
            }
            if (encontrado == false) {
                String[] linea = {peliculas.get(j), "9999", "999", "-DESCONOCIDO-", "-DESCONOCIDO-"};
                matrizDePeliculas[peliculas.size() + 1 - z] = linea;
                z++;
            }
        }

        return matrizDePeliculas;
    }

    public String[][] getColeccionDePeliculasOrdenadas() {
        ArrayList<Pelicula> coleccionDePeliculas = filmoteca.getColeccionDePeliculas();
        coleccionDePeliculas.sort(Comparator.comparing(Pelicula::getTitulo));
        if (coleccionDePeliculas == null || coleccionDePeliculas.isEmpty()) {
            return null;
        }

        String[][] matrizDePeliculas = new String[coleccionDePeliculas.size() + 1][5];
        String[] encabezado = {Pelicula.encabezado[0], Pelicula.encabezado[1], Pelicula.encabezado[2], Pelicula.encabezado[3], Pelicula.encabezado[9]};
        matrizDePeliculas[0] = encabezado;
        for (int i = 0; i < coleccionDePeliculas.size(); i++) {
            String[] temp = coleccionDePeliculas.get(i).stateAsStringList();
            String[] linea = {temp[0], temp[1], temp[2], temp[3], temp[9]};
            matrizDePeliculas[i + 1] = linea;
        }

        return matrizDePeliculas;
    }

    public String[][] getColeccionDeDirectoresOrdenados() {
        ArrayList<Director> coleccionDeDirectores = filmoteca.getColeccionDeDirectores();

        Comparator<Director> nacionalidad = Comparator.comparing(Director::getNacionalidad);
        Comparator<Director> fechaNac = Comparator.comparing(Director::getFechaNac);
        coleccionDeDirectores.sort(nacionalidad.thenComparing(fechaNac));

        if (coleccionDeDirectores == null || coleccionDeDirectores.isEmpty()) {
            return null;
        }
        String[][] matrizDeDirectores = new String[coleccionDeDirectores.size() + 1][];
        matrizDeDirectores[0] = Director.encabezado;
        for (int i = 0; i < coleccionDeDirectores.size(); i++) {
            matrizDeDirectores[i + 1] = coleccionDeDirectores.get(i).stateAsStringList();
        }

        matrizDeDirectores[0] = Director.encabezado;

        return matrizDeDirectores;
    }

    public String[][] getColeccionDeActoresOrdenados() {
        ArrayList<Actor> coleccionDeActores = filmoteca.getColeccionDeActores();

        Comparator<Actor> anio = Comparator.comparing(Actor::getAnio);
        Comparator<Actor> nombre = Comparator.comparing(Actor::getNombre);
        coleccionDeActores.sort(anio.thenComparing(nombre));

        if (coleccionDeActores == null || coleccionDeActores.isEmpty()) {
            return null;
        }
        String[][] matrizDeActores = new String[coleccionDeActores.size() + 1][];
        matrizDeActores[0] = Actor.encabezado;
        for (int i = 0; i < coleccionDeActores.size(); i++) {
            matrizDeActores[i + 1] = coleccionDeActores.get(i).stateAsStringList();
        }

        matrizDeActores[0] = Actor.encabezado;

        return matrizDeActores;
    }

    public void salida() {
        for (int i = 0; i < filmoteca.getNOMBRES_ARCHIVOS_BINARIOS().length; i++) {
            Path p = Rutas.pathToFileInFolderOnDesktop(filmoteca.getNOMBRE_CARPETA(), filmoteca.getNOMBRES_ARCHIVOS_BINARIOS()[i]);
            try {
                FileOutputStream fos = new FileOutputStream(p.toFile());
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                ObjectOutputStream oos = new ObjectOutputStream(bos);
                switch (i) {
                    case 0:
                        oos.writeObject(filmoteca.getColeccionDePeliculas());
                    case 1:
                        oos.writeObject(filmoteca.getColeccionDeDirectores());
                    case 2:
                        oos.writeObject(filmoteca.getColeccionDeActores());
                }
                oos.close();
            } catch (IOException ex) {
                System.err.printf("No fue posible guardar el archivo binario %s", filmoteca.getNOMBRES_ARCHIVOS_BINARIOS()[i]);
            }
        }
    }

}
