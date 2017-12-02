package com.hdp.smp.front.ui.mbean;

import com.hdp.smp.shcedule.JobScheduler;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import oracle.adf.share.ADFContext;
import oracle.adf.share.security.SecurityContext;
import oracle.adf.view.rich.component.rich.layout.RichPanelBox;

import org.apache.log4j.Logger;
//import javax.servlet.htpp.*;
public class Login extends MBean{

    public Login() {
    }
    
    public static final Logger log = Logger.getLogger(Login.class);
    
    private RichPanelBox pb1;//loginbox

    public static String mainpage = "main.jspx";
    public static String logoutpage = "login.jspx";

    //property
    private String _username;
    private String _password;

    public void setUsername(String _username) {
        this._username = _username;
    }

    public String getUsername() {
        return _username;
    }

    public void setPassword(String _password) {
        this._password = _password;
    }

    public String getPassword() {
        return _password;
    }

    public static String  msg_key_login_fail_head="login.error.msg.head";
    public static String msg_key_login_fail_detail="login.error.msg.detail";
    public String doLogin2() {
        //start quartz shceduler
        JobScheduler.get();
        //....
        FacesContext ctx = FacesContext.getCurrentInstance();
        if (_username == null || _password == null) {
            //showError("Invalid credentials", "An incorrect username or password was specified.", null);
            showError(this.getMsg(msg_key_login_fail_head), 
                              this.getMsg(msg_key_login_fail_detail), null);
            //stay in the login page
            return null;
        } else {
            ExternalContext ectx = ctx.getExternalContext();
            HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();
            try {
                log.info("======******* login ====== username=" + _username );
                request.login(_username, _password); // Servlet 3.0 login

                _username = null;
                _password = null;
                HttpSession session = request.getSession();
                String viewId = ctx.getViewRoot().getViewId();
                log.info("viewId=" + viewId);
                session.setAttribute("success_url", "/faces/" + mainpage); 

                printMsgAoutSecurity();

                //if login success, redirect to main page
                redirect(ectx.getRequestContextPath() + "/adfAuthentication" + "?success_url=/faces/" + mainpage);
            } catch (Exception ex) {
                //if login error, stay in login page
                log.error("login error:"+ex);
        
               // showError("ServletException", "Login failed.Please verify the username and password and try again.", null);
               showError(this.getMsg(msg_key_login_fail_head), 
                                 this.getMsg(msg_key_login_fail_detail), null);
                
                return null;
            }
        }
        return null; //"loginSuccess";
    }


    //redirect
    private void redirect(String forwardUrl) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        ExternalContext ectx = ctx.getExternalContext();
        try {
            log.info("...redirect to ...:" + forwardUrl);
            ectx.redirect(forwardUrl); 
        } catch (IOException ie) {
            showError("IOException", "An error occurred during redirecting. Please consult logs for more information.",
                      ie);
        }
    }

    //--show error
    private void showError(String errType, String message, Exception e) {
        log.info("...show error msg.....");
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, errType, message);
        FacesContext.getCurrentInstance().addMessage(this.pb1.getClientId(), msg);
        if (e != null) {
           log.error(e);
        }
    }


    //----logoff
    public String logoff() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        ExternalContext ectx = ctx.getExternalContext();
        HttpServletRequest httpRequest = (HttpServletRequest) ectx.getRequest();
        try {
            httpRequest.logout(); // Servlet 3.0 logout
            
            /*** ,has log out, so no need to invalidate session esle wil throw error.
            HttpSession session = httpRequest.getSession(false);
            if (session != null) {
                try{
                session.invalidate();
                } catch (Exception e){
                    log.error(e);
                    }
            }
            ***/
            String viewId = ctx.getViewRoot().getViewId();
            log.info("logout .... the viewId=" + viewId);
            String logoutUrl = ectx.getRequestContextPath() + "/faces/" + logoutpage; //ctx.getViewRoot().getViewId();
            redirect(logoutUrl);
        } catch (ServletException e) {
            showError("ServletException", "An error occurred during logout. Please consult logs for more information.",e);
        }
        return null;
    }


    public String cancel() {
        return null;
    }
    
    public String go2UserProfile(){
        return "profile";
        }

    public void printMsgAoutSecurity() {
        String currentUser = ADFContext.getCurrent().getSecurityContext().getUserName();
        log.info("====================current user : " + currentUser);

        boolean authenticated = ADFContext.getCurrent().getSecurityContext().isAuthenticated();
        log.info("====================is authenticated : " + authenticated);

        for (String role : ADFContext.getCurrent().getSecurityContext().getUserRoles()) {
            log.info("====================user's role : " + role);
        }

        SecurityContext sec = ADFContext.getCurrent().getSecurityContext();
        if (sec.isUserInRole("admin-users")) {
            log.info("====user role is admin ");
        } else if (sec.isUserInRole("valid-users")) {
           log.info("====user role is common user ");
        } else {
            log.info("====ADF sucuritycontext can not know if user is in a role! ");
        }

    }

    public void setLoginBox(RichPanelBox pb1) {
        this.pb1 = pb1;
    }

    public RichPanelBox getLoginBox() {
        return pb1;
    }

}
