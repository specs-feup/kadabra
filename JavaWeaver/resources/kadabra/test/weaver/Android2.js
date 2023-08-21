laraImport("weaver.Query");

const manifest = Query.root().manifest;

for (const activity of Query.searchFrom(manifest, "xmlElement", {name: "activity"})) {
	
	// Looking for main activity
	if(activity.getAttribute("android:name") !== ".MainActivity") {
		continue;
	}

	// Print actions inside activity
	for(const action of Query.searchFrom(activity, "xmlElement", "action")) {
  		println("action: " + action.getAttribute("android:name"));		
	}

}