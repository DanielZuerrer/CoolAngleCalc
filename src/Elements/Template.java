package Elements;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Template {

    private final String IMG = "resources/images/";
    private final String IMG_FORMAT= ".png";

    private Image templateImage;
    private TemplateSide templateSide = TemplateSide.RIGHT;

    public Template() {
        setTemplateImage("templateR");
    }

    public void flip() {
        templateSide = TemplateSide.getOther(templateSide);
        setTemplateImage(templateSide.getTemplateImageName());
    }

    public Image getTemplateImage() {
        return templateImage;
    }

    private void setTemplateImage(String imageName) {
        try {
            FileInputStream inputStream = new FileInputStream(IMG + imageName + IMG_FORMAT);
            this.templateImage = new Image(inputStream);
        } catch (FileNotFoundException e) {
            System.err.print(e.getStackTrace());
            System.out.println("Couldn't load image");
        }
    }

    public TemplateSide getTemplateSide() {
        return templateSide;
    }
}
