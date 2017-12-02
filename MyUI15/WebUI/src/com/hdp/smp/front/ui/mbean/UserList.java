package com.hdp.smp.front.ui.mbean;

import com.hdp.smp.model.Role;
import com.hdp.smp.model.User;
import com.hdp.smp.persistence.DAO;
import com.hdp.smp.persistence.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.hibernate.Session;

public class UserList extends ArrayList<User> {
    public static final Logger log = Logger.getLogger(UserList.class);

    private List<User> data = null;

    public UserList() {
        super();
        load();
        this.initColumn();
    }

    private UIComponent getUIComponent(String name) {
        FacesContext facesCtx = FacesContext.getCurrentInstance();
        UIComponent component = facesCtx.getViewRoot().findComponent(name);
        return component;
    }

    private void initColumn() {
        UIComponent colen = this.getUIComponent("c4");
        UIComponent colcn = this.getUIComponent("c5");
        if(colen==null || colcn ==null) return;
        log.info(" English column found:"+colen  +" , Chinese column found :"+colcn);
        if(   this.isCN() ) {
                colen.setRendered(false);
                colcn.setRendered(true);
            }
        else {
                colen.setRendered(true);
                colcn.setRendered(false);
            }
    }

    public void load() {
        if (data == null) {
            data = this.getAllUsers();
        }
        this.addAll(data);
    }

    private boolean isCN() {
        //convert by locale
        MBean mb = new MBean();
        String lang = mb.getUserLocale().getLanguage();
        log.info("user language=" + lang);
        boolean isCN = lang.equalsIgnoreCase("zh");
        return isCN;
    }

    public List<User> getAllUsers() {
        DAO dao = new DAO();
        Session session = HibernateUtil.getSession();
        List<User> users = dao.getAll(session, User.class);
        session.close();


        List<User> localizedUsers = new ArrayList<User>();

        for (User u : users) {
            User uiUser = new User();
            uiUser.setId(u.getId());
            uiUser.setName(u.getName());
            uiUser.setPasswd(u.getPasswd());
            uiUser.setRoleName(u.getRoleName());


            Role r = u.getRole();
            if (r == null) {
                log.info("user " + u + " role is null!");
                continue;
            }
            Role uiRole = new Role();
            uiRole.setId(r.getId());
            uiRole.setDescription_CN(r.getDescription_CN());
            uiRole.setDescription_EN(r.getDescription_EN());
            if (isCN()) {
                uiRole.setName(r.getName_CN());
            } else {
                uiRole.setName(r.getName_EN());
            }

            uiUser.setRole(uiRole);

            localizedUsers.add(uiUser);
        }

        return localizedUsers;

    }


}
