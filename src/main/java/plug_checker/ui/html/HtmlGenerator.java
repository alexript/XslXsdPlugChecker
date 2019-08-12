package plug_checker.ui.html;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;

import static j2html.TagCreator.*;
import static j2html.TagCreator.h1;
import j2html.attributes.Attr;
import java.io.IOException;
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
                                        td().attr(Attr.ALIGN, "center").with(ImageEmbedder.resultIconElement(true, ImageEmbedder.IconSet.Small))
                                ),
                                tr().with(
                                        td().with(span(checkPrefixHtmlLabel)),
                                        td().attr(Attr.ALIGN, "center").with(ImageEmbedder.resultIconElement(false, ImageEmbedder.IconSet.Small))
                                ),
                                tr().with(
                                        td().with(span(checkNecessaryParametersHtmlLabel)),
                                        td().attr(Attr.ALIGN, "center").with(ImageEmbedder.resultIconElement(true, ImageEmbedder.IconSet.Small))
                                ),
                                tr().with(
                                        td().with(span(checkIdPolExtractHtmlLabel)),
                                        td().attr(Attr.ALIGN, "center").with(ImageEmbedder.resultIconElement(true, ImageEmbedder.IconSet.Small))
                                ),
                                tr().with(
                                        td().with(span(checkINNHtmLabel)),
                                        td().attr(Attr.ALIGN, "center").with(ImageEmbedder.resultIconElement(true, ImageEmbedder.IconSet.Small))
                                ),
                                tr().with(
                                        td().with(span(checkULorIPHtmlLabel)),
                                        td().attr(Attr.ALIGN, "center").with(ImageEmbedder.resultIconElement(true, ImageEmbedder.IconSet.Small))
                                ),
                                tr().with(
                                        td().with(span(checkTrustwHtmlLabel)),
                                        td().attr(Attr.ALIGN, "center").with(ImageEmbedder.resultIconElement(true, ImageEmbedder.IconSet.Small))
                                ),
                                tr().with(
                                        td().with(span(checkCodNoHtmlLabel)),
                                        td().attr(Attr.ALIGN, "center").with(ImageEmbedder.resultIconElement(true, ImageEmbedder.IconSet.Small))
                                ),
                                tr().with(
                                        td().with(span(checkVIdDockHtmlLabel)),
                                        td().attr(Attr.ALIGN, "center").with(ImageEmbedder.resultIconElement(true, ImageEmbedder.IconSet.Small))
                                ),
                                tr().with(
                                        td().with(span(checkForDifficultFormatsHtmlLabel)),
                                        td().attr(Attr.ALIGN, "center").with(ImageEmbedder.resultIconElement(true, ImageEmbedder.IconSet.Small))
                                )
                        )
                )
                )
        ).render();

        try {
            System.out.println(result);
            File tempFile = new File(outputFileName);
            try (FileWriter writer = new FileWriter(tempFile)) {
                writer.write(result);
                writer.flush();
            }

            Desktop.getDesktop().browse(tempFile.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
