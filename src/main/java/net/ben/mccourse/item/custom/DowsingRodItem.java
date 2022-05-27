package net.ben.mccourse.item.custom;

import net.ben.mccourse.MCCourseMod;
import net.ben.mccourse.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class DowsingRodItem extends Item {

    public DowsingRodItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {

        if(pContext.getLevel().isClientSide) {
            BlockPos positionClicked = pContext.getClickedPos();
            Player player = pContext.getPlayer();
            player.swing(pContext.getHand());
            boolean foundBlock = false;

            for(int i = 0; i<= positionClicked.getY() + 64; i++) {
                Block blockBelow = pContext.getLevel().getBlockState(positionClicked.below(i)).getBlock();

                if(isValuableBlock(blockBelow)) {
                    outputValuableCoordinates(positionClicked.below(i), player, blockBelow);
                    foundBlock = true;
                    break;
                }
            }

            if(!foundBlock) {
                player.sendMessage(new TranslatableComponent("item.mccourse.dowsing_rod.no_valuables"), player.getUUID());
            }
        }
        pContext.getItemInHand().hurtAndBreak(1, pContext.getPlayer(),
                (player -> player.broadcastBreakEvent(player.getUsedItemHand())));
        return super.useOn(pContext);
    }

    private void outputValuableCoordinates(BlockPos blockPos, Player player, Block blockBelow) {

        player.sendMessage(new TextComponent("Found " + blockBelow.getRegistryName().toString().toString() + " at " +
                "(" + blockPos.getX() + ", " + blockPos.getY() + ", " + blockPos.getZ() + ")"), player.getUUID());
    }

    private boolean isValuableBlock(Block block) {
        return block == Blocks.COAL_ORE || block == Blocks.COPPER_ORE || block == Blocks.GOLD_ORE || block == Blocks.IRON_ORE ||
                block == Blocks.LAPIS_ORE || block == Blocks.EMERALD_ORE || block == Blocks.REDSTONE_ORE || block == Blocks.DIAMOND_ORE ||
                block == Blocks.DEEPSLATE_EMERALD_ORE || block == Blocks.DEEPSLATE_COAL_ORE || block == Blocks.DEEPSLATE_COPPER_ORE
                || block == Blocks.DEEPSLATE_GOLD_ORE || block == Blocks.DEEPSLATE_IRON_ORE || block == Blocks.DEEPSLATE_DIAMOND_ORE || block == Blocks.DEEPSLATE_REDSTONE_ORE
                || block == Blocks.DEEPSLATE_LAPIS_ORE;
    }
}
