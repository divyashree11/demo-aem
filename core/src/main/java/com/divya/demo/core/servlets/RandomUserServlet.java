package com.divya.demo.core.servlets;


import com.divya.demo.core.service.RandomUserService;
import com.divya.demo.core.service.StateService;
import com.drew.lang.annotations.NotNull;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component(service = { Servlet.class },immediate = true)
@SlingServletPaths(
        value = {"/bin/userapi"}
)
@ServiceDescription("Random User Servlet")
public class RandomUserServlet extends SlingAllMethodsServlet {
    private Logger logger = LoggerFactory.getLogger(RandomUserServlet.class);

    @Reference
    RandomUserService randomUserService;

    @Reference
    private ResourceResolverFactory resolverFactory;

    protected void doGet(@NotNull SlingHttpServletRequest request, @NotNull SlingHttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {


            String users = randomUserService.getUsers();
            logger.info("User details="+users.toString());
            if(users!=null){
                Map<String, Object> serviceParams = new HashMap<String, Object>();
                serviceParams.put(ResourceResolverFactory.SUBSERVICE, "readService");
                ResourceResolver resolver = resolverFactory.getServiceResourceResolver(serviceParams);
                Resource resource= resolver.getResource("/content/demo/us/en/jcr:content");

                Node node = resource.adaptTo(Node.class);
                //parse json
                JSONObject json = null;
                try {
                    json = new JSONObject(users).getJSONArray("results").getJSONObject(0);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                node.setProperty("gender",json.getString("gender"));
                Session session = resolver.adaptTo(Session.class);
                session.save();

            }

            response.setContentType("application/json");
            response.getWriter().write(users.toString());
        } catch ( Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}
