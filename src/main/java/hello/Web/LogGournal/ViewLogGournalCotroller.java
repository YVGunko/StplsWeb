package hello.Web.LogGournal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import hello.utils;
import hello.Device.Device;
import hello.Device.DeviceService;
import hello.LogGournal.LogGournal;
import hello.LogGournal.LogGournalRepository;
import hello.User.User;
import hello.User.UserRepository;
import hello.User.UserService;

@Controller
@RequestMapping("/viewLogGournal")
public class ViewLogGournalCotroller {
	@Autowired	
	DeviceService deviceService;
	@Autowired	
	UserRepository uRepository;
	@Autowired
	UserService UserService;
	@Autowired
	LogGournalRepository lgRepository;
	
	LogGournalFilter logGournalFilter = new LogGournalFilter();
	
	@ModelAttribute("devices")
	public List<Device> populateDevices() {
	    return deviceService.findAll();
	}

	@ModelAttribute("users")
	public List<User> populateUsers() {
	    return UserService.findAll();
	}

	@GetMapping
	public ModelAndView get() throws ParseException {

		return mavPost(logGournalFilter);

	}
	@PostMapping(params="action=filter")
	public ModelAndView mavPost(

	    @ModelAttribute("logGournalFilter") LogGournalFilter logGournalFilter ) throws ParseException {
			this.logGournalFilter = logGournalFilter;
			ModelAndView mv = new ModelAndView();
			List<LogGournal> logGournal = new ArrayList<>();
			List<LogGournalWeb> logGournalWeb = new ArrayList<>();
			
			SimpleDateFormat mdyFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
			
			if (logGournalFilter.getDateS() ==null) logGournalFilter.setDateS(utils.toLocalDate(new Date()).toString());
			if (logGournalFilter.getDateE() ==null) logGournalFilter.setDateE(utils.toLocalDate(new Date()).toString());
			
			if ((logGournalFilter.getUser() == null) || (logGournalFilter.getUser().getId()==0)){
				logGournal = lgRepository.findByDtBetweenOrderByDt(utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(logGournalFilter.getDateS())),
						utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(logGournalFilter.getDateE())));
				if (logGournal!=null) {
					for (LogGournal b : logGournal) {
						//Long id, String sdate, String deviceName, String userName,	String source, String RecordsAffected
						logGournalWeb.add(new LogGournalWeb(b.getId(), mdyFormat.format(b.getDt()), b.getDevice().getName(), b.getUser().getName(),
								b.getSource(), String.valueOf(b.getRecordsAffected())));
					}
				}
			}
			else
			{
				logGournalWeb.add (new LogGournalWeb ((long) (0), "", "", "", "" ,""));
			} 
			
			mv.addObject("logGournal", logGournalWeb);
			

			mv.addObject("logGournalFilter", logGournalFilter);
			mv.setViewName("viewLogGournal");
			
			return mv;

	}
	
	@PostMapping(params="action=open")
		public ModelAndView mavOpen(
	
		    @ModelAttribute("logGournalFilter") LogGournalFilter logGournalFilter ) throws ParseException {
				this.logGournalFilter = logGournalFilter;
				ModelAndView mv = new ModelAndView();
		return mv;
	
	}	
	
}
