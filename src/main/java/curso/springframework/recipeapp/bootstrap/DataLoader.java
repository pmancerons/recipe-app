package curso.springframework.recipeapp.bootstrap;

import curso.springframework.recipeapp.domain.*;
import curso.springframework.recipeapp.repositories.CategoryRepository;
import curso.springframework.recipeapp.repositories.RecipeRepository;
import curso.springframework.recipeapp.repositories.UnitOfMeasureRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner{

    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final RecipeRepository recipeRepository;

    public DataLoader(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository, curso.springframework.recipeapp.repositories.RecipeRepository recipeRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadPerfectGuacamole();
    }

    private void loadPerfectGuacamole(){
        Set<Ingredient> ingredients = new HashSet<>();

        UnitOfMeasure unit = unitOfMeasureRepository.findByDescription("Unit").get();
        UnitOfMeasure teaSpoon =  unitOfMeasureRepository.findByDescription("Tea Spoon").get();
        UnitOfMeasure tableSpoon =  unitOfMeasureRepository.findByDescription("Table Spoon").get();
        UnitOfMeasure dash =  unitOfMeasureRepository.findByDescription("Dash").get();

        Ingredient avocado = new Ingredient();
        avocado.setDescription("Avocado");
        avocado.setAmount(BigDecimal.valueOf(2l));
        avocado.setUnitOfMeasure(unit);
        ingredients.add(avocado);

        Ingredient salt = new Ingredient();
        salt.setDescription("Salt");
        salt.setAmount(BigDecimal.valueOf(0.5));
        salt.setUnitOfMeasure(teaSpoon);
        ingredients.add(salt);

        Ingredient lemonJuice = new Ingredient();
        lemonJuice.setDescription("Lemon juice");
        lemonJuice.setAmount(BigDecimal.valueOf(1));
        lemonJuice.setUnitOfMeasure(tableSpoon);
        ingredients.add(lemonJuice);

        Ingredient mincedRedOnion = new Ingredient();
        mincedRedOnion.setDescription("Minced red onions");
        mincedRedOnion.setAmount(BigDecimal.valueOf(2));
        mincedRedOnion.setUnitOfMeasure(tableSpoon);
        ingredients.add(mincedRedOnion);

        Ingredient cilantro = new Ingredient();
        cilantro.setDescription("Cilantro finely chopped");
        cilantro.setAmount(BigDecimal.valueOf(2));
        cilantro.setUnitOfMeasure(tableSpoon);
        ingredients.add(cilantro);

        Ingredient serranoChiles = new Ingredient();
        serranoChiles.setDescription("Serrano chiles, stems and seeds removed, minced");
        serranoChiles.setAmount(BigDecimal.valueOf(2));
        serranoChiles.setUnitOfMeasure(unit);
        ingredients.add(serranoChiles);

        Ingredient pepper = new Ingredient();
        pepper.setDescription("grated black pepper");
        pepper.setAmount(BigDecimal.valueOf(1));
        pepper.setUnitOfMeasure(unit);
        ingredients.add(pepper);

        Ingredient tomato = new Ingredient();
        tomato.setDescription("tomato, seed and pulp removed, chopped");
        tomato.setAmount(BigDecimal.valueOf(0.5));
        tomato.setUnitOfMeasure(unit);
        ingredients.add(tomato);

        Ingredient tortilla = new Ingredient();
        tortilla.setDescription("Tortilla chips, to serve");
        tortilla.setAmount(BigDecimal.valueOf(3));
        tortilla.setUnitOfMeasure(unit);
        ingredients.add(tortilla);

        Category mexican = categoryRepository.findByDescription("Mexican").get();
        Category fastFood = categoryRepository.findByDescription("Fast Food").get();

        Set<Category> categories = new HashSet<>();
        categories.add(mexican);
        categories.add(fastFood);

        Notes note = new Notes();
        note.setRecipeNotes("procedure");

        Recipe perfectGuacamole = new Recipe();
        perfectGuacamole.setCategories(categories);
        perfectGuacamole.setCookTime(0);
        perfectGuacamole.setDescription("Perfect Guacamole");
        perfectGuacamole.setDifficulty(Difficulty.EASY);

        perfectGuacamole.setPrepTime(10);
        perfectGuacamole.setDirections("directions");
        perfectGuacamole.setSource("Simply Recipes");
        perfectGuacamole.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        perfectGuacamole.setNotes(note);

        ingredients.forEach(ingredient -> {
            perfectGuacamole.addIngredient(ingredient);
        });

        Recipe copy = new Recipe();
        copy.setCookTime(0);
        copy.setDescription("Perfect");
        copy.setDifficulty(Difficulty.EASY);
        copy.setPrepTime(10);
        copy.setDirections("directions");
        copy.setSource("Simply Recipes");
        copy.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        copy.setNotes(new Notes());

        recipeRepository.save(perfectGuacamole);
        recipeRepository.save(copy);
    }
}
