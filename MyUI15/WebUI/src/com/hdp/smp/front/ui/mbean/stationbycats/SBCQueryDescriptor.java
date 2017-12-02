package com.hdp.smp.front.ui.mbean.stationbycats;

import java.util.Collections;
import java.util.Map;

import oracle.adf.view.rich.model.AttributeCriterion;
import oracle.adf.view.rich.model.ConjunctionCriterion;
import oracle.adf.view.rich.model.Criterion;
import oracle.adf.view.rich.model.QueryDescriptor;

public class SBCQueryDescriptor extends QueryDescriptor {
    public SBCQueryDescriptor() {
        super();
    }

    public static void main(String[] args) {
        SBCQueryDescriptor sBCQueryDescriptor = new SBCQueryDescriptor();
    }

    @Override
    public void addCriterion(String string) {

    }

    @Override
    public void changeMode(QueryDescriptor.QueryMode queryMode) {
    }

    @Override
    public ConjunctionCriterion getConjunctionCriterion() {

        return new MyConjunctionCriterion();
    }

    @Override
    public String getName() {
        return "StationByCat";
    }

    @Override
    public Map<String, Object> getUIHints() {
        // TODO Implement this method
        return Collections.emptyMap();
    }

    @Override
    public void removeCriterion(Criterion criterion) {
    }

    @Override
    public AttributeCriterion getCurrentCriterion() {
        return null;
    }

    @Override
    public void setCurrentCriterion(AttributeCriterion attributeCriterion) {
    }
}
