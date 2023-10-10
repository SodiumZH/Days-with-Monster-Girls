package net.sodiumstudio.dwmg.entities.handlers.hmag;

import java.util.Map;
import java.util.function.Supplier;

import net.sodiumstudio.nautils.ContainerHelper;
import net.sodiumstudio.nautils.containers.MapPair;
import net.sodiumstudio.nautils.math.RndUtil;

public class HandlerRedcap extends HandlerItemDropping
{

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Supplier<Double>> getDeltaProc() {
		return ContainerHelper.<String, Supplier<Double>>mapOf(
				MapPair.of("minecraft:wheat", () -> RndUtil.rndRangedDouble(0.02, 0.04)),
				MapPair.of("minecraft:bread", () -> RndUtil.rndRangedDouble(0.03, 0.06)),
				MapPair.of("hmag:cureberry", () -> RndUtil.rndRangedDouble(0.08, 0.12)),
				MapPair.of("hmag:randomberry", () -> RndUtil.rndRangedDouble(0.08, 0.12)),
				MapPair.of("hmag:exp_berry", () -> RndUtil.rndRangedDouble(0.08, 0.12)),
				MapPair.of("minecraft:golden_apple", () -> RndUtil.rndRangedDouble(0.10, 0.15)),
				MapPair.of("hmag:golden_tropical_fish", () -> RndUtil.rndRangedDouble(0.10, 0.15)),
				MapPair.of("minecraft:iron_axe", () -> RndUtil.rndRangedDouble(0.05, 0.07)),
				MapPair.of("minecraft:gold_axe", () -> RndUtil.rndRangedDouble(0.07, 0.10)),
				MapPair.of("minecraft:diamond_axe", () -> RndUtil.rndRangedDouble(0.12, 0.18)),
				MapPair.of("minecraft:netherite_axe", () -> RndUtil.rndRangedDouble(0.50, 1.00)),
				MapPair.of("twilightforest:maze_map_focus", () -> RndUtil.rndRangedDouble(0.15, 0.30))
				);
	}

	@Override
	public int getHoldingItemTime() {
		return 5 * 20;
	}

}
