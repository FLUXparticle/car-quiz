import org.gradle.api.DefaultTask;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.FileType;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.PathSensitive;
import org.gradle.api.tasks.PathSensitivity;
import org.gradle.api.tasks.TaskAction;
import org.gradle.work.FileChange;
import org.gradle.work.Incremental;
import org.gradle.work.InputChanges;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public abstract class Convert extends DefaultTask {

    @InputFile
    public abstract RegularFileProperty getXsltFile();

    @Incremental
    @PathSensitive(PathSensitivity.NAME_ONLY)
    @InputDirectory
    public abstract DirectoryProperty getInputDir();

    @OutputDirectory
    public abstract DirectoryProperty getOutputDir();

    @TaskAction
    public void performTask(InputChanges inputChanges) throws IOException {
        DirectoryProperty inputDir = getInputDir();
        DirectoryProperty outputDir = getOutputDir();
        var fileChanges = inputChanges.getFileChanges(inputDir);

        for (FileChange fileChange : fileChanges) {
            if (fileChange.getFileType() == FileType.FILE) {
                System.out.println(fileChange);

                File xmlFile = fileChange.getFile();
                String filename = xmlFile.getName();
                String basename = filename
                        .substring(0, filename.lastIndexOf('.'));

                File foFile = outputDir.file(basename + ".fo")
                        .get().getAsFile();

                Files.writeString(foFile.toPath(), "");
            }
        }
    }


}
