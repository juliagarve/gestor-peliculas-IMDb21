# IMDb21 - Aplicación de Control de Base de Datos de Películas

IMDb21 es una aplicación desarrollada en Java para controlar una base de datos de películas. La aplicación sigue el patrón Modelo-Vista-Controlador (MVC) y utiliza la biblioteca.jar para diversas funcionalidades.

## Funcionalidades

La aplicación presenta un menú en pantalla con las siguientes opciones y submenús:

### Arranque

Al iniciar, el programa comprueba la existencia de ficheros binarios en la carpeta del escritorio (~/Desktop/IMDB21/). Si no existen, importa ficheros de texto alternativos con formato delimitado por almohadillas (#).

### Salida de programa

Guarda los archivos películas.bin, actores.bin y directores.bin en formato binario en la carpeta IMDB21 del escritorio.

### Opción Archivos

- Exportar directores a un archivo llamado directores.col en formato de columnas.
- Exportar películas a un archivo llamado películas.html en formato de tabla.

### Opción Películas

- Altas: Añadir una película a la colección de películas.
- Bajas: Borrar una película de la colección de películas.
- Modificaciones: Cambiar cualquiera de los atributos de una película, excepto su título y las colecciones de actores y directores.
- Consulta: Mostrar todos los datos de una película buscando por título.

### Opción Directores

- Altas: Añadir un director a la colección de directores.
- Bajas: Borrar un director de la colección de directores.
- Modificaciones: Cambiar cualquiera de los atributos del director, excepto su nombre y la colección de películas.

### Opción Actores

- Altas: Añadir un actor a la colección de actores.
- Bajas: Borrar un actor de la colección de actores.
- Modificaciones: Cambiar cualquiera de los atributos del actor, excepto su nombre y la colección de películas.
- Películas de un actor: Listar las películas interpretadas por un actor.

### Opción Listados

- Imprime la relación de películas ordenadas alfabéticamente por nombre.
- Imprime la relación de directores ordenada por nacionalidad y año de nacimiento.
- Imprime la relación de actores ordenada por año de debut y nombre.

## Arquitectura de la Aplicación

1. Utilización del patrón Modelo-Vista-Controlador (MVC).
2. Todos los ficheros están situados en la carpeta del escritorio llamada "IMDB21".
3. Uso de LocalDate para todas las fechas.
4. Uso de enteros para todos los años.
5. Se desaconseja el uso de Scanner y Console, en su lugar se utiliza Esdia.
6. El programa es multiplataforma y no contiene rutas fijas.
