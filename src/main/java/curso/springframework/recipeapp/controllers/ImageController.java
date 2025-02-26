package curso.springframework.recipeapp.controllers;


import curso.springframework.recipeapp.commands.RecipeCommand;
import curso.springframework.recipeapp.services.ImageService;
import curso.springframework.recipeapp.services.RecipeService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ImageController {

    private final ImageService imageService;
    private final RecipeService recipeService;

    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("recipe/{id}/image")
    public String showUploadForm(@PathVariable String id, Model model){
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));

        return "recipe/imageuploadform";
    }

    @PostMapping("recipe/{id}/image")
    public String handleImagePost(@PathVariable String id, @RequestParam("imagefile") MultipartFile file){

        imageService.saveImageFile(Long.valueOf(id), file);

        return "redirect:/recipe/" + id + "/show";
    }

    @GetMapping("recipe/{recipeId}/recipeimage")
    public void getImageFromDB(@PathVariable String recipeId, HttpServletResponse httpServletResponse) throws IOException {
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));

        if(recipeCommand.getImage() != null) {
            byte[] byteArray = new byte[recipeCommand.getImage().length];

            int i = 0;

            for (Byte b : recipeCommand.getImage()) {
                byteArray[i++] = b;
            }

            httpServletResponse.setContentType("image/jpeg");
            InputStream inputStream = new ByteArrayInputStream(byteArray);
            IOUtils.copy(inputStream, httpServletResponse.getOutputStream());
        }
    }
}