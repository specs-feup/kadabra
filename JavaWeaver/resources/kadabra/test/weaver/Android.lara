aspectdef Android

	select app.androidManifest.element{"action"} end
	apply
		console.log("Attributes: " + $element.attributeNames);
		console.log("Attribute value: " + $element.attribute("android:name"));
		
		$element.setAttribute("android:name", "newValue");
	end


	select app.androidManifest end
	apply
		console.log("Manifest:");
		printlnObject($androidManifest.asJson);
	end

end

