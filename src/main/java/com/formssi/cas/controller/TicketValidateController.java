package com.formssi.cas.controller;

import com.formssi.cas.util.CasServerUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import com.formssi.cas.entity.Result;

@Controller
@RequestMapping("/sso/ticket")
@Api(value = "凭证校验Controller", tags = "凭证校验服务")
public class TicketValidateController {
    public static final Logger log = LoggerFactory.getLogger(TicketValidateController.class);

    @Autowired
    private CasServerUtil util;


    @ApiOperation(httpMethod = "GET", value = "凭证生成")
    @RequestMapping(value = "/validate", method = { RequestMethod.POST,
            RequestMethod.GET }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result validate(HttpServletRequest request, HttpServletResponse response) {
        String tgt = request.getParameter("ticket");

        // 判断tgt是否为空
        if (StringUtils.isEmpty(tgt)) {
            return Result.error(Result.RESULT_CODE_TGT_NULL, "用户TGT凭证为空 ")
                    .setDatas(Result.RESULT_CODE_NOT_REGISTRY_SESSION + "", "用户TGT凭证为空");
        }

        String st = util.getST(tgt);

        // ST 查询关联用户, 模拟用户登入凭证
        String content = util.getContent(st);
        // 用户信息
        String username = "";
        // 解析
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(content)));
            Element root = doc.getDocumentElement();
            NodeList nodeList = root.getElementsByTagName("cas:user");
            if (nodeList != null) {
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node user = nodeList.item(i);
                    username = user.getTextContent();
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(Result.RESULT_CODE_TGT_ERROR, "用户TGT凭证为空 ")
                    .setDatas(Result.RESULT_CODE_NOT_REGISTRY_SESSION + "", "CAS REST服务异常");

        }
        // 生成jwt 信息 返回前端
        Map<String, Object> paramter = new HashMap<String, Object>();
        if (!StringUtils.isEmpty(username)) {
            paramter.put("username", username);
            paramter.put("tgt", tgt);
        }
        // 生成jwt token
        //String token = JwtTokenUtil.createToken(paramter);
        String token = "dadsdewewewee";
        return Result.ok().setDatas(Result.RESULT_CODE_SUCCESS +"" , "用户登入成功").setDatas("token", token);
    }
}
