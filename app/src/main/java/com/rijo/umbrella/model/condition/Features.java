package com.rijo.umbrella.model.condition;

/**
 * Created by rijogeorge on 10/7/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Features {

    @SerializedName("conditions")
    @Expose
    private Integer conditions;

    public Integer getConditions() {
        return conditions;
    }

    public void setConditions(Integer conditions) {
        this.conditions = conditions;
    }

}
