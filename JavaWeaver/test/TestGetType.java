import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import spoon.Launcher;
import weaver.kadabra.util.KadabraLog;
import weaver.utils.weaving.TypeUtils;

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

public class TestGetType {

    @Test
    public void test() {
        Launcher launcher = new Launcher();
        launcher.buildModel();
        String string = "String,String,String,String";
        List<String> parseGenerics = TypeUtils.parseGenerics(string);
        KadabraLog.info(parseGenerics.size() + "->" + parseGenerics);
        assertEquals(4, parseGenerics.size());

        string = "String,    List<String>,\tMap<String,String>,\nMap<String,Map<java.lang.String ,String> >";
        parseGenerics = TypeUtils.parseGenerics(string);
        KadabraLog.info(parseGenerics.size() + "->" + parseGenerics);
        assertEquals(4, parseGenerics.size());
    }

}
