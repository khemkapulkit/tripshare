package com.github.tripville.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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


import com.github.tripville.model.MemberLogin;
import com.github.tripville.model.SearchTrip;
import com.github.tripville.model.Trip;
import com.github.tripville.model.TripReq;
import com.github.tripville.service.DriverService;
import com.github.tripville.service.TripReqService;
import com.github.tripville.service.TripService;

	@Controller
	@SessionAttributes("trip")
	public class TripController {
		
		@Autowired
		private TripService tripService;
		
		@Autowired
		private TripReqService tripReqService;
		
		@Autowired
		private SearchTrip searchtrip;
		 
		 
		public static String[] selectedtrips;
		@Autowired
		private DriverService driverHistoryService;
		
		@Autowired
		private MemberLogin studentLogin;
		 
		public ArrayList<Trip> driverTripList;
		public ArrayList<TripReq> tripRequestList;
		public String formattedStartDate;
		public ArrayList<TripReq> passengerTripList;
		
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
				return "redirect:login.html";
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
		
		@RequestMapping(value="/manageTrip", method=RequestMethod.GET)
		public ModelAndView manageTrip(Model model, HttpSession session, @RequestParam("tripId") int tripId) {			
			try{
				ModelAndView modelAndView = new ModelAndView("manageTrip", "student", studentLogin);
				modelAndView.setViewName("manageTrip");
				
				Trip tripDetails = new Trip();
				
				tripDetails = tripService.getTripDetails(tripId);
				formattedStartDate = getFormattedDate(tripDetails.getStartDate());
				
				modelAndView.addObject("formattedStartDate", formattedStartDate);
				
				System.out.println("~~~~~~~~~~~~~~" +  formattedStartDate);
				
				tripRequestList = tripReqService.getTripRequestsForTrip(tripId);  
				modelAndView.addObject("tripRequestList", tripRequestList);
				
				System.out.println("tripRequestList" + tripRequestList + tripRequestList.size());
				
				MemberLogin user = (MemberLogin) session.getAttribute("student");
				String loggedInUserId = tripService.getUserId(user.getUserName()); 
				System.out.println("loggedInUserId" + loggedInUserId);
				tripDetails.setUserId(loggedInUserId);
				model.addAttribute("trip", tripDetails);
				return modelAndView;
				
			}catch(Exception e){
				ModelAndView modelAndView = new ModelAndView();
				modelAndView.setViewName("redirect:/login.html");
				return modelAndView;
			}
			
		}
		

		@RequestMapping(value="/manageTrip", method=RequestMethod.POST)
		public ModelAndView home(@Valid @ModelAttribute("student") MemberLogin studentLogin, BindingResult result, Model model, HttpSession session) {			
			ModelAndView modelAndView = new ModelAndView("viewtrip", "student", studentLogin);
			modelAndView.setViewName("redirect:/viewtrip.html");
			MemberLogin user = (MemberLogin) session.getAttribute("student");
			String loggedInUserId = tripService.getUserId(user.getUserName()); 
			driverTripList = tripService.getTripsforDriver(loggedInUserId);
			modelAndView.addObject("driverTripList", driverTripList);
			passengerTripList = tripReqService.getTripsforPassenger(loggedInUserId);
			modelAndView.addObject("passengerTripList", passengerTripList);
			return modelAndView;
		}
		
		@RequestMapping(value="/viewtrip", method=RequestMethod.GET)
		public ModelAndView viewtrip(Model model, HttpSession session) {			
			
			try{
				
				ModelAndView modelAndView = new ModelAndView("viewtrip", "student", studentLogin);
				modelAndView.setViewName("viewtrip");
				MemberLogin user = (MemberLogin) session.getAttribute("student");
				String loggedInUserId = tripService.getUserId(user.getUserName()); 
				driverTripList = tripService.getTripsforDriver(loggedInUserId);
				modelAndView.addObject("driverTripList", driverTripList);
				passengerTripList = tripReqService.getTripsforPassenger(loggedInUserId);
				modelAndView.addObject("passengerTripList", passengerTripList);
				
				return modelAndView;
			}
			catch(Exception e){
				ModelAndView modelAndView = new ModelAndView();
				modelAndView.setViewName("redirect:/login.html");
				return modelAndView; // Adding comment
			}
		}

		
		@RequestMapping(value="/viewtrip", method=RequestMethod.POST)
		public ModelAndView viewtrip(@Valid @ModelAttribute("student") MemberLogin studentLogin, BindingResult result, Model model, HttpSession session) {			
			
			try{
				
				ModelAndView modelAndView = new ModelAndView("viewtrip", "student", studentLogin);
				modelAndView.setViewName("redirect:/home.html");
				MemberLogin user = (MemberLogin) session.getAttribute("student");
				return modelAndView;
			}
			catch(Exception e){
				ModelAndView modelAndView = new ModelAndView();
				modelAndView.setViewName("redirect:/login.html");
				return modelAndView; // Adding comment
			}
		}
		
		public String getFormattedDate(Date date){
			return new SimpleDateFormat("MM/dd/yyyy").format(date);
		}
		
		
		@RequestMapping(value="/processTripRequest", method=RequestMethod.GET)
		public ModelAndView processTripRequest(Model model, HttpSession session, @RequestParam("reqId") int reqId,  @RequestParam("status") String status) {			
			try{
				
				TripReq tripRequestObj =  tripReqService.getTripRequestDetails(reqId);
				if(status.contains("Approve")){
					status = "Approved";
				}else if(status.contains("Decline")){
					status = "Declined";
				}
				tripRequestObj.setStatus(status);
				tripReqService.save(tripRequestObj);
				ModelAndView modelAndView = new ModelAndView("manageTrip", "student", studentLogin);
				modelAndView.setViewName("redirect:/manageTrip.html?tripId="+tripRequestObj.getTripid());
				MemberLogin user = (MemberLogin) session.getAttribute("student");
				return modelAndView;
			}
			catch(Exception e){
				ModelAndView modelAndView = new ModelAndView();
				modelAndView.setViewName("redirect:/login.html");
				return modelAndView;
			}
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
					List<Trip> trip = tripService.searchTrip(searchtrip.getFromAddress(), searchtrip.getToAddress());
					for(int i = 0; i < trip.size(); i++) {
			            System.out.println(trip.get(i).getTripId());
			        }
					if (!trip.isEmpty()) {
						modelAndView.setViewName("searchtrip");
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
		
		@RequestMapping(value="/sendrequest", method=RequestMethod.GET)
		public  ModelAndView sendrequest(Model model, HttpSession session, @RequestParam Map<String, String> map) {			
			ModelAndView modelAndView = new ModelAndView();
			try{
				TripReq tripRequest = new TripReq();
				MemberLogin user = (MemberLogin) session.getAttribute("student");
				String loggedInUserId = tripService.getUserId(user.getUserName()); 
				System.out.println("loggedInUserId" + loggedInUserId);
				tripRequest.setCopassid(loggedInUserId);
				tripRequest.setTripid(Integer.parseInt(map.get("id")));
				//tripRequest.setTripreqid(55);
				tripRequest.setStatus("Pending");
				tripService.save(tripRequest);
				//ModelAndView modelAndView = new ModelAndView("manageTrip", "student", studentLogin);
				modelAndView.setViewName("redirect:/home.html");
				
				return modelAndView;
				//return "home";
			}catch(Exception e){
				//ModelAndView modelAndView = new ModelAndView();
				modelAndView.setViewName("redirect:/login.html");
				return modelAndView;
			}			
		}
		

}
	
	

