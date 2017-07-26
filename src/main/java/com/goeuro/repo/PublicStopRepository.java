package com.goeuro.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.goeuro.entities.PublicStop;


public interface PublicStopRepository extends JpaRepository<PublicStop, Long> {
	
    @Query("select routeNo from PublicStop p where stopNo in (:departureStopNo) and exists(select routeNo from PublicStop where routeNo = p.routeNo and stopNo in (:arrivalStopNo))")
    public List<PublicStop> find(@Param("departureStopNo") String dep_sid, @Param("arrivalStopNo") String arr_sid);
    
    @Query(value="select * from public_stop limit 1;", nativeQuery = true)
    public PublicStop findOne();
}