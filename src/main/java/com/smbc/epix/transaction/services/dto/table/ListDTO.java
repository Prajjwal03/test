package com.smbc.epix.transaction.services.dto.table;

import java.io.Serializable;

import lombok.Data;

@Data
public class ListDTO implements Serializable {

    private static final long serialVersionUID = 8088299633711992521L;

    private int id;

    private String list;
}

