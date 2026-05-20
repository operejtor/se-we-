package pl.plneconomy;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import pl.plneconomy.item.ModItems;

@Mod(PLNEconomyMod.MOD_ID)
public class PLNEconomyMod {
    public static final String MOD_ID = "plneconomy";

    public PLNEconomyMod() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.ITEMS.register(eventBus);
    }
}
