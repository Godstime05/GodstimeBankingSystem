package com.godtime.GodstimeBankingSystem.repository;

import com.godtime.GodstimeBankingSystem.model.facility.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {

    Optional<Facility> findFacilityByFacilityName(String facilityName);
}