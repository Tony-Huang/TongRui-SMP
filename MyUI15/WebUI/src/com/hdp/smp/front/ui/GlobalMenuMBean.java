package com.hdp.smp.front.ui;

import java.io.IOException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

public class GlobalMenuMBean {
    public GlobalMenuMBean() {
    }


    public void setAdvanced(ActionEvent ae) throws IOException
    {
//      if (_optimization == _ADVANCED)
//      {
//        return;
//      }
//      _optimization = _ADVANCED;
      _issueRedirect();
    }

    private static void _issueRedirect() throws IOException
    {
      FacesContext context = FacesContext.getCurrentInstance();
      ExternalContext ec = context.getExternalContext();
      
      String url = context.getApplication().getViewHandler().getActionURL(context, context.getViewRoot().getViewId());
      
      ec.redirect(ec.encodeActionURL(url));
      
      context.responseComplete();
    }

    public String goGongYiPingZhongChaJiTai() {
        // Add event code here...
        return "gongYiPinZhongChajiTai";
    }
}
