# 🔮 JavaFX Akinator Game

A premium desktop clone of the famous **Akinator** character-guessing game. Built with **Java 11** and **JavaFX**, the application challenges players by guessing their chosen character (real or fictional) through interactive questions.

It features two distinct game modes:
1. **Classic Mode**: A rule-based offline engine that filters a preloaded character-attribute database matrix.
2. **AI Mode**: An open-domain conversational guessing loop powered by the **Groq Cloud API** (`llama-3.3-70b-versatile`).

---

## 🚀 Key Advantages

* **Portable Zero-Config SQLite Backend**: Migrated from a remote MySQL database to a local, self-contained SQLite configuration (`users.db` and `akinator.db`). The tables auto-initialize on the first run, allowing zero-configuration portable deployment.
* **State-of-the-Art AI Inference**: Leverages Groq API to perform conversational question/answering on open-domain characters with sub-second response times.
* **Premium Glassmorphic Aesthetics**: Linear blue gradient panels overlaid with translucent white-bordered containers, drop-shadow glow effects, and visual dimming overlays behind interactive pop-ups.
* **3D Animated Mechanical Buttons**: Home, music, and profile buttons feature 3D gold bevel designs with pressed state translations (`translate-y: 2px`) and hover scaling effects.
* **Competitive Gamification**: Tracks player high-scores and statistics, ranks users on a leaderboard, and highlights the active player's row with a gold-glowing border.
* **Secure Sessions**: User authentication with password hashing stored locally.

---

## 📂 Project Structure & Folder Roles

The codebase follows the Model-View-Controller (MVC) architecture pattern split into logical packages:

### 1. Java Source Code (`src/main/java/com/ensa/akinator`)

