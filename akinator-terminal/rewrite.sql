-- ==========================================
-- 1. TABLE CREATION 
-- ==========================================
DROP TABLE IF EXISTS character_answers;
DROP TABLE IF EXISTS questions;
DROP TABLE IF EXISTS characters;
DROP TABLE IF EXISTS categories;

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
    expected_answer INT, 
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
-- 3. INSERT ALL 105 CHARACTERS
-- ==========================================
INSERT INTO characters (id, name, category_id, image_path) VALUES 
-- Footballers (1-15)
(1, 'Lionel Messi', 1, 'https://upload.wikimedia.org/wikipedia/commons/b/b4/Lionel-Messi-Argentina-2022-FIFA-World-Cup_%28cropped%29.jpg'),
(2, 'Cristiano Ronaldo', 1, 'https://upload.wikimedia.org/wikipedia/commons/8/8c/Cristiano_Ronaldo_2018.jpg'),
(3, 'Yassine Bounou', 1, 'https://upload.wikimedia.org/wikipedia/commons/f/f6/Yassine_Bounou_%28cropped%29.jpg'),
(22, 'Kylian Mbappé', 1, 'https://ui-avatars.com/api/?name=Kylian+Mbappe&size=256&background=random'),
(23, 'Neymar Jr', 1, 'https://ui-avatars.com/api/?name=Neymar+Jr&size=256&background=random'),
(24, 'Kevin De Bruyne', 1, 'https://ui-avatars.com/api/?name=Kevin+De+Bruyne&size=256&background=random'),
(25, 'Manuel Neuer', 1, 'https://ui-avatars.com/api/?name=Manuel+Neuer&size=256&background=random'),
(26, 'Erling Haaland', 1, 'https://ui-avatars.com/api/?name=Erling+Haaland&size=256&background=random'),
(27, 'Zinedine Zidane', 1, 'https://ui-avatars.com/api/?name=Zinedine+Zidane&size=256&background=random'),
(28, 'Gianluigi Buffon', 1, 'https://ui-avatars.com/api/?name=Gianluigi+Buffon&size=256&background=random'),
(29, 'Alexia Putellas', 1, 'https://ui-avatars.com/api/?name=Alexia+Putellas&size=256&background=random'),
(30, 'Ronaldinho', 1, 'https://ui-avatars.com/api/?name=Ronaldinho&size=256&background=random'),
(31, 'Achraf Hakimi', 1, 'https://ui-avatars.com/api/?name=Achraf+Hakimi&size=256&background=random'),
(92, 'Luka Modric', 1, 'https://ui-avatars.com/api/?name=Luka+Modric&size=256&background=random'),
(93, 'Sergio Ramos', 1, 'https://ui-avatars.com/api/?name=Sergio+Ramos&size=256&background=random'),

-- Anime & Manga (16-30)
(4, 'Itachi Uchiha', 2, 'https://upload.wikimedia.org/wikipedia/en/e/e0/Itachi_Uchiha.jpg'),
(5, 'Naruto Uzumaki', 2, 'https://upload.wikimedia.org/wikipedia/en/9/9a/NarutoUzamaki.png'),
(6, 'Light Yagami', 2, 'https://upload.wikimedia.org/wikipedia/en/0/0c/Light_Yagami_manga.jpg'),
(32, 'Goku', 2, 'https://ui-avatars.com/api/?name=Goku&size=256&background=random'),
(33, 'Vegeta', 2, 'https://ui-avatars.com/api/?name=Vegeta&size=256&background=random'),
(34, 'Sasuke Uchiha', 2, 'https://ui-avatars.com/api/?name=Sasuke+Uchiha&size=256&background=random'),
(35, 'Levi Ackerman', 2, 'https://ui-avatars.com/api/?name=Levi+Ackerman&size=256&background=random'),
(36, 'Monkey D. Luffy', 2, 'https://ui-avatars.com/api/?name=Monkey+D+Luffy&size=256&background=random'),
(37, 'Roronoa Zoro', 2, 'https://ui-avatars.com/api/?name=Roronoa+Zoro&size=256&background=random'),
(38, 'Eren Yeager', 2, 'https://ui-avatars.com/api/?name=Eren+Yeager&size=256&background=random'),
(39, 'Mikasa Ackerman', 2, 'https://ui-avatars.com/api/?name=Mikasa+Ackerman&size=256&background=random'),
(40, 'Saitama', 2, 'https://ui-avatars.com/api/?name=Saitama&size=256&background=random'),
(41, 'Gojo Satoru', 2, 'https://ui-avatars.com/api/?name=Gojo+Satoru&size=256&background=random'),
(94, 'Edward Elric', 2, 'https://ui-avatars.com/api/?name=Edward+Elric&size=256&background=random'),
(95, 'Killua Zoldyck', 2, 'https://ui-avatars.com/api/?name=Killua+Zoldyck&size=256&background=random'),

