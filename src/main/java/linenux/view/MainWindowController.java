package linenux.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Created by yihangho on 10/16/16.
 */
public class MainWindowController {
    @FXML
    private SplitPane splitPane;
    @FXML
    private AnchorPane commandBoxContainer;

    @FXML
    private void initialize() {
        setupTodoBox();
        setupDeadlineBox();
        setupEventBox();
        setupResultBox();
        splitPane.setDividerPositions(0.25, 0.50, 0.75);
        setupCommandBox();
    }

    private void setupTodoBox() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainWindowController.class.getResource("/view/TodoBox.fxml"));
            AnchorPane todoBox = loader.load();
            splitPane.getItems().add(todoBox);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupDeadlineBox() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainWindowController.class.getResource("/view/DeadlineBox.fxml"));
            AnchorPane deadlineBox = loader.load();
            splitPane.getItems().add(deadlineBox);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupEventBox() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainWindowController.class.getResource("/view/EventBox.fxml"));
            AnchorPane eventBox = loader.load();
            splitPane.getItems().add(eventBox);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupResultBox() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainWindowController.class.getResource("/view/ResultBox.fxml"));
            AnchorPane resultBox = loader.load();
            splitPane.getItems().add(resultBox);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupCommandBox() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainWindowController.class.getResource("/view/CommandBox.fxml"));
            TextField field = loader.load();
            AnchorPane.setTopAnchor(field, 0.0);
            AnchorPane.setRightAnchor(field, 0.0);
            AnchorPane.setBottomAnchor(field, 0.0);
            AnchorPane.setLeftAnchor(field, 0.0);
            commandBoxContainer.getChildren().add(field);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
