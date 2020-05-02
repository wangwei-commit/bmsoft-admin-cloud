package com.bmsoft.cloud.file.domain;


import com.bmsoft.cloud.file.entity.File;
import lombok.Data;

/**
 * 文件查询 DO
 *
 * @author bmsoft
 * @date 2019/05/07
 */
@Data
public class FileQueryDO extends File {
    private File parent;

}
