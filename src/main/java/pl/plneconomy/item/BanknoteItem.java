package pl.plneconomy.item;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class BanknoteItem extends Item {
    private final Denomination denomination;

    public BanknoteItem(Denomination denomination) {
        super(new Item.Properties().stacksTo(64));
        this.denomination = denomination;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("Wartość: " + Denomination.fmt(denomination.grosze())).withStyle(ChatFormatting.GOLD));
        if (stack.getCount() > 1) {
            tooltip.add(Component.literal("Łącznie: " + Denomination.fmt(denomination.grosze() * stack.getCount()))
                    .withStyle(ChatFormatting.YELLOW));
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return InteractionResultHolder.pass(player.getItemInHand(hand));
    }
}
