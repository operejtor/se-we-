package pl.plneconomy.data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

public class BankData extends SavedData {
    public static final String DATA_NAME = "plneconomy";
    private final Map<UUID, Long> balances = new HashMap<>();

    public static BankData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(BankData::load, BankData::new, DATA_NAME);
    }

    public long getBalance(UUID uuid) {
        return balances.getOrDefault(uuid, 0L);
    }

    public void deposit(UUID uuid, long grosze) {
        balances.put(uuid, getBalance(uuid) + grosze);
        setDirty();
    }

    public boolean withdraw(UUID uuid, long grosze) {
        if (!hasSufficientBalance(uuid, grosze)) {
            return false;
        }
        balances.put(uuid, getBalance(uuid) - grosze);
        setDirty();
        return true;
    }

    public boolean transfer(UUID from, UUID to, long grosze) {
        if (!withdraw(from, grosze)) {
            return false;
        }
        deposit(to, grosze);
        return true;
    }

    public boolean hasSufficientBalance(UUID uuid, long grosze) {
        return getBalance(uuid) >= grosze;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        ListTag listTag = new ListTag();
        balances.forEach((uuid, amount) -> {
            CompoundTag row = new CompoundTag();
            row.putUUID("uuid", uuid);
            row.putLong("balance", amount);
            listTag.add(row);
        });
        tag.put("balances", listTag);
        return tag;
    }

    public static BankData load(CompoundTag tag) {
        BankData data = new BankData();
        ListTag listTag = tag.getList("balances", Tag.TAG_COMPOUND);
        for (Tag value : listTag) {
            CompoundTag row = (CompoundTag) value;
            data.balances.put(row.getUUID("uuid"), row.getLong("balance"));
        }
        return data;
    }
}
