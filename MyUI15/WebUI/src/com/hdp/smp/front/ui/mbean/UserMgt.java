package com.hdp.smp.front.ui.mbean;

import com.hdp.smp.front.util.CredentialUtil;
import com.hdp.smp.front.util.LangSetting;
import com.hdp.smp.model.Role;
import com.hdp.smp.model.User;
import com.hdp.smp.persistence.DAO;
import com.hdp.smp.persistence.HibernateUtil;

import java.util.List;
import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import oracle.adf.view.rich.component.rich.data.RichTable;

import org.apache.log4j.Logger;

import org.hibernate.Session;

public class UserMgt extends MBean {
    public UserMgt() {
        super();
    }
    
    public static Logger log = Logger.getLogger(UserMgt.class);

    private String username;
    private String passwd;
    private String description;
    private String roleName;

    private Role selectedRole;

    private RichTable userTable;
    
    
    UIComponent cnColumn;
    UIComponent enColumn;
    
    
     private UIComponent getUIComponent(String name) {
         FacesContext facesCtx = FacesContext.getCurrentInstance();
         UIComponent component = facesCtx.getViewRoot().findComponent(name) ;
         return component;
       }



    public List<SelectItem> getSelectionRoles() {
        Locale lo = this.getUserLocale();
        return CredentialUtil.getSelectionRoles(lo);
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setSelectedRole(Role selectedRole) {
        this.selectedRole = selectedRole;
    }

    public Role getSelectedRole() {
        return selectedRole;
    }


    public void setUserTable(RichTable userTable) {
        this.userTable = userTable;
    }

    public RichTable getUserTable() {
        return userTable;
    }


    public void setCnColumn(UIComponent cnColumn) {
        this.cnColumn = cnColumn;
    }

    public UIComponent getCnColumn() {
        return cnColumn;
    }

    public void setEnColumn(UIComponent enColumn) {
        this.enColumn = enColumn;
    }

    public UIComponent getEnColumn() {
        return enColumn;
    }
    
    public boolean isEnglish() {
       return  !LangSetting.isCN();
        }
    
    public boolean isChinese() {
            return  LangSetting.isCN();
        }


    public static final String headkey="server.error.msg.head";
    public static final String  detailkey= "server.error.msg.detail";
    //===add user======
    public String saveUser() {
        saveUser2DB();
        return "back";
    }


    private void saveUser2DB() {
        User u = new User();
        try {
            u.setName(username);
            String encryptedPwd = CredentialUtil.encryptTomcatRealmPasswd(this.passwd);
            u.setPasswd(encryptedPwd);
            u.setRoleName(selectedRole.getName());
            u.setRole(this.selectedRole);
            DAO udao = new DAO();
            Session session = HibernateUtil.getSession();
            udao.save(u, session);
            session.close();
        } catch (Exception e) {
            log.error(e);
            this.showError(getMsg(headkey), getMsg(detailkey));
        }
    }

    //====== update user======
    public void updateUser(ActionEvent actionEvent) {
        updateUser2DB();
    }

    private void updateUser2DB() {
        User user = (User) this.userTable.getSelectedRowData();
        if (user== null) return;
        try{
        DAO dao = new DAO();
        Session session = HibernateUtil.getSession();
        dao.saveOrUpdate(user, session);
        session.close();
        } catch (Exception e) {
            log.error(e);
            this.showError(getMsg(headkey), getMsg(detailkey));
        }
    }
    
    
    //======delete user ======
    public void deleteUser(ActionEvent actionEvent) {
        this.deleteFromDB();
    }

    private void deleteFromDB() {
        User user = (User) this.userTable.getSelectedRowData();
        if (user == null ) return;
        try{
        Session session = HibernateUtil.getSession();
        DAO udao = new DAO();
        udao.delete(user, session);
        session.close();
        } catch (Exception e) {
            log.error(e);
            this.showError(getMsg(headkey), getMsg(detailkey));
        }
        
    }

}
