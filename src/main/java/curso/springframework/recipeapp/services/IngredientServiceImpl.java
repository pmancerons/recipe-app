package curso.springframework.recipeapp.services;

import curso.springframework.recipeapp.commands.IngredientCommand;
import curso.springframework.recipeapp.converters.IngredientCommandToIngredient;
import curso.springframework.recipeapp.converters.IngredientToIngredientCommand;
import curso.springframework.recipeapp.domain.Ingredient;
import curso.springframework.recipeapp.domain.Recipe;
import curso.springframework.recipeapp.repositories.RecipeRepository;
import curso.springframework.recipeapp.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService{

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand, IngredientCommandToIngredient ingredientCommandToIngredient, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
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

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {

        Optional<Recipe> optionalRecipe = recipeRepository.findById(ingredientCommand.getRecipeId());

        if(!optionalRecipe.isPresent()){
            return new IngredientCommand();
        }else{
            Recipe recipe = optionalRecipe.get();

            Optional<Ingredient> optionalIngredient =  recipe.getIngredients().stream()
                    .filter(i -> i.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            if(optionalIngredient.isPresent()){
                Ingredient ingredientFound = optionalIngredient.get();

                ingredientFound.setAmount(ingredientCommand.getAmount());
                ingredientFound.setDescription(ingredientCommand.getDescription());
                ingredientFound.setUnitOfMeasure(
                        unitOfMeasureRepository.findById(ingredientCommand.getUnitOfMeasure().getId())
                        .orElseThrow(() -> new RuntimeException("uom not found")));
            }else{
                Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
                recipe.addIngredient(ingredient);
                ingredient.setRecipe(recipe);
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(i -> i.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            if(!savedIngredientOptional.isPresent()){
                savedIngredientOptional = savedRecipe.getIngredients().stream()
                        .filter(i -> i.getDescription().equals(ingredientCommand.getDescription()))
                        .filter(i -> i.getAmount().equals(ingredientCommand.getAmount()))
                        .filter(i -> i.getUnitOfMeasure().getId().equals(ingredientCommand.getUnitOfMeasure().getId()))
                        .findFirst();
            }

            return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
        }

    }

    @Transactional
    @Override
    public void deleteById(Long recipeId, Long ingredientId) {

        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);

        if(optionalRecipe.isPresent()){
            Recipe recipe = optionalRecipe.get();

            Optional<Ingredient> optionalIngredient =  recipe.getIngredients().stream()
                    .filter(i -> i.getId().equals(ingredientId))
                    .findFirst();

            if(optionalIngredient.isPresent()){
                Ingredient ingredientToDelete = optionalIngredient.get();
                ingredientToDelete.setRecipe(null);
                recipe.getIngredients().remove(ingredientToDelete);
            }

            recipeRepository.save(recipe);
        }
    }
}
