package com.pluralsight.bookstore.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

//everything after 'api' in the uri is the JAX-RS endpoint
@ApplicationPath("api")
//extends JAX-RS application class
public class JAXRSConfiguration extends Application {

}
