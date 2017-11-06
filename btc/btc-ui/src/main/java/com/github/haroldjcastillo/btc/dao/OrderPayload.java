/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.haroldjcastillo.btc.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.haroldjcastillo.btc.common.ObjectMapperFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author harold.castillo
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderPayload implements Comparable<OrderPayload> {

    private final SimpleStringProperty r;
    private final SimpleStringProperty a;
    private final SimpleStringProperty v;
    private final SimpleStringProperty t;
    private final SimpleStringProperty d;

    public OrderPayload() {
        this.r = new SimpleStringProperty();
        this.a = new SimpleStringProperty();
        this.v = new SimpleStringProperty();
        this.t = new SimpleStringProperty();
        this.d = new SimpleStringProperty();
    }  

    public String getR() {
        return r.getValue();
    }

    public void setR(String r) {
        this.r.setValue(r);
    }

    public String getA() {
        return a.getValue();
    }

    public void setA(String a) {
        this.a.setValue(a);
    }

    public String getV() {
        return v.getValue();
    }

    public void setV(String v) {
        this.v.setValue(v);
    }

    public String getT() {
        return t.getValue();
    }

    public void setT(String t) {
        this.t.setValue(t);
    }

    public String getD() {
        return d.getValue();
    }

    public void setD(String d) {
        this.d.setValue(d);
    }

    @Override
    public int compareTo(OrderPayload orderPayload) {
        return Double.valueOf(this.getV()).compareTo(Double.valueOf(orderPayload.v.getValue()));
    }

    @Override
    public String toString() {
        try {
            return ObjectMapperFactory.objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(OrderPayload.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.toString();
    }
    
    
}
