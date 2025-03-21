function L2Provider() {
    this.orElse = function (defValue) {
        return (
            "weaver.kadabra.system.CpuInfo.getL2CacheSizeOrElse(" +
            defValue +
            ")"
        );
    };
}

export default function CpuInfo() {
    L2Provider.prototype.toString = function myToString() {
        return "weaver.kadabra.system.CpuInfo.getL2CacheSize()";
    };

    return {
        l2: new L2Provider(),
        numThreads: "weaver.kadabra.system.CpuInfo.getCPUThreads()",
    };
}
