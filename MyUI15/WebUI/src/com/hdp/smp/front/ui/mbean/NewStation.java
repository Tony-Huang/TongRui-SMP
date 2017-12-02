package com.hdp.smp.front.ui.mbean;

import com.hdp.smp.front.service.MonitorService;
import com.hdp.smp.front.util.MonitorConnUtil;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;


public class NewStation //extends MBean
{
    public static final Logger log = Logger.getLogger(NewStation.class);
    
    public NewStation() {
        super();
    }

    private Integer NO;
    private String ip;
    private int port;
    private int monitorNO;
    private String description;


    public void setNO(Integer NO) {
        this.NO = NO;
    }

    public Integer getNO() {
        return NO;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void setMonitorNO(int monitorNO) {
        this.monitorNO = monitorNO;
    }

    public int getMonitorNO() {
        return monitorNO;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }


    public static final String msg_key_monitor_test_error = "monitor.test.msg.error";
    public static final String msg_key_monitor_test_success = "monitor.test.msg.success";
    public static final String msg_key_monitor_duplicate = "monitor.id.duplicate.msg.error";
    
    /**
     * 检查机台编号唯一性
     */
    public void checkNO() {
        }

    public void testMonitorConn(ActionEvent actionEvent) {
        log.info("test connection for ip=" + ip + " port=" + port);
        MonitorConnUtil.testConn(ip, port);
//        ModbusComm mdc = new ModbusComm(this.ip, this.port);
//        boolean conn = mdc.canConnect();
//        MBean mb = new MBean();
//        if (!conn) {
//            String errorMsg = mb.getMsg(msg_key_monitor_test_error);
//            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Can not connect", errorMsg);
//            FacesContext.getCurrentInstance().addMessage(null, msg);
//        } else {
//            String okMsg = mb.getMsg(msg_key_monitor_test_success);
//            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Connect ok", okMsg);
//            FacesContext.getCurrentInstance().addMessage(null, msg);
//        }

    }

    public String addStationAction() {
        log.info("******new station, ip=" + ip + " ,port=" + port + " ,monitorID=" + monitorNO);
        MonitorService ms = new MonitorService();
        boolean stNOExist = ms.stNOExist(this.NO);
        if ( stNOExist ) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Duplicate", "机台编号重复!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return null;
            }
        
        boolean monIdExist = ms.monitorIdExist(this.monitorNO);
        if ( monIdExist ) {
            log.info("the monitorId already exist, to show error!!");
            MBean mb = new MBean();
            String errorMsg = mb.getMsg(msg_key_monitor_duplicate);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Duplicate", errorMsg);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
        }

        ms.addMonitor(monitorNO, port, ip, description, this.NO);

        //go to monitor/station list page
        return "back";
    }


}
