package com.ct.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVO implements Serializable {
    private Integer id;
    private String name;
    private Float price;
    private String fileName;
}
