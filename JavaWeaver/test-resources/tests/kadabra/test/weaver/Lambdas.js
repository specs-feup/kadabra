import Query from "@specs-feup/lara/api/weaver/Query.js";

var code = "";
for (const $jp of Query.root().descendants) {
    if ($jp.joinPointType === "method" || $jp.joinPointType === "constructor") {
        code += $jp.code;
    }
}

console.log("Code from methods:\n" + code);

console.log("Code from app:\n" + Query.root().code);
