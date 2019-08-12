package plug_checker.ui.html;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;

import static j2html.TagCreator.*;
import static j2html.TagCreator.h1;
import j2html.attributes.Attr;
import j2html.tags.EmptyTag;
import static plug_checker.constants.Constants.*;

public class HtmlGenerator {

    // TODO: replace hardcoded "+" with result Map from checker
    public static void generateHtml(String outputFileName) {
        String result = html(head(
                title(reportHtmlTitle),
                // link().withRel("stylesheet").withHref("/css/main.css")
                style("table, th, td {\n"
                        + "  border: 1px solid black;\n"
                        + "  border-collapse: collapse;\n"
                        + "}")
        ),
                body(main(attrs("#main.content"),
                        h1(reportHtmlTitle),
                        table().with(tr().with(th().with(span(checkHtmlLabel)),
                                th().with(span(resultHtmlLabel))
                        ),
                                tr().with(
                                        td().with(span(idFileHtmlLabel)),
                                        td().attr(Attr.ALIGN, "center").with(resultIconElement(true))
                                ),
                                tr().with(
                                        td().with(span(checkPrefixHtmlLabel)),
                                        td().attr(Attr.ALIGN, "center").with(resultIconElement(false))
                                ),
                                tr().with(
                                        td().with(span(checkNecessaryParametersHtmlLabel)),
                                        td().attr(Attr.ALIGN, "center").with(resultIconElement(true))
                                ),
                                tr().with(
                                        td().with(span(checkIdPolExtractHtmlLabel)),
                                        td().attr(Attr.ALIGN, "center").with(resultIconElement(true))
                                ),
                                tr().with(
                                        td().with(span(checkINNHtmLabel)),
                                        td().attr(Attr.ALIGN, "center").with(resultIconElement(true))
                                ),
                                tr().with(
                                        td().with(span(checkULorIPHtmlLabel)),
                                        td().attr(Attr.ALIGN, "center").with(resultIconElement(true))
                                ),
                                tr().with(
                                        td().with(span(checkTrustwHtmlLabel)),
                                        td().attr(Attr.ALIGN, "center").with(resultIconElement(true))
                                ),
                                tr().with(
                                        td().with(span(checkCodNoHtmlLabel)),
                                        td().attr(Attr.ALIGN, "center").with(resultIconElement(true))
                                ),
                                tr().with(
                                        td().with(span(checkVIdDockHtmlLabel)),
                                        td().attr(Attr.ALIGN, "center").with(resultIconElement(true))
                                ),
                                tr().with(
                                        td().with(span(checkForDifficultFormatsHtmlLabel)),
                                        td().attr(Attr.ALIGN, "center").with(resultIconElement(true))
                                )
                        )
                )
                )
        ).render();

        try {
            System.out.println(result);
            File tempFile = new File(outputFileName);
            FileWriter writer = new FileWriter(tempFile);
            writer.write(result);
            writer.close();

            Desktop.getDesktop().browse(tempFile.toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected static EmptyTag resultIconElement(boolean result) {
        return img().attr(Attr.SRC, resultIcon(result));
    }

    protected static String resultIcon(boolean result) {
        String imgRef = result ? "iconset/16x16/tick.png" : "iconset/16x16/cross.png";
        return HtmlGenerator.class.getResource(imgRef).toString();
    }
}
