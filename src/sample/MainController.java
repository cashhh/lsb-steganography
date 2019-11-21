package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainController {

    private FileChooser fileChooser = new FileChooser();

    public TextField coverImageFilepath;
    public TextField hiddenImageFilepath;
    public TextField outputImageFilepath;
    public Button coverImageBrowseButton;
    public Button hiddenImageBrowseButton;
    public Button outputImageBrowseButton;
    public CheckBox extraInformationCheckbox;
    public Button mergeImageButton;
    private File coverImage;
    private File hiddenImage;
    private File outputImage;

    public void openFileChooser(ActionEvent actionEvent) {
        Stage stage = App.getPrimaryStage();
        if(actionEvent.getSource().equals(coverImageBrowseButton)) {
            fileChooser.setTitle("Select Cover Image File");
            coverImage = fileChooser.showOpenDialog(stage);
            if (coverImage != null) {
                coverImageFilepath.setText(coverImage.getAbsolutePath());
            }

        } else if (actionEvent.getSource().equals(hiddenImageBrowseButton)) {
            fileChooser.setTitle("Select Hidden Image File");
            hiddenImage = fileChooser.showOpenDialog(stage);
            if (hiddenImage != null) {
                hiddenImageFilepath.setText(hiddenImage.getAbsolutePath());
            }
        } else if (actionEvent.getSource().equals(outputImageBrowseButton)) {
            fileChooser.setTitle("Select Output Image File");
            outputImage = fileChooser.showOpenDialog(stage);
            if (outputImage != null) {
                outputImageFilepath.setText(outputImage.getAbsolutePath());
            }
        }
    }

    public void mergeImage(ActionEvent actionEvent) throws IOException {
        BufferedImage coverImage = ImageIO.read(this.coverImage);
        BufferedImage hiddenImage = ImageIO.read(this.hiddenImage);

        BufferedImage stegoImage = LSB.merge(coverImage, hiddenImage);

        if (extraInformationCheckbox.isSelected()) {
            System.out.println("Convert image and show extra info");
        } else {
            if (saveImage(stegoImage)) {
                System.out.println("File saved");
            } else {
                System.out.println("Error occured");
            }
        }
    }

    private boolean saveImage(BufferedImage stegoImage) {
        try {
            File file = new File("");
            ImageIO.write(stegoImage, "png", file);

            return true;
        } catch (IOException e) {
            e.printStackTrace();

            return false;
        }
    }

    public void exitProgram(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void openHelpDialog(ActionEvent actionEvent) {

    }
}
