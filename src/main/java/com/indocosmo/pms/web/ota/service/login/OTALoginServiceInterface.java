package com.indocosmo.pms.web.ota.service.login;

import com.indocosmo.pms.web.ota.dto.hotel.HotelInfoDTO;

public interface OTALoginServiceInterface {
	
	public HotelInfoDTO gethotelinfo(String name, String password);
	
}
