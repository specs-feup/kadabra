/**
 * Copyright 2016 SPeCS.
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

package kadabra.resources;

import pt.up.fe.specs.util.providers.ResourceProvider;

public enum LaraAPIResources implements ResourceProvider {

    Logger(PackageProvider.instPackage, "Logger"),

    ;

    private LaraAPIResources(String subPackage, String fileName) {
	resource = basePackage + subPackage + fileName + extension;
    }

    private final String resource;
    static final String basePackage = "lara/";
    static final String extension = ".lara";

    @Override
    public String getResource() {

	return resource;
    }

}

interface PackageProvider {
    static final String adaptPackage = "adapt/";
    static final String monitorPackage = "monitor/";
    static final String instPackage = "inst/";
    static final String systemPackage = "system/";
}
