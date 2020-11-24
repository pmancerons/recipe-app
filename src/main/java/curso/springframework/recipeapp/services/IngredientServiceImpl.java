package curso.springframework.recipeapp.services;

import curso.springframework.recipeapp.commands.IngredientCommand;
import curso.springframework.recipeapp.converters.IngredientToIngredientCommand;
import curso.springframework.recipeapp.domain.Ingredient;
import curso.springframework.recipeapp.domain.Recipe;
import curso.springframework.recipeapp.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService{

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final RecipeRepository recipeRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand, RecipeRepository recipeRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public IngredientCommand findByRecipeIdAndId(Long recipeId,Long ingredientId) {
        IngredientCommand result = null;

        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);

        if (optionalRecipe.isPresent()) {
            Recipe recipe = optionalRecipe.get();
            Optional<IngredientCommand> optionalIngredient =
                    recipe.getIngredients().stream()
                            .filter(i -> i.getId().equals(ingredientId))
                            .map(i -> ingredientToIngredientCommand.convert(i))
                            .findFirst();
            if (optionalIngredient.isPresent()) {
                result = optionalIngredient.get();
            }
        }

        //todo handle exception if ingredient command is null

        return result;
    }
}
