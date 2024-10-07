package com.data.room.domain;

import javax.persistence.*;

@Entity
@SequenceGenerator(
        name = "board_seq_generator",
        sequenceName = "seq_board",
        initialValue = 1,
        allocationSize = 1
)
public class Board {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "board_seq_generator"
    )
    private int board_num;
    private String board_kind;
    private String board_title;
    private String board_content;
    private String board_date;

    public int getBoard_num() {
        return board_num;
    }

    public void setBoard_num(int board_num) {
        this.board_num = board_num;
    }

    public String getBoard_kind() {
        return board_kind;
    }

    public void setBoard_kind(String board_kind) {
        this.board_kind = board_kind;
    }

    public String getBoard_title() {
        return board_title;
    }

    public void setBoard_title(String board_title) {
        this.board_title = board_title;
    }

    public String getBoard_content() {
        return board_content;
    }

    public void setBoard_content(String board_content) {
        this.board_content = board_content;
    }

    public String getBoard_date() {
        return board_date;
    }

    public void setBoard_date(String board_date) {
        this.board_date = board_date;
    }
}
