package curso.springframework.recipeapp.services;

import curso.springframework.recipeapp.domain.Recipe;
import curso.springframework.recipeapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import java.io.IOException;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void saveImageFile(Long recipeId, MultipartFile file) {
        try{
            Recipe recipe = recipeRepository.findById(recipeId).get();

            Byte[] byteObject = new Byte[file.getBytes().length];

            int i = 0;
            for (Byte b: file.getBytes()) {
                byteObject[i++] = b;
            }

            recipe.setImage(byteObject);

            recipeRepository.save(recipe);
        }catch (IOException ex){
            log.error("Error trying to sabe image", ex);

            ex.printStackTrace();
        }
    }
}
