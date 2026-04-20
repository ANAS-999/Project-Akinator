-- ==========================================
-- 1. TABLE CREATION
-- ==========================================

CREATE TABLE categories (
    id INT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE characters (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category_id INT,
    image_path VARCHAR(255),
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE questions (
    id INT PRIMARY KEY,
    question_text VARCHAR(255) NOT NULL,
    parent_question_id INT DEFAULT NULL
);

CREATE TABLE character_answers (
    character_id INT,
    question_id INT,
    expected_answer INT, -- 1 for Yes, 0 for No, -1 for Don't Know
    PRIMARY KEY (character_id, question_id),
    FOREIGN KEY (character_id) REFERENCES characters(id),
    FOREIGN KEY (question_id) REFERENCES questions(id)
);

-- ==========================================
-- 2. INSERT CATEGORIES
-- ==========================================
INSERT INTO categories (id, name) VALUES 
(1, 'Football'), (2, 'Anime'), (3, 'Actors'), 
(4, 'Singers'), (5, 'YouTubers'), (6, 'Video Games'), (7, 'Tech');

-- ==========================================
-- 3. INSERT CHARACTERS
-- ==========================================
INSERT INTO characters (id, name, category_id, image_path) VALUES 
-- Footballers (1-3)
(1, 'Lionel Messi', 1, 'messi.png'),
(2, 'Cristiano Ronaldo', 1, 'ronaldo.png'),
(3, 'Yassine Bounou', 1, 'bounou.png'),
-- Anime (4-6)
(4, 'Itachi Uchiha', 2, 'itachi.png'),
(5, 'Naruto Uzumaki', 2, 'naruto.png'),
(6, 'Light Yagami', 2, 'light.png'),
-- Actors (7-9)
(7, 'Leonardo DiCaprio', 3, 'leo.png'),
(8, 'Cillian Murphy', 3, 'cillian.png'),
(9, 'Zendaya', 3, 'zendaya.png'),
-- Singers (10-12)
(10, 'The Weeknd', 4, 'weeknd.png'),
(11, 'Michael Jackson', 4, 'mj.png'),
(12, 'Taylor Swift', 4, 'taylor.png'),
-- YouTubers (13-15)
(13, 'MrBeast', 5, 'mrbeast.png'),
(14, 'IShowSpeed', 5, 'speed.png'),
(15, 'PewDiePie', 5, 'pewdiepie.png'),
-- Video Games (16-18)
(16, 'Arthur Morgan', 6, 'arthur.png'),
(17, 'Mario', 6, 'mario.png'),
(18, 'Kratos', 6, 'kratos.png'),
-- Tech (19-21)
(19, 'Bill Gates', 7, 'gates.png'),
(20, 'Steve Jobs', 7, 'jobs.png'),
(21, 'Linus Torvalds', 7, 'linus.png');

-- ==========================================
-- 4. INSERT QUESTIONS
-- ==========================================
INSERT INTO questions (id, question_text) VALUES 
-- Level 1: The Traffic Cops (1-8)
(1, 'Is your character a real, living person?'),
(2, 'Is your character female?'),
(3, 'Is your character famous for playing a sport?'),
(4, 'Is your character from an anime or manga?'),
(5, 'Is your character an actor or actress?'),
(6, 'Is your character primarily a singer or musician?'),
(7, 'Is your character famous for making YouTube or Twitch videos?'),
(8, 'Is your character from a video game?'),
-- Level 2 & 3: Category Scalpels (9-20)
(9, 'Has your character ever won the World Cup?'),
(10, 'Is your character a goalkeeper?'),
(11, 'Did your character wipe out their entire clan?'),
(12, 'Is your character the main protagonist of their story?'),
(13, 'Did your character star in the movie Oppenheimer?'),
(14, 'Is your character known as the "King of Pop"?'),
(15, 'Is your character known for giving away millions of dollars?'),
(16, 'Is your character a cowboy from the Wild West?'),
(17, 'Did your character create the Linux kernel?'),
(18, 'Is your character deceased (dead)?'),
(19, 'Does your character have blonde hair?'),
(20, 'Is your character associated with Microsoft?');

-- ==========================================
-- 5. MAPPING THE ANSWERS 
-- (1 = Yes, 0 = No)
-- ==========================================

-- 1. Lionel Messi
INSERT INTO character_answers (character_id, question_id, expected_answer) VALUES 
(1,1,1), (1,2,0), (1,3,1), (1,4,0), (1,5,0), (1,6,0), (1,7,0), (1,8,0), (1,9,1), (1,10,0), (1,11,0), (1,12,0), (1,13,0), (1,14,0), (1,15,0), (1,16,0), (1,17,0), (1,18,0), (1,19,0), (1,20,0);

-- 2. Cristiano Ronaldo
INSERT INTO character_answers (character_id, question_id, expected_answer) VALUES 
(2,1,1), (2,2,0), (2,3,1), (2,4,0), (2,5,0), (2,6,0), (2,7,0), (2,8,0), (2,9,0), (2,10,0), (2,11,0), (2,12,0), (2,13,0), (2,14,0), (2,15,0), (2,16,0), (2,17,0), (2,18,0), (2,19,0), (2,20,0);

-- 3. Yassine Bounou
INSERT INTO character_answers (character_id, question_id, expected_answer) VALUES 
(3,1,1), (3,2,0), (3,3,1), (3,4,0), (3,5,0), (3,6,0), (3,7,0), (3,8,0), (3,9,0), (3,10,1), (3,11,0), (3,12,0), (3,13,0), (3,14,0), (3,15,0), (3,16,0), (3,17,0), (3,18,0), (3,19,0), (3,20,0);

-- 4. Itachi Uchiha
INSERT INTO character_answers (character_id, question_id, expected_answer) VALUES 
(4,1,0), (4,2,0), (4,3,0), (4,4,1), (4,5,0), (4,6,0), (4,7,0), (4,8,0), (4,9,0), (4,10,0), (4,11,1), (4,12,0), (4,13,0), (4,14,0), (4,15,0), (4,16,0), (4,17,0), (4,18,1), (4,19,0), (4,20,0);

-- 5. Naruto Uzumaki
INSERT INTO character_answers (character_id, question_id, expected_answer) VALUES 
(5,1,0), (5,2,0), (5,3,0), (5,4,1), (5,5,0), (5,6,0), (5,7,0), (5,8,0), (5,9,0), (5,10,0), (5,11,0), (5,12,1), (5,13,0), (5,14,0), (5,15,0), (5,16,0), (5,17,0), (5,18,0), (5,19,1), (5,20,0);

-- 6. Light Yagami
INSERT INTO character_answers (character_id, question_id, expected_answer) VALUES 
(6,1,0), (6,2,0), (6,3,0), (6,4,1), (6,5,0), (6,6,0), (6,7,0), (6,8,0), (6,9,0), (6,10,0), (6,11,0), (6,12,1), (6,13,0), (6,14,0), (6,15,0), (6,16,0), (6,17,0), (6,18,1), (6,19,0), (6,20,0);

-- 7. Leonardo DiCaprio
INSERT INTO character_answers (character_id, question_id, expected_answer) VALUES 
(7,1,1), (7,2,0), (7,3,0), (7,4,0), (7,5,1), (7,6,0), (7,7,0), (7,8,0), (7,9,0), (7,10,0), (7,11,0), (7,12,0), (7,13,0), (7,14,0), (7,15,0), (7,16,0), (7,17,0), (7,18,0), (7,19,1), (7,20,0);

-- 8. Cillian Murphy
INSERT INTO character_answers (character_id, question_id, expected_answer) VALUES 
(8,1,1), (8,2,0), (8,3,0), (8,4,0), (8,5,1), (8,6,0), (8,7,0), (8,8,0), (8,9,0), (8,10,0), (8,11,0), (8,12,0), (8,13,1), (8,14,0), (8,15,0), (8,16,0), (8,17,0), (8,18,0), (8,19,0), (8,20,0);

-- 9. Zendaya
INSERT INTO character_answers (character_id, question_id, expected_answer) VALUES 
(9,1,1), (9,2,1), (9,3,0), (9,4,0), (9,5,1), (9,6,0), (9,7,0), (9,8,0), (9,9,0), (9,10,0), (9,11,0), (9,12,0), (9,13,0), (9,14,0), (9,15,0), (9,16,0), (9,17,0), (9,18,0), (9,19,0), (9,20,0);

-- 10. The Weeknd
INSERT INTO character_answers (character_id, question_id, expected_answer) VALUES 
(10,1,1), (10,2,0), (10,3,0), (10,4,0), (10,5,0), (10,6,1), (10,7,0), (10,8,0), (10,9,0), (10,10,0), (10,11,0), (10,12,0), (10,13,0), (10,14,0), (10,15,0), (10,16,0), (10,17,0), (10,18,0), (10,19,0), (10,20,0);

-- 11. Michael Jackson
INSERT INTO character_answers (character_id, question_id, expected_answer) VALUES 
(11,1,0), (11,2,0), (11,3,0), (11,4,0), (11,5,0), (11,6,1), (11,7,0), (11,8,0), (11,9,0), (11,10,0), (11,11,0), (11,12,0), (11,13,0), (11,14,1), (11,15,0), (11,16,0), (11,17,0), (11,18,1), (11,19,0), (11,20,0);

-- 12. Taylor Swift
INSERT INTO character_answers (character_id, question_id, expected_answer) VALUES 
(12,1,1), (12,2,1), (12,3,0), (12,4,0), (12,5,0), (12,6,1), (12,7,0), (12,8,0), (12,9,0), (12,10,0), (12,11,0), (12,12,0), (12,13,0), (12,14,0), (12,15,0), (12,16,0), (12,17,0), (12,18,0), (12,19,1), (12,20,0);

-- 13. MrBeast
INSERT INTO character_answers (character_id, question_id, expected_answer) VALUES 
(13,1,1), (13,2,0), (13,3,0), (13,4,0), (13,5,0), (13,6,0), (13,7,1), (13,8,0), (13,9,0), (13,10,0), (13,11,0), (13,12,0), (13,13,0), (13,14,0), (13,15,1), (13,16,0), (13,17,0), (13,18,0), (13,19,0), (13,20,0);

-- 14. IShowSpeed
INSERT INTO character_answers (character_id, question_id, expected_answer) VALUES 
(14,1,1), (14,2,0), (14,3,0), (14,4,0), (14,5,0), (14,6,0), (14,7,1), (14,8,0), (14,9,0), (14,10,0), (14,11,0), (14,12,0), (14,13,0), (14,14,0), (14,15,0), (14,16,0), (14,17,0), (14,18,0), (14,19,0), (14,20,0);

-- 15. PewDiePie
INSERT INTO character_answers (character_id, question_id, expected_answer) VALUES 
(15,1,1), (15,2,0), (15,3,0), (15,4,0), (15,5,0), (15,6,0), (15,7,1), (15,8,0), (15,9,0), (15,10,0), (15,11,0), (15,12,0), (15,13,0), (15,14,0), (15,15,0), (15,16,0), (15,17,0), (15,18,0), (15,19,1), (15,20,0);

-- 16. Arthur Morgan
INSERT INTO character_answers (character_id, question_id, expected_answer) VALUES 
(16,1,0), (16,2,0), (16,3,0), (16,4,0), (16,5,0), (16,6,0), (16,7,0), (16,8,1), (16,9,0), (16,10,0), (16,11,0), (16,12,1), (16,13,0), (16,14,0), (16,15,0), (16,16,1), (16,17,0), (16,18,1), (16,19,0), (16,20,0);

-- 17. Mario
INSERT INTO character_answers (character_id, question_id, expected_answer) VALUES 
(17,1,0), (17,2,0), (17,3,0), (17,4,0), (17,5,0), (17,6,0), (17,7,0), (17,8,1), (17,9,0), (17,10,0), (17,11,0), (17,12,1), (17,13,0), (17,14,0), (17,15,0), (17,16,0), (17,17,0), (17,18,0), (17,19,0), (17,20,0);

-- 18. Kratos
INSERT INTO character_answers (character_id, question_id, expected_answer) VALUES 
(18,1,0), (18,2,0), (18,3,0), (18,4,0), (18,5,0), (18,6,0), (18,7,0), (18,8,1), (18,9,0), (18,10,0), (18,11,0), (18,12,1), (18,13,0), (18,14,0), (18,15,0), (18,16,0), (18,17,0), (18,18,0), (18,19,0), (18,20,0);

-- 19. Bill Gates
INSERT INTO character_answers (character_id, question_id, expected_answer) VALUES 
(19,1,1), (19,2,0), (19,3,0), (19,4,0), (19,5,0), (19,6,0), (19,7,0), (19,8,0), (19,9,0), (19,10,0), (19,11,0), (19,12,0), (19,13,0), (19,14,0), (19,15,0), (19,16,0), (19,17,0), (19,18,0), (19,19,0), (19,20,1);

-- 20. Steve Jobs
INSERT INTO character_answers (character_id, question_id, expected_answer) VALUES 
(20,1,0), (20,2,0), (20,3,0), (20,4,0), (20,5,0), (20,6,0), (20,7,0), (20,8,0), (20,9,0), (20,10,0), (20,11,0), (20,12,0), (20,13,0), (20,14,0), (20,15,0), (20,16,0), (20,17,0), (20,18,1), (20,19,0), (20,20,0);

-- 21. Linus Torvalds
INSERT INTO character_answers (character_id, question_id, expected_answer) VALUES 
(21,1,1), (21,2,0), (21,3,0), (21,4,0), (21,5,0), (21,6,0), (21,7,0), (21,8,0), (21,9,0), (21,10,0), (21,11,0), (21,12,0), (21,13,0), (21,14,0), (21,15,0), (21,16,0), (21,17,1), (21,18,0), (21,19,0), (21,20,0);