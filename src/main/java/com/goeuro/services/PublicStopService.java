package com.goeuro.services;

import com.goeuro.entities.PublicStop;

public interface PublicStopService {
	public PublicStop save(PublicStop entry);
	public boolean routeExists(String dep_sid, String arr_sid);
	public void cleanUp();
}
