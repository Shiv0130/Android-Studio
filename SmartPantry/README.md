# Smart Pantry Manager (MAD700)

Java Android application that suggests recipes based on leftover pantry ingredients to help reduce food waste.

**Student:** Richfield BSc IT — MAD700 Practical Assignment  
**Repo:** https://github.com/Shiv0130/Android-Studio (SmartPantry folder)

## Features
- **Pantry list** with RecyclerView (SQLite-backed)
- **CRUD**: add, edit (tap row), delete (long-press) ingredients
- **Form validation** (name + positive quantity required)
- **Suggested recipes** matched strictly to current pantry items
- **Recipe detail** (prep time, ingredients, step-by-step instructions)
- **Settings** with SharedPreferences (display name, notifications)
- **Bottom navigation**: Pantry | Recipes | Settings
- All user-facing text from `strings.xml` (no hardcoded UI copy)

## Database option: SQLite (and why)
SQLite is built into Android, works fully offline and needs no account or server. The data is relational (one recipe has many ingredient lines), so tables with a foreign key fit it well. Firebase or PostgreSQL would add a network dependency and a backend for a single-user, on-device app.

## Database setup
There is nothing to install. `PantryDbHelper` (extends `SQLiteOpenHelper`) creates the database automatically on first launch.

- **File:** `smart_pantry.db` (app-private storage)
- **Version:** `2` (v2 added the recipe tables; `onUpgrade` rebuilds only the recipe tables and keeps the pantry)

| Table | Columns |
|---|---|
| `pantry` | `_id` INTEGER PK AUTOINCREMENT, `name` TEXT NOT NULL, `quantity` REAL NOT NULL, `unit` TEXT, `expiry` TEXT |
| `recipes` | `_id` INTEGER PK AUTOINCREMENT, `name` TEXT NOT NULL, `instructions` TEXT, `prep` INTEGER (minutes) |
| `recipe_ingredients` | `_id` INTEGER PK AUTOINCREMENT, `recipe_id` INTEGER NOT NULL (FK to `recipes._id`), `name` TEXT NOT NULL, `quantity` REAL NOT NULL, `unit` TEXT |

**Seeding**
- `recipes` and `recipe_ingredients` are seeded with **18 recipes** in `onCreate` (and again after an upgrade).
- If the pantry is empty, `MainActivity.seedIfEmpty()` adds 3 sample items: Tomato 3 units, Rice 2 cups and Eggs 6 units.

**Reset the database**
- On the device: Settings > Apps > SmartPantry > Storage > **Clear storage**, or uninstall the app.
- With adb: `adb shell pm clear com.example.smartpantry`, or `adb uninstall com.example.smartpantry`.
- Settings (display name, notifications) are stored separately in SharedPreferences (`smart_pantry_prefs`), and clearing data resets them too.

## Build and run
1. Clone it with `git clone https://github.com/Shiv0130/Android-Studio.git`.
2. In Android Studio, choose **File > Open** and select the `SmartPantry` folder (not the repo root).
3. Let Gradle sync finish (it uses the JDK bundled with Android Studio).
4. Start an emulator in Device Manager (tested on a "Small Phone" AVD) or connect a device (Android 6.0 / API 23 or newer).
5. Click **Run 'app'**.

Command line: `cd SmartPantry && ./gradlew assembleDebug` (on Windows, `gradlew.bat assembleDebug`). Then run `adb install -r app/build/outputs/apk/debug/app-debug.apk`.

## Version control (lab history)
| Lab | Focus |
|-----|--------|
| 1 | Project + strings |
| 2–3 | Layout shell, RecyclerView, adapter |
| 4 | SQLite helper + load list |
| 5–6 | Add/Edit form + delete |
| 7–8 | Recipe matching, detail, Settings |
| 9 | Polish, README, submission branch |

Branch **`lab-9`** holds the Lab 9 version. The Phase 2 fixes (strict quantity matching, plural/unit handling, 18 seeded recipes in SQLite v2, Gradle wrapper, launcher icons) are on **`phase-2-fixes`**, which is the submission branch.

## Match rule
A recipe is suggested only if **every** required ingredient is in the pantry **with at least the required quantity**. Names are case-insensitive and singular/plural safe (tomatoes = tomato), and units are converted (kg to g, L to ml). See `Recipe.matches()` and `IngredientMatcher.hasEnough()`.

## Demo path
1. Open app → seeded sample items (or add your own)
2. FAB → add ingredient → validation toasts if empty/invalid
3. Tap row → edit → save
4. Long-press → delete confirm
5. Recipes tab → matched recipes or empty message
6. Tap recipe → detail
7. Settings → save name/toggle → reopen to confirm persistence
