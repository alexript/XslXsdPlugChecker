/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plug_checker.ui.html;

import static j2html.TagCreator.img;
import j2html.attributes.Attr;
import j2html.tags.EmptyTag;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author malyshev
 */
public class ImageEmbedder {

    public enum IconSet {
        Small("16x16"), Big("32x32");
        private final String dirName;

        private IconSet(String dirName) {
            this.dirName = dirName;
        }

        @Override
        public String toString() {
            return dirName;
        }

        public String getPath(String imgFileName) {
            return "iconset/" + toString() + "/" + imgFileName;
        }
    }

    private static String embed(URL url) {

        byte[] imageBytes = new byte[0];
        try (BufferedInputStream bis = new BufferedInputStream(url.openStream())) {
            for (byte[] ba = new byte[bis.available()];
                    bis.read(ba) != -1;) {
                byte[] baTmp = new byte[imageBytes.length + ba.length];
                System.arraycopy(imageBytes, 0, baTmp, 0, imageBytes.length);
                System.arraycopy(ba, 0, baTmp, imageBytes.length, ba.length);
                imageBytes = baTmp;
            }
        } catch (IOException ex) {
            Logger.getLogger(ImageEmbedder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "data:image/png;base64," + DatatypeConverter.printBase64Binary(imageBytes);
    }

    public static EmptyTag resultIconElement(boolean result, IconSet set) {
        return img().attr(Attr.SRC, resultIcon(result, set));
    }

    protected static String resultIcon(boolean result, IconSet set) {
        String imgRef = result ? set.getPath("tick.png") : set.getPath("cross.png");
        return embed(ImageEmbedder.class.getResource(imgRef));
    }
}
