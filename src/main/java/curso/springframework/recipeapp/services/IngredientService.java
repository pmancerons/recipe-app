package curso.springframework.recipeapp.services;

import curso.springframework.recipeapp.commands.IngredientCommand;

public interface IngredientService {

    IngredientCommand findByRecipeIdAndId(Long recipeId,Long ingredientId);
}
