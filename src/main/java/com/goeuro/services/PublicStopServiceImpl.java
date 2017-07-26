package com.goeuro.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.goeuro.entities.PublicStop;
import com.goeuro.repo.PublicStopRepository;

@Repository
public class PublicStopServiceImpl implements PublicStopService {

	@Autowired
	private PublicStopRepository publicStopRepository;

	@Override
	public PublicStop save(PublicStop entry) {
		return publicStopRepository.save(entry);
	}

	@Override
	public boolean routeExists(String dep_sid, String arr_sid) {
		
		if ( ((ArrayList<PublicStop>) publicStopRepository.find(dep_sid, arr_sid)).size() > 0 )
		{
			return true;
		}
		
		return false;
	}

	@Override
	public void cleanUp() {
		publicStopRepository.deleteAll();
	}
}