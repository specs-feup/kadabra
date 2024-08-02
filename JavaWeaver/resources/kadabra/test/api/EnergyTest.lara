import lara.code.Energy;

aspectdef InstrumentCode
	
	// Instrument call
	var energy = new Energy();

	select type.executable.call end
	apply
		energy.measure($call, "Energy:");
	end
	
	select file end
	apply
		console.log($file.srcCode);
	end
	
end