-- Actors (31-45)
(7, 'Leonardo DiCaprio', 3, 'https://upload.wikimedia.org/wikipedia/commons/4/46/Leonardo_Dicaprio_Cannes_2019.jpg'),
(8, 'Cillian Murphy', 3, 'https://upload.wikimedia.org/wikipedia/commons/a/a5/Cillian_Murphy_Press_Conference_The_Party_Berlinale_2017_02cr.jpg'),
(9, 'Zendaya', 3, 'https://upload.wikimedia.org/wikipedia/commons/2/28/Zendaya_-_2019_by_Glenn_Francis.jpg'),
(42, 'Brad Pitt', 3, 'https://ui-avatars.com/api/?name=Brad+Pitt&size=256&background=random'),
(43, 'Tom Cruise', 3, 'https://ui-avatars.com/api/?name=Tom+Cruise&size=256&background=random'),
(44, 'Robert Downey Jr.', 3, 'https://ui-avatars.com/api/?name=Robert+Downey&size=256&background=random'),
(45, 'Johnny Depp', 3, 'https://ui-avatars.com/api/?name=Johnny+Depp&size=256&background=random'),
(46, 'Scarlett Johansson', 3, 'https://ui-avatars.com/api/?name=Scarlett+Johansson&size=256&background=random'),
(47, 'Margot Robbie', 3, 'https://ui-avatars.com/api/?name=Margot+Robbie&size=256&background=random'),
(48, 'Heath Ledger', 3, 'https://ui-avatars.com/api/?name=Heath+Ledger&size=256&background=random'),
(49, 'Matt Damon', 3, 'https://ui-avatars.com/api/?name=Matt+Damon&size=256&background=random'),
(50, 'Morgan Freeman', 3, 'https://ui-avatars.com/api/?name=Morgan+Freeman&size=256&background=random'),
(51, 'Tom Hanks', 3, 'https://ui-avatars.com/api/?name=Tom+Hanks&size=256&background=random'),
(96, 'Keanu Reeves', 3, 'https://ui-avatars.com/api/?name=Keanu+Reeves&size=256&background=random'),
(97, 'Tom Hardy', 3, 'https://ui-avatars.com/api/?name=Tom+Hardy&size=256&background=random'),

