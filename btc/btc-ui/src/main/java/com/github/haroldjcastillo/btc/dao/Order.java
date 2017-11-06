/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.haroldjcastillo.btc.dao;

import java.util.List;
import java.util.Map;

/**
 *
 * @author harold.castillo
 */
public class Order implements Bitso {
    
    private String type;
    private String book;
    private Map<String, List<OrderPayload>> payload;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public Map<String, List<OrderPayload>> getPayload() {
        return payload;
    }

    public void setPayload(Map<String, List<OrderPayload>> payload) {
        this.payload = payload;
    }
    
    
}