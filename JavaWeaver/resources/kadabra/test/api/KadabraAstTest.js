import { KadabraAst } from "../../../../../Kadabra-JS/src-api/kadabra/KadabraAst.js"

for (const constant of KadabraAst.getConstantInitializations()) {
    console.log("Constant: " + constant.code);
}
