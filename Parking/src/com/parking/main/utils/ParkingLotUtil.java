package com.parking.main.utils;

import java.util.ArrayList;
import java.util.List;

import com.parking.main.model.ParkingLotInformation;
import com.parking.main.model.ParkingLotInformation.Spot;
import com.parking.main.model.ParkingLotInformation.Spot.Booking;

public class ParkingLotUtil {
	
	public ParkingLotInformation getParkingLotInfoById (int id) {
		
		List<ParkingLotInformation> plist = getParkingLotsList();
		
		for (ParkingLotInformation plInfo : plist){
			if (plInfo.getId() == id){
				return plInfo;
			}
				
		}
		
		return null;
	}
	
	public List<ParkingLotInformation> getParkingLotsList (){

		List<ParkingLotInformation> pLots = new ArrayList<ParkingLotInformation> ();
		
		pLots.add(getPLData("Diamonp Parking Queen Anne",
				"3161 Elliott Avenue #100 Seattle, WA 98121 (206) 447-7275",
				"http://grfx.cstv.com/schools/utva/graphics/auto/DiamondParking.jpg",
				5
				));
		
		
 		
		return pLots;
	}

	private ParkingLotInformation getPLData(String name, String addr, String url, int num) {
		
		ParkingLotInformation pLot = new ParkingLotInformation();
		pLot.setName(name);
		pLot.setAddress(addr);
		pLot.setPhotoURL(url);
		pLot.setTotalSpots(num);
		
		pLot.setSpaces(getPSpaceData(pLot));
		
		return pLot;
	}

	private List<Spot> getPSpaceData(ParkingLotInformation pLot) {
		
		List<Spot> spots = new ArrayList<Spot>();
		
		Spot spt = pLot.new Spot();
		
		spt.setBookings(getBookingData(spt));
		
		spots.add(spt);
		
		return spots;
	}

	private List<Booking> getBookingData(Spot spt) {
		List<Booking> bookings = new ArrayList<Booking>();
		
		Booking bkg = spt.new Booking();
		
		bookings.add(bkg);
		
		return bookings;
	}

}
