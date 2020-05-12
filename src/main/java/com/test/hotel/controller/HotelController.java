package com.test.hotel.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.test.hotel.model.Hotel;
import com.test.hotel.model.HotelModel;
import com.test.hotel.service.HotelService;

@Controller
@RequestMapping("/")
public class HotelController {

	
	 private final HotelService hotelService;

	    public HotelController(HotelService hotelService){
	        super();
	        this.hotelService = hotelService;
	    }

	    @GetMapping(value={"/", "/index"})
	    public String getHomePage(Model model){

	        return "index";
	    }

	    @GetMapping(value="/guests")
	    public String getGuests(Model model){
	        List<Hotel> guests = this.hotelService.getAllGuests();
	        model.addAttribute("guests", guests);
	        return "guests-view";
	    }

	    @GetMapping(value="/guests/add")
	    public String getAddGuestForm(Model model){
	        return "guest-view";
	    }

	    @PostMapping(value="/guests")
	    public ModelAndView addGuest(HttpServletRequest request, Model model, @ModelAttribute HotelModel guestModel){
	        Hotel guest = this.hotelService.addGuest(guestModel);
	        model.addAttribute("guest", guest);
	        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
	        return new ModelAndView("redirect:/guests/" + guest.getId());
	    }

	    @GetMapping(value="/guests/{id}")
	    public String getGuest(Model model, @PathVariable long id){
	        Hotel guest = this.hotelService.getGuest(id);
	        model.addAttribute("guest", guest);
	        return "guest-view";
	    }

	    @PostMapping(value="/guests/{id}")
	    public String updateGuest(Model model, @PathVariable long id, @ModelAttribute HotelModel guestModel){
	        Hotel guest = this.hotelService.updateGuest(id, guestModel);
	        model.addAttribute("guest", guest);
	        model.addAttribute("guestModel", new HotelModel());
	        return "guest-view";
	    }
}
