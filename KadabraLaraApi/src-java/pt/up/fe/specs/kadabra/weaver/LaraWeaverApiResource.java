/**
 * Copyright 2013 SuikaSoft.
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

package pt.up.fe.specs.kadabra.weaver;

import org.lara.interpreter.weaver.utils.LaraResourceProvider;

/**
 * @author Joao Bispo
 *
 */
public enum LaraWeaverApiResource implements LaraResourceProvider {
	KADABRA_ELSE_JP("jp/KadabraElseJp.lara"),
	KADABRA_FILE_JP("jp/KadabraFileJp.lara"),
	KADABRA_LOOP_JP("jp/KadabraLoopJp.lara"),
	KADABRA_IF_JP("jp/KadabraIfJp.lara"),
    KADABRA_BINARY_JP("jp/KadabraBinaryJp.lara"),
    KADABRA_DECL_JP("jp/KadabraDeclJp.lara"),
	KADABRA_CONSTRUCTOR_CALL_JP("jp/KadabraConstructorCallJp.lara"),
	KADABRA_CONSTRUCTOR_JP("jp/KadabraConstructorJp.lara"),
	KADABRA_PARAM_JP("jp/KadabraParamJp.lara"),
	KADABRA_VAR_REF_JP("jp/KadabraVarRefJp.lara"),
	KADABRA_VAR_DECL_JP("jp/KadabraVarDeclJp.lara"),	
	KADABRA_TYPE_JP("jp/KadabraTypeJp.lara"),
	KADABRA_FIELD_JP("jp/KadabraFieldJp.lara"),
	KADABRA_FIELD_REF_JP("jp/KadabraFieldRefJp.lara"),
	KADABRA_MEMBER_CALL_JP("jp/KadabraMemberCallJp.lara"),
	KADABRA_CALL_JP("jp/KadabraCallJp.lara"),
	KADABRA_FUNCTION_JP("jp/KadabraFunctionJp.lara"),
	KADABRA_METHOD_JP("jp/KadabraMethodJp.lara"),
    KADABRA_CLASS_TYPE_JP("jp/KadabraClassTypeJp.lara"),
	KADABRA_CLASS_JP("jp/KadabraClassJp.lara"),
    KADABRA_INTERFACE_JP("jp/KadabraInterfaceJp.lara"),
    KADABRA_JOIN_POINT("jp/KadabraJoinPoint.lara"),
    COMMON_JOIN_POINTS("jp/CommonJoinPoints.lara"),

    //JOINPOINTS("JoinPoints.lara"),
    WEAVER_LAUNCHER("WeaverLauncher.lara");

    private final String resource;

    private static final String WEAVER_PACKAGE = "kadabra/";
    private static final String BASE_PACKAGE = "weaver/";

    /**
     * @param resource
     */
    private LaraWeaverApiResource(String resource) {
        this.resource = WEAVER_PACKAGE + getSeparatorChar() + BASE_PACKAGE + resource;
    }

    /* (non-Javadoc)
     * @see org.suikasoft.SharedLibrary.Interfaces.ResourceProvider#getResource()
     */
    @Override
    public String getOriginalResource() {
        return resource;
    }

}
