package br.com.oyster.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.oyster.model.Region;
import br.com.oyster.model.Station;
import br.com.oyster.repository.RegionRepository;
import br.com.oyster.service.RegionService;

@Service
public class RegionServiceImpl implements RegionService {

	private final RegionRepository regionRepository;

	@Autowired
	public RegionServiceImpl(RegionRepository regionRepository) {
		this.regionRepository = regionRepository;
	}

	@Override
	public Region getRegion(String name) {
		return regionRepository.getRegion(name);

	}

	@Override
	public Region createRegion(String name) {
		Region region = new Region(name);
		return regionRepository.newRegion(region);
	}

	@Override
	public Station createStation(String name, int zone) {
		Station station = new Station(name, zone);
		return (Station)regionRepository.newRegion(station);
	}

	@Override
	public void printRegions(boolean justStation) {
		int i = 0;
		for(String region : regionRepository.getRegions(justStation)) {
			System.out.println(i + " - " + region);
			i++;
		}
	}


}
