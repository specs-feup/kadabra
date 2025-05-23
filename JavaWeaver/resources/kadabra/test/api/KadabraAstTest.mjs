import { KadabraAst } from "KADABRA/api/kadabra/KadabraAst.js";

for (const constant of KadabraAst.getConstantInitializations()) {
    console.log("Constant: " + constant.code);
}
