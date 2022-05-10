package multi_snake;

public class Button {
    /*
    *  "Button" class was created in order to avoid using Java's default buttons.
    *  The way it works is that "WindowManager" class looks for mouse's position and if it's clicked
    *  it checks if it's in border with any button using the areas of those Buttons, if so it runs the function of the button.
    */
    private int ButtonWidth;
    private int ButtonHeight;
    private int ButtonBorderSize;
    private String ButtonText;
    private String ButtonColor;
    
    Button(int ButtonWidth, int ButtonHeight, String ButtonText, String Button_Color) {
        this.ButtonWidth = ButtonWidth;
        this.ButtonHeight = ButtonHeight;
        this.ButtonText = ButtonText;
        this.Button_Color = Button_Color;
    }
    Button(int ButtonWidth, int ButtonHeight, int ButtonBorderSize, String ButtonText, String Button_Color) {
        this.ButtonWidth = ButtonWidth;
        this.ButtonHeight = ButtonHeight;
        this.ButtonBorderSize = ButtonBorderSize;
        this.ButtonText = ButtonText;
        this.Button_Color = Button_Color;
    }

    public int get_Width() {
        return ButtonWidth;
    }
    public int get_Heigh() {
        return ButtonHeight;
    }
    public int get_BorderSize() {
        return ButtonBorderSize;
    }
    public String get_Text() {
        return ButtonText;
    }
    public char get_Color() {
        return Button_Color;
    }
    public int set_Width(int ButtonWidth) {
        this.ButtonWidth = ButtonWidth;
    }
    public int set_Height(int ButtonHeight) {
        this.ButtonHeight = ButtonHeight;
    }
    public int set_BorderSize(int ButtonBorderSize) {
        this.ButtonBorderSize = ButtonBorderSize;
    }
    public String set_Text(String ButtonText) {
        this.ButtonText = ButtonText;
    }
    public String set_Color(String ButtonColor) {
        this.ButtonColor = ButtonColor;
    }
}