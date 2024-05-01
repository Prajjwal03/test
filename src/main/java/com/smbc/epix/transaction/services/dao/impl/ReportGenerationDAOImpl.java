package com.smbc.epix.transaction.services.dao.impl;

import java.util.List;

import com.smbc.epix.transaction.services.dao.BaseDAO;
import com.smbc.epix.transaction.services.dao.ReportGenerationDAO;
import com.smbc.epix.transaction.services.dto.ReportGenerationDTO;
import com.smbc.epix.transaction.services.model.DynamicTable;
import org.springframework.stereotype.Repository;

@Repository
public class ReportGenerationDAOImpl extends BaseDAO<ReportGenerationDTO> implements ReportGenerationDAO {

    public List<ReportGenerationDTO> getReportDetails(DynamicTable dynamicQuery) {
        return selectList("ReportGeneration.getReportDetails", dynamicQuery);
    }
}
