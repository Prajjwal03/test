package com.smbc.epix.transaction.services.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ReportGenerationDTO implements Serializable {

    private static final long serialVersionUID = -3521585983137123101L;

    private String reportId;

    private String reportName;

    private String description;

    private String reportType;
}
