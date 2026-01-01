class L2Provider {
    orElse(defValue) {
        return ("weaver.kadabra.system.CpuInfo.getL2CacheSizeOrElse(" +
            defValue +
            ")");
    }
    toString() {
        return "weaver.kadabra.system.CpuInfo.getL2CacheSize()";
    }
}
export default function CpuInfo() {
    return {
        l2: new L2Provider(),
        numThreads: "weaver.kadabra.system.CpuInfo.getCPUThreads()",
    };
}
//# sourceMappingURL=CpuInfo.js.map