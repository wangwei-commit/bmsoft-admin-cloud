package com.bmsoft.cloud.msgs.service.impl;

import com.bmsoft.cloud.base.service.SuperServiceImpl;
import com.bmsoft.cloud.msgs.dao.MsgsCenterInfoReceiveMapper;
import com.bmsoft.cloud.msgs.entity.MsgsCenterInfoReceive;
import com.bmsoft.cloud.msgs.service.MsgsCenterInfoReceiveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 消息中心 接收表
 * 全量数据
 * </p>
 *
 * @author bmsoft
 * @date 2019-08-01
 */
@Slf4j
@Service
public class MsgsCenterInfoReceiveServiceImpl extends SuperServiceImpl<MsgsCenterInfoReceiveMapper, MsgsCenterInfoReceive> implements MsgsCenterInfoReceiveService {

}
