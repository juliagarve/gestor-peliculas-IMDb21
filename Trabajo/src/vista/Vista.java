/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

/**
 *
 * @author Julia
 */
import com.coti.tools.Esdia;
import com.coti.tools.OpMat;
import controlador.Controlador;
import static java.lang.System.out;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Vista {

    Controlador controlador = new Controlador();

    public void arranque() {
        controlador.arranque();
    }

    public void runMenu(String MENU, String[] opcionesPosibles) {
        boolean salir = false;
        do {
            String opcion = Esdia.readString(MENU, opcionesPosibles).toLowerCase();
            switch (opcion) {
                case "1":
                    runMenuArchivos();
                    break;
                case "2":
                    runMenuPeliculas();
                    break;
                case "3":
                    runMenuDirectores();
                    break;
                case "4":
                    runMenuActores();
                    break;
                case "5":
                    runMenuListados();
                    break;
                case "q":
                    salir = Esdia.yesOrNo("Seguro que quiere salir?");

            }
        } while (!salir);
    }

    private void runMenuArchivos() {
        boolean salir = false;
        final String MENU = "%n1.- Exportar directores a archivo col"
                + "%n2.- Exportar películas a archivo html"
                + "%nq.- Volver a menú principal";
        final String[] opcionesPosibles = {"1", "2", "q"};
        do {
            String opcion = Esdia.readString(MENU, opcionesPosibles).toLowerCase();
            switch (opcion) {
                case "1":
                    exportarDirectoresCol();
                    break;
                case "2":
                    exportarPeliculasHTML();
                    break;
                case "q":
                    salir = Esdia.yesOrNo("Seguro que quiere volver al menú principal?");

            }
        } while (!salir);
    }

    private void exportarDirectoresCol() {
        String mensajeError = controlador.exportarDirectoresCol();
        if (!mensajeError.isEmpty()) {
            System.out.printf(mensajeError);
        }
        int num = controlador.getNumDirectores();
        System.out.printf("%nSe ha creado una tabla con %d directores.%n", num);
        String nombre = controlador.getNombreArchivoCol();
        System.out.printf("EL archivo col se llama %s%n", nombre);
    }

    private void exportarPeliculasHTML() {
        String mensajeError = controlador.exportarPeliculasHTML();
        if (!mensajeError.isEmpty()) {
            System.out.printf(mensajeError);
        }
        int num = controlador.getNumPeliculas();
        System.out.printf("%nSe ha creado una tabla con %d películas.%n", num);
        String nombre = controlador.getNombreArchivoHTML();
        System.out.printf("EL archivo HTML se llama %s%n", nombre);
    }

    private void runMenuPeliculas() {
        boolean salir = false;
        final String MENU = "%n1.- Altas"
                + "%n2.- Bajas"
                + "%n3.- Modificaciones"
                + "%n4.- Consultas"
                + "%nq.- Volver a menú principal";
        final String[] opcionesPosibles = {"1", "2", "3", "4", "q"};
        do {
            String opcion = Esdia.readString(MENU, opcionesPosibles).toLowerCase();
            switch (opcion) {
                case "1":
                    altasPeliculas();
                    break;
                case "2":
                    bajasPeliculas();
                    break;
                case "3":
                    modificacionesPeliculas();
                    break;
                case "4":
                    consultaPeliculas();
                    break;
                case "q":
                    salir = Esdia.yesOrNo("Seguro que quiere volver al menú principal?");

            }
        } while (!salir);
    }

    private void altasPeliculas() {
        System.out.printf("%nIntroduzca a continuación los datos de la película a añadir.%n");
        String titulo = Esdia.readString("Título: ");
        int anio = Esdia.readInt("Año: ");
        while (anio <= 0) {
            System.out.printf("El año no puede ser nulo o negativo. Vuelva a introducirlo.%n");
            anio = Esdia.readInt("Año: ");
        }
        int duracion = Esdia.readInt("Duración: ");
        while (duracion <= 0) {
            System.out.printf("La duración no puede ser nula o negativa. Vuelva a introducirla.%n");
            duracion = Esdia.readInt("Duración: ");
        }
        String pais = Esdia.readString("País: ");
        int numDirectores = Esdia.readInt("Número de directores: ");
        while (numDirectores <= 0) {
            System.out.printf("El número de directores no puede ser nulo o negativo. Vuelva a introducirlo.%n");
            numDirectores = Esdia.readInt("Número de directores: ");
        }
        ArrayList<String> direccion = new ArrayList<>();
        for (int i = 0; i < numDirectores; i++) {
            String temp = Esdia.readString("\tDirector " + (i + 1) + ": ");
            direccion.add(temp);
        }
        String guionista = Esdia.readString("Guionista: ");
        String musica = Esdia.readString("Música: ");
        int numActores = Esdia.readInt("Número de actores:");
        while (numActores <= 0) {
            System.out.printf("El número de actores no puede ser nulo o negativo. Vuelva a introducirlo.%n");
            numActores = Esdia.readInt("Número de actores: ");
        }
        ArrayList<String> reparto = new ArrayList<>();
        for (int i = 0; i < numActores; i++) {
            String temp = Esdia.readString("\tActor " + (i + 1) + ": ");
            reparto.add(temp);
        }
        String productora = Esdia.readString("Productora: ");
        String genero = Esdia.readString("Género: ");
        String sinopsis = Esdia.readString("Sinopsis: ");
        controlador.altaPelicula(titulo, anio, duracion, pais, direccion, guionista, musica, reparto, productora, genero, sinopsis);

    }

    private void bajasPeliculas() {
        String titulo = Esdia.readString("Introduzca el título de la película a eliminar: ");
        controlador.eliminarPelicula(titulo);
        System.out.printf("%nSe ha eliminado la película %s%n", titulo);
    }

    private void modificacionesPeliculas() {
        final String MENU = "%n1.- Año"
                + "%n2.- Duración"
                + "%n3.- País"
                + "%n4.- Guionista"
                + "%n5.- Música"
                + "%n6.- Productora"
                + "%n7.- Género"
                + "%n8.- Sinopsis%n";
        final String[] opcionesPosibles = {"1", "2", "3", "4", "5", "6", "7", "8"};

        String titulo = Esdia.readString("%nIntroduzca el título de la película a modificar: ");
        System.out.printf("Introduzca a continuación el número de la opción que desea modificar.%n%n");
        String opcion = Esdia.readString(MENU, opcionesPosibles).toLowerCase();
        switch (opcion) {
            case "1":
                modificarAnio(titulo);
                break;
            case "2":
                modificarDuracion(titulo);
                break;
            case "3":
                modificarPais(titulo);
                break;
            case "4":
                modificarGuionista(titulo);
                break;
            case "5":
                modificarMusica(titulo);
                break;
            case "6":
                modificarProductora(titulo);
                break;
            case "7":
                modificarGenero(titulo);
                break;
            case "8":
                modificarSinopsis(titulo);
        }

    }

    private void modificarAnio(String titulo) {
        int anio = Esdia.readInt("Introduzca el año: ");
        while (anio <= 0) {
            System.out.printf("El año no puede ser nulo o negativo. Vuelva a introducirlo.%n");
            anio = Esdia.readInt("Año: ");
        }
        String mensajeError = controlador.setAnioPelicula(titulo, anio);
        if (!mensajeError.isEmpty()) {
            System.out.printf(mensajeError);
        }
    }

    private void modificarDuracion(String titulo) {
        int duracion = Esdia.readInt("Introduzca la duración (en minutos): ");
        while (duracion <= 0) {
            System.out.printf("La duración no puede ser nula o negativa. Vuelva a introducirla.%n");
            duracion = Esdia.readInt("Introduzca la duración: ");
        }
        String mensajeError = controlador.setDuracionPelicula(titulo, duracion);
        if (!mensajeError.isEmpty()) {
            System.out.printf(mensajeError);
        }
    }

    private void modificarPais(String titulo) {
        String pais = Esdia.readString("Introduzca el país: ");
        String mensajeError = controlador.setPaisPelicula(titulo, pais);
        if (!mensajeError.isEmpty()) {
            System.out.printf(mensajeError);
        }
    }

    private void modificarGuionista(String titulo) {
        String guionista = Esdia.readString("Introduzca el guionista: ");
        String mensajeError = controlador.setGuionistaPelicula(titulo, guionista);
        if (!mensajeError.isEmpty()) {
            System.out.printf(mensajeError);
        }
    }

    private void modificarMusica(String titulo) {
        String musica = Esdia.readString("Introduzca la persona responsable de la música: ");
        String mensajeError = controlador.setMusicaPelicula(titulo, musica);
        if (!mensajeError.isEmpty()) {
            System.out.printf(mensajeError);
        }
    }

    private void modificarProductora(String titulo) {
        String productora = Esdia.readString("Introduzca la productora: ");
        String mensajeError = controlador.setProductoraPelicula(titulo, productora);
        if (!mensajeError.isEmpty()) {
            System.out.printf(mensajeError);
        }
    }

    private void modificarGenero(String titulo) {
        String genero = Esdia.readString("Introduzca el género: ");
        String mensajeError = controlador.setGeneroPelicula(titulo, genero);
        if (!mensajeError.isEmpty()) {
            System.out.printf(mensajeError);
        }
    }

    private void modificarSinopsis(String titulo) {
        String sinopsis = Esdia.readString("Introduzca la sinopsis: ");
        String mensajeError = controlador.setSinopsisPelicula(titulo, sinopsis);
        if (!mensajeError.isEmpty()) {
            System.out.printf(mensajeError);
        }
    }

    private void consultaPeliculas() {
        String titulo = Esdia.readString("%nIntroduzca el titulo de la película que se quiere consultar: ");
        System.out.printf("%n");
        String[][] DatosPeliculas = controlador.getPelicula(titulo);
        try {
            OpMat.printToScreen3(DatosPeliculas);
        } catch (Exception ex) {
            System.out.printf("No se ha encontrado la película en la base de datos.");
        }
    }

    private void runMenuDirectores() {
        boolean salir = false;
        final String MENU = "%n1.- Altas"
                + "%n2.- Bajas"
                + "%n3.- Modificaciones"
                + "%nq.- Volver a menú principal";
        final String[] opcionesPosibles = {"1", "2", "3", "q"};
        do {
            String opcion = Esdia.readString(MENU, opcionesPosibles).toLowerCase();
            switch (opcion) {
                case "1":
                    altasDirectores();
                    break;
                case "2":
                    bajasDirectores();
                    break;
                case "3":
                    modificacionesDirectores();
                    break;
                case "q":
                    salir = Esdia.yesOrNo("Seguro que quiere volver al menú principal?");

            }
        } while (!salir);
    }

    private void altasDirectores() {
        System.out.printf("%nIntroduzca a continuación los datos del director a añadir.%n");
        String nombre = Esdia.readString("Nombre: ");
        String temp = Esdia.readString("Fecha de nacimiento (AAAA-MM-DD): ");
        boolean flag = false;
        LocalDate fechaNac = null;
        do {
            try {
                fechaNac = LocalDate.parse(temp);
                flag = true;
            } catch (Exception ex) {
                System.out.printf("%nFecha introducida invalida vuelva a introducirla%n");
                temp = Esdia.readString("Fecha de nacimiento (AAAA-MM-DD): ");
            }
        } while (!flag);
        String nacionalidad = Esdia.readString("Nacionalidad: ");
        String ocupacion = Esdia.readString("Ocupación: ");
        int numPeliculas = Esdia.readInt("Número de peliculas del director que se van introducir: ");
        while (numPeliculas <= 0) {
            System.out.printf("El número de películas no puede ser nulo o negativo. Vuelva a introducirlo.%n");
            numPeliculas = Esdia.readInt("Número de peliculas del director que se van introducir: ");
        }
        ArrayList<String> peliculas = new ArrayList<>();
        for (int i = 0; i < numPeliculas; i++) {
            temp = Esdia.readString("\tPelícula " + (i + 1) + ": ");
            peliculas.add(temp);
        }
        controlador.altaDirector(nombre, fechaNac, nacionalidad, ocupacion, peliculas);
        System.out.printf("%nSe ha añadido el director %s a la colección.%n", nombre);
    }

    private void bajasDirectores() {
        String nombre = Esdia.readString("Introduzca el nombre del director a eliminar: ");
        ArrayList<String> peliculas = controlador.eliminarDirector(nombre);
        if (peliculas.isEmpty()) {
            System.out.printf("%nSe ha eliminado al director %s de la colección.%n", nombre);
        } else {
            System.out.printf("%nNo se ha podido eliminar al director %s de la colección porque tiene las siguientes peliculas todavía dadas de alta:%n", nombre);
            for (String pelicula : peliculas) {
                System.out.printf(" - %s%n", pelicula);
            }
        }
    }

    private void modificacionesDirectores() {
        final String MENU = "%n1.- Fecha de nacimiento"
                + "%n2.- Nacionalidad"
                + "%n3.- Ocupación%n";
        final String[] opcionesPosibles = {"1", "2", "3"};

        String nombre = Esdia.readString("%nIntroduzca el nombre del director a modificar: ");
        System.out.printf("Introduzca a continuación el número de la opción que desea modificar.");
        String opcion = Esdia.readString(MENU, opcionesPosibles).toLowerCase();
        switch (opcion) {
            case "1":
                modificarFechaNacDirector(nombre);
                break;
            case "2":
                modificarNacionalidadDirector(nombre);
                break;
            case "3":
                modificarOcupacionDirector(nombre);
                break;
        }
    }

    private void modificarFechaNacDirector(String nombre) {
        String temp = Esdia.readString("Introduzca la fecha de nacimiento (AAAA-MM-DD): ");
        boolean flag = false;
        LocalDate fechaNac = null;
        do {
            try {
                fechaNac = LocalDate.parse(temp);
                flag = true;
            } catch (Exception ex) {
                System.out.printf("%nFecha introducida invalida vuelva a introducirla%n");
                temp = Esdia.readString("Fecha de nacimiento (AAAA-MM-DD): ");
            }
        } while (!flag);
        String mensajeError = controlador.setFechaNacDirector(nombre, fechaNac);
        if (!mensajeError.isEmpty()) {
            System.out.printf(mensajeError);
        }
    }

    private void modificarNacionalidadDirector(String nombre) {
        String nacionalidad = Esdia.readString("Introduzca la nacionalidad: ");
        String mensajeError = controlador.setNacionalidadDirector(nombre, nacionalidad);
        if (!mensajeError.isEmpty()) {
            System.out.printf(mensajeError);
        }
    }

    private void modificarOcupacionDirector(String nombre) {
        String ocupacion = Esdia.readString("Introduzca la ocupacion: ");
        String mensajeError = controlador.setOcupacionDirector(nombre, ocupacion);
        if (!mensajeError.isEmpty()) {
            System.out.printf(mensajeError);
        }
    }

    private void runMenuActores() {
        boolean salir = false;
        final String MENU = "%n1.- Altas"
                + "%n2.- Bajas"
                + "%n3.- Modificaciones"
                + "%n4.- Películas de un actor"
                + "%nq.- Volver a menú principal";
        final String[] opcionesPosibles = {"1", "2", "3", "4", "q"};
        do {
            String opcion = Esdia.readString(MENU, opcionesPosibles).toLowerCase();
            switch (opcion) {
                case "1":
                    altasActores();
                    break;
                case "2":
                    bajasActores();
                    break;
                case "3":
                    modificacionesActores();
                    break;
                case "4":
                    peliculasActores();
                    break;
                case "q":
                    salir = Esdia.yesOrNo("Seguro que quiere volver al menú principal?");

            }
        } while (!salir);
    }

    private void altasActores() {
        System.out.printf("%nIntroduzca a continuación los datos del actor a añadir.%n");
        String nombre = Esdia.readString("Nombre: ");
        String temp = Esdia.readString("Fecha de nacimiento (AAAA-MM-DD): ");
        boolean flag = false;
        LocalDate fechaNac = null;
        do {
            try {
                fechaNac = LocalDate.parse(temp);
                flag = true;
            } catch (Exception ex) {
                System.out.printf("%nFecha introducida invalida vuelva a introducirla%n");
                temp = Esdia.readString("Fecha de nacimiento (AAAA-MM-DD): ");
            }
        } while (!flag);
        String nacionalidad = Esdia.readString("Nacionalidad: ");
        int anio = Esdia.readInt("Año de debut: ");
        while (anio <= 0) {
            System.out.printf("El año no puede ser nulo o negativo. Vuelva a introducirlo.%n");
            anio = Esdia.readInt("Año de debut: ");
        }
        int numPeliculas = Esdia.readInt("Número de peliculas del actor que se van introducir: ");
        while (numPeliculas <= 0) {
            System.out.printf("El número de películas no puede ser nulo o negativo. Vuelva a introducirlo.%n");
            numPeliculas = Esdia.readInt("Número de peliculas del director que se van introducir: ");
        }
        ArrayList<String> peliculas = new ArrayList<>();
        for (int i = 0; i < numPeliculas; i++) {
            temp = Esdia.readString("\tPelícula " + (i + 1) + ": ");
            peliculas.add(temp);
        }
        controlador.altaActor(nombre, fechaNac, nacionalidad, anio, peliculas);
        System.out.printf("%nSe ha añadido el actor %s a la colección.%n", nombre);

    }

    private void bajasActores() {
        String nombre = Esdia.readString("Introduzca el nombre del actor a eliminar: ");
        ArrayList<String> peliculas = controlador.eliminarActor(nombre);
        if (peliculas.isEmpty()) {
            System.out.printf("%nSe ha eliminado al actor %s de la colección.%n", nombre);
        } else {
            System.out.printf("%nNo se ha podido eliminar al actor %s de la colección porque tiene las siguientes peliculas todavía dadas de alta:%n", nombre);
            for (String pelicula : peliculas) {
                System.out.printf(" - %s%n", pelicula);
            }
        }
    }

    private void modificacionesActores() {
        final String MENU = "%n1.- Fecha de nacimiento"
                + "%n2.- Nacionalidad"
                + "%n3.- Año de debut%n";
        final String[] opcionesPosibles = {"1", "2", "3"};

        String nombre = Esdia.readString("%nIntroduzca el nombre del actor a modificar: ");
        System.out.printf("Introduzca a continuación el número de la opción que desea modificar.");
        String opcion = Esdia.readString(MENU, opcionesPosibles).toLowerCase();
        switch (opcion) {
            case "1":
                modificarFechaNacActor(nombre);
                break;
            case "2":
                modificarNacionalidadActor(nombre);
                break;
            case "3":
                modificarAnioActor(nombre);
                break;
        }
    }

    private void modificarFechaNacActor(String nombre) {
        String temp = Esdia.readString("Introduzca la fecha de nacimiento (AAAA-MM-DD): ");
        boolean flag = false;
        LocalDate fechaNac = null;
        do {
            try {
                fechaNac = LocalDate.parse(temp);
                flag = true;
            } catch (Exception ex) {
                System.out.printf("%nFecha introducida invalida vuelva a introducirla%n");
                temp = Esdia.readString("Fecha de nacimiento (AAAA-MM-DD): ");
            }
        } while (!flag);
        String mensajeError = controlador.setFechaNacActor(nombre, fechaNac);
        if (!mensajeError.isEmpty()) {
            System.out.printf(mensajeError);
        }
    }

    private void modificarNacionalidadActor(String nombre) {
        String nacionalidad = Esdia.readString("Introduzca la nacionalidad: ");
        String mensajeError = controlador.setNacionalidadActor(nombre, nacionalidad);
        if (!mensajeError.isEmpty()) {
            System.out.printf(mensajeError);
        }
    }

    private void modificarAnioActor(String nombre) {
        int anio = Esdia.readInt("Introduzca el año de debut: ");
        String mensajeError = controlador.setAnioActor(nombre, anio);
        if (!mensajeError.isEmpty()) {
            System.out.printf(mensajeError);
        }
    }

    private void peliculasActores() {
        String nombre = Esdia.readString("%nIntroduzca el nombre del actor: ");
        String[][] matrizPeliculas = controlador.getPeliculasActor(nombre);
        try {
            OpMat.printToScreen3(matrizPeliculas);
        } catch (Exception ex) {
            System.out.printf("%nError. La colección de películas está vacía.%n");
        }
    }

    private void runMenuListados() {
        boolean salir = false;
        final String MENU = "%n1.- Mostrar relación de películas ordenada alfabéticamente"
                + "%n2.- Mostrar relación de directores ordenada por nacionalidad y año de nacimiento"
                + "%n3.- Mostrar relación de actores ordenada por año de debut y nombre"
                + "%nq.- Volver a menú principal";
        final String[] opcionesPosibles = {"1", "2", "3", "q"};
        do {
            String opcion = Esdia.readString(MENU, opcionesPosibles).toLowerCase();
            switch (opcion) {
                case "1":
                    mostrarPeliculasOrdenadas();
                    break;
                case "2":
                    mostrarDirectoresOrdenados();
                    break;
                case "3":
                    mostrarActoresOrdenados();
                    break;
                case "4":
                    consultaPeliculas();
                    break;
                case "q":
                    salir = Esdia.yesOrNo("Seguro que quiere volver al menú principal?");

            }
        } while (!salir);
    }

    private void mostrarPeliculasOrdenadas() {
        String[][] matrizPeliculas = controlador.getColeccionDePeliculasOrdenadas();
        try {
            OpMat.printToScreen3(matrizPeliculas);
        } catch (Exception ex) {
            System.out.printf("%nError. La colección de películas está vacía.%n");
        }
    }

    private void mostrarDirectoresOrdenados() {
        String[][] matrizDirectores = controlador.getColeccionDeDirectoresOrdenados();
        try {
            OpMat.printToScreen3(matrizDirectores);
        } catch (Exception ex) {
            System.out.printf("%nError. La colección de directores está vacía.%n");
        }
    }

    private void mostrarActoresOrdenados() {
        String[][] matrizActores = controlador.getColeccionDeActoresOrdenados();
        try {
            OpMat.printToScreen3(matrizActores);
        } catch (Exception ex) {
            System.out.printf("%nError. La colección de actores está vacía.%n");
        }
    }

    public void salida() {
        controlador.salida();
    }

}
