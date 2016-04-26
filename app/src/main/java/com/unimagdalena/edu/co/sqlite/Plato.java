package com.unimagdalena.edu.co.sqlite;

import com.afollestad.inquiry.annotations.Column;

import java.io.Serializable;

public class Plato implements Serializable {

    @Column(name = "_id", primaryKey = true, notNull = true, autoIncrement = true)
    private long id;

    @Column
    private String nombre;

    @Column
    private String descripcion;

    @Column
    private String precio;

    @Column
    private String tipoDePlato;

    @Column
    private String tipoDeComida;

    @Column
    private boolean enPromocion;

    public Plato() {
    }

    public Plato(String nombre, String descripcion, String precio, String tipoDePlato, String tipoDeComida, boolean enPromocion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.tipoDePlato = tipoDePlato;
        this.tipoDeComida = tipoDeComida;
        this.enPromocion = enPromocion;
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

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getTipoDePlato() {
        return tipoDePlato;
    }

    public void setTipoDePlato(String tipoDePlato) {
        this.tipoDePlato = tipoDePlato;
    }

    public String getTipoDeComida() {
        return tipoDeComida;
    }

    public void setTipoDeComida(String tipoDeComida) {
        this.tipoDeComida = tipoDeComida;
    }

    public boolean isEnPromocion() {
        return enPromocion;
    }

    public void setEnPromocion(boolean enPromocion) {
        this.enPromocion = enPromocion;
    }
}
