package Images;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ImageController {

    private static ImageController instance = null;

    private Image image1;
    private Image image2;
    private ActiveImage activeImage = ActiveImage.FIRST;

    protected ImageController() {
    }

    public static ImageController getInstance() {
        if (instance == null) {
            instance = new ImageController();
        }
        return instance;
    }

    public void setImage1(String path) {
        try {
            FileInputStream image1Input = new FileInputStream(path);
            this.image1 = new Image(image1Input);
        } catch (FileNotFoundException e) {
            System.err.print(e.getStackTrace());
            System.out.println("Couldn't load image");
        }
        activeImage = ActiveImage.FIRST;
    }


    public void setImage2(String path) {
        try {
            FileInputStream image2Input = new FileInputStream(path);
            this.image2 = new Image(image2Input);
        } catch (FileNotFoundException e) {
            System.err.print(e.getStackTrace());
            System.out.println("Couldn't load image");
        }
        activeImage = ActiveImage.SECOND;
    }

    public void switchActiveImage() {
        activeImage = ActiveImage.getOther(activeImage);
    }

    public Image getImage1() {
        return image1;
    }

    public Image getImage2() {
        return image2;
    }

    public Image getActiveImage() {
        if (this.activeImage == ActiveImage.FIRST) {
            return getImage1();
        } else if (this.activeImage == ActiveImage.SECOND) {
            return getImage2();
        }
        return null;
    }

    private enum ActiveImage {

        FIRST, SECOND;

        public static ActiveImage getOther(ActiveImage activeImage) {
            return activeImage == FIRST ? SECOND : FIRST;
        }
    }
}