-- Singers (46-60)
(10, 'The Weeknd', 4, 'https://upload.wikimedia.org/wikipedia/commons/c/c2/The_Weeknd_Cannes_2023.jpg'),
(11, 'Michael Jackson', 4, 'https://upload.wikimedia.org/wikipedia/commons/3/31/Michael_Jackson_in_1988.jpg'),
(12, 'Taylor Swift', 4, 'https://upload.wikimedia.org/wikipedia/commons/b/b1/Taylor_Swift_at_the_2023_MTV_Video_Music_Awards_%283%29.png'),
(52, 'Justin Bieber', 4, 'https://ui-avatars.com/api/?name=Justin+Bieber&size=256&background=random'),
(53, 'Ed Sheeran', 4, 'https://ui-avatars.com/api/?name=Ed+Sheeran&size=256&background=random'),
(54, 'Rihanna', 4, 'https://ui-avatars.com/api/?name=Rihanna&size=256&background=random'),
(55, 'Beyoncé', 4, 'https://ui-avatars.com/api/?name=Beyonce&size=256&background=random'),
(56, 'Eminem', 4, 'https://ui-avatars.com/api/?name=Eminem&size=256&background=random'),
(57, 'Drake', 4, 'https://ui-avatars.com/api/?name=Drake&size=256&background=random'),
(58, 'Freddie Mercury', 4, 'https://ui-avatars.com/api/?name=Freddie+Mercury&size=256&background=random'),
(59, 'Elvis Presley', 4, 'https://ui-avatars.com/api/?name=Elvis+Presley&size=256&background=random'),
(60, 'Billie Eilish', 4, 'https://ui-avatars.com/api/?name=Billie+Eilish&size=256&background=random'),
(61, 'Bruno Mars', 4, 'https://ui-avatars.com/api/?name=Bruno+Mars&size=256&background=random'),
(98, 'Ariana Grande', 4, 'https://ui-avatars.com/api/?name=Ariana+Grande&size=256&background=random'),
(99, 'Harry Styles', 4, 'https://ui-avatars.com/api/?name=Harry+Styles&size=256&background=random'),

-- YouTubers (61-75)
(13, 'MrBeast', 5, 'https://upload.wikimedia.org/wikipedia/commons/a/a9/MrBeast_in_2023_%28cropped%29.jpg'),
(14, 'IShowSpeed', 5, 'https://upload.wikimedia.org/wikipedia/commons/3/30/IShowSpeed_2023_%28cropped%29.jpg'),
(15, 'PewDiePie', 5, 'https://upload.wikimedia.org/wikipedia/commons/8/86/PewDiePie_at_PAX_2015_crop.jpg'),
(62, 'Kai Cenat', 5, 'https://ui-avatars.com/api/?name=Kai+Cenat&size=256&background=random'),
(63, 'xQc', 5, 'https://ui-avatars.com/api/?name=xQc&size=256&background=random'),
(64, 'Ninja', 5, 'https://ui-avatars.com/api/?name=Ninja&size=256&background=random'),
(65, 'Markiplier', 5, 'https://ui-avatars.com/api/?name=Markiplier&size=256&background=random'),
(66, 'Jacksepticeye', 5, 'https://ui-avatars.com/api/?name=Jacksepticeye&size=256&background=random'),
(67, 'KSI', 5, 'https://ui-avatars.com/api/?name=KSI&size=256&background=random'),
(68, 'Logan Paul', 5, 'https://ui-avatars.com/api/?name=Logan+Paul&size=256&background=random'),
(69, 'Pokimane', 5, 'https://ui-avatars.com/api/?name=Pokimane&size=256&background=random'),
(70, 'Valkyrae', 5, 'https://ui-avatars.com/api/?name=Valkyrae&size=256&background=random'),
(71, 'TommyInnit', 5, 'https://ui-avatars.com/api/?name=TommyInnit&size=256&background=random'),
(100, 'Marques Brownlee', 5, 'https://ui-avatars.com/api/?name=Marques+Brownlee&size=256&background=random'),
(101, 'Dream', 5, 'https://ui-avatars.com/api/?name=Dream&size=256&background=random'),

