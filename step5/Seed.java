import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

public enum Seed {
   CROSS("X", "images/cross.png"),
   NOUGHT("O", "images/circle.png"),
   NO_SEED(" ", null);

   private final String display;
   private final Image img;

   Seed(String display, String imgPath) {
      this.display = display;
      Image loaded = null;
      if (imgPath != null) {
         URL url = getClass().getClassLoader().getResource(imgPath);
         if (url != null) loaded = new ImageIcon(url).getImage();
      }
      this.img = loaded;
   }

   public String getDisplay() { return display; }
   public Image getImage() { return img; }
}
