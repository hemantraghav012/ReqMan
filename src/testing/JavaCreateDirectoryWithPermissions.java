package testing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

public class JavaCreateDirectoryWithPermissions {

    public static void main(String[] args) throws IOException {

        String fileName = "/home/janbodnar/tmp/newdir";

        Path mypath = Paths.get(fileName);

        if (!Files.exists(mypath)) {
        
            Set<PosixFilePermission> permissions = PosixFilePermissions.fromString("rwxr--r--");
            FileAttribute<Set<PosixFilePermission>> fileAttributes = PosixFilePermissions.asFileAttribute(permissions);

            Files.createDirectory(mypath, fileAttributes);
            System.out.println("Directory created");
            
        } else {
            System.out.println("Directory already exists");
        }
    }
}