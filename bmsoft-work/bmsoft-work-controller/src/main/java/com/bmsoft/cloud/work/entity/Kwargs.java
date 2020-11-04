package com.bmsoft.cloud.work.entity;

import com.bmsoft.cloud.work.entity.scripts.Scenarios;
import com.bmsoft.cloud.work.entity.scripts.Scripts;
import com.bmsoft.cloud.work.enumeration.inventory.TemplateFileType;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Kwargs {
    private Map<String,Object> extravars;
    private Map<String,Object> vars;
    private List<InventoryEntity> groups;
    private TemplateFileType templateFileType;
    private List<HostEntity> hosts;
    private List<SourceEntity> dynamiclist;
    private String filterHost;
    private String tags;
    private String skipTags;
    private Scenarios scenarios;
    private Scripts scripts;
    private Map<String,Object> inParam;
    private Map<String,Object> outParam;
}
