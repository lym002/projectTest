package com.jsjf.service.claims;


import com.zcdj.dto.SyncProduct;

public interface BypFinancialService {
    /**
     * 调用接口数据持久化
     * @param data
     * @return
     * @throws Exception
     */
    String addClaimsInfo(String data) throws Exception;

    /**
     * 跑计息时候需要用的服务,放款数据同步
     * @return
     * @throws Exception
     */
    String syncProductInfo(SyncProduct dto) throws Exception;

    /**
     * 跑回款任务,还款同步
     * @return
     * @throws Exception
     */
    String syncRepaymentStatus(String[] param) throws Exception;

}