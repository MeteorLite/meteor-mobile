package java.awt.event

import sun.awt.ExtendedKeyCodes


class KeyEvent(val AWTKeyId: Int) {
    companion object {

        fun getExtendedKeyCodeForChar(c: Int): Int {
            // Return a keycode (if any) associated with a character.
            return ExtendedKeyCodes.getExtendedKeyCodeForChar(c)
        }

        val KEY_FIRST = 400

        /**
         * The last number in the range of ids used for key events.
         */
        val KEY_LAST = 402

        /**
         * The "key typed" event.  This event is generated when a character is
         * entered.  In the simplest case, it is produced by a single key press.
         * Often, however, characters are produced by series of key presses, and
         * the mapping from key pressed events to key typed events may be
         * many-to-one or many-to-many.
         */
        val KEY_TYPED = KEY_FIRST

        /**
         * The "key pressed" event. This event is generated when a key
         * is pushed down.
         */
        val KEY_PRESSED = 1 + KEY_FIRST //Event.KEY_PRESS


        /**
         * The "key released" event. This event is generated when a key
         * is let up.
         */
        val KEY_RELEASED = 2 + KEY_FIRST //Event.KEY_RELEASE


        /* Virtual key codes. */

        /* Virtual key codes. */
        /** Constant for the ENTER virtual key.  */
        val VK_ENTER = '\n'.code

        /** Constant for the BACK_SPACE virtual key.  */
        val VK_BACK_SPACE = '\b'.code

        /** Constant for the TAB virtual key.  */
        val VK_TAB = '\t'.code

        /** Constant for the CANCEL virtual key.  */
        val VK_CANCEL = 0x03

        /** Constant for the CLEAR virtual key.  */
        val VK_CLEAR = 0x0C

        /** Constant for the SHIFT virtual key.  */
        val VK_SHIFT = 0x10

        /** Constant for the CONTROL virtual key.  */
        val VK_CONTROL = 0x11

        /** Constant for the ALT virtual key.  */
        val VK_ALT = 0x12

        /** Constant for the PAUSE virtual key.  */
        val VK_PAUSE = 0x13

        /** Constant for the CAPS_LOCK virtual key.  */
        val VK_CAPS_LOCK = 0x14

        /** Constant for the ESCAPE virtual key.  */
        val VK_ESCAPE = 0x1B

        /** Constant for the SPACE virtual key.  */
        val VK_SPACE = 0x20

        /** Constant for the PAGE_UP virtual key.  */
        val VK_PAGE_UP = 0x21

        /** Constant for the PAGE_DOWN virtual key.  */
        val VK_PAGE_DOWN = 0x22

        /** Constant for the END virtual key.  */
        val VK_END = 0x23

        /** Constant for the HOME virtual key.  */
        val VK_HOME = 0x24

        /**
         * Constant for the non-numpad **left** arrow key.
         * @see .VK_KP_LEFT
         */
        val VK_LEFT = 0x25

        /**
         * Constant for the non-numpad **up** arrow key.
         * @see .VK_KP_UP
         */
        val VK_UP = 0x26

        /**
         * Constant for the non-numpad **right** arrow key.
         * @see .VK_KP_RIGHT
         */
        val VK_RIGHT = 0x27

        /**
         * Constant for the non-numpad **down** arrow key.
         * @see .VK_KP_DOWN
         */
        val VK_DOWN = 0x28

        /**
         * Constant for the comma key, ","
         */
        val VK_COMMA = 0x2C

        /**
         * Constant for the minus key, "-"
         * @since 1.2
         */
        val VK_MINUS = 0x2D

        /**
         * Constant for the period key, "."
         */
        val VK_PERIOD = 0x2E

        /**
         * Constant for the forward slash key, "/"
         */
        val VK_SLASH = 0x2F

        /** VK_0 thru VK_9 are the same as ASCII '0' thru '9' (0x30 - 0x39) */

        /** VK_0 thru VK_9 are the same as ASCII '0' thru '9' (0x30 - 0x39)  */
        /** Constant for the "0" key.  */
        val VK_0 = 0x30

        /** Constant for the "1" key.  */
        val VK_1 = 0x31

        /** Constant for the "2" key.  */
        val VK_2 = 0x32

        /** Constant for the "3" key.  */
        val VK_3 = 0x33

        /** Constant for the "4" key.  */
        val VK_4 = 0x34

        /** Constant for the "5" key.  */
        val VK_5 = 0x35

        /** Constant for the "6" key.  */
        val VK_6 = 0x36

        /** Constant for the "7" key.  */
        val VK_7 = 0x37

        /** Constant for the "8" key.  */
        val VK_8 = 0x38

        /** Constant for the "9" key.  */
        val VK_9 = 0x39

        /**
         * Constant for the semicolon key, ";"
         */
        val VK_SEMICOLON = 0x3B

        /**
         * Constant for the equals key, "="
         */
        val VK_EQUALS = 0x3D

        /** VK_A thru VK_Z are the same as ASCII 'A' thru 'Z' (0x41 - 0x5A) */

        /** VK_A thru VK_Z are the same as ASCII 'A' thru 'Z' (0x41 - 0x5A)  */
        /** Constant for the "A" key.  */
        val VK_A = 0x41

        /** Constant for the "B" key.  */
        val VK_B = 0x42

        /** Constant for the "C" key.  */
        val VK_C = 0x43

        /** Constant for the "D" key.  */
        val VK_D = 0x44

        /** Constant for the "E" key.  */
        val VK_E = 0x45

        /** Constant for the "F" key.  */
        val VK_F = 0x46

        /** Constant for the "G" key.  */
        val VK_G = 0x47

        /** Constant for the "H" key.  */
        val VK_H = 0x48

        /** Constant for the "I" key.  */
        val VK_I = 0x49

        /** Constant for the "J" key.  */
        val VK_J = 0x4A

        /** Constant for the "K" key.  */
        val VK_K = 0x4B

        /** Constant for the "L" key.  */
        val VK_L = 0x4C

        /** Constant for the "M" key.  */
        val VK_M = 0x4D

        /** Constant for the "N" key.  */
        val VK_N = 0x4E

        /** Constant for the "O" key.  */
        val VK_O = 0x4F

        /** Constant for the "P" key.  */
        val VK_P = 0x50

        /** Constant for the "Q" key.  */
        val VK_Q = 0x51

        /** Constant for the "R" key.  */
        val VK_R = 0x52

        /** Constant for the "S" key.  */
        val VK_S = 0x53

        /** Constant for the "T" key.  */
        val VK_T = 0x54

        /** Constant for the "U" key.  */
        val VK_U = 0x55

        /** Constant for the "V" key.  */
        val VK_V = 0x56

        /** Constant for the "W" key.  */
        val VK_W = 0x57

        /** Constant for the "X" key.  */
        val VK_X = 0x58

        /** Constant for the "Y" key.  */
        val VK_Y = 0x59

        /** Constant for the "Z" key.  */
        val VK_Z = 0x5A

        /**
         * Constant for the open bracket key, "["
         */
        val VK_OPEN_BRACKET = 0x5B

        /**
         * Constant for the back slash key, "\"
         */
        val VK_BACK_SLASH = 0x5C

        /**
         * Constant for the close bracket key, "]"
         */
        val VK_CLOSE_BRACKET = 0x5D

        /** Constant for the number pad "0" key.  */
        val VK_NUMPAD0 = 0x60

        /** Constant for the number pad "1" key.  */
        val VK_NUMPAD1 = 0x61

        /** Constant for the number pad "2" key.  */
        val VK_NUMPAD2 = 0x62

        /** Constant for the number pad "3" key.  */
        val VK_NUMPAD3 = 0x63

        /** Constant for the number pad "4" key.  */
        val VK_NUMPAD4 = 0x64

        /** Constant for the number pad "5" key.  */
        val VK_NUMPAD5 = 0x65

        /** Constant for the number pad "6" key.  */
        val VK_NUMPAD6 = 0x66

        /** Constant for the number pad "7" key.  */
        val VK_NUMPAD7 = 0x67

        /** Constant for the number pad "8" key.  */
        val VK_NUMPAD8 = 0x68

        /** Constant for the number pad "9" key.  */
        val VK_NUMPAD9 = 0x69

        /** Constant for the number pad multiply key.  */
        val VK_MULTIPLY = 0x6A

        /** Constant for the number pad add key.  */
        val VK_ADD = 0x6B

        /**
         * This constant is obsolete, and is included only for backwards
         * compatibility.
         * @see .VK_SEPARATOR
         */
        val VK_SEPARATER = 0x6C

        /**
         * Constant for the Numpad Separator key.
         * @since 1.4
         */
        val VK_SEPARATOR = VK_SEPARATER

        /** Constant for the number pad subtract key.  */
        val VK_SUBTRACT = 0x6D

        /** Constant for the number pad decimal point key.  */
        val VK_DECIMAL = 0x6E

        /** Constant for the number pad divide key.  */
        val VK_DIVIDE = 0x6F

        /** Constant for the delete key.  */
        val VK_DELETE = 0x7F /* ASCII DEL */

        /** Constant for the NUM_LOCK key.  */
        val VK_NUM_LOCK = 0x90

        /** Constant for the SCROLL_LOCK key.  */
        val VK_SCROLL_LOCK = 0x91

        /** Constant for the F1 function key.  */
        val VK_F1 = 0x70

        /** Constant for the F2 function key.  */
        val VK_F2 = 0x71

        /** Constant for the F3 function key.  */
        val VK_F3 = 0x72

        /** Constant for the F4 function key.  */
        val VK_F4 = 0x73

        /** Constant for the F5 function key.  */
        val VK_F5 = 0x74

        /** Constant for the F6 function key.  */
        val VK_F6 = 0x75

        /** Constant for the F7 function key.  */
        val VK_F7 = 0x76

        /** Constant for the F8 function key.  */
        val VK_F8 = 0x77

        /** Constant for the F9 function key.  */
        val VK_F9 = 0x78

        /** Constant for the F10 function key.  */
        val VK_F10 = 0x79

        /** Constant for the F11 function key.  */
        val VK_F11 = 0x7A

        /** Constant for the F12 function key.  */
        val VK_F12 = 0x7B

        /**
         * Constant for the F13 function key.
         * @since 1.2
         */
        /* F13 - F24 are used on IBM 3270 keyboard; use random range for constants. */
        val VK_F13 = 0xF000

        /**
         * Constant for the F14 function key.
         * @since 1.2
         */
        val VK_F14 = 0xF001

        /**
         * Constant for the F15 function key.
         * @since 1.2
         */
        val VK_F15 = 0xF002

        /**
         * Constant for the F16 function key.
         * @since 1.2
         */
        val VK_F16 = 0xF003

        /**
         * Constant for the F17 function key.
         * @since 1.2
         */
        val VK_F17 = 0xF004

        /**
         * Constant for the F18 function key.
         * @since 1.2
         */
        val VK_F18 = 0xF005

        /**
         * Constant for the F19 function key.
         * @since 1.2
         */
        val VK_F19 = 0xF006

        /**
         * Constant for the F20 function key.
         * @since 1.2
         */
        val VK_F20 = 0xF007

        /**
         * Constant for the F21 function key.
         * @since 1.2
         */
        val VK_F21 = 0xF008

        /**
         * Constant for the F22 function key.
         * @since 1.2
         */
        val VK_F22 = 0xF009

        /**
         * Constant for the F23 function key.
         * @since 1.2
         */
        val VK_F23 = 0xF00A

        /**
         * Constant for the F24 function key.
         * @since 1.2
         */
        val VK_F24 = 0xF00B

        /**  Constant for the PRINTSCREEN key.  */
        val VK_PRINTSCREEN = 0x9A

        /**  Constant for the INSERT key.  */
        val VK_INSERT = 0x9B

        /**  Constant for the HELP key.  */
        val VK_HELP = 0x9C

        /**  Constant for the META key.  */
        val VK_META = 0x9D

        /**  Constant for the BACK_QUOTE  key.  */
        val VK_BACK_QUOTE = 0xC0

        /**  Constant for the QUOTE key.  */
        val VK_QUOTE = 0xDE

        /**
         * Constant for the numeric keypad **up** arrow key.
         * @see .VK_UP
         *
         * @since 1.2
         */
        val VK_KP_UP = 0xE0

        /**
         * Constant for the numeric keypad **down** arrow key.
         * @see .VK_DOWN
         *
         * @since 1.2
         */
        val VK_KP_DOWN = 0xE1

        /**
         * Constant for the numeric keypad **left** arrow key.
         * @see .VK_LEFT
         *
         * @since 1.2
         */
        val VK_KP_LEFT = 0xE2

        /**
         * Constant for the numeric keypad **right** arrow key.
         * @see .VK_RIGHT
         *
         * @since 1.2
         */
        val VK_KP_RIGHT = 0xE3

        /* For European keyboards */
        /* For European keyboards */
        /** @since 1.2
         */
        val VK_DEAD_GRAVE = 0x80

        /** @since 1.2
         */
        val VK_DEAD_ACUTE = 0x81

        /** @since 1.2
         */
        val VK_DEAD_CIRCUMFLEX = 0x82

        /** @since 1.2
         */
        val VK_DEAD_TILDE = 0x83

        /** @since 1.2
         */
        val VK_DEAD_MACRON = 0x84

        /** @since 1.2
         */
        val VK_DEAD_BREVE = 0x85

        /** @since 1.2
         */
        val VK_DEAD_ABOVEDOT = 0x86

        /** @since 1.2
         */
        val VK_DEAD_DIAERESIS = 0x87

        /** @since 1.2
         */
        val VK_DEAD_ABOVERING = 0x88

        /** @since 1.2
         */
        val VK_DEAD_DOUBLEACUTE = 0x89

        /** @since 1.2
         */
        val VK_DEAD_CARON = 0x8a

        /** @since 1.2
         */
        val VK_DEAD_CEDILLA = 0x8b

        /** @since 1.2
         */
        val VK_DEAD_OGONEK = 0x8c

        /** @since 1.2
         */
        val VK_DEAD_IOTA = 0x8d

        /** @since 1.2
         */
        val VK_DEAD_VOICED_SOUND = 0x8e

        /** @since 1.2
         */
        val VK_DEAD_SEMIVOICED_SOUND = 0x8f

        /** @since 1.2
         */
        val VK_AMPERSAND = 0x96

        /** @since 1.2
         */
        val VK_ASTERISK = 0x97

        /** @since 1.2
         */
        val VK_QUOTEDBL = 0x98

        /** @since 1.2
         */
        val VK_LESS = 0x99

        /** @since 1.2
         */
        val VK_GREATER = 0xa0

        /** @since 1.2
         */
        val VK_BRACELEFT = 0xa1

        /** @since 1.2
         */
        val VK_BRACERIGHT = 0xa2

        /**
         * Constant for the "@" key.
         * @since 1.2
         */
        val VK_AT = 0x0200

        /**
         * Constant for the ":" key.
         * @since 1.2
         */
        val VK_COLON = 0x0201

        /**
         * Constant for the "^" key.
         * @since 1.2
         */
        val VK_CIRCUMFLEX = 0x0202

        /**
         * Constant for the "$" key.
         * @since 1.2
         */
        val VK_DOLLAR = 0x0203

        /**
         * Constant for the Euro currency sign key.
         * @since 1.2
         */
        val VK_EURO_SIGN = 0x0204

        /**
         * Constant for the "!" key.
         * @since 1.2
         */
        val VK_EXCLAMATION_MARK = 0x0205

        /**
         * Constant for the inverted exclamation mark key.
         * @since 1.2
         */
        val VK_INVERTED_EXCLAMATION_MARK = 0x0206

        /**
         * Constant for the "(" key.
         * @since 1.2
         */
        val VK_LEFT_PARENTHESIS = 0x0207

        /**
         * Constant for the "#" key.
         * @since 1.2
         */
        val VK_NUMBER_SIGN = 0x0208

        /**
         * Constant for the "+" key.
         * @since 1.2
         */
        val VK_PLUS = 0x0209

        /**
         * Constant for the ")" key.
         * @since 1.2
         */
        val VK_RIGHT_PARENTHESIS = 0x020A

        /**
         * Constant for the "_" key.
         * @since 1.2
         */
        val VK_UNDERSCORE = 0x020B

        /**
         * Constant for the Microsoft Windows "Windows" key.
         * It is used for both the left and right version of the key.
         * @see .getKeyLocation
         * @since 1.5
         */
        val VK_WINDOWS = 0x020C

        /**
         * Constant for the Microsoft Windows Context Menu key.
         * @since 1.5
         */
        val VK_CONTEXT_MENU = 0x020D

        /* for input method support on Asian Keyboards */

        /* not clear what this means - listed in Microsoft Windows API */
        /* for input method support on Asian Keyboards */ /* not clear what this means - listed in Microsoft Windows API */
        /** Constant for the FINAL key.  */
        val VK_FINAL = 0x0018

        /** Constant for the Convert function key.  */ /* Japanese PC 106 keyboard, Japanese Solaris keyboard: henkan */
        val VK_CONVERT = 0x001C

        /** Constant for the Don't Convert function key.  */ /* Japanese PC 106 keyboard: muhenkan */
        val VK_NONCONVERT = 0x001D

        /** Constant for the Accept or Commit function key.  */ /* Japanese Solaris keyboard: kakutei */
        val VK_ACCEPT = 0x001E

        /* not clear what this means - listed in Microsoft Windows API */
        /* not clear what this means - listed in Microsoft Windows API */
        /** Constant for the MODECHANGE key.  */
        val VK_MODECHANGE = 0x001F

        /* replaced by VK_KANA_LOCK for Microsoft Windows and Solaris;
       might still be used on other platforms */
        /* replaced by VK_KANA_LOCK for Microsoft Windows and Solaris;
       might still be used on other platforms */
        /**
         * Constant for the KANA lock key.
         * @see .VK_KANA_LOCK
         */
        val VK_KANA = 0x0015

        /* replaced by VK_INPUT_METHOD_ON_OFF for Microsoft Windows and Solaris;
       might still be used for other platforms */
        /* replaced by VK_INPUT_METHOD_ON_OFF for Microsoft Windows and Solaris;
       might still be used for other platforms */
        /**
         * Constant for KANJI.
         * @see .VK_INPUT_METHOD_ON_OFF
         */
        val VK_KANJI = 0x0019

        /**
         * Constant for the Alphanumeric function key.
         * @since 1.2
         */
        /* Japanese PC 106 keyboard: eisuu */
        val VK_ALPHANUMERIC = 0x00F0

        /**
         * Constant for the Katakana function key.
         * @since 1.2
         */
        /* Japanese PC 106 keyboard: katakana */
        val VK_KATAKANA = 0x00F1

        /**
         * Constant for the Hiragana function key.
         * @since 1.2
         */
        /* Japanese PC 106 keyboard: hiragana */
        val VK_HIRAGANA = 0x00F2

        /**
         * Constant for the Full-Width Characters function key.
         * @since 1.2
         */
        /* Japanese PC 106 keyboard: zenkaku */
        val VK_FULL_WIDTH = 0x00F3

        /**
         * Constant for the Half-Width Characters function key.
         * @since 1.2
         */
        /* Japanese PC 106 keyboard: hankaku */
        val VK_HALF_WIDTH = 0x00F4

        /**
         * Constant for the Roman Characters function key.
         * @since 1.2
         */
        /* Japanese PC 106 keyboard: roumaji */
        val VK_ROMAN_CHARACTERS = 0x00F5

        /**
         * Constant for the All Candidates function key.
         * @since 1.2
         */
        /* Japanese PC 106 keyboard - VK_CONVERT + ALT: zenkouho */
        val VK_ALL_CANDIDATES = 0x0100

        /**
         * Constant for the Previous Candidate function key.
         * @since 1.2
         */
        /* Japanese PC 106 keyboard - VK_CONVERT + SHIFT: maekouho */
        val VK_PREVIOUS_CANDIDATE = 0x0101

        /**
         * Constant for the Code Input function key.
         * @since 1.2
         */
        /* Japanese PC 106 keyboard - VK_ALPHANUMERIC + ALT: kanji bangou */
        val VK_CODE_INPUT = 0x0102

        /**
         * Constant for the Japanese-Katakana function key.
         * This key switches to a Japanese input method and selects its Katakana input mode.
         * @since 1.2
         */
        /* Japanese Macintosh keyboard - VK_JAPANESE_HIRAGANA + SHIFT */
        val VK_JAPANESE_KATAKANA = 0x0103

        /**
         * Constant for the Japanese-Hiragana function key.
         * This key switches to a Japanese input method and selects its Hiragana input mode.
         * @since 1.2
         */
        /* Japanese Macintosh keyboard */
        val VK_JAPANESE_HIRAGANA = 0x0104

        /**
         * Constant for the Japanese-Roman function key.
         * This key switches to a Japanese input method and selects its Roman-Direct input mode.
         * @since 1.2
         */
        /* Japanese Macintosh keyboard */
        val VK_JAPANESE_ROMAN = 0x0105

        /**
         * Constant for the locking Kana function key.
         * This key locks the keyboard into a Kana layout.
         * @since 1.3
         */
        /* Japanese PC 106 keyboard with special Windows driver - eisuu + Control; Japanese Solaris keyboard: kana */
        val VK_KANA_LOCK = 0x0106

        /**
         * Constant for the input method on/off key.
         * @since 1.3
         */
        /* Japanese PC 106 keyboard: kanji. Japanese Solaris keyboard: nihongo */
        val VK_INPUT_METHOD_ON_OFF = 0x0107

        /* for Sun keyboards */
        /* for Sun keyboards */
        /** @since 1.2
         */
        val VK_CUT = 0xFFD1

        /** @since 1.2
         */
        val VK_COPY = 0xFFCD

        /** @since 1.2
         */
        val VK_PASTE = 0xFFCF

        /** @since 1.2
         */
        val VK_UNDO = 0xFFCB

        /** @since 1.2
         */
        val VK_AGAIN = 0xFFC9

        /** @since 1.2
         */
        val VK_FIND = 0xFFD0

        /** @since 1.2
         */
        val VK_PROPS = 0xFFCA

        /** @since 1.2
         */
        val VK_STOP = 0xFFC8

        /**
         * Constant for the Compose function key.
         * @since 1.2
         */
        val VK_COMPOSE = 0xFF20

        /**
         * Constant for the AltGraph function key.
         * @since 1.2
         */
        val VK_ALT_GRAPH = 0xFF7E

        /**
         * Constant for the Begin key.
         * @since 1.5
         */
        val VK_BEGIN = 0xFF58

        /**
         * This value is used to indicate that the keyCode is unknown.
         * KEY_TYPED events do not have a keyCode value; this value
         * is used instead.
         */
        val VK_UNDEFINED = 0x0

        /**
         * KEY_PRESSED and KEY_RELEASED events which do not map to a
         * valid Unicode character use this for the keyChar value.
         */
        val CHAR_UNDEFINED = 0xFFFF.toChar()

        /**
         * A constant indicating that the keyLocation is indeterminate
         * or not relevant.
         * `KEY_TYPED` events do not have a keyLocation; this value
         * is used instead.
         * @since 1.4
         */
        val KEY_LOCATION_UNKNOWN = 0

        /**
         * A constant indicating that the key pressed or released
         * is not distinguished as the left or right version of a key,
         * and did not originate on the numeric keypad (or did not
         * originate with a virtual key corresponding to the numeric
         * keypad).
         * @since 1.4
         */
        val KEY_LOCATION_STANDARD = 1

        /**
         * A constant indicating that the key pressed or released is in
         * the left key location (there is more than one possible location
         * for this key).  Example: the left shift key.
         * @since 1.4
         */
        val KEY_LOCATION_LEFT = 2

        /**
         * A constant indicating that the key pressed or released is in
         * the right key location (there is more than one possible location
         * for this key).  Example: the right shift key.
         * @since 1.4
         */
        val KEY_LOCATION_RIGHT = 3

        /**
         * A constant indicating that the key event originated on the
         * numeric keypad or with a virtual key corresponding to the
         * numeric keypad.
         * @since 1.4
         */
        val KEY_LOCATION_NUMPAD = 4
    }
    var isConsumed = false

    fun consume() {

    }
}
