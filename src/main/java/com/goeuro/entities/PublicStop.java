package com.goeuro.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;


@Entity
@Table(indexes = {@Index(name = "stopNo",  columnList="stopNo", unique = false)})

public class PublicStop  {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String stopNo;
	private String routeNo;
	private long stopNoOrder;

	public PublicStop()
    {
    	
    }

	public PublicStop(String stopNo) {
		this.stopNo = stopNo;
	}

	public PublicStop(String stopNo, String routeNo) {
		this.stopNo = stopNo;
		this.routeNo = routeNo;
	}

	public PublicStop(String stopNo, String routeNo, long stopNoOrder) {
		this.stopNo = stopNo;
		this.routeNo = routeNo;
		this.stopNoOrder = stopNoOrder;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStopNo() {
		return stopNo;
	}

	public void setStopNo(String stopNo) {
		this.stopNo = stopNo;
	}

	public String getRouteNo() {
		return routeNo;
	}

	public void setRouteNo(String routeNo) {
		this.routeNo = routeNo;
	}

	public long getStopNoOrder() {
		return stopNoOrder;
	}

	public void setStopNoOrder(long stopNoOrder) {
		this.stopNoOrder = stopNoOrder;
	}
}