package org.example;

import org.example.entities.Book;
import org.example.entities.Genre;
import org.example.services.BookService;
import org.example.services.RoleService;
import org.example.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Set;

@SpringBootApplication
@EnableScheduling
public class KutubyApplication {
    public static void main(String[] args) {

        SpringApplication.run(KutubyApplication.class, args);
    }

    //@Bean
    CommandLineRunner commandLineRunnerUserDetails(UserService userService, RoleService roleService){
        return args-> {
            roleService.addNewRole("USER");
            roleService.addNewRole("ADMIN");
            //userService.addUser("user1", "1234", "user1@gmail.com", "1234");
            //userService.addUser("user2", "1234", "user2@gmail.com", "1234");
            userService.addUser("admin", "admin@gmail.com", "1234", "1234");
            //userService.addRoleToUser("user1","USER");
            //userService.addRoleToUser("user2","USER");
            //userService.addRoleToUser("admin","USER");
            userService.addRoleToUser("admin","ADMIN");
            userService.removeRoleFromUser("admin","USER");

        };
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    //@Bean
    CommandLineRunner commandLineRunnerBooks(BookService bookService){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        return args-> {
            bookService.addBook(new Book("IceBreaker", "Hannah Grace", "English", "IceBreaker.jpg",
                    Set.of(Genre.ROMANCE), dateFormat.parse("2022-11-22"),
                    "A TikTok sensation! Sparks fly when a competitive figure skater and hockey team captain are forced to share a rink.", 40));
            bookService.addBook(new Book("It Ends With Us", "Colleen Hoover", "English", "IEWU.jpg",
                    Set.of(Genre.ROMANCE), dateFormat.parse("2020-07-26"),
                    "In this “brave and heartbreaking novel that digs its claws into you and doesn’t let go, long after you’ve finished it” (Anna Todd, New York Times bestselling author) from the #1 New York Times bestselling author of All Your Perfects, a workaholic with a too-good-to-be-true romance can’t stop thinking about her first love.", 40));
            bookService.addBook(new Book("It Starts With Us", "Colleen Hoover", "English", "ISWU.jpg",
                    Set.of(Genre.ROMANCE), dateFormat.parse("2022-10-18"),
                    "Before It Ends with Us, it started with Atlas. Colleen Hoover tells fan favorite Atlas’s side of the story and shares what comes next in this long-anticipated sequel to the “glorious and touching” (USA TODAY) #1 New York Times bestseller It Ends with Us.", 40));
            bookService.addBook(new Book("Rich Dad Poor Dad - What the Rich Teach Their Kids About Money", "Robert T. Kiyosaki", "English", "RDPD.jpg",
                    Set.of(Genre.BUSINESS), dateFormat.parse("2022-11-22"),
                    "In Rich Dad Poor Dad, the #1 Personal Finance book of all time, Robert Kiyosaki shares the story of his two dad: his real father and his rich dad. One was educated and an employee all his life, the other's education was street smarts\" over traditional classroom education and he took the path of entrepreneurship?a road that led him to become one of the wealthiest men in Hawaii. Robert's poor dad struggled financially all his life. and these two dads had varying points of view of money and investing.Rich Dad Poor Dad will?? explode the myth that you need to earn a high income to become rich? challenge the belief that your house is an asset? show parents why they can't rely on the school system to teach their kids about money? define, once and for all, an asset and a liability? explain the difference between good debt and bad debt? teach you to see the world of money from different perspectives? discuss the shift in mindset that can put you on the road to financial freedom.", 70));
            bookService.addBook(new Book("The Lord of the Rings", "J.R.R. Tolkien", "English", "LOTR.jpg",
                    Set.of(Genre.FANTASY), dateFormat.parse("1954-07-29"),
                    "A classic high-fantasy epic featuring wizards, hobbits, and a quest to destroy a powerful ring.", 95));
            bookService.addBook(new Book("To Kill a Mockingbird", "Harper Lee", "English", "TKAM.jpg",
                    Set.of(Genre.LITERARY_FICTION, Genre.CLASSIC, Genre.SOCIAL_ISSUE), dateFormat.parse("1960-07-11"),
                    "A classic novel that addresses serious issues of race and morality in the American South.", 95));
            bookService.addBook(new Book("1984", "George Orwell", "English", "1984.jpg",
                    Set.of(Genre.SCIENCE_FICTION, Genre.DYSTOPIAN, Genre.POLITICAL), dateFormat.parse("1949-06-08"),
                    "A dystopian novel depicting a totalitarian regime and the struggles of individuals against it.", 90));
            bookService.addBook(new Book("Murder on the Orient Express", "Agatha Christie", "English", "MOTOE.jpg",
                    Set.of(Genre.MYSTERY), dateFormat.parse("1934-01-14"),
                    "A detective novel featuring the famous Hercule Poirot solving a murder mystery on a train.", 90));
            bookService.addBook(new Book("The Help", "Kathryn Stockett", "English", "the_help.jpg",
                    Set.of(Genre.HISTORICAL, Genre.SOCIAL_ISSUE), dateFormat.parse("2009-02-10"),
                    "Set in 1960s Mississippi, the novel explores the lives of African American maids during the civil rights movement.", 85));
            bookService.addBook(new Book("Pride and Prejudice", "Jane Austen", "English", "pride&prejudice.jpg",
                    Set.of(Genre.CLASSIC, Genre.ROMANCE, Genre.SOCIAL_ISSUE), dateFormat.parse("1813-01-28"),
                    "A romantic novel set in Georgian England, exploring themes of love, marriage, and social status.", 80));
            bookService.addBook(new Book("The Da Vinci Code", "Dan Brown", "English", "TDVC.jpg",
                    Set.of(Genre.THRILLER, Genre.MYSTERY), dateFormat.parse("2003-03-18"),
                    "A fast-paced thriller that follows symbologist Robert Langdon as he unravels a mystery involving the Catholic Church.", 85));
            bookService.addBook(new Book("The Diary of a Young Girl", "Anne Frank", "English", "TDOAYG.jpg",
                    Set.of(Genre.BIOGRAPHY, Genre.WAR), dateFormat.parse("1947-06-25"),
                    "The diary of Anne Frank, a Jewish girl hiding from the Nazis during World War II, offering a firsthand account of the Holocaust.", 80));
            bookService.addBook(new Book("It", "Stephen King", "English", "It.jpg",
                    Set.of(Genre.HORROR), dateFormat.parse("1986-09-15"),
                    "A horror novel about a malevolent entity that terrorizes the town of Derry, Maine, preying on children.", 85));
            bookService.addBook(new Book("The Seven Habits of Highly Effective People", "Stephen R. Covey", "English", "TSHOHEP.jpg",
                    Set.of(Genre.SELF_HELP), dateFormat.parse("1989-08-15"),
                    "A self-help book that presents seven habits for personal and professional effectiveness.", 90));
            bookService.addBook(new Book("A Brief History of Time", "Stephen Hawking", "English", "ABHOT.jpg",
                    Set.of(Genre.SCIENCE, Genre.PHYSICS), dateFormat.parse("1988-09-01"),
                    "A popular science book that explains complex concepts in cosmology, such as the Big Bang and black holes, for a general audience.", 95));
            bookService.addBook(new Book("Good Omens", "Terry Pratchett, Neil Gaiman", "English", "good_omens.jpg",
                    Set.of(Genre.COMEDY, Genre.FANTASY), dateFormat.parse("1990-05-01"),
                    "A comedic fantasy novel about an angel and a demon who team up to prevent the apocalypse.", 85));
            bookService.addBook(new Book("On the Road", "Jack Kerouac", "English", "on_the_road.jpg",
                    Set.of(Genre.TRAVEL, Genre.BEAT_GENERATION), dateFormat.parse("1957-10-20"),
                    "A novel based on Kerouac's travels across North America, capturing the essence of the Beat Generation.", 80));
            bookService.addBook(new Book("Ethics", "Baruch Spinoza", "English", "ethics.jpg",
                    Set.of(Genre.PHILOSOPHY), dateFormat.parse("1677-01-01"),
                    "A philosophical treatise that explores Spinoza's ideas on metaphysics, epistemology, and ethics.", 75));
            bookService.addBook(new Book("Atlas of the Heart", "Brené Brown", "English", "AOTH.jpg",
                    Set.of(Genre.SELF_HELP), dateFormat.parse("2021-11-30"),
                    "A guide to understanding and navigating the complexities of human emotions, based on research and personal anecdotes.", 95));
            bookService.addBook(new Book("Atomic Habits", "James Clear", "English", "atomic_habits.jpg",
                    Set.of(Genre.SELF_HELP), dateFormat.parse("2018-10-16"),
                    "A practical guide to building good habits, breaking bad ones, and mastering the tiny behaviors that lead to remarkable results.", 90));
        };
    }
}
