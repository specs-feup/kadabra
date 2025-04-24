class L2Provider {
    orElse(defValue: string): string {
        return (
            "weaver.kadabra.system.CpuInfo.getL2CacheSizeOrElse(" +
            defValue +
            ")"
        );
    }

    toString(): string {
        return "weaver.kadabra.system.CpuInfo.getL2CacheSize()";
    }
}

export default function CpuInfo() {
    return {
        l2: new L2Provider(),
        numThreads: "weaver.kadabra.system.CpuInfo.getCPUThreads()",
    };
}
