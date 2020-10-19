package com.hro.exercise.nbachallenge.command;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.istack.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CommentDto {

    private Integer id;

    @NotNull
    private String comment;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date date;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "CommentDto{" +
                "comment='" + comment + '\'' +
                ", id=" + id +
                ", date=" + date +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (o == this) {return true;}
        if (o instanceof CommentDto &&  (((CommentDto) o).id) == this.id) {
            return true;
        }
        return false;
    }
}
