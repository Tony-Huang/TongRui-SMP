package com.hdp.smp.front.util;

import com.hdp.smp.front.ui.mbean.Login;

import java.util.Map;

import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.context.FacesContext;

import oracle.adf.share.ADFContext;

public class MBeanUtil {
    public MBeanUtil() {
        super();
    }
    
    private static   ADFContext adfctx;
    
    public static ADFContext getCurrentAdfCtx() {
       // if ( adfctx ==null ) {
            adfctx = ADFContext.findCurrent();//getCurrent(); 
        //    }
        return adfctx;
        }
    
    public static Login getLoginBean(){
          FacesContext ctx = FacesContext.getCurrentInstance();
          ExpressionFactory ef = ctx.getApplication().getExpressionFactory();
          ValueExpression ve = ef.createValueExpression(ctx.getELContext(), "#{Login}", Login.class);
          Login loginBean = (Login)ve.getValue(ctx.getELContext());
          return loginBean;
    }
    
    
    public static Object getRequestOrSessionMBean(String beanName,Class beanClass){
            FacesContext ctx = FacesContext.getCurrentInstance();
            ExpressionFactory ef = ctx.getApplication().getExpressionFactory();
            ValueExpression ve = ef.createValueExpression(ctx.getELContext(), "#{"+beanName+"}",beanClass);
            Object bean = ve.getValue(ctx.getELContext());
            return bean;
        }
    
    public static Map getViewScope () {
            ADFContext adfctx =getCurrentAdfCtx(); 
            Map viewScope = adfctx.getViewScope();//getSessionScope(); 
            return viewScope;
        }
    
    public static Map getRequestScope () {
            ADFContext adfctx = getCurrentAdfCtx() ; 
            Map reqScope = adfctx.getRequestScope();
            return reqScope;
        }

}
