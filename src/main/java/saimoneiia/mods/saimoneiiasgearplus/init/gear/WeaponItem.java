package saimoneiia.mods.saimoneiiasgearplus.init.gear;

import net.minecraft.world.entity.player.Player;

public class WeaponItem extends BaseEquipment {
    protected int skillInputs = 3;

    public WeaponItem(int skillInputs) {
        this.skillInputs = skillInputs;
    }

    public int getRequiredSkillInputs() { return skillInputs; }

    protected void skill0(Player player) {}

    protected void skill1(Player player) {}

    protected void skill2(Player player) {}

    protected void skill3(Player player) {}

    public void castSkill(Player player, int skillCode) {
        int skillId = skillCode - (int) Math.pow(2, skillInputs);
        switch (skillId) {
            case 0 -> skill0(player);
            case 1 -> skill1(player);
            case 2 -> skill2(player);
            case 3 -> skill3(player);
            default -> System.out.println("Shouldn't get here when casting skills");
        }
    }
}
