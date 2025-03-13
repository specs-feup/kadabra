laraImport("weaver.Weaver");
laraImport("weaver.Query");
laraImport("lara.Io");

function Test()	{
	//const counter = 0;

	// Select binary operators in each file
	for (const $op of Query.search('binaryExpression')) {
	
		if ($op.operator === '<') {
		
			// Store current operator
			var previousOp = $op.operator;

			// Set new operator
			$op.operator = '>';
			
			console.log($op.getAncestor('method').srcCode);

			// Create output folder for this code version
			//const outputFolder = Io.getPath("output_" + counter);
			//counter++;

			// Write modified code
			//Weaver.writeCode(outputFolder);

			// Restore operator
			$op.operator = previousOp;
		}
	}

	// Select unary operators and change them for --
	for (const $op of Query.search('method', {name: 'unaryTest'}).search('unaryExpression')) {
		// Store current operator
		var previousOp = $op.operator;
	
		// Set new operator
		$op.operator = '--';
		
		console.log($op.getAncestor('method').srcCode);
	
		// Restore operator
		$op.operator = previousOp;
	}
	
	// Select unary operators and remove them
	for (const $op of Query.search('method', {name: 'unaryTest2'}).search('unaryExpression')) {
		
		// If operator is a single statement in a block, cannot replace it with operand
		// E.g., a++; cannot be replaced with a;, it does not compile in Java
		if (!$op.parent.isBlock) {
			$op.insertReplace($op.operand);
		}
	}
	
	console.log(Query.search('method', {name: 'unaryTest2'}).getFirst().srcCode);

	// Select unary operators and remove them
	for (const $op of Query.search('method', {name: 'unaryTest3'}).search('unaryExpression')) {
		$op.insertReplace("--a");
	}
	
	console.log(Query.search('method', {name: 'unaryTest3'}).getFirst().srcCode);
	
	// Select unary operator !, remove it and restore it
	for (const $op of Query.search('method', {name: 'unaryTest4'}).search('unaryExpression')) {
		
		// Copies the unary expression (e.g.,  !a)
		var $originalOp = $op.copy;

		// Replaces the unary expression with the operand (e.g., !a becomes a) and returns the new expression
		var $newOp = $op.insertReplace($op.operand);
		console.log(Query.search('method', {name: 'unaryTest4'}).getFirst().srcCode);

		// Replaces the new expression with a copy of the original expression (e.g., a becomes !a again)
		$newOp.insertReplace($originalOp);
		console.log(Query.search('method', {name: 'unaryTest4'}).getFirst().srcCode);
	}
}
