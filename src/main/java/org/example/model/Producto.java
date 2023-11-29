package org.example.model;
//productID,
// productName,
// supplierID,
// categoryID,
// quantityPerUnit,   (tipo String, corregir)
// unitPrice,
// unitsInStock,
// unitsOnOrder,
// reorderLevel,
// discontinued


// Cración de clase que implementa interface Comparable.
public class Producto implements Comparable<Producto> {
    private int idProducto;
    private String nombreProducto;
    private int idProveedor;
    private int idCategoria;
    private double precioUnidad;
    private int unidadesEnExistencia;
    private String cantPorUnid;
    // Nuevo familia
    private String familia;


    public Producto(int idProducto, String nombreProducto, int idProveedor, int idCategoria, String cantPorUnid, double precioUnidad, int unidadesEnExistencia, String familia) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.idProveedor = idProveedor;
        this.idCategoria = idCategoria;
        this.precioUnidad = precioUnidad;
        this.cantPorUnid = cantPorUnid;
        this.unidadesEnExistencia = unidadesEnExistencia;
        this.familia=familia;
    }


    public String getCantPorUnid() {
        return cantPorUnid;
    }

    public void setCantPorUnid(String cantPorUnid) {
        this.cantPorUnid = cantPorUnid;
    }
    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public double getPrecioUnidad() {
        return precioUnidad;
    }

    public void setPrecioUnidad(double precioUnidad) {
        this.precioUnidad = precioUnidad;
    }

    public int getUnidadesEnExistencia() {
        return unidadesEnExistencia;
    }

    public void setUnidadesEnExistencia(int unidadesEnExistencia) {
        this.unidadesEnExistencia = unidadesEnExistencia;
    }
    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }

    @Override
    public String toString() {
        return String.format("%6d %-35s %6d %6d %7.2f %6d %-20s %-10s",idProducto,nombreProducto, idProveedor,idCategoria,precioUnidad,unidadesEnExistencia,cantPorUnid,familia);

    }
    // Implementación de método abstracto de la interface Comparable
    // Establce orden basado en el nombre del producto.
    @Override
    public int compareTo(Producto o) {
        return getNombreProducto().compareTo(o.getNombreProducto());
    }
    
    public static Producto creaDesdeArray(String[] s){
        String familia[] = {"carne","verdura","lacteo","cereal"};

        return new Producto(
                Integer.parseInt(s[0]), //idProducto
                s[1], //nombreProducto
                Integer.parseInt(s[2]), //idproveedor
                Integer.parseInt(s[3]), // idCat
                s[4], // cantPorUnidad
                Double.parseDouble(s[5]),  //preunid
                Integer.parseInt(s[6]),familia[Integer.parseInt(s[3]) % 4]);
    }
}
