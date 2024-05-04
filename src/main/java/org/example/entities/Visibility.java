package org.example.entities;

import lombok.Getter;

@Getter
public enum Visibility {
    Private("Only the owner can see it."),
    Limited("Show only the included books without any personal info"),
    Rated("Show included books with their ratings"),
    Public("Show all the informations about the items")
    ;
    private String description;
    Visibility(String s) {
        description = s;
    }

}
