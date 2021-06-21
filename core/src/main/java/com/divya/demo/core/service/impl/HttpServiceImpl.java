package com.divya.demo.core.service.impl;

import com.divya.demo.core.service.HttpService;
import com.divya.demo.core.util.HttpConfiguration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = HttpService.class, immediate = true)
@Designate(ocd = HttpConfiguration.class)
public class HttpServiceImpl implements HttpService {
    /**
     * Logger
     */
    private static final Logger log = LoggerFactory.getLogger(HttpServiceImpl.class);

    /**
     * Instance of the OSGi configuration class
     */
    private HttpConfiguration configuration;

    @Activate
    protected void activate(HttpConfiguration configuration) {
        this.configuration = configuration;
    }

    /**
     * Overridden method of the HttpService
     */
    @Override
    public String makeHttpCall() {

        log.info("----------< Reading the config values >----------");

        try {

            /**
             * Reading values from the configuration
             */
            boolean enable = configuration.enableConfig();
            String protocol = configuration.getProtocol();
            String server = configuration.getServer();
            String endpoint = configuration.getEndpoint();

            /**
             * Constructing the URL
             */
            String url = protocol + "://" + server + "/" + endpoint;

            /**
             * Make HTTP call only if "enable" is true
             */
            if (enable) {
                /**
                 * Making the actual HTTP call
                 */
                String response = "Network.readJson(url)";

                /**
                 * Printing the response in the logs
                 */
                log.info("----------< JSON response from the webservice is >----------");
                log.info(response);

                return response;

            } else {

                log.info("----------< Configuration is not enabled >----------");

                return "Configuration not enabled";
            }

        } catch (Exception e) {

            log.error(e.getMessage(), e);

            return "Error occurred" + e.getMessage();
        }
    }
}