-- Video Games (76-90)
(16, 'Arthur Morgan', 6, 'https://upload.wikimedia.org/wikipedia/en/a/a6/Arthur_Morgan.png'),
(17, 'Mario', 6, 'https://upload.wikimedia.org/wikipedia/en/a/a9/Mario_Illusion_SM3DW.png'),
(18, 'Kratos', 6, 'https://upload.wikimedia.org/wikipedia/en/2/22/Kratos_PS4.jpg'),
(72, 'Master Chief', 6, 'https://ui-avatars.com/api/?name=Master+Chief&size=256&background=random'),
(73, 'Lara Croft', 6, 'https://ui-avatars.com/api/?name=Lara+Croft&size=256&background=random'),
(74, 'Geralt of Rivia', 6, 'https://ui-avatars.com/api/?name=Geralt+of+Rivia&size=256&background=random'),
(75, 'John Marston', 6, 'https://ui-avatars.com/api/?name=John+Marston&size=256&background=random'),
(76, 'Sonic the Hedgehog', 6, 'https://ui-avatars.com/api/?name=Sonic&size=256&background=random'),
(77, 'Link', 6, 'https://ui-avatars.com/api/?name=Link&size=256&background=random'),
(78, 'Zelda', 6, 'https://ui-avatars.com/api/?name=Zelda&size=256&background=random'),
(79, 'Cloud Strife', 6, 'https://ui-avatars.com/api/?name=Cloud+Strife&size=256&background=random'),
(80, 'Ellie', 6, 'https://ui-avatars.com/api/?name=Ellie&size=256&background=random'),
(81, 'Joel Miller', 6, 'https://ui-avatars.com/api/?name=Joel+Miller&size=256&background=random'),
(102, 'Nathan Drake', 6, 'https://ui-avatars.com/api/?name=Nathan+Drake&size=256&background=random'),
(103, 'Pikachu', 6, 'https://ui-avatars.com/api/?name=Pikachu&size=256&background=random'),

-- Tech Titans (91-105)
(19, 'Bill Gates', 7, 'https://upload.wikimedia.org/wikipedia/commons/a/a0/Bill_Gates_2018.jpg'),
(20, 'Steve Jobs', 7, 'https://upload.wikimedia.org/wikipedia/commons/d/dc/Steve_Jobs_Headshot_2010-CROP.jpg'),
(21, 'Linus Torvalds', 7, 'https://upload.wikimedia.org/wikipedia/commons/0/01/LinuxCon_Europe_Linus_Torvalds_03_%28cropped%29.jpg'),
(82, 'Mark Zuckerberg', 7, 'https://ui-avatars.com/api/?name=Mark+Zuckerberg&size=256&background=random'),
(83, 'Jeff Bezos', 7, 'https://ui-avatars.com/api/?name=Jeff+Bezos&size=256&background=random'),
(84, 'Satya Nadella', 7, 'https://ui-avatars.com/api/?name=Satya+Nadella&size=256&background=random'),
(85, 'Paul Allen', 7, 'https://ui-avatars.com/api/?name=Paul+Allen&size=256&background=random'),
(86, 'Tim Cook', 7, 'https://ui-avatars.com/api/?name=Tim+Cook&size=256&background=random'),
(87, 'Steve Wozniak', 7, 'https://ui-avatars.com/api/?name=Steve+Wozniak&size=256&background=random'),
(88, 'Larry Page', 7, 'https://ui-avatars.com/api/?name=Larry+Page&size=256&background=random'),
(89, 'Sergey Brin', 7, 'https://ui-avatars.com/api/?name=Sergey+Brin&size=256&background=random'),
(90, 'Ada Lovelace', 7, 'https://ui-avatars.com/api/?name=Ada+Lovelace&size=256&background=random'),
(91, 'Alan Turing', 7, 'https://ui-avatars.com/api/?name=Alan+Turing&size=256&background=random'),
(104, 'Elon Musk', 7, 'https://ui-avatars.com/api/?name=Elon+Musk&size=256&background=random'),
(105, 'Nikola Tesla', 7, 'https://ui-avatars.com/api/?name=Nikola+Tesla&size=256&background=random');

