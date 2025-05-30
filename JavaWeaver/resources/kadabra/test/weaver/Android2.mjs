import Query from "@specs-feup/lara/api/weaver/Query.js";

const manifest = Query.root().manifest;

for (const activity of Query.searchFrom(manifest, "xmlElement", {
    name: "activity",
})) {
    // Looking for main activity
    if (activity.attribute("android:name") !== ".MainActivity") {
        continue;
    }

    // Print actions inside activity
    for (const action of Query.searchFrom(activity, "xmlElement", "action")) {
        console.log("action: " + action.attribute("android:name"));
    }
}
