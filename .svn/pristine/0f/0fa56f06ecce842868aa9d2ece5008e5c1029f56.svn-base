package com.twkj.controllers;

import com.twkj.helper.DownloadAct;
import com.twkj.helper.JacksonUtil;
import com.twkj.service.FinanceService;
import com.twkj.service.PersonnelMattersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author: xianlehuang
 * @Description:财务
 * @date: 2019/12/12 - 16:48
 */
@Controller
@RequestMapping("/finance")
public class FinanceAction {

    @Autowired
    private FinanceService financeService;
    private DownloadAct downloadAct = new DownloadAct();
    private JacksonUtil jacksonUtil = JacksonUtil.buildNormalBinder();

    //出差申请查询
    @RequestMapping("/findBusinessTravelApplication")
    @ResponseBody
    public String findBusinessTravelApplication(HttpServletRequest request){
        String msg = financeService.findBusinessTravelApplication(request);
        return msg;
    }

    //出差申请导出
    @RequestMapping("/findBusinessTravelApplicationExcel")
    @ResponseBody
    public String findBusinessTravelApplicationExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String a[] = {"姓名","申请日期","部门","事由","日期","地点","是否支付","支付费用","备注","总经理","财务负责人","部门负责人"};//导出列明
        String b[] = {"APPLY_NAME","APPLY_TIME","DEPARTMENT","REASON","DATE_TIME","ADDRESS","IS_ADVANCE","COST","REMARKS","GENERAL_MANAGER","FINANCIAL_DIRECTOR","DEPARTMENT_HEAD"};//导出map中的key
        String gzb = "出差申请";//导出sheet名和导出的文件名
        String msg = financeService.findBusinessTravelApplication(request);
        List<Map<String, Object>> list = DownloadAct.parseJSON2List2(msg);
        downloadAct.download(request,response,a,b,gzb,list);
        return null;
    }

    //出差申请添加
    @RequestMapping("/addBusinessTravelApplication")
    @ResponseBody
    public String addBusinessTravelApplication(HttpServletRequest request){
        return financeService.addBusinessTravelApplication(request);
    }

    //出差申请审批
    @RequestMapping("/auditBusinessTravelApplication")
    @ResponseBody
    public String auditBusinessTravelApplication(HttpServletRequest request){
        return financeService.auditBusinessTravelApplication(request);
    }

    //出差申请修改
    @RequestMapping("/updateBusinessTravelApplication")
    @ResponseBody
    public String updateBusinessTravelApplication(HttpServletRequest request){
        return financeService.updateBusinessTravelApplication(request);
    }

    //出差申请删除
    @RequestMapping("/deleteBusinessTravelApplication")
    @ResponseBody
    public String deleteBusinessTravelApplication(HttpServletRequest request){
        return financeService.deleteBusinessTravelApplication(request);
    }

    //合同签订查询
    @RequestMapping("/findContractSign")
    @ResponseBody
    public String findContractSign(HttpServletRequest request){
        String msg = financeService.findContractSign(request);
        return msg;
    }

    //合同签订导出
    @RequestMapping("/findContractSignExcel")
    @ResponseBody
    public String findContractSignExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String a[] = {"合同编号","合同名称","类型","内容摘要","签约方名称","签约方联系人","签约方联系电话","签约方地址","签约方法人代表","签约方传真","合同金额","拟签订日期","申请部门","申请人","申请时间","部门经理","财务部","总经理"};//导出列明
        String b[] = {"CONTRACT_NUMBER","CONTRACT_NAME","CONTRACT_TYPE","CONTENT_ABSTRACT","CONTRACT_PARTY_NAME","CONTRACT_PARTY_CONTACTS","CONTRACT_PARTY_PHONE","CONTRACT_PARTY_ADDRESS","CONTRACT_PARTY_LEGALNAME","CONTRACT_PARTY_FAX","CONTRACT_AMOUNT","SIGN_TIME","APPLICATION_DEPARTMENT","APPLICATION_PERSON","APPLICATION_TIME","DEPARTMENT_SIGNATURE","FINANCE_SIGNATURE","GENERAL_MANAGER_SIGNATURE"};//导出map中的key
        String gzb = "合同签订";//导出sheet名和导出的文件名
        String msg = financeService.findContractSign(request);
        List<Map<String, Object>> list = DownloadAct.parseJSON2List2(msg);
        downloadAct.download(request,response,a,b,gzb,list);
        return null;
    }

    //合同签订添加
    @RequestMapping("/addContractSign")
    @ResponseBody
    public String addContractSign(HttpServletRequest request){
        return financeService.addContractSign(request);
    }
    //合同签订审批
    @RequestMapping("/auditContractSign")
    @ResponseBody
    public String auditContractSign(HttpServletRequest request){
        return financeService.auditContractSign(request);
    }

    //合同签订修改
    @RequestMapping("/updateContractSign")
    @ResponseBody
    public String updateContractSign(HttpServletRequest request){
        return financeService.updateContractSign(request);
    }

    //合同签订删除
    @RequestMapping("/deleteContractSign")
    @ResponseBody
    public String deleteContractSign(HttpServletRequest request){
        return financeService.deleteContractSign(request);
    }

    //开票申请查询
    @RequestMapping("/findInvoiceApplication")
    @ResponseBody
    public String findInvoiceApplication(HttpServletRequest request){
        String msg = financeService.findInvoiceApplication(request);
        return msg;
    }

    //开票申请导出
    @RequestMapping("/findInvoiceApplicationExcel")
    @ResponseBody
    public String findInvoiceApplicationExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String a[] = {"单位名称","开票内容","开票金额","发票类型","银行账号及开户行","地址、电话","姓名","联系方式","邮寄地址","税号"};//导出列明
        String b[] = {"UNIT_NAME","INVOICE_CONTENTS","INVOICE_AMOUNT","INVOICE_TYPE","OPEN_BANK","ADDRESS","NAME","CONTACT_INFORMATION","MAILING_ADDRESS","DUTY_PARAGRAPH"};//导出map中的key
        String gzb = "开票申请";//导出sheet名和导出的文件名
        String msg = financeService.findInvoiceApplication(request);
        List<Map<String, Object>> list = DownloadAct.parseJSON2List2(msg);
        downloadAct.download(request,response,a,b,gzb,list);
        return null;
    }


    //开票申请添加
    @RequestMapping("/addInvoiceApplication")
    @ResponseBody
    public String addInvoiceApplication(HttpServletRequest request){
        return financeService.addInvoiceApplication(request);
    }
    //开票申请修改
    @RequestMapping("/updateInvoiceApplication")
    @ResponseBody
    public String updateInvoiceApplication(HttpServletRequest request){
        return financeService.updateInvoiceApplication(request);
    }

    //开票申请删除
    @RequestMapping("/deleteInvoiceApplication")
    @ResponseBody
    public String deleteInvoiceApplication(HttpServletRequest request){
        return financeService.deleteInvoiceApplication(request);
    }
}
