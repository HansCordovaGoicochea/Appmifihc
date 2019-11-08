package tesis.hyc.com.appmifihc.Clases;

import java.util.Date;

public class Customer {

    private String idcustomer;
    private String num_document;
    private String nombre_completo;
    private String email;
    private String celular;
    private String direccion;
    private Date fecha_nacimiento;

    public String passwd;

    public Customer() {
    }

    public Customer(String idcustomer, String num_document, String nombre_completo, String email, String celular, String direccion, Date fecha_nacimiento, String passwd) {
        this.idcustomer = idcustomer;
        this.num_document = num_document;
        this.nombre_completo = nombre_completo;
        this.email = email;
        this.celular = celular;
        this.direccion = direccion;
        this.fecha_nacimiento = fecha_nacimiento;
        this.passwd = passwd;
    }

    public String getIdcustomer() {
        return idcustomer;
    }

    public void setIdcustomer(String idcustomer) {
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

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}