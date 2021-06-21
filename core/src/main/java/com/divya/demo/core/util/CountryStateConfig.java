package com.divya.demo.core.util;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.osgi.service.metatype.annotations.Option;

@ObjectClassDefinition(
        name = "Country-State Configuration",
        description = "This configuration reads the values to make an HTTP call to a JSON webservice")
public @interface CountryStateConfig {

    /**
     * Returns the server
     *
     * @return {@link String}
     */
    @AttributeDefinition(
            name = "CountryStateMap",
            description = "Enter the Country:{States}")
    public String[] getCountryStateMap();

}
