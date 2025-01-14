package emu.grasscutter.game.quest.conditions;

import emu.grasscutter.data.common.quest.SubQuestData;
import emu.grasscutter.data.common.quest.SubQuestData.*;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.quest.QuestValueCond;
import lombok.val;

import static emu.grasscutter.game.quest.enums.QuestCond.QUEST_COND_ACTIVITY_END;

@QuestValueCond(QUEST_COND_ACTIVITY_END)
public class ConditionActivityEnd extends BaseCondition {

    @Override
    public boolean execute(Player owner, SubQuestData questData, QuestAcceptCondition condition, String paramStr, int... params) {
        val activityId = condition.getParam()[0];
        return owner.getActivityManager().hasActivityEnded(activityId);
    }

}
