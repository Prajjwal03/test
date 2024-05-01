package com.smbc.epix.transaction.services.dto.table;

import java.io.Serializable;

import lombok.Data;

@Data
public class ListApplicationDTO implements Serializable {

    private static final long serialVersionUID = 8818719603474528808L;

    private String application;

    private String applicationLink;
}