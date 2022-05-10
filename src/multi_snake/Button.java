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
    private boolean ButtonActive;
    Button(int ButtonWidth, int ButtonHeight, String ButtonText, String Button_Color) {
        this.ButtonWidth = ButtonWidth;
        this.ButtonHeight = ButtonHeight;
        this.ButtonText = ButtonText;
        this.ButtonColor = Button_Color;
        this.ButtonActive = false;
    }
    Button(int ButtonWidth, int ButtonHeight, int ButtonBorderSize, String ButtonText, String Button_Color) {
        this.ButtonWidth = ButtonWidth;
        this.ButtonHeight = ButtonHeight;
        this.ButtonBorderSize = ButtonBorderSize;
        this.ButtonText = ButtonText;
        this.ButtonColor = Button_Color;
        this.ButtonActive = false;
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
    public void set_Width(int ButtonWidth) {
        this.ButtonWidth = ButtonWidth;
    }
    public void set_Height(int ButtonHeight) {
        this.ButtonHeight = ButtonHeight;
    }
    public void set_BorderSize(int ButtonBorderSize) {
        this.ButtonBorderSize = ButtonBorderSize;
    }
    public void set_Text(String ButtonText) {
        this.ButtonText = ButtonText;
    }
    public void set_Color(String ButtonColor) {
        this.ButtonColor = ButtonColor;
    }
    public boolean get_Active() {
        return this.ButtonActive;
    }
    public void switch_Active() {
        this.ButtonActive = !this.ButtonActive;
    }
}