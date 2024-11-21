package me.lodaris.teleportplugin;

import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.UUID;

public class TeleportRequestManager {
    private final HashMap<UUID, TeleportRequest> requests = new HashMap<>();

    public void addRequest(Player sender, Player target) {
        UUID targetId = target.getUniqueId();
        requests.put(targetId, new TeleportRequest(sender.getUniqueId(), System.currentTimeMillis()));
    }

    public TeleportRequest getRequest(Player target) {
        return requests.get(target.getUniqueId());
    }

    public void removeRequest(Player target) {
        requests.remove(target.getUniqueId());
    }

    public boolean isRequestExpired(Player target) {
        TeleportRequest request = getRequest(target);
        if (request == null) return true;
        return (System.currentTimeMillis() - request.getRequestTime()) > 30_000;
    }

    public static class TeleportRequest {
        private final UUID senderId;
        private final long requestTime;

        public TeleportRequest(UUID senderId, long requestTime) {
            this.senderId = senderId;
            this.requestTime = requestTime;
        }

        public UUID getSenderId() {
            return senderId;
        }

        public long getRequestTime() {
            return requestTime;
        }
    }
}

