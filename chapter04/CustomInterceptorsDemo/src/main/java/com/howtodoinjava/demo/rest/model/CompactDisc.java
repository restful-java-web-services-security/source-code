package com.howtodoinjava.demo.rest.model;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "compactDisc")
public class CompactDisc implements Serializable {
	private static final long serialVersionUID = 1L;

	@XmlElement(name = "discName")
	private String discName;

	@XmlElement(name = "bandName")
	private String bandName;

	@XmlElement(name = "releaseDate")
	private Date releaseDate;

	@XmlElement(name = "price")
	private double price;

	/**
	 * @return the discName
	 */
	public String getDiscName() {
		return discName;
	}

	/**
	 * @param discName
	 *            the discName to set
	 */
	public void setDiscName(String discName) {
		this.discName = discName;
	}

	/**
	 * @return the bandName
	 */
	public String getBandName() {
		return bandName;
	}

	/**
	 * @param bandName
	 *            the bandName to set
	 */
	public void setBandName(String bandName) {
		this.bandName = bandName;
	}

	/**
	 * @return the releaseDate
	 */
	public Date getReleaseDate() {
		return releaseDate;
	}

	/**
	 * @param releaseDate
	 *            the releaseDate to set
	 */
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

}
