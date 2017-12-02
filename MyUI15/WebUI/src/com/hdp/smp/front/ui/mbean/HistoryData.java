package com.hdp.smp.front.ui.mbean;

import com.hdp.smp.front.service.HistoryDataService;
import com.hdp.smp.front.ui.model.StationByCat;
import com.hdp.smp.front.util.DataCache;
import com.hdp.smp.front.util.FormatUtil;
import com.hdp.smp.front.util.LangSetting;
import com.hdp.smp.front.util.MBeanUtil;
import com.hdp.smp.model.MaterialNameValue;
import com.hdp.smp.model.Shift;
import com.hdp.smp.model.Station;
import com.hdp.smp.model.StatisData;
import com.hdp.smp.persistence.HibernateUtil;
import com.hdp.smp.persistence.StationDAO;
import com.hdp.smp.persistence.StatisDAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.apache.myfaces.trinidad.event.DisclosureEvent;

import org.hibernate.Session;

public class HistoryData {
    public HistoryData() {
        super();
    }

    public static final Logger log = Logger.getLogger(HistoryData.class);

    //=============================================
    //工艺品种查机台...
    //=============================================
    private Date start;
    private Date end;
    private MaterialNameValue selectedMat;
    private Integer branch;
    private Float twist;

    private List<StationByCat> data_sbc = null;

    public List<SelectItem> getSelectionMats() {
        return DataCache.getSelectionMats();
    }


    public void setStart(Date start) {
        this.start = start;
    }

