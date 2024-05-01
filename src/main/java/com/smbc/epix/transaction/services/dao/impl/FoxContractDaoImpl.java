package com.smbc.epix.transaction.services.dao.impl;

import java.util.List;

import com.smbc.epix.transaction.services.dao.BaseDAO;
import com.smbc.epix.transaction.services.dao.foxdb.FoxContractDao;
import com.smbc.epix.transaction.services.dto.table.FoxContractDetailsDTO;
import org.springframework.stereotype.Repository;

@Repository
public class FoxContractDaoImpl extends BaseDAO implements FoxContractDao {

    @Override
    public List<FoxContractDetailsDTO> getLatestVersion(String contractNo) {
        return selectList("com.smbc.epix.transaction.services.dao.foxdb.FoxContractDao.getLatestVersion", contractNo);
    }
}
