package com.smbc.epix.transaction.services.dao;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smbc.epix.transaction.services.exception.DAOException;
import com.smbc.epix.transaction.services.utils.NGLogger;
import lombok.Setter;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class BaseDAO<E> {

    @Setter
    @Autowired
    @Qualifier("sessionFactory")
    protected SqlSessionFactoryBean sessionFactory;

    @Autowired
    private NGLogger ngLogger;

    /**
     * create new sql session object
     *
     * @return {@link SqlSession} sql session object to execute queries
     */
    protected SqlSession openSqlSession() {
        try {
            if (sessionFactory != null && sessionFactory.getObject() != null) {
                return sessionFactory.getObject().openSession();
            }
        } catch (Exception e) {
            ngLogger.errorLog("Cannot get sql session for the database." + e);
        }
        return null;
    }

    /**
     * close sql session after completing transaction
     *
     * @param sqlSession object to execute queries
     */
    protected void closeSqlSession(SqlSession sqlSession) {
        if (sqlSession != null) {
            sqlSession.close();
        }
    }

    /**
     * Retrieve one record from database based on provided statement.
     *
     * @param statement Sql statement
     * @return BaseVO object.
     * @throws DAOException Throw this exception any error occurred within this method.
     */
    protected E selectOne(String statement) {
        SqlSession session = openSqlSession();

        try {
            return session.selectOne(statement);
        } catch (Exception e) {
            ngLogger.errorLog("Error occurred while selecting record using selectOne function." + e);
            throw new DAOException();
        } finally {
            closeSqlSession(session);
        }
    }

    /**
     * Retrieve one record from database based on provided statement and criteria.
     *
     * @param statement Sql statement
     * @param object    Search criteria
     * @return BaseVO object.
     * @throws DAOException Throw this exception any error occurred within this method.
     */
    protected E selectOne(String statement, Object object) {
        SqlSession session = openSqlSession();

        try {
            return session.selectOne(statement, object);
        } catch (Exception e) {
            ngLogger.errorLog("Error occurred while selecting record using selectOne function." + e);
            throw new DAOException();
        } finally {
            closeSqlSession(session);
        }
    }

    /**
     * Retrieve one or many records from database based on provided statement.
     *
     * @param statement Sql statement
     * @return BaseVO object.
     * @throws DAOException Throw this exception any error occurred within this method.
     */
    protected List<E> selectList(String statement) {
        SqlSession session = openSqlSession();

        try {
            return session.selectList(statement);
        } catch (Exception e) {
            ngLogger.errorLog("Error occurred while selecting records using selectList function." + e);
            throw new DAOException();
        } finally {
            closeSqlSession(session);
        }
    }

    /**
     * Retrieve one or many records from database based on provided statement and
     * parameters.
     *
     * @param statement Sql statement
     * @param object    Search criteria
     * @return BaseVO object.
     * @throws DAOException Throw this exception any error occurred within this method.
     */
    protected List<E> selectList(String statement, Object object) {
        SqlSession session = openSqlSession();

        try {
            return session.selectList(statement, object);
        } catch (Exception e) {
            ngLogger.errorLog("Error occurred while selecting records using selectList function." + e);
            throw new DAOException();
        } finally {
            closeSqlSession(session);
        }
    }

    /**
     * Insert VO object into database based on provided statement.
     *
     * @param statement Sql statement.
     * @param vo        The VO object to be inserted into database.
     * @return Return true if success, otherwise return false.
     * @throws DAOException Throw this exception any error occurred within this method.
     */
    protected Boolean insert(String statement, E vo) {
        SqlSession session = openSqlSession();

        try {
            session.insert(statement, vo);
            return true;
        } catch (Exception e) {
            throw new DAOException(e);
        } finally {
            closeSqlSession(session);
        }
    }

    /**
     * Insert List of object into database based on provided statement.
     *
     * @param statement Sql statement.
     * @param obj       The VO object to be inserted into database.
     * @return Return true if success, otherwise return false.
     * @throws DAOException Throw this exception any error occurred within this method.
     */
    protected Boolean insert(String statement, List<E> obj) {
        SqlSession session = openSqlSession();

        try {
            session.insert(statement, obj);
            return true;
        } catch (Exception e) {
            throw new DAOException(e);
        } finally {
            closeSqlSession(session);
        }
    }

    /**
     * Update VO object based on provided statement.
     *
     * @param statement Sql statement
     * @param vo        Update this object to database.
     * @return Return true if success, otherwise return false.
     * @throws DAOException Throw this exception any error occurred within this method.
     */
    protected Boolean update(String statement, E vo) {
        SqlSession session = openSqlSession();

        try {
            session.update(statement, vo);
            return true;
        } catch (Exception e) {
            throw new DAOException(e);
        } finally {
            closeSqlSession(session);
        }
    }

    /**
     * Delete database record based on provided statement and id.
     *
     * @param statement Sql statement
     * @param id        Delete record based on this id.
     * @return Return true if success, otherwise return false.
     * @throws DAOException Throw this exception any error occurred within this method.
     */
    protected Boolean delete(String statement, Object id) {
        SqlSession session = openSqlSession();

        try {
            session.delete(statement, id);
            return true;
        } catch (Exception e) {
            throw new DAOException(e);
        } finally {
            closeSqlSession(session);
        }
    }

    /**
     * Count record number based on search criteria.
     *
     * @param statement Sql statement
     * @param object    Search criteria
     * @return Total record number
     * @throws DAOException Throw this exception any error occurred within this method.
     */
    protected Long count(String statement, Object object) {
        SqlSession session = openSqlSession();

        try {
            return session.selectOne(statement, object);
        } catch (Exception e) {
            ngLogger.errorLog("Error occurred while getting record count using count function." + e);
            throw new DAOException();
        } finally {
            closeSqlSession(session);
        }
    }

    /**
     * Set DELETE_FLAG = 'Y'
     *
     * @param statement Sql statement
     * @param obj       Update record based on provided criteria.
     * @return Return true if success, otherwise return false.
     * @throws DAOException Throw this exception any error occurred within this method.
     */
    protected Boolean softDelete(String statement, Object obj) {
        SqlSession session = openSqlSession();

        try {
            session.update(statement, obj);
            return true;
        } catch (Exception e) {
            ngLogger.errorLog("Error occurred while deleting record(s) using softDelete function." + e);
            throw new DAOException(e);
        } finally {
            closeSqlSession(session);
        }
    }

    protected String serialize(Object object) {
        ngLogger.consoleLog("BaseDAO:seralize");
        String json = "";
        try {
            ObjectMapper mapper = new ObjectMapper();
            json = mapper.writeValueAsString(object);
        } catch (Exception e) {
            ngLogger.errorLog("Failed to serialize object." + e);
        }

        return json;
    }

    protected List<Long> selectLongList(String statement, Object obj) {
        SqlSession session = openSqlSession();

        try {
            return session.selectList(statement, obj);
        } catch (Exception e) {
            ngLogger.errorLog("Error occurred while selecting list of long records using selectLongList function." + e);
            throw new DAOException();
        } finally {
            closeSqlSession(session);
        }
    }

    protected List<String> selectStringList(String statement, Object obj) {
        SqlSession session = openSqlSession();

        try {
            return session.selectList(statement, obj);
        } catch (Exception e) {
            ngLogger.errorLog("Error occurred while selecting list of string record(s) using selectStringList function." + e);
            throw new DAOException();
        } finally {
            closeSqlSession(session);
        }
    }

    protected List<String> selectStringList(String statement) {
        SqlSession session = openSqlSession();

        try {
            return session.selectList(statement);
        } catch (Exception e) {
            ngLogger.errorLog("Error occurred while selecting list of string record(s) using selectStringList function." + e);
            throw new DAOException();
        } finally {
            closeSqlSession(session);
        }
    }
}
