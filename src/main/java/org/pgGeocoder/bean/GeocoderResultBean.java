/**
 * パッケージ名：org.geocoderService.bean
 * ファイル名  ：GeocoderResultBean.java
 * 
 * @author mbasa
 * @since Apr 28, 2021
 */
package org.pgGeocoder.bean;

import java.util.HashMap;
import java.util.Map;

import io.vertx.mutiny.sqlclient.Row;

/**
 * 説明：
 *
 */

public class GeocoderResultBean {

    private int code;
    private String address       = "";
    private String todofuken     = "";
    private String shikuchoson   = "";
    private String ooaza         = "";
    private String chiban        = "";
    private String go            = "";
    private String details       = "";
    private CoordinatesBean coordinates = new CoordinatesBean();
    
    /**
     * コンストラクタ
     *
     */
    public GeocoderResultBean() {
    }
    
    public static String createGeoJson(Row row) {

        GeocoderResultBean grb = new GeocoderResultBean();
        
        grb.setCode(row.getInteger("code"));
        
        if( row.getString("address") != null )
            grb.setAddress(row.getString("address"));
        if( row.getString("todofuken") != null )
            grb.setTodofuken(row.getString("todofuken"));
        if( row.getString("shikuchoson") != null )
            grb.setShikuchoson(row.getString("shikuchoson"));
        if( row.getString("ooaza") != null )
            grb.setOoaza(row.getString("ooaza"));
        if( row.getString("chiban") != null )
            grb.setChiban(row.getString("chiban"));
        if( row.getString("go") != null )
            grb.setGo(row.getString("go"));
        
        CoordinatesBean cb = new CoordinatesBean();
        cb.setX(row.getDouble("x"));
        cb.setY(row.getDouble("y"));
        
        grb.setCoordinates( cb );
        
        StringBuffer jaddr = new StringBuffer();
        
        if( grb != null ) {
            jaddr.append("{\"type\":\"Feature\",");
            jaddr.append("\"properties\":{");
            jaddr.append("\"code\":").append(grb.getCode()).append(",");            
            jaddr.append("\"address\":\"").
                append(grb.getAddress()).append("\",");
            jaddr.append("\"todofuken\":\"").
                append(grb.getTodofuken()).append("\",");
            jaddr.append("\"shikuchoson\":\"").
                append(grb.getShikuchoson()).append("\",");
            jaddr.append("\"ooaza\":\"").
                append(grb.getOoaza()).append("\",");
            jaddr.append("\"chiban\":\"").
                append(grb.getChiban()).append("\",");
            jaddr.append("\"go\":\"").
                append(grb.getGo()).append("\"},");
            jaddr.append("\"geometry\":{");
            jaddr.append("\"type\":\"Point\",");
            jaddr.append("\"coordinates\": [");
            jaddr.append(grb.getCoordinates().getX()).append(",");
            jaddr.append(grb.getCoordinates().getY()).append("] }");
            jaddr.append("}");
        }
        
        return jaddr.toString();
        
    }
    public static Map<String,Object> createJson(Row row) {
        GeocoderResultBean grb = new GeocoderResultBean();
        
        grb.setCode(row.getInteger("code"));
        
        if( row.getString("address") != null )
            grb.setAddress(row.getString("address"));
        if( row.getString("todofuken") != null )
            grb.setTodofuken(row.getString("todofuken"));
        if( row.getString("shikuchoson") != null )
            grb.setShikuchoson(row.getString("shikuchoson"));
        if( row.getString("ooaza") != null )
            grb.setOoaza(row.getString("ooaza"));
        if( row.getString("chiban") != null )
            grb.setChiban(row.getString("chiban"));
        if( row.getString("go") != null )
            grb.setGo(row.getString("go"));
        
        CoordinatesBean cb = new CoordinatesBean();
        cb.setX(row.getDouble("x"));
        cb.setY(row.getDouble("y"));
        
        grb.setCoordinates( cb );
        
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("result", grb);
        
        return map;                                
    }

    /**
     * @return code を取得する
     */
    public int getCode() {
        return code;
    }

    /**
     * @param code code を設定する
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * @return address を取得する
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address address を設定する
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return todofuken を取得する
     */
    public String getTodofuken() {
        return todofuken;
    }

    /**
     * @param todofuken todofuken を設定する
     */
    public void setTodofuken(String todofuken) {
        this.todofuken = todofuken;
    }

    /**
     * @return shikuchoson を取得する
     */
    public String getShikuchoson() {
        return shikuchoson;
    }

    /**
     * @param shikuchoson shikuchoson を設定する
     */
    public void setShikuchoson(String shikuchoson) {
        this.shikuchoson = shikuchoson;
    }

    /**
     * @return ooaza を取得する
     */
    public String getOoaza() {
        return ooaza;
    }

    /**
     * @param ooaza ooaza を設定する
     */
    public void setOoaza(String ooaza) {
        this.ooaza = ooaza;
    }

    /**
     * @return chiban を取得する
     */
    public String getChiban() {
        return chiban;
    }

    /**
     * @param chiban chiban を設定する
     */
    public void setChiban(String chiban) {
        this.chiban = chiban;
    }

    /**
     * @return go を取得する
     */
    public String getGo() {
        return go;
    }

    /**
     * @param go go を設定する
     */
    public void setGo(String go) {
        this.go = go;
    }

    /**
     * @return coordinates を取得する
     */
    public CoordinatesBean getCoordinates() {
        return coordinates;
    }

    /**
     * @param coordinates coordinates を設定する
     */
    public void setCoordinates(CoordinatesBean coordinates) {
        this.coordinates = coordinates;
    }
    
    public void setX( double x ) {
        this.coordinates.setX(x);
    }
    
    public void setY( double y ) {
        this.coordinates.setY(y);
    }
    /*
    public double getX() {
        return this.coordinates.getX();
    }
    
    public double getY() {
        return this.coordinates.getY();
    }
    */

    /**
     * @return details を取得する
     */
    public String getDetails() {
        return details;
    }

    /**
     * @param details details を設定する
     */
    public void setDetails(String details) {
        this.details = details;
    }

}
