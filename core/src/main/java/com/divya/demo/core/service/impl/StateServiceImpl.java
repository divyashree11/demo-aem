package com.divya.demo.core.service.impl;

import com.divya.demo.core.service.HttpService;
import com.divya.demo.core.service.StateService;
import com.divya.demo.core.util.CountryStateConfig;
import com.divya.demo.core.util.HttpConfiguration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Component(service = StateService.class, immediate = true)
@Designate(ocd = CountryStateConfig.class)
public class StateServiceImpl implements StateService {
    /**
     * Logger
     */
    private static final Logger log = LoggerFactory.getLogger(StateServiceImpl.class);

    /**
     * Instance of the OSGi configuration class
     */
    private CountryStateConfig configuration;

    @Activate
    protected void activate(CountryStateConfig configuration) {
        this.configuration = configuration;
    }


    @Override
    public String[] getStates(String country) {

        log.info("----------< Reading the config values >----------");

        try {

            /**
             * Reading values from the configuration
             */

            String[] CountryStateMap = configuration.getCountryStateMap();

            List<String> stateList = new ArrayList<>();
            for(String countryStateMap:CountryStateMap){
                if(countryStateMap!=null && country!=null && country.equals(countryStateMap.split("::")[0])){
                    //int i = 0;
                    for(String state:countryStateMap.split("::")[1].split(",")){
                        //states[i]=state;
                        //i++;
                        stateList.add(state);
                    }
                }
            }
            String[] states = new String[stateList.size()];
            int count = 0;
            for(String s:stateList){
                states[count++]=s;
            }
            return states;
        } catch (Exception e) {

            log.error(e.getMessage(), e);

            //return "Error occurred" + e.getMessage();
        }
        return new String[0];
    }
}
