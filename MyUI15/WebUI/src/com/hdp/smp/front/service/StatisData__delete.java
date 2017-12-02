package com.hdp.smp.front.service;


/**
 * The view for statis data, such as station compare, shift compare,etc
 */
public class StatisData__delete {
    public StatisData__delete() {
        super();
    }

    private int stationId;

    private double avgProductionEfficiency;  //生产效率
    private double avgEnergyConsumptionPerTon;//吨纱能耗
    private double avgShiftGrossProduction;//班次总产量
    private long sumStationGrossProduction;//机台总产量
    private double avgDoffTimeConsumption;//落纱耗时
    private double avgDoffBrokenHeads;//落纱断头
    private double avgBrokenEndsPer1000Spindles;//千锭时断头率
    private double avgEmptySpindles;//空锭
    private double avgBrokenSpindles;//坏锭数
    private double avgCreepSpindles;//滑锭
    private long sumBrokenHeads;//机台总断头数

    private long sumEmptySpindles;//总空锭数
    private long sumBadSpindles;//总坏锭数
    private long sumCreepSpindles;//总滑锭数
    private long sumDoffTimes;//总落纱次数

    private double sumGrossProductionByShift;//班次总产量


    public void setAvgProductionEfficiency(double avgProductionEfficiency) {
        this.avgProductionEfficiency = avgProductionEfficiency;
    }

    public double getAvgProductionEfficiency() {
        return avgProductionEfficiency;
    }

    public void setAvgEnergyConsumptionPerTon(double avgEnergyConsumptionPerTon) {
        this.avgEnergyConsumptionPerTon = avgEnergyConsumptionPerTon;
    }

    public double getAvgEnergyConsumptionPerTon() {
        return avgEnergyConsumptionPerTon;
    }

    public void setAvgShiftGrossProduction(double avgShiftGrossProduction) {
        this.avgShiftGrossProduction = avgShiftGrossProduction;
    }

    public double getAvgShiftGrossProduction() {
        return avgShiftGrossProduction;
    }

    public void setSumStationGrossProduction(long sumStationGrossProduction) {
        this.sumStationGrossProduction = sumStationGrossProduction;
    }

    public long getSumStationGrossProduction() {
        return sumStationGrossProduction;
    }

    public void setAvgDoffTimeConsumption(double avgDoffTimeConsumption) {
        this.avgDoffTimeConsumption = avgDoffTimeConsumption;
    }

    public double getAvgDoffTimeConsumption() {
        return avgDoffTimeConsumption;
    }

    public void setAvgDoffBrokenHeads(double avgDoffBrokenHeads) {
        this.avgDoffBrokenHeads = avgDoffBrokenHeads;
    }

    public double getAvgDoffBrokenHeads() {
        return avgDoffBrokenHeads;
    }

    public void setAvgBrokenEndsPer1000Spindles(double avgBrokenEndsPer1000Spindles) {
        this.avgBrokenEndsPer1000Spindles = avgBrokenEndsPer1000Spindles;
    }

    public double getAvgBrokenEndsPer1000Spindles() {
        return avgBrokenEndsPer1000Spindles;
    }

    public void setAvgEmptySpindles(double avgEmptySpindles) {
        this.avgEmptySpindles = avgEmptySpindles;
    }

    public double getAvgEmptySpindles() {
        return avgEmptySpindles;
    }

    public void setAvgBrokenSpindles(double avgBrokenSpindles) {
        this.avgBrokenSpindles = avgBrokenSpindles;
    }

    public double getAvgBrokenSpindles() {
        return avgBrokenSpindles;
    }

    public void setAvgCreepSpindles(double avgCreepSpindles) {
        this.avgCreepSpindles = avgCreepSpindles;
    }

    public double getAvgCreepSpindles() {
        return avgCreepSpindles;
    }

    public void setSumBrokenHeads(long sumBrokenHeads) {
        this.sumBrokenHeads = sumBrokenHeads;
    }

    public long getSumBrokenHeads() {
        return sumBrokenHeads;
    }

    public void setSumEmptySpindles(long sumEmptySpindles) {
        this.sumEmptySpindles = sumEmptySpindles;
    }

    public long getSumEmptySpindles() {
        return sumEmptySpindles;
    }

    public void setSumBadSpindles(long sumBadSpindles) {
        this.sumBadSpindles = sumBadSpindles;
    }

    public long getSumBadSpindles() {
        return sumBadSpindles;
    }

    public void setSumCreepSpindles(long sumCreepSpindles) {
        this.sumCreepSpindles = sumCreepSpindles;
    }

    public long getSumCreepSpindles() {
        return sumCreepSpindles;
    }

    public void setSumDoffTimes(long sumDoffTimes) {
        this.sumDoffTimes = sumDoffTimes;
    }

    public long getSumDoffTimes() {
        return sumDoffTimes;
    }

    public void setSumGrossProductionByShift(double sumGrossProductionByShift) {
        this.sumGrossProductionByShift = sumGrossProductionByShift;
    }

    public double getSumGrossProductionByShift() {
        return sumGrossProductionByShift;
    }


    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public int getStationId() {
        return stationId;
    }


}
