package com.hdp.smp.front.ui.mbean;

import com.hdp.smp.front.service.MonitorService;
import com.hdp.smp.front.service.SystemSettingService;
import com.hdp.smp.front.util.DataCache;
import com.hdp.smp.front.util.MonitorConnUtil;
import com.hdp.smp.model.CategoryNameValue;
import com.hdp.smp.model.MaterialNameValue;
import com.hdp.smp.model.Monitor;
import com.hdp.smp.model.ParamSetting;
import com.hdp.smp.model.Station;

import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.nav.RichButton;

import org.apache.log4j.Logger;


public class SystemSetting extends MBean {
    public static final Logger log = Logger.getLogger(SystemSetting.class);

    public SystemSetting() {
        super();
    }


    //=============================================================
    // station mgt
    //=============================================================
    private RichTable stationTable;
    private RichButton okBtn;


    public void setStationTable(RichTable stationTable) {
        this.stationTable = stationTable;
    }

    public RichTable getStationTable() {
        return stationTable;
    }

    public void setOkBtn(RichButton okBtn) {
        this.okBtn = okBtn;
    }

    public RichButton getOkBtn() {
        return okBtn;
    }
    //operations...
    public void updateStation(ActionEvent actionEvent) {
        Monitor mon = (Monitor) stationTable.getSelectedRowData();
        log.info("update selected Monitor=" + mon);
        if (mon == null)
            return;

        MonitorService ms = new MonitorService();
        ms.updateMonitor(mon);
    }

    public void deleteStation(ActionEvent actionEvent) {
        Monitor mon = (Monitor) stationTable.getSelectedRowData();
        log.info("delete selected Monitor=" + mon);
        if (mon == null)
            return;

        MonitorService ms = new MonitorService();
        ms.deleteMonitor(mon);

    }
    
    public void testModbusConn(ActionEvent ae) {
        Monitor mon = (Monitor) stationTable.getSelectedRowData();
        if( mon== null)  return;
        log.info("test modbus connection,selected monitor="+mon);
        MonitorConnUtil.testConn(mon.getIp(), mon.getPort());
                                       
    }


    public static final String msg_key_monitor_test_error = "monitor.test.msg.error";
    public static final String msg_key_monitor_test_success = "monitor.test.msg.success";
    //========================================================
    // craftparam mgt  设备参数管理
    //========================================================

    private float materialParam;
    private float categoryParam;
    private int spindleCount;
    private int sensorRange;
    private float standardTwist;
    private int standardSpeed;
    private int qualityMetrics;
    private int avgSpindleSpeed;
    private int frontRollerDiameter;
    private int middleRollerDiameter;
    private int backRollerDiameter;
    private String stationIDs;

    public void setMaterialParam(float materialParam) {
        this.materialParam = materialParam;
    }

    public float getMaterialParam() {
        return materialParam;
    }

    public void setCategoryParam(float categoryParam) {
        this.categoryParam = categoryParam;
    }

    public float getCategoryParam() {
        return categoryParam;
    }

    public void setSpindleCount(int spindleCount) {
        this.spindleCount = spindleCount;
    }

    public int getSpindleCount() {
        return spindleCount;
    }

    public void setSensorRange(int sensorRange) {
        this.sensorRange = sensorRange;
    }

    public int getSensorRange() {
        return sensorRange;
    }

    public void setStandardTwist(float standardTwist) {
        this.standardTwist = standardTwist;
    }

    public float getStandardTwist() {
        return standardTwist;
    }

    public void setStandardSpeed(int standardSpeed) {
        this.standardSpeed = standardSpeed;
    }

    public int getStandardSpeed() {
        return standardSpeed;
    }

    public void setQualityMetrics(int qualityMetrics) {
        this.qualityMetrics = qualityMetrics;
    }

    public int getQualityMetrics() {
        return qualityMetrics;
    }

    public void setAvgSpindleSpeed(int avgSpindleSpeed) {
        this.avgSpindleSpeed = avgSpindleSpeed;
    }

    public int getAvgSpindleSpeed() {
        return avgSpindleSpeed;
    }

    public void setFrontRollerDiameter(int frontRollerDiameter) {
        this.frontRollerDiameter = frontRollerDiameter;
    }

    public int getFrontRollerDiameter() {
        return frontRollerDiameter;
    }

    public void setMiddleRollerDiameter(int middleRollerDiameter) {
        this.middleRollerDiameter = middleRollerDiameter;
    }

    public int getMiddleRollerDiameter() {
        return middleRollerDiameter;
    }

    public void setBackRollerDiameter(int backRollerDiameter) {
        this.backRollerDiameter = backRollerDiameter;
    }

    public int getBackRollerDiameter() {
        return backRollerDiameter;
    }

    public void setStationIDs(String stationIDs) {
        this.stationIDs = stationIDs;
    }

    public String getStationIDs() {
        return stationIDs;
    }


    public static final String msg_key_write_craftparam_error = "craft.param.save.msg.error";