* **[App.java](file:///e:/Programming/School/ENSA/GI-S6/Java/Java-Project-Akinator/src/main/java/com/ensa/akinator/App.java)**: The main entry point of the JavaFX application. Handles window stage initialization, global CSS resource bindings, and screen navigation routing.
* **[Controllers/](file:///e:/Programming/School/ENSA/GI-S6/Java/Java-Project-Akinator/src/main/java/com/ensa/akinator/Controllers)**: Associates FXML visual views with code logic:
  * **[PrimaryController.java](file:///e:/Programming/School/ENSA/GI-S6/Java/Java-Project-Akinator/src/main/java/com/ensa/akinator/Controllers/PrimaryController.java)**: Handles the home menu, background sound controls, and overlays for the custom glassmorphic About/Scoring rules dialog.
  * **[LoginFXMLController.java](file:///e:/Programming/School/ENSA/GI-S6/Java/Java-Project-Akinator/src/main/java/com/ensa/akinator/Controllers/LoginFXMLController.java)**: Evaluates user authentication and handles login form submissions.
  * **[SignUpFXMLController.java](file:///e:/Programming/School/ENSA/GI-S6/Java/Java-Project-Akinator/src/main/java/com/ensa/akinator/Controllers/SignUpFXMLController.java)**: Processes registration forms, hashes player passwords, and updates user tables.
  * **[GameController.java](file:///e:/Programming/School/ENSA/GI-S6/Java/Java-Project-Akinator/src/main/java/com/ensa/akinator/Controllers/GameController.java)**: Orchestrates the offline Classic mode, updating step labels, scoring, and yes/no options.
  * **[AiGameController.java](file:///e:/Programming/School/ENSA/GI-S6/Java/Java-Project-Akinator/src/main/java/com/ensa/akinator/Controllers/AiGameController.java)**: Coordinates the Groq API connection loop, executing API requests inside background threads to prevent UI freezes.
  * **[CharacterController.java](file:///e:/Programming/School/ENSA/GI-S6/Java/Java-Project-Akinator/src/main/java/com/ensa/akinator/Controllers/CharacterController.java)**: Displays the character's name and image on a successful guess.
  * **[NotFoundController.java](file:///e:/Programming/School/ENSA/GI-S6/Java/Java-Project-Akinator/src/main/java/com/ensa/akinator/Controllers/NotFoundController.java)**: Handles the fallback screen when Akinator fails to guess the player's character.
  * **[HistoryController.java](file:///e:/Programming/School/ENSA/GI-S6/Java/Java-Project-Akinator/src/main/java/com/ensa/akinator/Controllers/HistoryController.java)**: Renders user history tables and generates the player leaderboard.
* **[Exceptions/](file:///e:/Programming/School/ENSA/GI-S6/Java/Java-Project-Akinator/src/main/java/com/ensa/akinator/Exceptions)**: Custom error implementations.
  * **[AIConnectionFailedException.java](file:///e:/Programming/School/ENSA/GI-S6/Java/Java-Project-Akinator/src/main/java/com/ensa/akinator/Exceptions/AIConnectionFailedException.java)**: Catches REST API connection timeouts.
* **[GameEngines/](file:///e:/Programming/School/ENSA/GI-S6/Java/Java-Project-Akinator/src/main/java/com/ensa/akinator/GameEngines)**: The algorithmic brains of the game:
  * **[GameEngine.java](file:///e:/Programming/School/ENSA/GI-S6/Java/Java-Project-Akinator/src/main/java/com/ensa/akinator/GameEngines/GameEngine.java)**: Filters preloaded character data nodes by checking boolean attribute responses.
  * **[AIGameEngine.java](file:///e:/Programming/School/ENSA/GI-S6/Java/Java-Project-Akinator/src/main/java/com/ensa/akinator/GameEngines/AIGameEngine.java)**: Manages dialog history arrays and frames prompts to instruct the LLM to act strictly as Akinator.
* **[Managers/](file:///e:/Programming/School/ENSA/GI-S6/Java/Java-Project-Akinator/src/main/java/com/ensa/akinator/Managers)**: External integrations and data queries:
  * **[DatabaseManager.java](file:///e:/Programming/School/ENSA/GI-S6/Java/Java-Project-Akinator/src/main/java/com/ensa/akinator/Managers/DatabaseManager.java)**: Manages SQLite connection pooling.
  * **[PlayerDAO.java](file:///e:/Programming/School/ENSA/GI-S6/Java/Java-Project-Akinator/src/main/java/com/ensa/akinator/Managers/PlayerDAO.java)**: Coordinates SQL operations for credentials, session scores, high scores, and history tables.
  * **[AIManager.java](file:///e:/Programming/School/ENSA/GI-S6/Java/Java-Project-Akinator/src/main/java/com/ensa/akinator/Managers/AIManager.java)**: Establishes HTTP connections to Groq to send user messages and retrieve Llama responses.
* **[Models/](file:///e:/Programming/School/ENSA/GI-S6/Java/Java-Project-Akinator/src/main/java/com/ensa/akinator/Models)**: Plain Java object (POJO) definitions:
  * **[Player.java](file:///e:/Programming/School/ENSA/GI-S6/Java/Java-Project-Akinator/src/main/java/com/ensa/akinator/Models/Player.java)**: Stores active player profile details.
  * **[Character.java](file:///e:/Programming/School/ENSA/GI-S6/Java/Java-Project-Akinator/src/main/java/com/ensa/akinator/Models/Character.java)**: Represents character models.
  * **[Question.java](file:///e:/Programming/School/ENSA/GI-S6/Java/Java-Project-Akinator/src/main/java/com/ensa/akinator/Models/Question.java)** & **[FilteredQuestion.java](file:///e:/Programming/School/ENSA/GI-S6/Java/Java-Project-Akinator/src/main/java/com/ensa/akinator/Models/FilteredQuestion.java)**: Structural query objects.
  * **[Answer.java](file:///e:/Programming/School/ENSA/GI-S6/Java/Java-Project-Akinator/src/main/java/com/ensa/akinator/Models/Answer.java)** & **[AnswerEnum.java](file:///e:/Programming/School/ENSA/GI-S6/Java/Java-Project-Akinator/src/main/java/com/ensa/akinator/Models/AnswerEnum.java)**: Maps user choices (`Yes`, `No`, `Don't Know`, etc.).
* **[Utils/](file:///e:/Programming/School/ENSA/GI-S6/Java/Java-Project-Akinator/src/main/java/com/ensa/akinator/Utils)**:
  * **[Global.java](file:///e:/Programming/School/ENSA/GI-S6/Java/Java-Project-Akinator/src/main/java/com/ensa/akinator/Utils/Global.java)**: Stores static global state variables like `loggedInPlayer`.
  * **[Functions.java](file:///e:/Programming/School/ENSA/GI-S6/Java/Java-Project-Akinator/src/main/java/com/ensa/akinator/Utils/Functions.java)**: Instantiates visual dialog windows.
  * **[AudioPlayer.java](file:///e:/Programming/School/ENSA/GI-S6/Java/Java-Project-Akinator/src/main/java/com/ensa/akinator/Utils/AudioPlayer.java)**: Controls background music.

### 2. View Resources (`src/main/resources/com/ensa/akinator`)
* **FXML Visual Templates**: Layouts for screens (`primary.fxml`, `game.fxml`, `aigame.fxml`, `LoginFXML.fxml`, `SignUp.fxml`, `character.fxml`, `notfound.fxml`, `history.fxml`).
* **CSS stylesheets (`styles/`)**: Custom rules styling user interfaces:
  * `primary.css`: Styles for the main interface, cards, and text layout.
  * `login_signup.css`: Styles for the login/signup inputs and cards.
  * `game.css` & `aigame.css`: Designs active hud elements, step markers, and score headers.
  * `history.css`: Sets leaderboard layouts, including golden row highlights for the active user.
* **Assets & Audios (`assets/`, `audios/`)**: Graphic assets and background soundtracks.

---

## ⚙️ Gameplay & Systems Logic

### 1. User Sessions
* Non-logged-in users can play as guests, but their scores are not logged.
* Authenticated users start games with score tracking enabled. High scores are saved to `users.db` when exceeding personal records.

### 2. Classic Filter Algorithm
* The engine queries all available character matrices from `akinator.db`.
* Iteratively picks the question that bisects the candidate array most evenly.
* Filters characters based on yes/no answers until only 1 matching character remains.

### 3. AI Groq API Conversational Loop
* When AI mode starts, the engine instructs the Llama model to adopt the Akinator persona (asking one yes/no question at a time, avoiding polite conversational filler).
* User answers are stored in a history array and appended to the context window:
  ```
  You asked: "Is your character real?" User answered: "Yes"
  You asked: "Is he a soccer player?" User answered: "Yes"
  ```
* Once confidence levels reach 95%, Llama submits a final guess matching `I think it is [Name]`. The controller parses the guess to trigger the victory window.

### 4. Scoring System Rules
* **Answer Step**: Every question answered adds `+98` points to the active score.
* **Defeating Akinator**: If the engine fails to guess the character, the player earns a flat `+1489` points.
* **Game Completion**: Total matches increment by `+1`, and a high score is recorded if the active score exceeds the user's high score.

---

## 🔮 Future Roadmap

* **Dynamic DB Learning Core**: Implement a system that updates the offline `akinator.db` when Akinator loses. The user is prompted for their character's name and a distinguishing yes/no question, expanding the database dynamically.
* **Voice-Activated Commands**: Integrate speech recognition so players can speak answers ("Yes", "No", "Probably") to advance gameplay.
* **Multiplayer Battles**: Implement WebSockets to allow two local players to compete against each other in real-time.