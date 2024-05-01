package com.smbc.epix.transaction.services.dao;

import java.util.List;

import com.smbc.epix.transaction.services.dto.ReportGenerationDTO;
import com.smbc.epix.transaction.services.model.DynamicTable;

public interface ReportGenerationDAO {

    List<ReportGenerationDTO> getReportDetails(DynamicTable dynamicQuery);
}
