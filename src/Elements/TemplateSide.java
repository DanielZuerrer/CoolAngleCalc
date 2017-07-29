package Elements;

public enum TemplateSide {

    RIGHT("R", "templateR"), LEFT("L", "templateL");

    private String letter;
    private String templateImageName;

    TemplateSide(String letter, String templateImageName) {
        this.letter = letter;
        this.templateImageName = templateImageName;
    }

    public String getLetter() {
        return letter;
    }

    public String getTemplateImageName() {
        return templateImageName;
    }

    public static TemplateSide getOther(TemplateSide templateSide) {
        return templateSide == RIGHT ? LEFT : RIGHT;
    }
}
