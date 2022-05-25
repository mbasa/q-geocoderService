package org.pgGeocoder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.pgGeocoder.bean.GeocoderResultBean;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;

@ApplicationScoped
@Path("/service")
public class GeocoderResource {

    @Inject
    io.vertx.mutiny.pgclient.PgPool client;
    
    @GET
    @Path("/geocode/json/{address}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Object> geocodeJson(String address) {    
        return client.preparedQuery("select * from geocoder($1)")
                .execute(Tuple.of(address))
                .onItem().transform(RowSet::iterator) 
                .onItem().transform(iterator -> 
                iterator.hasNext() ? 
                        GeocoderResultBean.createJson(iterator.next()) : null);                 
    }
    
    @GET
    @Path("/geocode/geojson/{address}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Object> geocodeGeoJson(String address) {    
        return client.preparedQuery("select * from geocoder($1)")
                .execute(Tuple.of(address))
                .onItem().transform(RowSet::iterator) 
                .onItem().transform(iterator -> 
                iterator.hasNext() ? 
                        GeocoderResultBean.createGeoJson(iterator.next()) : null);                 
    }
    
    /*    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from RESTEasy Reactive";
    }
    */
}