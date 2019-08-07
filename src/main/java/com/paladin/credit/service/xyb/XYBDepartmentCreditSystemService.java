package com.paladin.credit.service.xyb;

import com.paladin.credit.service.xyb.request.XYBReqCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * <信用办联合惩戒系统接口对接>
 *
 * @author Huangguochen
 * @create 2019/7/25 10:29
 */
@Component
public class XYBDepartmentCreditSystemService {

    private  final  XYBCreditSysServlet xybCreditSysServlet;

    @Autowired
    public XYBDepartmentCreditSystemService(XYBCreditSysServlet xybCreditSysServlet) {
      this.xybCreditSysServlet = xybCreditSysServlet;
    }


    @Value("${xyb.org.url}")
    private String orgInfoUrl;

    @Value("${xyb.black.url}")
    private String blackInfoUrl;

    @Value("${xyb.xzcf.url}")
    private String xzcfInfoUrl;

    @Value("${xyb.red.url}")
    private String redInfoUrl;

    @Value("${xyb.hypd.url}")
    private String hypdInfoUrl;

    public Map getRedInfo(XYBReqCondition condition) {
        return xybCreditSysServlet.getPostRequest(redInfoUrl,condition,Map.class);
    }

    public Map getBlackInfo(XYBReqCondition condition) {
        return xybCreditSysServlet.getPostRequest(blackInfoUrl,condition,Map.class);
    }

    public Map getXzcfInfo(XYBReqCondition condition) {
        return xybCreditSysServlet.getPostRequest(xzcfInfoUrl,condition,Map.class);
    }

    public Map getHypdInfo(XYBReqCondition condition) {
        return xybCreditSysServlet.getPostRequest(hypdInfoUrl,condition,Map.class);
    }

}