-- ==========================================
-- 4. INSERT 96 PERFECT SCALPEL QUESTIONS
-- ==========================================
INSERT INTO questions (id, question_text, parent_question_id) VALUES 
-- Level 1 (Base Core)
(1, 'Is your character a real, living person?', NULL),
(2, 'Is your character female?', NULL),
(3, 'Is your character deceased?', NULL),
(4, 'Is your character famous for playing a sport?', NULL),
(5, 'Is your character from an anime or manga?', NULL),
(6, 'Is your character an actor or actress?', NULL),
(7, 'Is your character primarily a singer or musician?', NULL),
(8, 'Is your character famous for making YouTube or Twitch videos?', NULL),
(9, 'Is your character from a video game?', NULL),
(10, 'Is your character a technology billionaire or historical innovator?', NULL),

-- Football (Parent: 4)
(11, 'Is your character a goalkeeper?', 4),
(12, 'Is your character an attacker (striker or winger)?', 4),
(13, 'Is your character a midfielder?', 4),
(14, 'Is your character a defender?', 4),
(15, 'Has your character ever played in the English Premier League?', 4),
(16, 'Has your character ever played for Real Madrid?', 4),
(17, 'Has your character ever played for FC Barcelona?', 4),
(18, 'Has your character ever won the World Cup?', 4),
(19, 'Has your character ever won the Ballon d''Or?', 4),
(20, 'Is your character from an African country?', 4),
(21, 'Is your character German?', 4),
(22, 'Is your character naturally left-footed?', 4),

-- Anime (Parent: 5)
(23, 'Is your character a ninja?', 5),
(24, 'Is your character from the Dragon Ball universe?', 5),
(25, 'Is your character a pirate?', 5),
(26, 'Does your character fight against Titans?', 5),
(27, 'Is your character the main protagonist of their story?', 5),
(28, 'Does your character use a sword as their main weapon?', 5),
(29, 'Is your character from Death Note?', 5),
(30, 'Is your character from Jujutsu Kaisen?', 5),
(31, 'Did your character wipe out their entire clan?', 5),
(32, 'Is your character from Hunter x Hunter?', 5),
(33, 'Does your character have naturally blonde hair?', 5),
(34, 'Is your character bald?', 5),

-- Actors (Parent: 6)
(35, 'Is your character famous for playing a comic book superhero or villain?', 6),
(36, 'Is your character in the Marvel Cinematic Universe (MCU)?', 6),
(37, 'Did your character play the Joker or Bane?', 6),
(38, 'Did your character star in the movie Oppenheimer?', 6),
(39, 'Did your character famously play a pirate?', 6),
(40, 'Is your character known for doing their own extreme stunts?', 6),
(41, 'Has your character won an Oscar for Best Actor or Supporting Actor?', 6),
(42, 'Is your character known for playing John Wick or Neo?', 6),
(43, 'Did your character voice a famous animated cowboy?', 6),
(44, 'Does your character have naturally blonde hair?', 6),
(45, 'Did your character play Black Widow?', 6),
(46, 'Did your character play Jason Bourne?', 6),
(47, 'Did your character star in Titanic?', 6),
(48, 'Is your character famous for their deep voice, sometimes playing God?', 6),

-- Singers (Parent: 7)
(49, 'Is your character known as the "King of Pop"?', 7),
(50, 'Is your character a rapper?', 7),
(51, 'Is your character white?', 7),
(52, 'Is your character British?', 7),
(53, 'Is your character from Canada?', 7),
(54, 'Was your character in a famous boy band?', 7),
(55, 'Does your character have naturally blonde hair?', 7),
(56, 'Did your character start their career in Country music?', 7),
(57, 'Did your character create Fenty Beauty or sing "Umbrella"?', 7),
(58, 'Was your character in Destiny''s Child?', 7),
(59, 'Did your character star on a Nickelodeon TV show?', 7),
(60, 'Does your character have red hair?', 7),

