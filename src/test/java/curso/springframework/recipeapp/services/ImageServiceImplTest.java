package curso.springframework.recipeapp.services;

import curso.springframework.recipeapp.commands.RecipeCommand;
import curso.springframework.recipeapp.domain.Recipe;
import curso.springframework.recipeapp.repositories.RecipeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.Optional;

public class ImageServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    ImageService imageService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        imageService = new ImageServiceImpl(recipeRepository);
    }

    @Test
    public void saveImageFile() throws IOException {
        MultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt"
                    , "text/plain","spring framework".getBytes());

        Recipe recipe = new Recipe();
        recipe.setId(1l);

        Optional<Recipe> optionalRecipe = Optional.of(recipe);

        Mockito.when(recipeRepository.findById(1l)).thenReturn(optionalRecipe);

        ArgumentCaptor<Recipe> recipeCaptor = ArgumentCaptor.forClass(Recipe.class);

        imageService.saveImageFile(1l,multipartFile);

        Mockito.verify(recipeRepository,Mockito.times(1)).save(recipeCaptor.capture());
        Recipe savedRecipe = recipeCaptor.getValue();
        Assertions.assertEquals(multipartFile.getBytes().length,savedRecipe.getImage().length);
    }
}
