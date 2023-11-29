package org.example;

import org.example.model.Producto;
import org.example.util.Util;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    static List<Producto> products;
    public static void main(String[] args) throws IOException {
        products = Util.leeProductos2();

        todosProductos();
        consultasSimples();
        consultasFiltradas();
        consultasOrdenacion();
        consultasAgrupados();

    }

        private static  void todosProductos() {
            // Creamos el flujo
            // Equivale a selec * from products
            System.out.println("Todos los productos");
            Stream<Producto> s = products.stream(); //flujo de elementos Producto
            s.forEach(product -> System.out.println(product)); // imprime la lista de productos
            // productStream.forEach(System.out::println); esta linea es equivalente a la anterior
            System.out.println();
        }

    private static void consultasSimples() {
        // Consultas Simpples select name from products
        System.out.println("\n\n**Nombre de los productos");
        //Stream<String> s = products.stream().map(Producto::getNombreProducto);
        Stream<String> s = products.stream().map(p -> p.getNombreProducto());
        //s.forEach(System.out::println);
        s.forEach(e-> System.out.println(e));
    }

    private static void consultasFiltradas() {
        // Filtrado
        //   select name from products where units_in_stock < 10
        // Importante el filter va antes del map

        System.out.println("\n\n**Nombre de los productos con unidades en stock menor de 10");
        Stream<String> s = products.stream()
                .filter(e->e.getUnidadesEnExistencia() < 10)
                .map(Producto::getNombreProducto);
        s.forEach(System.out::println);

    }

    private static void consultasOrdenacion() {
        //Orodenacion
        // select name from products where units_in_stock < 1 order by units_in_stock asc
//        El método sorted recibe un Comparator. Ésta misma interfaz Comparator tiene algunos métodos que nos serán de gran ayuda
//
//        comparingInt() Permite comparar elementos de tipo int
//        comparingDouble() Permite comparar elementos de tipo double
//        comparingLong() Permite comparar elementos de tipo long
//        thenComparing() Permite anidar comparaciones. Útil cuándo deseamos ordenar por más de 1 atributo (ejemplo más adelante)
        System.out.println("\n\n** Nombre de los productos con unidades en stock menor de 10 ordenados por unidades en stock ascendente");
        Stream<String> s = products.stream()
                .filter(p->p.getUnidadesEnExistencia() < 10)
                .sorted((o1, o2) -> o1.getUnidadesEnExistencia()-o2.getUnidadesEnExistencia())
                .map(Producto::getNombreProducto); //.map(Producto::toString);
        s.forEach(System.out::println);
        System.out.println();

        // Ordenado de mayor a menor
        System.out.println("\n\n** Nombre de los productos con unidades en stock menor de 10 ordenados por unidades en stock descedente");
        s = products.stream()
                .filter(p -> p.getUnidadesEnExistencia() < 10)
                .sorted((o1, o2) -> o2.getUnidadesEnExistencia() - o1.getUnidadesEnExistencia())
                .map(Producto::getNombreProducto);
        s.forEach(System.out::println);
        System.out.println();

        System.out.println("\n\n** Nombre de los productos con unidades en stock menor de 10 ordenados  por unidad de stock de forma descendente y por nombre de producto de forma descendente");
        s = products.stream()
                .filter(p->p.getUnidadesEnExistencia()<10)
                .sorted(Comparator.comparingInt(Producto::getUnidadesEnExistencia)
                        .reversed()
                        .thenComparing(Producto::getNombreProducto)
                )
                //.map(Producto::getNombreProducto);
                .map(producto -> producto.getUnidadesEnExistencia() + " --- " + producto.getNombreProducto() );
        s.forEach(System.out::println);
        System.out.println();

        System.out.println("\n\n** Nombre de los productos con unidades en stock menor de 10 ordenados  por unidad de stock de forma ascendente y por nombre de producto de forma descendente");
        s = products.stream()
                .filter(p->p.getUnidadesEnExistencia() < 10)
                .sorted(Comparator.comparingInt(Producto::getUnidadesEnExistencia)
                        .thenComparing(Comparator.comparing(Producto::getNombreProducto).reversed())
                )
                .map(Producto::getNombreProducto);
        s.forEach(System.out::println);
        System.out.println();

        System.out.println("\n\n** Nombre de los productos con unidades en stock menor de 10 ordenados  por unidad de stock de forma ascendente y por nombre de producto de forma descendente");
        // select productName, unitsInStock from products where unitsInStock < 10 order by unitsInStock desc, productName asc;
        s = products.stream()
                .filter(p -> p.getUnidadesEnExistencia() < 10)
                //recordar que el método sorted recibe un Comparator.
                .sorted(
                        Comparator
                                .comparing(Producto::getUnidadesEnExistencia) //ordenamos ascendente por unitsInStock
                                .thenComparing( // después ordenamos por otro campo
                                        Collections.reverseOrder( // pero este segundo campo será por orden descendente
                                                Comparator.comparing(Producto::getNombreProducto) // el segundo campo a ordenar
                                        )
                                )
                )
                //.map(Producto::getNombreProducto);
        .map(producto -> producto.getUnidadesEnExistencia() + " --- " + producto.getNombreProducto() );

        s.forEach(System.out::println); //imprime el resultado en consola
        System.out.println();

        // Equivalente

        s = products.stream()
                .filter(p -> p.getUnidadesEnExistencia() < 10)
                //recordar que el método sorted recibe un Comparator.
                .sorted(
                        Comparator
                                .comparing(Producto::getUnidadesEnExistencia) //ordenamos ascendente por unitsInStock
                                .thenComparing( // después ordenamos por otro campo
                                        // pero este segundo campo será por orden descendente
                                                Comparator.comparing(Producto::getNombreProducto).reversed() // el segundo campo a ordenar

                                )
                )
                //.map(Producto::getNombreProducto);
                .map(producto -> producto.getUnidadesEnExistencia() + " --- " + producto.getNombreProducto() );
        System.out.println("Equivalente:");
        s.forEach(System.out::println); //imprime el resultado en consola
        System.out.println();



//        El método sorted()recibe un Comparator
//        La interfaz Comparator nos proporciona algúnos métodos que nos serán útiles para las ordenaciones.
//        Existe una clase Collections que tiene un método reverseOrder() el cual devuelve un Comparator que impone el reverso de una ordenación.
//        Hay que tener cuidado donde se aplican las operaciones como reversos ya que podríamos aplicarlos a toda la colección y no a los campos que deseamos.



    }

    private static void consultasAgrupados() {
        // Agrupado
        //   select name from products where units_in_stock < 10
        // En SQL las operaciones como sum, max, min, avg, group by, partition by, etc., se llaman funciones de agregado.
        // En Java, se especifican en el método collect
        // Select count(1), supplierID from products GROUP BY  supplierID
        System.out.println("Obtener el número de productos agrupados por proveedor");
        Map<Integer, Long> c1 = products.stream()
                .collect( //en el metodo collect se especifican las funciones de agregacion
                        Collectors.groupingBy( // deseamos agrupar
                                Producto::getIdProveedor, // agrupamos por proveedor
                                Collectors.counting() // realizamos el conteo
                        )
                );

        c1.forEach((s, c) -> System.out.printf("proveedor: %s: productos: %s \n", s, c));
        System.out.println();

//        Dado que en el método collect especificamos funciones de agregado, casi siempre nos auxiliaremos de la clase Collectors
//        la cuál nos proporciona varios métodos de funciones de agregado. En este ejemplo, usamos el método groupingBy
//        Si deseamos filtrar todos los productos que en almacen tengan menos de 20 unidades de existencia y agrupados por existencia,

        Map<Integer, List<Producto>> c2 = products.stream()
                .filter(p -> p.getUnidadesEnExistencia() < 20)
                .collect(Collectors.groupingBy(Producto::getUnidadesEnExistencia));

        c2.forEach((unidades, producto) -> System.out.printf("existencias: %s Productos: %s \n", unidades, producto));
    }

    private static void consultasAgregados() {
        // Sumas, medias, etc.
        // Select  unitsInStock, sum(unitPrice) from products GROUP BY unitsInStock
        System.out.println("Obtener la suma del precio unitario de todos los productos agrupados por el número de existencias en el almacén");
        Map<Integer, Double> collect = products.stream()
                .collect( //en el metodo collect se especifican las funciones de agregacion
                        Collectors.groupingBy( // deseamos agrupar
                                Producto::getUnidadesEnExistencia, //agrupamos por existencias en stock
                                Collectors.summingDouble( //el tipo de dato a sumar es double
                                        Producto::getPrecioUnidad //sumamos el precio unitario
                                )
                        )
                );

        collect.forEach((stock, suma) -> System.out.printf("en stock: %s: suma: %s \n", stock, suma));
        System.out.println();
    }

    private static void consultasHaving() {
        // Having, filtros sobre los agregados o agrupamientos
        // Select  unitsInStock, sum(unitPrice) from products GROUP BY unitsInStock HAVING sum(unitPrice) > 100
        System.out.println("Obtener la suma del precio unitario de todos los productos agrupados por el número de existencias en el almacén, pero solo obtener aquellos registros cuya suma sea mayor a 100");
        List<Map.Entry<Integer, Double>> entryList = products.stream()
                .collect( //en el método collect se especifican las funciones de agregación
                        Collectors.groupingBy( // deseamos agrupar
                                Producto::getUnidadesEnExistencia, //agrupamos por existencias en stock
                                Collectors.summingDouble( //sumamos el precio unitario el cual es tipo double
                                        Producto::getPrecioUnidad // agrupamos por proveedor
                                )
                        )
                ).entrySet()
                .stream() //volvemos a generar un stream
                .filter(p -> p.getValue() > 100) //filtramos (simula el having)
                .collect(Collectors.toList());

        entryList.forEach(list -> System.out.printf("en stock: %s, suma: %s\n", list.getKey(), list.getValue()));
        System.out.println();
    }

    private static void consultasOtras() {
        // prromedio de existencias en almacén
        System.out.println("Promedio de existencias en almacén");
        Double average = products.stream()
                .collect(Collectors.averagingInt(Producto::getUnidadesEnExistencia));
        System.out.printf("Promedio de existencias en almacén: %s", average);
        System.out.println();

        // Producto con el precio unitario más alto
        System.out.println("Producto con el precio unitario más alto");
        Optional<Producto> product = products.stream().max(Comparator.comparing(Producto::getPrecioUnidad));
        System.out.println(product.get());
        System.out.println();

        // Podemos obtener el count, sum, min, max y average con una sola operación.
        // Por ejemplo si queremos obtener estas estadísticas respecto al precio unitario
        System.out.println("obtener el count, sum, min, max y average con una sola operación respecto al precio unitario");
        DoubleSummaryStatistics statistics =
                products.stream().collect(Collectors.summarizingDouble(Producto::getPrecioUnidad));
        System.out.println(statistics);
        System.out.println();

        // Limitar el numero de productos devueltos
        System.out.println("Limitar el numero de productos devueltos");
        products.stream().limit(50); // limitamos a 50 productos
        // Saltar hasta el elemento indicado y a partir de ahí devolver todos los elementos
        System.out.println("Saltar hasta el elemento indicado y a partir de ahí devolver todos los elementos");
        Stream<Producto> skip = products.stream().skip(5); //obtenemos los productos a partir del 6 (inclusive)
        skip.forEach(System.out::println);
        System.out.println();
    }



}