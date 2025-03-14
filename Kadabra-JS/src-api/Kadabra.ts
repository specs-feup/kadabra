import JavaTypes from "@specs-feup/lara/api/lara/util/JavaTypes.js"

const ArgumentsParser = JavaTypes.getType("pt.up.fe.specs.util.parsing.arguments.ArgumentsParser");
const KadabraLauncher = JavaTypes.getType("weaver.gui.KadabraLauncher");

export default class Kadabra {

  /**
   * Launches a Kadabra weaving session.
   * @param {(string|Array)} args - The arguments to pass to the weaver, as if it was launched from the command-line
   * @return {Boolean} true if the weaver executes without problems, false otherwise
   */
  static runKadabra = function (args) {
    // If string, separate arguments
    if (typeof args === "string") {
      args = ArgumentsParser.newCommandLine().parse(args);
    }

    return KadabraLauncher.execute(args);
  };
}
