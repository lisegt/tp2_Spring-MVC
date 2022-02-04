package monprojet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import lombok.extern.slf4j.Slf4j;
import monprojet.dao.CityRepository;
import monprojet.dao.CountryRepository;
import monprojet.entity.City;
import monprojet.entity.Country;

@Controller
@RequestMapping(path = "/gestionDesVilles") // This means URL's start with /gestionDesVilles (after Application path)
@Slf4j
public class CityController {
	
	private static final String DEFAULT_VIEW = "gestionDesVilles";

	@Autowired
	private CityRepository daoCity;
	@Autowired
	private CountryRepository daoCountry;
	
	@GetMapping(path = "defaultView")
	public String recupererDonnees(Model model) {
		model.addAttribute("ville", daoCity.findAll());
		model.addAttribute("pays", daoCountry.findAll() );
		return DEFAULT_VIEW;
	}

	@PostMapping(path = "defaultView")
	public String ajouterVille(@RequestParam String nomVille, @RequestParam int popVille, @RequestParam int pays){
		Country myCountry = daoCountry.findById(pays).orElseThrow(); //pays est l'id du pays récupéré en value ds form mustache
		City newCity = new City (nomVille, myCountry);
		newCity.setPopulation(popVille);

		daoCity.save(newCity);
		return "redirect:defaultView";

	}
}

