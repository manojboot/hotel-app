package com.test.hotel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.test.hotel.model.Hotel;
import com.test.hotel.model.HotelModel;

@Service
public class HotelService {

	private static final String GUESTS = "/guest";
	private static final String ALLGUESTS = "/allGuest";
    private static final String SLASH = "/";

    @Value("${manumahrani.guest.service.url}")
    private String guestServiceUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public List<Hotel> getAllGuests(){
        String url = guestServiceUrl + GUESTS + ALLGUESTS;
        HttpEntity<String> request = new HttpEntity<>(null, null);
        return this.restTemplate.exchange(url, HttpMethod.GET, request, new ParameterizedTypeReference<List<Hotel>>() { }).getBody();
    }

    public Hotel addGuest(HotelModel guestModel){
        String url = guestServiceUrl + GUESTS;
        HttpEntity<HotelModel> request = new HttpEntity<>(guestModel, null);
        return this.restTemplate.exchange(url, HttpMethod.POST, request, Hotel.class).getBody();
    }

    public Hotel getGuest(long id) {
        String url = guestServiceUrl + GUESTS + SLASH + id;
        HttpEntity<String> request = new HttpEntity<>(null, null);
        return this.restTemplate.exchange(url, HttpMethod.GET, request, Hotel.class).getBody();
    }

    public Hotel updateGuest(long id, HotelModel guestModel) {
        System.out.println(guestModel);
        String url = guestServiceUrl + GUESTS + SLASH + id;
        HttpEntity<HotelModel> request = new HttpEntity<>(guestModel, null);
        return this.restTemplate.exchange(url, HttpMethod.PUT, request, Hotel.class).getBody();
    }
}
