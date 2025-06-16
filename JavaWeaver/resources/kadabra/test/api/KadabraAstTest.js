import { KadabraAst } from "@specs-feup/kadabra/api/kadabra/KadabraAst.js";

for (const constant of KadabraAst.getConstantInitializations()) {
    console.log("Constant: " + constant.code);
}
