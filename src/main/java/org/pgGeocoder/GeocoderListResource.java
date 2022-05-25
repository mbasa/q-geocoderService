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
}
