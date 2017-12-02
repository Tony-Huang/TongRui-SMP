package com.hdp.smp.front.service;


/**
 * The view for statis data, such as station compare, shift compare,etc
 */
public class StatisData__delete {
    public StatisData__delete() {
        super();
    }

    private int stationId;

    private double avgProductionEfficiency;  //����Ч��
    private double avgEnergyConsumptionPerTon;//��ɴ�ܺ�
    private double avgShiftGrossProduction;//����ܲ���
    private long sumStationGrossProduction;//��̨�ܲ���
    private double avgDoffTimeConsumption;//��ɴ��ʱ
    private double avgDoffBrokenHeads;//��ɴ��ͷ
    private double avgBrokenEndsPer1000Spindles;//ǧ��ʱ��ͷ��
    private double avgEmptySpindles;//�ն�
    private double avgBrokenSpindles;//������
    private double avgCreepSpindles;//����
    private long sumBrokenHeads;//��̨�ܶ�ͷ��

    private long sumEmptySpindles;//�ܿն���
    private long sumBadSpindles;//�ܻ�����
    private long sumCreepSpindles;//�ܻ�����
    private long sumDoffTimes;//����ɴ����

    private double sumGrossProductionByShift;//����ܲ���


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
