package tk.patsite.patsmpmod;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;

public final class Settings {
    private static final class Values {
        private static final Identifier PACKET_MODVERSION_ID = new Identifier("patsmpmod", "modversion");
        private static final Identifier PACKET_RESOURCEPACK_CHANGE_ID = new Identifier("patsmpmod", "resourcepackchange");
        private static final String MOD_ID = "patsmpmod";
        private static final String OUTDATED_MOD_KICK_REASON =  "Invalid modpack! \n" +
                                                                "Download original modpack at: \n" +
                                                                "https://org.patsite.tk/";

    }


    public static String getModVersion() {
        return FabricLoader.getInstance().getModContainer(Values.MOD_ID).orElseThrow().getMetadata().getVersion().toString();
    }
    public static String getOutdatedModKickReason() {
        return Values.OUTDATED_MOD_KICK_REASON;
    }
    public static Identifier getPacketModversionId() {
        return Values.PACKET_MODVERSION_ID;
    }
}
