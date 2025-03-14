class Kadabra {
  /*
	static save() {
		console.log("Saving");
	}
*/

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

    const KadabraLauncher = Java.type("weaver.gui.KadabraLauncher");
    return KadabraLauncher.execute(args);
  };
}
