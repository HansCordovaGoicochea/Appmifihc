package tesis.hyc.com.appmifihc.Clases;

import com.orm.SugarRecord;

public class PromocionesBanner {

    public Integer id_oferta;
    public String nombre;
    public String descripcion;

    public PromocionesBanner() {
    }

    public PromocionesBanner(Integer id_oferta, String nombre, String descripcion) {
        this.id_oferta = id_oferta;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Integer getId_oferta() {
        return id_oferta;
    }

    public void setId_oferta(Integer id_oferta) {
        this.id_oferta = id_oferta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
