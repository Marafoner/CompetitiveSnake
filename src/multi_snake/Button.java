package multi_snake;

public class Button {
    /*
    *  "Button" class was created in order to avoid using Java's default buttons.
    *  The way it works is that "WindowManager" class looks for mouse's position and if it's clicked
    *  it checks if it's in border with any button using the areas of those Buttons, if so it runs the function of the button.
    */
	private int x, y;
    private int ButtonWidth;
    private int ButtonHeight;
    private int ButtonBorderSize;
    private String ButtonText;
    private String ButtonColor;
    private boolean ButtonActive;
    Button(int x, int y, String ButtonText) {
    	this.x = x;
    	this.y = y;
    	this.ButtonText = ButtonText;
    	this.ButtonHeight = 100;
    	this.ButtonWidth = 200;
    	this.ButtonActive = true;
    	this.ButtonColor = "GREEN";
    }
    Button(int x, int y, int ButtonBorderSize) {
    	this.x = x;
    	this.y = y;
    	this.ButtonBorderSize = ButtonBorderSize;
    }
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
    public int get_Height() {
        return ButtonHeight;
    }
    public int get_BorderSize() {
        return ButtonBorderSize;
    }
    public String get_Text() {
        return ButtonText;
    }
    public String get_Color() {
        return ButtonColor;
    }
    public int get_x() {
    	return x;
    }
    public int get_y() {
    	return y;
    }
    public void set_x(int x) {
    	this.x = x;
    }
    public void set_y(int y) {
    	this.y = y;
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