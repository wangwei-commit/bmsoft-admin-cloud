package com.bmsoft.cloud.work.entity;

import lombok.Data;

import java.util.List;

@Data
public class InventoryEntity {


    private String name;
    private List<HostEntity>  hosts;
    private List<InventoryEntity> children;

}
