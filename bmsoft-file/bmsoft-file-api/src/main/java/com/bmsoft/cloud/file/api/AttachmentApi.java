package com.bmsoft.cloud.file.api;


import com.bmsoft.cloud.base.R;
import com.bmsoft.cloud.file.dto.AttachmentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件接口
 *
 * @author bmsoft
 * @date 2019/06/21
 */
@FeignClient(name = "${bmsoft.feign.file-server:bmsoft-file-server}"/*, fallback = AttachmentApiFallback.class*/)
public interface AttachmentApi {

    /**
     * 通过feign-form 实现文件 跨服务上传
     *
     * @param file
     * @param id
     * @param bizId
     * @param bizType
     * @return
     */
    @PostMapping(value = "/attachment/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    R<AttachmentDTO> upload(
            @RequestPart(value = "file") MultipartFile file,
            @RequestParam(value = "isSingle", required = false, defaultValue = "false") Boolean isSingle,
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "bizId", required = false) String bizId,
            @RequestParam(value = "bizType", required = false) String bizType);

}
