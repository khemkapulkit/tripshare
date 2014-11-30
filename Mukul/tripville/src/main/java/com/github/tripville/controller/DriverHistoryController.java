package com.github.tripville.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;


import com.github.tripville.model.DriverHistory;
import com.github.tripville.model.MemberLogin;
import com.github.tripville.model.Trip;
import com.github.tripville.service.DriverService;
import com.github.tripville.service.TripService;

	@Controller
	@SessionAttributes("driverHistory")
	public class DriverHistoryController {
		
		@Autowired
		private TripService tripService;
		
		@Autowired
		private DriverService driverHistoryService;
		
		@Autowired
		private MemberLogin studentLogin;
		
		public String formattedStartDate;
		public String getFormattedDate(Date date){
			return new SimpleDateFormat("MM/dd/yyyy").format(date);
		}
		
		
		@RequestMapping(value="/updateDriverHistory", method=RequestMethod.GET)
		public ModelAndView updateDriverHistory(Model model, HttpSession session, @RequestParam("tripId") int tripId) {			
			try{
				ModelAndView modelAndView = new ModelAndView("updateDriverHistory", "student", studentLogin);
				modelAndView.setViewName("updateDriverHistory");
				
				Trip tripDetails = new Trip();
				tripDetails = tripService.getTripDetails(tripId);
				formattedStartDate = getFormattedDate(tripDetails.getStartDate());
				modelAndView.addObject("formattedStartDate", formattedStartDate);
				
				MemberLogin user = (MemberLogin) session.getAttribute("student");
				String loggedInUserId = tripService.getUserId(user.getUserName()); 
				System.out.println("loggedInUserId" + loggedInUserId);
				model.addAttribute("trip", tripDetails);
				
				
				DriverHistory driverHistory = driverHistoryService.findDriverHistoryForTrip(String.valueOf(tripId), loggedInUserId);
				
				System.out.println("driverHistory" + driverHistory);
				if(driverHistory == null){
					driverHistory = new DriverHistory();
					System.out.println("loggedInUserId" + loggedInUserId);
					System.out.println("String.valueOf(tripId)" + String.valueOf(tripId));
					System.out.println("tripDetails.getUserId()" + tripDetails.getUserId());
					
					driverHistory.setCopassId(loggedInUserId);
					driverHistory.setTripid(String.valueOf(tripId));
					driverHistory.setUserid(tripDetails.getUserId());
					driverHistory.setRating(0);
					
				}
				System.out.println("~~~~~~~~~ driverHistory.getRating()" +driverHistory.getRating() );
				System.out.println("~~~~~~~~~ driverHistory.getComments()" +driverHistory.getComments() );
				
				modelAndView.addObject("driverHistory", driverHistory);
				
				return modelAndView;
				
			}catch(Exception e){
				ModelAndView modelAndView = new ModelAndView();
				modelAndView.setViewName("redirect:/login.html");
				return modelAndView;
			}
			
		}
		

		@RequestMapping(value="/updateDriverHistory", method=RequestMethod.POST)
		public ModelAndView updateDriverHistory(@Valid @ModelAttribute("driverHistory") DriverHistory driverHistory, BindingResult result, Model model, HttpSession session,  @RequestParam("btnClk") String request) {			
			ModelAndView modelAndView = new ModelAndView();
			
			if (request.equalsIgnoreCase("Submit")) {
				System.out.println("Save" );
				System.out.println("driverHistory"+ driverHistory);
				driverHistoryService.save(driverHistory);
				modelAndView.setViewName("redirect:/viewtrip.html");
		    } else if (request.equalsIgnoreCase("Cancel")) {
				System.out.println("Cancel" );
				modelAndView.setViewName("redirect:/viewtrip.html");
			}
			return modelAndView;
		}
		
		
}
		
		