    public String writeCraftParams2Monitor() {
        try {
            //write the params to monitors
            SystemSettingService sss = new SystemSettingService();
            ParamSetting ps = new ParamSetting();
            //ps.setAvgSpindleSpeed(avgSpindleSpeed);
            ps.setCategoryParam(categoryParam);
            ps.setBackRollerDiameter(backRollerDiameter);
            ps.setFrontRollerDiameter(frontRollerDiameter);
            ps.setMiddleRollerDiameter(middleRollerDiameter);
            ps.setMaterialParam(materialParam);
            //ps.setQualityMetrics(qualityMetrics);
            ps.setSensorRange(sensorRange);
            ps.setSpindleCount(spindleCount);
            ps.setStandardSpeed(standardSpeed);
            ps.setStandardTwist(standardTwist);
            //ps.set
            sss.writeParams2Monitors(ps);
            //导航到主页面
            MBean mb = new MBean();
            mb.showInfo("Success", mb.getMsg("craft.param.save.msg.success"));
            return "back";
        } catch (Throwable th) {
            log.error(th);
            String errorMsg = this.getMsg(msg_key_write_craftparam_error);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Can not connect", errorMsg);
            FacesContext.getCurrentInstance().addMessage(null, msg);

            return  "back";
        }


    }

    //==========================================
    // Category name mapping mgt
    //===========================================
    private RichTable catMappingTable;
    private String catName;
    private float catValue;
    //private Date updateDate;
    
    private List<Station> selectedStations;


    public void setSelectedStations(List<Station> selectedStations) {
        this.selectedStations = selectedStations;
    }

    public List<Station> getSelectedStations() {
        return selectedStations;
    }

    public List<SelectItem> getSelectionStations(){
       return  DataCache.getSelectionStations();
        }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatValue(float catValue) {
        this.catValue = catValue;
    }

    public float getCatValue() {
        return catValue;
    }


    public void setCatMappingTable(RichTable catMappingTable) {
        this.catMappingTable = catMappingTable;
    }

    public RichTable getCatMappingTable() {
        return catMappingTable;
    }

    public String addCatAction() {
        log.info("......to save the category name value mapping......");
        CategoryNameValue cnv = new CategoryNameValue(this.catName,this.catValue);
        cnv.setStartDate(new Date());
        SystemSettingService sss = new SystemSettingService();
        sss.addCatName2(cnv,this.getSelectedStations());
        return "back";
    }

    public void updateCat(ActionEvent ae) {
        log.info("......to update the category name value mapping......");
        if ( this.catMappingTable.getSelectedRowData() ==null) {
            return;
            }
        Station cnv = (Station) this.catMappingTable.getSelectedRowData();
        log.info("......selected Category=" + cnv.getCat());
        SystemSettingService sss = new SystemSettingService();
        CategoryNameValue cat = cnv.getCat();
        cat.setUpdateDateTup(new Date());//record the time that user modify the name on UI.
        cat.setTakeEffect(true);
        sss.updateCatName(cat);
    }

    public void deleteCat(ActionEvent ae) {
        log.info("......to delete the category name value mapping......");
        if ( this.catMappingTable.getSelectedRowData() ==null) {
            return;
            }
        Station st = (Station) this.catMappingTable.getSelectedRowData();
        log.info("......selected Category=" + st.getCat());
        SystemSettingService sss = new SystemSettingService();
        sss.deleteCatName2(st.getCat());
    }


    //===============================================
    //material name mapping mgt
    //===============================================
    private RichTable matMappingTable;
    private String matName;
    private int matValue;

    public void setMatMappingTable(RichTable matMappingTable) {
        this.matMappingTable = matMappingTable;
    }

    public RichTable getMatMappingTable() {
        return matMappingTable;
    }


    public void setMatName(String matName) {
        this.matName = matName;
    }

    public String getMatName() {
        return matName;
    }

    public void setMatValue(int matValue) {
        this.matValue = matValue;
    }

    public int getMatValue() {
        return matValue;
    }


    public String addMatAction() {
        log.info("......to save the material name value mapping......");
        MaterialNameValue mnv = new MaterialNameValue(this.matName,this.matValue);
        SystemSettingService sss = new SystemSettingService();
        sss.addMatName(mnv);
        
        return "back";
    }

    public void updateMat(ActionEvent ae) {
        log.info("......to update the material name value mapping......");
        MaterialNameValue mnv = (MaterialNameValue)this.matMappingTable.getSelectedRowData();
        log.info("......selected Material="+mnv);
        SystemSettingService sss = new SystemSettingService();
        sss.updateMatName(mnv);
    }

    public void deleteMat(ActionEvent ae) {
        log.info("......to delete the material name value mapping......");
        MaterialNameValue mnv = (MaterialNameValue)this.matMappingTable.getSelectedRowData();
        log.info("......selected Material="+mnv);
        SystemSettingService sss = new SystemSettingService();
        sss.deleteMatName(mnv);
        
    }
    
    //===================================================
    // data acauisition frequency
    //===================================================
    
    private int frequency;


    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getFrequency() {
        return frequency;
    }
    
    //update evey job of monitor data reading frequency
    public void updateJobFrequency(ActionEvent ae) {
           log.info("============update frequency as :"+this.getFrequency() +" ===========");
            MonitorService ms = new MonitorService();
            ms.updateSchedulerFrequency(this.getFrequency());
        }
}
