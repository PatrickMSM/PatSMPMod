package tk.patsite.patsmpmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PatSMPMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LogManager.getLogger("PatSMPMod");

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		ServerPlayNetworking.registerGlobalReceiver(Settings.getPacketModversionId(), (server, player, handler, buf, responseSender) -> {
			// Client sent message with Mod Version
			// Check if version matches with the server version
			// The server mod is always updated. If they aren't equal
			// That means the client is outdated
			String clientVersion = buf.readString();

			if (!clientVersion.equals(Settings.getModVersion())) {
				// NOT EQUAL
				// Kick the client
				player.networkHandler.disconnect(Text.of(Settings.getOutdatedModKickReason()));
			}
		});

		ClientPlayConnectionEvents.JOIN.register((handler, sender, client)->{
			// Connected to a server
			// Send message to server with Mod Version
			PacketByteBuf buf = PacketByteBufs.create();

			// Write mod version to buffer
			buf.writeString(Settings.getModVersion());

			// Send message
			ClientPlayNetworking.send(Settings.getPacketModversionId(), buf);
		});


		LOGGER.info("Mod loaded!");
	}

}
