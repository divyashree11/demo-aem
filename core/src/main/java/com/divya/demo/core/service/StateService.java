package com.divya.demo.core.service;

public interface StateService {

    /**
     * This method makes the HTTP call on the given URL
     *
     * @param url
     * @return {@link String}
     */
    public String[] getStates(String country);
}
