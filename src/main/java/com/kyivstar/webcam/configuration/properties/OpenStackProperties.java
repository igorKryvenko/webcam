package com.kyivstar.webcam.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by igor on 20.09.17.
 */
@ConfigurationProperties(prefix = "kyivstar.openstack")
public class OpenStackProperties {
    private Host host = new Host();
    private Identity identity = new Identity();
    private Boolean enableHttpLoggingFilter;
    private Swift swift = new Swift();

    public Host getHost() {
        return host;
    }

    public Identity getIdentity() {
        return identity;
    }

    public Swift getSwift() {
        return swift;
    }

    public Boolean getEnableHttpLoggingFilter() {
        return enableHttpLoggingFilter;
    }

    public OpenStackProperties setEnableHttpLoggingFilter(Boolean enableHttpLoggingFilter) {
        this.enableHttpLoggingFilter = enableHttpLoggingFilter;
        return this;
    }

    public static class Host {
        private String controller;
        private String auth;

        public String getController() {
            return controller;
        }

        public Host setController(String controller) {
            this.controller = controller;
            return this;
        }

        public String getAuth() {
            return auth;
        }

        public Host setAuth(String auth) {
            this.auth = auth;
            return this;
        }
    }

    public static class Identity {
        private String domain;
        private String username;
        private String password;

        public String getDomain() {
            return domain;
        }

        public Identity setDomain(String domain) {
            this.domain = domain;
            return this;
        }

        public String getUsername() {
            return username;
        }

        public Identity setUsername(String username) {
            this.username = username;
            return this;
        }

        public String getPassword() {
            return password;
        }

        public Identity setPassword(String password) {
            this.password = password;
            return this;
        }
    }

    public static class Swift {
        private String container;

        public String getContainer() {
            return container;
        }

        public Swift setContainer(String container) {
            this.container = container;
            return this;
        }
    }
}
