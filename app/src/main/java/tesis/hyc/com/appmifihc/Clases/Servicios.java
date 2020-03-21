package tesis.hyc.com.appmifihc.Clases;

public class Servicios {


    public Integer id_mifi_servicios;
    public Integer id_customer;
    public String nombre_servicio;
    public Double monto_servicio;
    public Integer plazo_servicio;
    public String fecha_servicio;
    public Integer num_certificado_servicio;
    public Double monto_restante_servicio;
    public Double monto_mensual_prest;
    public Double tasa_interes_servicio;
    public Integer id_currecy;
    public Integer estado_servicio;
    public Integer codigo_servicio;



    public Servicios() {
    }

    public Servicios(Integer id_mifi_servicios, Integer id_customer, String nombre_servicio, Double monto_servicio, Integer plazo_servicio, String fecha_servicio, Integer num_certificado_servicio, Double monto_restante_servicio, Double monto_mensual_prest, Double tasa_interes_servicio, Integer id_currecy, Integer estado_servicio, Integer codigo_servicio) {
        this.id_mifi_servicios = id_mifi_servicios;
        this.id_customer = id_customer;
        this.nombre_servicio = nombre_servicio;
        this.monto_servicio = monto_servicio;
        this.plazo_servicio = plazo_servicio;
        this.fecha_servicio = fecha_servicio;
        this.num_certificado_servicio = num_certificado_servicio;
        this.monto_restante_servicio = monto_restante_servicio;
        this.monto_mensual_prest = monto_mensual_prest;
        this.tasa_interes_servicio = tasa_interes_servicio;
        this.id_currecy = id_currecy;
        this.estado_servicio = estado_servicio;
        this.codigo_servicio = codigo_servicio;
    }

    public Integer getId_mifi_servicios() {
        return id_mifi_servicios;
    }

    public void setId_mifi_servicios(Integer id_mifi_servicios) {
        this.id_mifi_servicios = id_mifi_servicios;
    }

    public Integer getId_customer() {
        return id_customer;
    }

    public void setId_customer(Integer id_customer) {
        this.id_customer = id_customer;
    }

    public String getNombre_servicio() {
        return nombre_servicio;
    }

    public void setNombre_servicio(String nombre_servicio) {
        this.nombre_servicio = nombre_servicio;
    }

    public Double getMonto_servicio() {
        return monto_servicio;
    }

    public void setMonto_servicio(Double monto_servicio) {
        this.monto_servicio = monto_servicio;
    }

    public Integer getPlazo_servicio() {
        return plazo_servicio;
    }

    public void setPlazo_servicio(Integer plazo_servicio) {
        this.plazo_servicio = plazo_servicio;
    }

    public String getFecha_servicio() {
        return fecha_servicio;
    }

    public void setFecha_servicio(String fecha_servicio) {
        this.fecha_servicio = fecha_servicio;
    }

    public Integer getNum_certificado_servicio() {
        return num_certificado_servicio;
    }

    public void setNum_certificado_servicio(Integer num_certificado_servicio) {
        this.num_certificado_servicio = num_certificado_servicio;
    }

    public Double getMonto_restante_servicio() {
        return monto_restante_servicio;
    }

    public void setMonto_restante_servicio(Double monto_restante_servicio) {
        this.monto_restante_servicio = monto_restante_servicio;
    }

    public Double getMonto_mensual_prest() {
        return monto_mensual_prest;
    }

    public void setMonto_mensual_prest(Double monto_mensual_prest) {
        this.monto_mensual_prest = monto_mensual_prest;
    }

    public Double getTasa_interes_servicio() {
        return tasa_interes_servicio;
    }

    public void setTasa_interes_servicio(Double tasa_interes_servicio) {
        this.tasa_interes_servicio = tasa_interes_servicio;
    }

    public Integer getId_currecy() {
        return id_currecy;
    }

    public void setId_currecy(Integer id_currecy) {
        this.id_currecy = id_currecy;
    }

    public Integer getEstado_servicio() {
        return estado_servicio;
    }

    public void setEstado_servicio(Integer estado_servicio) {
        this.estado_servicio = estado_servicio;
    }

    public Integer getCodigo_servicio() {
        return codigo_servicio;
    }

    public void setCodigo_servicio(Integer codigo_servicio) {
        this.codigo_servicio = codigo_servicio;
    }
}