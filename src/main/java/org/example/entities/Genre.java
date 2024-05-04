package org.example.entities;

import lombok.Getter;

@Getter
public enum Genre {
    FICTION("Fiction", "Works of imagination that are not entirely based on real events, characters, or settings."),
    NON_FICTION("Non-fiction", "Works that are based on facts, real events, and real people, such as biographies, essays, and documentaries."),
    MYSTERY("Mystery", "Works that involve a puzzle, crime, or secret that the protagonist attempts to solve."),
    THRILLER("Thriller", "Works that are fast-paced, suspenseful, and often involve danger, intrigue, and action."),
    ROMANCE("Romance", "Works that focus on romantic relationships, love, and emotional connections between characters."),
    SCIENCE_FICTION("Science fiction (Sci-Fi)", "Works that explore imaginative and futuristic concepts, often involving advanced technology, space exploration, and extraterrestrial life."),
    FANTASY("Fantasy", "Works that involve magical or supernatural elements, often set in imaginary worlds with unique creatures, magic systems, and mythologies."),
    HISTORICAL_FICTION("Historical fiction", "Works that are set in the past and incorporate real historical events, settings, and figures."),
    HORROR("Horror", "Works that are intended to frighten, unsettle, or evoke feelings of fear or dread in the reader."),
    BIOGRAPHY("Biography/Autobiography", "Works that tell the story of a person's life, written by someone else (biography) or by the person themselves (autobiography)."),
    MEMOIR("Memoir", "Works that are based on personal experiences, memories, and reflections of the author."),
    SELF_HELP("Self-help", "Works that provide guidance, advice, and strategies for personal growth, improvement, and self-awareness."),
    BUSINESS("Business/Finance", "Works that focus on topics related to business, economics, finance, entrepreneurship, and management."),
    TRAVEL("Travel", "Works that explore and document travel experiences, destinations, cultures, and adventures."),
    POETRY("Poetry", "Works that use language, imagery, and rhythm to evoke emotions, express ideas, and create beauty."),
    DRAMA("Drama/Play", "Works that are intended to be performed on stage and often explore complex human emotions, relationships, and conflicts."),
    YOUNG_ADULT("Young Adult (YA)", "Works that are targeted at a teenage audience and often explore themes relevant to young adults, such as identity, friendship, and coming-of-age."),
    CHILDRENS("Children's literature", "Works that are written for and targeted at children, typically up to the age of 12, and often include imaginative stories, colorful illustrations, and moral lessons."),
    GRAPHIC_NOVEL("Graphic novel/Comic", "Works that use a combination of illustrations and text to tell a story, often in a serialized format."),
    COOKBOOK("Cookbook", "Works that provide recipes, cooking tips, and culinary advice for preparing meals and dishes.");

    private final String name;
    private final String description;

    Genre(String name, String description) {
        this.name = name;
        this.description = description;
    }

}
