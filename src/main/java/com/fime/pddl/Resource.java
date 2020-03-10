package com.fime.pddl;

import lombok.Data;

@Data
public class Resource {

    private String resourceName;
    private boolean resourceFree;
    private int resourceQuantity;

}
