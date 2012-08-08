package login;

import java.awt.Color;
import java.awt.color.ColorSpace;
import java.util.ArrayList;
import java.util.List;

/**
 * For selecting different color combinations that are readable.
 * @author Curt
 */
final class ColorSelector {

    static final Color[] COLORS = generateDistinctColors();
    static final int MAX = COLORS.length;
    
    static Color getBackground(String text) {
        return COLORS[hash(text) % MAX];
    }

    static Color[] generateDistinctColors() {
        List<Color> list = new ArrayList<Color>();
        float[] range = new float[] {0.0f , 0.25f, 0.5f, 0.75f, 1.0f};
        for (float x : range){
            for (float y : range){
                for (float z : range){
                    list.add(rgb(x,y,z));
                }
            }
        }
        return list.toArray(new Color[0]);
    }
    
    static Color getForeground(String text) {
        int hash = hash(text);
        int fore = hash % MAX;
        int pick = hash % (MAX - 1);
        int back = pick;
        int tries = 0;
        while (tooClose(fore,back)) {
           back++;
           if (back==MAX) {
               back = 0;
           }
           if (tries>MAX) {
               throw new IllegalArgumentException();
           }
           tries++;
        }
        return COLORS[back];
    }

    static final ColorSpace RGB = ColorSpace.getInstance(ColorSpace.CS_sRGB);
    static float[] rgb(Color color) {
        int size = RGB.getNumComponents();
        float[] parts = new float[size];
        color.getColorComponents(RGB, parts);
        return parts;
    }

    static final ColorSpace CIE = ColorSpace.getInstance(ColorSpace.CS_CIEXYZ);
    static float[] cie(Color color) {
        int size = CIE.getNumComponents();
        float[] parts = new float[size];
        color.getColorComponents(CIE, parts);
        return parts;
    }

    static Color rgb(float x, float y, float z) {
        return new Color(x,y,z);
    }
    
    static boolean tooClose(int fore, int back) {
        int size = CIE.getNumComponents();
        float[] c1 = cie(COLORS[fore]);
        float[] c2 = cie(COLORS[back]);
        float dist = 0;
        for (int i=0; i<size; i++) {
            float delta = c1[i] - c2[i];
            dist += delta * delta; 
        }      
//       System.out.println(String.format("%s %s %s",fore,back,dist));
        return dist < 0.65;
    }

    static int hash(String text) {
        int hash = text.hashCode();
        return (hash<0) ? -hash : hash;
    }
}
