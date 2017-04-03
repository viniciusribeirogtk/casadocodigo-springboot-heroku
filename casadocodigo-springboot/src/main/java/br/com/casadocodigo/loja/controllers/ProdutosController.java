package br.com.casadocodigo.loja.controllers;

import java.io.UnsupportedEncodingException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.models.Categoria;
import br.com.casadocodigo.loja.models.Produto;
import br.com.casadocodigo.loja.models.TipoPreco;
import br.com.casadocodigo.loja.repository.ProdutoDAO;
import br.com.casadocodigo.loja.validation.ProdutoValidation;

@Controller
@RequestMapping("/produtos")
public class ProdutosController {

	@Autowired
	private ProdutoDAO produtoDao;
	
	@Autowired
	private FileSaver fileSaver;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new ProdutoValidation());
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView listar() throws UnsupportedEncodingException {
		Iterable<Produto> listaDeProdutos = produtoDao.findAll();
		ModelAndView modelAndView = new ModelAndView("produtos/lista");
		modelAndView.addObject("produtos", listaDeProdutos);
		return modelAndView;
	}

	@RequestMapping("/form")
	public ModelAndView form(Produto produto) {
		ModelAndView modelAndView = new ModelAndView("produtos/form");
		modelAndView.addObject("tipos", TipoPreco.values());
		modelAndView.addObject("categorias", Categoria.values());
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.POST)
	@CacheEvict(value="produtosHome", allEntries=true)
	public ModelAndView gravar(MultipartFile sumario, @Valid Produto produto, BindingResult result, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			 return form(produto);
		}
		String filePath = fileSaver.gravar("casadocodigo-imgs", sumario);
		produto.setSumarioPath(filePath);
		produtoDao.save(produto);
		redirectAttributes.addFlashAttribute("sucesso", "Produto cadastrado com sucesso!");
		return new ModelAndView("redirect:/produtos");
	}

	@RequestMapping("detalhe/{id}")
	public ModelAndView detalhe(@PathVariable("id")Long id){
		ModelAndView modelAndView = new ModelAndView("/produtos/detalhe");
		Produto produto = produtoDao.findOne(id).get();
		modelAndView.addObject("produto", produto);
		return modelAndView;
	}
	
	@RequestMapping(value="remover",method = RequestMethod.POST)
	@CacheEvict(value="produtosHome", allEntries=true)
	public ModelAndView remover(Long id, RedirectAttributes redirectAttributes){
		Produto produto = produtoDao.findOne(id).get();
		produtoDao.delete(produto);
		redirectAttributes.addFlashAttribute("sucesso", "Produto removido com sucesso!");
		return new ModelAndView("redirect:/produtos");
	}
	
	@RequestMapping(value="editar",method = RequestMethod.POST)
	@CacheEvict(value="produtosHome", allEntries=true)
	public ModelAndView editar(Long id){
		Produto produto = produtoDao.findOne(id).get();
		ModelAndView view = new ModelAndView("produtos/form");
		view.addObject("tipos", TipoPreco.values());
		view.addObject("categorias", Categoria.values());
		view.addObject("produto", produto);
		return view;
	}
}
