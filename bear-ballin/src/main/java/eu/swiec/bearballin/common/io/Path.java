/*
 * Bear Ballin - Testing framework
 *
 * Copyright 2010 Grzegorz Swiec (swiec.eu).
 * https://github.com/swiec/bear-ballin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package eu.swiec.bearballin.common.io;

public class Path {

    private final String path;

    private final String dirName;
    private final String fileName;
    private final String baseFileName;
    private final String fileExtension;

    public Path(String path) {
        super();
        this.path = path;

        int lastSlash = lastSlashIndex();
        this.dirName = path.substring(0, lastSlash + 1);
        this.fileName = path.substring(lastSlash + 1);
        this.baseFileName = path.substring(lastSlash + 1, path.lastIndexOf("."));
        this.fileExtension = path.substring(path.lastIndexOf(".") + 1);
    }

    private int lastSlashIndex() {
        int index = 0;

        index = path.lastIndexOf(47);
        if (index < path.lastIndexOf(92)) {
            index = path.lastIndexOf(92);
        }
gswiec
        return index;
    }

    public String getBaseDir() {
        return dirName;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public String getBaseFileName() {
        return baseFileName;
    }

}
