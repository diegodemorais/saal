package br.com.oyster.service;

import br.com.oyster.model.Region;
import br.com.oyster.model.Station;

public interface RegionService {
    Region getRegion(String name);
    Region createRegion(String name);
	Station createStation(String name, int zone);
	void printRegions(boolean justStation);
}
