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

package weaver.utils.weaving;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import spoon.reflect.factory.Factory;
import spoon.reflect.reference.CtArrayTypeReference;
import spoon.reflect.reference.CtTypeReference;
import tdrc.utils.StringUtils;
import weaver.utils.element.CtTypeReferenceUtils;

public class TypeUtils {
    /**
     * input: String,List<Integer>,Map<String,String>,Map<String,Map<String,String>> output: String | List<Integer> |
     * Map<String,String> | Map<String,Map<String,String>
     * 
     * @param type
     */
    public static List<String> parseGenerics(String type) {

	List<String> actualTypes = new ArrayList<>();
	String currentString = "";
	int numtags = 0;

	for (int i = 0; i < type.length(); i++) {
	    char charAt = type.charAt(i);
	    switch (charAt) {
	    case '>':
		numtags--;
		currentString += charAt;
		break;
	    case '<':
		numtags++;
		currentString += charAt;
		break;
	    case ',':
		if (numtags == 0) {
		    actualTypes.add(currentString);
		    currentString = "";
		} else {
		    currentString += charAt;
		}
		break;
	    case ' ':
	    case '\t':
	    case '\r':
	    case '\n':
		break;
	    default:
		currentString += charAt;
		break;
	    }
	}
	actualTypes.add(currentString);
	return actualTypes;
    }

    /**
     * 
     * @param fieldType
     * @param factory
     * @return
     */
    public static CtTypeReference<Object> typeOf(String fieldType, final Factory factory) {
	String baseType = fieldType;
	List<String> generics;
	if (baseType.contains("<")) {
	    baseType = baseType.substring(0, baseType.indexOf("<"))
		    + baseType.substring(baseType.lastIndexOf(">") + 1); // So it includes arrays definition
	    generics = getGenerics(fieldType);
	} else {
	    generics = Collections.emptyList();
	}

	final CtTypeReference<Object> fieldTypeRef;

	fieldTypeRef = factory.Type().createReference(baseType);

	for (String string : generics) {
	    CtTypeReference<Object> generic = typeOf(string, factory);
	    fieldTypeRef.addActualTypeArgument(generic);
	}

	return fieldTypeRef;
    }

    /**
     * 
     * @param type
     * @return
     */
    private static List<String> getGenerics(String type) {
	if (!type.contains("<")) {
	    return Collections.emptyList();
	}
	// E.gs.: List<String> | Map<String,String> | Map<String,List<String>> | Map<String,Map<String,String>>
	type = type.substring(type.indexOf("<") + 1, type.lastIndexOf(">"));
	// type = String | String,String | String,List<String> | String,Map<String,String>

	return parseGenerics(type);
    }

    public static String getTypeString(CtTypeReference<?> type) {
	if (type instanceof CtArrayTypeReference) {
	    return getTypeString((CtArrayTypeReference<?>) type);
	}
	// TODO generics
	return CtTypeReferenceUtils.getType(type);
    }

    public static String getTypeString(CtArrayTypeReference<?> type) {
	String typeStr = CtTypeReferenceUtils.getType(type);
	int dimension = type.getDimensionCount();
	return typeStr + StringUtils.repeat("[]", dimension);
	// int dimension = 1;
	// CtTypeReference<?> typeRef = type;
	// while ((typeRef = type.getComponentType()) != null) {
	// dimension++;
	// }
    }
}
