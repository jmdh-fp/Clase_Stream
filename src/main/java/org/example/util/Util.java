package org.example.util;

import org.example.model.Producto;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Util {

    public static List<Producto> leeProductos() throws FileNotFoundException {
        String familia[] = {"carne","verdura","lacteo","cereal"};
        List<Producto> listaProductos = new ArrayList<>();
        Scanner sc = new Scanner(new File("products.csv"));
        sc.nextLine();

        while (sc.hasNextLine()){
            String lineaFich = sc.nextLine();
            String[] linea = lineaFich.split(",");
            String tipoFamilia = familia[Integer.parseInt(linea[3])%4];
            Producto producto = new Producto(
                    Integer.parseInt(linea[0]), //idProducto
                    linea[1], //nombreProducto
                    Integer.parseInt(linea[2]), //idproveedor
                    Integer.parseInt(linea[3]), // idCat
                    linea[4], // cantPorUnidad
                    Double.parseDouble(linea[5]),  //preunid
                    Integer.parseInt(linea[6]),tipoFamilia); // UnidExist
            listaProductos.add(producto);
        }

        return listaProductos;

    }

     public static void   pintaProductos(List<Producto> lista){
         //return String.format("%6d %-35s %4d %4d %6.2f %4d %-20s %-10s",idProducto,nombreProducto, idProveedor,idCategoria,precioUnidad,unidadesEnExistencia,cantPorUnid,familia);

         System.out.printf("%6s %-35s %6s %6s %7s %6s %-20s %-10s\n","idPro","nombreProducto", "idProv","idCat","preUnid","Stock","cantPorUnid","familia");

         for (Producto p: lista){
            System.out.println(p.toString());
        }
    }



}
