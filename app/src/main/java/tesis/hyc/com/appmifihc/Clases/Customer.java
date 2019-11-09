package tesis.hyc.com.appmifihc.Clases;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

public class Customer extends SugarRecord {


    public Integer idcustomer;
    public String num_document;
    public String nombre_completo;
    public String email;
    public String celular;
    public String direccion;
    public String fecha_nacimiento;

    @Ignore
    public String passwd;

    public Customer() {
    }

    public Customer(Integer idcustomer, String num_document, String nombre_completo, String email, String celular, String direccion, String fecha_nacimiento) {
        this.idcustomer = idcustomer;
        this.num_document = num_document;
        this.nombre_completo = nombre_completo;
        this.email = email;
        this.celular = celular;
        this.direccion = direccion;
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public Integer getIdcustomer() {
        return idcustomer;
    }

    public void setIdcustomer(Integer idcustomer) {
        this.idcustomer = idcustomer;
    }

    public String getNum_document() {
        return num_document;
    }

    public void setNum_document(String num_document) {
        this.num_document = num_document;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }
}