package com.bmsoft.cloud.file.api.fallback;

import com.bmsoft.cloud.base.R;
import com.bmsoft.cloud.file.api.AttachmentApi;
import com.bmsoft.cloud.file.dto.AttachmentDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * 熔断
 *
 * @author bmsoft
 * @date 2019/07/25
 */
@Component
public class AttachmentApiFallback implements AttachmentApi {
    @Override
    public R<AttachmentDTO> upload(MultipartFile file, Boolean isSingle, Long id, String bizId, String bizType) {
        return R.timeout();
    }
}
