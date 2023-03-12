package net.runelite.rs;

import java.util.HashMap;

public class ObfuscatedClassMap extends HashMap<String, String> {
    public static ObfuscatedClassMap INSTANCE = new ObfuscatedClassMap();
    static {
		INSTANCE.put("com/jagex/oldscape/pub/RefreshAccessTokenRequester", "com/jagex/oldscape/pub/RefreshAccessTokenRequester");
		INSTANCE.put("com/jagex/oldscape/pub/RefreshAccessTokenResponse", "com/jagex/oldscape/pub/RefreshAccessTokenResponse");
		INSTANCE.put("net/runelite/rs/Opcodes", "net/runelite/rs/Opcodes");
		INSTANCE.put("osrs/AABB", "ie");
		INSTANCE.put("osrs/AbstractArchive", "ne");
		INSTANCE.put("osrs/AbstractByteArrayCopier", "mh");
		INSTANCE.put("osrs/AbstractFont", "ou");
		INSTANCE.put("osrs/AbstractRasterProvider", "sm");
		INSTANCE.put("osrs/AbstractSocket", "pj");
		INSTANCE.put("osrs/AbstractSound", "ce");
		INSTANCE.put("osrs/AbstractUserComparator", "pl");
		INSTANCE.put("osrs/AbstractWorldMapData", "js");
		INSTANCE.put("osrs/AbstractWorldMapIcon", "ku");
		INSTANCE.put("osrs/AccessFile", "re");
		INSTANCE.put("osrs/Actor", "dq");
		INSTANCE.put("osrs/Animation", "ig");
		INSTANCE.put("osrs/ApproximateRouteStrategy", "cy");
		INSTANCE.put("osrs/Archive", "mx");
		INSTANCE.put("osrs/ArchiveDisk", "pv");
		INSTANCE.put("osrs/ArchiveDiskAction", "mk");
		INSTANCE.put("osrs/ArchiveDiskActionHandler", "mp");
		INSTANCE.put("osrs/ArchiveLoader", "dp");
		INSTANCE.put("osrs/AttackOption", "dy");
		INSTANCE.put("osrs/AudioFilter", "cj");
		INSTANCE.put("osrs/BoundaryObject", "jc");
		INSTANCE.put("osrs/Bounds", "qy");
		INSTANCE.put("osrs/Buddy", "pt");
		INSTANCE.put("osrs/BuddyRankComparator", "ea");
		INSTANCE.put("osrs/Buffer", "sy");
		INSTANCE.put("osrs/BufferedFile", "rq");
		INSTANCE.put("osrs/BufferedNetSocket", "pr");
		INSTANCE.put("osrs/BufferedSink", "pg");
		INSTANCE.put("osrs/BufferedSource", "px");
		INSTANCE.put("osrs/ByteArrayNode", "qc");
		INSTANCE.put("osrs/ByteArrayPool", "ot");
		INSTANCE.put("osrs/BZip2Decompressor", "sw");
		INSTANCE.put("osrs/BZip2State", "ss");
		INSTANCE.put("osrs/Calendar", "mv");
		INSTANCE.put("osrs/Canvas", "aa");
		INSTANCE.put("osrs/ChatChannel", "du");
		INSTANCE.put("osrs/ClanChannel", "gn");
		INSTANCE.put("osrs/ClanChannelMember", "fy");
		INSTANCE.put("osrs/ClanMate", "po");
		INSTANCE.put("osrs/ClanSettings", "fi");
		INSTANCE.put("osrs/class0", "aj");
		INSTANCE.put("osrs/class1", "al");
		INSTANCE.put("osrs/class10", "ak");
		INSTANCE.put("osrs/class102", "dn");
		INSTANCE.put("osrs/class103", "di");
		INSTANCE.put("osrs/class105", "ei");
		INSTANCE.put("osrs/class11", "ax");
		INSTANCE.put("osrs/class110", "er");
		INSTANCE.put("osrs/class12", "as");
		INSTANCE.put("osrs/class121", "ey");
		INSTANCE.put("osrs/class122", "eq");
		INSTANCE.put("osrs/class123", "eh");
		INSTANCE.put("osrs/class124", "ek");
		INSTANCE.put("osrs/class125", "ej");
		INSTANCE.put("osrs/class126", "ez");
		INSTANCE.put("osrs/class127", "ew");
		INSTANCE.put("osrs/class128", "eu");
		INSTANCE.put("osrs/class129", "ed");
		INSTANCE.put("osrs/class13", "ay");
		INSTANCE.put("osrs/class130", "fz");
		INSTANCE.put("osrs/class131", "fr");
		INSTANCE.put("osrs/class132", "fs");
		INSTANCE.put("osrs/class133", "fe");
		INSTANCE.put("osrs/class134", "fl");
		INSTANCE.put("osrs/class135", "fa");
		INSTANCE.put("osrs/class136", "ft");
		INSTANCE.put("osrs/class137", "ff");
		INSTANCE.put("osrs/class138", "fb");
		INSTANCE.put("osrs/class139", "fg");
		INSTANCE.put("osrs/class14", "am");
		INSTANCE.put("osrs/class140", "fj");
		INSTANCE.put("osrs/class141", "fv");
		INSTANCE.put("osrs/class143", "fo");
		INSTANCE.put("osrs/class144", "fk");
		INSTANCE.put("osrs/class145", "fw");
		INSTANCE.put("osrs/class146", "fn");
		INSTANCE.put("osrs/class147", "fc");
		INSTANCE.put("osrs/class148", "fm");
		INSTANCE.put("osrs/class149", "fq");
		INSTANCE.put("osrs/class15", "az");
		INSTANCE.put("osrs/class150", "fh");
		INSTANCE.put("osrs/class152", "fu");
		INSTANCE.put("osrs/class153", "fd");
		INSTANCE.put("osrs/class154", "fp");
		INSTANCE.put("osrs/class155", "fx");
		INSTANCE.put("osrs/class156", "gm");
		INSTANCE.put("osrs/class157", "gk");
		INSTANCE.put("osrs/class158", "gb");
		INSTANCE.put("osrs/class159", "go");
		INSTANCE.put("osrs/class16", "ae");
		INSTANCE.put("osrs/class161", "ga");
		INSTANCE.put("osrs/class162", "gz");
		INSTANCE.put("osrs/class163", "gu");
		INSTANCE.put("osrs/class164", "gw");
		INSTANCE.put("osrs/class166", "gr");
		INSTANCE.put("osrs/class167", "gs");
		INSTANCE.put("osrs/class169", "gl");
		INSTANCE.put("osrs/class17", "au");
		INSTANCE.put("osrs/class171", "gc");
		INSTANCE.put("osrs/class173", "gx");
		INSTANCE.put("osrs/class174", "gg");
		INSTANCE.put("osrs/class175", "gd");
		INSTANCE.put("osrs/class178", "gp");
		INSTANCE.put("osrs/class18", "ag");
		INSTANCE.put("osrs/class181", "gt");
		INSTANCE.put("osrs/class188", "hs");
		INSTANCE.put("osrs/class19", "at");
		INSTANCE.put("osrs/class196", "hw");
		INSTANCE.put("osrs/class199", "hy");
		INSTANCE.put("osrs/class2", "ac");
		INSTANCE.put("osrs/class20", "af");
		INSTANCE.put("osrs/class206", "hi");
		INSTANCE.put("osrs/class207", "ht");
		INSTANCE.put("osrs/class208", "ic");
		INSTANCE.put("osrs/class212", "im");
		INSTANCE.put("osrs/class220", "ik");
		INSTANCE.put("osrs/class240", "jr");
		INSTANCE.put("osrs/class248", "jq");
		INSTANCE.put("osrs/class249", "jh");
		INSTANCE.put("osrs/class258", "jj");
		INSTANCE.put("osrs/class26", "bm");
		INSTANCE.put("osrs/class260", "kk");
		INSTANCE.put("osrs/class268", "kf");
		INSTANCE.put("osrs/class269", "kw");
		INSTANCE.put("osrs/class27", "bv");
		INSTANCE.put("osrs/class270", "kj");
		INSTANCE.put("osrs/class271", "kr");
		INSTANCE.put("osrs/class272", "kc");
		INSTANCE.put("osrs/class278", "ks");
		INSTANCE.put("osrs/class279", "kq");
		INSTANCE.put("osrs/class28", "bo");
		INSTANCE.put("osrs/class281", "kl");
		INSTANCE.put("osrs/class287", "lk");
		INSTANCE.put("osrs/class288", "lv");
		INSTANCE.put("osrs/class289", "lg");
		INSTANCE.put("osrs/class29", "bs");
		INSTANCE.put("osrs/class290", "lr");
		INSTANCE.put("osrs/class291", "lq");
		INSTANCE.put("osrs/class293", "lf");
		INSTANCE.put("osrs/class3", "ab");
		INSTANCE.put("osrs/class30", "bg");
		INSTANCE.put("osrs/class304", "ll");
		INSTANCE.put("osrs/class305", "le");
		INSTANCE.put("osrs/class306", "lu");
		INSTANCE.put("osrs/class307", "ls");
		INSTANCE.put("osrs/class308", "la");
		INSTANCE.put("osrs/class31", "bh");
		INSTANCE.put("osrs/class310", "lo");
		INSTANCE.put("osrs/class311", "ly");
		INSTANCE.put("osrs/class313", "ma");
		INSTANCE.put("osrs/class315", "mw");
		INSTANCE.put("osrs/class319", "mi");
		INSTANCE.put("osrs/class320", "md");
		INSTANCE.put("osrs/class323", "mb");
		INSTANCE.put("osrs/class324", "mz");
		INSTANCE.put("osrs/class325", "mj");
		INSTANCE.put("osrs/class326", "mr");
		INSTANCE.put("osrs/class329", "mc");
		INSTANCE.put("osrs/class33", "bk");
		INSTANCE.put("osrs/class331", "mq");
		INSTANCE.put("osrs/class333", "mu");
		INSTANCE.put("osrs/class345", "na");
		INSTANCE.put("osrs/class350", "nb");
		INSTANCE.put("osrs/class351", "nu");
		INSTANCE.put("osrs/class352", "nt");
		INSTANCE.put("osrs/class353", "np");
		INSTANCE.put("osrs/class36", "bz");
		INSTANCE.put("osrs/class364", "om");
		INSTANCE.put("osrs/class365", "oa");
		INSTANCE.put("osrs/class366", "ok");
		INSTANCE.put("osrs/class368", "ov");
		INSTANCE.put("osrs/class369", "og");
		INSTANCE.put("osrs/class370", "oh");
		INSTANCE.put("osrs/class371", "ob");
		INSTANCE.put("osrs/class372", "oi");
		INSTANCE.put("osrs/class373", "op");
		INSTANCE.put("osrs/class375", "ow");
		INSTANCE.put("osrs/class376", "oj");
		INSTANCE.put("osrs/class377", "od");
		INSTANCE.put("osrs/class379", "on");
		INSTANCE.put("osrs/class380", "oz");
		INSTANCE.put("osrs/class381", "oc");
		INSTANCE.put("osrs/class383", "or");
		INSTANCE.put("osrs/class385", "ox");
		INSTANCE.put("osrs/class388", "oq");
		INSTANCE.put("osrs/class389", "oy");
		INSTANCE.put("osrs/class390", "pu");
		INSTANCE.put("osrs/class391", "pi");
		INSTANCE.put("osrs/class392", "pm");
		INSTANCE.put("osrs/class393", "ps");
		INSTANCE.put("osrs/class394", "pe");
		INSTANCE.put("osrs/class395", "pn");
		INSTANCE.put("osrs/class4", "an");
		INSTANCE.put("osrs/class418", "qa");
		INSTANCE.put("osrs/class419", "qe");
		INSTANCE.put("osrs/class420", "qh");
		INSTANCE.put("osrs/class421", "qt");
		INSTANCE.put("osrs/class422", "qx");
		INSTANCE.put("osrs/class423", "qo");
		INSTANCE.put("osrs/class424", "qs");
		INSTANCE.put("osrs/class425", "qv");
		INSTANCE.put("osrs/class426", "ql");
		INSTANCE.put("osrs/class427", "qf");
		INSTANCE.put("osrs/class428", "qd");
		INSTANCE.put("osrs/class429", "qq");
		INSTANCE.put("osrs/class430", "qw");
		INSTANCE.put("osrs/class431", "qn");
		INSTANCE.put("osrs/class432", "qb");
		INSTANCE.put("osrs/class433", "qg");
		INSTANCE.put("osrs/class434", "qm");
		INSTANCE.put("osrs/class435", "qr");
		INSTANCE.put("osrs/class436", "qz");
		INSTANCE.put("osrs/class451", "rv");
		INSTANCE.put("osrs/class455", "ri");
		INSTANCE.put("osrs/class456", "rn");
		INSTANCE.put("osrs/class458", "rl");
		INSTANCE.put("osrs/class461", "rr");
		INSTANCE.put("osrs/class462", "rw");
		INSTANCE.put("osrs/class463", "rj");
		INSTANCE.put("osrs/class464", "ro");
		INSTANCE.put("osrs/class465", "rg");
		INSTANCE.put("osrs/class466", "rd");
		INSTANCE.put("osrs/class467", "rf");
		INSTANCE.put("osrs/class468", "sd");
		INSTANCE.put("osrs/class471", "st");
		INSTANCE.put("osrs/class473", "sk");
		INSTANCE.put("osrs/class475", "se");
		INSTANCE.put("osrs/class478", "sz");
		INSTANCE.put("osrs/class481", "so");
		INSTANCE.put("osrs/class482", "sg");
		INSTANCE.put("osrs/class485", "sl");
		INSTANCE.put("osrs/class486", "sj");
		INSTANCE.put("osrs/class492", "sa");
		INSTANCE.put("osrs/class495", "tl");
		INSTANCE.put("osrs/class499", "tm");
		INSTANCE.put("osrs/class5", "ao");
		INSTANCE.put("osrs/class50", "bw");
		INSTANCE.put("osrs/class6", "av");
		INSTANCE.put("osrs/class60", "cc");
		INSTANCE.put("osrs/class69", "cx");
		INSTANCE.put("osrs/class7", "aq");
		INSTANCE.put("osrs/class70", "cu");
		INSTANCE.put("osrs/class72", "cw");
		INSTANCE.put("osrs/class8", "ap");
		INSTANCE.put("osrs/class83", "dr");
		INSTANCE.put("osrs/class87", "dd");
		INSTANCE.put("osrs/class88", "dv");
		INSTANCE.put("osrs/class9", "ar");
		INSTANCE.put("osrs/class90", "dj");
		INSTANCE.put("osrs/class98", "dl");
		INSTANCE.put("osrs/class99", "df");
		INSTANCE.put("osrs/Client", "client");
		INSTANCE.put("osrs/ClientPacket", "kb");
		INSTANCE.put("osrs/ClientPreferences", "db");
		INSTANCE.put("osrs/Clock", "gj");
		INSTANCE.put("osrs/CollisionMap", "iz");
		INSTANCE.put("osrs/Coord", "lt");
		INSTANCE.put("osrs/DbRowType", "sh");
		INSTANCE.put("osrs/DbTableType", "sx");
		INSTANCE.put("osrs/Decimator", "co");
		INSTANCE.put("osrs/DefaultsGroup", "pp");
		INSTANCE.put("osrs/DemotingHashTable", "ky");
		INSTANCE.put("osrs/DesktopPlatformInfoProvider", "ra");
		INSTANCE.put("osrs/DevicePcmPlayer", "ah");
		INSTANCE.put("osrs/DevicePcmPlayerProvider", "aw");
		INSTANCE.put("osrs/DirectByteArrayCopier", "mo");
		INSTANCE.put("osrs/DirectWrapper", "ka");
		INSTANCE.put("osrs/DualNode", "qk");
		INSTANCE.put("osrs/DualNodeDeque", "nl");
		INSTANCE.put("osrs/DynamicObject", "cr");
		INSTANCE.put("osrs/EnumComposition", "hg");
		INSTANCE.put("osrs/EvictingDualNodeHashTable", "kt");
		INSTANCE.put("osrs/FaceNormal", "if");
		INSTANCE.put("osrs/FileSystem", "gh");
		INSTANCE.put("osrs/FillMode", "sc");
		INSTANCE.put("osrs/FloorDecoration", "ib");
		INSTANCE.put("osrs/FloorOverlayDefinition", "hz");
		INSTANCE.put("osrs/FloorUnderlayDefinition", "hk");
		INSTANCE.put("osrs/Font", "oe");
		INSTANCE.put("osrs/FontName", "ry");
		INSTANCE.put("osrs/Fonts", "rs");
		INSTANCE.put("osrs/Frames", "iw");
		INSTANCE.put("osrs/Friend", "pk");
		INSTANCE.put("osrs/FriendLoginUpdate", "pz");
		INSTANCE.put("osrs/FriendsChat", "pa");
		INSTANCE.put("osrs/FriendsList", "py");
		INSTANCE.put("osrs/FriendSystem", "cg");
		INSTANCE.put("osrs/GameBuild", "mf");
		INSTANCE.put("osrs/GameEngine", "br");
		INSTANCE.put("osrs/GameObject", "jm");
		INSTANCE.put("osrs/GrandExchangeEvent", "nv");
		INSTANCE.put("osrs/GrandExchangeEvents", "nz");
		INSTANCE.put("osrs/GrandExchangeOffer", "nd");
		INSTANCE.put("osrs/GrandExchangeOfferAgeComparator", "nf");
		INSTANCE.put("osrs/GrandExchangeOfferNameComparator", "nr");
		INSTANCE.put("osrs/GrandExchangeOfferOwnWorldComparator", "ck");
		INSTANCE.put("osrs/GrandExchangeOfferTotalQuantityComparator", "ng");
		INSTANCE.put("osrs/GrandExchangeOfferUnitPriceComparator", "no");
		INSTANCE.put("osrs/GrandExchangeOfferWorldComparator", "nw");
		INSTANCE.put("osrs/GraphicsDefaults", "pw");
		INSTANCE.put("osrs/GraphicsObject", "cq");
		INSTANCE.put("osrs/GZipDecompressor", "te");
		INSTANCE.put("osrs/HealthBar", "dm");
		INSTANCE.put("osrs/HealthBarDefinition", "hn");
		INSTANCE.put("osrs/HealthBarUpdate", "dw");
		INSTANCE.put("osrs/HitSplatDefinition", "hf");
		INSTANCE.put("osrs/HorizontalAlignment", "ho");
		INSTANCE.put("osrs/Huffman", "mg");
		INSTANCE.put("osrs/Ignored", "pc");
		INSTANCE.put("osrs/IgnoreList", "pq");
		INSTANCE.put("osrs/IndexedSprite", "sp");
		INSTANCE.put("osrs/Instrument", "cn");
		INSTANCE.put("osrs/IntegerNode", "rx");
		INSTANCE.put("osrs/InterfaceParent", "de");
		INSTANCE.put("osrs/Interpreter", "cs");
		INSTANCE.put("osrs/IntHashTable", "td");
		INSTANCE.put("osrs/InvDefinition", "hb");
		INSTANCE.put("osrs/IsaacCipher", "tk");
		INSTANCE.put("osrs/ItemComposition", "hh");
		INSTANCE.put("osrs/ItemContainer", "dk");
		INSTANCE.put("osrs/ItemLayer", "ij");
		INSTANCE.put("osrs/IterableDualNodeQueue", "nh");
		INSTANCE.put("osrs/IterableDualNodeQueueIterator", "nn");
		INSTANCE.put("osrs/IterableNodeDeque", "ni");
		INSTANCE.put("osrs/IterableNodeDequeDescendingIterator", "nc");
		INSTANCE.put("osrs/IterableNodeHashTable", "rz");
		INSTANCE.put("osrs/IterableNodeHashTableIterator", "rp");
		INSTANCE.put("osrs/JagexCache", "gi");
		INSTANCE.put("osrs/JSONArray", "org/json/osrs.JSONArray");
		INSTANCE.put("osrs/JSONException", "org/json/osrs.JSONException");
		INSTANCE.put("osrs/JSONObject$Null", "org/json/osrs.JSONObject$Null");
		INSTANCE.put("osrs/JSONObject", "org/json/osrs.JSONObject");
		INSTANCE.put("osrs/JSONString", "org/json/osrs.JSONString");
		INSTANCE.put("osrs/JSONTokener", "org/json/osrs.JSONTokener");
		INSTANCE.put("osrs/KeyHandler", "ad");
		INSTANCE.put("osrs/KitDefinition", "hv");
		INSTANCE.put("osrs/Language", "nq");
		INSTANCE.put("osrs/Link", "qi");
		INSTANCE.put("osrs/LinkDeque", "ny");
		INSTANCE.put("osrs/Login", "cp");
		INSTANCE.put("osrs/LoginPacket", "ln");
		INSTANCE.put("osrs/LoginScreenAnimation", "dt");
		INSTANCE.put("osrs/LoginType", "rm");
		INSTANCE.put("osrs/LongNode", "qj");
		INSTANCE.put("osrs/MenuAction", "cm");
		INSTANCE.put("osrs/Message", "ct");
		INSTANCE.put("osrs/Messages", "ex");
		INSTANCE.put("osrs/MidiFileReader", "lm");
		INSTANCE.put("osrs/MidiPcmStream", "lz");
		INSTANCE.put("osrs/MilliClock", "gq");
		INSTANCE.put("osrs/Model", "ix");
		INSTANCE.put("osrs/ModelData", "iu");
		INSTANCE.put("osrs/ModelData0", "ju");
		INSTANCE.put("osrs/ModeWhere", "nm");
		INSTANCE.put("osrs/MouseHandler", "ba");
		INSTANCE.put("osrs/MouseRecorder", "ds");
		INSTANCE.put("osrs/MouseWheelHandler", "ai");
		INSTANCE.put("osrs/MoveSpeed", "is");
		INSTANCE.put("osrs/MusicPatch", "lb");
		INSTANCE.put("osrs/MusicPatchNode", "lp");
		INSTANCE.put("osrs/MusicPatchNode2", "lj");
		INSTANCE.put("osrs/MusicPatchPcmStream", "ld");
		INSTANCE.put("osrs/MusicTrack", "lh");
		INSTANCE.put("osrs/NanoClock", "gy");
		INSTANCE.put("osrs/NetCache", "nk");
		INSTANCE.put("osrs/NetFileRequest", "ns");
		INSTANCE.put("osrs/NewShit", "hj");
		INSTANCE.put("osrs/Node", "ru");
		INSTANCE.put("osrs/NodeDeque", "nj");
		INSTANCE.put("osrs/NodeHashTable", "rh");
		INSTANCE.put("osrs/NPC", "dz");
		INSTANCE.put("osrs/NPCComposition", "hl");
		INSTANCE.put("osrs/ObjectComposition", "hu");
		INSTANCE.put("osrs/ObjectNode", "qp");
		INSTANCE.put("osrs/ObjectSound", "ch");
		INSTANCE.put("osrs/ObjTypeCustomisation", "gv");
		INSTANCE.put("osrs/Occluder", "io");
		INSTANCE.put("osrs/OtlTokenResponse", "com/jagex/oldscape/pub/osrs.OtlTokenResponse");
		INSTANCE.put("osrs/PacketBuffer", "sq");
		INSTANCE.put("osrs/PacketBufferNode", "kp");
		INSTANCE.put("osrs/PacketWriter", "ep");
		INSTANCE.put("osrs/ParamComposition", "hp");
		INSTANCE.put("osrs/PcmPlayer", "bd");
		INSTANCE.put("osrs/PcmStream", "bj");
		INSTANCE.put("osrs/PcmStreamMixer", "bc");
		INSTANCE.put("osrs/PcmStreamMixerListener", "cb");
		INSTANCE.put("osrs/PendingSpawn", "da");
		INSTANCE.put("osrs/PlatformInfo", "rt");
		INSTANCE.put("osrs/PlatformInfoProvider", "rb");
		INSTANCE.put("osrs/Player", "dh");
		INSTANCE.put("osrs/PlayerComposition", "lc");
		INSTANCE.put("osrs/Players", "en");
		INSTANCE.put("osrs/PlayerType", "mn");
		INSTANCE.put("osrs/PrivateChatMode", "tg");
		INSTANCE.put("osrs/Projectile", "ci");
		INSTANCE.put("osrs/Rasterizer2D", "sf");
		INSTANCE.put("osrs/Rasterizer3D", "ih");
		INSTANCE.put("osrs/RasterProvider", "bl");
		INSTANCE.put("osrs/RawPcmStream", "bn");
		INSTANCE.put("osrs/RawSound", "be");
		INSTANCE.put("osrs/ReflectionCheck", "bq");
		INSTANCE.put("osrs/Renderable", "iq");
		INSTANCE.put("osrs/RouteStrategy", "ip");
		INSTANCE.put("osrs/RunException", "tw");
		INSTANCE.put("osrs/Scene", "ir");
		INSTANCE.put("osrs/SceneTileModel", "il");
		INSTANCE.put("osrs/SceneTilePaint", "it");
		INSTANCE.put("osrs/Script", "cv");
		INSTANCE.put("osrs/ScriptEvent", "dx");
		INSTANCE.put("osrs/ScriptFrame", "cd");
		INSTANCE.put("osrs/SecureRandomCallable", "dc");
		INSTANCE.put("osrs/SecureRandomFuture", "cf");
		INSTANCE.put("osrs/SequenceDefinition", "hx");
		INSTANCE.put("osrs/ServerPacket", "ke");
		INSTANCE.put("osrs/Skeleton", "in");
		INSTANCE.put("osrs/Skills", "mm");
		INSTANCE.put("osrs/SoftWrapper", "ko");
		INSTANCE.put("osrs/SoundCache", "bb");
		INSTANCE.put("osrs/SoundEffect", "bt");
		INSTANCE.put("osrs/SoundEnvelope", "by");
		INSTANCE.put("osrs/SoundSystem", "bp");
		INSTANCE.put("osrs/SpotAnimationDefinition", "ha");
		INSTANCE.put("osrs/SpriteMask", "lx");
		INSTANCE.put("osrs/SpritePixels", "sn");
		INSTANCE.put("osrs/Strings", "me");
		INSTANCE.put("osrs/StructComposition", "hm");
		INSTANCE.put("osrs/StudioGame", "ml");
		INSTANCE.put("osrs/Task", "gf");
		INSTANCE.put("osrs/TaskHandler", "ge");
		INSTANCE.put("osrs/Texture", "ii");
		INSTANCE.put("osrs/TextureLoader", "jp");
		INSTANCE.put("osrs/TextureProvider", "ia");
		INSTANCE.put("osrs/Tile", "id");
		INSTANCE.put("osrs/TileItem", "do");
		INSTANCE.put("osrs/Tiles", "dg");
		INSTANCE.put("osrs/Timer", "os");
		INSTANCE.put("osrs/TriBool", "pf");
		INSTANCE.put("osrs/UrlRequest", "eo");
		INSTANCE.put("osrs/UrlRequester", "eb");
		INSTANCE.put("osrs/User", "pb");
		INSTANCE.put("osrs/UserComparator1", "sb");
		INSTANCE.put("osrs/UserComparator10", "es");
		INSTANCE.put("osrs/UserComparator2", "su");
		INSTANCE.put("osrs/UserComparator3", "ec");
		INSTANCE.put("osrs/UserComparator4", "ev");
		INSTANCE.put("osrs/UserComparator5", "el");
		INSTANCE.put("osrs/UserComparator6", "em");
		INSTANCE.put("osrs/UserComparator7", "et");
		INSTANCE.put("osrs/UserComparator8", "ef");
		INSTANCE.put("osrs/UserComparator9", "eg");
		INSTANCE.put("osrs/UserList", "ph");
		INSTANCE.put("osrs/Username", "tj");
		INSTANCE.put("osrs/Usernamed", "pd");
		INSTANCE.put("osrs/VarbitComposition", "hq");
		INSTANCE.put("osrs/VarcInt", "hr");
		INSTANCE.put("osrs/Varcs", "ee");
		INSTANCE.put("osrs/VarpDefinition", "hc");
		INSTANCE.put("osrs/Varps", "li");
		INSTANCE.put("osrs/VertexNormal", "iy");
		INSTANCE.put("osrs/VerticalAlignment", "he");
		INSTANCE.put("osrs/ViewportMouse", "iv");
		INSTANCE.put("osrs/VorbisCodebook", "bi");
		INSTANCE.put("osrs/VorbisFloor", "bu");
		INSTANCE.put("osrs/VorbisMapping", "bf");
		INSTANCE.put("osrs/VorbisResidue", "bx");
		INSTANCE.put("osrs/VorbisSample", "cl");
		INSTANCE.put("osrs/WallDecoration", "jg");
		INSTANCE.put("osrs/Widget", "my");
		INSTANCE.put("osrs/World", "cz");
		INSTANCE.put("osrs/WorldMap", "rc");
		INSTANCE.put("osrs/WorldMapArchiveLoader", "rk");
		INSTANCE.put("osrs/WorldMapArea", "jw");
		INSTANCE.put("osrs/WorldMapAreaData", "km");
		INSTANCE.put("osrs/WorldMapCacheName", "kg");
		INSTANCE.put("osrs/WorldMapData_0", "jo");
		INSTANCE.put("osrs/WorldMapData_1", "je");
		INSTANCE.put("osrs/WorldMapDecoration", "ji");
		INSTANCE.put("osrs/WorldMapDecorationType", "ms");
		INSTANCE.put("osrs/WorldMapElement", "hd");
		INSTANCE.put("osrs/WorldMapEvent", "kh");
		INSTANCE.put("osrs/WorldMapIcon_0", "jy");
		INSTANCE.put("osrs/WorldMapIcon_1", "jk");
		INSTANCE.put("osrs/WorldMapID", "jx");
		INSTANCE.put("osrs/WorldMapLabel", "jb");
		INSTANCE.put("osrs/WorldMapLabelSize", "jd");
		INSTANCE.put("osrs/WorldMapManager", "jt");
		INSTANCE.put("osrs/WorldMapRectangle", "jl");
		INSTANCE.put("osrs/WorldMapRegion", "ja");
		INSTANCE.put("osrs/WorldMapScaleHandler", "kn");
		INSTANCE.put("osrs/WorldMapSection", "kd");
		INSTANCE.put("osrs/WorldMapSection0", "jz");
		INSTANCE.put("osrs/WorldMapSection1", "kx");
		INSTANCE.put("osrs/WorldMapSection2", "jf");
		INSTANCE.put("osrs/WorldMapSectionType", "jv");
		INSTANCE.put("osrs/WorldMapSprite", "jn");
		INSTANCE.put("osrs/Wrapper", "ki");
		INSTANCE.put("osrs/ZoneOperation", "kz");
    }
}