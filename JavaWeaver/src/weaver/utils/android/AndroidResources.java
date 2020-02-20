/**
 * Copyright 2020 SPeCS.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License. under the License.
 */

package weaver.utils.android;

import java.io.File;
import java.util.List;

import pt.up.fe.specs.util.SpecsIo;

/**
 * Read-only, for now.
 * 
 * @author JoaoBispo
 *
 */
public class AndroidResources {

    // Can be null
    private final File srcFolder;

    public AndroidResources(File srcFolder) {
        this.srcFolder = srcFolder;
    }

    public File getAndroidManifest() {
        if (srcFolder == null) {
            return null;
        }

        var androidManifest = new File(srcFolder, "main/AndroidManifest.xml");

        return androidManifest.isFile() ? androidManifest : null;
    }

    /**
     * Creates a AndroidResources instance from the given list of sources.
     * 
     * @param sources
     * @return
     */
    public static AndroidResources newInstance(List<File> sources) {
        // Try to find a 'src' folder with a 'main/AndroidManifest.xml'

        var srcFolder = sources.stream()
                .map(AndroidResources::getSrcFolder)
                .filter(src -> src != null)
                .findFirst()
                .orElse(null);

        return new AndroidResources(srcFolder);
    }

    private static File getSrcFolder(File path) {
        var candidateFolder = path;
        // System.out.println("PATH: " + path);
        // System.out.println("FILES: " + SpecsIo.getFilesRecursive(path));
        // Get parent in source is a file
        if (candidateFolder.isFile()) {
            candidateFolder = candidateFolder.getParentFile();
        }

        // Check if it is a folder named 'src'
        if (candidateFolder.getName().equals("src")) {
            if (hasAndroidManifest(candidateFolder)) {
                return candidateFolder;
            }
        }

        // Check if has any folder named 'src'
        return SpecsIo.getFoldersRecursive(candidateFolder).stream()
                .filter(file -> file.getName().equals("src") && hasAndroidManifest(file))
                .findFirst()
                .orElse(null);

    }

    private static boolean hasAndroidManifest(File srcFolder) {
        return new File(srcFolder, "main/AndroidManifest.xml").isFile();
        /*        
        var mainFolder = new File(srcFolder, "main");
        
        if (!mainFolder.isDirectory()) {
            return false;
        }
        
        var androidManifest = new File(mainFolder, "AndroidManifest.xml");
        
        return androidManifest.isFile();
        */
    }
}
