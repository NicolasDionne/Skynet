package utilitaires;

import javafx.stage.FileChooser;

import java.io.File;


public class FileChooserUtility {

    public static FileChooser createFileChooser() {
        FileChooser chooser = new FileChooser();

        String userDirectoryString = System.getProperty("user.home");
        File userDirectory = new File(userDirectoryString);
        if(!userDirectory.canRead()) {
            userDirectory = new File("c:/");
        }
        chooser.setInitialDirectory(userDirectory);
        return chooser ;
    }


}
