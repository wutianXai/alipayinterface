package com.wutian.alipayinterface.epay.imp;

/**
 * PayService
 * 支付服务
 * @author ：wutian
 * @date ：Created in 2020/1/8 17:26
 */
import com.alipay.api.AlipayApiException;
import com.wutian.alipayinterface.epay.model.AlipayBean;

public interface PayService {

    /**
     * 支付宝支付接口
     * @param alipayBean
     * @return
     * @throws AlipayApiException
     */
    String aliPay(AlipayBean alipayBean) throws AlipayApiException;

}