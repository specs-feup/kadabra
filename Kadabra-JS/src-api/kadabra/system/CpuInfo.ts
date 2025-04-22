aspectdef CpuInfo
	output l2, numThreads end

	function L2Provider(){
	
		this.orElse = function(defValue){
			return 'weaver.kadabra.system.CpuInfo.getL2CacheSizeOrElse('+defValue+')';
		};
	};
	L2Provider.prototype.toString = function myToString() {
  		return "weaver.kadabra.system.CpuInfo.getL2CacheSize()";	
	};
	

	//GETL2.ORELSE
	l2 = new L2Provider();
	
	numThreads = "weaver.kadabra.system.CpuInfo.getCPUThreads()";
end
