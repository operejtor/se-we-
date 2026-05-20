package pl.plneconomy.item;

import java.util.EnumMap;
import java.util.Map;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import pl.plneconomy.PLNEconomyMod;

public final class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PLNEconomyMod.MOD_ID);
    public static final Map<Denomination, RegistryObject<BanknoteItem>> BY_DENOM = new EnumMap<>(Denomination.class);

    static {
        for (Denomination denomination : Denomination.values()) {
            BY_DENOM.put(denomination, ITEMS.register(denomination.key(), () -> new BanknoteItem(denomination)));
        }
    }

    private ModItems() {
    }
}
