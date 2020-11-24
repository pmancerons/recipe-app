package curso.springframework.recipeapp.controllers;

import curso.springframework.recipeapp.commands.IngredientCommand;
import curso.springframework.recipeapp.services.IngredientService;
import curso.springframework.recipeapp.services.RecipeService;
import curso.springframework.recipeapp.services.UnitOfMeasureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IngredientController {
    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("/recipe/{recipeId}/ingredients")
    public String listIngredientsFromRecipeId(@PathVariable String recipeId, Model model){
        model.addAttribute("recipe",recipeService.findCommandById(Long.valueOf(recipeId)));

        return "recipe/ingredients/list";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String showIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
            model.addAttribute("ingredient"
                    ,ingredientService.findByRecipeIdAndId(Long.valueOf(recipeId),Long.valueOf(ingredientId)));

            return "recipe/ingredients/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateRecipeIngredient(@PathVariable String recipeId
            , @PathVariable String ingredientId, Model model){
        model.addAttribute("ingredient"
                ,ingredientService.findByRecipeIdAndId(Long.valueOf(recipeId),Long.valueOf(ingredientId)));

        model.addAttribute("uomList", unitOfMeasureService.findAll());

        return "recipe/ingredients/ingredientform";
    }

    @PostMapping("/recipe/{recipeId}/ingredient")
    public String saveOrUpdateIngredient(@ModelAttribute IngredientCommand ingredientCommand){
        IngredientCommand savedIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand);
        return  "redirect:/recipe/" + savedIngredientCommand.getRecipeId()
                + "/ingredient/" + savedIngredientCommand.getId() + "/show";
    }
}
