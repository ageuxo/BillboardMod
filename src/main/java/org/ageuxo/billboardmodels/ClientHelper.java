package org.ageuxo.billboardmodels;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.ageuxo.billboardmodels.model.BillboardSpriteModel;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ClientHelper {

    public static TextureAtlasSprite getParticleSprite(BlockState state) {
        if (!FMLEnvironment.dist.isClient()) {
            throw new RuntimeException("Don't call this on servers.");
        }

        return Minecraft.getInstance()
                .getBlockRenderer()
                .getBlockModel(state)
                .getParticleIcon(ModelData.EMPTY);
    }

    public static Optional<BillboardSpriteModel> getBillboardModel(BlockState state) {
        if (Minecraft.getInstance().getBlockRenderer().getBlockModel(state) instanceof BillboardSpriteModel billboardModel) {
            return Optional.of(billboardModel);
        }

        return Optional.empty();
    }


}
