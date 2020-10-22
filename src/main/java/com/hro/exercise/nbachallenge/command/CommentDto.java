package com.hro.exercise.nbachallenge.command;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hro.exercise.nbachallenge.persistence.model.Comment;
import com.hro.exercise.nbachallenge.util.AppConstants;
import com.sun.istack.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Comment Dto
 * Assures security/filter when providing comment data to the user
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CommentDto implements Comparable<CommentDto> {

    @JsonProperty("Comment ID")
    private Integer id;

    @NotNull
    @JsonProperty("Comment")
    private String comment;

    @NotNull
    @DateTimeFormat(pattern = AppConstants.DAY_TIME_FORMAT)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = AppConstants.DAY_AND_HOUR_TIME_FORMAT, timezone = "GMT")
    @Temporal(TemporalType.DATE)
    @JsonProperty("Date")
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
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {

        return (o == this) || o instanceof CommentDto && (((CommentDto) o).id) == id;
    }

    @Override
    public int compareTo(CommentDto o) {
        return (getDate() == null || o.getDate() == null) ? 0 : getDate().compareTo(o.getDate());
    }
}
