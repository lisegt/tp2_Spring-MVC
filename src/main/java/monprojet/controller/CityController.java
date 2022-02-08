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
	
	@GetMapping(path = "show")
	public String recupererDonnees(Model model) {
		model.addAttribute("villes", daoCity.findAll());
		model.addAttribute("pays", daoCountry.findAll());

		//on initialise les champs du formulaire avec une ville par défaut
		Country france = daoCountry.getById(1);
		City cityDefaut = new City("Nouvelle ville", france);
		cityDefaut.setPopulation(50);
		model.addAttribute("city", cityDefaut);

		return DEFAULT_VIEW;
	}
	@GetMapping(path="edit")
	public String modifCityPuisMontreListe(@RequestParam("id") City city, Model model){
		model.addAttribute("city", city);
		model.addAttribute("pays", daoCountry.findAll());
		return "formulaireEditCity";
	}

	@GetMapping(path="delete")
	public String supprimeCityPuisMontreListe(@RequestParam("id") City city){
		daoCity.delete(city);
		return "redirect:/gestionDesVilles/show";
	}
	/*
	@PostMapping(path = "save")
	public String ajouterVille(@RequestParam String nomVille, @RequestParam int popVille, @RequestParam int country){
		Country myCountry = daoCountry.findById(country).orElseThrow(); //pays est l'id du pays récupéré en value ds form mustache
		City newCity = new City (nomVille, myCountry);
		newCity.setPopulation(popVille);

		daoCity.save(newCity);
		return "redirect:/gestionDesVilles/show";
	}
	*/

	@PostMapping(path = "save")
	public String enregistrerUneVille(City uneVille){
		
		daoCity.save(uneVille);
		return "redirect:/gestionDesVilles/show";
	}
}