-- YouTubers (Parent: 8)
(61, 'Does your character primarily stream on Twitch?', 8),
(62, 'Is your character known for giving away millions of dollars?', 8),
(63, 'Has your character fought in influencer boxing matches?', 8),
(64, 'Is your character British?', 8),
(65, 'Is your character famous for playing Minecraft?', 8),
(66, 'Did your character hide their face behind a mask for years?', 8),
(67, 'Is your character a technology reviewer?', 8),
(68, 'Is your character known for barking and loving Cristiano Ronaldo?', 8),
(69, 'Is your character Irish?', 8),
(70, 'Is your character Swedish?', 8),
(71, 'Is your character known as the king of Five Nights at Freddy''s?', 8),
(72, 'Is your character a co-owner of 100 Thieves?', 8),
(73, 'Did your character famously have bright blue hair?', 8),
(74, 'Is your character French-Canadian?', 8),

-- Video Games (Parent: 9)
(75, 'Is your character a cowboy from the Wild West?', 9),
(76, 'Does your character tragically die of Tuberculosis?', 9),
(77, 'Is your character primarily from a PlayStation exclusive franchise?', 9),
(78, 'Is your character the mascot for Xbox?', 9),
(79, 'Is your character from a Nintendo franchise?', 9),
(80, 'Is your character an animal or creature?', 9),
(81, 'Is your character blue?', 9),
(82, 'Is your character from The Last of Us?', 9),
(83, 'Is your character from Final Fantasy?', 9),
(84, 'Is your character a Witcher?', 9),
(85, 'Is your character from God of War?', 9),
(86, 'Is your character from Uncharted?', 9),
(87, 'Does your character use a sword?', 9),

-- Tech (Parent: 10)
(88, 'Is your character associated with Microsoft?', 10),
(89, 'Is your character the CURRENT CEO of a major tech company?', 10),
(90, 'Is your character associated with Apple?', 10),
(91, 'Is your character associated with Google?', 10),
(92, 'Is your character from Russia?', 10),
(93, 'Did your character create the Linux kernel?', 10),
(94, 'Did your character create Facebook (Meta)?', 10),
(95, 'Did your character found Amazon?', 10),
(96, 'Is your character associated with Tesla or SpaceX?', 10);

-- ==========================================
-- 5. THE MAGIC CROSS JOIN (Sets all answers to 0)
-- ==========================================
INSERT INTO character_answers (character_id, question_id, expected_answer)
SELECT c.id, q.id, 0
FROM characters c CROSS JOIN questions q;

-- ==========================================
-- 6. FLIPPING THE CORRECT ANSWERS TO '1' (YES)
-- *CORRECTED TO USE SUBQUERIES FOR CATEGORIES!*
-- ==========================================

-- Base Core (Q1-Q10)
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 1 AND character_id IN (SELECT id FROM characters WHERE category_id IN (1, 3, 4, 5, 7)); -- Real People
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 2 AND character_id IN (29, 39, 9, 46, 47, 12, 54, 55, 60, 98, 69, 70, 73, 78, 80, 90); -- Females
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 3 AND character_id IN (48, 11, 58, 59, 20, 85, 90, 91, 105, 4, 6, 16, 75, 81); -- Deceased
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 4 AND character_id IN (SELECT id FROM characters WHERE category_id = 1); -- Sports
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 5 AND character_id IN (SELECT id FROM characters WHERE category_id = 2); -- Anime
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 6 AND character_id IN (SELECT id FROM characters WHERE category_id = 3); -- Actors
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 7 AND character_id IN (SELECT id FROM characters WHERE category_id = 4); -- Singers
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 8 AND character_id IN (SELECT id FROM characters WHERE category_id = 5); -- YouTubers
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 9 AND character_id IN (SELECT id FROM characters WHERE category_id = 6); -- Games
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 10 AND character_id IN (SELECT id FROM characters WHERE category_id = 7); -- Tech

