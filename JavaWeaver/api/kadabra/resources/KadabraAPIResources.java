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

public enum KadabraAPIResources implements ResourceProvider {

    CounterList(PackageProvider.monitorPackage, "CounterList"),
    Counter(PackageProvider.monitorPackage, "Counter"),
    Timer(PackageProvider.monitorPackage, "Timer"),
    Specializer(PackageProvider.adaptPackage, "Specializer"),
    Adapter(PackageProvider.adaptPackage, "Adapter"),
    DataFeature(PackageProvider.adaptPackage, "DataFeature"),
    Configs(PackageProvider.adaptPackage, "Configs"),
    Measurers(PackageProvider.adaptPackage, "Measurers"),
    Algorithm(PackageProvider.adaptPackage, "Algorithm"),
    Autotuner(PackageProvider.adaptPackage, "Autotuner"),
    VersionTester(PackageProvider.adaptPackage, "VersionTester"),
    CpuInfo(PackageProvider.systemPackage, "CpuInfo"),

    BinaryExpressionMutator("mutation/", "BinaryExpressionMutator"),

    KADABRA_JAVA_TYPES("_KadabraJavaTypes"),
    KADABRA_NODES("KadabraNodes"),

    Factory("Factory"),
    Utils("Utils"),
    Transform("Transform"),
    Kadabra("Metrics"),
    Types("Types"),
    Concurrent("Concurrent");

    private KadabraAPIResources(String subPackage, String fileName) {
        resource = basePackage + subPackage + fileName + extension;
    }

    private KadabraAPIResources(String fileName) {
        resource = basePackage + fileName + extension;
    }

    private final String resource;
    static final String basePackage = "kadabra/";
    static final String extension = ".lara";

    @Override
    public String getResource() {

        return resource;
    }

}
