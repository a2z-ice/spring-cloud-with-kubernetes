package com.ba.cms.admin.gateway.amqp;


import com.ba.cms.admin.config.annotation.JobProcessJWTTokenInjector;
import com.ba.cms.admin.exception.Remote5XXException;
import com.ba.cms.admin.gateway.amqp.message.DataSyncInfoMessage;
import com.ba.cms.admin.gateway.amqp.message.OrganizationMessage;
import com.ba.cms.admin.gateway.service.RemoteDateSyncService;
import com.ba.cms.admin.services.billpay.UtilityBillOrganizationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.ba.cms.admin.gateway.amqp.GatewayRabbitConfig.*;

@Slf4j
@Component
@AllArgsConstructor
public class GatewayDataSyncListener {
    private final AmqpTemplate amqpTemplate;
    private final UtilityBillOrganizationService utilityBillOrgSvc;
    private final RemoteDateSyncService remoteDateSyncService;

    @RabbitListener(queues = SSLWIRELESS_SERVICE_LIST_SYN_QUEUE, id = SSLWIRELESS_SERVICE_LIST_SYN_QUEUE,containerFactory = RETRY_ON_ERROR_SINGLECHANNEL_SINGLEMESSAGE_CONTAINER_FACTORY)
    public void processPerformTransaction(OrganizationMessage message)  {
        log.info("Receive instruction to perform data syn to database : {}",message);
//        utilityBillOrgSvc.saveOrganizationFromAmqpMessage(message);

    }
    @RabbitListener(queues = GATEWAY_SSLWIRLESS_START_SERVICE_LIST_DATA_SYN_QUEUE, id = GATEWAY_SSLWIRLESS_START_SERVICE_LIST_DATA_SYN_QUEUE, containerFactory = RETRY_ON_ERROR_SINGLECHANNEL_SINGLEMESSAGE_CONTAINER_FACTORY)
    public void startDataSync(DataSyncInfoMessage dataSyncInfoMessage) {
        log.info("received for data sync instruction: {} and throwing 5xx exception",dataSyncInfoMessage);
        try {
            log.info("received for data sync instruction: {} and throwing 5xx exception",dataSyncInfoMessage);
            throw new Remote5XXException("remote internal server error exception...");
        } catch (Exception e) {
            log.error("Error occurred while processing message: {}", e.getMessage());
            throw new AmqpRejectAndDontRequeueException(e.getMessage(), e);
        }

//        remoteDateSyncService.syncSSLWirelessServiceList(dataSyncInfoMessage);
    }

    @RabbitListener(queues = GATEWAY_SSLWIRLESS_START_SERVICE_LIST_DATA_SYN_DEADLETTER_QUEUE)
    public void processFailedMessagesRequeue(Message failedMessage) {
        log.info("Received failed message, requeueing: {}", failedMessage.toString());

        log.info("received routing key:{}", failedMessage.getMessageProperties().getReceivedRoutingKey());
        log.info("message :{}", failedMessage);
        long count = Long.valueOf(((Map) ((List) failedMessage.getMessageProperties().getHeaders().get("x-death")).get(0)).get("count")+"");
//        ((Map)((List)failedMessage.getMessageProperties().getHeaders().get("x-death")).get(0)).put("count",count++);
// The following line do infinity number of try do not do this unless you handle how many number of try you want to send the message to parking queue
//        amqpTemplate.send(failedMessage.getMessageProperties().getHeaders().get("x-first-death-queue")+"", failedMessage);

    }


}
