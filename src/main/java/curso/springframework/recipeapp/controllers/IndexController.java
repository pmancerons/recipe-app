package curso.springframework.recipeapp.controllers;

import curso.springframework.recipeapp.domain.Category;
import curso.springframework.recipeapp.domain.Ingredient;
import curso.springframework.recipeapp.domain.UnitOfMeasure;
import curso.springframework.recipeapp.repositories.CategoryRepository;
import curso.springframework.recipeapp.repositories.RecipeRepository;
import curso.springframework.recipeapp.repositories.UnitOfMeasureRepository;
import org.apache.catalina.filters.RemoteIpFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class IndexController {

    private RecipeRepository recipeRepository;

    public IndexController(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @RequestMapping({"","/","/index","/index.html"})
    public String getIndexPage(Model model){

        model.addAttribute("recipes",recipeRepository.findAll());

        return "index";
    }
}
