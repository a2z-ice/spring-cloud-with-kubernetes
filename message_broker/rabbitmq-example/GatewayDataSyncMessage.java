package com.ba.cms.admin.gateway.amqp;

import com.ba.cms.admin.dtos.message.MetaDataBean;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GatewayDataSyncMessage  {
    private String dataSyncType;
    private MetaDataBean metaData;

    public GatewayDataSyncMessage(String dataSyncType) {
        this.dataSyncType = dataSyncType;
        this.metaData = MetaDataBean.get();
    }

}
