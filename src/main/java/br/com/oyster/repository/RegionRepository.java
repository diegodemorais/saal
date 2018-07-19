package br.com.oyster.repository;


import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import br.com.oyster.model.Region;
import br.com.oyster.model.Station;

@Component
public class RegionRepository {
    private Set<Region> regions= new LinkedHashSet<>();

    public Region getRegion(String name) {
		Region found = null;
		int zoneCurrent, zoneNew;
		for(Region region : regions) {
			if (name.equals(region.getName())) {
				if (found == null) {
					found = region;
				} else {
					if (found instanceof Station) {
						zoneCurrent = ((Station)found).getZone();
						zoneNew = ((Station)region).getZone();
						if ( zoneNew < zoneCurrent || zoneCurrent == 1) {
							found = (Station)region;
						}
					}
				}
			}
		}
		return found;
    }

    public Region newRegion(Region region) {
        regions.add(region);
        return region;
    }
    
    public Set<String> getRegions(boolean justStation) {
    	Set<String> set = new LinkedHashSet<>();
		for(Region region : regions) {
			if (justStation) {
				if (region instanceof Station) {
					set.add(region.getName());
				}
			} else {
				set.add(region.getName());
			}
		}
		return set;
    }
    
}
