package org.example;

import org.example.model.Producto;
import org.example.util.Util;

import java.io.FileNotFoundException;
import java.text.Collator;
import java.util.*;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        List<Producto> listaProductos = Util.leeProductos();

        Util.pintaProductos(listaProductos);

        // Ordenamos con el criterio implementado en la clase Producto
        System.out.println("Lista ordenada por nombre producto ascendente");
        Collections.sort(listaProductos);
        Util.pintaProductos(listaProductos);

        // Otra forma es usar un Comparator para establecer el criterio de ordenación
        // Esto nos permite oredenar objetos de una clase que no implementa Comparable
        // En caso de que la clase implemente Comparable, su criterio de ordenación es
        // anulado por el que definamos en el Comparator.

        // Comparator que ordena por unidedades en existencia, ascendente

        // *** Forma 1. Creación de una clase que implementa la interface Comparator

        // Clase anidada. Podemos crearla en un fichero aparte si nos interesa.
        class ComparadorUnidExistencia implements Comparator<Producto>{
            @Override
            public int compare(Producto o1, Producto o2) {
                return o1.getUnidadesEnExistencia() - o2.getUnidadesEnExistencia();
            }
        }

        Comparator<Producto> objComparadorUnidExistencia = new ComparadorUnidExistencia();
        // Ordenación usando el Comparator
        listaProductos.sort(objComparadorUnidExistencia);
        Util.pintaProductos(listaProductos);

        // *** Forma 2. Creación usando una clase anónima.
         Comparator<Producto> ordenPorCantEnStock = new Comparator<>() {
            @Override
            public int compare(Producto o1, Producto o2) {
                return o1.getUnidadesEnExistencia() - o2.getUnidadesEnExistencia();
            }
        };

        // Ordenación usando el Comparator
        listaProductos.sort(ordenPorCantEnStock);
        System.out.println("Ordenados por cantidad en stok ascendente");
        Util.pintaProductos(listaProductos);

        // *** Forma 3. Creación del comparador con clase anónima en la llamada al método sort.
        listaProductos.sort(new Comparator<>() {
            @Override
            public int compare(Producto o1, Producto o2) {
                return o1.getUnidadesEnExistencia() - o2.getUnidadesEnExistencia();
            }
        });

        // *** Forma 4. Utilizando expresiones lambda ya que Comparable es interface funcional
        System.out.println("\nOrdenado por cant en stock de forma descendente");
        listaProductos.sort(( o1, o2) ->   o2.getUnidadesEnExistencia() - o1.getUnidadesEnExistencia() );
        Util.pintaProductos(listaProductos);

        //*** Ordenaciones usando criterios locales. Por ejemplo para el alfabeto español
        // hacer ordenación correcta de la ñ.
        // Si no ordena bien la ñ y otros caracteres deberemos utiliza un Collator
        // Para que ordene bien los textos según alfabeto de la configuración local.

        System.out.println("\nOrdenado por nombre de producto (usando collator");
        Collator collator = Collator.getInstance(); // Recoge configuración local del SO de la máquina.
        // Ordenación por nombre producto usando el Collator
        listaProductos.sort((o1, o2) -> collator.compare(o1.getNombreProducto(), o2.getNombreProducto()));
        Util.pintaProductos(listaProductos);

        // También se puede indicar con un objeto Locale
        // Collator collator = Collator.getInstance(new Locale("es"));
        // Ver. documentación de Collator para ver más opciones.

        // *** Ordenación por criterios "subjetivos" no basada en valor númerico ni alfabético
        // Queremos establecer un orden personalizado para familia.
        // Orden subjetivo de importancia de más importante a menos: cereal, lacteo, carne, verdura
        // Creamos un comparator para ello. Tiene más lógica que los que usado más arriba.

        // Clase interna
        class OrdenFamilia implements Comparator<Producto>{
            // Colección hasMap para asignar a cada familia un peso (grado de importancia)
            Map<String,Integer> familiasOrden;

            // Constructor. Crea hashMap y mete las familias con su peso.
            public OrdenFamilia() {
                familiasOrden = new HashMap<>();
                familiasOrden.put("cereal",1);
                familiasOrden.put("lacteo",2);
                familiasOrden.put("carne",3);
                familiasOrden.put("verdura",4);
            }

            // Implementación del método de la interface Comparator.
            // Criterio de ordenación basado el peso asignado.
            @Override
            public int compare(Producto o1, Producto o2) {
               return familiasOrden.get(o2.getFamilia()) - familiasOrden.get(o1.getFamilia());
            }
        }

        // Ordenamos por familia
        // instancia del Comparador
        Comparator<Producto> ordenFamilia = new OrdenFamilia();
        listaProductos.sort(ordenFamilia);
        System.out.println("\n\nOrdenación por familia ");
        Util.pintaProductos(listaProductos);

        //*** Ejemplo de ordenación por dos atributos
        // Ordenación por familia y cantidad en stock descendente
        // Reutilizando los Comparator que ya teníamos.

        Comparator<Producto> ordenFamiliaCantStock = new Comparator<>() {
            @Override
            public int compare(Producto o1, Producto o2) {
                int orden;
                if ((orden =ordenFamilia.compare(o1, o2) )!= 0) {
                    return orden;
                }
                return ordenPorCantEnStock.compare(o2,o1) ;
            }
        };

        listaProductos.sort(ordenFamiliaCantStock);
        System.out.println("\nOrdenación por Familia y CantidadEnStock");
        Util.pintaProductos(listaProductos);

        // *** Interface Set. Clases que la implementan: HashSet, TreeSet, LinkedHashSet
        // Consultar documentación para ver detalle de cada uno.
        // Todas las clases que implementa interface Set son collecciones que no admiten duplicados.

        // ** Ejemplo uso HashMap.
        // HashMap guarda elementos sin duplicados. No lo hace en ningún orden concreto. Cuando iteramos
        // subre ella para extraer sus elementos estos son dados en un orden no determinado.
        // LinkedHashSet sí extrae elementos en el mismo orden que se introdujeron.

        System.out.println("\nLista de números no repes");
        // Crea arraylist de enteros.
        List<Integer> listaNum = Arrays.asList(45, 1,4,6,1,45,8,4);
       // Crea hashSet de enteros.
        Set<Integer> listaSinRepes = new HashSet<>();

        // Añade los elementos del arrayList al hashSet. Los repetidos simplemente no los añadirá.
        for(Integer n: listaNum)
            listaSinRepes.add(n);

        // Iteramos el hashmap y mostramos sus elementos por consola.
        // Veremos que no números repetidos.
        // OJO: El orden de salida es aleatorio. No se asegura ningún orden concreto.
        for(Integer n: listaSinRepes)
            System.out.println(n);

        // Al constructor se le puede pasar objeto que implemente interface List
        // Descartará los repetidos que haya en el arrayList.
        Set<Integer> listaSinRepes2 = new HashSet<>(listaNum);

        for (Integer n:listaSinRepes2)
            System.out.println(n);


        // ** Ejemplo con TreeSet.
        // TreeSet ESTABLECE UN ORDEN en sus elementos. Utiliza internamente un arbol binario (red black tree)
        // para ordenar los elementos. (Ver doc. para más info)

        // Su constructo tb. admite List como entrada.
        TreeSet<Integer> listaOrdenada = new TreeSet<>(listaNum);
        System.out.println("Elementos ordenados y sin repes");
        for(Integer n: listaOrdenada)
            System.out.println(n);

        // Probar a cambiar el products.csv y poner dos productos con el mismo nombre
        // ¿Qúe ocurre al meter elementos en el treeset?
        listaProductos = Util.leeProductos(); // Lectura del fichero tras el cambio.
        TreeSet<Producto> listOrdProd = new TreeSet<>(listaProductos);

        for(Producto p: listOrdProd)
            System.out.println(p.toString());
    }


}