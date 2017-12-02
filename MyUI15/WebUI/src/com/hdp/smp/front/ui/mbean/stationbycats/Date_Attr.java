package com.hdp.smp.front.ui.mbean.stationbycats;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import oracle.adf.view.rich.model.AttributeCriterion;
import oracle.adf.view.rich.model.AttributeDescriptor;

public class Date_Attr extends AttributeCriterion {
    private String name;
    private String opLabel;
    private String opName;
    public Date_Attr() {
        super();
    }
    
    public Date_Attr(String name,String oplabel, String opname) {
        super();
        this.name =name;
        this.opLabel = oplabel;
        this.opName = opname;
    }

    @Override
    public AttributeDescriptor getAttribute() {
     AttributeDescriptor ad=   new AttributeDescriptor () {
            @Override
            public Set<AttributeDescriptor.Operator> getSupportedOperators() {
                return Collections.emptySet();
            }

            @Override
            public AttributeDescriptor.ComponentType getComponentType() {
                return  AttributeDescriptor.ComponentType.inputDate;
            }

            @Override
            public int getLength() {
                return 0;
            }

            @Override
            public int getMaximumLength() {
                return 0;
            }

            @Override
            public Object getModel() {
             
                return null;
            }

            @Override
            public Class getType() {
                return Date.class;
            }

            @Override
            public boolean isReadOnly() {
                return false;
            }

            @Override
            public boolean isRequired() {
                return false;
            }

            @Override
            public String getName() {
                return name;
            }

            @Override
            public String getLabel() {
                return name;
            }

            @Override
            public String getDescription() {
                return null;
            }
        };
        return ad;
    }

    @Override
    public AttributeDescriptor.Operator getOperator() {
        AttributeDescriptor ad = this.getAttribute();
      //  AttributeDescriptor.Operator op =ad.Operator;
        AttributeDescriptor.Operator op=    ad.new Operator  (){

            @Override
            public String getLabel() {     
                return opLabel;
            }

            @Override
            public Object getValue() {
                return opName;
            }

            @Override
            public int getOperandCount() {
                return 1;
            }
        };
        
        return op;
    }

    @Override
    public Map<String, AttributeDescriptor.Operator> getOperators() {
        return Collections.emptyMap();
    }

    @Override
    public List<? extends Object> getValues() {
        return Collections.emptyList();
    }

    @Override
    public boolean isRemovable() {
        return false;
    }

    @Override
    public void setOperator(AttributeDescriptor.Operator operator) {
    }
}
