package com.smbc.epix.transaction.services.dto.table;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserRoleDTO implements Serializable {

    private static final long serialVersionUID = -2903944589409050726L;

    @JsonProperty("RoleID")
    private int roleId;

    @JsonProperty("UserCategoryName")
    private String categoryName;
}
