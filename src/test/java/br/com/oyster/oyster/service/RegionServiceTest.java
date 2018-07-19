package br.com.oyster.oyster.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import br.com.oyster.model.Station;
import br.com.oyster.repository.RegionRepository;
import br.com.oyster.service.RegionService;
import br.com.oyster.service.impl.RegionServiceImpl;

public class RegionServiceTest {

	private static final String REGION_NAME = "Chelsea";
	private static final String STATION_NAME = "Holborn";
	private static final int ZONE1 = 1;
	private static final int ZONE2 = 2;
	private RegionService regionService;

	@Before
	public void setUp() {
		regionService = new RegionServiceImpl(new RegionRepository());
	}

	@Test
	public void TestAllMethods() {
		// REGION
		regionService.createRegion(REGION_NAME);
		String expectedRegionName = REGION_NAME;
		assertEquals(expectedRegionName, regionService.getRegion(REGION_NAME).getName());
		
		// STATION
		regionService.createStation(STATION_NAME, ZONE1);
		regionService.createStation(STATION_NAME, ZONE2);

		String expectedStationName = STATION_NAME;
		assertEquals(expectedStationName, regionService.getRegion(STATION_NAME).getName());
		
		int expectedStationZone = ZONE2;
		assertEquals(expectedStationZone, ((Station)regionService.getRegion(STATION_NAME)).getZone());

	}


}
