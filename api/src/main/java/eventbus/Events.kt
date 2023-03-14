package eventbus

enum class Events {
    ACTOR_DEATH,
    ADDED_FRIEND,
    ANIMATION_CHANGED,
    APPLET_LOADED,
    AREA_SOUND_EFFECT_PLAYED,
    AUTOMATED_MENU,
    BEFORE_MENU_RENDER,
    BEFORE_RENDER,
    CANVAS_SIZE_CHANGED,
    CHAT_MESSAGE,
    CHEAT_ENTERED,
    CHECK_CLICK,
    CLAN_CHANNEL_CHANGED,
    CLAN_MEMBER_JOINED,
    CLAN_MEMBER_LEFT,
    SEND_CLICK_PACKET,
    CLIENT_LOADED,
    CLIENT_TICK,
    COMMAND_EXECUTED,
    CONFIG_CHANGED,
    DECORATIVE_OBJECT_CHANGED,
    DECORATIVE_OBJECT_DESPAWNED,
    DECORATIVE_OBJECT_SPAWNED,
    DIALOG_PROCESSED,
    DRAGGING_WIDGET_CHANGED,
    DRAW,
    DYNAMIC_OBJECT_ANIMATION_CHANGED,
    EXPERIENCE_GAINED,
    FOCUS_CHANGED,
    FRIENDS_CHAT_CHANGED,
    FRIENDS_CHAT_MEMBER_JOINED,
    FRIENDS_CHAT_MEMBER_LEFT,
    GAME_OBJECT_CHANGED,
    GAME_OBJECT_DESPAWNED,
    GAME_OBJECT_SPAWNED,
    GAME_STATE_CHANGED,
    GAME_TICK,
    GRAND_EXCHANGE_OFFER_CHANGED,
    GRAND_EXCHANGE_OFFER_COMPLETED,
    GRAND_EXCHANGE_SEARCHED,
    GRAPHIC_CHANGED,
    GRAPHICS_OBJECT_CREATED,
    GROUND_OBJECT_CHANGED,
    GROUND_OBJECT_SPAWNED,
    GROUND_OBJECT_DESPAWNED,
    HEALTH_BAR_UPDATED,
    HITSPLAT_APPLIED,
    INTERACT,
    INTERACTING_CHANGED,
    INVENTORY_CHANGED,
    INVOKE_MENU_ACTION,
    ITEM_CONTAINER_CHANGED,
    ITEM_DESPAWNED,
    ITEM_ADDED,
    ITEM_REMOVED,
    ITEM_OBTAINED,
    ITEM_QUANTITY_CHANGED,
    ITEM_SPAWNED,
    LOADING_TEXT_CHANGED,
    LOGIN_INDEX_CHANGED,
    LOGIN_STATE_CHANGED,
    MENU,
    MENU_ACTION_PROCESSED,
    MENU_ENTRY_ADDED,
    MENU_OPENED,
    MENU_OPTION_CLICKED,
    MENU_SHOULD_LEFT_CLICK,
    NAMEABLE_NAME_CHANGED,
    NPC_ACTION_CHANGED,
    NPC_CHANGED,
    NPC_DESPAWNED,
    NPC_LOOT_RECEIVED,
    NPC_SPAWNED,
    OVERHEAD_TEXT_CHANGED,
    OVERHEAD_PRAYER_CHANGED,
    OVERLAY_MENU_CHANGED,
    PACKET_QUEUED,
    PLANE_CHANGED,
    PLAYER_CHANGED,
    PLAYER_COMPOSITION_CHANGED,
    PLAYER_DESPAWNED,
    PLAYER_LOOT_RECEIVED,
    PLAYER_MENU_OPTIONS_CHANGED,
    PLAYER_SKULL_CHANGED,
    PLAYER_SPAWNED,
    PLUGIN_CHANGED,
    POST_HEALTHBAR,
    POST_ITEM_COMPOSITION,
    POST_STRUCT_COMPOSITION,
    PROJECTILE_MOVED,
    PROJECTILE_SPAWNED,
    REMOVED_FRIEND,
    RESUME_PAUSE_SENT,
    RESIZEABLE_CHANGED,
    SCRIPT_CALLBACK,
    SCRIPT_POST_FIRED,
    SCRIPT_PRE_FIRED,
    SOUND_EFFECT_PLAYED,
    STAT_CHANGED,
    USERNAME_CHANGED,
    VARCLIENT_INT_CHANGED,
    VARCLIENT_STR_CHANGED,
    VARBIT_CHANGED,
    VOLUME_CHANGED,
    WALL_OBJECT_CHANGED,
    WALL_OBJECT_DESPAWNED,
    WALL_OBJECT_SPAWNED,
    WIDGET_MENU_OPTION_CLICKED,
    WIDGET_HIDDEN_CHANGED,
    WIDGET_LOADED,
    WIDGET_POSITIONED,
    WIDGET_PRESSED,
    WIDGET_CLOSED,
    WORLD_CHANGED,
    WORLD_LIST_LOAD,

    ANIMATION_FRAME_INDEX_CHANGED,
    ATTACHED_MODEL_EVENT,
    CAMERA_LOOK_AT_EVENT,
    CAMERA_MOVE_TO_EVENT,
    CAMERA_RESET_EVENT,
    CAMERA_SHAKE_EVENT,
    COMBAT_LEVEL_CHANGE_EVENT,
    CONTAINER_ITEM_CHANGE,
    EXACT_MOVE_EVENT,
    FACED_DIRECTION_CHANGED,
    GET_DYNAMIC_OBJECT_FOR_ANIMATION_EVENT,
    HASH_TABLE_NODE_GET_CALL,
    HASH_TABLE_NODE_PUT,
    HINT_ARROW_EVENT,
    IF_OPEN_SUB_EVENT,
    IF_OPEN_TOP_EVENT,
    JINGLE_PLAYED,
    MINIMAP_STATE_CHANGE,
    NAME_CHANGE_EVENT,
    NPC_MOVED,
    PENDING_SPAWN_UPDATED,
    PLAYER_ANIMATION_PLAYED,
    PLAYER_MOVED,
    POST_WIDGET_CONSTRUCTED,
    REBUILD_REGION_EVENT,
    RECOLOUR_EVENT,
    RUN_ENERGY_CHANGED_EVENT,
    SERVER_PACKET_READ_COMPLETE_EVENT,
    SERVER_PACKET_READ_STARTED_EVENT,
    SHOW_PUBLIC_PLAYER_CHAT_CHANGED,
    SOUND_EFFECT_RECEIVED,
    WIDGET_COLOR_CHANGED,
    WIDGET_MODEL_CHANGED,
    WIDGET_MODEL_ROTATE,
    WIDGET_POSITION_CHANGED,
    WIDGET_SCROLL_HEIGHT_CHANGED,
    WIDGET_SEQUENCE_CHANGED,
    WIDGET_SET_NPC_HEAD,
    WIDGET_SET_OBJECT,
    WIDGET_SET_PLAYER_HEAD,
    WIDGET_TEXT_CHANGED,
    WIDGET_VISIBILITY_CHANGE,
    WIDGET_ZOOM_CHANGED,
    XP_DROP,;
}