package com.ittc.ipkb.textParsingUtility.beans;

/**
 * 
 * @author rsompalli
 *
 * Java bean class that holds the information related a taxonomy like class, order, family etc.,
 */
public class TaxonomyBean 
{
	private String taxonomyType;
	private String taxonomyName;
	private String authorInfo;
	private String hyperLinkInfo;
	private String description;
	private String timePeriod;
	
	public String getTaxonomyType() {
		return taxonomyType;
	}
	public void setTaxonomyType(String taxonomyType) {
		this.taxonomyType = taxonomyType;
	}
	public String getTaxonomyName() {
		return taxonomyName;
	}
	public void setTaxonomyName(String taxonomyName) {
		this.taxonomyName = taxonomyName;
	}
	public String getAuthorInfo() {
		return authorInfo;
	}
	public void setAuthorInfo(String authorInfo) {
		this.authorInfo = authorInfo;
	}
	public String getHyperLinkInfo() {
		return hyperLinkInfo;
	}
	public void setHyperLinkInfo(String hyperLinkInfo) {
		this.hyperLinkInfo = hyperLinkInfo;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTimePeriod() {
		return timePeriod;
	}
	public void setTimePeriod(String timePeriod) {
		this.timePeriod = timePeriod;
	}
}
