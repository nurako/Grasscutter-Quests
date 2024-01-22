package emu.grasscutter.game.entity;

import emu.grasscutter.data.binout.config.ConfigEntityGadget;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.game.quest.enums.QuestContent;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.server.event.entity.EntityDamageEvent;
import emu.grasscutter.utils.Position;
import lombok.Getter;
import org.anime_game_servers.gi_lua.models.ScriptArgs;

import static org.anime_game_servers.gi_lua.models.constants.EventType.EVENT_SPECIFIC_GADGET_HP_CHANGE;

public abstract class EntityBaseGadget extends GameEntity {
    @Getter(onMethod = @__(@Override))
    protected final Position position;
    @Getter(onMethod = @__(@Override))
    protected final Position rotation;

    @Getter private final int campId;
    @Getter private final int campType;

    public EntityBaseGadget(Scene scene) {
        this(scene, null, null);
    }

    public EntityBaseGadget(Scene scene, Position position, Position rotation) {
        this(scene, position, rotation, 0, 0);
    }

    public EntityBaseGadget(Scene scene, Position position, Position rotation, int campId, int campType) {
        super(scene);
        this.position = position != null ? position.clone() : new Position();
        this.rotation = rotation != null ? rotation.clone() : new Position();
        this.campId = campId;
        this.campType = campType;
    }

    public abstract int getGadgetId();

    @Override
    public int getEntityTypeId() {
        return getGadgetId();
    }

    @Override
    public void onDeath(int killerId) {
        super.onDeath(killerId); // Invoke super class's onDeath() method.

        getScene().getPlayers().forEach(p -> p.getQuestManager().queueEvent(QuestContent.QUEST_CONTENT_DESTROY_GADGET, this.getGadgetId()));
    }

    @Override
    public void callLuaHPEvent(EntityDamageEvent event) {
        super.callLuaHPEvent(event);
        getScene().getScriptManager().callEvent(new ScriptArgs(this.getGroupId(), EVENT_SPECIFIC_GADGET_HP_CHANGE, getConfigId(), getGadgetId())
            .setSourceEntityId(getId())
            .setParam3((int) this.getFightProperty(FightProperty.FIGHT_PROP_CUR_HP))
            .setEventSource(getConfigId())
        );
    }

    protected void fillFightProps(ConfigEntityGadget configGadget) {
        if (configGadget == null || configGadget.getCombat() == null) {
            return;
        }
        var combatData = configGadget.getCombat();
        var combatProperties = combatData.getProperty();

        var targetHp = combatProperties.getHP();
        setFightProperty(FightProperty.FIGHT_PROP_MAX_HP, targetHp);
        setFightProperty(FightProperty.FIGHT_PROP_BASE_HP, targetHp);
        if (combatProperties.isInvincible()) {
            targetHp = Float.POSITIVE_INFINITY;
        }
        setFightProperty(FightProperty.FIGHT_PROP_CUR_HP, targetHp);

        var atk = combatProperties.getAttack();
        setFightProperty(FightProperty.FIGHT_PROP_BASE_ATTACK, atk);
        setFightProperty(FightProperty.FIGHT_PROP_CUR_ATTACK, atk);

        var def = combatProperties.getDefence();
        setFightProperty(FightProperty.FIGHT_PROP_BASE_DEFENSE, def);
        setFightProperty(FightProperty.FIGHT_PROP_CUR_DEFENSE, def);

        setLockHP(combatProperties.isLockHP());
    }
}
