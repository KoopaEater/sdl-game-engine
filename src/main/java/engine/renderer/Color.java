package engine.renderer;

public class Color {
    public static Color RED = new Color(255, 0, 0);
    public static Color GREEN = new Color(0, 255, 0);
    public static Color BLUE = new Color(0, 0, 255);
    public static Color WHITE = new Color(255);
    private final int red, green, blue, alpha;
    public Color(int r, int g, int b) {
        red = r;
        green = g;
        blue = b;
        alpha = 255;
    }
    public Color(int r, int g, int b, int a) {
        red = r;
        green = g;
        blue = b;
        alpha = a;
    }
    public Color(int greyscale) {
        red = greyscale;
        green = greyscale;
        blue = greyscale;
        alpha = 255;
    }
    public Color(String hex) {
        if (hex.startsWith("#")) {
            hex = hex.substring(1);
        } else if (hex.startsWith("0x")) {
            hex = hex.substring(2);
        }
        int hexLength = hex.length();
        if (!(hexLength == 6 || hexLength == 8)) {
            throw new IllegalArgumentException("Invalid hexadecimal format");
        }
        int intValue = Integer.parseInt(hex, 16);
        alpha = hexLength == 8 ? (intValue >> 24) & 0xFF : 255;
        red = (intValue >> 16) & 0xFF;
        green = (intValue >> 8) & 0xFF;
        blue = (intValue >> 0) & 0xFF;
    }
    public byte redAsByte() {
        return (byte) red;
    }
    public byte greenAsByte() {
        return (byte) green;
    }
    public byte blueAsByte() {
        return (byte) blue;
    }
    public byte alphaAsByte() {
        return (byte) alpha;
    }
}
