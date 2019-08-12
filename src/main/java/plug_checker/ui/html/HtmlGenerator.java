package plug_checker.ui.html;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;

import static j2html.TagCreator.*;
import static j2html.TagCreator.h1;

import j2html.attributes.Attr;
import j2html.tags.ContainerTag;

import java.io.IOException;
import java.util.HashMap;

import static plug_checker.constants.Constants.*;

public class HtmlGenerator {
    public static void generateHtml(HashMap<String, String> checkResult, String outputFileName) {
        String result = html(head(
                title(reportHtmlTitle),
                style("table, th, td {\n"
                        + "  border: 1px solid black;\n"
                        + "  border-collapse: collapse;\n"
                        + "}")
                ),
                body(main(attrs("#main.content"),
                        h1(reportHtmlTitle),
                        table().with(
                                tr().with(
                                        th().with(span(checkHtmlLabel)),
                                        th().with(span(resultHtmlLabel))
                                ),
                                tr().with(
                                        td().with(span(idFileHtmlLabel)),
                                        generateOutputCell(checkResult.get(idFileHtmlLabel))
                                ),
                                tr().with(
                                        td().with(span(checkPrefixHtmlLabel)),
                                        generateOutputCell(checkResult.get(checkPrefixHtmlLabel))
                                ),
                                tr().with(
                                        td().with(span(checkNecessaryParametersHtmlLabel)),
                                        generateOutputCell("+")
                                ),
                                tr().with(
                                        td().with(span(checkIdPolExtractHtmlLabel)),
                                        generateOutputCell("+")
                                ),
                                tr().with(
                                        td().with(span(checkINNHtmLabel)),
                                        generateOutputCell("+")
                                ),
                                tr().with(
                                        td().with(span(checkULorIPHtmlLabel)),
                                        generateOutputCell("+")
                                ),
                                tr().with(
                                        td().with(span(checkTrustwHtmlLabel)),
                                        generateOutputCell("+")
                                ),
                                tr().with(
                                        td().with(span(checkCodNoHtmlLabel)),
                                        generateOutputCell("+")
                                ),
                                tr().with(
                                        td().with(span(checkVIdDockHtmlLabel)),
                                        generateOutputCell("+")
                                ),
                                tr().with(
                                        td().with(span(checkForDifficultFormatsHtmlLabel)),
                                        generateOutputCell("+")
                                )
                        )
                        )
                )
        ).render();

        try {
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

    private static ContainerTag generateOutputCell(String checkResult) {
        if("+".equals(checkResult)) {
            return td().attr(Attr.ALIGN, "center").with(ImageEmbedder.resultIconElement(true, ImageEmbedder.IconSet.Small));
        }
        return td().attr(Attr.ALIGN, "left").with(ImageEmbedder.resultIconElement(false, ImageEmbedder.IconSet.Small), span(checkResult));
    }

}
