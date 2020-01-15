package com.wutian.alipayinterface.epay.controller;

import com.wutian.alipayinterface.epay.imp.PayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * PSWController
 *
 * @author ：wutian
 * @date ：Created in 2020/1/15 11:28
 */
@RestController()
@RequestMapping("Psw")
public class PswController {
    private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);

    @RequestMapping(value = "test-zip")
    public void getTestPsw(HttpServletResponse response) {


        String token = "YsyjND1feJ8X0B2dYPi3EbOwRZSUpDbV";
        String codepay_id = "449743";

        String price = "0.5";
        String type = "1";
        //支付人的唯一标识
        String pay_id = "youke";

        String param = "test-zip";
        //通知地址
        String notify_url = "http://localhost:8888/order/notify";
        //支付后同步跳转地址
        String return_url = "http://localhost:8888/Psw/success";

        //参数有中文则需要URL编码
        String url = "https://api.xiuxiu888.com/creat_order?" +
                "id=" + codepay_id +
                "&pay_id=" + pay_id +
                "&price=" + price +
                "&type=" + type +
                "&token=" + token +
                "&param=" + param +
                "&notify_url=" + notify_url +
                "&return_url=" + return_url;
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            e.printStackTrace();
            LOG.error(e.toString());
        }
    }

    /**
     *验证通知 处理自己的业务
     */
    @RequestMapping(value = "success")
    public ModelAndView getTestPsw(HttpServletRequest request) {


        //记得更改 http://codepay.fateqq.com 后台可设置
        String key = "niAvr8O4pRtKrSS0xmFOWvzzqI2FK6Cn";
        //申明hashMap变量储存接收到的参数名用于排序
        Map<String, String> params = new HashMap<>(32);
        //获取请求的全部参数
        Map requestParams = request.getParameterMap();
        //申明字符变量 保存接收到的变量
        String valueStr;
        for (Object o : requestParams.keySet()) {
            String name = (String) o;
            String[] values = (String[]) requestParams.get(name);
            valueStr = values[0];
            //乱码解决，这段代码在出现乱码时使用。如果sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            //增加到params保存
            params.put(name, valueStr);
        }
        //转为数组
        List<String> keys = new ArrayList<>(params.keySet());
        //重新排序
        Collections.sort(keys);
        String prestr = "";
        //获取接收到的sign 参数
        String sign = params.get("sign");

        //遍历拼接url 拼接成a=1&b=2 进行MD5签名
        for (String key_name : keys) {
            String value = params.get(key_name);
            //跳过这些 不签名
            if (value == null || "".equals(value) || "sign".equals(key_name)) {
                continue;
            }
            if ("".equals(prestr)) {
                prestr = key_name + "=" + value;
            } else {
                prestr = prestr + "&" + key_name + "=" + value;
            }
        }
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (md != null) {
            md.update((prestr + key).getBytes());
        }
        String mySign = new BigInteger(1, md != null ? md.digest() : new byte[0]).toString(16).toLowerCase();
        if (mySign.length() != 32) {mySign = "0" + mySign;}
        if (mySign.equals(sign)) {
            //编码要匹配 编码不一致中文会导致加密结果不一致
            //参数合法处理业务
            //流水号
            String pay_no = request.getParameter("pay_no");
            //用户唯一标识
            String pay_id = request.getParameter("pay_id");
            //付款金额
            String money  = request.getParameter("money");
            //提交的金额
            String price = request.getParameter("price");
            LOG.info("success pay for order");

            return new ModelAndView("test-zip");

        } else {
            //参数不合法
            LOG.info("fail pay for order");
        }
        return new ModelAndView("error");
    }
}
