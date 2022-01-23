package com.divya.demo.core.service;

import org.apache.commons.lang.StringUtils;
import org.apache.jackrabbit.api.JackrabbitSession;
import org.apache.jackrabbit.api.security.user.Authorizable;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.auth.core.spi.AuthenticationInfo;
import org.apache.sling.auth.core.spi.AuthenticationInfoPostProcessor;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component(service = AuthenticationInfoPostProcessor.class)
public class CustomAuthenticationInfoPostProcessor implements AuthenticationInfoPostProcessor {

    @Reference
    private ResourceResolverFactory resolverFactory;

    @Reference
    private ResourceResolver resourceResolver;

    private Session session;

    public static final Logger LOG = LoggerFactory.getLogger(CustomAuthenticationInfoPostProcessor.class);


    @Override
    public void postProcess(AuthenticationInfo info, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws LoginException {
        if (info == null) {
            LOG.debug("AuthenticationInfo is null. " + "Skip post processing this request.");
            return;
        }
        String userId = info.getUser();
        if (StringUtils.isNotBlank(userId)) {

            try {

                Map<String, Object> serviceParams = new HashMap<String, Object>();
                serviceParams.put(ResourceResolverFactory.SUBSERVICE, "custom-user-manager");
                ResourceResolver resolver = resolverFactory.getServiceResourceResolver(serviceParams);
                session = resolver.adaptTo(Session.class);

                UserManager userManager = ((JackrabbitSession) session).getUserManager();
                Authorizable user = userManager.getAuthorizable(userId);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

                user.setProperty("profile/lastloggedin", session.getValueFactory().createValue(sdf.format(new Date())));

                session.save();
                session.logout();


            } catch (Exception e) {
                LOG.debug("Error in CustomAuthenticationInfoPostProcessor"+e.getMessage());
            }

        }
    }
}