    public Date getStart() {
        return start;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Date getEnd() {
        return end;
    }

    public void setSelectedMat(MaterialNameValue selectedMat) {
        this.selectedMat = selectedMat;
    }

    public MaterialNameValue getSelectedMat() {
        return selectedMat;
    }

    public void setBranch(Integer branch) {
        this.branch = branch;
    }

    public Integer getBranch() {
        return branch;
    }

    public void setTwist(Float twist) {
        this.twist = twist;
    }

    public Float getTwist() {
        return twist;
    }
    
    public String getDisplayCraftName () {
        log.info ("---get craftName , selectedMat="+this.selectedMat +" branch="+this.branch  +"  twist="+this.twist);
        if (this.selectedMat ==null || this.branch ==null  || this.twist ==null){
            return "";
            }
        return DataCache.getCraftName(selectedMat.getCode(), branch, twist);
        }
    
    public String getDisplayCraftNameFromDB () {
        StatisDAO dao = new StatisDAO();
        Session session = HibernateUtil.getSession();
        String name =dao.getCraftNaming(session, selectedMat.getCode(), branch, twist);
        session.close();
        return name;
        
        }

    public List<StationByCat> getStationByCat() {
        if (data_sbc == null) {
            Map viewScope = MBeanUtil.getViewScope();
            if( viewScope ==null ) {
                return new ArrayList<StationByCat>();
                }
            
            Date start =  (Date)MBeanUtil.getViewScope().get("sbc_start");
            Date end  =  (Date)MBeanUtil.getViewScope().get("sbc_end");
            MaterialNameValue mat = (MaterialNameValue)MBeanUtil.getViewScope().get("sbc_mat");
            Integer branch = (Integer)MBeanUtil.getViewScope().get("sbc_branch");
            Float twist =  (Float)MBeanUtil.getViewScope().get("sbc_twist");
            
            log.info("---startDate=" + FormatUtil.formateDate(start) + "  ;endDate=" + FormatUtil.formateDate(end) + "   ;selectedMat=" + mat
                          +" ;branch="+branch +" ;twist="+twist);
            
            HistoryDataService hds = new HistoryDataService();
            data_sbc = hds.getStDataByMatBranchTwist( start  , end, mat , branch , twist ); 
        }
        return data_sbc;
    }


    public void queryData(ActionEvent actionEvent) {
        if (this.selectedMat == null || this.start == null) {
            return;
        }
        if (this.end == null) {
            end = new Date();
        }
        log.info("......query data for StationByCat......");
      
        MBeanUtil.getViewScope().put("sbc_start", start); 
        MBeanUtil.getViewScope().put("sbc_end", end); 
        MBeanUtil.getViewScope().put("sbc_mat", selectedMat);
        MBeanUtil.getViewScope().put("sbc_branch", branch); 
        MBeanUtil.getViewScope().put("sbc_twist", twist); 
    }

    //============================================================================
    //banCiShiJianDuiBi  班次时间对比
    //============================================================================
    private Station st1;
    private Station st2;
    private Integer stationId1;
    private Integer stationId2;
    private Date date1_start;
    private Date date2_start;
    private Date date1_end;
    private Date date2_end;
    private Shift shift1; //selected shift
    private Shift shift2; //selected shift


    public List<SelectItem> getSelectionShift() {
        return DataCache.getSelectionShifts();
    }

    public void setSt1(Station st1) {
        this.st1 = st1;
    }

    public Station getSt1() {
        if(stationId1 == null) return null;
        //  return st1;
        StationDAO dao = new StationDAO();
        Session session = HibernateUtil.getSession();
        Station st =  dao.getByNO(session, stationId1);
        session.close();
        return st;
    }

    public void setSt2(Station st2) {
        this.st2 = st2;
    }

    public Station getSt2() {
        if (stationId2 ==null ) return null; 
        // return st2;
        StationDAO dao = new StationDAO();
        Session session = HibernateUtil.getSession();
        Station st =  dao.getByNO(session, stationId2);  
        session.close();
        return st;
    }


    public void setStationId1(Integer stationId1) {
        this.stationId1 = stationId1;
    }

    public Integer getStationId1() {
        return stationId1;
    }

    public void setStationId2(Integer stationId2) {
        this.stationId2 = stationId2;
    }

    public Integer getStationId2() {
        return stationId2;
    }

    public void setDate1_start(Date date1_start) {
        this.date1_start = date1_start;
    }

    public Date getDate1_start() {
        return date1_start;
    }

    public void setDate2_start(Date date2_start) {
        this.date2_start = date2_start;
    }

    public Date getDate2_start() {
        return date2_start;
    }

    public void setDate1_end(Date date1_end) {
        this.date1_end = date1_end;
    }

    public Date getDate1_end() {
        return date1_end;
    }

    public void setDate2_end(Date date2_end) {
        this.date2_end = date2_end;
    }

    public Date getDate2_end() {
        return date2_end;
    }

    public void setShift1(Shift shift1) {
        this.shift1 = shift1;
    }

    public Shift getShift1() {
        return shift1;
    }

    public void setShift2(Shift shift2) {
        this.shift2 = shift2;
    }

    public Shift getShift2() {
        return shift2;
    }

    public String getShift1Name() {
        return shift1 == null ? "shift1" : shift1.getName();
    }

    public String getShift2Name() {
        return shift2 == null ? "shift2" : shift2.getName();
    }
    


    public void doShiftCompare(ActionEvent actionEvent) {
        log.info("------query data for shift comparasion------");
        Station st1 = this.getSt1();
        Station st2 = this.getSt2();
        Shift shift1 = this.getShift1();
        Shift shift2 = this.getShift2();
        Date start1 = this.getDate1_start();
        Date end1 = this.getDate1_end();
        Date start2 = this.getDate2_start();
        Date end2 = this.getDate2_end();
 
        MBeanUtil.getViewScope().put("shiftComp_st1", st1); 
        MBeanUtil.getViewScope().put("shiftComp_st2", st2); 
        MBeanUtil.getViewScope().put("shiftComp_shift1", shift1); 
        MBeanUtil.getViewScope().put("shiftComp_shift2",shift2); 
        MBeanUtil.getViewScope().put("shiftComp_start1",start1); 
        MBeanUtil.getViewScope().put("shiftComp_start2",start2); 
        MBeanUtil.getViewScope().put("shiftComp_end1", end1); 
        MBeanUtil.getViewScope().put("shiftComp_end2", end2); 

    }


    List<ShiftDataComparasion> shiftCompare = null;
    public List<ShiftDataComparasion> getAllShiftCompData() {
        if (shiftCompare == null) {
            if (MBeanUtil.getViewScope() == null) {
                return new ArrayList<ShiftDataComparasion>();
                }
            Station st1 = (Station)MBeanUtil.getViewScope().get("shiftComp_st1"); 
            Station st2 = (Station)MBeanUtil.getViewScope().get("shiftComp_st2"); 
            Shift shift1 = (Shift)MBeanUtil.getViewScope().get("shiftComp_shift1"); 
            Shift shift2 = (Shift)MBeanUtil.getViewScope().get("shiftComp_shift2"); 
            Date start1 = (Date)MBeanUtil.getViewScope().get("shiftComp_start1"); 
            Date start2 = (Date)MBeanUtil.getViewScope().get("shiftComp_start2"); 
            Date end1 = (Date)MBeanUtil.getViewScope().get("shiftComp_end1"); 
            Date end2 = (Date)MBeanUtil.getViewScope().get("shiftComp_end2"); 

            if (shift1 == null || shift2 == null) {
                return new ArrayList<ShiftDataComparasion>();
            }
            
            Locale local = LangSetting.getUserLocale();
            HistoryDataService hds = new HistoryDataService();
            
            Integer stId1= st1 ==null? null: st1.getId();
            Integer stId2 = st2 ==null? null:st2.getId();
            log.info("shift1: " + shift1 + " startTime:" + FormatUtil.formateDate(start1) + "  endTime:" +  FormatUtil.formateDate(end1) + " stationId:" +stId1 );
            log.info("shift2: " + shift2 + " startTime:" + FormatUtil.formateDate(start2) + "  endTime:" +   FormatUtil.formateDate(end2) + " stationId:" + stId2 );
            shiftCompare = hds.getShiftDataComparation2(start1, start2, end1, end2, shift1, shift2, st1, st2, local);

        }
        return shiftCompare;
    }


    //============================================
    // Jitai shuju duibi机台数据对比
    //============================================
    private List<Shift> shift_STData; 
    private Date start_STData;
    private Date end_STData;
    private List<Station> selectedStations_STData;

    private Integer catValue_STData;
    private MaterialNameValue selectedMat_STData;
    private Float  twist_STData;


    public List<SelectItem> getSelectionShift_STData() {
        return DataCache.getSelectionShifts();
    }

    public void setShift_STData(List<Shift> Shift_STData) {
        this.shift_STData = Shift_STData;
    }

    public List<Shift> getShift_STData() {
        return shift_STData;
    }


    public void setStart_STData(Date start_STData) {
        this.start_STData = start_STData;
    }

    public Date getStart_STData() {
        return start_STData;
    }

    public void setEnd_STData(Date end_STData) {
        this.end_STData = end_STData;
    }

    public Date getEnd_STData() {
        return end_STData;
    }


    public void setSelectedStations_STData(List<Station> selectedStations_STData) {
        this.selectedStations_STData = selectedStations_STData;
    }

    public List<Station> getSelectedStations_STData() {
        return selectedStations_STData;
    }

    public List<SelectItem> getSelectionStations_STData() {
        return DataCache.getSelectionStations();
    }
    
    public void setCatValue_STData(Integer catValue) {
        this.catValue_STData =catValue; 
        }
    
    public Integer getCatValue_STData() {
         return this.catValue_STData;
        }


    public void setTwist_STData(Float twist_STData) {
        this.twist_STData = twist_STData;
    }

    public Float getTwist_STData() {
        return twist_STData;
    }


    public void setSelectedMat_STData(MaterialNameValue selectedMat_STData) {
        this.selectedMat_STData = selectedMat_STData;
    }

    public MaterialNameValue getSelectedMat_STData() {
        return selectedMat_STData;
    }


    public List<SelectItem> getSelectionMats_STData() {
        return DataCache.getSelectionMats();
    }

    
    List<StatisData> stationCompare = null;
    public List<StatisData> getStationCompData () {
        if( stationCompare ==null) {
            if (MBeanUtil.getViewScope() == null ) {
                return new ArrayList<StatisData>();
                }
            
                Float cat = (Float)MBeanUtil.getViewScope().get("stationComp_cat"); //Float
                List<Shift> shifts = (List<Shift>)MBeanUtil.getViewScope().get("stationComp_shift"); //List<Shift>
                Date start = (Date)MBeanUtil.getViewScope().get("stationComp_start"); //Date
                Date end = (Date)MBeanUtil.getViewScope().get("stationComp_end"); //Date
                MaterialNameValue mat = (MaterialNameValue)MBeanUtil.getViewScope().get("stationComp_mat");// MaterialNameValue
                List<Station> sts = ( List<Station>)MBeanUtil.getViewScope().get("stationComp_station");// List<Station>
                Float twist = (Float)MBeanUtil.getViewScope().get("stationComp_twist");//Float
                
                log.info("---Station comp query--- shift="+shifts +" ;stations="+sts +"  ;start="+start +"  ;end="+end +"  ;cat="+cat +" ;mat="+mat +" ;twist="+twist);
                if (  sts ==null) {
                    return new ArrayList<StatisData>();
                    }
                
                HistoryDataService hds = new HistoryDataService();
                stationCompare = hds.getStData2(shifts, sts, start, end, cat, mat,twist);
            }
         
           return stationCompare;
        }
    

    public void doStationCompare(ActionEvent actionEvent) {
        Float cat4query =null;
        if(catValue_STData!=null && catValue_STData >0 ){
                Float floatCatValue = (float)catValue_STData ;
                cat4query = floatCatValue;
            }
        
        log.debug("------Query data for station comparasion------cat:"+catValue_STData  +"  mat:"+selectedMat_STData +"  start:"+start_STData +" end:"+end_STData
                 +" shift:"+shift_STData  +"  stations:"+selectedStations_STData);
        
        MBeanUtil.getViewScope().put("stationComp_cat", cat4query); //Float
        MBeanUtil.getViewScope().put("stationComp_shift", shift_STData); //List<Shift>
        MBeanUtil.getViewScope().put("stationComp_start", start_STData); //Date
        MBeanUtil.getViewScope().put("stationComp_end", end_STData); //Date
        MBeanUtil.getViewScope().put("stationComp_mat", selectedMat_STData);// MaterialNameValue
        MBeanUtil.getViewScope().put("stationComp_station", selectedStations_STData);// List<Station>
        MBeanUtil.getViewScope().put("stationComp_twist", twist_STData);//Float
        
    }

      //=============================================
     // Station  statis by date range (机台时间段统计)
    //==============================================
    private Date start_StSummary;
    private Date end_Stsummary;
    private List<Station> selectedSts_StSummary;

    public void setStart_StSummary(Date start_StSummary) {
        this.start_StSummary = start_StSummary;
    }

    public Date getStart_StSummary() {
        return start_StSummary;
    }

    public void setEnd_Stsummary(Date end_Stsummary) {
        this.end_Stsummary = end_Stsummary;
    }

    public Date getEnd_Stsummary() {
        return end_Stsummary;
    }

    public void setSelectedSts_StSummary(List<Station> selectedSts_StSummary) {
        this.selectedSts_StSummary = selectedSts_StSummary;
    }

    public List<Station> getSelectedSts_StSummary() {
        return selectedSts_StSummary;
    }


    private List<StatisData> stSummary;
    public List<StatisData> getStSummaryData () {
        if( stSummary ==null) {
               if(MBeanUtil.getViewScope() == null ) {
                return new ArrayList<StatisData>();
                }
                Date start =  (Date)MBeanUtil.getViewScope().get("stSummary_start"); 
                Date end  =  (Date)MBeanUtil.getViewScope().get("stSummary_end"); 
                List<Station> stations =  (List<Station>)MBeanUtil.getViewScope().get("stSummary_station"); 
                log.info("------query station summary data------start:"+start_StSummary +" end:"+end_Stsummary  +"  stations:"+selectedSts_StSummary);
                if ( stations == null ) {
                    return new ArrayList<StatisData>();
                    }
                
            HistoryDataService hds = new HistoryDataService();
            stSummary =   hds.stationSummary(stations,  start,  end) ;
            }
           return stSummary;
        }
    
    public void doStationSummary(ActionEvent actionEvent) {
         MBeanUtil.getViewScope().put("stSummary_start", start_StSummary); 
         MBeanUtil.getViewScope().put("stSummary_end", end_Stsummary); 
         MBeanUtil.getViewScope().put("stSummary_station", selectedSts_StSummary); 
         
    }
    
    
    //=====================================================
    //所有机台统计数据
    //=====================================================
    //the properties  used in page of suoYouJiTaiTongJi.jspx ---start
    private Date start_allStSummary;
    private Date end_allStSummary;


    public void setStart_allStSummary(Date start_allStSummary) {
        this.start_allStSummary = start_allStSummary;
    }

    public Date getStart_allStSummary() {
        return start_allStSummary;
    }

    public void setEnd_allStSummary(Date end_allStSummary) {
        this.end_allStSummary = end_allStSummary;
    }

    public Date getEnd_allStSummary() {
        return end_allStSummary;
    }
    //the properties  used in page of suoYouJiTaiTongJi.jspx ---end
    
    /*** old method...
    public  StatisData getAllStationSummary () {
        if (start_StSummary == null  ||  end_Stsummary ==null ) {
            log.info("---first time load data for allStationSummary");
            return new StatisData();
            }
        
             if (allStationSummary != null  )  {
                log.info(">>>>>>allStationSummary property kept, so return it...");
                return allStationSummary;
                }
             else {
                    HistoryDataService hds = new HistoryDataService();
                    log.info(">>>>>>requery db for all station summary...");
                    allStationSummary =  hds.allStationSummary( start_StSummary, end_Stsummary);
                return allStationSummary;
            
                }
        }
    ***/
    

    private  StatisData  allStationSummary;
    public  StatisData getAllStationSummary () {
        if ( allStationSummary == null  ) {
           Date start = start_allStSummary;   // (Date)MBeanUtil.getViewScope().get("stSummary_start");  //in stationsummary
           Date end  = end_allStSummary;   // (Date)MBeanUtil.getViewScope().get("stSummary_end");  //in stationsummary
           log.info("---load data for allStationSummary, start="+start + " ;end="+end);        
            HistoryDataService hds = new HistoryDataService();
            allStationSummary =  hds.allStationSummary( start , end);
            }
        return allStationSummary;
        }
    
    //the methods  used in page of suoYouJiTaiTongJi.jspx
    public void doAllStationSummary(ActionEvent actionEvent) {
       log.info("******start="+start_allStSummary  +"   end="+end_allStSummary);
      // if (start_allStSummary == null) return;
        HistoryDataService hds = new HistoryDataService();
        log.info("******query db for all station summary...");
        allStationSummary =  hds.allStationSummary( start_allStSummary, end_allStSummary);
    }
    
    public void showAllStDetail(DisclosureEvent disclosureEvent) {
        Date start =  (Date)MBeanUtil.getViewScope().get("stSummary_start"); 
        Date end  =  (Date)MBeanUtil.getViewScope().get("stSummary_end"); 
        log.info("******showAllStDetail  start="+start +"   ;end="+end);

         HistoryDataService hds = new HistoryDataService();
         log.info("******showAllStDetail  query db for all station summary...");
         allStationSummary =  hds.allStationSummary( start,end );
    }

    
    public Integer getStationCount() {
            HistoryDataService hds = new HistoryDataService();
            Integer  result = hds.stationCount();
            return result;
        }

}
