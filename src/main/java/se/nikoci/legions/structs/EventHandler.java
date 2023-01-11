package se.nikoci.legions.structs;

import se.nikoci.legions.events.LegionCreateEvent;

public interface EventHandler {

    //LegionCmd
    default void onLegionCreate(LegionCreateEvent e) {}
    default void onLegionDelete(){}
    default void onLegionNameChange(){}
    default void onLegionDescriptionChange(){}
    default void onLegionIconChange(){}
    default void onLegionLeaderChange(){}
    default void onLegionMemberJoin(){}
    default void onLegionMemberLeave(){}
    default void onLegionMemberDeath(){}
    default void onLegionPowerChange(){}
    default void onLegionMaxPowerChange(){}
    default void onLegionCoreSet(){}
    default void onLegionCoreBreak(){}
    default void onLegionCoreUpgrade(){}

}
