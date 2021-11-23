package pe.edu.upc.spring.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sun.el.parser.ParseException;

import pe.edu.upc.spring.model.Actor;
import pe.edu.upc.spring.service.IActorService;

@Controller
@RequestMapping("/actor")
public class ActorController {
	@Autowired
	private IActorService aService;
	
	@RequestMapping("/registrar")
	public String register(@Valid @ModelAttribute("actor") Actor actor, @RequestParam("idPath") Integer idPath, BindingResult binRes, Model model) 
		throws Exception
	{
		if (binRes.hasErrors()){
			model.addAttribute("listActors", aService.findAllSortIdAsc());
			model.addAttribute("actorbusqueda", new Actor()); 
			return "listActor";
		}	
		else {
			if(idPath == 1) {
				int rpta = aService.save(actor);
				if(rpta > 0) {
					model.addAttribute("idPath", 1);
					model.addAttribute("mensaje", "Ya existe este actor");
					model.addAttribute("listActors", aService.findAllSortIdAsc());
					model.addAttribute("actor",new Actor());
					model.addAttribute("actorbusqueda", new Actor()); 
					return "listActor";
				}		
				else {
					model.addAttribute("idPath", 1);
					model.addAttribute("mensaje", "Se registro un actor correctamente");
					model.addAttribute("listActors", aService.findAllSortIdAsc());
					model.addAttribute("actor",new Actor());
					model.addAttribute("actorbusqueda", new Actor()); 
					return "listActor";
				}
			}
			else {
				aService.update(actor);
				model.addAttribute("idPath", 1);
				model.addAttribute("mensaje", "Se actualizó un actor correctamente");
				model.addAttribute("listActors", aService.findAllSortIdAsc());
				model.addAttribute("actor",new Actor());
				model.addAttribute("actorbusqueda", new Actor()); 
				return "listActor";
			}
			
		}
	}
	
	@RequestMapping("/modificar/{id}")
	public String modify(@PathVariable int id, Model model, RedirectAttributes objRedir) 
		throws ParseException
	{
		Optional<Actor> objActor = aService.findById(id);
		if (objActor == null) {
			objRedir.addFlashAttribute("mensaje", "Ocurrio un roche, LUZ ROJA");
			return "redirect:/actor/listar";
		}
		else {
			model.addAttribute("actor", objActor);
			model.addAttribute("actorbusqueda", new Actor());
			model.addAttribute("listActors",aService.findAllSortIdAsc());
			model.addAttribute("idPath", 2);
			return "listActor";                   
		}
	}
	
	@RequestMapping("/eliminar")
	public String delete(Map<String, Object> model, @RequestParam(value="id") Integer id) {
		try {
			if(id!=null && id>0) {
				aService.delete(id);
				model.put("actor",new Actor()); //importante
				model.put("actorbusqueda", new Actor()); //importante
				model.put("listActors", aService.findAllSortIdAsc());
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			model.put("Mensaje", "Ocurrio un error");
			model.put("listActors", aService.findAllSortIdAsc());
			model.put("actor", new Actor());
			model.put("actorbusqueda", new Actor());
		}
		return "listActor";
	}
	
	@RequestMapping("/listar")
	public String list(Map<String, Object> model) {
		model.put("idPath", 1);
		model.put("listActors", aService.findAllSortIdAsc());
		model.put("actor",new Actor());
		model.put("actorbusqueda", new Actor()); 
		return "listActor";
	}
	
	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model,@ModelAttribute("actorbusqueda") Actor actor) 
		throws ParseException
	{
		List<Actor>listActors;
		listActors = aService.findByName(actor.getNameActor());
		
		model.put("actor", new Actor());
		model.put("listActors", listActors);
		
		return "listActor";
	}
}
