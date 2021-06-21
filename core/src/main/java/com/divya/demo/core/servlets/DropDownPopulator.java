package com.divya.demo.core.servlets;


import com.divya.demo.core.service.StateService;
import com.drew.lang.annotations.NotNull;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.json.JSONArray;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = { Servlet.class },immediate = true)
@SlingServletPaths(
        value = {"/bin/populateStates","/demo/states"}
)
@ServiceDescription("DropDown Demo Servlet")
public class DropDownPopulator extends SlingAllMethodsServlet {
    private Logger logger = LoggerFactory.getLogger(DropDownPopulator.class);

    @Reference
    StateService stateService;

    protected void doGet(@NotNull SlingHttpServletRequest request, @NotNull SlingHttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            /*String US_STATES[] = {"0=Alabama",
                    "1=Alaska",
                    "2=Arizona",
                    "3=Arkansas",
                    "4=California",
                    "5=Colorado",
                    "6=Connecticut",
                    "7=Delaware",
                    "8=Florida",
                    "9=Georgia",
                    "10=Hawaii",
                    "11=Idaho",
                    "12=Illinois",
                    "13=Indiana",
                    "14=Iowa",
                    "15=Kansas",
                    "16=Kentucky",
                    "17=Louisiana",
                    "18=Maine",
                    "19=Maryland",
                    "20=Massachusetts",
                    "21=Michigan",
                    "22=Minnesota",
                    "23=Mississippi",
                    "24=Missouri",
                    "25=Montana",
                    "26=Nebraska",
                    "27=Nevada",
                    "28=New Hampshire",
                    "29=New Jersey",
                    "30=New Mexico",
                    "31=New York",
                    "32=North Carolina",
                    "33=North Dakota",
                    "34=Ohio",
                    "35=Oklahoma",
                    "36=Oregon",
                    "37=Pennsylvania",
                    "38=Rhode Island",
                    "39=South Carolina",
                    "40=South Dakota",
                    "41=Tennessee",
                    "42=Texas",
                    "43=Utah",
                    "44=Vermont",
                    "45=Virginia",
                    "46=Washington",
                    "47=West Virginia",
                    "48=Wisconsin",
                    "49=Wyoming"};
            String AUSTRALIAN_STATES[] = {"0=Ashmore and Cartier Islands",
                    "1=Australian Antarctic Territory",
                    "2=Australian Capital Territory",
                    "3=Christmas Island",
                    "4=Cocos (Keeling) Islands",
                    "5=Coral Sea Islands",
                    "6=Heard Island and McDonald Islands",
                    "7=Jervis Bay Territory",
                    "8=New South Wales",
                    "9=Norfolk Island",
                    "10=Northern Territory",
                    "11=Queensland",
                    "12=South Australia",
                    "13=Tasmania",
                    "14=Victoria",
                    "15=Western Australia"};*/
             String country = request.getParameter("country");
            /*JSONArray stateJsonArray = new JSONArray();
            if (country.length() > 0) {
                if ("australia".equalsIgnoreCase(country)) {
                    stateJsonArray = new JSONArray();
                    for (String state : AUSTRALIAN_STATES) {
                        stateJsonArray.put(state);
                    }
                } else if ("unitedstates".equalsIgnoreCase(country)) {
                    stateJsonArray = new JSONArray();
                    for (String state : US_STATES) {
                        stateJsonArray.put(state);
                    }
                }
                response.setContentType("application/json");
                response.getWriter().write(stateJsonArray.toString());
            }*/

            String[] states = stateService.getStates(country);
            JSONArray stateJsonArray = new JSONArray();
            for (String state : states) {
                stateJsonArray.put(state);
            }
            if(states!=null){
                response.setContentType("application/json");
                response.getWriter().write(stateJsonArray.toString());
            }

        } catch ( Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}
