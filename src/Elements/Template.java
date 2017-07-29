package Elements;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

public class Template {

    private final String IMG = "/images/";
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
            URL url = getClass().getResource(IMG + imageName + IMG_FORMAT);
            this.templateImage = new Image(url.openStream());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't load image");
        }
    }

    public TemplateSide getTemplateSide() {
        return templateSide;
    }
}
