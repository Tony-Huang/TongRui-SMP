package com.hdp.smp.front.ui.mbean.stationbycats;

import java.util.ArrayList;
import java.util.List;

import oracle.adf.view.rich.model.ConjunctionCriterion;
import oracle.adf.view.rich.model.Criterion;

public class MyConjunctionCriterion extends ConjunctionCriterion {
    List<Criterion> criterionList = null;
    //Map<String, Criterion> criterionSearchFieldMap = null;

    public MyConjunctionCriterion() {
        criterionList = new ArrayList<Criterion>();
        //   criterionSearchFieldMap = new HashMap<String, Criterion>();
        criterionList.add(new Cat_Attr());
        criterionList.add(new Date_Attr("start",">=","gt"));
        criterionList.add(new Date_Attr("end","<=","lt"));
    }

    public Object getKey(Criterion criterion) {
        for (int index = 0; index < criterionList.size(); index++) {
            if (criterionList.get(index).equals(criterion))
                return new Integer(index); // Return an Object
        }
        return null;
    }

    public Criterion getCriterion(Object key) {
        int index = -1;

        // Always expect Integer object from framework as Integer is passed in getKey() method
        if (key != null && key instanceof Integer) {
            index = ((Integer) key).intValue();
        }

        if (index > -1 && index < criterionList.size())
            return criterionList.get(index);

        return null;
    }

    public ConjunctionCriterion.Conjunction getConjunction() {
        ConjunctionCriterion.Conjunction conjType = ConjunctionCriterion.Conjunction.AND;
        return conjType;
    }

    public List<Criterion> getCriterionList() {
        return criterionList;
    }

    public void setConjunction(ConjunctionCriterion.Conjunction conjunction) {
        //
    }


}
