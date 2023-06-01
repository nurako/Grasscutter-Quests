package emu.grasscutter.game.quest.content;

import emu.grasscutter.game.quest.GameQuest;
import emu.grasscutter.game.quest.QuestValueContent;
import emu.grasscutter.data.common.quest.SubQuestData.QuestContentCondition;

import static emu.grasscutter.game.quest.enums.QuestContent.QUEST_CONTENT_MONSTER_DIE;

@QuestValueContent(QUEST_CONTENT_MONSTER_DIE)
public class ContentMonsterDie extends BaseContent {
    @Override
    public boolean execute(GameQuest quest, QuestContentCondition condition, String paramStr, int... params) {
        return condition.getParam()[0] == params[0];
    }
}