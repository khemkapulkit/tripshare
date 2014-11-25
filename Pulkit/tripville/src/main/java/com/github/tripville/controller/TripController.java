package com.github.tripville.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;




import com.github.tripville.model.Member;
import com.github.tripville.model.MemberLogin;
import com.github.tripville.model.SearchTrip;
import com.github.tripville.model.Trip;
import com.github.tripville.service.TripService;

	@Controller
	@SessionAttributes("trip")
	public class TripController {
		
		@Autowired
		private TripService tripService;
		
			 
		 @Autowired
			private SearchTrip searchtrip;
		 
	
		 
		@RequestMapping(value="/addtrip", method=RequestMethod.GET)
		public String addtrip(Model model, HttpSession session) {			
			try{
				Trip tripDetails = new Trip();
				MemberLogin user = (MemberLogin) session.getAttribute("student");
				String loggedInUserId = tripService.getUserId(user.getUserName()); 
				System.out.println("loggedInUserId" + loggedInUserId);
				tripDetails.setUserId(loggedInUserId);
				model.addAttribute("trip", tripDetails);
				return "addtrip";
			}catch(Exception e){
				return "redirect:index.html";
			}
			
		}
		
		
		@RequestMapping(value="/addtrip", method=RequestMethod.POST)
		public String addtrip(@Valid @ModelAttribute("trip") Trip tripDetails, BindingResult result, Model model, @RequestParam("btnClk") String request) {
			System.out.println("btnClk" + request);
			if (request.equalsIgnoreCase("Submit")) {
				System.out.println("Save" );
				if (result.hasErrors()) {
					System.out.println(result.getAllErrors());
				} else {
					tripService.save(tripDetails);
					tripDetails = new Trip();
					model.addAttribute("trip", tripDetails);
					model.addAttribute("message", "Created new trip.");
				} 

		    } else if (request.equalsIgnoreCase("Reset")) {
		    	System.out.println("reset" );	
		    	tripDetails = new Trip();
				model.addAttribute("trip", tripDetails);
				return "redirect:addtrip.html";
		    } else if (request.equalsIgnoreCase("Cancel")) {
				System.out.println("Cancel" );
				return "redirect:home.html";
			}
			return "addtrip";
			
		}
		
		
		@RequestMapping(value="/searchtrip", method=RequestMethod.GET)
		public String searchtrip(Model model, HttpSession session) {			
			searchtrip = new SearchTrip();		
			model.addAttribute("searchtrip", searchtrip);
			return "searchtrip";
		}
		
		
		@RequestMapping(value="/searchtrip", method=RequestMethod.POST)
		public ModelAndView searchtrip(@Valid @ModelAttribute("searchtrip") SearchTrip searchtrip, BindingResult result, Model model, @RequestParam("btnClk") String request, HttpSession session) {
			ModelAndView modelAndView = new ModelAndView(); 
			System.out.println("btnClk" + request);
			if (request.equalsIgnoreCase("Search")) {
				if (result.hasErrors()) {
					System.out.println(result.getAllErrors());
				} else {
					List<Trip> trip = tripService.searchTrip(searchtrip.getFromAddress());
					for(int i = 0; i < trip.size(); i++) {
			            System.out.println(trip.get(i).getTripId());
			        }
					if (!trip.isEmpty()) {
						modelAndView.setViewName("searchtrip");
						MemberLogin user = (MemberLogin) session.getAttribute("student");
						modelAndView.addObject("searchlist", trip);
						return modelAndView;
					} else {				
						modelAndView.setViewName("searchtrip");
						return modelAndView;
					}
				} 

		    } else if (request.equalsIgnoreCase("Reset")) {
		    	searchtrip = new SearchTrip();		
				model.addAttribute("searchtrip", searchtrip);
				modelAndView.setViewName("searchtrip");
				return modelAndView;
		    } else if (request.equalsIgnoreCase("Cancel")) {
				System.out.println("Cancel" );
				modelAndView.setViewName("home");
				return modelAndView;
			}
			modelAndView.setViewName("searchtrip");
			return modelAndView;
		}
	}

