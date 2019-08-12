package plug_checker.ui;

import plug_checker.checker.Checker;
import plug_checker.ui.html.HtmlGenerator;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import static plug_checker.constants.Constants.*;

public class MainWindow extends JFrame {

    private static final long serialVersionUID = -4492574305264279365L;

    public MainWindow() {
        initiateLayout();
        applyVisualConstraints();
        setActionListeners();
    }

    private final JButton getXslButton = new JButton(new ImageIcon(getClass().getResource("/images/choose_file_icon.png")));
    private final JButton getXsdButton = new JButton(new ImageIcon(getClass().getResource("/images/choose_file_icon.png")));

    private final JTextField xslPathField = new JTextField();
    private final JTextField xsdPathField = new JTextField();

    private final JLabel xslPathLabel = new JLabel(xslFieldLabel);
    private final JLabel xsdPathLabel = new JLabel(xsdFieldLabel);

    private final JLabel xslPathErrorLabel = new JLabel(xslPathErrorText);
    private final JLabel xsdPathErrorLabel = new JLabel(xsdPathErrorText);

    private final JButton createReport = new JButton(createReportButtonLabel);

    private boolean checkIfFileExists(String path) {
        File temp = new File(path);
        return temp.exists() && temp.canRead();
    }

    private String processChooseFileDialog() {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int returnValue = jfc.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            return selectedFile.getAbsolutePath();
        }
        return "";
    }

    private void initiateLayout() {
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
        setTitle("XSL/XSD document checker");

        setSize(600, 600);
        setMinimumSize(new Dimension(500, 500));
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);

        pack();
    }

    private void setActionListeners() {
        getXslButton.addActionListener(e -> {
            String filePath = processChooseFileDialog();
            if (!filePath.equals("")) {
                xslPathField.setText(filePath);
            }
        });

        getXsdButton.addActionListener(e -> {
            String filePath = processChooseFileDialog();
            if (!filePath.equals("")) {
                xsdPathField.setText(filePath);
            }
        });

        createReport.addActionListener(e -> {
            String xslFilePath = xslPathField.getText();
            String xsdFilePath = xsdPathField.getText();

            boolean xslFileExists = checkIfFileExists(xslFilePath);
            boolean xsdFileExists = checkIfFileExists(xsdFilePath);

            xslPathErrorLabel.setVisible(!xslFileExists);
            xsdPathErrorLabel.setVisible(!xsdFileExists);
            if (!(xslFileExists && xsdFileExists)) {
                return;
            }

            // TODO: add checker call here, transfer check result to generator
            Checker checker = new Checker(xslFilePath, xsdFilePath);
            HashMap<String, String> checkResult = checker.checkAllEntries();

            //   HtmlGenerator.generateHtml(checkResult);
            //            try (Stream<String> stream = Files.lines(Paths.get(xslFilePath))){
//                stream.forEach(System.out::println);
//            } catch (IOException ioexception){
//                ioexception.printStackTrace();
//            }
// TODO: add checker call here, transfer check result to generator
            try {

                HtmlGenerator.generateHtml(File.createTempFile("output", ".html").getAbsolutePath());
            } catch (IOException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void applyVisualConstraints() {
        getXslButton.setBorder(null);
        getXsdButton.setBorder(null);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 0, 10);
        this.add(xslPathLabel, constraints);

        constraints.gridy = 2;
        this.add(xsdPathLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 3;
        this.add(xslPathField, constraints);

        constraints.insets = new Insets(0, 10, 0, 10);
        constraints.gridy = 1;
        xslPathErrorLabel.setForeground(Color.red);
        xslPathErrorLabel.setVisible(false);
        this.add(xslPathErrorLabel, constraints);

        constraints.gridy = 3;
        xsdPathErrorLabel.setForeground(Color.red);
        xsdPathErrorLabel.setVisible(false);
        this.add(xsdPathErrorLabel, constraints);

        constraints.gridy = 2;
        this.add(xsdPathField, constraints);

        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.weightx = 0;
        constraints.insets = new Insets(10, 0, 10, 20);
        this.add(getXslButton, constraints);

        constraints.gridy = 2;
        this.add(getXsdButton, constraints);

        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.weightx = 2;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.anchor = GridBagConstraints.EAST;
        constraints.fill = GridBagConstraints.NONE;
        this.add(createReport, constraints);
    }
}
