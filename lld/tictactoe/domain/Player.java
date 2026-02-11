package lld.tictactoe.domain;

import lld.tictactoe.domain.enums.Mark;

public class Player {
    public final String id;
    public Mark mark;

    public Player(String id, Mark mark) {
        this.id = id;
        this.mark = mark;
    }
}
