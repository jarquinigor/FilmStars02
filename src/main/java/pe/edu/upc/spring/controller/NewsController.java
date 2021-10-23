package pe.edu.upc.spring.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

import pe.edu.upc.spring.model.News;
import pe.edu.upc.spring.service.INewsService;

@Controller
@RequestMapping("/noticia")
public class NewsController {
	@Autowired
	private INewsService nService;
	
	@RequestMapping("/bienvenido")
	public String goWelcomePage() {
		return "welcome"; 
	}
	
	@RequestMapping("/registrar")
	public String register(@ModelAttribute("news") News objNews, BindingResult binRes, Model model) 
		throws ParseException
	{
		if (binRes.hasErrors())
			return "noticia";
		else {
			boolean flag = nService.save(objNews);
			if(flag)
				return "redirect:/noticia/listar";
			else {
				model.addAttribute("mensaje", "Rochezaso");
				return "redirect:/noticia/irRegistrar";
			}
		}
	}
	
	@RequestMapping("/modificar/{id}")
	public String modify(@PathVariable int id, Model model, RedirectAttributes objRedir) 
		throws ParseException
	{
		Optional<News> objNews = nService.findById(id);
		if (objNews == null) {
			objRedir.addFlashAttribute("mensaje", "Rochezaso");
			return "redirect:/noticia/listar";
		}
		else {
			model.addAttribute("news", objNews);
			model.addAttribute("newsbusqueda", new News());
			model.addAttribute("listNews", nService.findAllSortAsc());
			return "listNews";                   
		}
	}
	
	@RequestMapping("/eliminar")
	public String delete(Map<String, Object> model, @RequestParam(value="id") Integer id) {
		try {
			if(id!=null && id>0) {
				nService.delete(id);
				model.put("news",new News());
				model.put("newsbusqueda", new News());
				model.put("listNews", nService.findAllSortAsc());
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			model.put("Mensaje", "Ocurrio un error");
			model.put("listNews", nService.findAll());
		}
		return "listNews";
	}
	
	@RequestMapping("/listar")
	public String list(Map<String, Object> model) {
		model.put("listNews", nService.findAllSortAsc());
		model.put("news",new News());
		model.put("newsbusqueda", new News());
		
		return "listNews";
	}
	
	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model,@ModelAttribute("newsbusqueda") News news) 
		throws ParseException
	{
		List<News>listNews;
		listNews = nService.findByName(news.getTitleNews());
		
		model.put("news", new News());
		model.put("listNews", listNews);
		
		return "listNews";
	}
}
