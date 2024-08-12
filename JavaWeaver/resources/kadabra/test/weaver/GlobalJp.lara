aspectdef GlobalJp

	// App children
	select app end
	apply
		console.log("app num children: " + $app.numChildren);	
	end
	
	// File children
	select file end
	apply
		console.log("file num children: " + $file.numChildren);	
	end	

	// Statement children
	select type.method{"foo"}.statement end
	apply
		console.log("stmt children: " + $statement.children);
		console.log("stmt child 0: " + $statement.child(0));
		console.log("stmt num children: " + $statement.numChildren);
		
		break;
	end


	// App ast
	select app end
	apply
		console.log("app ast:\n" + $app.ast);	
	end
end
