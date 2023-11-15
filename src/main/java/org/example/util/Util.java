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
        for (Producto p: lista){
            System.out.println(p.toString());
        }
    }



}
