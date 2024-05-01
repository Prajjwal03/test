package com.smbc.epix.transaction.services.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

public class Result {

    private Object result;
    private List<ResultMessage> resultMessageList;

    public Result() {
        resultMessageList = new ArrayList<>();
    }

    public Result(Object result) {
        this.result = result;
    }

    public void addResultMessage(ResultMessage resultMessage) {
        resultMessageList.add(resultMessage);
    }

    public Object getResult() {
        if (result == null) {
            result = "";
        } else if (List.class.isAssignableFrom(result.getClass())) {
            List resultList = (List) result;
            if (CollectionUtils.isEmpty(resultList)) {
                result = "";
            }
        } else {
            return result;
        }
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public List<ResultMessage> getResultMessageList() {
        if (CollectionUtils.isEmpty(resultMessageList)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(resultMessageList);
    }

    public void setResultMessageList(List<ResultMessage> resultMessageList) {
        this.resultMessageList = new ArrayList<>(resultMessageList);
    }
}
