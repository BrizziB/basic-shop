package rest.service;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class CorsFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestCtx, ContainerResponseContext responseCtx) throws IOException {

        responseCtx.getHeaders().add("Access-Control-Expose-Headers", "Set-Cookie, JSESSIONID"); //espone il campo set-cookie dell'header dei messaggi http
        responseCtx.getHeaders().add("Access-Control-Allow-Origin", "http://localhost:4200"); //permette di NON filtrare le richieste provenienti da localhost:4200
        responseCtx.getHeaders().add("Access-Control-Allow-Credentials", "true");//permette la lettura dei cookie
        responseCtx.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");//permette i metodi http get, post, put, delete
        responseCtx.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, Set-Cookie, JSESSIONID");//permette la lettura degli header riportati
        
    }
}