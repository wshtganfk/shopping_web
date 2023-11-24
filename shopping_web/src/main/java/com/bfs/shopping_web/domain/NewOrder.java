package com.bfs.shopping_web.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

@Data
public class NewOrder implements Serializable {

    private int productId;
    private int quantity;


}

