laraImport("kadabra.KadabraAst");

for (const constant of KadabraAst.getConstantInitializations()) {
    console.log("Constant: " + constant.code);
}
