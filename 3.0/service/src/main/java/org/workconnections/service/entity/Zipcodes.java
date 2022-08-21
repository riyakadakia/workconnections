package org.workconnections.service.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Zipcodes {

	@Id
	private String id;
	private String zip;
	private String lat;
	private String lng;
	private String city;
	private String state_id;
	private String state_name;
	private String zcta;
	private String parent_zcta;
	private String population;
	private String density;
	private String county_fips;
	private String county_name;
	private String county_weights;
	private String county_names_all;
	private String county_fips_all;
	private String imprecise;
	private String military;
	private String timezone;
	
	public Integer getId() throws Exception {
		Integer zipcodesId = Integer.valueOf(-1);
		if (this.id != null) {
			zipcodesId = Integer.valueOf(this.id);
		}
		return zipcodesId;
	}
	
	public void setId(Integer id) {
		this.id = String.valueOf(id);
	}
	
	public Integer getZip() {
		Integer zipcode = Integer.valueOf(-1);
		if (zip != null && !zip.isEmpty()) {
			zipcode = Integer.valueOf(zip);			
		}
		return zipcode;
	}
	
	public void setZip(Integer zipcode) {
		this.zip = String.valueOf(zipcode);
	}
	
	public Double getLat() {
		Double latd = Double.valueOf(-1);
		if (lat != null && lat.length() > 0 && !lat.isEmpty()) {
			latd = Double.valueOf(lat);
		}
		return latd;
	}
	
	public void setLat(Double latd) {
		this.lat = String.valueOf(latd);
	}
	
	public Double getLng() {
		Double lngd = Double.valueOf(-1);
		if (lng != null && lng.length() > 0 && !lng.isEmpty()) {
			lngd = Double.valueOf(lng);
		}
		return lngd;
	}
	
	public void setLng(Double lngd) {
		this.lng = String.valueOf(lngd);
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getState_id() {
		return state_id;
	}
	
	public void setState_id(String state_id) {
		this.state_id = state_id;
	}
	
	public String getState_name() {
		return state_name;
	}
	
	public void setState_name(String state_name) {
		this.state_name = state_name;
	}
	
	public String getZcta() {
		return zcta;
	}
	
	public void setZcta(String zcta) {
		this.zcta = zcta;
	}
	
	public String getParent_zcta() {
		return parent_zcta;
	}
	
	public void setParent_zcta(String parent_zcta) {
		this.parent_zcta = parent_zcta;
	}
	
	public String getPopulation() {
		return population;
	}
	
	public void setPopulation(String population) {
		this.population = population;
	}
	
	public String getDensity() {
		return density;
	}
	
	public void setDensity(String density) {
		this.density = density;
	}
	
	public String getCounty_fips() {
		return county_fips;
	}
	
	public void setCounty_fips(String county_fips) {
		this.county_fips = county_fips;
	}
	
	public String getCounty_name() {
		return county_name;
	}
	
	public void setCounty_name(String county_name) {
		this.county_name = county_name;
	}
	
	public String getCounty_weights() {
		return county_weights;
	}
	
	public void setCounty_weights(String county_weights) {
		this.county_weights = county_weights;
	}
	
	public String getCounty_names_all() {
		return county_names_all;
	}
	
	public void setCounty_names_all(String county_names_all) {
		this.county_names_all = county_names_all;
	}
	
	public String getCounty_fips_all() {
		return county_fips_all;
	}
	
	public void setCounty_fips_all(String county_fips_all) {
		this.county_fips_all = county_fips_all;
	}
	
	public String getImprecise() {
		return imprecise;
	}
	
	public void setImprecise(String imprecise) {
		this.imprecise = imprecise;
	}
	
	public String getMilitary() {
		return military;
	}
	
	public void setMilitary(String military) {
		this.military = military;
	}
	
	public String getTimezone() {
		return timezone;
	}
	
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	
}
