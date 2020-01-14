package com.wutian.alipayinterface.epay.server;

/**
 * PayServiceImpl
 *
 * @author ：wutian
 * @date ：Created in 2020/1/8 17:27
 */
import com.wutian.alipayinterface.epay.imp.PayService;
import com.wutian.alipayinterface.epay.model.Alipay;
import com.wutian.alipayinterface.epay.model.AlipayBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alipay.api.AlipayApiException;
@Service
public class PayServiceImpl implements PayService {

    @Autowired
    private Alipay alipay;

    @Override
    public String aliPay(AlipayBean alipayBean) throws AlipayApiException {
        return alipay.pay(alipayBean);
    }

}