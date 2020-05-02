package com.bmsoft.cloud.sms.service.impl;

import com.bmsoft.cloud.base.service.SuperServiceImpl;
import com.bmsoft.cloud.sms.dao.SmsSendStatusMapper;
import com.bmsoft.cloud.sms.entity.SmsSendStatus;
import com.bmsoft.cloud.sms.service.SmsSendStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 短信发送状态
 * </p>
 *
 * @author bmsoft
 * @date 2019-08-01
 */
@Slf4j
@Service
public class SmsSendStatusServiceImpl extends SuperServiceImpl<SmsSendStatusMapper, SmsSendStatus> implements SmsSendStatusService {

}
