package com.ecua3d.quote.security.audit;


import com.ecua3d.quote.model.AuditingFields;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.HibernateException;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.*;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;
import java.util.Optional;

@Component
public class SecurityAuditListenerImp implements PreInsertEventListener, PersistEventListener {

    @Lazy
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private AuditorAware<String> auditorAware;

    @PostConstruct
    private void init() {
        SessionFactoryImpl sessionFactory = this.entityManagerFactory.unwrap(
                SessionFactoryImpl.class);
        EventListenerRegistry registry = sessionFactory.getServiceRegistry()
                .getService(EventListenerRegistry.class);
        registry.prependListeners(EventType.PERSIST, this);
    }

    @Override
    public void onPersist(PersistEvent event) throws HibernateException {
        if (event.getObject() instanceof AuditingFields) {
            this.loadInsertAuditFields((AuditingFields) event.getObject());
        }
    }

    @Override
    public void onPersist(PersistEvent event, PersistContext createdAlready) throws HibernateException {

    }

    @Override
    public boolean onPreInsert(PreInsertEvent preInsertEvent) {
        if (preInsertEvent.getEntity() instanceof AuditingFields) {
            this.loadInsertAuditFields((AuditingFields) preInsertEvent.getEntity());
        }
        return false;
    }


    private void loadInsertAuditFields(AuditingFields audit) {
        if(audit.getCreateByUser() != null) return;
        Optional<String> auditUser = auditorAware.getCurrentAuditor();
        if(auditUser.isEmpty()) throw new RuntimeException("User who is Creating was not found", new Exception());
        audit.setCreateByUser(auditUser.get());
        audit.setCreatedFromIp(getIp());
        audit.setCreatedDate(new Date());
    }

    private String getIp() {
        String ipAddress;
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                    .getRequest();
            ipAddress = request.getHeader("x-original-forwarded-for");
            if (StringUtils.isBlank(ipAddress)) {
                ipAddress = request.getHeader("X-FORWARDED-FOR");
            }
            if (StringUtils.isBlank(ipAddress)) {
                ipAddress = request.getRemoteAddr();
            } else {
                ipAddress = ipAddress.split(",")[0];
            }
        } catch (Exception e) {
            ipAddress = "ND";
        }
        return StringUtils.isBlank(ipAddress) ? "ND" : ipAddress;
    }
}