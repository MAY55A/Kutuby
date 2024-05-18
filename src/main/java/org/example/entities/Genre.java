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
    COOKBOOK("Cookbook", "Works that provide recipes, cooking tips, and culinary advice for preparing meals and dishes."),
    LITERARY_FICTION("Literary fiction", "Works that focus on character development, intricate prose, and exploration of deeper themes and emotions. They often prioritize artistic expression and philosophical exploration over conventional storytelling or genre conventions."),
    SOCIAL_ISSUE("Social issue","Works that explore and addresse societal problems, inequalities, or issues affecting communities or individuals."),
    POLITICAL("Political","Works that focus on politics, government structures, power dynamics, and political ideologies."),
    DYSTOPIAN("Dystopian","Works that portray a society characterized by oppressive government control, environmental degradation, or other negative societal trends."),
    CLASSIC("Classic","Works that are a representative of high quality, enduring literature that has stood the test of time and is widely recognized as culturally significant."),
    WAR("War","Works that deal with themes related to armed conflict, its causes, consequences, and impact on individuals or societies."),
    SCIENCE("Science","Works that explore scientific concepts, theories, discoveries, or advancements in various fields of study."),
    COMEDY("Comedy","Works that are intended to amuse and entertain, often through humor, satire, or absurd situations."),
    PHYSICS("Physics","Works that focus on the principles, laws, and phenomena of physics, including topics such as matter, energy, motion, and forces."),
    BEAT_GENERATION("Beat generation","Works that reflect the cultural and literary movement of the Beat Generation, characterized by a rejection of societal norms, experimentation with language and form, and an emphasis on spontaneity and individualism."),
    PHILOSOPHY("Philosophy","Works that delve into fundamental questions about existence, knowledge, values, reason, mind, and language, seeking to understand the nature of reality and the human experience.");

    private final String name;
    private final String description;

    Genre(String name, String description) {
        this.name = name;
        this.description = description;
    }

}
