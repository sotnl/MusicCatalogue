# 🎵 My Favorite Music Catalogue

A JavaFX + PostgreSQL desktop app for managing your personal music library.
Built with the same architecture as the Student Management System it was derived from.

## Stack

| Layer      | Technology                          |
|------------|-------------------------------------|
| UI         | JavaFX 21 + FXML                    |
| Language   | Java 21                             |
| Build      | Maven                               |
| Database   | PostgreSQL                          |
| Auth       | SHA-256 password hashing (dotenv)   |

## Features

- Login / Sign-up with hashed passwords
- Add, update, delete, and browse songs
- Fields: Title, Artist, Album, Genre (dropdown), Release Year, Rating (1–5 ★), Notes
- Paginated table (10 songs per page)
- 12 built-in music genres
- 20 seed songs to get started

## Setup

### 1. Create the database

```sql
CREATE DATABASE music_catalogue;
```

Then run the schema + seed file:

```bash
psql -U postgres -d music_catalogue -f music_catalogue.sql
```

### 2. Configure the .env file

Edit `.env` in the project root:

```
DB_URL=jdbc:postgresql://localhost:5432/music_catalogue
DB_USER=postgres
DB_PASSWORD=your_password_here
```

### 3. Run the app

```bash
mvn clean javafx:run
```

## Default Credentials

| Username | Password  |
|----------|-----------|
| admin    | admin123  |
| user     | pass1234  |

## Project Structure

```
src/main/java/com/example/music_catalogue/
├── app/            MusicApplication.java (JavaFX entry point)
├── controller/     AuthController, SignUpController, MusicController
├── factory/        AuthWindowFactory, MusicWindowFactory
├── model/          Song, Genre, UserAccount
├── repository/     SongRepository, GenreRepository, AuthRepository
└── util/           Database, PasswordUtil

src/main/resources/com/example/music_catalogue/
├── login-view.fxml
├── signup-view.fxml
└── music-view.fxml
```
