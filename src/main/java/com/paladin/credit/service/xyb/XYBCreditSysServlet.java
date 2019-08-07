package com.paladin.credit.service.xyb;

import com.paladin.credit.service.xyb.request.XYBReqCondition;
import com.paladin.credit.service.xyb.response.XYBHttpMessageConverterExtractor;
import com.paladin.framework.core.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

/**
 * @author Huangguochen
 * @create 2019/7/25 9:13
 */
@Component
public class XYBCreditSysServlet{
    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(XYBCreditSysServlet.class);

    @Value("${proxy.enabled}")
    private boolean proxyEnabled;

    private RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        LOGGER.info("<------------初始化restTemplate开始-------------------->");
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(15000);
        factory.setConnectTimeout(15000);

  /*      if (proxyEnabled) {
            SocketAddress address = new InetSocketAddress(proxyHost, proxyPort);
            Proxy proxy = new Proxy(Proxy.Type.HTTP, address);
            factory.setProxy(proxy);
        }*/

        restTemplate = new RestTemplate(factory);
        LOGGER.info("<------------初始化restTemplate结束-------------------->");
    }


    public <T> T getRequest(String url, String[] params, Class<T> responseType) {
        return getRequest(url, params, responseType, 5);
    }



    public <T> T getPostRequest(String url, XYBReqCondition condition, Class<T> responseType) {
        MultiValueMap<String,Object> param = new LinkedMultiValueMap<>();
        param.add("acctount",condition.getAcctount());
        param.add("pwd",condition.getPwd());
        param.add("qymc",condition.getQymc());
        param.add("tyshxydm",condition.getTyshxydm());
        param.add("gszch",condition.getGszch());
        param.add("zzjgdm",condition.getZzjgdm());
        param.add("visitpt",condition.getVisitpt());
        param.add("visitreason",condition.getVisitreason());
        return postFormRequest(url, param, responseType, 3);
    }


    /**
     * 功能描述: <GET 请求>
     * @param url
     * @param params
     * @param responseType
     * @param times
     * @return  T
     * @author  Huangguochen
     * @date  2019/7/25
     */
    private  <T> T getRequest(String url, String[] params, Class<T> responseType, int times) {
        ResponseEntity<T> response;
        try {
            response = restTemplate.getForEntity(url, responseType);
        } catch (Exception e) {
            LOGGER.error("请求信用办联合奖惩系统异常！", e);
            throw new BusinessException("连接信用办联合奖惩系统异常!");
        }

        HttpStatus status = response.getStatusCode();
        if (status == HttpStatus.OK) {
            return response.getBody();
        }else {
            if (times > 0) {
                return getRequest(url, params, responseType, --times);
            } else {
                LOGGER.error("请求多次信用办联合奖惩系统异常，返回状态[" + status + "]");
                throw new BusinessException("连接信用办联合奖惩系统异常!");
            }
        }
    }

    /**
     * 功能描述: <POST Json请求>
     * @param url
     * @param param
     * @param responseType
     * @param times
     * @return  T
     * @author  Huangguochen
     * @date  2019/7/25
     */
    public  <T> T postFormRequest(String url, MultiValueMap<String,Object> param, Class<T> responseType, int times) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<?> entity = new HttpEntity<>(param, headers);

        ResponseEntity<T> response;
        try {
            RequestCallback requestCallback = restTemplate.httpEntityCallback(entity, responseType);
            ResponseExtractor<ResponseEntity<T>> extractor = new XYBHttpMessageConverterExtractor<>(responseType, restTemplate.getMessageConverters());
            response = restTemplate.execute(url, HttpMethod.POST, requestCallback, extractor);
        } catch (Exception e) {
            LOGGER.error("请求信用办联合奖惩系统异常！", e);
            throw new BusinessException("连接信用办联合奖惩系统异常!");
        }

        HttpStatus status = response.getStatusCode();
        if (status == HttpStatus.OK) {
            return response.getBody();
        } else {
            if (times > 0) {
                return postFormRequest(url, param, responseType, --times);
            } else {
                LOGGER.error("请求多次信用办联合奖惩系统异常，返回状态[" + status + "]");
                throw new BusinessException("连接信用办联合奖惩系统异常!");
            }
        }
    }

}
