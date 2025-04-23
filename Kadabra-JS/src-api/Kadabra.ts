import KadabraJavaTypes from "./kadabra/KadabraJavaTypes.js";

export default class Kadabra {
    /**
     * Launches a Kadabra weaving session.
     * @param args - The arguments to pass to the weaver, as if it was launched from the command-line
     * @returns true if the weaver executes without problems, false otherwise
     */
    static runKadabra(args: string | Array<string>) {
        // If string, separate arguments
        if (typeof args === "string") {
            args =
                KadabraJavaTypes.ArgumentsParser.newCommandLine().parse(args);
        }

        return KadabraJavaTypes.KadabraLauncher.execute(args);
    }
}
