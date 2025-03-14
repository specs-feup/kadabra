laraImport("weaver.Query");

const manifest = Query.root().manifest;

for (const element of Query.searchFrom(manifest, "xmlElement", "action")) {
    console.log("Attributes: " + element.attributeNames);
    console.log("Attribute value: " + element.attribute("android:name"));

    element.setAttribute("android:name", "newValue");
}

console.log("Manifest:");
console.log(object2string(manifest.asJson));
