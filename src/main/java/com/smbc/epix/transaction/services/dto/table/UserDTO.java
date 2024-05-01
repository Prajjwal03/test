package com.smbc.epix.transaction.services.dto.table;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 2734498412420163362L;

    @JsonProperty("PersonalName")
    private String personalName;

    @JsonProperty("FamilyName")
    private String familyName;
}
