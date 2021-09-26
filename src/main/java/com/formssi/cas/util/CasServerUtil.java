package com.formssi.cas.util;



import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.formssi.cas.config.CasServerProperties;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component("casServerUtil")
public class CasServerUtil {
    @Autowired
    private CasServerProperties properties;

    /**
     * 功能概述:验证用户提供的oauth2.0 的accessToken 是否合法
     * @param accessToken
     * @return
     */
    public String validateAccessToken(String accessToken){
        Map<String, Object> map = new HashMap<>();
        map.put("access_token", accessToken.trim());
        return HTTPUtil.doGet(this.properties.getAccessTokenPath(), map);
    }

    /**
     * 功能概述:获取到 （Tokent generate tiker ,token生成票据）tgt
     * @return
     */
    public String getTGT(String username,String password){
        String tgt = "";
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("username", URLEncoder.encode(username, "UTF-8"));
            map.put("password", URLEncoder.encode(password, "UTF-8"));
            String html = HTTPUtil.doPost(this.properties.getTicketPath(), map);

            Document document = Jsoup.parse(html);
            Elements elements = document.select("form");
            tgt = elements.attr("action");

            if (tgt != null) {
                tgt = tgt.substring(tgt.lastIndexOf("/") + 1);
            }
        } catch (Exception e) {
            return "";
        }
        return tgt;
    }



    /**
     * 功能概述:根据票据生成工具，获取st
     * @param tgt
     * @return
     */
    public String getST(String tgt){
        String serviceTicket = "";
        try {
            //获取返回的ticket票据
            Map<String, Object> map = new HashMap<>();
            map.put("service", this.properties.getDomain());
            serviceTicket = HTTPUtil.doPost(this.properties.getTicketPath()+"/"+tgt, map);
        } catch (Exception e) {
            return "";
        }
        return serviceTicket;
    }

    /**
     * 功能概述:验证st的合法性
     * @param tgt
     * @return
     */
    public boolean validateST(String st){
        //获取返回的ticket票据
        Map<String, Object> map = new HashMap<>();
        map.put("service", this.properties.getDomain());
        map.put("ticket", st);

        String html = HTTPUtil.doGet(this.properties.getValidatePath(), map);
        if(StringUtils.isEmpty(html)){
            return false;
        }
        return html.contains("<cas:authenticationSuccess>");
    }

    /**
     * 功能概述:验证st的合法性
     * @param tgt
     * @return
     */
    public String getContent(String st){
        //获取返回的ticket票据
        Map<String, Object> map = new HashMap<>();
        map.put("service", this.properties.getDomain());
        map.put("ticket", st);

        return HTTPUtil.doGet(this.properties.getValidatePath(), map);
    }

    public String deleteTGT(String tgt){
        Map<String, Object> map = new HashMap<>();
        map.put("ticket", tgt);
        return HTTPUtil.doDelete(this.properties.getTicketPath(), map);
    }


    /**
     * 机能概要: 先通过用户名密码，
     * 先生成tikect的 token，然后再通过token获取到id
     * @param args
     * @throws Exception
     */
    public static void main(String [] args) throws Exception {


//       String username ="admin";
//       String password ="123456";
//
//
//       CasServerUtil utils = CasServerUtil.getInstance();
//       String tgt = utils.getTGT(username, password);
//       System.out.println("用户登入凭证:" + tgt);
//       String st = utils.getSt(tgt);
//       System.out.println("授权凭证:" + st);
//
//       boolean target = utils.validateST(st);
//       System.out.println("是否有效授权凭证:" + target);


    }




}
