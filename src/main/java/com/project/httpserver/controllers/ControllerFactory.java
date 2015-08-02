package com.project.httpserver.controllers;

public class ControllerFactory {

    private static final String HELLO = "/hello";
    private static final String STATUS = "/status";
    public static final String REDIRECT = "/redirect\\?url=.*";

    public static Controller getController(String uri) {
        switch (uri) {
            case HELLO:
                return new HelloController();
            case STATUS:
                return new StatusController();
            default:
                if (uri.matches(REDIRECT)) {
                    String url = uri.substring(14);

                    if (!url.matches("http://.*")) {
                        url = "http://" + url;
                    }

                    return new RedirectController(url);
                } else {
                    return new NotFoundController();
                }
        }

    }

}

