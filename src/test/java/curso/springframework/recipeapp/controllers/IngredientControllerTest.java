package curso.springframework.recipeapp.controllers;

import curso.springframework.recipeapp.commands.IngredientCommand;
import curso.springframework.recipeapp.commands.RecipeCommand;
import curso.springframework.recipeapp.domain.Ingredient;
import curso.springframework.recipeapp.services.IngredientService;
import curso.springframework.recipeapp.services.RecipeService;
import curso.springframework.recipeapp.services.UnitOfMeasureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;

class IngredientControllerTest {

    @Mock
    RecipeService recipeService;

    @Mock
    IngredientService ingredientService;

    @Mock
    UnitOfMeasureService unitOfMeasureService;

    IngredientController ingredientController;

    MockMvc mockMvcIngredientController;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        ingredientController = new IngredientController(recipeService, ingredientService, unitOfMeasureService);

        mockMvcIngredientController = MockMvcBuilders.standaloneSetup(ingredientController).build();
    }

    @Test
    public void testListIngredients() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();

        Mockito.when(recipeService.findCommandById(ArgumentMatchers.any())).thenReturn(recipeCommand);

        mockMvcIngredientController.perform(MockMvcRequestBuilders.get("/recipe/1/ingredients"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/ingredients/list"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));

        Mockito.verify(recipeService,Mockito.times(1)).findCommandById(ArgumentMatchers.any());
    }

    @Test
    public void testShowIngredients() throws Exception {
        IngredientCommand ingredientCommand = new IngredientCommand();

        Mockito.when(ingredientService.findByRecipeIdAndId(ArgumentMatchers.anyLong(),ArgumentMatchers.anyLong()))
                .thenReturn(ingredientCommand);

        mockMvcIngredientController.perform(MockMvcRequestBuilders.get("/recipe/1/ingredient/1/show"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/ingredients/show"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("ingredient"));
    }

    @Test
    public void testUpdateRecipeIngredient() throws Exception {
        IngredientCommand ingredient = new IngredientCommand();

        Mockito.when(ingredientService.findByRecipeIdAndId(ArgumentMatchers.anyLong()
                    ,ArgumentMatchers.anyLong()))
            .thenReturn(ingredient);

        Mockito.when(unitOfMeasureService.findAll()).thenReturn(new HashSet<>());

        mockMvcIngredientController.perform(MockMvcRequestBuilders.get("/recipe/1/ingredient/1/update"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/ingredients/ingredientform"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("ingredient"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("uomList"));
    }

    @Test
    public void saveOrUpdate() throws Exception {
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(2l);
        ingredientCommand.setRecipeId(1l);

        Mockito.when(ingredientService.saveIngredientCommand(ArgumentMatchers.any()))
                .thenReturn(ingredientCommand);

        mockMvcIngredientController.perform(
                MockMvcRequestBuilders.post("/recipe/1/ingredient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id","")
                .param("description", "desc")
            ).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/recipe/1/ingredient/2/show"));


    }

}