-- Football (Q11-Q22)
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 11 AND character_id IN (3, 25, 28);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 12 AND character_id IN (1, 2, 22, 23, 26, 29, 30);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 13 AND character_id IN (24, 27, 92);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 14 AND character_id IN (31, 93);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 15 AND character_id IN (2, 24, 26, 92);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 16 AND character_id IN (2, 22, 27, 31, 92, 93);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 17 AND character_id IN (1, 23, 29, 30);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 18 AND character_id IN (1, 22, 25, 27, 28, 29, 30, 93);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 19 AND character_id IN (1, 2, 27, 29, 30, 92);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 20 AND character_id IN (3, 31);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 21 AND character_id IN (25);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 22 AND character_id IN (1);

-- Anime (Q23-Q34)
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 23 AND character_id IN (4, 5, 34);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 24 AND character_id IN (32, 33);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 25 AND character_id IN (36, 37);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 26 AND character_id IN (35, 38, 39);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 27 AND character_id IN (5, 6, 32, 36, 38, 40, 94);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 28 AND character_id IN (34, 35, 37, 39);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 29 AND character_id IN (6);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 30 AND character_id IN (41);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 31 AND character_id IN (4);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 32 AND character_id IN (95);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 33 AND character_id IN (5, 94);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 34 AND character_id IN (40);

-- Actors (Q35-Q48)
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 35 AND character_id IN (9, 44, 46, 47, 48, 97);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 36 AND character_id IN (9, 44, 46);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 37 AND character_id IN (48, 97);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 38 AND character_id IN (8, 44, 49);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 39 AND character_id IN (45);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 40 AND character_id IN (43);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 41 AND character_id IN (7, 8, 42, 44, 48, 49, 50, 51);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 42 AND character_id IN (96);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 43 AND character_id IN (51);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 44 AND character_id IN (42, 47);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 45 AND character_id IN (46);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 46 AND character_id IN (49);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 47 AND character_id IN (7);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 48 AND character_id IN (50);

-- Singers (Q49-Q60)
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 49 AND character_id IN (11);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 50 AND character_id IN (56, 57);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 51 AND character_id IN (12, 52, 53, 56, 58, 59, 60, 98, 99);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 52 AND character_id IN (53, 58, 99);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 53 AND character_id IN (10, 52, 57);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 54 AND character_id IN (99);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 55 AND character_id IN (12, 60);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 56 AND character_id IN (12);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 57 AND character_id IN (54);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 58 AND character_id IN (55);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 59 AND character_id IN (98);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 60 AND character_id IN (53);

-- YouTubers (Q61-Q74)
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 61 AND character_id IN (62, 63, 64, 69, 70);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 62 AND character_id IN (13);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 63 AND character_id IN (67, 68);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 64 AND character_id IN (67, 71);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 65 AND character_id IN (15, 71, 101);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 66 AND character_id IN (101);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 67 AND character_id IN (100);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 68 AND character_id IN (14);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 69 AND character_id IN (66);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 70 AND character_id IN (15);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 71 AND character_id IN (65);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 72 AND character_id IN (70);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 73 AND character_id IN (64);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 74 AND character_id IN (63);

-- Video Games (Q75-Q87)
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 75 AND character_id IN (16, 75);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 76 AND character_id IN (16);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 77 AND character_id IN (18, 80, 81, 102);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 78 AND character_id IN (72);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 79 AND character_id IN (17, 77, 78, 103);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 80 AND character_id IN (76, 103);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 81 AND character_id IN (76);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 82 AND character_id IN (80, 81);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 83 AND character_id IN (79);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 84 AND character_id IN (74);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 85 AND character_id IN (18);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 86 AND character_id IN (102);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 87 AND character_id IN (74, 77, 79);

-- Tech (Q88-Q96)
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 88 AND character_id IN (19, 84, 85);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 89 AND character_id IN (84, 86);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 90 AND character_id IN (20, 86, 87);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 91 AND character_id IN (88, 89);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 92 AND character_id IN (89);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 93 AND character_id IN (21);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 94 AND character_id IN (82);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 95 AND character_id IN (83);
UPDATE character_answers SET expected_answer = 1 WHERE question_id = 96 AND character_id IN (104);