/**
 * パッケージ名：org.pgGeocoder
 * ファイル名  ：GeocoderListResource.java
 * 
 * @author mbasa
 * @since May 25, 2022
 */
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

/**
 * 説明：
 *
 */
@ApplicationScoped
@Path("/list")
public class GeocoderListResource {

    @Inject
    io.vertx.mutiny.pgclient.PgPool client;

    /**
     * コンストラクタ
     *
     */
    public GeocoderListResource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Object> listTodofuken() {
        
        String sql = "with tt as (select todofuken,lat,lon from "
                + "address_t order by id) "
                + "select cast(array_to_json(array_agg(tt)) as text) from tt";
        
        return client.query(sql).execute()
            .onItem().transform(RowSet::iterator) 
            .onItem().transform(iterator -> 
            iterator.hasNext() ? 
                GeocoderResultBean.createJsonList(iterator.next()) : null);                
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{todofuken}")
    public Uni<Object> listShikuchoson( String todofuken ) {
        
        String sql = "select cast(array_to_json("
                + "array_agg(address_s order by shikuchoson)) as text) "
                + "from address_s where todofuken = $1";
        
        return client.preparedQuery(sql).execute(Tuple.of(todofuken))
            .onItem().transform(RowSet::iterator) 
            .onItem().transform(iterator -> 
            iterator.hasNext() ? 
                GeocoderResultBean.createJsonList(iterator.next()) : null);                
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{todofuken}/{shikuchoson}")
    public Uni<Object> listOoaza( String todofuken, String shikuchoson ) {
        
        String sql = "with tt as (select todofuken,shikuchoson,ooaza,lat,lon "
                + "from address_o where todofuken = $1 and shikuchoson = $2 "
                + "order by ooaza) select cast(array_to_json(array_agg(tt)) "
                + "as text) from tt;";
        
        return client.preparedQuery(sql)
                .execute(Tuple.of(todofuken,shikuchoson) )
                .onItem().transform(RowSet::iterator) 
                .onItem().transform(iterator -> 
                iterator.hasNext() ? 
                GeocoderResultBean.createJsonList(iterator.next()) : null);                
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{todofuken}/{shikuchoson}/{ooaza}")
    public Uni<Object> listBanchi( String todofuken, String shikuchoson,
            String ooaza) {
        
        String sql = "with tt as (select todofuken,shikuchoson,"
                + "ooaza,chiban as banchi, lat,lon "
                + "from address where todofuken = $1 and shikuchoson = $2 "
                + "and ooaza = $3 order by cast(chiban as numeric)) "
                + "select cast(array_to_json(array_agg(tt)) "
                + "as text) from tt;";
        
        return client.preparedQuery(sql)
                .execute(Tuple.of(todofuken,shikuchoson,ooaza) )
                .onItem().transform(RowSet::iterator) 
                .onItem().transform(iterator -> 
                iterator.hasNext() ? 
                GeocoderResultBean.createJsonList(iterator.next()) : null);                
    }
}
