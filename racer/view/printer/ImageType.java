package com.javarush.games.racer.view.printer;

public enum ImageType {
    SYM_RU_LETTER_A('а', 'a'),
    SYM_RU_LETTER_B('б'),
    SYM_RU_LETTER_V('в', 'b'),
    SYM_RU_LETTER_G('г'),
    SYM_RU_LETTER_D('д'),
    SYM_RU_LETTER_YE('е', 'e'),
    SYM_RU_LETTER_YO('ё'),
    SYM_RU_LETTER_J('ж'),
    SYM_RU_LETTER_Z('з'),
    SYM_RU_LETTER_I('и'),
    SYM_RU_LETTER_IKR('й'),
    SYM_RU_LETTER_K('к', 'k'),
    SYM_RU_LETTER_L('л'),
    SYM_RU_LETTER_M('м', 'm'),
    SYM_RU_LETTER_N('н', 'h'),
    SYM_RU_LETTER_O('о', 'o'),
    SYM_RU_LETTER_P('п'),
    SYM_RU_LETTER_R('р', 'p'),
    SYM_RU_LETTER_S('с', 'c'),
    SYM_RU_LETTER_T('т', 't'),
    SYM_RU_LETTER_U('у'),
    SYM_RU_LETTER_F('ф'),
    SYM_RU_LETTER_H('х'),
    SYM_RU_LETTER_C('ц'),
    SYM_RU_LETTER_CH('ч'),
    SYM_RU_LETTER_SHA('ш'),
    SYM_RU_LETTER_SCHA('щ'),
    SYM_RU_LETTER_SOFT('ь'),
    SYM_RU_LETTER_Y('ы'),
    SYM_RU_LETTER_HARD('ъ'),
    SYM_RU_LETTER_E('э'),
    SYM_RU_LETTER_YU('ю'),
    SYM_RU_LETTER_YA('я'),
    SYM_EN_LETTER_D('d'),
    SYM_EN_LETTER_F('f'),
    SYM_EN_LETTER_G('g'),
    SYM_EN_LETTER_I('i'),
    SYM_EN_LETTER_J('j'),
    SYM_EN_LETTER_L('l'),
    SYM_EN_LETTER_N('n'),
    SYM_EN_LETTER_Q('q'),
    SYM_EN_LETTER_R('r'),
    SYM_EN_LETTER_S('s'),
    SYM_EN_LETTER_U('u'),
    SYM_EN_LETTER_V('v'),
    SYM_EN_LETTER_W('w'),
    SYM_EN_LETTER_X('x'),
    SYM_EN_LETTER_Y('y'),
    SYM_EN_LETTER_Z('z'),
    SYM_DIGIT_0('0'),
    SYM_DIGIT_1('1'),
    SYM_DIGIT_2('2'),
    SYM_DIGIT_3('3'),
    SYM_DIGIT_4('4'),
    SYM_DIGIT_5('5'),
    SYM_DIGIT_6('6'),
    SYM_DIGIT_7('7'),
    SYM_DIGIT_8('8'),
    SYM_DIGIT_9('9'),
    SYM_SYMBOL_COMMA(','),
    SYM_SYMBOL_DOT('.'),
    SYM_SYMBOL_COLON(':'),
    SYM_SYMBOL_EXCLAMATION('!'),
    SYM_SYMBOL_QUESTION('?'),
    SYM_SYMBOL_SPACE(' '),
    SYM_SYMBOL_DASH('-'),
    SYM_SYMBOL_EQUALS('='),
    SYM_SYMBOL_ASTERISK('*'),
    SYM_SYMBOL_SLASH('/'),
    SYM_SYMBOL_SQ_BRACKET_L('['),
    SYM_SYMBOL_SQ_BRACKET_R(']'),
    SYM_SYMBOL_NEWLINE('\n'),
    SYM_SYMBOL_APOSTROPHE('\''),
    SYM_SYMBOL_QUOTE('\"');

    private final char[] characters;

    public char[] getCharacters() {
        return characters;
    }

    ImageType(char... characters) {
        this.characters = characters;
    }
}
