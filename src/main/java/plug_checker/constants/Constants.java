package plug_checker.constants;

public class Constants {
    public static final String xslFieldLabel = "Выберите файл xsl";
    public static final String xsdFieldLabel = "Выберите файл xsd";
    public static final String xslPathErrorText = "xsl файл не найден!";
    public static final String xsdPathErrorText = "xsd файл не найден!";
    public static final String createReportButtonLabel = "Сформировать отчет";
    public static final String reportHtmlTitle = "Отчет по тестированию";
    public static final String idFileHtmlLabel = "Наличие переменной префикса ИдФайл";
    public static final String checkPrefixHtmlLabel = "Проверка префикса на латинские буквы";
    public static final String checkNecessaryParametersHtmlLabel = "Проверка на наличие обязательных переменных";
    public static final String checkIdPolExtractHtmlLabel = "Проверка на Извлечение ИдПол из ИдФайла";
    public static final String checkINNHtmLabel = "Проверка на определение ИНН и КПП";
    public static final String checkULorIPHtmlLabel = "Проверка на то, кто сдает документ (ЮЛ и/или ИП)";
    public static final String checkTrustwHtmlLabel = "Проверка на доверенность";
    public static final String checkCodNoHtmlLabel = "Проверка на наличие КодНО";
    public static final String checkVIdDockHtmlLabel = "Проверка на наличие ВидДок";
    public static final String checkForDifficultFormatsHtmlLabel = "Проверка на пустышку сложных форматов НДС, бухбаланс";
    public static final String checkHtmlLabel = "Проверка";
    public static final String resultHtmlLabel = "Результат";

    public static final String filePrefixAttribute = "PaternFile";
    public static final String alfavit1Attribute = "Alfavit1";
    public static final String alfavit2Attribute = "Alfavit2";
    public static final String fileStartTestAttribute = "not(starts-with(translate($ИдФайл, $Alfavit1, $Alfavit2),$PaternFile))";
    public static final String invalidMsgAttribute = "InvalidMsg";
    public static final String invalidMsgSelectAttribute = "string('Неверное значение реквизита: ')";
    public static final String idFileAttribute = "ИдФайл";
    public static final String idFileSelectAttribute = "/Файл/@ИдФайл";
    public static final String idPolFileAttribute = "ИдПолФайл";
}
