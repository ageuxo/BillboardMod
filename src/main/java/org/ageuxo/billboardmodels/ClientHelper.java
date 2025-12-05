package org.ageuxo.billboardmodels;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.fml.loading.FMLEnvironment;

import javax.annotation.ParametersAreNonnullByDefault;

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


}
