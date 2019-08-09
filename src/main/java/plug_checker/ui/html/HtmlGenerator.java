package plug_checker.ui.html;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;

import static j2html.TagCreator.*;
import static j2html.TagCreator.h1;
import static plug_checker.constants.Constants.*;

public class HtmlGenerator {
    // TODO: replace hardcoded "+" with result Map from checker
    public static void generateHtml(){
        String result = html(
                head(
                        title(reportHtmlTitle),
                        // link().withRel("stylesheet").withHref("/css/main.css")
                        style("table, th, td {\n" +
                                "  border: 1px solid black;\n" +
                                "  border-collapse: collapse;\n" +
                                "}")
                ),
                body(
                        main(attrs("#main.content"),
                                h1(reportHtmlTitle),
                                table().with(
                                        tr().with(
                                               th().with(span(checkHtmlLabel)),
                                               th().with(span(resultHtmlLabel))
                                        ),
                                        tr().with(
                                                td().with(span(idFileHtmlLabel)),
                                                td().with(span("+"))
                                        ),
                                        tr().with(
                                                td().with(span(checkPrefixHtmlLabel)),
                                                td().with(span("+"))
                                        ),
                                        tr().with(
                                                td().with(span(checkNecessaryParametersHtmlLabel)),
                                                td().with(span("+"))
                                        ),
                                        tr().with(
                                                td().with(span(checkIdPolExtractHtmlLabel)),
                                                td().with(span("+"))
                                        ),
                                        tr().with(
                                                td().with(span(checkINNHtmLabel)),
                                                td().with(span("+"))
                                        ),
                                        tr().with(
                                                td().with(span(checkULorIPHtmlLabel)),
                                                td().with(span("+"))
                                        ),
                                        tr().with(
                                                td().with(span(checkTrustwHtmlLabel)),
                                                td().with(span("+"))
                                        ),

                                        tr().with(
                                                td().with(span(checkCodNoHtmlLabel)),
                                                td().with(span("+"))
                                        ),

                                        tr().with(
                                                td().with(span(checkVIdDockHtmlLabel)),
                                                td().with(span("+"))
                                        ),

                                        tr().with(
                                                td().with(span(checkForDifficultFormatsHtmlLabel)),
                                                td().with(span("+"))
                                        )
                                )
                        )

                )
        ).render();

        try {
            System.out.println(result);
            File tempFile = File.createTempFile("output", ".html");
            FileWriter writer = new FileWriter(tempFile);
            writer.write(result);
            writer.close();

            Desktop.getDesktop().browse(tempFile.toURI());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
