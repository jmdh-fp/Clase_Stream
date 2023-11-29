package org.example.util;

import org.example.model.Producto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Util {


    // Forma alternativa de leer el fichero Utilizando Files.lines, que devuelve un Stream
    public static List<Producto> leeProductos2() throws IOException {
        List<Producto> listaProductos;
        try(Stream<String> stream = Files.lines(Paths.get("products.csv"))) {

            listaProductos = stream.skip(1L)            // Salta primera línea de los nombres de columna
                    .map(linea -> linea.split(","))  // Hace split de la línea
                    .map(arr -> Producto.creaDesdeArray(arr))  // Crea un objeto Producto con array del split
                    .collect(Collectors.toList());            // Crea una lista con el stream resultante.

        }
        return listaProductos;
    }

    public static List<Producto> leeProductos() throws FileNotFoundException {

        List<Producto> listaProductos = new ArrayList<>();
        Scanner sc = new Scanner(new File("products.csv"));
        sc.nextLine();

        while (sc.hasNextLine()){
            String lineaFich = sc.nextLine();
            String[] linea = lineaFich.split(",");
            listaProductos.add(Producto.creaDesdeArray(linea));
        }

        return listaProductos;

    }

     public static void   pintaProductos(List<Producto> lista){
         System.out.printf("%6s %-35s %6s %6s %7s %6s %-20s %-10s\n","idPro","nombreProducto", "idProv","idCat","preUnid","Stock","cantPorUnid","familia");

         for (Producto p: lista){
            System.out.println(p.toString());
        }
    }



}
